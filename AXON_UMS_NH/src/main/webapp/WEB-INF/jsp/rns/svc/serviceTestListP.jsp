<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.09.03
	*	설명 : 자동발송메일 테스트메일 발송목록 화면
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header_ems.jsp"%>

<script type="text/javascript" src="<c:url value='/js/rns/svc/serviceTestListP.js'/>"></script>

<body>
	<div id="wrapper" class="ems">
		<header>
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
							<h2>자동메일 테스트 발송목록</h2>
						</div>

					</section>
					<!-- //cont-head -->

					<!-- cont-body// -->
					<section class="cont-body">

						<form id="searchForm" name="searchForm" method="post">
							<input type="hidden" id="page" name="page" value="<c:out value='${searchVO.page}'/>"> <input type="hidden" id="rows" name="rows" value="<c:out value='${searchVO.rows}'/>">
							<fieldset>
								<legend>조회 및 목록</legend>

								<!-- 조회// -->
								<div class="graybox">
									<!-- <div class="title-area">
										<h3 class="h3-title">조회</h3>
									</div> -->

									<div class="list-area">
										<ul>
											<li><label>발송일시</label>
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
												</div></li>
											<li><label>캠페인명</label>
												<div class="list-item">
													<input type="text" id="searchTnm" name="searchTnm" placeholder="캠페인명을 입력해주세요.">
												</div></li>
										</ul>
									</div>
									<!-- btn-wrap// -->
									<div class="btn-wrap">
										<button type="button" class="btn big fullpurple" onclick="goSearch('1');">검색</button>
										<button type="button" class="btn big" onclick="goInit();">초기화</button>
									</div>
									<!-- //btn-wrap -->
								</div>
								<!-- //조회 -->


								<!-- 목록&페이징// -->
								<div id="divServiceList"></div>
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

	<!-- 테스트결과목록팝업// -->
	<%@ include file="/WEB-INF/jsp/inc/pop/pop_sendtest_result_rns.jsp"%>
	<!-- //테스트결과목록팝업 -->

</body>
</html>
