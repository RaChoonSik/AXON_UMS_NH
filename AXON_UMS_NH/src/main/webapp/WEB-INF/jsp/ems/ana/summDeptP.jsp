<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.09.08
	*	설명 : 통계분석 기간별누적분석 누적통계 사용자그룹별 탭화면
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>

<!-- 목록// -->
<div class="graybox">
	<div class="title-area">
		<!-- <h3 class="h3-title">목록</h3> -->
		<div class="title-area-left">
			<span class="total">Total: <em><c:out value="${fn:length(deptList)}" /></em></span>
		</div>
		<!-- btn-wrap// -->
		<div class="btn-wrap">
			<!-- <button type="button" class="btn big fullblue" onclick="goExcelDown('Dept');">엑셀 다운로드</button> -->
		</div>
		<!-- //btn-wrap -->
	</div>

	<div class="grid-area pb20">
		<table class="grid">
			<caption>그리드 정보</caption>
			<colgroup>
				<col style="width: 10%;">
				<col style="width: 9%;">
				<col style="width: 9%;">
				<col style="width: 9%;">
				<col style="width: 9%;">
				<col style="width: 10%;">
				<col style="width: 9%;">
				<col style="width: 9%;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">사용자그룹</th>
					<th scope="col">발송수</th>
					<th scope="col">성공수</th>
					<th scope="col">실패수</th>
					<th scope="col">오픈수</th>
					<th scope="col">유효오픈수</th>
					<th scope="col">클릭수</th>
					<th scope="col">수신거부</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="sumSendCnt" value="${0}" />
				<c:set var="sumSuccCnt" value="${0}" />
				<c:set var="sumFailCnt" value="${0}" />
				<c:set var="sumOpenCnt" value="${0}" />
				<c:set var="sumValidCnt" value="${0}" />
				<c:set var="sumClickCnt" value="${0}" />
				<c:set var="sumBlockCnt" value="${0}" />
				<c:if test="${fn:length(deptList) > 0}">
					<!-- 데이터가 있을 경우// -->
					<c:forEach items="${deptList}" var="dept">
						<c:set var="sumSendCnt" value="${sumSendCnt + dept.sendCnt}" />
						<c:set var="sumSuccCnt" value="${sumSuccCnt + dept.succCnt}" />
						<c:set var="sumFailCnt" value="${sumFailCnt + (dept.sendCnt - dept.succCnt)}" />
						<c:set var="sumOpenCnt" value="${sumOpenCnt + dept.openCnt}" />
						<c:set var="sumValidCnt" value="${sumValidCnt + dept.validCnt}" />
						<c:set var="sumClickCnt" value="${sumClickCnt + dept.clickCnt}" />
						<c:set var="sumBlockCnt" value="${sumBlockCnt + dept.blockCnt}" />
						<tr>
							<td>
								<c:out value="${dept.deptNm}" />
							</td>
							<td>
								<fmt:formatNumber var="sendCntNum" type="number" value="${dept.sendCnt}" />
								<c:out value="${sendCntNum}" />
							</td>
							<td>
								<fmt:formatNumber var="succCntNum" type="number" value="${dept.succCnt}" />
								<c:out value="${succCntNum}" />
							</td>
							<td>
								<fmt:formatNumber var="failCntNum" type="number" value="${dept.sendCnt - dept.succCnt}" />
								<c:out value="${failCntNum}" />
							</td>
							<td>
								<fmt:formatNumber var="openCntNum" type="number" value="${dept.openCnt}" />
								<c:out value="${openCntNum}" />
							</td>
							<td>
								<fmt:formatNumber var="validCntNum" type="number" value="${dept.validCnt}" />
								<c:out value="${validCntNum}" />
							</td>
							<td>
								<fmt:formatNumber var="clickCntNum" type="number" value="${dept.clickCnt}" />
								<c:out value="${clickCntNum}" />
							</td>
							<td>
								<fmt:formatNumber var="blockCntNum" type="number" value="${dept.blockCnt}" />
								<c:out value="${blockCntNum}" />
							</td>
						</tr>
					</c:forEach>
					<!-- //데이터가 있을 경우 -->
				</c:if>

				<c:if test="${empty deptList}">
					<!-- 데이터가 없을 경우// -->
					<tr>
						<td colspan="8" class="no_data">등록된 내용이 없습니다.</td>
					</tr>
					<!-- //데이터가 없을 경우 -->
				</c:if>
			</tbody>
			<tfoot>
				<tr>
					<td>합계</td>
					<td>
						<fmt:formatNumber var="sumSendCntNum" type="number" value="${sumSendCnt}" />
						<c:out value="${sumSendCntNum}" />
					</td>
					<td>
						<fmt:formatNumber var="sumSuccCntNum" type="number" value="${sumSuccCnt}" />
						<c:out value="${sumSuccCntNum}" />
					</td>
					<td>
						<fmt:formatNumber var="sumFailCntNum" type="number" value="${sumFailCnt}" />
						<c:out value="${sumFailCntNum}" />
					</td>
					<td>
						<fmt:formatNumber var="sumOpenCntNum" type="number" value="${sumOpenCnt}" />
						<c:out value="${sumOpenCntNum}" />
					</td>
					<td>
						<fmt:formatNumber var="sumValidCntNum" type="number" value="${sumValidCnt}" />
						<c:out value="${sumValidCntNum}" />
					</td>
					<td>
						<fmt:formatNumber var="sumClickCntNum" type="number" value="${sumClickCnt}" />
						<c:out value="${sumClickCntNum}" />
					</td>
					<td>
						<fmt:formatNumber var="sumBlockCntNum" type="number" value="${sumBlockCnt}" />
						<c:out value="${sumBlockCntNum}" />
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
<!-- //목록 -->

<!-- btn-wrap// -->
<!-- <div class="btn-wrap">
	<button type="button" class="btn big fullblue" onclick="goExcelDown('Dept');">엑셀 다운로드</button>
</div> -->
<!-- //btn-wrap -->