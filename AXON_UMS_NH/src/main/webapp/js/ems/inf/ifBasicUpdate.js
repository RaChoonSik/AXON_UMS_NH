// 수정 클릭시
function goUpdate() {	
	//if($("#ifCampId").val() != "") {
		// 입력 폼 검사
		if(checkForm()) {
			return;
		}
		
		var query = 'input[name="chkMappTbl"]';
	  	var checkboxs = document.querySelectorAll(query);
		
	  	var  idx ; 
        var  idx2;
	    var  value; 
        var  value2;
	
		var  i;
		var  j;
	
		for (i = 0; i < checkboxs.length; i++) {
			idx  = checkboxs[i].value;
			value = $("#ifCol"+ idx ).val();
			if(  value == '' ){
				$("#ifCol"+ idx ).focus();
				alert("연계항목은 필수입니다.");
				return;
			} 
			value = $("#ifColName"+ idx ).val();
			if(  value == '' ){
				$("#ifColName"+ idx ).focus();
				alert("연계항목명은 필수입니다.");
				return;
			} 
			value = $("#ifColType"+ idx ).val();
			if(  value == '' ){
				$("#ifColType"+ idx ).focus();
				alert("항목유형은 필수입니다.");
				return;
			}
			
			value = $("#metaCol"+ idx ).val();
			for (j =(i+1); j < checkboxs.length; j++) {
				idx2 = checkboxs[j].value;
				value2 = $("#metaCol"+ idx2 ).val();
				if(value != '' && value == value2 ){
					alert(value +"통합항목은 중복일수 없습니다.");
					return;
				}
			}
			
			$("#sortNum"+ idx ).val( i );
			console.log("================>>" + $("#sortNum"+ idx ).val() );
			
		}
		
		for(var i=0;i<7;i++){
			$("#metaCol"+ i).removeAttr("disabled","disabled");
			$("#ifColType"+ i).removeAttr("disabled","disabled");
			$("#encType"+ i).removeAttr("disabled","disabled");
			$("#returnYn"+ i).removeAttr("disabled","disabled");
		}
		
		var frm = $("#ifFormVo")[0];
		var frmData = new FormData(frm);
		$.ajax({
			type: "POST",
			url: './ifBasicUpdate.json',
			data: frmData,
			processData: false,
			contentType: false,
			success: function (res) {
				if(res.result == "Success") {
					alert("수정되었습니다.");
					if( $("#popupYn").val() == 'Y'){
						parent.goIfBasciListPopResize();
						$("#ifFormVo").attr("target","").attr("action","./ifBasicListP.ums").submit();
					}else{
						$("#ifFormVo").attr("target","").attr("action","./ifBasicListP.ums").submit();
					}
				} else {
					alert(res.resultMessage  );
				}
			},
			error: function (e) {
				alert("수정 처리중 오류가 발생하였습니다." + e);
			}
		});
	
} 


// 입력 폼 검사
function checkForm() {
	var errstr = "";
	var errflag = false;
	if($("#ifCampId").val() == "") {
		errstr += "[연계아이디]"; 
		errflag = true;
	}
	if($("#ifCampName").val() == "") {
		errstr += "[연계명]"; 
		errflag = true;
	}
	if($("#interfaceType").val() == "") {
		errstr += "[인터페이스종류]"; 
		errflag = true;
	}
	if($("#sendType").val() == "") {
		errstr += "[발송종류]"; 
		errflag = true;
	}
	if($("#responseYn").val() == "") {
		errstr += "[전송결과리턴여부]"; 
		errflag = true;
	}
	if($("#deptNo").val() == "") {
		errstr += "[사용자그룹]"; 
		errflag = true;
	}
	if($("#deptNo").val() == "") {
		errstr += "[사용자그룹]"; 
		errflag = true;
	}
	if($("#userId").val() == "") {
		errstr += "[사용자명]"; 
		errflag = true;
	}
	if($("#itDevNm").val() == "") {
		errstr += "[연계IT담당자]"; 
		errflag = true;
	}
	if($("#status").val() == "") {
		errstr += "[사용상태]"; 
		errflag = true;
	}
	if($("#secuMailYn").val() == "") {
		errstr += "[보안메일]"; 
		errflag = true;
	}
	
	
	if(errflag) {
		alert("다음 정보를 확인하세요.\n" + errstr);
	}
	
	if(getByteStr($("#ifCampId").val()) > 10 ) {
		alert("연계아이디는 10byte를 넘을 수 없습니다.");
		$("#ifCampId").focus();
		$("#ifCampId").select();
		errflag = true;
	}
	if(getByteStr($("#ifCampName").val()) > 100 ) {
		alert("연계명은 100byte를 넘을 수 없습니다.");
		$("#ifCampName").focus();
		$("#ifCampName").select();
		errflag = true;
	}
	if(getByteStr($("#respEaiId").val()) > 20 ) {
		alert("전송결과EAI아이디 20byte를 넘을 수 없습니다.");
		$("#ifCampName").focus();
		$("#ifCampName").select();
		errflag = true;
	}
	if(getByteStr($("#respEaiId").val()) > 20 ) {
		alert("전송결과EAI아이디 20byte를 넘을 수 없습니다.");
		$("#respEaiId").focus();
		$("#respEaiId").select();
		errflag = true;
	}
	if(getByteStr($("#itDevNm").val()) > 20 ) {
		alert("전송결과EAI아이디 20byte를 넘을 수 없습니다.");
		$("#itDevNm").focus();
		$("#itDevNm").select();
		errflag = true;
	}
	return errflag;
}


