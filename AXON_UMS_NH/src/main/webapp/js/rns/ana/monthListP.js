/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.09.22
	*	설명 : 월별통계 화면
*	설명 : 월별통계 JavaScript
**********************************************************/

$(document).ready(function(){
	/* month picker */
	var today = getTodayType();
	var options = {
		pattern: 'yyyy.mm', 
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'], 
	};
	$("#searchYm").monthpicker(options);
	
	//초기값
	function getTodayType(){
		var date = new Date();
		return date.getFullYear() + "." + ("0"+(date.getMonth()+1)).slice(-2);
	}
	$('#searchYm').val(today);
	
	goSearch();
		
	//달력버튼
	$('.btn-calendar').on('click', function(){
		$('#searchYm').focus(); 
	});
	 
});
// 검색 버튼 클릭
function goSearch() { 	
	var param = $("#searchForm").serialize();
	console.log(param);
	$.ajax({
		type : "GET",
		url : "/rns/ana/monthList.ums?" + param,
		dataType : "html",
		success : function(pageHtml){
			$("#divMonthDataList").html(pageHtml);
		},
		error : function(){
			alert("목록 조회에 실패하였습니다");
		}
	});
}

// 초기화 버튼 클릭
function goInit() {
	$("#searchForm")[0].reset();
	var date = new Date();
	$('#searchYm').val(date.getFullYear() + "." + ("0"+(date.getMonth()+1)).slice(-2));
}

// 목록에서 메일명 클릭시
function goTestList(mid, sdate, tnm) {
	$("#txtSendDt").html( sdate );
	$("#txtTaskNm").html( tnm );
	
	$("#testSendForm input[name='mid']").val( mid );
	var param = $("#testSendForm").serialize();
	$.ajax({
		type : "GET",
		url : "/rns/ana/serviceTestResultList.ums?" + param,
		dataType : "html",
		success : function(pageHtml){
			$("#testResultList").html(pageHtml);
			
			fn.popupOpen('#popup_testsend_result');
		},
		error : function(){
			alert("목록 조회에 실패하였습니다");
		}
	});
}

// 페이징
function goPageNum(pageNum) {
	goSearch(pageNum);
}

// 페이징 테스트
function goPageNumTest(pageNum) {
	$("#testSendForm input[name='page']").val( pageNum );
	var param = $("#testSendForm").serialize();
	$.ajax({
		type : "GET",
		url : "/rns/ana/serviceTestResultList.ums?" + param,
		dataType : "html",
		success : function(pageHtml){
			$("#testResultList").html(pageHtml);
		},
		error : function(){
			alert("목록 조회에 실패하였습니다");
		}
	});
}

function goExcel(){
	$("#searchForm").attr("target","iFrmExcel").attr("action","/rns/ana/monthExcelList.ums").submit();
}