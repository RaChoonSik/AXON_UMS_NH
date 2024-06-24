<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.08.18
	*	설명 : 사용자그룹 정보수정
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header_ems.jsp" %>

<script type="text/javascript" src="<c:url value='/js/ums.common.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/ems/inf/ifBasicUpdate.js'/>"></script>

<body>
	<div id="wrapper">
	<c:choose>
		<c:when test="${empty NEO_FRAME_YN}">
			<header class="util">
				<h1 class="logo">
					<a href="/ems/index.ums"><span class="txt-blind">LOGO</span></a>
				</h1>
				<!-- 공통 표시부// -->
				<c:import url="/WEB-INF/jsp/inc/top.jsp"></c:import>
				<!-- //공통 표시부 -->
			</header>
			<div id="wrap" class="ems">
				<!-- lnb// -->
				<div id="lnb">
					<!-- LEFT MENU -->
					<c:import url="/WEB-INF/jsp/inc/menu_ems.jsp"></c:import>
					<!-- LEFT MENU -->
				</div>
				<!-- //lnb -->
				<div class="content-wrap">
		</c:when>
		<c:otherwise>
			<div id="wrap" class="ems">
				<div class="content-wrap">
		</c:otherwise>
	</c:choose>
					<!-- content// -->
					<div id="content" class="single-style">

						<!-- cont-head// -->
						<section class="cont-head">
							<div class="title">
								<h2>연계정보 수정</h2>
							</div>
						</section>
						<!-- //cont-head -->
	
						<!-- cont-body// -->
						<section class="cont-body">
				
							<form:form method="post" action="" modelAttribute="ifFormVo">
								<input type="hidden" id="tableRowCount" value="<c:out value='${fn:length(ifMappList)}'/>" >
								<input type="hidden" id="popupYn" name="popupYn" value="<c:out value='${searchVO.popupYn}'/>" >
								<input type="hidden" id="rnsYn" name="rnsYn" value="${searchVO.rnsYn}">
								
								<input type="hidden" id="page" name="page" value="${searchVO.page}">
								<input type="hidden" name="searchIfCampId" value="<c:out value='${searchVO.searchIfCampId}'/>" >
								<input type="hidden" name="searchIfCampName" value="<c:out value='${searchVO.searchIfCampName}'/>" >
								<input type="hidden" name="searchStatus" value="<c:out value='${searchVO.searchStatus}'/>" >
								<input type="hidden" name="searchDeptNo" value="<c:out value='${searchVO.searchDeptNo}'/>" >
								
								<fieldset>
									<legend>내용</legend>
									
			
									<!-- 내용// -->
									<div class="graybox">
										<div class="title-area">
											<h3 class="h3-title">기본정보</h3>
											<span class="required">
												*필수입력 항목
											</span>
										</div>
										
										<div class="list-area">
											<ul>
												<li>
													<label>연계아이디</label>
													<div class="list-item">
														<p class="inline-txt"><c:out value="${ifBasic.ifCampId}"/></p>
														<input type="hidden" id="ifCampId" name="ifBasicVO.ifCampId" value="<c:out value='${ifBasic.ifCampId}'/>" >
													</div>
												</li>
												<li>
													<label class="required">연계명</label>
													<div class="list-item">
														<input type="text" id="ifCampName" name="ifBasicVO.ifCampName" placeholder="" value="<c:out value='${ifBasic.ifCampName}'/>" >
													</div>
												</li>
												<li>
													<label class="required">인터페이스<br>형태</label>
													<div class="list-item">
														<div class="select">	
															<select id="interfaceType" name="ifBasicVO.interfaceType" title="인터페이스 형태 선택">
																<c:if test="${fn:length(code201List) > 0}">
																	<option value="">선택</option>
																	<c:forEach items="${code201List}" var="code201">
																		<option value="<c:out value='${code201.cd}'/>"<c:if test="${code201.cd eq ifBasic.interfaceType}"> selected</c:if>><c:out value='${code201.cdNm}'/></option>
																	</c:forEach>
																</c:if>
															</select>
														</div>
													</div>
												</li>
												<li>
													<label class="required">발송형태</label>
													<div class="list-item">
														<div class="select">
															<select id="sendType" name="ifBasicVO.sendType" title="발송형태 선택">
																<c:if test="${fn:length(code202List) > 0}">
																 	<option value="">선택</option>
																	<c:forEach items="${code202List}" var="code202">
																		<option value="<c:out value='${code202.cd}'/>"<c:if test="${code202.cd eq ifBasic.sendType}"> selected</c:if>><c:out value='${code202.cdNm}'/></option>
																	</c:forEach>
																</c:if>
															</select>
														</div>
													</div>
												</li>
												
												<li>
													<label class="required">전송결과<br>리턴여부</label>
													<div class="list-item">
														<div class="select">
															<select id="responseYn" name="ifBasicVO.responseYn" title="옵션 선택">
																<option value="" >선택</option>
																<option value="Y"  <c:if test="${'Y' eq ifBasic.responseYn }"> selected</c:if> >사용</option>
																<option value="N"  <c:if test="${'N' eq ifBasic.responseYn }"> selected</c:if> >사용안함</option>
															</select>
														</div>
													</div>
												</li>
												
												<li>
													<label class="required">전송결과<br>EAI아이디</label>
													<div class="list-item">
														<input type="text" id="respEaiId" name="ifBasicVO.respEaiId" maxlength="13"  value="<c:out value='${ifBasic.respEaiId}'/>" >
													</div>
												</li>
												
												<li>
													<label class="required">사용자그룹</label>
													<div class="list-item">
														<div class="select">
															<%-- 관리자의 경우 전체 요청그룹을 전시하고 그 외의 경우에는 해당 그룹만 전시함 --%>
															<c:if test="${'Y' eq NEO_ADMIN_YN}">
																<select id="deptNo" name="ifBasicVO.deptNo" onchange="getUserList(this.value);" title="사용자그룹 선택">
																	<c:if test="${fn:length(deptList) > 0}">
																		<c:forEach items="${deptList}" var="dept">
																			<option value="<c:out value='${dept.deptNo}'/>" <c:if test="${dept.deptNo eq ifBasic.deptNo}"> selected</c:if> ><c:out value='${dept.deptNm}'/></option>
																		</c:forEach>
																	</c:if>
																</select>
															</c:if>
															<c:if test="${'N' eq NEO_ADMIN_YN}">
																<select id="deptNo" name="ifBasicVO.deptNo" onchange="getUserList(this.value);" title="사용자그룹 선택">
																	<c:if test="${fn:length(deptList) > 0}">
																		<c:forEach items="${deptList}" var="dept">
																			<c:if test="${dept.deptNo == ifBasic.deptNo}">
																				<option value="<c:out value='${dept.deptNo}'/>" selected><c:out value='${dept.deptNm}'/></option>
																			</c:if>
																		</c:forEach>
																	</c:if>
																</select>
															</c:if>
														</div>
													</div>
												</li>
												
												
												<li>
														<label class="required">사용자명</label>
														<div class="list-item">
															<div class="select">
															<c:if test="${'Y' eq NEO_ADMIN_YN}">
																<select id="userId" name="ifBasicVO.userId" title="사용자명 선택">
																	<option value="">선택</option>
																	<c:if test="${fn:length(userList) > 0}">
																		<c:forEach items="${userList}" var="user">
																			<option value="<c:out value='${user.userId}'/>" <c:if test="${user.userId eq ifBasic.userId}"> selected</c:if> ><c:out value='${user.userNm}'/></option>
																		</c:forEach>
																	</c:if>
																</select>
															</c:if>
															<c:if test="${'N' eq NEO_ADMIN_YN}">	
																<select id="userId" name="ifBasicVO.userId" title="사용자명 선택">
																	<option value="">선택</option>
																	<c:if test="${fn:length(userList) > 0}">
																		<c:forEach items="${userList}" var="user">
																			
																			<option value="<c:out value='${user.userId}'/>" <c:if test="${user.userId eq ifBasic.userId}"> selected</c:if> ><c:out value='${user.userNm}'/></option>
																			
																		</c:forEach>
																	</c:if>
																</select>
															</c:if>	
															</div>
														</div>
													</li>
													<li>
														<label class="required">연계IT담당자</label>
														<div class="list-item">
																<input type="text" id="itDevNm" name="ifBasicVO.itDevNm" maxlength="100" value="<c:out value='${ifBasic.itDevNm}'/>"  >
														</div>
													</li>
												
													<li>
														<label class="required">보안메일</label>
														<div class="list-item">
															<div class="select">
																<select id="secuMailYn" name="ifBasicVO.secuMailYn" title="옵션 선택">
																	<option value="" >선택</option>
																	<option value="Y" <c:if test="${'Y' eq ifBasic.secuMailYn }"> selected</c:if> >사용</option>
																	<option value="N" <c:if test="${'N' eq ifBasic.secuMailYn }"> selected</c:if> >사용안함</option>
																</select>
															</div>
														</div>
													</li>
													
													<li>
														<label class="required">사용상태</label>
														<div class="list-item">
															<div class="select">
																<select id="status" name="ifBasicVO.status" title="사용상태 선택">
																	<c:if test="${fn:length(code012List) > 0}">
																	 	<option value="">선택</option>
																		<c:forEach items="${code012List}" var="code012">
																			<option value="<c:out value='${code012.cd}'/>"<c:if test="${code012.cd eq ifBasic.status}"> selected</c:if>><c:out value='${code012.cdNm}'/></option>
																		</c:forEach>
																	</c:if>
																</select>
															</div>
														</div>
													</li>
													
											</ul>
										</div>
									</div>
									<!-- //내용 -->
									
									<!-- 목록// -->
									<div class="graybox" id="divIfMappList" >
										<div class="title-area">
											<h3 class="h3-title">매핑정보</h3>
											<span class="total"></span>
			
											<!-- 버튼// -->
											<div class="btn-wrap">
												
												<button type="button" class="btn fullblue" onclick="fn.popupOpen('#popup_file_mapp');">엑셀등록</button>
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
																		<input type="hidden" id="sortNum${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].sortNum" value=""   >
																	</td>
																	<td>
																		<input type="text" id="ifCol${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].ifCol" value="<c:out value="${ifMapp.ifCol}"/>"   <c:if test="${ifMappStatus.index < 7 }"> readOnly</c:if>     >
																	</td>
																	<td>
																		<input type="text" id="ifListCol${ifMappStatus.index}" name="ifMappVOList[${ifMappStatus.index}].ifListCol" value="<c:out value="${ifMapp.ifListCol}"/>"   <c:if test="${ifMappStatus.index < 7 }"> readOnly</c:if>     >
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
									</div>
									
									
									<!-- btn-wrap// -->
									<div class="btn-wrap">
										<button type="button" class="btn big fullblue" onclick="goUpdate();" >수정</button>
										<button type="button" class="btn big" onclick="goCancel();" >목록</button>
									</div>
									<!-- //btn-wrap -->
										
								</fieldset>
								
							</form:form>
							
						</section>
						<!-- //cont-body -->

					</div>
					<!-- // content -->
				</div>
			</div>
		</div>
	</div>
	
	<!-- 파일등록 팝업// -->
	<%@ include file="/WEB-INF/jsp/ems/inf/pop/pop_upload_mapp.jsp" %>
	<!-- //파일등록 팝업 -->		
	
	<iframe id="iFrmDown" name="iFrmDown" style="width:0px;height:0px;"></iframe>


</body>
</html>
