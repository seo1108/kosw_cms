<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<security:authentication property="details" var="user"/>


<c:set var="path" value="${requestScope['javax.servlet.forward.servlet_path']}" /> 

<c:url var="userListURL" value="/userList">
	<c:param name="cSeq" value="${customer.custSeq }"/>
</c:url>

<c:if test="${fn:indexOf(path, '/buildingList') != -1}">
<c:set var="title" value="건물 리스트" /></c:if>

<c:if test="${fn:indexOf(path, '/userList') != -1}">
<c:set var="title" value="사용자 리스트" /></c:if>

<c:url var="userAllListURL" value="/userAllList">
</c:url>

<c:if test="${fn:indexOf(path, '/userAllList') != -1}">
<c:set var="title" value="회원 리스트" /></c:if>

<c:if test="${fn:indexOf(path, '/cafeList') != -1}">
<c:set var="title" value="Cafe 리스트" /></c:if>


<!-- 우리회사 설정  -->
<c:url var="customerOneURL" value="/customerOne"/>
<c:if test="${fn:indexOf(path, '/customerOne') != -1}">
<c:set var="title" value="우리 회사 설정" /></c:if>

<c:url var="buildingURL" value="/building" >
	<c:param name="cSeq" value="${customer.custSeq }"/>
</c:url>
<c:if test="${fn:indexOf(path, '/building') != -1}">
<c:set var="title" value="건물 정보" /></c:if>



<c:url var="customerListURL" value="/customerList" />
<c:if test="${fn:indexOf(path, '/customerList') != -1}">
<c:set var="title" value="고객사 리스트" /></c:if>

<c:url var="cafeListURL" value="/cafeList" />
<c:if test="${fn:indexOf(path, '/cafeList') != -1}">
<c:set var="title" value="Cafe 리스트" /></c:if>




<c:url var="userAllListURL" value="/userAllList" />
<c:if test="${fn:indexOf(path, '/userAllList') != -1}">
<c:set var="title" value="회원 리스트" /></c:if>

<c:url var="buildingListURL" value="/buildingList" />
<c:if test="${fn:indexOf(path, '/buildingList') != -1}">
<c:set var="title" value="건물 리스트" /></c:if>



<c:url var="customerAddURL" value="/customerAdd" />
<c:if test="${fn:indexOf(path, '/customerAdd') != -1}">
<c:set var="title" value="고객사 등록" /></c:if>



<c:url var="beaconManufacAddURL" value="/beaconManufacAdd" />
<c:if test="${fn:indexOf(path, '/beaconManufacAdd') != -1}">
<c:set var="title" value="비콘 제조사 등록" /></c:if>

<c:if test="${fn:indexOf(path, '/beaconManufacEdit') != -1}">
<c:set var="title" value="비콘 제조사 수정" /></c:if>

<c:url var="beaconManufacListURL" value="/beaconManufacList" />
<c:if test="${fn:indexOf(path, '/beaconManufacList') != -1}">
<c:set var="title" value="비콘 제조사 리스트" /></c:if>


<c:url var="statUserURL" value="/statUser" />
<c:if test="${fn:indexOf(path, '/statUser') != -1}">
<c:set var="title" value="회사 개별 통계" /></c:if>


<c:url var="statGroupURL" value="/statGroup">
	<c:param name="custSeq" value="${customer.custSeq }"/>
</c:url>
<c:if test="${fn:indexOf(path, '/statGroup') != -1}">
<c:set var="title" value="전체 그룹 통계" /></c:if>

<c:url var="statPerUserURL" value="/statPerUser" />
<c:if test="${fn:indexOf(path, '/statPerUser') != -1}">
<c:set var="title" value="개인 통계" /></c:if>


<c:url var="statCafeURL" value="/statCafeUser">
	<c:param name="cafeseq" value=""/>
</c:url>
<c:if test="${fn:indexOf(path, '/statCafeUser') != -1}">
<c:set var="title" value="Cafe 개별 통계" /></c:if>

<c:url var="statCafeCategoryURL" value="/statCafeCategory">
	<c:param name="cafeseq" value=""/>
