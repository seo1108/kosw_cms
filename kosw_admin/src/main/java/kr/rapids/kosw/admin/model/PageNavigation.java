package kr.rapids.kosw.admin.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;
/**
 * 페이지 관련 클레스
 */
@Alias("page")
public class PageNavigation {
	
	private String search = "";
	
	private int pageViewCnt = 10; 	//View에 보여질 리스트 수
	private int pageCount = 10; 	//한번에 보여질 페이징 카운터 Max
	
	private int pageNo = 1;			//현재 보여질 페이지 초기값
	private int totalCount = 0;		//전체 Data 수
	private int startPage = 0;		//시작 페이지 [1] <-- 1 or 11 or 21 or 31 .... 91
	private int endPage = 0;		//끝나는 페이지 시작이 1이면 - 10 11 이면 - 20 ...100
	private int totalPage = 0;		//총페이지 수
	private int beforePage = 0;		//이전페이지
	private int nextPage = 0;		//다음페이지
	private int startList = 0;		//시작 페이지 설정 (start rownum)
	private int endList = 0;		//끝 페이지 설정 (end rownum)
	private int rowNum = 0;			// DB table seq 를 사용하지 않고 표시할 페이지 번호
	private String all;				// 전체검색 키워드
	
	static public String excludePageNoQuery(String queryString) throws UnsupportedEncodingException{
		String result = "";
		Map<String, String> query_pairs = new HashMap<String, String>();
		String[] pairs = queryString.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
		    String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
		    if ("".equals(key)){
		    	continue;
		    }
		    if ("pageNo".equals(key)){
		    	continue;
		    }
		    String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : "";
		    query_pairs.put(key, value);
		}
		
		ArrayList<String> arrayList = new ArrayList<String>();
		for (String key : query_pairs.keySet()){
			String q = key + "=" + query_pairs.get(key);
			arrayList.add(q);
		}
		
		result = String.join("&", arrayList);
		return result;
	}
	
	static public String pageQueryString(String uri, String queryString, int pageNo) throws UnsupportedEncodingException{
		String result = "";
		Map<String, String> query_pairs = new HashMap<String, String>();
		if (queryString == null){
			queryString = "";
		}
		String[] pairs = queryString.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
		    String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
		    if ("".equals(key)){
		    	continue;
		    }
		    String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : "";
		    query_pairs.put(key, value);
		}
		query_pairs.put("pageNo", String.valueOf(pageNo));
		
		ArrayList<String> arrayList = new ArrayList<String>();
		for (String key : query_pairs.keySet()){
			String q = key + "=" + query_pairs.get(key);
			arrayList.add(q);
		}
		
		result = uri + "?" + String.join("&", arrayList);
		return result;
	}
	
	
	
	public PageNavigation() {
	}
	
	public PageNavigation paginate(int pageNo , int totalCount ){
		if (pageNo < 1){
			pageNo = 1;
		}
		this.totalCount = totalCount;
		this.pageNo = pageNo;
		
		totalPage = (totalCount / pageViewCnt) + (totalCount % pageViewCnt == 0 ? 0 : 1);	//총 페이지 수
		if (pageNo > totalPage){
			pageNo = totalPage;
		}
		
		startPage = ((pageNo - 1) / pageCount) * pageCount + 1;
		endPage = (startPage + pageCount) - 1;	//pageCount 만큼 page가 보이도록
		endPage = endPage > totalPage ? totalPage : endPage;
		
		
		
		if(pageNo == 1){beforePage = 1;}else{beforePage = pageNo - 1;}
		if (pageNo == totalPage){
			nextPage = totalPage;
		}else{
			nextPage = pageNo + 1;
		}
		
		
		
		startList = (pageNo -1) * pageViewCnt;
		endList = (pageNo) * pageViewCnt - 1;
		return this;
	}
	
	
	public PageNavigation(int pageNo , int totalCount ) {
		if (pageNo < 1){
			pageNo = 1;
		}
		
		this.totalCount = totalCount;
		this.pageNo = pageNo;
		
		totalPage = (totalCount / pageViewCnt) + (totalCount % pageViewCnt == 0 ? 0 : 1);	//총 페이지 수
		if (pageNo > totalPage){
			pageNo = totalPage;
		}
		
		if (pageNo < 1){
			pageNo = 1;
		}
		
		startPage = ((pageNo - 1) / pageCount) * pageCount + 1;
		endPage = (startPage + pageCount) - 1;	//pageCount 만큼 page가 보이도록
		endPage = endPage > totalPage ? totalPage : endPage;
		
		
		if(pageNo == 1){beforePage = 1;}else{beforePage = pageNo - 1;}
		if (pageNo == totalPage){
			nextPage = totalPage;
		}else{
			nextPage = pageNo + 1;
		}
		
		startList = (pageNo -1) * pageViewCnt;	
		endList = (pageNo) * pageViewCnt - 1;
		
		System.out.println(
				"totalPage :" + totalPage + "\n" +
				"startPage :" + startPage + "\n" + 
				"endPage :" + endPage + "\n" + 
				"startList : " + startList + "\n" + 
				"endList : " + endList + "\n" + 
				"pageViewCnt : " + pageViewCnt + "\n" + 
				"beforePage : " + beforePage + "\n" + 
				"nextPage : " + nextPage 
				);
	}
	
	
	public PageNavigation(int pageNo , int totalCount, int pageViewCnt ) {
		if (pageNo < 1){
			pageNo = 1;
		}
		
		this.totalCount = totalCount;
		this.pageNo = pageNo;
		this.pageViewCnt = pageViewCnt;
		
		totalPage = (totalCount / pageViewCnt) + (totalCount % pageViewCnt == 0 ? 0 : 1);	//총 페이지 수
		if (pageNo > totalPage){
			pageNo = totalPage;
		}
		
		if (pageNo < 1){
			pageNo = 1;
		}
		
		startPage = ((pageNo - 1) / pageCount) * pageCount + 1;
		endPage = (startPage + pageCount) - 1;	//pageCount 만큼 page가 보이도록
		endPage = endPage > totalPage ? totalPage : endPage;
		
		
		
		if(pageNo == 1){
			beforePage = 1;
		}else{
			beforePage = pageNo - 1;
		}
		
		if (pageNo == totalPage){
			nextPage = totalPage;
		}else{
			nextPage = pageNo + 1;
		}
		
		
		startList = (pageNo -1) * pageViewCnt;	
		endList = (pageNo) * pageViewCnt - 1;
		
		System.out.println(
				"totalPage :" + totalPage + "\n" +
				"startPage :" + startPage + "\n" + 
				"endPage :" + endPage + "\n" + 
				"startList : " + startList + "\n" + 
				"endList : " + endList + "\n" + 
				"pageViewCnt : " + pageViewCnt + "\n" + 
				"beforePage : " + beforePage + "\n" + 
				"nextPage : " + nextPage 
				);
	}
	
	
	
	
	
	
	public int getPageViewCnt() {
		return pageViewCnt;
	}

	public void setPageViewCnt(int pageViewCnt) {
		this.pageViewCnt = pageViewCnt;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBeforePage() {
		return beforePage;
	}

	public void setBeforePage(int beforePage) {
		this.beforePage = beforePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getStartList() {
		return startList;
	}

	public void setStartList(int startList) {
		this.startList = startList;
	}
	
	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	
	public int getEndList() {
		return endList;
	}

	public void setEndList(int endList) {
		this.endList = endList;
	}

	public static void main(String[] args) {
		new PageNavigation(1, 300);
	}
	
	
}