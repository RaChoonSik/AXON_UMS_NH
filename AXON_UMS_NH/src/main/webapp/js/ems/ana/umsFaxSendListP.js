/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2024.04.30
    *	설명 : 상세로그 JavaScript
**********************************************************/

$(document).ready(function() {
 
	// 화면 로딩시 검색 실행
	goSearch("1");
});

function goChagePerPageRows(rows){
	$("#searchForm input[name='rows']").val(rows);
	goSearch("1");
}

// 검색 버튼 클릭
function goSearch(pageNo) {
	if($("#searchStartDt").val() > $("#searchEndDt").val()) {
		alert("검색 시 시작일은 종료일보다 클 수 없습니다.");
		return;
	}
	
	var endDt = $("#searchEndDt").val().split('.');
	var startDt = $("#searchStartDt").val().split('.');
	var endDate = new Date(endDt[0], endDt[1], endDt[2]);
	var startDate = new Date(startDt[0], startDt[1], startDt[2]);
	
	
	var diff = endDate - startDate;
	var currDay = 24 * 60 * 60 * 1000;// 시 * 분 * 초 * 밀리세컨
	if ( parseInt(diff/currDay) > 61 ){
		alert("검색 기간은 60일을 넘길 수 없습니다");
		return;
	}
	 
	$("#searchForm input[name='page']").val(pageNo);
	var param = $("#searchForm").serialize();
	$.ajax({
		type : "GET",
		url : "./umsFaxSendList.ums?" + param,
		dataType : "html",
		success : function(pageHtml){
			$("#divFaxSendList").html(pageHtml);
		},
		error : function(){
			alert("목록 조회에 오류가 발생하였습니다");
		}
	});
}

function goFaxDetil(obj){

	var umsFaxSeq = "";   	// 팩스발송순번
	var toId = "";   		// 고객번호
	var toName = ""; 		// 수신자이름
	var toFax = "";  		// 수신자FAX번호
	var fromId = ""; 		// 발신자사번
	var fromCntr = ""; 		// 발신자센터코드
	var fromGrp = ""; 		// 발신자그룹
	var fromTeam = ""; 		// 발신자팀코드
	var fromName = "";  	// 발송자이름
	var fromFax = ""; 		// 발송자FAX번호
	var faxCode = ""; 		// 팩스유형코드
	var subject = ""; 		// 팩스제목
	var targetDate = ""; 	// 발송예약일시
	var sendTime = ""; 		// 발송완료시간
	var errorCode = ""; 	// 발송결과코드
	var attachFile1Name = ""; 	// 1번첨부파일 
	var attachFile2Name = ""; 	// 2번첨부파일 
	var attachFile3Name = ""; 	// 3번첨부파일  
		
	var selTr = $(obj.parentNode.parentNode); 
	var sell = $(selTr.children("td"));	 
	
	for(var j= 0; j < sell.length; j ++){
		var idx = $(sell[j]).index();
		var val = $(sell[j]).text();
		if( idx == 1 ){ //팩스발송순번  
			umsFaxSeq = val;
		}else if( idx == 2 ){ //고객번호 
			toId = val;
		}else if( idx == 3 ){ //수신자이름
			toName  = val;
		}else if( idx == 4 ){ //수신자FAX번호
			toFax  = val;
		}else if( idx == 5 ){ //발신자사번
			fromId  = val;
		}else if( idx == 6 ){ //발송자이름
			fromName = val;
		}else if( idx == 7 ){ //팩스제목
			subject  = val;
		}else if( idx == 8 ){ //발송예약일시
			targetDate = val;
		} else if( idx == 9 ){ //발송완료시간
			sendTime = val;
		} else if( idx == 10 ){ //발송결과코드
			errorCode = val;
		}else if( idx == 11){ //발신자센터코드ID
			fromCntr  = val;
		}else if( idx == 12){ //발신자그룹
			fromGrp  = val;
		}else if( idx == 13 ){ //발신자팀코드
			fromTeam  = val;
		}else if( idx == 14 ){ //발송자FAX번호
			fromFax  = val;
		}else if( idx == 15 ){ //팩스유형코드
			faxCode  = val;
		}else if( idx == 16 ){ //1번첨부파일
			attachFile1Name = val;
		} else if( idx == 17 ){ //2번첨부파일
			attachFile2Name = val;
		} else if( idx == 18 ){ //3번첨부파일
			attachFile3Name = val; 
		}
	}
	
	$("#popUmsFaxSeq").val(umsFaxSeq);
	$("#popToId").text(toId);
	$("#popToName").text(toName);
	$("#popToFax").text(toFax);
	$("#popFromId").text(fromId);
	$("#popFromCntr").text(fromCntr);
	$("#popFromGrp").text(fromGrp);
	$("#popFromTeam").text(fromTeam);
	$("#popFromName").text(fromName);
	$("#popFromFax").text(fromFax);
	$("#popFaxCode").text(faxCode);
	$("#popSubject").text(subject);
	$("#popTargetDate").text(targetDate);
	$("#popSendTime").text(sendTime);
	$("#popErrorCode").text(errorCode);
	
	$("#popFaxAttach1FileName").text(attachFile1Name);
	$("#popFaxAttach2FileName").text(attachFile2Name);
	$("#popFaxAttach3FileName").text(attachFile3Name);
	
	
	
	if(attachFile1Name == "" ) {
		$("#popFaxAttach1").css("display", "none");
	} else {
		$("#popFaxAttach1").css("display", "");
	}

	if(attachFile2Name == "" ) {
		$("#popFaxAttach2").css("display", "none");
	} else {
		$("#popFaxAttach2").css("display", "");
	}
	
	if(attachFile3Name == "" ) {
		$("#popFaxAttach3").css("display", "none");
	} else {
		$("#popFaxAttach3").css("display", "");
	}
	
	fn.popupOpen('#popup_fax_detail');
	
}

