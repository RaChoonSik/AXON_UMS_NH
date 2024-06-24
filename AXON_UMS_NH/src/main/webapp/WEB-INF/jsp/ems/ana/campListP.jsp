<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.09.07
	*	설명 : 통계분석 캠페인별 분석 목록 화면
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header_ems.jsp"%>

<script type="text/javascript" src="<c:url value='/js/ems/ana/campListP.js'/>"></script>

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
							<h2>캠페인별 분석 목록</h2>
						</div>

					</section>
					<!-- //cont-head -->

					<!-- cont-body// -->
					<section class="cont-body">

						<form id="searchForm" name="searchForm" method="post">
							<input type="hidden" id="page" name="page" value="<c:out value='${searchVO.page}'/>">
							<input type="hidden" id="rows" name="rows" value="<c:out value='${searchVO.rows}'/>">
							<input type="hidden" id="campNo" name="campNo" value="0">

							<fieldset>
								<legend>조회 및 목록</legend>

								<!-- 조회// -->
								<div class="graybox">
<!-- 									<div class="title-area">
										<h3 class="h3-title">조회</h3>
									</div>
 -->
									<div class="list-area">
										<ul>
											<li>
												<label>등록일시</label>
												<div class="list-item">
													<!-- datepickerrange// -->
													<div class="datepickerrange fromDate">
														<label> <fmt:parseDate var="startDt" value="${searchVO.searchStartDt}" pattern="yyyyMMdd" /> <fmt:formatDate var="searchStartDt" value="${startDt}" pattern="yyyy.MM.dd" /> <input type="text" id="searchStartDt" name="searchStartDt" value="<c:out value='${searchStartDt}'/>" readonly>
														</label>
													</div>
													<span class="hyppen date">~</span>
													<div class="datepickerrange toDate">
														<label> <fmt:parseDate var="endDt" value="${searchVO.searchEndDt}" pattern="yyyyMMdd" /> <fmt:formatDate var="searchEndDt" value="${endDt}" pattern="yyyy.MM.dd" /> <input type="text" id="searchEndDt" name="searchEndDt" value="<c:out value='${searchEndDt}'/>" readonly>
														</label>
													</div>
													<!-- //datepickerrange -->
												</div>
											</li>
											<li>
												<label>캠페인명</label>
												<div class="list-item">
													<input type="text" id="searchCampNm" name="searchCampNm" value="<c:out value='${searchVO.searchCampNm}'/>" placeholder="캠페인명을 입력해주세요.">
												</div>
											</li>
											<li>
												<label>캠페인목적</label>
												<div class="list-item">
													<div class="select">
														<select id="searchCampTy" name="searchCampTy" title="캠페인목적 선택">
															<option value="">선택</option>
															<c:if test="${fn:length(campTyList) > 0}">
																<c:forEach items="#{campTyList}" var="campTy">
																	<option value="<c:out value='${campTy.cd}'/>" <c:if test="${campTy.cd eq searchVO.searchCampTy}"> selected</c:if>><c:out value='${campTy.cdNm}' /></option>
																</c:forEach>
															</c:if>
														</select>
													</div>
												</div>
											</li>
											<li>
												<label>사용자그룹</label>
												<div class="list-item">
													<div class="select">
														<%-- 관리자의 경우 전체 요청그룹을 전시하고 그 외의 경우에는 해당 그룹만 전시함 --%>
														<c:if test="${'Y' eq NEO_ADMIN_YN}">
															<select id="searchDeptNo" name="searchDeptNo" onchange="getUserListSearch(this.value);" title="사용자그룹 선택">
																<option value="0">선택</option>
																<c:if test="${fn:length(deptList) > 0}">
																	<c:forEach items="${deptList}" var="dept">
																		<option value="<c:out value='${dept.deptNo}'/>" <c:if test="${dept.deptNo == searchVO.searchDeptNo}"> selected</c:if>><c:out value='${dept.deptNm}' /></option>
																	</c:forEach>
																</c:if>
															</select>
														</c:if>
														<c:if test="${'N' eq NEO_ADMIN_YN}">
															<select id="searchDeptNo" name="searchDeptNo" onchange="getUserListSearch(this.value);" title="사용자그룹 선택">
																<c:if test="${fn:length(deptList) > 0}">
																	<c:forEach items="${deptList}" var="dept">
																		<c:if test="${dept.deptNo == searchVO.searchDeptNo}">
																			<option value="<c:out value='${dept.deptNo}'/>" selected><c:out value='${dept.deptNm}' /></option>
																		</c:if>
																	</c:forEach>
																</c:if>
															</select>
														</c:if>
													</div>
												</div>
											</li>
											<li>
												<label>사용자명</label>
												<div class="list-item">
													<div class="select">
														<select id="searchUserId" name="searchUserId" title="사용자명 선택">
															<option value="">선택</option>
															<c:if test="${fn:length(userList) > 0}">
																<c:forEach items="${userList}" var="user">
																	<option value="<c:out value='${user.userId}'/>" <c:if test="${user.userId eq searchVO.searchUserId}"> selected</c:if>><c:out value='${user.userNm}' /></option>
																</c:forEach>
															</c:if>
														</select>

													</div>
												</div>
											</li>
											<li>
												<label>상태</label>
												<div class="list-item">
													<div class="select">
														<select id="searchStatus" name="searchStatus" title="상태 선택">
															<option value="ALL">선택</option>
															<c:if test="${fn:length(statusList) > 0}">
																<c:forEach items="${statusList}" var="status">
																	<option value="<c:out value='${status.cd}'/>" <c:if test="${status.cd eq searchVO.searchStatus}"> selected</c:if>><c:out value='${status.cdNm}' /></option>
																</c:forEach>
															</c:if>
														</select>

													</div>
												</div>
											</li>
										</ul>
									</div>
									<!-- btn-wrap// -->
									<div class="btn-wrap">
										<button type="button" class="btn big fullblue" onclick="goSearch('1');">검색</button>
										<!-- <button type="button" class="btn big" onclick="goInit();">초기화</button> -->
									</div>
									<!-- //btn-wrap -->
								</div>
								<!-- //조회 -->


								<!-- 목록&페이징// -->
								<div id="divCampList"></div>
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

	<!-- 신규발송팝업// -->
	<%@ include file="/WEB-INF/jsp/inc/pop/pop_new_mail.jsp"%>
	<!-- //신규발송팝업 -->
</body>
</html>