//  취소 클릭시(리스트로 이동)
function goCancel() {
	if( $("#popupYn").val() == 'Y'){
		parent.goIfBasciListPopResize();
	}
	$("#ifFormVo").attr("target","").attr("action","./ifBasicListP.ums").submit();
}


//  취소 클릭시(리스트로 이동)
function goAddRow() {
	var rowCount =  Number( $('#tableRowCount').val() );
	var param = "rowCount=" + rowCount; 
	
	var query = 'input[name="chkMappTbl"]:checked';
  	var checkboxs = document.querySelectorAll(query);

	$.ajax({
		type : "GET",
		url : "./ifBasicListAddRow.ums?" + param ,
		dataType : "html",
		success : function(pageHtml){
			
			if(checkboxs.length < 1 ){
				$('#mappingTable > tbody:last').append(pageHtml);
			}else{
				for (var i = 0; i < checkboxs.length; i++) {
					//if( checkboxs[i].value >= 7){
						$('#mappTr' + checkboxs[i].value  ).after(pageHtml);
					//}	
    			}
			}	
			$('#tableRowCount').val( rowCount + 1 );
			$('#ifCol'+ rowCount ).focus();
		},
		error : function(){
			alert("List Data Error!!");
		}
	});
}


//  취소 클릭시(리스트로 이동)
function goDeleteRow() {
	
	var query = 'input[name="chkMappTbl"]:checked';
  	var checkboxs = document.querySelectorAll(query);
  	
	var ifCampIds="";
	if(checkboxs.length < 1 ){
		alert("삭제할 매핑정보를 선택해주세요");
		return;
	} else {
		for (var i = 0; i < checkboxs.length; i++) {
			if( checkboxs[i].value >= 7){
				$('#mappTr' + checkboxs[i].value  ).remove();
			}	
    	}
	}
}




// 파일 업로드
function mappExcelUpload() {
	
	var ifCampId = $("#ifCampId").val();
	
	
	if($("#upTempFlPath").val() == "") {
		alert("파일을 선택해주세요.");
		return;
	}
	
	var extCheck = "xlsx";
	var fileName = $("#upTempFlPath").val();
	var fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
	
	$("#uploadIfCampId").val( ifCampId );
	$("#uploadNewYn").val("N");
	
	
	
	if(extCheck.toLowerCase().indexOf(fileExt.toLowerCase()) >= 0) {
		var frm = $("#fileUploadSeg")[0];
		var frmData = new FormData(frm);
		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: './uploadFile.json',
			data: frmData,
			processData: false,
			contentType: false,
			success: function (result) {
				if( result.successYn == 'Y'){
					alert("파일 업로드가 완료되었습니다. 반영위해 수정버튼 클릭해주세요");
					fn.popupClose('#popup_file_mapp');
					getifBasicUpdateList();
				}else{
					alert("파일 업로드가 실패되었습니다.");
				}
			},
			error: function (e) {
				alert("File Upload Error!!");
			}
		});
	} else {
		alert("등록 할 수 없는 파일 형식입니다. xlsx 만 가능합니다.");
	}
}

// 샘플 클릭시()샘플파일 다운로드)
function downloadSample() {
	iFrmDown.location.href = "./down.ums?downType=003";
}


// 검색 버튼 클릭시
function getifBasicUpdateList() {
	var frm = $("#ifFormVo")[0];
	var frmData = new FormData(frm);
	var query = null;
	var checkboxs = null;
	
	$.ajax({
		type : "POST",
		url : "./ifBasicUpdateList.ums" ,
		data: frmData,
		dataType : "html",
		processData: false,
		contentType: false,
		success : function(pageHtml){
			$("#divIfMappList").html(pageHtml);
			//count 수정
			query = 'input[name="chkMappTbl"]';
  			checkboxs = document.querySelectorAll(query);
	 		$('#tableRowCount').val(checkboxs.length); 
		},
		error : function(){
			alert("List Data Error!!");
		}
	});
	
} 