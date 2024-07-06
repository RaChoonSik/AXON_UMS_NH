/**********************************************************
	*	작성자 : 김준희
	*	작성일시 :2024.07.06
    *	설명 : 팩스 발송(대량) JavaScript
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
		url : "./umsFaxMasterList.ums?" + param,
		dataType : "html",
		success : function(pageHtml){
			$("#divFaxMasterList").html(pageHtml);
		},
		error : function(){
			alert("목록 조회에 오류가 발생하였습니다");
		}
	});
}

function goFaxMasterDetil(obj){

	var umsFaxMasterSeq = "";   	// 팩스발송순번 
	var fromId = ""; 		// 발신자사번
	var fromTeam = ""; 		// 발신자팀코드
	var fromCntr = ""; 		// 발신자센터코드
	var fromGrp = ""; 		// 발신자그룹
	var fromName = "";  	// 발송자이름
	var fromFax = ""; 		// 발송자FAX번호 
	var subject = ""; 		// 팩스제목
	var targetDate = ""; 	// 발송예약일시
	var sendTime = ""; 		// 발송완료시간
	var targetCnt = ""; 		// 타게팅시간
	var successCnt = ""; 		// 성공수
	var failCnt = ""; 		// 실패수
	var waitCnt = ""; 		// 대기수
	var attachFile1 = ""; 	// 1번첨부파일 
	var attachFile2 = ""; 	// 2번첨부파일 
	var attachFile3 = ""; 	// 3번첨부파일  
		
	var selTr = $(obj.parentNode.parentNode); 
	var sell = $(selTr.children("td"));	 
	
	for(var j= 0; j < sell.length; j ++){
		var idx = $(sell[j]).index();
		var val = $(sell[j]).text();
		if( idx == 1 ){ //팩스발송순번  
			umsFaxMasterSeq = val;
		}else if( idx == 2 ){ //발신자사번
			fromId  = val;
		}else if( idx == 3 ){ //발송자이름
			fromName = val;
		}else if( idx == 4 ){ //팩스제목
			subject  = val;
		}else if( idx == 5 ){ //발송예약일시
			targetDate = val;
		} else if( idx == 6 ){ //발송완료시간
			sendTime = val;
		} else if( idx == 7 ){ //대상건수
			targetCnt = val;
		} else if( idx == 8 ){ //성공건수
			successCnt = val;
		} else if( idx == 9 ){ //실패건수
			failCnt = val;
		} else if( idx == 10 ){ //대기건수
			waitCnt = val;
		}else if( idx == 11){ //발신자센터코드ID
			fromCntr  = val;
		}else if( idx == 12){ //발신자그룹
			fromGrp  = val;
		}else if( idx == 13 ){ //발신자팀코드
			fromTeam  = val;
		}else if( idx == 14 ){ //발송자FAX번호
			fromFax  = val;
		}else if( idx == 15 ){ //1번첨부파일
			attachFile1 = val;
		} else if( idx == 16 ){ //2번첨부파일
			attachFile2 = val;
		} else if( idx == 17 ){ //3번첨부파일
			attachFile3 = val; 
		}
	}
	
	$("#popUmsFaxMasterSeq").val(umsFaxMasterSeq);
	$("#popFromId").text(fromId);
	$("#popFromTeam").text(fromTeam);
	$("#popFromCntr").text(fromCntr);
	$("#popFromGrp").text(fromGrp);
	$("#popFromName").text(fromName);
	$("#popFromFax").text(fromFax);
	$("#popTargetDate").text(targetDate);
	$("#popSendTime").text(sendTime);
	$("#popSubject").text(subject);
	$("#popTargetCnt").text(targetCnt);
	$("#popSuccessCnt").text(successCnt);
	$("#popFailCnt").text(failCnt);
	$("#popWaitCnt").text(waitCnt);	
	$("#popFaxAttach1").text(attachFile1);
	$("#popFaxAttach2").text(attachFile2);
	$("#popFaxAttach3").text(attachFile3);
	 
	
	if(attachFile1 == "" ) {
		$("#popFaxAttach1tr").css("display", "none");
	} else {
		$("#popFaxAttach1tr").css("display", "");
	}

	if(attachFile2 == "" ) {
		$("#popFaxAttach2tr").css("display", "none");
	} else {
		$("#popFaxAttach2tr").css("display", "");
	}
	
	if(attachFile3 == "" ) {
		$("#popFaxAttach3tr").css("display", "none");
	} else {
		$("#popFaxAttach3tr").css("display", "");
	}
	
	fn.popupOpen('#popup_fax_master_detail');
	
}

function goFaxAttachFileDown(attachNo){
	
	var requestKey = $("#popUmsFaxMasterSeq").val();
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
 
 	$("#faxAttachDownForm").attr("target","iFrmMail").attr("action","../../com/faxMasterAttachDown.ums").submit();
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
		$("#searchForm").attr("target","iFrmExcel").attr("action","./umsFaxMasterExcelList.ums").submit();
	} else return;
}
 