</c:url>
<c:if test="${fn:indexOf(path, '/statCafeCategory') != -1}">
<c:set var="title" value="Cafe 카테고리 통계" /></c:if>



<c:url var="statCafeForAdminURL" value="/statCafeList">
	<c:param name="cafeseq" value=""/>
	<c:param name="type" value="individual"/>
</c:url>
<c:if test="${fn:indexOf(path, '/statCafeList') != -1}" />

<c:url var="statCafeCategoryForAdminURL" value="/statCafeList">
	<c:param name="cafeseq" value=""/>
	<c:param name="type" value="category"/>
</c:url>
<c:if test="${fn:indexOf(path, '/statCafeList') != -1}" />




<c:url var="beaconListURL" value="/beaconList" >
	<c:param name="cSeq" value="${customer.custSeq }"/>
</c:url>
<c:if test="${fn:indexOf(path, '/beaconList') != -1}">
<c:set var="title" value="비콘 리스트" /></c:if>

<c:url var="beaconSearchURL" value="/beaconSearch" />
<c:if test="${fn:indexOf(path, '/beaconSearch') != -1}">
<c:set var="title" value="비콘 검색" /></c:if>


<c:url var="beaconAddURL" value="/beaconAdd" >
	<c:param name="cSeq" value="${customer.custSeq }"/>
</c:url>
<c:if test="${fn:indexOf(path, '/beaconAdd') != -1}">
<c:set var="title" value="비콘 등록/수정" /></c:if>


<c:url var="characterListURL" value="/characterList" />
<c:if test="${fn:indexOf(path, '/characterList') != -1}">
	<c:set var="title" value="캐릭터 리스트" />
</c:if>

<c:url var="characterEditURL" value="/characterEdit" />
<c:if test="${fn:indexOf(path, '/characterEdit') != -1}">
	<c:set var="title" value="캐릭터 수정" />
</c:if>

<c:url var="characterAddURL" value="/characterAdd" />
<c:if test="${fn:indexOf(path, '/characterAdd') != -1}">
	<c:set var="title" value="캐릭터 등록" />
</c:if>


<c:url var="bbsListURL" value="/bbsList" />
<c:if test="${fn:indexOf(path, '/bbsList') != -1}">
	<c:set var="title" value="공지 게시판" />
</c:if>


<c:url var="pushListURL" value="/pushList" />
<c:if test="${fn:indexOf(path, '/pushList') != -1}">
	<c:set var="title" value="푸쉬 메세지" />
</c:if>

<c:url var="appVersionURL" value="/appVersion" />
<c:if test="${fn:indexOf(path, '/appVersion') != -1}">
	<c:set var="title" value="앱 버전 관리" />
</c:if>




<c:url var="cafeOfMineListURL" value="/cafeOfMineList" />
<c:if test="${fn:indexOf(path, '/cafeOfMineList') != -1}">
	<c:set var="title" value="내 Cafe 리스트" />
</c:if>



<script>
	$(function(){
		$(".menu").each(function(i,e){
			var t = $(e).find(".menu-name").text();
			console.log("${title}" + "___" + t + "__" + "${title}".indexOf(t));
			if ("${title}".indexOf(t) > -1) {
//			if (t == "${title}"){
				$(e).addClass("active");
				$(e).parents("div.collapse").eq(0).collapse("show");
			}else{
				$(e).removeClass("active");
				$(e).parents("div.collapse").eq(0).collapse("hide");
			}
		});	
	})
</script>


<%-- <div style="position:fixed;top:0px;left:0px;display:block;z-index:100;">
	<p>${path }</p>
</div> --%>



