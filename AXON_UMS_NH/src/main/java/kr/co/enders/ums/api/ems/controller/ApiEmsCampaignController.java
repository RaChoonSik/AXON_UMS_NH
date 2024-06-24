/**
 * 작성자 : 김준희
 * 작성일시 : 2024.03.05
 * 설명 : EMS API Controller
 */
package kr.co.enders.ums.api.ems.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter; 
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List; 
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.enders.ums.com.vo.DownloadVO;
import kr.co.enders.ums.ems.cam.service.CampaignService; 
import kr.co.enders.ums.ems.cam.vo.TaskVO; 
import kr.co.enders.util.Code; 
import kr.co.enders.util.PropertiesUtil;
import kr.co.enders.util.StringUtil;

@Controller
@RequestMapping("/api/ems")
public class ApiEmsCampaignController {
	private Logger logger = Logger.getLogger(this.getClass());
 
	@Autowired
	private CampaignService campaignService;

	
	@Autowired
	private PropertiesUtil properties;
	
	/****************************** 수신거부 API ******************************/
	//@SuppressWarnings("unchecked")
	@RequestMapping(value="/receiveDenyJson")
	private void goReceiveDenyJson(@RequestParam String userId , Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		logger.debug("ApiEmsCampaignController receiveDenyJson  userid  = " + userId); 
		  
		JSONArray agreArr = new JSONArray();
		JSONObject agreList = new JSONObject();
		JSONObject data = new JSONObject();
		
		String strReturn  = null;
		String strReturnMessage = null;
		
		if(userId != null  && !"".equals(userId)) { 
			
			data.put("REQ_TYPE", properties.getProperty("DENY.REQ_TYPE"));
			data.put("SEND_DT", StringUtil.getDate(Code.TM_YMD));
			data.put("SEND_TIME", StringUtil.getDate(Code.TM_HMS));
			data.put("TR_NO", StringUtil.getDate(Code.TM_YMDHMS));
			data.put("TR_DT", StringUtil.getDate(Code.TM_YMD));
			data.put("TR_TIME", StringUtil.getDate(Code.TM_YMDHMSM));
			data.put("CLIENT_ID", properties.getProperty("DENY.CLIENT_ID"));
			data.put("CHNL_CD", properties.getProperty("DENY.CHNL_CD"));
			data.put("PGM_NO", properties.getProperty("DENY.PGM_NO"));
			data.put("MBR_NO",userId);
			 
			
			agreList.put("AGRE_TYPE", properties.getProperty("DENY.AGRE_TYPE"));
			agreList.put("AGRE_YN", properties.getProperty("DENY.AGRE_YN"));
			agreList.put("AGRE_DT", StringUtil.getDate(Code.TM_YMDHMS));

			agreArr.put(agreList);
			
			data.put("CUST_AGRE_OBJ", agreArr);
			String reqData = data.toString();
			
			logger.debug("goReceiveDeny Api Request Send Data: " + reqData);
			
			String sendString = "";
			String apiUrl = properties.getProperty("DENY.REQ_URL"); //API URL 입력
			
			try {
				sendString = reqData; 
				
				int TIMEOUT_VALUE = 1000;   // 1초
				 	
				try {
					URL object = new URL(apiUrl);
					HttpURLConnection con = null;
					con = (HttpURLConnection) object.openConnection();
					con.setConnectTimeout(TIMEOUT_VALUE);
					con.setReadTimeout(TIMEOUT_VALUE);
					con.setDoOutput(true);
					con.setDoInput(true);					
					con.setRequestProperty("Content-Type", "application/json");
					con.setRequestProperty("Accept", "*/*");
					con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
					con.setRequestMethod("POST");
					OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
					wr.write(sendString);
					wr.flush();
	
					StringBuilder sb = new StringBuilder();
					
					int HttpResult = con.getResponseCode();
					logger.debug("goReceiveDeny http Status Code : "+ HttpResult);
	
					if (HttpResult == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
						String line = null;
						while ((line = br.readLine()) != null) {
							sb.append(line + "\n");
						}
						strReturn = sb.toString();
						logger.debug("goReceiveDeny API request result :  " + strReturn);
						br.close();
						try {
							JSONObject objDenyResult = new JSONObject(strReturn);
							JSONArray objDenyResultHeader = (JSONArray) objDenyResult.get("HEAD_DATA"); 
							JSONObject denyResultHeader = objDenyResultHeader.getJSONObject(0); 
							if ("S".equals(denyResultHeader.get("RST"))) {
								strReturn = "Success";
								strReturnMessage = denyResultHeader.get("RST_MSG").toString();
							} else {
								strReturn = "fail";
								strReturnMessage = denyResultHeader.get("RST_MSG").toString();
							}
						} catch (Exception e) {
							strReturn = "exception";
							strReturnMessage ="시스템 오류가 발생했습니다 다시 시도해주세요";
							logger.error("goReceiveDeny RST Check Error : " + e.getMessage());
						}
						strReturn = "Success";
					} else {
						logger.error("goReceiveDeny HttpURLConnection Fail : " + con.getResponseMessage());
						strReturn ="fail"; 
						strReturnMessage ="수신거부 요청 서버에 접속할 수 없습니다 다시 시도해주세요";
					}
				} catch (Exception e) { 
					logger.error("goReceiveDeny Api Request Exception : " + e.getMessage());
					strReturn = "exception";
					strReturnMessage ="시스템 오류가 발생했습니다 다시 시도해주세요";
				}
				
			} catch (Exception e) {
				strReturn = "exception";
				strReturnMessage ="시스템 오류가 발생했습니다 다시 시도해주세요";
				logger.error("goReceiveDeny Exception :" + e.getMessage());
			} 
		} else {
			strReturn = "fail";
			strReturnMessage ="수신거부 요청 정보가 누락되었습니다(사용자ID)";
		}
		
		logger.error("goReceiveDeny 결과 :" + strReturn + " / 결과 메시지 : " + strReturnMessage );
		
		try {
			sendResultJson(response, strReturn, strReturnMessage );
		} catch (Exception e) {
			logger.error("goReceiveDeny sendResultJson :" + e.getMessage() );
		}
	}
	
