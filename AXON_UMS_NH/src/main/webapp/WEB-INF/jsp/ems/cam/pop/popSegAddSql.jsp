<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.10.14
	*	설명 : 메일작성 내부 수신자그룹 등록(SQL)
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>

<div class="cont single-style">
	<form id="popSegSearchForm" name="popSegSearchForm" method="post">
		<input type="hidden" name="page" value="${searchVO.page}">
		<input type="hidden" name="searchStartDt" value="${searchVO.searchStartDt}">
		<input type="hidden" name="searchEndDt" value="${searchVO.searchEndDt}">
		<input type="hidden" name="searchSegNm" value="${searchVO.searchSegNm}">
		<input type="hidden" name="searchCreateTy" value="${searchVO.searchCreateTy}">
	</form>
	<form id="popSegInfoFormMail" name="popSegInfoFormMail" method="post">
		<input type="hidden" name="deptNo" value="<c:out value='${NEO_DEPT_NO}'/>">
		<input type="hidden" name="userId" value="<c:out value='${NEO_USER_ID}'/>">
        <input type="hidden" id="mergeKey" name="mergeKey">
        <input type="hidden" id="mergeCol" name="mergeCol">
		<input type="hidden" name="testType">
        <input type="hidden" name="createTy" value="002">
        <input type="hidden" name="serviceGb" value="10">
		<fieldset>
			<legend>수신자그룹 선택</legend>
			<!-- <h3 class="pop-title">수신자그룹 신규등록</h3> -->

			<!-- 조건// -->
			<div class="graybox">
				<div class="title-area">
					<h3 class="h3-title">조건</h3>
					<span class="required">*필수입력 항목</span>
				</div>
				
				<div class="list-area">
					<ul>
						<li>
							<label class="required">Connection</label>
							<div class="list-item">
								<div class="select">
									<select name="dbConnNo" title="Connection 선택">
										<c:if test="${fn:length(dbConnList) > 0}">
											<c:forEach items="${dbConnList}" var="dbConn">
												<option value="<c:out value='${dbConn.dbConnNo}'/>"><c:out value='${dbConn.dbConnNm}'/></option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>
						</li>
						<li class="col-full">
							<label class="required">수신자그룹</label>
							<div class="list-item">
								<input type="text" name="segNm" placeholder="수신자그룹 명칭을 입력해주세요.">
							</div>
						</li>
						<li class="col-full">
							<label>설명</label>
							<div class="list-item">
								<textarea name="segDesc" placeholder="수신자그룹 설명을 입력해주세요."></textarea>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<!-- //조건 -->

			<!-- 쿼리 등록// -->
			<div class="graybox">
				<div class="title-area">
					<h3 class="h3-title">쿼리 등록</h3>
				</div>
				
				<div class="list-area">
					<ul>
						<li class="col-full">
							<label>QUERY</label>
							<div class="list-item">
								<textarea id="popQuery" name="query" placeholder="QUERY를 입력해주세요."></textarea>
								<div class="querybox">
									<button type="button" class="btn" onclick="goPopSegQueryTest('000');">QUERY TEST</button>
								</div>
							</div>
						</li>
						<li class="col-full">
							<label>수신그룹 인원</label>
							<div class="list-item">
								<input type="hidden" id="totCnt" name="totCnt" value="0"/>
								<p class="inline-txt color-gray line-height40" id="txtTotCnt">0명</p>
							</div>
						</li>
						
						<li class="col-full">
							<label>재발송 QUERY</label>
							<div class="list-item">
								<textarea id="popRetryQuery" name="retryQuery"></textarea>
							</div>
						</li>
						<li class="col-full">
							<label>실시간 QUERY</label>
							<div class="list-item">
								<textarea id="popRealQuery" name="realQuery"></textarea>
							</div>
						</li>
						
					</ul>
				</div>
			</div>
			<!-- //쿼리 등록 -->
			
			<!-- btn-wrap// -->
			<div class="btn-wrap btn-biggest">
				<button type="button" class="btn big fullblue" onclick="goPopSegQueryTest('001');">등록</button>
				<button type="button" class="btn big" onclick="goPopSegList();">목록</button>
			</div>
			<!-- //btn-wrap -->
		</fieldset>
	</form>
</div>

