<%@page contentType="text/html; charset=UTF-8" %><%@page import="java.io.DataInputStream" %><%@ 
page import="java.util.*,java.lang.*,java.net.*,java.io.*" %><%@ 
page import="com.fasterxml.jackson.databind.*,com.fasterxml.jackson.databind.node.*,com.mp.util.*,kr.co.enders.util.PropertiesUtil" %><%@ 
page import="org.springframework.web.context.*,org.springframework.web.context.support.*,org.springframework.context.support.*" %>
<%@page import="java.io.*,java.util.*,java.sql.*,java.text.*,java.rmi.RemoteException,com.mp.util.*,com.mp.exception.*, java.net.*,com.custinfo.safedata.*" %>
<jsp:useBean id="util" class="com.mp.util.InitApp" scope="application" />
<jsp:useBean id="resp" class="com.mp.util.RespLog" scope="application" />
<%
    //퇴직연금 지급신청 내역 안내
 
	request.setCharacterEncoding("UTF-8");
	String MNAME = "FM_TYS0110.jsp";
	String p_bizkey   = util.getRequestData(request,"BIZKEY","");
	System.out.println("p_bizkey=" + p_bizkey);
	
	String strTmp = "";
	int    idxDot = -1;
	int    idxTild = -1;
	String recevSeqno = "";
	String ifCampId = "";
	String makeDate = "";
	
	StringBuffer sqlBuf   = new StringBuffer();
	HashMap    mainHash   = new HashMap();
	ArrayList  listArray  = new ArrayList();
	ArrayList  listArray2 = new ArrayList();
	ArrayList  listArray3 = new ArrayList();
	ArrayList  listArray4 = new ArrayList();
	
	JsonNode   jsonNode2  = null;
	HashMap    listHash   = null;
	
	
	if( p_bizkey == null   ||   p_bizkey.indexOf("BIZKEY") >= 0 ) {				
		mainHash.put("BASE_DT", "2021-10-27");
		mainHash.put("NAME", "홍길동");
		
	}else{
		
		if( p_bizkey != null && p_bizkey.length() > 8 ){
			makeDate = p_bizkey.substring(0,8);
			strTmp = p_bizkey.substring(8);
			if( strTmp != null ){
				idxDot = strTmp.indexOf('.');
				if(idxDot != -1  ){
					ifCampId   = strTmp.substring(0,idxDot);
					recevSeqno = strTmp.substring(idxDot+1);
				}
			}
		}
		
		sqlBuf.delete(0, sqlBuf.length());
		sqlBuf.append("SELECT  IF_MAKE_DATE, IF_CAMP_ID, IF_MAKE_SEQNO, RECEV_SEQNO, TASK_SEQNO,  RECEV_TIME, JSON_DATA  ");
		sqlBuf.append("FROM    IF_MAIL_INFO       ");
		sqlBuf.append("WHERE   IF_MAKE_DATE = ?   ");
		sqlBuf.append("AND     IF_CAMP_ID   = ?   ");
		sqlBuf.append("AND     RECEV_SEQNO  = ?   ");

		ParamType param = new ParamType();
		param.put(1, makeDate,Code.DB_VARCHAR);
		param.put(2,"FM_TYS0110",Code.DB_VARCHAR);
		param.put(3,recevSeqno,Code.DB_LONG);

		//처리 입력값 설정
		Hashtable inputhash = new Hashtable();
		inputhash.put("SQL",sqlBuf.toString() );
		inputhash.put("PARAM",param);
		inputhash.put("TYPE",Code.SQL_TYPE_SELECT_INFO);
		DataSet ds = null; //데이터 셋 선언
		
		try {
			ds = util.getDSIData(inputhash);
		} catch (Err err) {
			util.log(MNAME,"데이터 호출 에러 입니다.\n"+err.getEXStr());
			util.setErr(request,err.getCode(),MNAME,err.getMsg());
		} catch (Exception e) {
			util.log(MNAME,"데이터 호출 에러입니다.\n"+e.toString());
			util.setErr(request,"13F",MNAME,"데이터 호출 에러입니다.");
		}
		
		if( ds != null && ds.next() ){
			String jsonData = ds.getData("JSON_DATA");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readValue(jsonData, JsonNode.class);
			
			mainHash.put("PLAN_CD", jsonNode.get("PLAN_CD").asText());
			mainHash.put("JOINER_CD", jsonNode.get("JOINER_CD").asText());
			mainHash.put("SEND_YN", jsonNode.get("SEND_YN").asText());
			mainHash.put("SEND_DATE", jsonNode.get("SEND_DATE").asText());
			mainHash.put("FLIN_SNDD", jsonNode.get("FLIN_SNDD").asText());
			mainHash.put("SBSR", jsonNode.get("SBSR").asText());
			mainHash.put("JOING_DT", jsonNode.get("JOING_DT").asText());
			mainHash.put("IST_TYP", jsonNode.get("IST_TYP").asText());
			mainHash.put("SBSR_BLNG_CMP", jsonNode.get("SBSR_BLNG_CMP").asText());
			mainHash.put("PNSN_OPNG_POSB_DT", jsonNode.get("PNSN_OPNG_POSB_DT").asText());
			mainHash.put("INFO_NTF_PNTM", jsonNode.get("INFO_NTF_PNTM").asText());
			mainHash.put("PAYAM_SUM_AMT", jsonNode.get("PAYAM_SUM_AMT").asText());
			mainHash.put("MNIN_PFLS", jsonNode.get("MNIN_PFLS").asText());
			mainHash.put("HLFW_WTHD_AMT", jsonNode.get("HLFW_WTHD_AMT").asText());
			mainHash.put("FEE_AMT", jsonNode.get("FEE_AMT").asText());
			mainHash.put("EVAL_AMT", jsonNode.get("EVAL_AMT").asText());
			mainHash.put("ACML_YIELD", jsonNode.get("ACML_YIELD").asText());
			mainHash.put("YRUC_YIELD", jsonNode.get("YRUC_YIELD").asText());
			mainHash.put("JOING_DT2", jsonNode.get("JOING_DT2").asText());
			mainHash.put("BASE_DT", jsonNode.get("BASE_DT").asText());
			mainHash.put("IST_TYP2", jsonNode.get("IST_TYP2").asText());
			mainHash.put("SBSR2", jsonNode.get("SBSR2").asText());
			mainHash.put("SBSR_BLNG_CMP2", jsonNode.get("SBSR_BLNG_CMP2").asText());
			mainHash.put("CLPH_EMAIL", jsonNode.get("CLPH_EMAIL").asText());
			mainHash.put("FTRE_INSTL_AMT", jsonNode.get("FTRE_INSTL_AMT").asText());
			mainHash.put("PAYAM_CMP", jsonNode.get("PAYAM_CMP").asText());
			mainHash.put("PAYAM_SBSR", jsonNode.get("PAYAM_SBSR").asText());
			mainHash.put("SBTR_FEE", jsonNode.get("SBTR_FEE").asText());
			mainHash.put("SBTR_WTHD", jsonNode.get("SBTR_WTHD").asText());
			mainHash.put("MNIN_PFLS2", jsonNode.get("MNIN_PFLS2").asText());
			mainHash.put("EOCT_INSTL_AMT", jsonNode.get("EOCT_INSTL_AMT").asText());
			mainHash.put("PAYAM_CMP_WHOL", jsonNode.get("PAYAM_CMP_WHOL").asText());
			mainHash.put("PAYAM_SBSR_WHOL", jsonNode.get("PAYAM_SBSR_WHOL").asText());
			mainHash.put("SBTR_FEE_WHOL", jsonNode.get("SBTR_FEE_WHOL").asText());
			mainHash.put("SBTR_WTHD_WHOL", jsonNode.get("SBTR_WTHD_WHOL").asText());
			mainHash.put("PIAM_GUNT_CCNT", jsonNode.get("PIAM_GUNT_CCNT").asText());
			mainHash.put("RSLTS_DVDN_CCNT", jsonNode.get("RSLTS_DVDN_CCNT").asText());
			
			ArrayNode jsonList =(ArrayNode) jsonNode.get("LIST1");
			if( jsonList != null){
				for(int i =0;i< jsonList.size(); i++ ){
					jsonNode2 = (JsonNode) jsonList.get(i);
					listHash = new HashMap();
					listHash.put("HOLD_GDS_NM", jsonNode2.get("HOLD_GDS_NM").asText());
					listHash.put("HOLD_GDS_BUY_AMT", jsonNode2.get("HOLD_GDS_BUY_AMT").asText());
					listHash.put("HOLD_GDS_PRVS_AMT", jsonNode2.get("HOLD_GDS_PRVS_AMT").asText());
					listHash.put("HOLD_GDS_EVAL_PFLS", jsonNode2.get("HOLD_GDS_EVAL_PFLS").asText());
					listHash.put("HOLD_GDS_INSTL_EVAL", jsonNode2.get("HOLD_GDS_INSTL_EVAL").asText());
					listHash.put("HOLD_GDS_INSTL_WEGHT", jsonNode2.get("HOLD_GDS_INSTL_WEGHT").asText());

					listArray.add(listHash);
				}
			}
			mainHash.put("CASH_INSTL_EVAL", jsonNode.get("CASH_INSTL_EVAL").asText());
			mainHash.put("CASH_INSTL_WEGHT", jsonNode.get("CASH_INSTL_WEGHT").asText());
			mainHash.put("SUM_INSTL_EVAL", jsonNode.get("SUM_INSTL_EVAL").asText());
			mainHash.put("SUM_INSTL_WEGHT", jsonNode.get("SUM_INSTL_WEGHT").asText());
			mainHash.put("ACML_YIELD_STDD", jsonNode.get("ACML_YIELD_STDD").asText());
			mainHash.put("ACML_YIELD_ENDT", jsonNode.get("ACML_YIELD_ENDT").asText());
			mainHash.put("ACML_YIELD_YIELD", jsonNode.get("ACML_YIELD_YIELD").asText());
			mainHash.put("YRUC_YIELD_YIELD", jsonNode.get("YRUC_YIELD_YIELD").asText());
			
			jsonList =(ArrayNode) jsonNode.get("LIST2");
			if( jsonList != null){
				for(int i =0;i< jsonList.size(); i++ ){
					jsonNode2 = (JsonNode) jsonList.get(i);
					listHash = new HashMap();
					listHash.put("PIAM_GUNT_GDS_NM", jsonNode2.get("PIAM_GUNT_GDS_NM").asText());
					listHash.put("PIAM_GUNT_INTR", jsonNode2.get("PIAM_GUNT_INTR").asText());
					listHash.put("PIAM_GUNT_BUY_DT", jsonNode2.get("PIAM_GUNT_BUY_DT").asText());
					listHash.put("PIAM_GUNT_EXPR_SCHE_DT", jsonNode2.get("PIAM_GUNT_EXPR_SCHE_DT").asText());
					
					listArray2.add(listHash);
				}
			}
			
			mainHash.put("RSLTS_DVDN_YIELD_STDD", jsonNode.get("RSLTS_DVDN_YIELD_STDD").asText());
			mainHash.put("RSLTS_DVDN_YIELD_ENDT", jsonNode.get("RSLTS_DVDN_YIELD_ENDT").asText());
			
			jsonList =(ArrayNode) jsonNode.get("LIST3");
			if( jsonList != null){
				for(int i =0;i< jsonList.size(); i++ ){
					jsonNode2 = (JsonNode) jsonList.get(i);
					listHash = new HashMap();
					listHash.put("RSLTS_DVDN_GDS_NM", jsonNode2.get("RSLTS_DVDN_GDS_NM").asText());
					listHash.put("RSLTS_DVDN_ACML_YIELD", jsonNode2.get("RSLTS_DVDN_ACML_YIELD").asText());
					listHash.put("RSLTS_DVDN_YRUC_YIELD", jsonNode2.get("RSLTS_DVDN_YRUC_YIELD").asText());
					
					listArray3.add(listHash);
				}
			}
			
			mainHash.put("FEE_IMPS_PNSN_BFR_INSTL_ASET", jsonNode.get("FEE_IMPS_PNSN_BFR_INSTL_ASET").asText());
			mainHash.put("FEE_IMPS_PNSN_BFR_INSTL_MNIN", jsonNode.get("FEE_IMPS_PNSN_BFR_INSTL_MNIN").asText());
			mainHash.put("FEE_IMPS_PNSN_BFR_YIELD_ASET", jsonNode.get("FEE_IMPS_PNSN_BFR_YIELD_ASET").asText());
			mainHash.put("FEE_IMPS_PNSN_BFR_YIELD_MNIN", jsonNode.get("FEE_IMPS_PNSN_BFR_YIELD_MNIN").asText());
			mainHash.put("FEE_IMPS_PNSN_ATT_INSTL_ASET", jsonNode.get("FEE_IMPS_PNSN_ATT_INSTL_ASET").asText());
			mainHash.put("FEE_IMPS_PNSN_ATT_INSTL_MNIN", jsonNode.get("FEE_IMPS_PNSN_ATT_INSTL_MNIN").asText());
			mainHash.put("FEE_IMPS_PNSN_ATT_YIELD_ASET", jsonNode.get("FEE_IMPS_PNSN_ATT_YIELD_ASET").asText());
			mainHash.put("FEE_IMPS_PNSN_ATT_YIELD_MNIN", jsonNode.get("FEE_IMPS_PNSN_ATT_YIELD_MNIN").asText());
			
			
			jsonList =(ArrayNode) jsonNode.get("LIST4");
			if( jsonList != null){
				for(int i =0;i< jsonList.size(); i++ ){
					jsonNode2 = (JsonNode) jsonList.get(i);
					listHash = new HashMap();

					listHash.put("FUND_PAY_GDS_NM", jsonNode2.get("FUND_PAY_GDS_NM").asText());
					listHash.put("FUND_PAY_PRAT", jsonNode2.get("FUND_PAY_PRAT").asText());
					listHash.put("FUND_PAY_PRAT_AMT", jsonNode2.get("FUND_PAY_PRAT_AMT").asText());
					listArray4.add(listHash);
				}
			}

			//mainHash.put("IST_TYP_DIV", jsonNode.get("IST_TYP_DIV").asText());
			//mainHash.put("RSK_ASET_EXCS_GDCC", jsonNode.get("RSK_ASET_EXCS_GDCC").asText());
			
		}else{
			mainHash.put("BASE_DT", "데이터없음");
			mainHash.put("NAME", "데이터없음");
		}
	}
	ServletContext ctx = pageContext.getServletContext();
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
	PropertiesUtil properties = (PropertiesUtil) wac.getBean("properties");
	//String imgRootPath = properties.getProperty("JSP_IMG_ROOT_PATH");
	String imgRootPath = "/img";

