<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.07.13
	*	설명 : 탬틀릿 목록 조회
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>

<div class="graybox">
	<div class="title-area">
		<div class="title-area-left">
			<!-- <h3 class="h3-title">목록</h3> -->
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
		<!-- 버튼// -->
		<div class="btn-wrap">
			<button type="button" class="btn fullblue plus" onclick="goAdd();">신규등록</button>
<%-- 			<!-- 페이지 정렬// -->
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
			<!-- //페이지 정렬 --> --%>
		</div>
		<!-- //총 건 -->
	</div>

	<div class="grid-area">
		<table class="grid">
			<caption>템플릿 목록</caption>
			<colgroup>
				<col style="width: 5%;">
				<col style="width: 5%;">
				<col style="width: auto;">
				<col style="width: 10%;">
				<col style="width: 10%;">
				<col style="width: 5%;">
				<col style="width: 15%;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">템플릿번호</th>
					<th scope="col">템플릿명</th>
					<th scope="col">사용자그룹</th>
					<th scope="col">사용자명</th>
					<th scope="col">상태</th>
					<th scope="col">등록일시</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(templateList) > 0}">
					<!-- 데이터가 있을 경우// -->
					<c:forEach items="${templateList}" var="template" varStatus="tempStatus">
						<tr>
							<td>
								<c:out value="${pageUtil.totalRow - (pageUtil.currPage-1)*pageUtil.pageRow - tempStatus.index}" />
							</td>
							<td>
								<c:out value="${template.tempNo}" />
							</td>
							<td>
								<a href="javascript:goUpdate('<c:out value='${template.tempNo}'/>');" class="bold"><c:out value='${template.tempNm}' /></a>
							</td>
							<td>
								<c:out value="${template.deptNm}" />
							</td>
							<td>
								<c:out value="${template.userNm}" />
							</td>
							<td>
								<c:out value="${template.statusNm}" />
							</td>
							<td>
								<fmt:parseDate var="regDate" value="${template.regDt}" pattern="yyyyMMddHHmmss" />
								<fmt:formatDate var="regDt" value="${regDate}" pattern="yyyy.MM.dd HH:mm" />
								<c:out value="${regDt}" />
							</td>
						</tr>
					</c:forEach>
					<!-- //데이터가 있을 경우 -->
				</c:if>

				<c:if test="${empty templateList}">
					<!-- 데이터가 없을 경우// -->
					<tr>
						<td colspan="6" class="no_data">등록된 내용이 없습니다.</td>
					</tr>
					<!-- //데이터가 없을 경우 -->
				</c:if>
			</tbody>
		</table>
	</div>
	<!-- 페이징// -->
	<div class="paging">${pageUtil.pageHtml}</div>
</div>
<!-- //목록 -->

