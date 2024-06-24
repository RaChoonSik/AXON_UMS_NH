<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.09.07
	*	설명 : 대량메일 상세 로그목록 조회
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<%@ taglib prefix="crypto" uri="/WEB-INF/tlds/crypto.tld"%>

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
		<%-- <h3 class="h3-title">목록</h3>
		<span class="total">Total: <em><c:out value="${pageUtil.totalRow}"/></em></span> --%>

		<!-- btn-wrap// -->
		<div class="btn-wrap">
			<!-- <button type="button" class="btn big fullblue" onclick="excelDownload();">엑셀다운로드</button> -->
			<%-- <!-- 페이지 정렬// -->
			<div class="select">
				<select  onchange="goChagePerPageRows(this.value);" title="페이지당 결과">
					<c:if test="${fn:length(perPageList) > 0}">
						<c:forEach items="${perPageList}" var="perPage">
							<option value="<c:out value='${perPage.cdNm}'/>" <c:if test="${perPage.cdNm eq searchVO.rows}"> selected</c:if>><c:out value='${perPage.cdNm}'/>개씩 보기</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
			<!-- //페이지 정렬 --> --%>
		</div>
		<!-- //btn-wrap -->
	</div>

	<div class="grid-area">
		<table class="grid">
			<caption>그리드 정보</caption>
			<colgroup>
				<col style="width:5%;"><!-- 2021.12.30 col 추가 및 width 수정 -->
				<col style="width:6%;">
				<col style="width:6%;">
				<col style="width:6%;">				
				<col style="width:6%;">
				<col style="width:6%;">
				<col style="width:6%;">
				<col style="width:auto;">
				<col style="width:6%;">
				<col style="width:6%;">
				<col style="width:6%;">
				<col style="width:0%;">
				<col style="width:0%;">
				<col style="width:0%;">
				<col style="width:0%;">
				<col style="width:0%;">
				<col style="width:0%;">
				<col style="width:0%;">
				<col style="width:0%;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">팩스발송순번</th>
					<th scope="col">고객번호</th>
					<th scope="col">수신자이름</th>
					<th scope="col">수신자FAX번호</th>
					<th scope="col">발신자사번</th>
					<th scope="col">발송자이름</th>
					<th scope="col">팩스제목</th>
					<th scope="col">발송예약일시</th>
					<th scope="col">발송완료시간</th>
					<th scope="col">발송결과코드</th>
					<th scope="col" class="hide">발신자센터코드</th>
					<th scope="col" class="hide">발신자그룹</th>
					<th scope="col" class="hide">발신자팀코드</th>
					<th scope="col" class="hide">발송자FAX번호</th>
					<th scope="col" class="hide">팩스유형코드</th>
					<th scope="col" class="hide">첨부파일1파일</th>
					<th scope="col" class="hide">첨부파일2파일</th>
					<th scope="col" class="hide">첨부파일3파일</th>

				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(faxSendList) > 0}">
					<!-- 데이터가 있을 경우// -->
					<c:forEach items="${faxSendList}" var="faxSend" varStatus="faxSendStatus">
						<tr>
							<td><c:out value="${pageUtil.totalRow - (pageUtil.currPage-1)*pageUtil.pageRow - faxSendStatus.index}" /></td>  
							<td><a href="javascript:;" onclick="goFaxDetil(this);" class="bold"><c:out value="${faxSend.umsFaxSeq}" /></a></td>
							<td><c:out value="${faxSend.toId}" /></a></td>
							<td><c:out value="${faxSend.toName}" /></td>
							<td><c:out value="${faxSend.toFax}" /></td>
							<td><c:out value="${faxSend.fromId}" /></td>
							<td><c:out value="${faxSend.fromName}" /></td>
							<td><c:out value="${faxSend.subject}" /></td> 
							<td><fmt:parseDate var="targetDt" value="${faxSend.targetDate}" pattern="yyyyMMddHHmmss" />
								 <fmt:formatDate var="faxTargetDt" value="${targetDt}" pattern="yyyy.MM.dd HH:mm:ss" /> <c:out value='${faxTargetDt}' />
							</td> 
							<%-- <td><c:out value="${faxSend.targetDate}" /></td> --%>
							<td><c:out value="${faxSend.sendTime}" /></td>
							<td><c:out value="${faxSend.errorCode}" /></td>
							<td class="hide"><c:out value="${faxSend.fromCntr}" /></td>
							<td class="hide"><c:out value="${faxSend.fromGrp}" /></td>
							<td class="hide"><c:out value="${faxSend.fromTeam}" /></td>
							<td class="hide"><c:out value="${faxSend.fromFax}" /></td>
							<td class="hide"><c:out value="${faxSend.faxCode}" /></td>
							<td class="hide"><c:out value="${faxSend.attach01Name}" /></td>
							<td class="hide"><c:out value="${faxSend.attach02Name}" /></td>
							<td class="hide"><c:out value="${faxSend.attach03Name}" /></td>
						</tr>
					</c:forEach>
					<!-- //데이터가 있을 경우 -->
				</c:if>

				<c:if test="${empty faxSendList}">
					<!-- 데이터가 없을 경우// -->
					<tr>
						<td colspan="19" class="no_data">등록된 내용이 없습니다.</td>
					</tr>
					<!-- //데이터가 없을 경우 -->
				</c:if>
			</tbody>
		</table>
	</div>
	<!-- 페이징// -->
	<div class="paging">${pageUtil.pageHtml}</div>
	<!-- //페이징 -->
</div>
<!-- //목록 -->


