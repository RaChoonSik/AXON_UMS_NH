<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.08.19
	*	설명 : EMS 공통 헤더(header) 파일
	**********************************************************/
--%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="IE=EDGE">
<meta name="Author" content="enders">
<meta name="keywords" content="UMS">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AXON EMS 대량 이메일 시스템</title>
<link rel="shortcut icon" href="<c:url value='/img/favicon.png'/>" type="image/x-icon">
<link rel="icon" href="<c:url value='/img/favicon.png'/>" type="image/x-icon">
<link rel="stylesheet" href="<c:url value='/css/reset.css'/>">
<link rel="stylesheet" href="<c:url value='/css/jquery_library.css'/>">
<link rel="stylesheet" href="<c:url value='/css/common.css'/>">
<link rel="stylesheet" href="<c:url value='/css/style_ems.css'/>">

<script type="text/javascript" src="<c:url value='/js/jquery_2.1.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery_library.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/ems.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/ums.common.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var frameYn = '<%=(String)session.getAttribute("NEO_FRAME_YN")%>';
		if (frameYn == "N") {
			$("body").css("overflow", "hidden");
			$("#wrap .content-wrap").css("padding", "0");
			
			setTimeout(function(){
				//document.body.scrollHeight
				let height = $("#wrap .content-wrap").css("height").replace(/[^0-9]/g, "");;
				let message = {height: height};
				window.top.postMessage(message, "*");
			},300);
		}
	});
</script>
</head>
