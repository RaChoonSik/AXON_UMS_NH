<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.08.18
	*	설명 : 사용자그룹 정보수정
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header_ems.jsp" %>

						

							<div class="title-area">
								<h3 class="h3-title">매핑정보</h3>
								<span class="total"></span>

								<!-- 버튼// -->
								<div class="btn-wrap">
									<button type="button" class="btn fullblue" onclick="fn.popupOpen('#popup_file_mapp');">등록</button>
									<button type="button" class="btn" onclick="downloadSample();">샘플</button>
								
									<button type="button" class="btn fullblue plus" onclick="goAddRow();" >추가</button>
									<button type="button" class="btn" onclick="goDeleteRow();" >삭제</button>
								</div>
								<!-- //버튼 -->
							</div>
						
							<div class="grid-area" style="display: table;width:100%;table-layout: fixed" >
									<table class="grid" id="mappingTable" style="display: block;width:100%;"  >
										<caption>그리드 정보</caption>
										<colgroup>
											<col style="width:10%;">
											<col style="width:12%;">
											<col style="width:12%;">
											<col style="width:12%;">
											<col style="width:12%;">
											<col style="width:12%;">
											<col style="width:12%;">
										</colgroup>
										<thead style="display: block;overflow: auto;">
											<tr  style="display: table;width:100%;table-layout: fixed">
												<th scope="col"></th>
												<th scope="col">연계항목 <span style="color:red">*</span></th>
												<th scope="col">연계리스트상세</th>
												<th scope="col">연계항목명 <span style="color:red">*</span></th>
												<th scope="col">통합항목</th>
												<th scope="col">항목유형 <span style="color:red">*</span></th>
												<th scope="col">암호유형</th>
												<th scope="col">리턴여부</th>
											</tr>
										</thead>
										<tbody style="display: block;height: 300px; overflow: auto;">
											<!-- 데이터가 있을 경우// -->
											<c:if test="${fn:length(ifMappList) > 0}">
												<c:forEach items="${ifMappList}" var="ifMapp" varStatus="ifMappStatus">
													<tr id="mappTr${ifMappStatus.index}" style="display: table;width: 100%;table-layout: fixed">
														<td align="center">
															<label for="checkbox_<c:out value='${ifMappStatus.index}'/>"><input type="checkbox" id="checkbox_<c:out value='${ifMappStatus.index}'/>" name="chkMappTbl" value="<c:out value='${ifMappStatus.index}'/>"><span></span></label>
														</td>
														<td>
															<input type="text" id="ifCol${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].ifCol" value="<c:out value="${ifMapp.ifCol}"/>"   <c:if test="${ifMappStatus.index < 7 }"> readOnly</c:if>  >
															<input type="hidden" id="sortNum${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].sortNum" value=""/>"    >
														</td>
														<td>
															<input type="text" id="ifListCol${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].ifListCol" value="<c:out value="${ifMapp.ifListCol}"/>"   <c:if test="${ifMappStatus.index < 7 }"> readOnly</c:if>  >
														</td>
														<td><input type="text" id="ifColName${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].ifColName" value="<c:out value="${ifMapp.ifColName}"/>" <c:if test="${ifMappStatus.index < 7 }"> readOnly</c:if>  ></td>
														<td>
															<div class="list-item">	
																<div class="select">
																	<select id="metaCol${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].metaCol" <c:if test="${ifMappStatus.index < 7 }"> disabled="disabled" </c:if>  >
																		<c:if test="${fn:length(ifMailColNmList) > 0}">
																		 	<option value="">선택</option>
																			<c:forEach items="${ifMailColNmList}" var="ifMail">
																				<option value="<c:out value='${ifMail.ifMailCol}'/>"<c:if test="${ifMail.ifMailCol eq ifMapp.metaCol}"> selected</c:if>><c:out value='${ifMail.ifMailColNm}'/></option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>
															</div>	
													    </td>
														<td>
															<div class="list-item">
																<div class="select">
																	<select id="ifColType${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].ifColType"  <c:if test="${ifMappStatus.index < 7 }"> disabled="disabled" </c:if> >
																		<c:if test="${fn:length(code203List) > 0}">
																		 	<option value="">선택</option>
																			<c:forEach items="${code203List}" var="code203">
																				<option value="<c:out value='${code203.cd}'/>"<c:if test="${code203.cd eq ifMapp.ifColType}"> selected</c:if>><c:out value='${code203.cdNm}'/></option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>
															</div>
														</td>
														<td>
															<div class="list-item">
																<div class="select">
																	<select id="encType${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].encType" <c:if test="${ifMappStatus.index < 7 }"> disabled="disabled" </c:if>  >
																		<c:if test="${fn:length(code204List) > 0}">
																		 	<option value="">선택</option>
																			<c:forEach items="${code204List}" var="code204">
																				<option value="<c:out value='${code204.cd}'/>"<c:if test="${code204.cd eq ifMapp.encType}"> selected</c:if>><c:out value='${code204.cdNm}'/></option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>
															</div>	
														</td>
														
														<td>
															<div class="list-item">
																<div class="select">
																	<select id="returnYn${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].returnYn" <c:if test="${ifMappStatus.index < 7 }"> disabled="disabled" </c:if>  >
																		 	<option value="">선택</option>
																		 	<option value="Y" <c:if test="${'Y' eq ifMapp.returnYn}"> selected</c:if> >리턴</option>
																		 	<option value="N" <c:if test="${'N' eq ifMapp.returnYn}"> selected</c:if> >미리턴</option>
																	</select>
																</div>
															</div>	
														</td>
													</tr>
												</c:forEach>
											</c:if>
											<!-- 데이터가 없을 경우// -->
											<c:if test="${empty ifMappList}">
												<tr>
													<td colspan="7" class="no_data">등록된 내용이 없습니다.</td>
												</tr>
											</c:if>					
											<!-- //데이터가 없을 경우 -->
										</tbody>
									</table>
								</div>
