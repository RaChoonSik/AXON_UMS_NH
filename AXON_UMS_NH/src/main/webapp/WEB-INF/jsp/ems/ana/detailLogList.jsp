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
				<col style="width:5%;">
				<col style="width:10%;">
				<col style="width:auto;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:10%;">
				<col style="width:6%;">
				<col style="width:6%;">
				<col style="width:6%;">
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
					<th scope="col">메일유형</th>
					<th scope="col">발송일시</th>
					<th scope="col">메일명</th>
					<th scope="col">캠페인명</th>
					<th scope="col">고객ID</th>
					<th scope="col">고객명</th>
					<th scope="col">고객 이메일</th>
					<th scope="col">발송자명</th>
					<th scope="col">발송자 이메일</th>
					<th scope="col">발송결과</th>
					<th scope="col">발송구분</th>
					<th scope="col">재발송</th>
					<th scope="col" class="hide">Bizkey</th>
					<th scope="col" class="hide">보안메일타입</th>
					<th scope="col" class="hide">발송결과코드</th>
					<th scope="col" class="hide">수신확인</th>
					<th scope="col" class="hide">재발송수신그룹</th> 
					<th scope="col" class="hide">RNS컨텐츠타입</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(sendLogList) > 0}">
					<!-- 데이터가 있을 경우// -->
					<c:forEach items="${sendLogList}" var="sendLog" varStatus="logStatus">
						<tr>
							<td><c:out value="${pageUtil.totalRow - (pageUtil.currPage-1)*pageUtil.pageRow - logStatus.index}" /></td>
							<td><c:out value="${sendLog.sendRepeatNm}" /></td>
							<td>
								<fmt:parseDate var="sendDate" value="${sendLog.sendDt}" pattern="yyyyMMddHHmm" /> 
								<fmt:formatDate var="sendDt" value="${sendDate}" pattern="yyyy-MM-dd HH:mm" /> 
								<c:out value='${sendDt}' /></td>
							<td><a href="javascript:;" onclick="goMailDetil('<c:out value="${sendLog.taskNo}"/>', '<c:out value="${sendLog.subTaskNo}"/>', '<c:out value="${sendLog.attCnt}"/>', '<c:out value="${sendLog.serviceGb}"/>', '<c:out value="${sendLog.webAgent}"/>', '<c:out value="${sendLog.contFlPath}"/>', this);" class="bold"><c:out value="${sendLog.mailTitle}" /></a></td>
							<td><c:out value="${sendLog.campNm}" /></td>
							<td><crypto:decrypt colNm="CUST_ID" data="${sendLog.custId}" /></td>
							<td><crypto:decrypt colNm="CUST_NM" data="${sendLog.custNm}" /></td>
							<td><crypto:decrypt colNm="CUST_EM" data="${sendLog.custEm}" /></td>
							<%-- <td><c:out value="${sendLog.mailFromNm}" /></td> --%>
							<td><crypto:decrypt colNm="MAIL_FROM_NM" data="${sendLog.mailFromNm}" /></td>
							<td><crypto:decrypt colNm="MAIL_FROM_EM" data="${sendLog.mailFromEm}" /></td>
							
							<td><c:out value="${sendLog.sendRcodeNm}" /></td>
							<td>
								<c:choose>
									<c:when test="${0 eq sendLog.rtyNo}">
										본발송
									</c:when>
									<c:otherwise>
										재발송
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${'10' eq sendLog.serviceGb}">
										<c:if test="${'Y' eq reSendEmsAuth}">
											<button type="button" class="btn" value="0" onclick="goMailDetil('<c:out value="${sendLog.taskNo}"/>', '<c:out value="${sendLog.subTaskNo}"/>', '<c:out value="${sendLog.attCnt}"/>', '<c:out value="${sendLog.serviceGb}"/>', '<c:out value="${sendLog.webAgent}"/>', '<c:out value="${sendLog.contFlPath}"/>', this);">재발송</button>
										</c:if>

									</c:when>
									<c:otherwise>
										<c:if test="${'Y' eq reSendRnsAuth}">
											<button type="button" class="btn" value="0" onclick="goMailDetil('<c:out value="${sendLog.taskNo}"/>', '<c:out value="${sendLog.subTaskNo}"/>', '<c:out value="${sendLog.attCnt}"/>', '<c:out value="${sendLog.serviceGb}"/>', '<c:out value="${sendLog.webAgent}"/>', '<c:out value="${sendLog.contFlPath}"/>', this);">재발송</button>
										</c:if>
									</c:otherwise>
								</c:choose>
							</td>
							<!-- 
							<td> 
								<c:if test="${fn:length(sendLog.openDt) == 12}">
									<fmt:parseDate var="openDate" value="${sendLog.openDt}" pattern="yyyyMMddHHmm"/>
									<fmt:formatDate var="openDt" value="${openDate}" pattern="yyyy-MM-dd HH:mm"/>
									<c:out value='${openDt}'/>
								</c:if>
								<c:if test="${fn:length(sendLog.openDt) == 14}">
									<fmt:parseDate var="openDate" value="${sendLog.openDt}" pattern="yyyyMMddHHmmss"/>
									<fmt:formatDate var="openDt" value="${openDate}" pattern="yyyy-MM-dd HH:mm"/>
									<c:out value='${openDt}'/>
								</c:if>
							</td>
							 -->
							<td class="hide"><c:out value='${sendLog.bizkey}' /></td>
							<td class="hide"><c:out value='${sendLog.webAgentTyp}' /></td>
							<td class="hide"><c:out value='${sendLog.sendRcode}' /></td>
							<td class="hide"><c:out value='${sendLog.respAmt}' /></td>
							<td class="hide"><c:out value='${sendLog.segReal}' /></td>
							<td class="hide"><c:out value='${sendLog.ctnpos}' /></td>
							 
						</tr>
					</c:forEach>
					<!-- //데이터가 있을 경우 -->
				</c:if>

				<c:if test="${empty sendLogList}">
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


