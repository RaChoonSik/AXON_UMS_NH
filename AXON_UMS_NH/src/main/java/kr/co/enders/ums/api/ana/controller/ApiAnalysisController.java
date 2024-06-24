/**
 * 작성자 : 김준희
 * 작성일시 : 2024.04.19
 * 설명 : 통계/분석 Controller
 */ 
package kr.co.enders.ums.api.ana.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.enders.ums.com.service.CodeService;
import kr.co.enders.ums.com.service.CryptoService;
import kr.co.enders.ums.com.vo.CodeVO;
import kr.co.enders.ums.ems.ana.service.AnalysisService;
import kr.co.enders.ums.ems.ana.vo.MailDomainVO;
import kr.co.enders.ums.ems.ana.vo.MailErrorVO;
import kr.co.enders.ums.ems.ana.vo.MailSummVO;
import kr.co.enders.ums.ems.ana.vo.PeriodSummVO;
import kr.co.enders.ums.ems.ana.vo.RespLogVO;
import kr.co.enders.ums.ems.ana.vo.SendLogVO;
import kr.co.enders.ums.ems.cam.service.CampaignService;
import kr.co.enders.ums.ems.cam.vo.CampaignVO;
import kr.co.enders.ums.ems.cam.vo.TaskVO;
import kr.co.enders.ums.ems.seg.service.SegmentService;
import kr.co.enders.ums.ems.seg.vo.SegmentVO;
import kr.co.enders.ums.rns.ana.service.RnsAnalysisService;
import kr.co.enders.ums.rns.ana.vo.RnsAnaMonthVO;
import kr.co.enders.ums.rns.ana.vo.RnsAnaServiceVO;
import kr.co.enders.ums.rns.svc.service.RnsServiceService;
import kr.co.enders.ums.rns.svc.vo.RnsRecipientInfoVO;
import kr.co.enders.ums.rns.svc.vo.RnsServiceVO;
import kr.co.enders.ums.sys.log.service.SystemLogService;
import kr.co.enders.ums.sys.log.vo.ActionLogVO;
import kr.co.enders.util.Code;
import kr.co.enders.util.PageUtil;
import kr.co.enders.util.PropertiesUtil;
import kr.co.enders.util.StringUtil;

@Controller
@RequestMapping("/api/ana")
public class ApiAnalysisController {
	private Logger logger = Logger.getLogger(this.getClass());
	 

	@Autowired
	private AnalysisService analysisService;


	@RequestMapping(value="/sendResultTsEmail")
	private void getSendResultTsEmail(@RequestParam  String  requestKey , Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		logger.debug("ApiAnalysisController getSendResultTsEmail requestKey  = " + requestKey); 
		
		//요청키 기준으로 현재 정보를 가져와서 넘김
		String strReturn  = null;
		String strReturnMessage = null;
		String rcode = "";
		String rcodeNm = "";
		
		if(requestKey != null  && !"".equals(requestKey)) { 

			try {
				List<SendLogVO> mailList = null;
				mailList = analysisService.getSendResultRnsByKey(requestKey);
				if(mailList != null && mailList.size() > 0) {
					rcode = mailList.get(0).getSendRcode();
					rcodeNm = mailList.get(0).getSendRcodeNm(); 
					strReturn = "Success";
					strReturnMessage = "";
				} else {
					strReturn = "None";
					strReturnMessage = "발송결과없음 요청키 :[" + requestKey + "]";
				}
			} catch(Exception e) {
				strReturn = "Erorr";
				strReturnMessage = "시스템 오류 발생 요청키 :[" + requestKey + "]";
				logger.error("getSendResultTsEmail Error = " + e);
			}
		} else {
			strReturn = "Fail";
			strReturnMessage ="메일 발송결과 정보가 누락되었습니다(requestKey)";
		}
		
		logger.error(" getSendResultTsEmail 결과 :" + strReturn + " / 결과 메시지 : " + strReturnMessage );
		
		try {
			sendApiResultJson(response, requestKey,  rcode, rcodeNm, strReturn, strReturnMessage ,"S");
		} catch (Exception e) {
			logger.error(" getSendResultTsEmail sendApiResultJson :" + e.getMessage() );
		}
		
	}
	
	@RequestMapping(value="/mailOpenResult")
	private void getMailOpenResult(@RequestParam  String  requestKey , Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		logger.debug("ApiAnalysisController getMailOpenResult requestKey  = " + requestKey); 
		
		//요청키 기준으로 현재 정보를 가져와서 넘김
		String strReturn  = null;
		String strReturnMessage = null;
		String receiveYn = "";
		String receiveDate = "";
		
		if(requestKey != null  && !"".equals(requestKey)) { 

			try {
				List<SendLogVO> mailList = null;
				mailList = analysisService.getSendResultRnsByKey(requestKey);
				if(mailList != null && mailList.size() > 0) {
					
					receiveDate = mailList.get(0).getOpenDt(); 
					if(!"".equals(receiveDate)) {
						receiveYn ="Y";
					} else {
						receiveYn ="N";
					}
					
					strReturn = "Success";
					strReturnMessage = "";
				} else {
					strReturn = "None";
					strReturnMessage = "수신확인 결과없음 요청키 :[" + requestKey + "]";
				}
			} catch(Exception e) {
				strReturn = "Erorr";
				strReturnMessage = "시스템 오류 발생 요청키 :[" + requestKey + "]";
				logger.error("getMailOpenResult Error = " + e);
			}
		} else {
			strReturn = "Fail";
			strReturnMessage ="수신확인 결과 요청 정보가 누락되었습니다(requestKey)";
		}
		
		logger.error(" getMailOpenResult 결과 :" + strReturn + " / 결과 메시지 : " + strReturnMessage );
		
		try {
			sendApiResultJson(response, requestKey,  receiveYn, receiveDate, strReturn, strReturnMessage, "R");
		} catch (Exception e) {
			logger.error(" getMailOpenResult sendApiResultJson :" + e.getMessage() );
		}
		
	}
	
	public static void sendApiResultJson(HttpServletResponse response, String sRequestkey, String rcode, String rcodeNm, String sResultcode, String sResultmessage, String type) throws Exception {
		
		PrintWriter writer;
		String returnValue = ""; 
		
		if("S".equals(type)) {
			returnValue = "{ \"requestkey\":\"%s\", \"sResultcode\":\"%s\", \"sResultmessage\":\"%s\", \"rcode\":\"%s\", \"rcodeNm\":\"%s\"}";
		} else {
			returnValue = "{ \"requestkey\":\"%s\", \"sResultcode\":\"%s\", \"sResultmessage\":\"%s\", \"mailopenyn\":\"%s\", \"mailopendt\":\"%s\"}";
		}
		 
		
		if (sResultcode == null){
			sResultcode = "9999";
		}
		if ("000".equals(sResultcode)) {
			sResultmessage ="Success";
		}
		
		response.setContentType("text/plain; charset=UTF-8");
		writer = response.getWriter();
		
		writer.write(String.format(returnValue, sRequestkey, sResultcode, sResultmessage, rcode, rcodeNm));
		
		writer.flush();
		writer.close();
	}
	 
}
