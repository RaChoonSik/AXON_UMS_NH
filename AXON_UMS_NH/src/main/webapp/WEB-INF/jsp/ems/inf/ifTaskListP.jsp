<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2024.05.15
	*	설명 : 연계작업관리 목록 화면
	**********************************************************/
--%>
<%@page import="java.io.*,java.util.*,java.sql.*,java.text.*, java.net.*" %>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header_ems.jsp" %>

<script type="text/javascript" src="<c:url value='/js/ums.common.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/ems/inf/ifTaskListP.js'/>"></script>


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
							<h2>연계작업목록</h2>
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
											<label>접수일</label>
											<div class="list-item">
												<!-- datepickerrange// -->
												<div class="datepickerrange fromDate">
													<label>
														<fmt:parseDate var="startDt" value="${searchVO.searchStartDt}" pattern="yyyyMMdd"/>
														<fmt:formatDate var="searchStartDt" value="${startDt}" pattern="yyyy.MM.dd"/> 
														<input type="text" id="searchStartDt" name="searchStartDt" value="<c:out value='${searchStartDt}'/>" readonly>
													</label>
												</div>
												<span class="hyppen date"></span>
												<div class="datepickerrange toDate">
													<label>
														<fmt:parseDate var="endDt" value="${searchVO.searchEndDt}" pattern="yyyyMMdd"/>
														<fmt:formatDate var="searchEndDt" value="${endDt}" pattern="yyyy.MM.dd"/> 
														<input type="text" id="searchEndDt" name="searchEndDt" value="<c:out value='${searchEndDt}'/>" readonly>
													</label>
												</div>
												<!-- //datepickerrange -->
											</div>
										</li>
									
										<li>
											<label>연계아이디</label>
											<div class="list-item">
												<input type="text" id="searchIfCampId" name="searchIfCampId" value="${searchVO.searchIfCampId}"  maxlength="10" placeholder="아이디를 입력해주세요.">
											</div>
										</li>
										<li>
											<label>연계명</label>
											<div class="list-item">
												<input type="text" id="searchIfCampName" name="searchIfCampName" value="${searchVO.searchIfCampName}"  maxlength="48" placeholder="연계캠페인명을 입력해주세요.">
											</div>
										</li>
										<li>
											<label>사용자그룹</label>
											<div class="list-item">
												<div class="select">
													<%-- 관리자의 경우 전체 요청그룹을 전시하고 그 외의 경우에는 해당 그룹만 전시함 --%>
													<c:if test="${'Y' eq NEO_ADMIN_YN}">
														<select id="searchDeptNo" name="searchDeptNo" title="사용자그룹 선택">
															<option value="0">선택</option>
															<c:if test="${fn:length(deptList) > 0}">
																<c:forEach items="${deptList}" var="dept">
																	<option value="<c:out value='${dept.deptNo}'/>" <c:if test="${dept.deptNo eq searchVO.searchDeptNo}"> selected</c:if>><c:out value='${dept.deptNm}'/></option>
																</c:forEach>
															</c:if>
														</select>
													</c:if>
													<c:if test="${'N' eq NEO_ADMIN_YN}">
														<select id="searchDeptNo" name="searchDeptNo"  title="사용자그룹 선택">
															<option value="0">선택</option>
															<c:if test="${fn:length(deptList) > 0}">
																<c:forEach items="${deptList}" var="dept">
																	<c:if test="${dept.deptNo == searchVO.searchDeptNo}">
																		<option value="<c:out value='${dept.deptNo}'/>" selected><c:out value='${dept.deptNm}'/></option>
																	</c:if>	
																</c:forEach>
															</c:if>
														</select>
													</c:if>
												</div>
											</div>
										</li>
										
										<li>
											<label>작업상태</label>
											<div class="list-item">
												<div class="select">
												<select id="searchWorkStatus" name="searchWorkStatus" title="">
														<option value="">선택</option>
														<c:if test="${fn:length(code205List) > 0}">
															<c:forEach items="${code205List}" var="code205">
																<option value="<c:out value='${code205.cd}'/>" <c:if test="${code205.cd eq searchVO.searchWorkStatus}"> selected</c:if> ><c:out value='${code205.cdNm}'/></option>
															</c:forEach>
														</c:if>
													</select>
												</div>	
											</div>
										</li>
										
										<li>
											<label>사용상태</label>
											<div class="list-item">
												<div class="select">
												<select id="searchStatus" name="searchStatus" title="">
														<option value="">선택</option>
														<c:if test="${fn:length(code206List) > 0}">
															<c:forEach items="${code206List}" var="code206">
																<option value="<c:out value='${code206.cd}'/>" <c:if test="${code206.cd eq searchVO.searchStatus}"> selected</c:if> ><c:out value='${code206.cdNm}'/></option>
															</c:forEach>
														</c:if>
													</select>
												</div>	
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

</body>
</html>
