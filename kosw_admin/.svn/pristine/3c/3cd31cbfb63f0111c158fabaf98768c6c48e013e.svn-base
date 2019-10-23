package kr.rapids.kosw.admin.model;

import org.apache.ibatis.type.Alias;

/**
 * 페이지 관련 클레스
 */
@Alias("limitPage")
public class Page {
	
	private Integer totalCount;
	private Integer startList;
	private Integer pageViewCnt;
	
	private String search;
	private String sort;
	
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search.trim();
	}

	public void setPage(PageNavigation pageNavigation){
		totalCount = pageNavigation.getTotalCount();
		startList = pageNavigation.getStartList();
		pageViewCnt = pageNavigation.getPageViewCnt();
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getStartList() {
		return startList;
	}

	public void setStartList(Integer startList) {
		this.startList = startList;
	}

	public Integer getPageViewCnt() {
		return pageViewCnt;
	}

	public void setPageViewCnt(Integer pageViewCnt) {
		this.pageViewCnt = pageViewCnt;
	}
	
	
	
}