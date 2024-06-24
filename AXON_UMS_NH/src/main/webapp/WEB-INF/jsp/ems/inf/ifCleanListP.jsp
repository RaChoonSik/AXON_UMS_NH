<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2024.05.15
	*	설명 : 클린메일 목록 화면
	**********************************************************/
--%>
<%@page import="java.io.*,java.util.*,java.sql.*,java.text.*, java.net.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header_ems.jsp" %>
<script type="text/javascript" src="<c:url value='/js/ums.common.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/ems/inf/ifCleanListP.js'/>"></script>

<body>
	<div id="wrapper">
			
	<c:choose>
		<c:when test="${empty NEO_FRAME_YN}">
			<header class="util">
				<h1 class="logo">
					<a href="/ems/index.ums"><span class="txt-blind">LOGO</span></a>
				</h1>
				<!-- 공통 표시부// -->
				<c:import url="/WEB-INF/jsp/inc/top.jsp"></c:import>
				<!-- //공통 표시부 -->
			</header>
			<div id="wrap" class="ems">
				<!-- lnb// -->
				<div id="lnb">
					<!-- LEFT MENU -->
					<c:import url="/WEB-INF/jsp/inc/menu_ems.jsp"></c:import>
					<!-- LEFT MENU -->
				</div>
				<!-- //lnb -->
				<div class="content-wrap">
		</c:when>
		<c:otherwise>
			<div id="wrap" class="ems">
				<div class="content-wrap">
		</c:otherwise>
	</c:choose>
				<!-- content// -->
				<div id="content">

					<!-- cont-head// -->
					<section class="cont-head">
						<div class="title">
							<h2>클린메일조회</h2>
						</div>
					</section>
					<!-- //cont-head -->

					<!-- cont-body// -->
					<section class="cont-body">
				
					<form id="searchForm" name="searchForm" method="post">
						<input type="hidden" id="page" name="page" value="${searchVO.page}">
						<!-- 아래 rows 추가 -->
						<input type="hidden" id="rows" name="rows" value="<c:out value='${searchVO.rows}'/>">
						<!-- 추가 끝-->
						<input type="hidden" id="ifCampId" name="ifCampId" value="">
					
						<fieldset>
							<legend>조회 및 목록</legend>
	
							<!-- 조회// -->
							<div class="graybox">
								<div class="title-area">
									<h3 class="h3-title">조회</h3>
								</div> 
								
								<div class="list-area">
									<ul>
										<li>
											<label>작업년월</label>
											<div class="list-item" >
												<div class="select"  >
												<fmt:parseDate var="startDt" value="${searchVO.searchStartDt}" pattern="yyyyMMdd"/>
												<fmt:formatDate var="searchStartDt" value="${startDt}" pattern="yyyy"/> 
												
												<select id="searchCleanYY" name="SearchCleanYY"  title="작업년 선택"  >
																<option value="0">선택</option>
																	<c:forEach items="${yearList}" var="yList">
																		<option value="<c:out value='${yList.cd}'/>"  <c:if test="${yList.cd eq searchVO.searchCleanYY}"> selected</c:if>  ><c:out value='${yList.cd}'/></option>
																	</c:forEach>
												</select>
												</div>	
											</div>	
											<span class="hyppen date"></span>
											<div class="list-item" >
												<div class="select" >
												<fmt:formatDate var="searchStartMM" value="${startDt}" pattern="MM"/> 	
												<select id="searchCleanMM" name="searchCleanMM"  title="작업년 선택" >
																<option value="0">선택</option>
																	<c:forEach items="${monthList}" var="mList">
																		<option value="<c:out value='${mList.cd}'/>"  <c:if test="${mList.cd eq searchVO.searchCleanMM}"> selected</c:if>  ><c:out value='${mList.cd}'/></option>
																	</c:forEach>
															</select>
												</div>	
											</div>
										</li>
										<li>
											<label>고객ID</label>
											<div class="list-item">
												<input type="text" id="searchIfId" name="searchIfId" value="${searchVO.searchIfId}"  maxlength="48" placeholder="수신자아이디를 입력해주세요.">
											</div>
										</li>
										<li>
											<label>고객명</label>
											<div class="list-item">
												<input type="text" id="searchIfName" name="searchIfName" value="${searchVO.searchIfName}"  maxlength="48" placeholder="수신자명을 입력해주세요.">
											</div>
										</li>
										<li>
											<label>고객이메일</label>
											<div class="list-item">
												<input type="text" id="searchIfEmail" name="searchIfEmail" value="${searchVO.searchIfEmail}"  maxlength="48" placeholder="수신자이메일을 입력해주세요.">
											</div>
										</li>
										<li>
											<label>오류건수</label>
											<div class="list-item">
												<input type="text" id="searchErrCnt" name="searchErrCnt" value="${searchVO.searchErrCnt}"  maxlength="48" placeholder="조회기간내 오류건수를 입력해주세요.">
											</div>
										</li>
									</ul>
								</div>
							</div>
							<!-- //조회 -->
	
							<!-- btn-wrap// -->
							<div class="btn-wrap">
								<button type="button" class="btn big fullblue" onclick="goSearch('1')">검색</button>
								<button type="button" class="btn big" onclick="goReset()">초기화</button>
							</div>
							<!-- //btn-wrap -->
	
							<!-- 목록&페이징// -->
							<div id="divIfBasicList" style="margin-top:36px;"></div>
							<!-- //목록&페이징 -->
								
						</fieldset>
					</form>
		
					</section>
					<!-- //cont-body -->

				</div>
				<!-- // content -->
			</div>
		</div>
		</div>
	</div>
	<!-- 클린메일 팝업// -->
	<%@ include file="/WEB-INF/jsp/ems/inf/pop/pop_ifCleanListP.jsp" %>
	<!-- //클린메일 팝업 -->

</body>
</html>

 