	/****************************** 수신거부 API alert 메시지 테스트 ******************************/
	//@SuppressWarnings("unchecked")
	@RequestMapping(value="/receiveDeny")
	private String goReceiveDeny(@RequestParam String userId , Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		logger.debug("ApiEmsCampaignController receiveDeny  userid  = " + userId); 
		  
		JSONArray agreArr = new JSONArray();
		JSONObject agreList = new JSONObject();
		JSONObject data = new JSONObject();
		
		String strApiReturnMsg  = "";
		String strReturn  = "";
		String strReturnMessage = null;
		
		if(userId != null  && !"".equals(userId)) { 
			
			data.put("REQ_TYPE", properties.getProperty("DENY.REQ_TYPE"));
			data.put("SEND_DT", StringUtil.getDate(Code.TM_YMD));
			data.put("SEND_TIME", StringUtil.getDate(Code.TM_HMS));
			data.put("TR_NO", StringUtil.getDate(Code.TM_YMDHMS));
			data.put("TR_DT", StringUtil.getDate(Code.TM_YMD));
			data.put("TR_TIME", StringUtil.getDate(Code.TM_YMDHMSM));
			data.put("CLIENT_ID", properties.getProperty("DENY.CLIENT_ID"));
			data.put("CHNL_CD", properties.getProperty("DENY.CHNL_CD"));
			data.put("PGM_NO", properties.getProperty("DENY.PGM_NO"));
			data.put("MBR_NO",userId);
			 
			
			agreList.put("AGRE_TYPE", properties.getProperty("DENY.AGRE_TYPE"));
			agreList.put("AGRE_YN", properties.getProperty("DENY.AGRE_YN"));
			agreList.put("AGRE_DT", StringUtil.getDate(Code.TM_YMDHMS));

			agreArr.put(agreList);
			
			data.put("CUST_AGRE_OBJ", agreArr);
			String reqData = data.toString().trim();
			
			logger.debug("goReceiveDeny Api Request Send Data:" + reqData +"");
			
			String sendString = "";
			String apiUrl = properties.getProperty("DENY.REQ_URL"); //API URL 입력
			
			try {
				sendString = reqData; 
				
				int TIMEOUT_VALUE = 1000;   // 1초
				 	
				try {
					URL object = new URL(apiUrl);
					HttpURLConnection con = null;
					con = (HttpURLConnection) object.openConnection();
					con.setConnectTimeout(TIMEOUT_VALUE);
					con.setReadTimeout(TIMEOUT_VALUE);
					con.setDoOutput(true);
					con.setDoInput(true);					
					con.setRequestProperty("Content-Type", "application/json");
					con.setRequestProperty("Accept", "*/*");
					con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
					con.setRequestMethod("POST");
					OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
					wr.write(sendString);
					wr.flush();
	
					StringBuilder sb = new StringBuilder();
					
					int HttpResult = con.getResponseCode();
					logger.debug("goReceiveDeny http Status Code : "+ HttpResult);
	
					if (HttpResult == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
						String line = null;
						while ((line = br.readLine()) != null) {
							sb.append(line + "\n");
						} 
						strApiReturnMsg = sb.toString();
						logger.debug("goReceiveDeny API request result :  " + strApiReturnMsg);
						br.close();
						try {
							JSONObject objDenyResult = new JSONObject(strApiReturnMsg);
							JSONArray objDenyResultHeader = (JSONArray) objDenyResult.get("HEAD_DATA"); 
							JSONObject denyResultHeader = objDenyResultHeader.getJSONObject(0); 
							if ("S".equals(denyResultHeader.get("RST"))) {
								strReturn = "Success";
								strReturnMessage = denyResultHeader.get("RST_MSG").toString();
							} else {
								strReturn = "Fail";
								strReturnMessage = denyResultHeader.get("RST_MSG").toString();
							}
						} catch (Exception e) {
							strReturn = "Exception";
							strReturnMessage ="시스템 오류가 발생했습니다 다시 시도해주세요";
							logger.error("goReceiveDeny RST Check Error : " + e.getMessage());
						}
					} else {
						logger.error("goReceiveDeny HttpURLConnection Fail : " + con.getResponseMessage());
						strReturn ="Fail"; 
						strReturnMessage ="수신거부 요청 서버에 접속할 수 없습니다 다시 시도해주세요";
					}
				} catch (Exception e) { 
					logger.error("goReceiveDeny Api Request Exception : " + e.getMessage());
					strReturn = "Exception";
					strReturnMessage ="시스템 오류가 발생했습니다 다시 시도해주세요";
				}
				
			} catch (Exception e) {
				strReturn = "Exception";
				strReturnMessage ="시스템 오류가 발생했습니다 다시 시도해주세요";
				logger.error("goReceiveDeny Exception :" + e.getMessage());
			} 
		} else {
			strReturn = "Fail";
			strReturnMessage ="수신거부 요청 정보가 누락되었습니다(사용자ID)";
		}
		
		logger.error("goReceiveDeny 결과 :" + strReturn + " / 결과 메시지 : " + strReturnMessage );
		
		String retUrl = ""; 
		
		if("Success".equals(strReturn) ) {
			retUrl = "redirect:/api/ems/receiveDenyS.ums" ;
		} else {
			retUrl = "redirect:/api/ems/receiveDenyF.ums" ;
		} 
			 
		return retUrl;
	}
	
