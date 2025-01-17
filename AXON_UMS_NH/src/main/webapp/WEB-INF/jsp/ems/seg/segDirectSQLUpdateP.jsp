<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.08.22
	*	설명 : 수신자그룹 등록(직접SQL) 화면
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header_ems.jsp" %>

<script type="text/javascript" src="<c:url value='/js/ems/seg/segDirectSQLUpdateP.js'/>"></script>

<body>
	<div id="wrapper">
		<header class="util">
			<h1 class="logo">
				<a href="/ems/index.ums"><span class="txt-blind">LOGO</span></a>
			</h1>
			<!-- 공통 표시부// -->
			<%@ include file="/WEB-INF/jsp/inc/top.jsp"%>
			<!-- //공통 표시부 -->
		</header>
		<div id="wrap" class="ems">

			<!-- lnb// -->
			<div id="lnb">
				<!-- LEFT MENU -->
				<%@ include file="/WEB-INF/jsp/inc/menu_ems.jsp"%>
				<!-- LEFT MENU -->
			</div>
			<!-- //lnb -->
			<div class="content-wrap">
				<!-- content// -->
				<div id="content">

					<!-- cont-head// -->
					<section class="cont-head">
						<div class="title">
							<h2>수신자그룹 정보수정(직접SQL)</h2>
						</div>

					</section>
					<!-- //cont-head -->

					<!-- cont-body// -->
					<section class="cont-body">

						<form id="searchForm" name="searchForm" method="post">
							<input type="hidden" name="page" value="<c:out value='${searchVO.page}'/>">
							<input type="hidden" name="searchSegNm" value="<c:out value='${searchVO.searchSegNm}'/>">
							<input type="hidden" name="searchDeptNo" value="<c:out value='${searchVO.searchDeptNo}'/>">
							<input type="hidden" name="searchUserId" value="<c:out value='${searchVO.searchUserId}'/>">
							<input type="hidden" name="searchCreateTy" value="<c:out value='${searchVO.searchCreateTy}'/>">
							<input type="hidden" name="searchStatus" value="<c:out value='${searchVO.searchStatus}'/>">
							<input type="hidden" name="searchStartDt" value="<c:out value='${searchVO.searchStartDt}'/>">
							<input type="hidden" name="searchEndDt" value="<c:out value='${searchVO.searchEndDt}'/>">
							<input type="hidden" name="dbConnNo" value="<c:out value='${searchVO.dbConnNo}'/>">
						</form>
						<form id="segInfoForm" name="segInfoForm">
							<input type="hidden" id="page" name="page" value="1">
							<input type="hidden" id="segNo" name="segNo" value="<c:out value='${segmentInfo.segNo}'/>">
							<input type="hidden" id="dbConnNo" name="dbConnNo" value="<c:out value='${segmentInfo.dbConnNo}'/>">
							<input type="hidden" id="status" name="status" value="<c:out value='${segmentInfo.status}'/>">
							<input type="hidden" id="mergeKey" name="mergeKey" value="<c:out value='${segmentInfo.mergeKey}'/>">
							<input type="hidden" id="mergeCol" name="mergeCol" value="<c:out value='${segmentInfo.mergeCol}'/>">
							<input type="hidden" id="createTy" name="createTy" value="<c:out value='${segmentInfo.createTy}'/>">
							<input type="hidden" id="serviceGb" name="serviceGb" value="<c:out value='${segmentInfo.serviceGb}'/>">
							<input type="hidden" id="testType" name="testType">

							<fieldset>
								<legend>조건 및 쿼리등록</legend>

								<!-- 조건// -->
								<div class="graybox single-style">
									<div class="title-area">
										<!-- <h3 class="h3-title">조건</h3> -->
										<span class="required">*필수입력 항목</span>
									</div>

									<div class="list-area">
										<ul>
											<li>
												<label class="required">Connection</label>
												<div class="list-item">
													<c:set var="dbConnNm" value="" />
													<c:if test="${fn:length(dbConnList) > 0}">
														<c:forEach items="${dbConnList}" var="dbConn">
															<c:if test="${segmentInfo.dbConnNo == dbConn.dbConnNo}">
																<c:set var="dbConnNm" value="${dbConn.dbConnNm}" />
															</c:if>
														</c:forEach>
													</c:if>
													<input type="text" value="<c:out value='${dbConnNm}'/>" readonly>
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
																<!-- 그룹 선택 -->
																<c:if test="${fn:length(deptList) > 0}">
																	<c:forEach items="${deptList}" var="dept">
																		<option value="<c:out value='${dept.deptNo}'/>" <c:if test="${segmentInfo.deptNo == dept.deptNo}"> selected</c:if>><c:out value='${dept.deptNm}' /></option>
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
															<select id="userId" name="userId" title="사용자 선택">
																<option value="">선택</option>
																<c:if test="${fn:length(userList) > 0}">
																	<c:forEach items="${userList}" var="user">
																		<option value="<c:out value='${user.userId}'/>" <c:if test="${segmentInfo.userId eq user.userId}"> selected</c:if>><c:out value='${user.userNm}' /></option>
																	</c:forEach>
																</c:if>
															</select>
														</div>
													</div>
												</li>
											</c:if>
											<%-- 디자인 깨짐 방지 --%>
											<c:if test="${'N' eq NEO_ADMIN_YN}">
												<li>
													<label></label>
													<div class="list-item"></div>
												</li>
											</c:if>
											<li class="col-full">
												<label class="required">수신자그룹</label>
												<div class="list-item">
													<input type="text" id="segNm" name="segNm" value="<c:out value='${segmentInfo.segNm}'/>" placeholder="수신자그룹 명칭을 입력해주세요.">
												</div>
											</li>
											<li class="col-full">
												<label>설명</label>
												<div class="list-item">
													<textarea id="segDesc" name="segDesc" placeholder="수신자그룹 설명을 입력해주세요."><c:out value='${segmentInfo.segDesc}' /></textarea>
												</div>
											</li>
										</ul>

									</div>
								</div>
								<!-- //조건 -->

								<!-- 테이블 정보, 상세 정보// -->
