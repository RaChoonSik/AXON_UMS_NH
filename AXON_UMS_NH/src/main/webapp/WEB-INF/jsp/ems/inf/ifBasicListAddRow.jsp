<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2024.05.15
	*	설명 : 연계기본관리 목록 
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>

						<tr id="mappTr<c:out value='${ifMappVO.rowCount}'/>" style="display: table;width: 100%;table-layout: fixed" >
								<td align="center">
									<label for="checkbox_<c:out value='${ifMappVO.rowCount}'/>"><input type="checkbox" id="checkbox_<c:out value='${ifMappVO.rowCount}'/>" name="chkMappTbl" value="<c:out value='${ifMappVO.rowCount}'/>"><span></span></label>
								</td>
								<td>
									<input type="text" id="ifCol${ifMappVO.rowCount}" name="ifMappVOList[${ifMappVO.rowCount}].ifCol" value=""       >
									<input type="hidden" id="sortNum${ifMappVO.rowCount}" name="ifMappVOList[${ifMappVO.rowCount}].sortNum" value=""   >
								</td>
							    <td>
									<input type="text" id="ifListCol${ifMappVO.rowCount}" name="ifMappVOList[${ifMappVO.rowCount}].ifListCol" value=""     >
								</td>
								<td><input type="text" id="ifColName${ifMappVO.rowCount}" name="ifMappVOList[${ifMappVO.rowCount}].ifColName" value=""   ></td>
								<td>
									<div class="list-item">
										<div class="select">
											<select id="metaCol${ifMappVO.rowCount}" name="ifMappVOList[${ifMappVO.rowCount}].metaCol"  >
												<c:if test="${fn:length(ifMailColNmList) > 0}">
												 	<option value="">선택</option>
													<c:forEach items="${ifMailColNmList}" var="ifMail">
														<option value="<c:out value='${ifMail.ifMailCol}'/>" ><c:out value='${ifMail.ifMailColNm}'/></option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>	
							    </td>
								<td>
									<div class="list-item">
										<div class="select">
											<select id="ifColType${ifMappVO.rowCount}" name="ifMappVOList[${ifMappVO.rowCount}].ifColType"  >
												<c:if test="${fn:length(code203List) > 0}">
												 	<option value="" selected >선택</option>
													<c:forEach items="${code203List}" var="code203">
														<option value="<c:out value='${code203.cd}'/>"><c:out value='${code203.cdNm}'/></option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>	
								</td>
								<td>
									<div class="list-item">
										<div class="select">
											<select id="encType${ifMappVO.rowCount}" name="ifMappVOList[${ifMappVO.rowCount}].encType"  >
												<c:if test="${fn:length(code204List) > 0}">
												 	<option value="">선택</option>
													<c:forEach items="${code204List}" var="code204">
														<option value="<c:out value='${code204.cd}'/>" ><c:out value='${code204.cdNm}'/></option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>	
								</td>
								<td>
									<div class="list-item">
										<div class="select">
											<select id="returnYn${ifMappVO.rowCount}" name="ifMappVOList[${ifMappVO.rowCount}].returnYn"   >
												 	<option value="">선택</option>
												 	<option value="Y" <c:if test="${'Y' eq ifMappVO.returnYn}"> selected</c:if> >리턴</option>
												 	<option value="N" <c:if test="${'N' eq ifMappVO.returnYn}"> selected</c:if> >미리턴</option>
											</select>
										</div>
									</div>	
								</td>
								
							</tr>