	//@SuppressWarnings("unchecked")
	@RequestMapping(value="/receiveDenyTest")
	private String goReceiveDenyTest(@RequestParam String userId , Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		logger.debug("/api/ems/goReceiveDenyTest  userid  = " + userId); 
		  
	 
		String retUrl = "";  
		if("happyjune".equals(userId) ) { 
			retUrl = "redirect:/api/ems/receiveDenyS.ums" ;
		} else {
			retUrl = "redirect:/api/ems/receiveDenyF.ums" ;
		} 
		return retUrl;
	}
	
	/****************************** 발송 상태 요청 API ******************************/
	@RequestMapping(value="/mailWorkStatus")
	private void getMailWorkStatus(@RequestParam  String  requestKey , Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		logger.debug("ApiEmsCampaignController getMailWorkStatus requestKey  = " + requestKey); 
		
		//요청키 기준으로 현재 정보를 가져와서 넘김
		String strReturn  = null;
		String strReturnMessage = null;
		
		if(requestKey != null  && !"".equals(requestKey)) { 
			// 사용자 아이디, 비밀번호 체크
			try {
				List<TaskVO> mailList = null;
				mailList = campaignService.getMailWorkStatus(requestKey);
				if(mailList != null && mailList.size() > 0) {
					strReturn = mailList.get(0).getWorkStatus();
					strReturnMessage = mailList.get(0).getWorkStatusNm(); 
				} else {
					strReturn = "None";
					strReturnMessage = "발송내역없음 요청키 :[" + requestKey + "]";
				}
			} catch(Exception e) {
				strReturn = "Erorr";
				strReturnMessage = "시스템 오류 발생 요청키 :[" + requestKey + "]";
				logger.error("getMailWorkStatus Error = " + e);
			}
		} else {
			strReturn = "Fail";
			strReturnMessage ="메일 상태 요청 정보가 누락되었습니다(requestKey)";
		}
		
		logger.error(" getMailWorkStatus 결과 :" + strReturn + " / 결과 메시지 : " + strReturnMessage );
		
		try {
			sendResultJson(response, strReturn, strReturnMessage );
		} catch (Exception e) {
			logger.error(" getMailWorkStatus sendResultJson :" + e.getMessage() );
		}
		
	}
	
	public static void sendResultJson(HttpServletResponse response, String sResultCode, String sResultMessage) throws Exception {
		
		PrintWriter writer;
		String returnValue = "{ \"resultCode\":\"%s\", \"resultMessage\":\"%s\" }";
		
		if (sResultCode == null){
			sResultCode = "9999";
		}
		
		response.setContentType("text/plain; charset=UTF-8");
		
		writer = response.getWriter();
		writer.write(String.format(returnValue, sResultCode, sResultMessage));
		
		writer.flush();
		writer.close();
		
	}
	
	/**
	 * 수신권한 처리 
	 * @return
	 */
	@RequestMapping(value="/receiveDenyS")
	public String getReceiveDenySuccess() {
		return "api/ems/receiveDenyS";
	}
	
	/**
	 * 수신권한 처리 
	 * @return
	 */
	@RequestMapping(value="/receiveDenyF")
	public String getReceiveDenyFail() {
		return "api/ems/receiveDenyF";
	} 
 
}
