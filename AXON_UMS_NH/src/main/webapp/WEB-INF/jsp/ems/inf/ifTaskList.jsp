<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2024.05.15
	*	설명 : 연계작업관리 목록 
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
				<col style="width:5%;">
				<!-- <col style="width:5%;"> -->
				<col style="width:10%;">
				<col style="width:10%;">
				<col style="width:13%;">
				<col style="width:12%;">
				<col style="width:10%;">
				<col style="width:10%;">
				<col style="width:12%;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<!-- 
					<th scope="col">
						<label for="ifBasicAllChk"><input type="checkbox" id="ifBasicAllChk" name="ifBasicAllChk" onclick='selectAll(this)'><span></span></label>
					</th>
					 -->
					<th scope="col">작업시간</th>
					<th scope="col">작업종류</th>
					<th scope="col">생성일자</th>
					<th scope="col">연계명</th>
					<th scope="col">파일명</th>
					<th scope="col">처리상태</th>
					<th scope="col">사용상태</th>
					<th scope="col">에러메시지</th>
				</tr>
			</thead>
			<tbody>
				<!-- 데이터가 있을 경우// -->
				<c:if test="${fn:length(ifTaskList) > 0}">
					<c:forEach items="${ifTaskList}" var="ifTask" varStatus="ifTaskStatus">
						<tr>
							<td><c:out value="${pageUtil.totalRow - (pageUtil.currPage-1)*pageUtil.pageRow - ifTaskStatus.index}"/></td>
							<!-- 
							<td align="center">
								<label for="checkbox_<c:out value='${ifTaskStatus.count}'/>"><input type="checkbox" id="checkbox_<c:out value='${ifTaskStatus.count}'/>" name="delIfCampId" value="<c:out value=''/>"><span></span></label>
							</td>
							 -->
							<td><c:out value="${ifTask.workTime}"/></td>
							<td><c:out value="${ifTask.workKindNm}"/></td>
							
							<td><c:out value="${ifTask.ifMakeDate}"/></td>
							<td><c:out value="${ifTask.ifCampName}"/></td>
							<td><c:out value="${ifTask.fileName}"/></td>
							<td><c:out value="${ifTask.workStatusNm}"/></td>
							<td><c:out value="${ifTask.useStatusNm}"/></td>
							<td><c:out value="${ifTask.errMsg}"/></td>
						</tr>
					</c:forEach>
				</c:if>
				<!-- 데이터가 없을 경우// -->
				<c:if test="${empty ifTaskList}">
					<tr>
						<td colspan="9" class="no_data">등록된 내용이 없습니다.</td>
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