%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=800">
    <title>광주은행 이메일템플릿 - 적립금 운용현황 보고서[요약]</title>
    <style>
        /******** reset css ********/
        html,body,div,p,h1,h2,h3,h4,h5,h6,strong,span,label,legend, ul,ol,li,dl,dt,dd,table,thead,tbody,tr,th,td,tfoot,img,a,button,footer,section{padding:0;margin:0;}
        ul,ol,li{list-style:none;}
        button{background:transparent;cursor:pointer;}
        img{max-width:100%;vertical-align:top;}
        img,button,fieldset,table, tfoot{border:0;}
        address{font-style:normal;}
        label,a{display:inline-block;}
        h2,h3,strong,em{font-weight:600;}
        input,select,label,button,textarea{border-radius:0;-webkit-border-radius:0;background-color:transparent;color:#111;font-size:14px;line-height:normal;font-family:'Noto Sans KR','맑은고딕',Malgun Gothic,'돋움', Dotum, Helvetica,'Apple SD Gothic Neo',Sans-serif;vertical-align:middle;}
        table{width:100%;border-spacing:0;}
        a{border:0;color:#444;text-decoration:none;}
        a:hover{color:#0070D2;text-decoration:none;}
        *, *:before, *:after{box-sizing:border-box;}
        *:focus{outline:0;}
        html,body{height:100%;}
        legend, .hidden, .hide{position:absolute !important;top:0 !important;left:0 !important;padding:0 !important;border:0 !important;overflow:hidden !important;width:0 !important;height:0 !important;line-height:0 !important;}
        body{width:800px;margin:0 auto;padding:30px 16px;font-family:'Noto Sans KR','맑은고딕',Malgun Gothic,'돋움', Dotum, Helvetica,'Apple SD Gothic Neo',Sans-serif;font-size:13px;font-weight:400;color:#3a3a3a;line-height:normal;box-sizing:border-box;letter-spacing:-0.07em; }
        html, table, th, td{
            /* Prevent font scaling in landscape */
            -webkit-text-size-adjust: none; /*Chrome, Safari, newer versions of Opera*/
            -moz-text-size-adjust: none; /*Firefox*/
            -ms-text-size-adjust: none;  /*Ie*/
            -o-text-size-adjust: none; /*old versions of Opera*/
        }

        /******** common css ********/
        /* section */
        .tal{text-align:left !important;}
        .tac{text-align:center !important;}
        .tar{text-align:right !important;}
        .clear:after{content:'';display:block;clear:both;}
        .txt-black{color:#111111 !important;margin:0 !important}
        .txt-gray{font-size:14px;color:#999999;}
        .bold-blue{color:#0070D2 !important;font-weight:600 !important;font-size:20px !important;}
        .title-desc{margin-bottom:50px;font-size:16px;}
        .cont-head h1{width:173px;height:34px;text-indent:-9999px;}
        @media screen and (min-width:360px) and (max-width:720px){
            .cont-head h1{width:173px;height:34px;}
        }
        .cont-head h2{margin-top:26px;font-size:33px;color:#1F1A60;}
        .cont-body h3{position:relative;margin-bottom:20px;padding-bottom:10px;font-size:20px;color:#111; border-bottom:1px solid #CCCCCC;}
        .cont-body h3 ~ h3{margin-top:30px;}
        .cont-body h3:after{content:'';display:block;position:absolute;top:12px;right:10px;width:12px;height:8px; }
        .cont-body h3 .info-unit{position:absolute;top:3px;right:36px;font-size:14px;font-weight:400;color:#999;}
        .cont-body h3 .bold-blue{top:0px;}
        .cont-body h4{margin-bottom:10px;color:#111;font-size:16px;}
        .cont-body h4 ~ h4{margin-top:20px;}
        .cont-body h4 + ul {margin-bottom:20px;}
        .cont-body ul{font-size:14px;}
        .cont-body ol{font-size:14px;}
        .cont-body .list-dot{margin-bottom:20px;}
        .cont-body .list-dot li{position:relative;padding-left:10px;list-style-position:inside;font-size:14px;}
        .cont-body .list-dot li:before{content:'•';display: inline-block;position:absolute;top:0;left:0;}
        .cont-body .bluebox{width:100%;height:auto;margin-bottom:20px;padding:15px;background:#EFF8FF;font-size:14px;box-sizing:border-box;}
        .cont-body .bluebox h4{position:relative;margin-bottom:10px;padding-left:20px;font-size:16px;font-weight:600;color:#111;}
        .cont-body .bluebox h4:before{content:'';display:block;position:absolute;top:5px;left:0px; width:16px;height:16px;padding-right:5px;}
        .cont-body .bluebox strong{font-size:16px;color:#4D4D4D;}
        .cont-body .bluebox strong.dot{display:inline-block;margin-bottom:10px;color:#111;}
        .cont-body .bluebox strong.dot:before{content:'•';display:inline-block;padding-right:6px;}
        .cont-body .bluebox strong.check{position:relative;font-weight:600;color:#0070D2;}
        .cont-body .bluebox strong.check:before{content:'';display: inline-block;}

        .tab-menu{height:40px;margin-top:30px;margin-bottom:50px;}
        .tab-menu > button{display:block;float:left;min-width:160px;height:40px;margin-left:4px;background:#EFEFEF;font-size:16px;color:#4d4d4d;text-align:center;line-height:40px;}
        .tab-menu > button:first-child{margin-left:0;}
        .tab-menu > button.active,
        .tab-menu > button:hover{background:#0070D2;color:#fff;font-weight:600;}
        .tab-content{display:none;}
        .tab-content.active{display:block;}

        table{margin-bottom:30px;}
        table tr:first-child th, table tr:first-child td{border-top:1px solid #DADADA;}
        table tr > th:first-child{border-left:0}
        table th{padding:5px;background:#CCE9FF;font-size:12px;font-weight:600;border-bottom:1px solid #DADADA;border-left:1px solid #DADADA;}
        table td{padding:5px 10px;background:#fff;font-size: 12px;text-align:center;border-bottom: 1px solid #DADADA;border-left:1px solid #DADADA;}
        .tab ul + table{margin-bottom:20px;}
        .tab .table-title{margin-bottom:10px;font-size:14px;}
        .tab .total-bold{font-weight:600;color:#4d4d4d;}
        .tab .image-box {overflow:hidden;}
        .tab .image-box img{width:100%;}

        /* 파란배경이 없는 테이블의 경우 .table-white 클래스 사용*/
        .table-white th{background:#fff;text-align:left;border:0;}
        .table-white td{background:#fff;text-align:left;border:0;}
        .table-white tr:first-child th, .tab .table-white tr:first-child td{border-top:0;}
        .tab .caution ul {color:#999; list-style:none !important;text-indent:0;margin-bottom:0;padding-left:14px;}
        .tab .caution ul li:before {content:"\2022";color:#dadada !important;font-weight:bold;display:inline-block;width:1em;margin-left:-1em;}
        .tab .caution p{color:#999;font-size:14px;}

        /* 푸터 */
        footer{padding-top:80px;padding-bottom:30px;color:#9A9A9A;}
        footer dl{display: table;}
        footer dt,footer dd{display: table-cell;}
        footer .footer-logo{width:126px;height:26px;text-indent:-9999px;}
        footer dd{padding-left:36px;}
        footer p{padding-top:10px;}
        @media screen and (min-width:360px) and (max-width:720px){
            footer .footer-logo{}
        }
    </style>
    <script>
        'use strict';
        window.onload = function(){

            /* Function :: tab - inside tab */
            // IE11 forEach support
            if (window.NodeList && !NodeList.prototype.forEach) {
                NodeList.prototype.forEach = Array.prototype.forEach;
            }

            function findParent(el, className){
                var check = el.parentNode.classList.contains(className);
            
                if(check == true){
                    return el.parentNode;
                }else{
                    return findParent(el.parentNode, className);
                }
            }

            function bindingTab(container){
                var containEl = document.querySelectorAll(container);
            
                containEl.forEach(function(tabArea){
                    var tabLink = tabArea.querySelectorAll('.tab-link');
                    
                    tabLink.forEach(function(item){

                        item.addEventListener('click', function(){
                            // 변수설정
                            var parent = findParent(this, 'tab');
                            var idx = this.dataset['idx'];
                            var depth = this.dataset['depth'];
                            var tabLinkArr = parent.querySelectorAll('.tab-link[data-depth="'+ depth +'"]');
                            var contentArr = parent.querySelectorAll('.tab-content[data-depth="'+ depth +'"]');
                            
                            // 활성화
                            tabLinkArr.forEach(function(tabLink){ tabLink.classList.remove('active'); });
                            this.classList.add('active');
                            contentArr.forEach(function(content){ content.classList.remove('active'); });
                            parent.querySelector('.tab-content[data-idx="'+ idx +'"][data-depth="'+ depth +'"]').classList.add('active');

                        });
                    });
                });
            }
            bindingTab('.tab-wrap');
        }
    </script>
</head>
<body>
    <div id="wrap">
        <div id="content">
            <section class="cont-head">
                <h1>광주은행</h1>
                <h2>적립금 운용현황 보고서[요약]</h2>
            </section>
            <section class="cont-body">

                <div class="tab-wrap tab">

                    <div class="tab-menu clear">
                        <button class="tab-link active" data-depth="0" data-idx="0">요약</button>
                        <button class="tab-link" data-depth="0" data-idx="1">본문</button>
                    </div>
                    <!-- 요약정보// -->
                    <div class="tab-content active" data-depth="0" data-idx="0">
                        <p class="title-desc" style="margin-bottom:30px;">이 운용보고서 요약은 고객님이 가입하신 퇴직연금계좌의 비용, 실질수익률 등 주요정보를 제공하기 위한 것으로, 계약 관련, 상세내용은 본문에서 확인하실 수 있습니다.</p>
                        <h3>기본정보 <span class="info-unit">작성기준일<%=mainHash.get("FLIN_SNDD") %></span></h3>
                        <table>
                            <colgroup>
                                <col style="width:12%;">
                                <col style="width:12%;">
                                <col style="width:12%;">
                                <col style="width:12%;">
                            </colgroup>
                            <tbody>
                                <tr class="tac">
                                    <th>가입자(또는 사업장)</th>
									<td><%= mainHash.get("SBSR") %></td>
                                    <th>가입(계약)일자</th>
                                    <td><%= mainHash.get("JOING_DT") %></td>
                                </tr>
                                <tr class="tac">
                                    <th>제도유형</th>
                                    <td><%= mainHash.get("IST_TYP") %></td>
                                    <th>가입자 소속회사</th>
                                    <td><%= mainHash.get("SBSR_BLNG_CMP") %></td>
                                </tr>
                                <tr class="tac">
                                    <th>연금수령개시 가능일</th>
                                    <td colspan="3"><%= mainHash.get("PNSN_OPNG_POSB_DT") %></td>
                                </tr>
                            </tbody>
                        </table>

                        <h3>주요정보</h3>
                        <ul class="list-dot">
                            <li>
                                정보고지 시점(<%= mainHash.get("INFO_NTF_PNTM") %>) 기준으로 고객님께서 가입하신 퇴직연금계좌에 납부한 납입원금 합계액은<br>(① <%= mainHash.get("PAYAM_SUM_AMT") %>)이며, 적립금(평가금액)은 (④ <%= mainHash.get("EVAL_AMT") %>원), 누적수익률은 (⑤ <%= mainHash.get("ACML_YIELD") %>%)입니다.
                            </li>
                        </ul>
                        <table style="margin-bottom:5px;">
                            <colgroup>
                                <col style="width:25%;">
                                <col style="width:auto;">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>① 납입원금</th>
                                    <td class="tal"><%= mainHash.get("PAYAM_SUM_AMT") %>원</td>
                                </tr>
                                <tr>
                                    <th>② 운용수익</th>
                                    <td class="tal"><%= mainHash.get("MNIN_PFLS") %>원</td>
                                </tr>
                                <tr>
                                    <th>③ 중도인출금 등</th>
                                    <td class="tal">(-) <%= mainHash.get("HLFW_WTHD_AMT") %>원</td>
                                </tr>
                                <tr>
                                    <th>④ 수수료 금액</th>
                                    <td class="tal">(-) <%= mainHash.get("FEE_AMT") %>원</td>
                                </tr>
                            </tbody>
                        </table>
                        <ul class="list-dot" style="margin-bottom:5px;">
                            <li style="font-size:12px;">
                                납입원금[①]과 운용손익의 합계에서 중도인출금[③] 및 수수료 금액[④]을 차감한 결과가 적립금(평가금액)[⑤]입니다. 
                            </li>
                        </ul>
                        <table>
                            <colgroup>
                                <col style="width:25%;">
                                <col style="width:auto;">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>⑤ 적립금(평가금액)</th>
                                    <td class="tal"><%= mainHash.get("EVAL_AMT") %>원</td>
                                </tr>
                                <tr>
                                    <th>⑥ 누적수익률(수수료 차감 후)</th>
                                    <td class="tal"><%= mainHash.get("ACML_YIELD") %>%</td>
                                </tr>
                                <tr>
                                    <th>⑦ 연평균수익률</th>
                                    <td class="tal"><%= mainHash.get("YRUC_YIELD") %>%</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //요약정보 -->

                    <!-- 본문정보// -->
                    <div class="tab-content" data-depth="0" data-idx="1">
                        <table style="margin-bottom:10px;">
                            <colgroup>
                                <col style="width:25%;">
                                <col style="width:auto;">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>가입(계약)일자</th>
                                    <td class="tal"><%= mainHash.get("JOING_DT2") %></td>
                                </tr>
                                <tr>
                                    <th>기준일자</th>
                                    <td class="tal"><%= mainHash.get("BASE_DT") %></td>
                                </tr>
                                <tr>
                                    <th>제도유형</th>
                                    <td class="tal"><%= mainHash.get("IST_TYP2") %></td>
                                </tr>
                                <tr>
                                    <th>가입자(또는 사업장)</th>
                                    <td class="tal"><%= mainHash.get("SBSR2") %></td>
                                </tr>
                                <tr>
                                    <th>가입자 소속회사</th>
                                    <td class="tal"><%= mainHash.get("SBSR_BLNG_CMP2") %></td>
                                </tr>
                                <tr>
                                    <th>연락처(휴대폰/이메일)</th>
                                    <td class="tal"><%= mainHash.get("CLPH_EMAIL") %></td>
                                </tr> 
                            </tbody>
                        </table>
                        <p class="txt-gray">※ 상기 연락처가 변경되는 경우 통지하여 주시기 바랍니다.</p>

                        <div class="tab">
                            <div class="tab-menu clear" style="margin-top:50px;margin-bottom:30px;">
                                <button class="tab-link active" data-depth="1" data-idx="0">자산 현황</button>
                                <button class="tab-link" data-depth="1" data-idx="1">수익률 현황</button>
                                <button class="tab-link" data-depth="1" data-idx="2">수수료 등 비용 안내</button>
                                <button class="tab-link" data-depth="1" data-idx="3">기타사항</button>
                            </div>
    
                            <!-- 자산현황// -->
                            <div class="tab-content active" data-depth="1" data-idx="0">
                                <h3>적립금 현황</h3>
                                <table>
                                    <colgroup>
                                        <col style="width:124px;">
                                        <col style="width:100px;">
                                        <col style="width:100px;">
                                        <col style="width:100px;">
                                        <col style="width:100px;">
                                        <col style="width:124px;">
                                        <col style="width:124px;">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th scope="col" rowspan="2">전월말<br>적립금<br>(A)</th>
                                            <th scope="col" colspan="2">납입액(B)</th>
                                            <th scope="col" colspan="2">차감액(C)</th>
                                            <th scope="col" rowspan="2">운용손익(D)</th>
                                            <th scope="col" rowspan="2">당월말<br>적립금<br>(A+B-C+D)</th>
                                          </tr>
                                          <tr>
                                            <th style="border-left:1px solid #DADADA;">회사납입</th>
                                            <th>가입자납입</th>
                                            <th>수수료</th>
                                            <th>중도인출 등</th>
                                          </tr>
                                          <tr>
                                              <td style="border-left:0;"><%= mainHash.get("FTRE_INSTL_AMT") %>원</td>
                                              <td><%= mainHash.get("PAYAM_CMP") %>원</td>
                                              <td><%= mainHash.get("PAYAM_SBSR") %>원</td>
                                              <td><%= mainHash.get("SBTR_FEE") %>원</td>
                                              <td><%= mainHash.get("SBTR_WTHD") %>원</td>
                                              <td><%= mainHash.get("MNIN_PFLS2") %>원</td>
                                              <td><%= mainHash.get("EOCT_INSTL_AMT") %>원</td>
                                          </tr>
                                    </tbody>
                                </table>

                                <h3>납입 및 차감 세부내역</h3>

                                <p class="table-title">(1) 납입액(부담금)</p>
                                <table style="margin-bottom:20px;">
                                    <colgroup>
                                        <col style="width:190px;">
                                        <col style="width:190px;">
                                        <col style="width:190px;">
                                        <col style="width:190px;">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th scope="col" colspan="2">회사 납입액</th>
                                            <th scope="col" colspan="2">가입자 납입액</th>
                                        </tr>
                                        <tr>
                                            <th scope="col">납일일자</th>
                                            <th scope="col">납입금액</th>
                                            <th scope="col">납일일자</th>
                                            <th scope="col">납입금액</th>
                                        </tr>
                                        <tr>
                                            <td style="border-left:0;">계약일~전기말</td>
                                            <td><%= mainHash.get("PAYAM_CMP_WHOL") %>원</td>
                                            <td>계약일~전기말</td>
                                            <td><%= mainHash.get("PAYAM_SBSR_WHOL") %>원</td>
                                        </tr>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th style="border-top:0;border-left:0;">합계</td>
                                            <td style="border-top:0;"><%= mainHash.get("PAYAM_CMP_WHOL") %>원</td>
                                            <th style="border-top:0;">합계</td>
                                            <td style="border-top:0;"><%= mainHash.get("PAYAM_SBSR_WHOL") %>원</td>
                                        </tr>
                                    </tfoot>
                                </table>
                                <p class="table-title">(2) 차감액(수수료·중도인출 등)</p>
                                <table>
                                    <colgroup>
                                        <col style="width:128px;">
                                        <col style="width:128px;">
                                        <col style="width:128px;">
                                        <col style="width:128px;">
                                        <col style="width:128px;">
                                        <col style="width:128px;">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th scope="col" colspan="3">수수료</th>
                                            <th scope="col" colspan="3">중도인출금 등</th> 
                                        </tr>
                                        <tr>
                                            <th scope="col">세부항목</th>
                                            <th scope="col">차감일자</th>
                                            <th scope="col">차감금액</th>
                                            <th scope="col">세부항목</th>
                                            <th scope="col">차감일자</th>
                                            <th scope="col">차감금액</th>
                                        </tr>
                                        <tr>
                                            <td style="border-left:0;">수수료</td>
                                            <td>계약일~전기말</td>
                                            <td><%= mainHash.get("SBTR_FEE_WHOL") %>원</td>
                                            <td>중도인출금 등</td>
                                            <td>계약일~전기말</td>
                                            <td><%= mainHash.get("SBTR_WTHD_WHOL") %>원</td>
                                        </tr>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th colspan="2" style="border-top:0;border-left:0;">합계</td>
                                            <td style="border-top:0;"><%= mainHash.get("SBTR_FEE_WHOL") %>원</td>
                                            <th colspan="2" style="border-top:0;">합계</td>
                                            <td style="border-top:0;"><%= mainHash.get("SBTR_WTHD_WHOL") %>원</td>
                                        </tr>
                                    </tfoot>
                                </table>

                                <h3>보유 상품현황</h3>
                                <table style="margin-bottom:0;">
                                    <colgroup>
                                        <col style="width:auto;">
                                        <col style="width:94px;">
                                        <col style="width:94px;">
                                        <col style="width:94px;">
                                        <col style="width:100px;">
                                        <col style="width:100px;">
                                    </colgroup>
                                    <tbody>
                                    
                                    
                                        <tr>
                                            <th scope="col" rowspan="2">상품명</th>
                                            <th scope="col" rowspan="2">매입원금</th>
                                            <th scope="col" rowspan="2">지급금</th>
                                            <th scope="col" rowspan="2">평가손익</th>
                                            <th scope="col" colspan="2">적립금</th>
                                          </tr>
                                          <tr>
                                            <th scope="col" style="border-left:1px solid #DADADA;">평가금액</th>
                                            <th scope="col">비중</th>
                                          </tr>
<%
	for(int i = 0 ; i < listArray.size(); i++ ){
		listHash = (HashMap) listArray.get(i);
		
%>                                            
                                          <tr>
                                              <td><%= listHash.get("HOLD_GDS_NM") %></td>
                                              <td><%= listHash.get("HOLD_GDS_BUY_AMT") %>원</td>
                                              <td><%= listHash.get("HOLD_GDS_PRVS_AMT") %>원</td>
                                              <td><%= listHash.get("HOLD_GDS_EVAL_PFLS") %>원</td>
                                              <td><%= listHash.get("HOLD_GDS_INSTL_EVAL") %>원</td>
                                              <td><%= listHash.get("HOLD_GDS_INSTL_WEGHT") %></td>
                                        </tr>
<%
	}
%>                                        
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th style="border-top:0;border-left:0;">대기성 자금</td>
                                            <td colspan="3" style="border-top:0;">-</td>
                                            <td style="border-top:0;"><%= mainHash.get("CASH_INSTL_EVAL") %>원</td>
                                            <td style="border-top:0;"><%= mainHash.get("CASH_INSTL_WEGHT") %></td>
                                        </tr>
                                        <tr>
                                            <th  style="border-top:0;border-left:0;">합계</td>
                                            <td colspan="3" style="border-top:0;">-</td>
                                            <td style="border-top:0;"><%= mainHash.get("SUM_INSTL_EVAL") %>원</td>
                                            <td style="border-top:0;">100%</td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <!-- //자산현황 -->
    
                            <!-- 수익률 현황// -->
                            <div class="tab-content" data-depth="1" data-idx="1">
                                <h3>전체 수익률 현황</h3>
                                <table style="margin-bottom:10px;">
                                    <colgroup>
                                        <col style="width:50%"> 
                                        <col style="width:50%">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>누적수익률*<br>(<%= mainHash.get("ACML_YIELD_STDD") %> ~ <%= mainHash.get("ACML_YIELD_ENDT") %>)</th>
                                            <th>연평균수익률**</th>
                                        </tr>
                                        <tr>
                                            <td style="border-left:0;"><%= mainHash.get("ACML_YIELD_YIELD") %>%</td>
                                            <td><%= mainHash.get("YRUC_YIELD_YIELD") %>%</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <ul>
                                    <li>* 누적수익률 = {적립금(평가금액) + 중도인출금 등} / 납입원금 - 1</li>
                                    <li>** 연평균수익률(기하평균) = (1+가입 이후 누적수익률)^(1/가입년수) - 1<br>
                                        (단, 최초입금일 이후(전액 중도인출이 있는 경우는 인출 후 최초입금일 이후) 기간이 1년 미만인 경우 연평균수익률을 표시하지 않습니다.)
                                    </li>
                                </ul>

                                <h3>상품별 금리/수익률 현황</h3>
                                <p class="table-title">(1) 원리금보장형</p>
                                <table style="margin-bottom:10px;">
                                    <colgroup>
                                        <col style="width:auto">
                                        <col style="width:128px">
                                        <col style="width:128px">
                                        <col style="width:128px">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>상품명</th>
                                            <th>금리</th>
                                            <th>매수일자</th>
                                            <th>만기예정일</th>
                                        </tr>
<%
	for(int i = 0 ; i < listArray2.size(); i++ ){
		listHash = (HashMap) listArray2.get(i);
%>                                         
                                        <tr><!-- 테이블 목록 증감 :: tr 추가 -->
                                            <td style="border-left:0;"><%= listHash.get("PIAM_GUNT_GDS_NM") %></td>
                                            <td><%= listHash.get("PIAM_GUNT_INTR") %></td>
                                            <td><%= listHash.get("PIAM_GUNT_BUY_DT") %></td>
                                            <td><%= listHash.get("PIAM_GUNT_EXPR_SCHE_DT") %></td>
                                        </tr>
<%
	}
%>                                        
                                    </tbody>
                                </table>
                                <ul class="list-dot">
                                    <li>
                                        원리금보장상품은 만기예정일 3영업일 전까지 별도의 의사표시가 없을 경우 동일한운용방법(상품)으로 자동예치됩니다.<br>
                                        자동예치가 불가능한 경우에는 운용관리계약서에서 정한 운용방법(상품)으로 운용되며, 변경을 원할 경우<br>인터넷뱅킹(<a href="">www.kjbank.com</a>) 또는 스마트뱅킹을 통해 변경이 가능합니다.
                                    </li>
                                    <li>
                                        포괄적운용지시를 한 가입자의 경우 원리금보장상품은 만기에 고객님께서 약정한 포괄운용지시에 따라 최적금리의 상품(우리 회사가 제공가능한 한도 내)으로 자동 재예치됩니다.
                                        변경을 원할 경우 인터넷뱅킹(<a href="">www.kjbank.com</a>) 또는 스마트뱅킹을 통해 변경 가능합니다.
                                    </li>
                                </ul>
                                <p class="table-title">(2) 실적배당형</p>
                                <table style="margin-bottom:0;">
                                    <colgroup>
                                        <col style="width:auto">
                                        <col style="width:192px">
                                        <col style="width:192px">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th rowspan="2">상품명</th>
                                            <th colspan="2">수익률</th>
                                        </tr>
                                        <tr>
                                            <th style="border-left:1px solid #DADADA;">
                                                누적수익률<br>
                                                (<%= mainHash.get("RSLTS_DVDN_YIELD_STDD") %>~<%= mainHash.get("RSLTS_DVDN_YIELD_ENDT") %>)
                                            </th>
                                            <th>
                                                연환산수익률<br>
                                                (1년 이상 운용상품)
                                            </th>
                                        </tr>
<%
	for(int i = 0 ; i < listArray3.size(); i++ ){
		listHash = (HashMap) listArray3.get(i);
%>                                        
                                        <tr>
                                            <td style="border-left:0;"><%= listHash.get("RSLTS_DVDN_GDS_NM") %></td>
                                            <td><%= listHash.get("RSLTS_DVDN_ACML_YIELD") %>%</td>
                                            <td><%= listHash.get("RSLTS_DVDN_YRUC_YIELD") %>%</td>
                                        </tr>
<%
	}
%>                                        
                                    </tbody>
                                </table>
		                            </br></br>
		                            ※ 퇴직연금 가입고객은 Line-up에 없는 상품을 선정하여 줄 것을 요청할 수 있으며, 해당 요청 상품이 퇴직연금 자산운용과 관련한 광주은행 자체의 상품선정기준에 부합할 경우 상품라인업으로 선정
		                            </br></br></br>
		                            ○ 상품요청 프로세스
		                            </br></br>
		                            <table style="margin-bottom:10px;">
		                            <colgroup>
		                            	<col style="width:10%;">
		                                <col style="width:25%;">
		                                <col style="width:3%;">
		                                <col style="width:auto;">
		                            </colgroup>
		                            <tbody>
		                                <tr>
		                                <td style="border:none;"></td>
		                                    <th style="background:#ffffff;border-right:1px solid #DADADA;">가입고객 상품요청</th>
		                                    <td style="border:none;"></td>
		                                    <td class="tal" style="border:none;">1) 당행 홈페이지 이용 </br>
															∙ 전체메뉴 > 고객상담 > 고객아이디어제안 </br>
															2) 영업점 또는 고객센터(1588-3388) </br>
		                                    </td>
		                                </tr>
		                                <tr>
		                                <td style="border:none;"></br></td>
		                                <td style="border:none;">↓</br></td>
		                                <td style="border:none;"></br></td>
		                                <td style="border:none;"></br></td>
		                                </tr>
		                                <tr>
		                                <td style="border:none;"></td>
		                                    <th style="background:#ffffff;border-right:1px solid #DADADA;border-top:1px solid #DADADA;">상품적합성 검토</th>
										<td style="border:none;"></td>                                    
		                                    <td class="tal" style="border:none;">∙ 요청상품 특성 검토 </br>
															∙ 상품선정 적합성 검토 </br>
															  ☞ 부적합 해당 시 고객 통보 </br>
															</td>
		                                </tr>
		                                <tr>
		                                <td style="border:none;"></br></td>
		                                <td style="border:none;">↓</br></td>
		                                <td style="border:none;"></br></td>
		                                <td style="border:none;"></br></td>
		                                </tr>
		                                <tr>
		                                <td style="border:none;"></td>
		                                    <th style="background:#ffffff;border-right:1px solid #DADADA;border-top:1px solid #DADADA;">퇴직연금 상품선정위원회</th>
		                                <td style="border:none;"></td>
		                                    <td class="tal" style="border:none;"></br>∙ 상품판매여부 및 판매한도 등 결정 </br>
															  ☞ 부결 시 고객 통보 </br>
															</td>
		                                </tr>
		                                <tr>
		                                <td style="border:none;"></br></td>
		                                <td style="border:none;">↓</br></td>
		                                <td style="border:none;"></br></td>
		                                <td style="border:none;"></br></td>
		                                </tr>
		                                <tr>
		                                <td style="border:none;"></td>
		                                    <th style="background:#ffffff;border-right:1px solid #DADADA;border-top:1px solid #DADADA;">최종 상품선정</th>
		                                <td style="border:none;"></td>
		                                    <td class="tal" style="border:none;"></br>∙ 상품라인업 선정 및 고객 안내 </br>
															☞ 투자유형별 상품 운용 </br>
											</td>
		                                </tr>
		                            </tbody>
		                        </table>
		                        </br>
		                            * 요청 예시
		                        </br></br>
		                        <table style="margin-bottom:5px;">
		                            <colgroup>
		                                <col style="width:15%;">
		                                <col style="width:15%;">
		                                <col style="width:15%;">
		                                <col style="width:15%;">
		                                <col style="width:15%;">
		                                <col style="width:auto;">
		                            </colgroup>
		                            <tbody>
		                                <tr>
		                                    <th style="height:40px;">상품명</th>
		                                    <td class="tal" colspan="5"><p class="txt-gray" style="text-align:center;font-size:12px;">상품명 기재</p></td>
		                                </tr>
		                              	<tr>
		                              		<th style="height:40px;">가입제도</th>
		                                    <td class="tal"><p class="txt-gray" style="text-align:center;font-size:12px;">DC</p></td>
		                                    <th>가입자명</th>
		                                    <td class="tal"><p class="txt-gray" style="text-align:center;font-size:12px;">홍길동</p></td>
		                                    <th>연락처</th>
		                                    <td class="tal"><p class="txt-gray" style="text-align:center;font-size:12px;">핸드폰 : 000 - 000 - 000</br>
		                                    			    E-mail : 000 @ 000.000</p>
		                                    </td>
		                              	</tr>
		                            </tbody>
		                        </table>
                            </div>
                            <!-- //수익률 현황 -->
    
                            <!-- 수수료 등 비용안내// -->
                            <div class="tab-content" data-depth="1" data-idx="2">
                                <h3>수수료 부과 기준</h3>
                                <table style="margin-bottom:10px;">
                                    <colgroup>
                                        <col style="width:128px;">
                                        <col style="width:158px;">
                                        <col style="width:158px;">
                                        <col style="width:158px;">
                                        <col style="width:158px;">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th scope="col" rowspan="2">구분</th>
                                            <th scope="col" colspan="2">연금수령전</th>
                                            <th scope="col" colspan="2">연금수령시</th>
                                          </tr>
                                          <tr>
                                            <th style="border-left:1px solid #DADADA;">자산관리수수료</th>
                                            <th>운용관리수수료</th>
                                            <th>자산관리수수료</th>
                                            <th>운용관리수수료</th>
                                          </tr>
                                          <tr>
                                            <td style="border-left:0;">부과기준</td>
                                            <td><%= mainHash.get("FEE_IMPS_PNSN_BFR_INSTL_ASET") %></td>
                                            <td><%= mainHash.get("FEE_IMPS_PNSN_BFR_INSTL_MNIN") %></td>
                                            <td><%= mainHash.get("FEE_IMPS_PNSN_ATT_INSTL_ASET") %></td>
                                            <td><%= mainHash.get("FEE_IMPS_PNSN_ATT_INSTL_MNIN") %></td>
                                          </tr>
                                          <tr>
                                            <td style="border-left:0;">수수료율</td>
                                            <td><%= mainHash.get("FEE_IMPS_PNSN_BFR_YIELD_ASET") %></td>
                                            <td><%= mainHash.get("FEE_IMPS_PNSN_BFR_YIELD_MNIN") %></td>
                                            <td><%= mainHash.get("FEE_IMPS_PNSN_ATT_YIELD_ASET") %></td>
                                            <td><%= mainHash.get("FEE_IMPS_PNSN_ATT_YIELD_MNIN") %></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <ol style="margin-bottom:30px;">
                                    <li>1) DB 및 개인형IRP의 수수료는 적립금에서 자동 차감합니다. 또는, DC 및 기업형IRP의 경우에는 기업(사용자)이 납입한 부담금에 대해서는 기업이 수수료를 부담하고(별도 현금납부), 가입자가 추가 납입한 부담금에 대해서는 가입자가 수수료를 부담(적립금 차감)합니다.</li>
                                    <li>2) 적립금을 집합투자증권(펀드)으로 운용하는 경우에는 아래의 펀드보수가 추가로 발생(적립금 차감)할 수 있습니다.</li>
                                </ol>
                                <h3>펀드상품의 보수 현황</h3>
                                <table style="margin-bottom:10px;">
                                    <colgroup>
                                        <col style="width:auto">
                                        <col style="width:192px">
                                        <col style="width:192px">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>상품명</th>
                                            <th>총보수율</th>
                                            <th>적립금 100만원당 총보수액</th>
                                        </tr>
<%
	for(int i = 0 ; i < listArray4.size(); i++ ){
		listHash = (HashMap) listArray4.get(i);
%>                                       
                                        <tr>
                                            <td style="border-left:0;"><%= listHash.get("FUND_PAY_GDS_NM") %></td>
                                            <td><%= listHash.get("FUND_PAY_PRAT") %>%</td>
                                            <td><%= listHash.get("FUND_PAY_PRAT_AMT") %>원</td>
                                        </tr>
<%
	}
%>                                        
                                    </tbody>
                                </table>
                                <p class="txt-gray">※ 원리금보장형 상품에는 상품 수수료, 보수가 부과되지 않습니다.</p>
                            </div>
                            <!-- //수수료 등 비용안내 -->
    
                            <!-- 기타사항// -->
                            <div class="tab-content" data-depth="1" data-idx="3">
                                <h3>연금수령 안내</h3>
                                <ul class="list-dot">
                                    <li>'통합연금포털(https://100lifeplan.fss.or.kr)' 방문시 국민연금, 연금저축 등 개인별 연금 가입현황을 확인하실 수 있습니다.</li>
                                    <li>실제로 연금을 수령하기 위해서는 연금수령 개시신청 가능일 이후에 영업점을 방문하거나 금융회사 홈페이지를 통해 '연금 수령개시 신청서' 등 관련 서류를 제출하여야 합니다.</li>
                                </ul>

                                <h3>계좌이체 제도 안내</h3>
                                <p style="font-size:14px;">※ 관련 세법 개정으로 계좌이체 제도 내용이 달라질 수 있음에 주의하시기 바랍니다.</p>
                                <ol style="margin-bottom:20px;">
                                    <li>1. 계좌이체 제도는 소득세법 시행령 제40조의4(연금계좌의 이체)에 따라 기존 연금계좌에 있는 금액이 연금수령이 개시되기 전의 다른 연금계좌로 이체되는 경우 이를 인출로 보지 않고 계속 세액공제나 과세이연 혜택을 유지시키는 것을 의미합니다.</li>
                                    <li>2. 계좌이체는 전부 이전만 가능하고, 일부 금액의 이전은 불가합니다.</li>
                                    <li>3. 아래의 경우는 '계좌이체'로 보지 않으므로 세제상 혜택을 받을 수 없습니다.<br>1) 2013.3.1. 이후 가입한 개인형IRP계좌에서 2013.3.1. 이전에 가입한 개인형IRP계좌로 이체되는 경우<br>2) 개인형IRP계좌에 있는 일부 금액이 이체되는 경우</li>
                                    <li>4. 개인형IRP계좌 상호간의 이체 또는 개인형IRP계좌-연금저축계좌간 이체시에는 이체받을 금융회사(신규 가입 금융회사)만 방문하시면 됩니다. </li>
                                </ol>

                                <ul style="margin-bottom:10px;">
                                    <li><span style="font-weight:600;font-size:16px;">계좌이체가 가능한 경우</span></li>
                                </ul>
                                <ol style="margin-bottom:10px;">
                                    <li>1. 개인형IRP계좌(2013.3.1. 이후 가입계좌)에서 타 개인형IRP계좌(2013.3.1. 이후 가입계좌)로의 전액이체</li>
                                    <li>2. 개인형IRP계좌(2013.3.1. 이전 가입계좌)에서 타 개인형IRP계좌(2013.3.1. 이후 가입계좌)로의 전액이체</li>
                                    <li>3. 연령이 만 55세 이상이면서 연금계좌 불입기간이 5년을 경과하거나, 이연퇴직소득이 있고 만 55세 이상인 연금저축계좌 가입자가 개인형IRP로 전액이체(연금수령이 개시된 경우에도 가능함)</li>
                                    <li>4. 연령이 만 55세 이상이면서 연금계좌 불입기간이 5년을 경과하거나, 이연퇴직소득이 있고 만 55세 이상인 개인형IRP 가입자가 연금저축계좌로 전액이체(연금수령이 개시된 경우에도 가능함)</li>
                                </ol>
                                <p class="txt-gray" style="margin-bottom:20px;">※ 기타 적립금 운용현황에 대해 궁금한 사항은 ☏1600-4000로 문의하시기 바랍니다.</p>
                                <ul class="list-dot" style="margin-bottom:0;">
                                    <li>'통합연금포털(https://100lifeplan.fss.or.kr)'의 '퇴직연금 통합공시' 메뉴를 통해 퇴직연금사업자별 퇴직연금 수익률, 총비용부담률 등을 비교해 보실 수 있습니다.</li>
                                </ul>
                            </div>
                            <!-- //기타사항 -->
                        </div>
                    </div>
                    <!-- //본문정보 -->
                    
                </div>
            </section>

            <footer>
                <dl >
                    <dt class="footer-logo">광주</dt>
                    <dd>
                        <address>㈜광주은행, 광주광역시 동구 제봉로 225</address>
                        대표전화 <a href="tel:062-239-5000" style="color:#0070D2;text-decoration:underline;">062-239-5000</a> | 고객센터 <a href="tel:1600-4000" style="color:#0070D2;text-decoration:underline;">1600-4000</a>, <a href="tel:1588-3388" style="color:#0070D2;text-decoration:underline;">1588-3388</a> 
                    </dd>
                </dl>
                <p>Copyright 2021 KWANGJU BANK</p>
            </footer>
        </div>
    </div>
</body>
</html>