<div class="sidebar" data-color="orange" data-image='<c:url value="/assets/img/full-screen-image-3.jpg" />'>
    <!--

        Tip 1: you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple"
        Tip 2: you can also add an image using data-image tag

    -->
    
    <div class="logo">
        <a href="#" class="simple-text logo-mini">
        	&nbsp;&nbsp;&nbsp;&nbsp;
        </a>
		<a href="#" class="simple-text logo-normal">
			${user.custName }
		</a>
    </div>

	<div class="sidebar-wrapper">
        <div class="user">
			<div class="info">
				<div class="photo" style="border:0px;">
                    <img src='<c:url value="/img/icon.png" />' "/>
                </div>
					<span>
						${user.adminName }
					</span>
			</div>
        </div>

		
		
		
		<ul class="nav">
			<li>
				<a data-toggle="collapse" href="#myCafeList">
                    <i class="pe-7s-id"></i>
                    <p>Cafe
                       <b class="caret"></b>
                    </p>
                </a>
				<div class="collapse" id="myCafeList">
					<ul class="nav">
						<c:choose>
							<c:when test="${user.superAdmin eq 'true'}">
								<li class="menu">
									<a href="${cafeListURL }">
										<span class="sidebar-mini">&nbsp;</span>
										<span class="sidebar-normal menu-name">Cafe 리스트</span>
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li class="menu">
									<a href="${cafeOfMineListURL }">
										<span class="sidebar-mini">&nbsp;</span>
										<span class="sidebar-normal menu-name">내 Cafe 리스트</span>
									</a>
								</li>
							</c:otherwise>
						</c:choose>	
					</ul>
				</div>
			</li>
		
		
			<li class="menu">
				<a href="${customerOneURL }">
					<i class="pe-7s-id"></i>
					<p class="menu-name">우리 회사 설정</p>
				</a>
			</li>
			<%-- <c:if test="${user.superAdmin eq 'true'}">
			<li class="menu">
				<a href="${buildingURL }">
					<i class="fa fa-building"></i>
					<p class="menu-name">건물 정보</p>
				</a>
			</li>
			<li class="menu">
				<a href="${buildingListURL }">
					<i class="fa fa-building"></i>
					<p class="menu-name">건물 리스트</p>
				</a>
			</li>
			</c:if> --%>
			
			<li class="menu">
				<a href="${userListURL }">
					<i class="pe-7s-users"></i>
					<p class="menu-name">사용자 리스트</p>
				</a>
			</li>
			
			<c:if test="${user.superAdmin eq 'true'}">
			<li class="menu">
				<a href="${userAllListURL }">
					<i class="pe-7s-users"></i>
					<p class="menu-name">회원 리스트</p>
				</a>
			</li>
			</c:if>
			
			<li>
				<a data-toggle="collapse" href="#statList">
                    <i class="fa fa-bar-chart"></i>
                    <p>통계
                       <b class="caret"></b>
                    </p>
                </a>
				<div class="collapse" id="statList">
					<ul class="nav">
						<li class="menu">
							<a href="${statUserURL }">
								<span class="sidebar-mini">&nbsp;</span>
								<span class="sidebar-normal menu-name">회사 개별 통계</span>
							</a>
						</li>
						<li class="menu">
							<a href="${statGroupURL }">
								<span class="sidebar-mini">&nbsp;</span>
								<span class="sidebar-normal menu-name">전체 그룹 통계</span>
							</a>
						</li>
						
						
						
						<c:choose>
							<c:when test="${user.superAdmin eq 'true'}">
								<li class="menu">
									<a href="${statCafeForAdminURL }">
										<span class="sidebar-mini">&nbsp;</span>
										<span class="sidebar-normal menu-name">Cafe 개별 통계</span>
									</a>
								</li>
								
								<li class="menu">
									<a href="${statCafeCategoryForAdminURL }">
										<span class="sidebar-mini">&nbsp;</span>
										<span class="sidebar-normal menu-name">Cafe 카테고리 통계</span>
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li class="menu">
									<a href="${statCafeURL }">
										<span class="sidebar-mini">&nbsp;</span>
										<span class="sidebar-normal menu-name">Cafe 개별 통계</span>
									</a>
								</li>
								
								<li class="menu">
									<a href="${statCafeCategoryURL }">
										<span class="sidebar-mini">&nbsp;</span>
										<span class="sidebar-normal menu-name">Cafe 카테고리 통계</span>
									</a>
								</li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</li>
			
			<c:if test="${user.superAdmin eq 'true'}">
				<!-- 
				<li>
					<a data-toggle="collapse" href="#beaconList">
	                    <i class="pe-7s-signal"></i>
	                    <p>비콘
	                       <b class="caret"></b>
	                    </p>
	                </a>
					<div class="collapse" id="beaconList">
						<ul class="nav">
							<li class="menu">
								<a href="${beaconListURL }">
									<span class="sidebar-mini">&nbsp;</span>
									<span class="sidebar-normal menu-name">비콘 리스트</span>
								</a>
							</li>
							<li class="menu">
								<a href="${beaconSearchURL }">
									<span class="sidebar-mini">&nbsp;</span>
									<span class="sidebar-normal menu-name">비콘 검색</span>
								</a>
							</li>
							<li class="menu">
								<a href="${beaconAddURL }">
									<span class="sidebar-mini">&nbsp;</span>
									<span class="sidebar-normal menu-name">비콘 등록/수정</span>
								</a>
							</li>
						</ul>
					</div>
				</li>
				-->		
				<li>
					<a data-toggle="collapse" href="#customerList">
	                    <i class="fa fa-list-alt"></i>
	                    <p>Cafe
	                       <b class="caret"></b>
	                    </p>
	                </a>
					<div class="collapse" id="customerList">
						<ul class="nav">
							<li class="menu">
								<a href="${customerListURL }">
									<span class="sidebar-mini">&nbsp;</span>
									<span class="sidebar-normal menu-name">고객사 리스트</span>
								</a>
							</li>
							
							<!-- 
							<li class="menu">
								<a href="${customerAddURL }">
									<span class="sidebar-mini">&nbsp;</span>
									<span class="sidebar-normal menu-name">Cafe 등록</span>
								</a>
							</li>
							 -->
						</ul>
					</div>
				</li>
				
				<li>
					<a data-toggle="collapse" href="#characterList">
	                    <i class="pe-7s-piggy"></i>
	                    <p>캐릭터 메뉴
	                       <b class="caret"></b>
	                    </p>
	                </a>
					<div class="collapse" id="characterList">
						<ul class="nav">
							<li class="menu">
								<a href="${characterListURL }">
									<span class="sidebar-mini">&nbsp;</span>
									<span class="sidebar-normal menu-name">캐릭터 리스트</span>
								</a>
							</li>
							<li class="menu">
								<a href="${characterAddURL }">
									<span class="sidebar-mini">&nbsp;</span>
									<span class="sidebar-normal menu-name">캐릭터 등록</span>
								</a>
							</li>
						</ul>
					</div>
				</li>
			
			<!-- 
				<li>
					<a data-toggle="collapse" href="#manufacList">
	                    <i class="fa fa-bank"></i>
	                    <p>제조사 관리
	                       <b class="caret"></b>
	                    </p>
	                </a>
					<div class="collapse" id="manufacList">
						<ul class="nav">
							<li class="menu">
								<a href="${beaconManufacListURL }">
									<span class="sidebar-mini">&nbsp;</span>
									<span class="sidebar-normal menu-name">비콘 제조사 리스트</span>
								</a>
							</li>
							<li class="menu">
								<a href="${beaconManufacAddURL }">
									<span class="sidebar-mini">&nbsp;</span>
									<span class="sidebar-normal menu-name">비콘 제조사 등록</span>
								</a>
							</li>
						</ul>
					</div>
				</li>
			-->
				<li class="menu">
					<a href="${appVersionURL }">
						<i class="pe-7s-phone"></i>
						<p class="menu-name">앱 버전</p>
					</a>
				</li>
				
			</c:if>
			
			<li class="menu">
				<a href="${bbsListURL }">
					<i class="pe-7s-speaker"></i>
					<p class="menu-name">공지 게시판</p>
				</a>
			</li>
			
			
			<li class="menu">
				<a href="${pushListURL }">
					<i class="pe-7s-comment"></i>
					<p class="menu-name">앱 푸쉬 메세지</p>
				</a>
			</li>
			
		</ul>
	</div>
</div>