function goFaxAttachFileDown(attachNo){
	
	var requestKey = $("#popUmsFaxSeq").val();
	if (attachNo < 1 ){
		alert("다운로드 하려는 첨부파일 순번을 확인해주세요");
		return ;	
	}
	
	if (requestKey < 1 ){
		alert("다운로드 하려는 팩스발송고유번호를 확인해주세요");
		return;	
	} 
	
	$("#faxAttachDownForm input[name='downType']").val("010");
	$("#faxAttachDownForm input[name='requestKey']").val(requestKey);
	$("#faxAttachDownForm input[name='seqNo']").val(attachNo);
 
 	$("#faxAttachDownForm").attr("target","iFrmMail").attr("action","../../com/faxAttachDown.ums").submit();
/* 	
	$("#faxAttachDownForm").attr("target","");
    $("#faxAttachDownForm").attr("action", "<c:url value='/com/faxAttachDown.ums'/>");
    $("#faxAttachDownForm").submit();*/
}



// 페이징
function goPageNum(pageNum) {
	goSearch(pageNum);
}

// 엑셀다운로드 클릭시
function excelDownload() {
	if($("#searchStartDt").val() > $("#searchEndDt").val()) {
		alert("검색 시 시작일은 종료일보다 클 수 없습니다.");
		return;
	}
	 
	
	var endDt = $("#searchEndDt").val().split('.');
	var startDt = $("#searchStartDt").val().split('.');
	var endDate = new Date(endDt[0], endDt[1], endDt[2]);
	var startDate = new Date(startDt[0], startDt[1], startDt[2]);
	
	var diff = endDate - startDate;
	var currDay = 24 * 60 * 60 * 1000;// 시 * 분 * 초 * 밀리세컨
	if ( parseInt(diff/currDay) > 365 ){
		alert("엑셀 다운로드 기간은 1년을 넘길수 없습니다");
		return;
	}
	
	var a = confirm("엑셀 다운로드는 시간이 오래 걸립니다 진행하시겠습니까?");
	if ( a ) {
		$("#searchForm").attr("target","iFrmExcel").attr("action","./umsFaxSendExcelList.ums").submit();
	} else return;
}
 