<!-- 								<div class="tableinfobox"> -->
<!-- 									<div class="box"> -->
<!-- 										<div class="title-area"> -->
<!-- 											<h3 class="h3-title">테이블 정보</h3> -->
<!-- 										</div> -->

<!-- 										테이블목록// -->
<!-- 										<div class="grid-area" id="divMetaTableInfo"></div> -->
<!-- 										//테이블목록 -->
<!-- 									</div> -->
 

<!-- 									컬럼목록// -->
<!-- 									<div class="box" id="divMetaColumnInfo"></div> -->
<!-- 									//컬럼목록 -->
<!-- 								</div> -->
								<!-- //테이블 정보, 상세 정보 -->

								<!-- 쿼리 등록// -->
								<div class="graybox single-style">
									<div class="title-area">
										<h3 class="h3-title">쿼리 등록</h3>
										<!-- 버튼// -->
										<div class="btn-wrap">
											<button type="button" class="btn" onclick="goQueryTest('000');">QUERY TEST</button>
											<c:if test="${'002' ne segmentInfo.status}">
												<button type="button" id="btnDisable" class="btn<c:if test="${'001' eq segmentInfo.status}"> hide</c:if>" onclick="goDisable();">사용중지</button>
												<button type="button" id="btnEnable" class="btn<c:if test="${'000' eq segmentInfo.status}"> hide</c:if>" onclick="goEnable();">복구</button>
												<button type="button" class="btn" onclick="goDelete();">삭제</button>
											</c:if>
										</div>
										<!-- //버튼 -->
									</div>

									<div class="list-area">
										<ul>
											<li class="col-full">
												<label class="required">기본 QUERY
												</label>
												<div class="list-item">
													<textarea id="query" name="query"><c:out value="${segmentInfo.query}" /></textarea>
												</div>
											</li>
											<li class="col-full">
												<label onclick="goSegCnt();">수신그룹 미리보기
												</label>
												<div class="list-item">
													<p class="inline-txt color-gray" id="txtTotCnt">
														<c:out value='${segmentInfo.totCnt}' />
														명
													</p>
													<input type="hidden" id="totCnt" name="totCnt" value="<c:out value='${segmentInfo.totCnt}'/>">
													<button type="button" class="btn" onclick="goQueryTest('002')">미리보기</button>
												</div>
											</li>
											<li class="col-full">
												<label>재발송 QUERY
												</label>
												<div class="list-item">
													<textarea id="retryQuery" name="retryQuery"><c:out value="${segmentInfo.retryQuery}" /></textarea>
												</div>
											</li>
											<li class="col-full">
												<label>실시간 QUERY
												</label>
												<div class="list-item">
													<textarea id="realQuery" name="realQuery"><c:out value="${segmentInfo.realQuery}" /></textarea>
												</div>
											</li>
										</ul>
										<ul>
											<li>
												<label>등록자</label>
												<div class="list-item">
													<p class="inline-txt">
														<c:out value="${segmentInfo.regNm}" />
													</p>
												</div>
											</li>
											<li>
												<label>수정자</label>
												<div class="list-item">
													<p class="inline-txt">
														<c:out value="${segmentInfo.upNm}" />
													</p>
												</div>
											</li>
											<li>
												<label>등록일시</label>
												<div class="list-item">
													<fmt:parseDate var="regDt" value="${segmentInfo.regDt}" pattern="yyyyMMddHHmmss" />
													<fmt:formatDate var="regDt" value="${regDt}" pattern="yyyy.MM.dd HH:mm" />
													<p class="inline-txt">
														<c:out value="${regDt}" />
													</p>
												</div>
											</li>
											<li>
												<label>수정일시</label>
												<div class="list-item">
													<fmt:parseDate var="upDt" value="${segmentInfo.upDt}" pattern="yyyyMMddHHmmss" />
													<fmt:formatDate var="upDt" value="${upDt}" pattern="yyyy.MM.dd HH:mm" />
													<p class="inline-txt">
														<c:out value="${upDt}" />
													</p>
												</div>
											</li>
										</ul>
									</div>
									<!-- btn-wrap// -->
									<div class="btn-wrap btn-biggest">
										<button type="button" class="btn big fullblue" onclick="goQueryTest('003')">수정</button>
										<button type="button" class="btn big" onclick="goSegList();">취소</button>
									</div>
									<!-- //btn-wrap -->
								</div>
								<!-- //쿼리 등록 -->


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
	<%@ include file="/WEB-INF/jsp/inc/pop/pop_new_mail.jsp" %>
	<!-- //신규발송팝업 -->

	<!-- 미리보기 팝업// -->
	<%@ include file="/WEB-INF/jsp/inc/pop/pop_preview_seg.jsp" %>
	<!-- //미리보기 팝업 -->
	
	<!-- 조회사유팝업// -->
	<%@ include file="/WEB-INF/jsp/inc/pop/pop_search_reason.jsp" %>
	<!-- //조회사유팝업 -->	
	
</body>
</html>
