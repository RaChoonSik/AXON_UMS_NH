<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.08.13
	*	설명 : 캠페인 정보 등록 화면
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header_ems.jsp"%>

<script type="text/javascript" src="<c:url value='/js/ems/cam/campAddP.js'/>"></script>

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
				<div id="content" class="single-style">

					<!-- cont-head// -->
					<section class="cont-head">
						<div class="title">
							<h2>캠페인 신규등록</h2>
						</div>
					</section>
					<!-- //cont-head -->

					<!-- cont-body// -->
					<section class="cont-body">

						<div class="manage-wrap">
							<form id="campaignInfoForm" name="campaignInfoForm" method="post">
								<fieldset>
									<legend>조건 및 내용</legend>

									<!-- 조건// -->
									<div class="graybox">
										<div class="title-area">
											<h3 class="h3-title">조건</h3>
											<span class="required">*필수입력 항목</span>
										</div>

										<div class="list-area">
											<ul>
												<li>
													<label class="required">캠페인목적</label>
													<div class="list-item">
														<div class="select">
															<select id="campTy" name="campTy" title="캠페인목적 선택">
																<option value="">선택</option>
																<c:if test="${fn:length(campTyList) > 0}">
																	<c:forEach items="${campTyList}" var="campTy">
																		<option value="<c:out value='${campTy.cd}'/>"><c:out value='${campTy.cdNm}' /></option>
																	</c:forEach>
																</c:if>
															</select>
														</div>
													</div>
												</li>
											<%-- 관리자의 경우 전체 요청부서를 전시하고 그 외의 경우에는 숨김 --%>
											<c:if test="${'Y' eq NEO_ADMIN_YN}">
												<li>
													<label class="required">사용자그룹</label>
													<div class="list-item">
														<div class="select">
															<select id="deptNo" name="deptNo" onchange="getUserList(this.value);" title="사용자그룹 선택">
																<option value="0">선택</option>
																<c:if test="${fn:length(deptList) > 0}">
																	<c:forEach items="${deptList}" var="dept">
																		<option value="<c:out value='${dept.deptNo}'/>"><c:out value='${dept.deptNm}' /></option>
																	</c:forEach>
																</c:if>
															</select>
														</div>
													</div>
												</li>
												<li>
													<label class="required">사용자명</label>
													<div class="list-item">
														<div class="select">
															<select id="userId" name="userId" title="사용자명 선택">
																<option value="">선택</option>
															</select>
														</div>
													</div>
												</li>
											</c:if>
												<li>
													<label class="required">상태</label>
													<div class="list-item">
														<div class="select">
															<select id="status" name="status" title="상태 선택">
																<option value="">선택</option>
																<c:if test="${fn:length(statusList) > 0}">
																	<c:forEach items="${statusList}" var="status">
																		<option value="<c:out value='${status.cd}'/>" <c:if test="${status.cd eq '000'}"> selected</c:if>><c:out value='${status.cdNm}' /></option>
																	</c:forEach>
																</c:if>
															</select>
														</div>
													</div>
												</li>
												<!-- <li>
													<label>EAI 번호</label>
													<div class="list-item">
														<input type="text" id="eaiCampNo" name="eaiCampNo" maxlength="40" placeholder="EAI 번호를 입력해주세요.">
													</div>
												</li> -->
											</ul>
										</div>
									</div>
									<!-- //조건 -->

									<!-- 메일 내용// -->
									<div class="graybox">
										<div class="title-area">
											<h3 class="h3-title">캠페인 내용</h3>
										</div>

										<div class="list-area">
											<ul>
												<li class="col-full">
													<label class="required">캠페인명</label>
													<div class="list-item">
														<input type="text" id="campNm" name="campNm" placeholder="캠페인명을 입력해주세요." value="">
													</div>
												</li>
												<li class="col-full">
													<label>설명</label>
													<div class="list-item">
														<textarea id="campDesc" name="campDesc" placeholder="캠페인 설명을 입력해주세요."></textarea>
													</div>
												</li>
											</ul>
										</div>
										<!-- btn-wrap// -->
										<div class="btn-wrap">
											<button type="button" class="btn big fullblue" onclick="goAdd();">등록</button>
											<button type="button" class="btn big" onclick="goCancel();">취소</button>
										</div>
										<!-- //btn-wrap -->
									</div>
									<!-- //메일 내용 -->


								</fieldset>
							</form>
						</div>

					</section>
					<!-- //cont-body -->

				</div>
				<!-- // content -->
			</div>
		</div>
		</div>
	</div>

	<!-- 신규발송팝업// -->
	<%@ include file="/WEB-INF/jsp/inc/pop/pop_new_mail.jsp"%>
	<!-- //신규발송팝업 -->
</body>
</html>
