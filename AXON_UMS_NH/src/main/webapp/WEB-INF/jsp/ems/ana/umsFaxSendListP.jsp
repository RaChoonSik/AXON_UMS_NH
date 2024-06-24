<%--
	/**********************************************************
    *   작성자 : 김준희
    *   작성일시 : 2024.04.30
    *	설명 : UMS FAX 발송  목록 화면
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header_ems.jsp"%>


<script type="text/javascript" src="<c:url value='/js/ems/ana/umsFaxSendListP.js'/>"></script>

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
							<h2>팩스 발송 목록</h2>
						</div>

					</section>
					<!-- //cont-head -->

					<!-- cont-body// -->
					<section class="cont-body">

						<form id="searchForm" name="searchForm" method="post">
							<input type="hidden" id="page" name="page" value="<c:out value='${searchVO.page}'/>">
							<input type="hidden" id="rows" name="rows" value="<c:out value='${searchVO.rows}'/>">
							<input type="hidden" id="searchUmsFaxSeq" name="searchUmsFaxSeq" value="0">

							<fieldset>
								<legend>조회 및 목록</legend>

								<!-- 조회// -->
								<div class="graybox">
									<!-- 
									<div class="title-area">
										<h3 class="h3-title">조회</h3>
									</div>
 -->
									<div class="list-area">
										<ul>
											<li>
												<label>발송예약일시</label>
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
												<label>고객번호</label>
												<div class="list-item">
													<input type="text" id="searchToId" name="searchToId" value="<c:out value='${searchVO.searchToId}'/>" placeholder="고객번호를 입력해주세요.">
												</div>
											</li>
                                            <li>
                                                <label>수신자이름</label>
                                                <div class="list-item">
                                                    <input type="text" id="searchToName" name="searchToName" value="<c:out value='${searchVO.searchToName}'/>" placeholder="수신자이름을 입력해주세요.">
                                                </div>
                                            </li>
                                            <li>
                                                <label>수신자FAX번호</label>
                                                <div class="list-item">
                                                    <input type="text" id="searchToFax" name="searchToFax" value="<c:out value='${searchVO.searchToFax}'/>" placeholder="수신자FAX번호를 입력해주세요.">
                                                </div>
                                            </li>
                                            <li>
                                                <label>팩스유형코드</label>
                                                <div class="list-item">
                                                    <input type="text" id="searchFaxCode" name="searchFaxCode" value="<c:out value='${searchVO.searchFaxCode}'/>" placeholder="팩스유형코드 입력해주세요.">
                                                </div>
                                            </li>
                                            <li>
                                                <label>전송시스템구분코드</label>
                                                <div class="list-item">
                                                    <input type="text" id="searchSysDcd" name="searchSysDcd" value="<c:out value='${searchVO.searchSysDcd}'/>" placeholder="전송시스템구분코드를 입력해주세요.">
                                                </div>
                                            </li>
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
								<div id="divFaxSendList"></div>
								<!-- //목록&페이징 -->

							</fieldset>
						</form>
						<div style="width: 0px; height: 0px; display: none;">
							<iframe id="iFrmExcel" name="iFrmExcel" style="width: 0px; height: 0px;"></iframe>
						</div>

					</section>
					<!-- //cont-body -->

				</div>
				<!-- // content -->
			</div>
		</div>
	</div>

	<iframe id="iFrmMail" name="iFrmMail" style="width:0px;height:0px;"></iframe>
	
	<!--  팩스 정보 팝업// -->
	<%@ include file="/WEB-INF/jsp/inc/pop/pop_fax_detail.jsp"%>
	<!-- //자동발송 메일 정보 -->
	
	
</body>
</html>
