<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2024.06.14
	*	설명 : 팩스발송 상세 정보  
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="popup_fax_detail" class="poplayer poptestsendinfo">
	<div class="inner normal">
		<header>
			<h2>팩스 상세 정보</h2>
		</header>
		<div class="popcont">
			<div class="cont">
				<form id="faxAttachDownForm" name="faxAttachDownForm" method="post">
					<input type="hidden" id="requestKey" name="requestKey">
					<input type="hidden" id="seqNo" name="seqNo"/>
					<input type="hidden" id="downType" name="downType"/> 
				</form>
				<form id="faxDetailForm" name="faxDetailForm" method="post">
					<input type="hidden" id="popUmsFaxSeq" value="0">
					<fieldset>
						<legend>상세 정보</legend>
						<div class="graybox" style="margin-top:0;">
							<div class="title-area">
								<h3 class="h3-title">FAX 상세 정보</h3>
							</div>
							
							<div class="table-area">
								<table>
									<caption>FAX 상세 정보</caption>
									<colgroup>
										<col style="width:105px">
										<col style="width:225px">
										<col style="width:105px">
										<col style="width:225px">
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">고객번호</th>
											<td colspan="3" id="popToId"></td>
										</tr>
										<tr>
											<th scope="row">수신자이름</th>
											<td id="popToName"></td>
											<th scope="row">수신자FAX번호</th>
											<td id="popToFax"></td>
										</tr>
										<tr>
											<th scope="row">발신자센터코드</th>
											<td id="popFromCntr"></td>
											<th scope="row">발신자그룹</th>
											<td id="popFromGrp"></td> 
										</tr>
										<tr>
											<th scope="row">발송자이름</th>
											<td id="popFromName"></td>
											<th scope="row">발송자FAX번호</th>
											<td id="popFromFax"></td> 
										</tr>
										<tr>
											<th scope="row">팩스유형코드</th>
											<td id="popFaxCode"></td>
											<th scope="row">발송결과코드</th>
											<td id="popErrorCode"></td> 
										</tr>
										<tr>
											<th scope="row">팩스제목</th>
											<td colspan="3" id="popSubject"></td>
										</tr>
								        <tr id="popFaxAttach1"> 
								            <th scope="row">첨부파일 1번</th>
								            <td colspan="3"><span><a id="popFaxAttach1FileName" href="javascript:goFaxAttachFileDown('1')" style="color:#3a99ff;"/></a></span></td> 
								        </tr>
								        <tr id="popFaxAttach2">
								            <th scope="row">첨부파일 2번</th>
								            <td colspan="3"><span><a id="popFaxAttach2FileName" href="javascript:goFaxAttachFileDown('2')" style="color:#3a99ff;"/></a></span></td>  
								        </tr>
								        <tr id="popFaxAttach3">
								            <th scope="row">첨부파일 3번</th> 
								            <td colspan="3"><span><a id="popFaxAttach3FileName" href="javascript:goFaxAttachFileDown('3')" style="color:#3a99ff;"/></a></span></td>  
								        </tr>
									</tbody>
								</table>
							</div>
						</div>
						
					
						<!-- btn-wrap// -->
						<div class="btn-wrap">
							<button type="button" class="btn big fullblue" onclick="fn.popupClose('#popup_fax_detail');">닫기</button>
						</div>
						<!-- //btn-wrap -->
						
					</fieldset>
				</form>
				</div>
			</div>
			<button type="button" class="btn_popclose" onclick="fn.popupClose('#popup_fax_detail');"><span class="hidden">팝업닫기</span></button>
		</div>
		<span class="poplayer-background" onclick="fn.popupClose('#popup_fax_detail');"></span>
	</div>
	<!-- //팝업 -->
</body>
</html>