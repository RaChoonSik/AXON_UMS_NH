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
								<h3 class="h3-title">목록</h3>
								<span class="total">Total: <em><c:out value="${pageUtil.totalRow}"/></em></span>

								<!-- 버튼// -->
								<div class="btn-wrap">
									
								</div>
								<!-- //버튼 -->
							</div>

							<div class="grid-area">
								<table class="grid">
									<caption>그리드 정보</caption>
									<colgroup>
										<col style="width:6%;">
										<col style="width:8%;">
										<col style="width:10%;">
										<col style="width:15%;">
										<col style="width:15%;">
										<col style="width:10%;">
										<col style="width:auto;">
										<col style="width:10%;">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">NO</th>
											<th scope="col">발송일자</th>
											<th scope="col">캠페인번호</th>
											<th scope="col">캠페인명</th>
											<th scope="col">수신번호</th>
											<th scope="col">고객명</th>
											<th scope="col">고객이메일</th>
											<th scope="col">에러명</th>
										</tr>
									</thead>
									<tbody>
										<!-- 데이터가 있을 경우// -->
										<c:if test="${fn:length(ifCleanList) > 0}">
											<c:forEach items="${ifCleanList}" var="ifClean" varStatus="ifCleanStatus">
												<tr>
													<td><c:out value="${pageUtil.totalRow - (pageUtil.currPage-1)*pageUtil.pageRow - ifCleanStatus.index}"/></td>
													<td><c:out value="${ifClean.ifMakeDate}"/></td>
													<td><c:out value="${ifClean.ifCampId}"/></td>
													<td><c:out value="${ifClean.ifCampName}"/></td>
													<td><c:out value="${ifClean.recevSeqno}"/></td>
													<td><c:out value="${ifClean.ifName}"/></td>
													<td><c:out value="${ifClean.ifEmail}"/></td>
													<td><c:out value="${ifClean.sendResNm}"/></td>
												</tr>
											</c:forEach>
										</c:if>
										<!-- 데이터가 없을 경우// -->
										<c:if test="${empty ifCleanList}">
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
