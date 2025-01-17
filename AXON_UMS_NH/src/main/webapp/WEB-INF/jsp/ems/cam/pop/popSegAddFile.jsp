<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.10.14
	*	설명 : 메일작성 내부 수신자그룹 등록(파일)
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
        <input type="hidden" name="segFlPath">
        <input type="hidden" id="mergeKey" name="mergeKey">
        <input type="hidden" id="mergeCol" name="mergeCol">
        <input type="hidden" name="createTy" value="003">
        <input type="hidden" name="serviceGb" value="10">
		<fieldset>
			<legend>수신자그룹 선택</legend>
			<h3 class="pop-title">수신자그룹 신규등록</h3>

			<!-- 조건// -->
			<div class="graybox">
				<div class="title-area">
					<h3 class="h3-title">조건</h3>

					<!-- 버튼// -->
					<div class="btn-wrap">
						<span class="required">*필수입력 항목</span>
					</div>
					<!-- //버튼 -->
				</div>
				
				<div class="list-area">
					<ul>
						<li>
							<label class="required">파일등록</label>
							<div class="list-item">
								<input type="text" name="tempFlPath" placeholder="파일 등록" style="width:calc(100% - 13.5rem);" readonly>
								<button type="button" class="btn fullblue" onclick="fn.popupOpen('#popup_file_seg');">등록</button>
								<button type="button" class="btn" onclick="downloadSample();">샘플</button>
							</div>
						</li>
						<li>
							<label class="required">구분자</label>
							<div class="list-item">
								<input type="text" name="separatorChar" placeholder="구분자를 입력해주세요." style="width:calc(100% - 6.7rem);">
								<button type="button" class="btn fullblue" onclick="fncSep('<c:out value='${NEO_USER_ID}'/>');">등록</button>
							</div>
						</li>
						<li class="col-full">
							<label class="required">수신자 그룹</label>
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
						<li class="col-full">
							<label>수신그룹 인원</label>
							<div class="list-item">
								<input type="hidden" id="totCnt" name="totCnt" value="0"/>
								<p class="inline-txt color-gray line-height40" id="txtTotCnt">0명</p>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<!-- //조건 -->
			
			<!-- btn-wrap// -->
			<div class="btn-wrap btn-biggest">
				<button type="button" class="btn big fullblue" onclick="goPopSegInfoAddFile();">등록</button>
				<button type="button" class="btn big" onclick="goPopSegList();">목록</button>
			</div>
			<!-- //btn-wrap -->
		</fieldset>
	</form>
</div>
