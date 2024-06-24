<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.io.DataInputStream" %>
<%@page import="java.util.*,java.lang.*,java.net.*,java.io.*" %>
<jsp:useBean id="util" class="com.mp.util.InitApp" scope="application" />
<%
    //퇴직연금 지급신청 내역 안내
 
	request.setCharacterEncoding("UTF-8");
	String MNAME = "test";
	String p_bizkey   = util.getRequestData(request,"BIZKEY","");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=800">
    <title>광주은행 이메일템플릿 - 적립금 운용현황 보고서[요약]</title>

    </style>
    <script>       
    </script>
</head>
<body>
	<h1>VestMail Test</h1>
	<hr>
	<p>MNAME = <%=MNAME%></p>
	<p>p_bizkey = <%=p_bizkey%></p>
</body>
</html>