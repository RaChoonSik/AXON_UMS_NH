<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2024.05.15
	*	설명 : 연계기본관리 목록 
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>

<!-- 목록// -->
<div class="graybox">
	<div class="title-area">
		<div class="title-area-left">
			<span class="total">Total: <em><c:out value="${pageUtil.totalRow}" /></em></span>
			<div class="btn-wrap select03">
				<!-- 페이지 정렬// -->
				<div class="select">
					<select onchange="goChagePerPageRows(this.value);" title="페이지당 결과">
						<c:if test="${fn:length(perPageList) > 0}">
							<c:forEach items="${perPageList}" var="perPage">
								<option value="<c:out value='${perPage.cdNm}'/>" <c:if test="${perPage.cdNm eq searchVO.rows}"> selected</c:if>><c:out value='${perPage.cdNm}' />개씩 보기
								</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				<!-- //페이지 정렬 -->
			</div>
		</div>
		<!-- <h3 class="h3-title">목록</h3> -->

		<!-- 버튼// -->
		<div class="btn-wrap">
			<c:if test="${'Y' eq NEO_ADMIN_YN}">
				<button type="button" class="btn fullblue plus" onclick="fn.popupOpen('#popup_file_jsp');">JSP업로드</button>
			</c:if>
			<button type="button" class="btn fullblue plus" onclick="goAdd();">신규등록</button>
			<button type="button" class="btn" onclick="goDelete();">삭제</button>
		</div>
		<!-- //버튼 -->
	</div>

	<div class="grid-area">
		<table class="grid">
			<caption>그리드 정보</caption>
			<colgroup>
				<col style="width:5%;">
				<col style="width:5%;">
				<col style="width:10%;">
				<col style="width:13%;">
				<col style="width:12%;">
				<col style="width:10%;">
				<col style="width:12%;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col"> 
						<label for="ifBasicAllChk"><input type="checkbox" id="ifBasicAllChk" name="ifBasicAllChk" onclick='selectAll(this)'><span></span></label>
					</th>
					<th scope="col">연계아이디</th>
					<th scope="col">연계명</th>
					<th scope="col">인터페이스형태</th>
					<th scope="col">전송형태</th> 
					<th scope="col">등록일</th> 
				</tr>
			</thead>
			<tbody>
				<!-- 데이터가 있을 경우// -->
				<c:if test="${fn:length(ifBasicList) > 0}">
					<c:forEach items="${ifBasicList}" var="ifBasic" varStatus="ifBasicStatus">
						<tr>
							<td><c:out value="${pageUtil.totalRow - (pageUtil.currPage-1)*pageUtil.pageRow - ifBasicStatus.index}"/></td>
							<td align="center">
								<label for="checkbox_<c:out value='${ifBasicStatus.count}'/>"><input type="checkbox" id="checkbox_<c:out value='${ifBasicStatus.count}'/>" name="delIfCampId" value="<c:out value='${ifBasic.ifCampId}'/>"><span></span></label>
								
								<input type="hidden" id="tbIfCampId<c:out value='${ifBasicStatus.index}'/>"  value="<c:out value='${ifBasic.ifCampId}'/>">
								<input type="hidden" id="tbIfCampNm<c:out value='${ifBasicStatus.index}'/>"  value="<c:out value='${ifBasic.ifCampName}'/>">
								
							
								<input type="hidden" id="tbCampNo<c:out value='${ifBasicStatus.index}'/>"  value="<c:out value='${ifBasic.campNo}'/>">
								<input type="hidden" id="tbCampNm<c:out value='${ifBasicStatus.index}'/>"  value="<c:out value='${ifBasic.campNm}'/>">
								<input type="hidden" id="tbCampTy<c:out value='${ifBasicStatus.index}'/>"  value="<c:out value='${ifBasic.campTy}'/>">
								<input type="hidden" id="tbMergeKey<c:out value='${ifBasicStatus.index}'/>"  value="<c:out value='${ifBasic.mergeKey}'/>">
								
								<input type="hidden" id="tbSegNo<c:out value='${ifBasicStatus.index}'/>"  value="<c:out value='${ifBasic.segNo}'/>">
								<input type="hidden" id="tbSegNm<c:out value='${ifBasicStatus.index}'/>"  value="<c:out value='${ifBasic.segNm}'/>">
								<input type="hidden" id="tbSecuMailYn<c:out value='${ifBasicStatus.index}'/>"  value="<c:out value='${ifBasic.secuMailYn}'/>">
								
							</td>
							<td> 
								<a href="javascript:goUpdate('<c:out value='${ifBasic.ifCampId}'/>')" class="bold"><c:out value='${ifBasic.ifCampId}'/></a>
							</td>
							<td><c:out value="${ifBasic.ifCampName}"/></td>
							<td><c:out value="${ifBasic.interfaceTypeNm}"/></td>
							<td><c:out value="${ifBasic.sendTypeNm}"/></td> 
							<td><c:out value="${ifBasic.regDate}"/></td> 
						</tr>
					</c:forEach>
				</c:if>
				<!-- 데이터가 없을 경우// -->
				<c:if test="${empty ifBasicList}">
					<tr>
						<td colspan="7" class="no_data">등록된 내용이 없습니다.</td>
					</tr>
				</c:if>					
				<!-- //데이터가 없을 경우 -->
			</tbody>
		</table>
	</div>
</div>
<!-- //목록 -->

<!-- 페이징// -->
<div class="paging">
	${pageUtil.pageHtml}
</div>
<!-- //페이징 -->
