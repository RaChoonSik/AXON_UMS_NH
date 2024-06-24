<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2024.05.15
	*	설명 : 클린메일 목록 
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
	</div>

	<div class="grid-area">
		<table class="grid">
			<caption>그리드 정보</caption>
			<colgroup>
				<col style="width:6%;">
				<col style="width:15%;">
				<col style="width:15%;">
				<col style="width:15%;">
				<col style="width:auto;">
				<col style="width:10%;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">작업년월</th>
					<th scope="col">고객ID</th>
					<th scope="col">고객명</th>
					<th scope="col">고객이메일</th>
					<th scope="col">오류건수</th>
				</tr>
			</thead>
			<tbody>
				<!-- 데이터가 있을 경우// -->
				<c:if test="${fn:length(ifCleanList) > 0}">
					<c:forEach items="${ifCleanList}" var="ifClean" varStatus="ifCleanStatus">
						<tr>
							<td><c:out value="${pageUtil.totalRow - (pageUtil.currPage-1)*pageUtil.pageRow - ifCleanStatus.index}"/></td>
							<td ><c:out value="${ifClean.cleanYm}"/></td>
							<td>
							<a href="javascript:goIfCleanListPop('${ifClean.cleanYm}','${ifClean.ifId}');" class="bold"><c:out value="${ifClean.ifId}"/></a>
							</td>
							<td><c:out value="${ifClean.ifName}"/></td>
							<td><c:out value="${ifClean.ifEmail}"/></td>
							<td><c:out value="${ifClean.errCnt}"/></td>
						</tr>
					</c:forEach>
				</c:if>
				<!-- 데이터가 없을 경우// -->
				<c:if test="${empty ifCleanList}">
					<tr>
						<td colspan="6" class="no_data">등록된 내용이 없습니다.</td>
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
