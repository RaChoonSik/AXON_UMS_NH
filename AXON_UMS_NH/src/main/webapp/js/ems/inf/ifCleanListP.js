$(document).ready(function() {
	
	$("#searchIfCampId").keydown(function(e){
		if(e.keyCode == 13) {
			e.preventDefault();
		}
	});
	
	$("#searchIfCampName").keydown(function(e){
		if(e.keyCode == 13) {
			e.preventDefault();
		}
	});
	goSearch("1");
});


 
function goChagePerPageRows(rows){
	$("#searchForm input[name='rows']").val(rows);
	goSearch("1");
}
	
	

// 검색 버튼 클릭시
function goSearch(pageNo) {
	$("#searchForm input[name='page']").val(pageNo);
	var param = $("#searchForm").serialize();	
	console.log(param);
	$.ajax({
		type : "GET",
		url : "./ifCleanList.ums?" + param,
		dataType : "html",
		success : function(pageHtml){
			$("#divIfBasicList").html(pageHtml);
		},
		error : function(){
			alert("List Data Error!!");
		}
	});
} 

// 코드 또는 명 클릭시
function goUpdate(ifCampInd) {
	
	if( $("#popupYn").val() == 'Y'){
		parent.goIfBasciUpdatePopResize();
	}	
	$("#ifCampId").val(ifCampInd);
	$("#searchForm").attr("target","").attr("action","./ifBasicUpdateP.ums").submit();
}

// 초기화 버튼 클릭시
function goReset() {
	$("#searchForm")[0].reset();
}

// 신규등록 버튼 클릭시
function goAdd() {
	if( $("#popupYn").val() == 'Y'){
		parent.goIfBasciAddPopResize();
	}
	$("#searchForm").attr("target","").attr("action","./ifBasicAddP.ums").submit();
	//document.location.href = "./ifBasicAddP.ums";
}
 
//삭제 EVENT 구현
function goDelete() { 
	 
	const query = 'input[name="delIfCampId"]:checked';
  	const checkboxs = document.querySelectorAll(query);
  	
	var ifCampIds="";
	if(checkboxs.length < 1 ){
		alert("삭제할 사용자그룹  정보를  선택해주세요");
		return;
	} else {
		for (var i = 0; i < checkboxs.length; i++) {
        	ifCampIds += checkboxs[i].value + ',';	
    	}
	}
	
	var ret = window.confirm('삭제하시겠습니까?')
	if( ! ret ){
		return;
	}
	$.getJSON("/ems/inf/ifBasicDelete.json?ifCampIds=" + ifCampIds, function(data) {
		if(data) {
			if(data.result == "Success") {
		 		alert("삭제에 성공 하였습니다");
				$("#page").val("1");
				$("#searchForm").attr("target", "").attr("action", "/ems/inf/ifBasicListP.ums").submit();
			} else {
				alert(data.resultMessage  );
			}
		} else {
			alert("삭제에 실패 하였습니다");
		}
	});  
} 
   
function selectAll(selectAll)  {
	$("input[name='delIfCampId']").each(function(idx,item){
		if( $(item).is(":disabled") == false) {
			$(item).prop("checked",selectAll.checked);
		}
	});
} 

function goPageNum(page) {
	goSearch(page);
}


function selectRow( rowIndex ){
	var rnsYn = $("#rnsYn").val();
	
	if(rnsYn == 'Y'){
		var ifCampId = $("#tbIfCampId"+ rowIndex ).val();
		var webAgenUrl =  $("#agentUrlRoot").val() +  '/ems/mail/' + ifCampId + '.jsp?BIZKEY=$:MAP1:$';
		parent.$("#webAgentUrl" ).val(webAgenUrl);
		parent.$("#txtWebAgentUrl").html("첨부파일로 지정되었습니다.");
		parent.$("#eaiCampNo").val( ifCampId);
	}else{
		var campNo = $("#tbCampNo"+ rowIndex ).val();
		var campNm = $("#tbCampNm"+ rowIndex ).val();
		var campTy = $("#tbCampTy"+ rowIndex ).val();
		
		parent.setPopCampInfo(campNo, campNm, campTy);
		
		var segNo = $("#tbSegNo"+ rowIndex ).val();
		var mergeKey = $("#tbMergeKey"+ rowIndex ).val();
		var segNm = $("#tbSegNm"+ rowIndex ).val();
		
		var segNoMerge = segNo + '|'+ mergeKey;
		parent.setPopSegInfo(segNoMerge,segNm);
		
		var ifCampId = $("#tbIfCampId"+ rowIndex ).val();
		var webAgenUrl =  $("#agentUrlRoot").val() +  '/ems/mail/' + ifCampId + '.jsp?BIZKEY=$:BIZKEY:$';
		parent.$("#webAgentUrl" ).val(webAgenUrl);
		parent.$("#txtWebAgentUrl").html("첨부파일로 지정되었습니다.");
	}
	parent.fn.popupClose('#popup_ifBasicListP');
	
}



// 등록 클릭시
function goIfCleanListPop( cleanYm, ifId     ) {
	$("#iframeIfCleanListP").css('height','1100px');
	$("#iframeIfCleanListP").attr("src","/ems/inf/ifCleanPopListP.ums?searchCleanYm=" + cleanYm + '&searchIfId=' + ifId ); 
	fn.popupOpen('#popup_ifCleanListP');
}



