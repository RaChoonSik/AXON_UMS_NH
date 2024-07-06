/**
 * 작성자 : 김준희
 * 작성일시 : 2024.06.06
 * 설명 : Custom API Controller
 */
package kr.co.enders.ums.api.cust.controller;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping; 

import kr.co.enders.ums.com.vo.DownloadVO;
import kr.co.enders.ums.ems.ana.service.AnalysisService;
import kr.co.enders.ums.ems.ana.vo.UmsFaxSendVO;
import kr.co.enders.util.PropertiesUtil;
import kr.co.enders.util.StringUtil;
 
@Controller
@RequestMapping("/api/cust")
public class ApiFileController {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AnalysisService analysisService;
	
	@Autowired
	private PropertiesUtil properties;
	 
	/**
	 * 파일을 다운로드 한다.
	 * @param downloadVO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/down")
	public void fileDownloadPath(@ModelAttribute DownloadVO downloadVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Api fileDownload attachPath = " + downloadVO.getAttachPath());
		
		//요청 필수 정보 있는지 확인 
		
		String fileName = "";
		String filePath = "";
		
		logger.debug("fileDownload attachNm   = " + downloadVO.getAttachPath()); 
		
		fileName = downloadVO.getAttachPath();
		if ("".equals(fileName)) {
			logger.error("fileDownload request info  missing");
			sendApiResultJson(response, downloadVO.getAttachPath(), "E002", "다운로드정보가 누락되었습니다");
		} else {
			filePath = properties.getProperty("FILE.FAX_PATH") + "/" + downloadVO.getAttachPath();
			
			logger.debug("fileDownload fileName = " + fileName);
			logger.debug("fileDownload filePath = " + filePath);
			
			File downTargetFile = new File(filePath);
			
			//파일이 있는지 확인 
			if (!downTargetFile.exists()) {
				logger.error("fileDownload filePath = " + filePath + " not exist");
				sendApiResultJson(response, fileName, "E003", "파일이 존재 하지 않습니다");
			}  else {
				byte fileBytes[] = FileUtils.readFileToByteArray(downTargetFile);
				
				response.setContentType("application/octet-stream");
				response.setContentLength(fileBytes.length);
				response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8") + "\";");
				response.setHeader("Content-Trasfer-Encoding", "binary");
				response.getOutputStream().write(fileBytes);
				
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}
	
	/**
	 * 파일을 다운로드 한다.
	 * @param downloadVO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downSeq")
	public void fileDownloadSeq(@ModelAttribute DownloadVO downloadVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Api fileDownloadSeq downType= " + downloadVO.getDownType());
		logger.debug("Api fileDownloadSeq requestSeq = " + downloadVO.getRequestSeq());
		logger.debug("Api fileDownloadSeq requestKey= " + downloadVO.getRequestKey());
		
		//요청 필수 정보 있는지 확인 
		
		//다운로드 타입 확인 
		if(StringUtil.isNull(downloadVO.getDownType()) || !"090".equals(downloadVO.getDownType())) {
			logger.error("fileDownload downType 정보가 090 이 아닙니다");
			sendApiResultJson(response, downloadVO.getDownType(), "E001", "다운로드타입이 맞지 않습니다");
			return;
		} 
		
		//요청정보에 맞는 데이터 있는지 확인 
		
		if(StringUtil.isNull(downloadVO.getRequestKey()) || StringUtil.isNull(downloadVO.getRequestSeq())) {
			logger.error("fileDownload request info  missing");
			sendApiResultJson(response, downloadVO.getRequestKey() + "_" + downloadVO.getRequestSeq() , "E002", "다운로드정보가 누락되었습니다");
			return;
		} 
		
		if ( StringUtil.getStringToInt(downloadVO.getRequestKey()) == 0 || StringUtil.getStringToInt(downloadVO.getRequestSeq()) == 0) {
			logger.error("fileDownload request info  missing");
			sendApiResultJson(response, downloadVO.getRequestKey() + "_" + downloadVO.getRequestSeq() , "E002", "다운로드정보가 누락되었습니다");
			return;
		}
		
		int umsFaxSendSeq = StringUtil.getStringToInt(downloadVO.getRequestKey());
		int umsFaxAttachSeq = StringUtil.getStringToInt(downloadVO.getRequestSeq());
		
		if(umsFaxAttachSeq > 3 ) {
			logger.error("fileDownload request info  missing");
			sendApiResultJson(response, downloadVO.getRequestKey() + "_" + downloadVO.getRequestSeq() , "E002", "다운로드정보가 누락되었습니다");
			return;
		}
		
		//찾으려는 파일의 정보가 있는 지 확인 
		UmsFaxSendVO searchUmsFaxSendVO = new UmsFaxSendVO();
		searchUmsFaxSendVO.setSearchUmsFaxSeq(umsFaxSendSeq);
				
		UmsFaxSendVO umsFaxSendVO = analysisService.getUmsFaxInfo(searchUmsFaxSendVO);
		
		if(umsFaxSendVO == null) {
			logger.error("fileDownload attach file info not exist");
			sendApiResultJson(response, downloadVO.getRequestKey() + "_" + downloadVO.getRequestSeq() , "E004", "파일정보가 없습니다");
			return;
		}
		
		String fileName = "";
		String filePath = "";
		
		switch(umsFaxAttachSeq) {
		case 1:
			fileName = umsFaxSendVO.getAttach01Name();
			filePath = umsFaxSendVO.getAttach01();
			break;
		case 2:
			fileName = umsFaxSendVO.getAttach02Name();
			filePath = umsFaxSendVO.getAttach02();
			break;
		case 3:
			fileName = umsFaxSendVO.getAttach03Name();
			filePath = umsFaxSendVO.getAttach03();
			break;
		}
		
		if("".equals(fileName) || "".equals(filePath)) {
			logger.error("fileDownload attach file info not exist");
			sendApiResultJson(response, downloadVO.getRequestKey() + "_" + downloadVO.getRequestSeq() , "E004", "파일정보가 없습니다");
			return;
		} 
		 
		filePath = properties.getProperty("FILE.FAX_PATH") + "/" + filePath;
		
		
		logger.debug("fileDownload fileName = " + fileName);
		logger.debug("fileDownload filePath = " + filePath);
		
		File downTargetFile = new File(filePath);
		
		if (!downTargetFile.exists()) {
			logger.error("fileDownload target file  missing");
			sendApiResultJson(response, fileName, "E003", "파일이 존재 하지 않습니다");
			return;
		}  else {
			byte fileBytes[] = FileUtils.readFileToByteArray(downTargetFile);
			
			response.setContentType("application/octet-stream");
			response.setContentLength(fileBytes.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8") + "\";");
			response.setHeader("Content-Trasfer-Encoding", "binary");
			response.getOutputStream().write(fileBytes);
			
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
	
	/**
	 * 파일을 다운로드 한다.
	 * @param downloadVO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downSeqV")
	public ResponseEntity<InputStreamResource> fileDownloadSeqV(@ModelAttribute DownloadVO downloadVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Api fileDownloadSeqV downType= " + downloadVO.getDownType());
		logger.debug("Api fileDownloadSeqV requestSeq = " + downloadVO.getRequestSeq());
		logger.debug("Api fileDownloadSeqV requestKey= " + downloadVO.getRequestKey());
		
		//요청 필수 정보 있는지 확인 
		
		//다운로드 타입 확인 
		if(StringUtil.isNull(downloadVO.getDownType()) || !"009".equals(downloadVO.getDownType())) {
			logger.error("fileDownload downType 정보가 009 이 아닙니다");
			sendApiResultJson(response, downloadVO.getDownType(), "E001", "다운로드타입이 맞지 않습니다");
			return null;
		} 
		
		//요청정보에 맞는 데이터 있는지 확인 
		
		if(StringUtil.isNull(downloadVO.getRequestKey()) || StringUtil.isNull(downloadVO.getRequestSeq())) {
			logger.error("fileDownload request info  missing");
			sendApiResultJson(response, downloadVO.getRequestKey() + "_" + downloadVO.getRequestSeq() , "E002", "다운로드정보가 누락되었습니다");
			return null;
		} 
		
		if ( StringUtil.getStringToInt(downloadVO.getRequestKey()) == 0 || StringUtil.getStringToInt(downloadVO.getRequestSeq()) == 0) {
			logger.error("fileDownload request info  missing");
			sendApiResultJson(response, downloadVO.getRequestKey() + "_" + downloadVO.getRequestSeq() , "E002", "다운로드정보가 누락되었습니다");
			return null;
		}
		
		int umsFaxSendSeq = StringUtil.getStringToInt(downloadVO.getRequestKey());
		int umsFaxAttachSeq = StringUtil.getStringToInt(downloadVO.getRequestSeq());
		
		if(umsFaxAttachSeq > 3 ) {
			logger.error("fileDownload request info  missing");
			sendApiResultJson(response, downloadVO.getRequestKey() + "_" + downloadVO.getRequestSeq() , "E002", "다운로드정보가 누락되었습니다");
			return null;
		}
		
		//찾으려는 파일의 정보가 있는 지 확인 
		UmsFaxSendVO searchUmsFaxSendVO = new UmsFaxSendVO();
		searchUmsFaxSendVO.setSearchUmsFaxSeq(umsFaxSendSeq);
				
		UmsFaxSendVO umsFaxSendVO = analysisService.getUmsFaxInfo(searchUmsFaxSendVO);
		
		if(umsFaxSendVO == null) {
			logger.error("fileDownload attach file info not exist");
			sendApiResultJson(response, downloadVO.getRequestKey() + "_" + downloadVO.getRequestSeq() , "E004", "파일정보가 없습니다");
			return null;
		}
		
		String fileName = "";
		String filePath = "";
		
		switch(umsFaxAttachSeq) {
		case 1:
			fileName = umsFaxSendVO.getAttach01Name();
			filePath = umsFaxSendVO.getAttach01();
			break;
		case 2:
			fileName = umsFaxSendVO.getAttach02Name();
			filePath = umsFaxSendVO.getAttach02();
			break;
		case 3:
			fileName = umsFaxSendVO.getAttach03Name();
			filePath = umsFaxSendVO.getAttach03();
			break;
		}
		
		if("".equals(fileName) || "".equals(filePath)) {
			logger.error("fileDownload attach file info not exist");
			sendApiResultJson(response, downloadVO.getRequestKey() + "_" + downloadVO.getRequestSeq() , "E004", "파일정보가 없습니다");
			return null;
		} 
		 
		filePath = properties.getProperty("FILE.FAX_PATH") + "/" + filePath;
		
		
		logger.debug("fileDownload fileName = " + fileName);
		logger.debug("fileDownload filePath = " + filePath);
		
		File downTargetFile = new File(filePath);
		
		if (!downTargetFile.exists()) {
			logger.error("fileDownload target file  missing");
			sendApiResultJson(response, fileName, "E003", "파일이 존재 하지 않습니다");
			return null;
		}  else {
			byte fileBytes[] = FileUtils.readFileToByteArray(downTargetFile);
	 
			
			File file = new File(filePath);
			String fullPathFileName = file.getName();
			// 파일 확장자 
			String ext = fullPathFileName.substring(fileName.lastIndexOf(".") + 1);
			HttpHeaders header = new HttpHeaders();
			Path fPath = Paths.get(file.getAbsolutePath());
			
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0"); 
			
			InputStreamResource resource3 = new InputStreamResource(new FileInputStream(file));
			
			return ResponseEntity.ok()
				.headers(header)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource3);
			
		} 
	}
	
	//다운로드 타입 정보가 없음 E001
	//다운로드 정보가 누락됨  E002
	//파일이 없음		 E003 
	//파일정보가 없음    E004
	
	public static void sendApiResultJson(HttpServletResponse response, String sRequestkey, String sResultcode, String sResultmessage) throws Exception {
		
		PrintWriter writer;
		String returnValue = ""; 
		
		returnValue = "{ \"filePath\":\"%s\", \"resultcode\":\"%s\", \"resultmessage\":\"%s\"}"; 
		
 
		
		response.setContentType("text/plain; charset=UTF-8");
		writer = response.getWriter();
		
		writer.write(String.format(returnValue, sRequestkey, sResultcode, sResultmessage));
		
		writer.flush();
		writer.close();
	}
}
