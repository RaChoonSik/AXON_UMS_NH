/**
 * 작성자 : 김준희
 * 작성일시 : 2021.08.21
 * 설명 : RNS 템플릿관리 Controller
 */
package kr.co.enders.ums.rns.tmp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream; 
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.enders.ums.com.service.CodeService;
import kr.co.enders.ums.com.vo.CodeVO;
import kr.co.enders.ums.rns.tmp.service.RnsTemplateService;
import kr.co.enders.ums.rns.tmp.vo.RnsTemplateVO;
import kr.co.enders.util.Code;
import kr.co.enders.util.PageUtil;
import kr.co.enders.util.PropertiesUtil;
import kr.co.enders.util.StringUtil;
import kr.co.enders.util.CrossScriptingFilter;

@Controller
@RequestMapping("/rns/tmp")
public class RnsTemplateController {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private PropertiesUtil properties;
	
	@Autowired
	private RnsTemplateService rnsTemplateService;
	
	/**
	 * 템플릿 목록 조회화면을 출력한다.
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/rnsTempListP")
	public String goRnsTemplateListP(@ModelAttribute RnsTemplateVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goRnsTemplateListP searchTempNm  = " + searchVO.getSearchTempNm());
		logger.debug("goRnsTemplateListP searchStatus  = " + searchVO.getSearchStatus());
		logger.debug("goRnsTemplateListP searchStartDt = " + searchVO.getSearchStartDt());
		logger.debug("goRnsTemplateListP searchEndDt   = " + searchVO.getSearchEndDt());
		logger.debug("goRnsTemplateListP searchDeptNo  = " + searchVO.getSearchDeptNo());
		logger.debug("goRnsTemplateListP searchUserId  = " + searchVO.getSearchUserId());
		
		// 검색 기본값 설정
		if(StringUtil.isNull(searchVO.getSearchStartDt())) {
			searchVO.setSearchStartDt(StringUtil.getCalcDateFromCurr(-1, "M", "yyyyMMdd"));
		} else {
			searchVO.setSearchStartDt(searchVO.getSearchStartDt().replaceAll("\\.", ""));
		}
		if(StringUtil.isNull(searchVO.getSearchEndDt())) {
			searchVO.setSearchEndDt(StringUtil.getCalcDateFromCurr(0, "D", "yyyyMMdd"));
		} else {
			searchVO.setSearchEndDt(searchVO.getSearchEndDt().replaceAll("\\.", ""));
		}
		if(StringUtil.isNull(searchVO.getSearchChannel())) searchVO.setSearchChannel("000");
		if(StringUtil.isNull(searchVO.getSearchStatus())) searchVO.setSearchStatus("ALL");
		if(searchVO.getSearchDeptNo() == 0) {
			if("Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
				searchVO.setSearchDeptNo(0);
			} else {
				searchVO.setSearchDeptNo((int)session.getAttribute("NEO_DEPT_NO"));
			}
		}
		searchVO.setUilang((String)session.getAttribute("NEO_UILANG"));
		searchVO.setAdminYn((String)session.getAttribute("NEO_ADMIN_YN"));
		
		// 부서목록(코드성) 조회
		CodeVO dept = new CodeVO();
		dept.setStatus("000"); // 정상
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(dept);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		// 사용자 목록 조회
		CodeVO user = new CodeVO();
		user.setDeptNo(searchVO.getSearchDeptNo());
		user.setStatus("000");
		List<CodeVO> userList = null;
		try {
			userList = codeService.getUserList(user);
		} catch(Exception e) {
			logger.error("codeService.getUserList error = " + e);
		}
		
		// 템플릿상태 목록
		CodeVO status = new CodeVO();
		status.setUilang((String)session.getAttribute("NEO_UILANG"));
		status.setCdGrp("C016");
		status.setUseYn("Y");
		List<CodeVO> statusList = null;
		try {
			statusList = codeService.getCodeList(status);
		} catch(Exception e) {
			logger.error("codeService.getCodeList[C016] error = " + e);
		}
		
		model.addAttribute("searchVO", searchVO);			// 검색 항목
		model.addAttribute("deptList", deptList);			// 부서 목록
		model.addAttribute("userList", userList);			// 사용자 목록
		model.addAttribute("statusList", statusList);		// 템플릿상태 목록
		
		return "rns/tmp/rnsTempListP";
	}
	
	/**
	 * 템플릿 목록을 조회한다.
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/rnsTempList")
	public String goRnsTemplateList(@ModelAttribute RnsTemplateVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goRnsTemplateList searchTempNm  = " + searchVO.getSearchTempNm());
		logger.debug("goRnsTemplateList searchStatus  = " + searchVO.getSearchStatus());
		logger.debug("goRnsTemplateList searchStartDt = " + searchVO.getSearchStartDt());
		logger.debug("goRnsTemplateList searchEndDt   = " + searchVO.getSearchEndDt());
		logger.debug("goRnsTemplateList searchDeptNo  = " + searchVO.getSearchDeptNo());
		logger.debug("goRnsTemplateList searchUserId  = " + searchVO.getSearchUserId());
		
		searchVO.setSearchStartDt(searchVO.getSearchStartDt().replaceAll("\\.", ""));
		searchVO.setSearchEndDt(searchVO.getSearchEndDt().replaceAll("\\.", ""));
		if(searchVO.getSearchDeptNo() == 0) {
			if("Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
				searchVO.setSearchDeptNo(0);
			} else {
				searchVO.setSearchDeptNo((int)session.getAttribute("NEO_DEPT_NO"));
			}
		}
		searchVO.setUilang((String)session.getAttribute("NEO_UILANG"));
		searchVO.setAdminYn((String)session.getAttribute("NEO_ADMIN_YN"));
		
		// 페이지 설정
		int page = StringUtil.setNullToInt(searchVO.getPage(), 1);
		int rows = StringUtil.setNullToInt(searchVO.getRows(),(int)session.getAttribute("NEO_PER_PAGE"));
		//int rows = StringUtil.setNullToInt(searchVO.getRows(), Integer.parseInt(properties.getProperty("LIST.ROW_PER_PAGE")));
		//int rows = StringUtil.setNullToInt((int)session.getAttribute("NEO_PER_PAGE"), Integer.parseInt(properties.getProperty("LIST.ROW_PER_PAGE")));
		searchVO.setPage(page);
		searchVO.setRows(rows);
		int totalCount = 0;
		
		// 템플릿 목록 조회
		List<RnsTemplateVO> templateList = null;
		try {
			templateList = rnsTemplateService.getTemplateList(searchVO);
		} catch(Exception e) {
			logger.error("rnsTemplateService.getTemplateList error = " + e);
		}
		
		// 코드그룹목록(코드성) 조회 -- 개인별페이지
		CodeVO perPage = new CodeVO();
		perPage.setUilang(searchVO.getUilang());
		perPage.setCdGrp("C126");
		perPage.setUseYn("Y");
		List<CodeVO> perPageList = null;
		try {
			perPageList = codeService.getCodeList(perPage);
		} catch (Exception e) {
			logger.error("codeService.getCodeList[126] error = " + e);
		}
		
		if(templateList != null && templateList.size() > 0) {
			totalCount = templateList.get(0).getTotalCount();
		}
		PageUtil pageUtil = new PageUtil();
		pageUtil.init(request, searchVO.getPage(), totalCount, rows);

		model.addAttribute("searchVO", searchVO);			// 검색 항목
		model.addAttribute("templateList", templateList);	// 템플릿 목록
		model.addAttribute("pageUtil", pageUtil);			// 페이징
		model.addAttribute("perPageList", perPageList);
		
		return "rns/tmp/rnsTempList";
	}
	
	/**
	 * 템플릿 파일 읽기
	 * @param templateVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/rnsTempFileView")
	public ModelAndView goRnsTempFileView(@ModelAttribute RnsTemplateVO templateVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goRnsTempFileView tempFlPath  = " + templateVO.getTempFlPath());
		
		/*
		File file = null;
		FileReader fileReader = null;
		*/
		FileInputStream input = null;
		InputStreamReader reader = null;		
		BufferedReader bufferedReader = null;
		StringBuffer sb = new StringBuffer();
		try {
			String basePath = properties.getProperty("FILE.UPLOAD_PATH");
			String tempPath = basePath + "/" + templateVO.getTempFlPath();
			
			//file = new File(tempPath);
			//fileReader = new FileReader(file);
			input = new FileInputStream(tempPath);
			reader = new InputStreamReader(input,"UTF-8");			
			bufferedReader = new BufferedReader(reader);
			String line = "";
			while((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
		} catch(Exception e) {
			logger.error("goRnsTempFileView error = " + e);
		} finally {
			if(bufferedReader != null) try { bufferedReader.close(); } catch(Exception e) {};
			if(reader != null) try { reader.close(); } catch(Exception e) {};
		}
		
		// jsonView 생성
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", "Success");
		map.put("tempVal", sb.toString().trim());
		ModelAndView modelAndView = new ModelAndView("jsonView", map);
		
		return modelAndView;
	}
	
	/**
	 * 템플릿 정보 등록 화면을 출력한다.
	 * @param templateVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/rnsTempAddP")
	public String goRnsTempAddP(@ModelAttribute RnsTemplateVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		// 부서목록(코드성) 조회
		CodeVO dept = new CodeVO();
		dept.setStatus("000"); // 정상
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(dept);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		// 사용자 목록 조회
		CodeVO user = new CodeVO();
		user.setDeptNo( "Y".equals((String)session.getAttribute("NEO_ADMIN_YN")) ? 0 : (int)session.getAttribute("NEO_DEPT_NO") );
		user.setStatus("000");
		List<CodeVO> userList = null;
		try {
			userList = codeService.getUserList(user);
		} catch(Exception e) {
			logger.error("codeService.getUserList error = " + e);
		}
		
		// 탬플릿상태 목록
		CodeVO status = new CodeVO();
		status.setUilang((String)session.getAttribute("NEO_UILANG"));
		status.setCdGrp("C016");
		status.setUseYn("Y");
		List<CodeVO> statusList = null;
		try {
			statusList = codeService.getCodeList(status);
		} catch(Exception e) {
			logger.error("codeService.getCodeList[C016] error = " + e);
		}
		
		model.addAttribute("deptList", deptList);			// 부서 목록
		model.addAttribute("userList", userList);			// 사용자 목록
		model.addAttribute("statusList", statusList);		// 탬플릿상태 목록
		
		return "rns/tmp/rnsTempAddP";
	}
	
	/**
	 * 템플릿 정보 등록
	 * @param templateVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/rnsTempAdd")
	public ModelAndView insertRnsTempInfo(@ModelAttribute RnsTemplateVO templateVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("insertRnsTempInfo tempNm     = " + templateVO.getTempNm());
		logger.debug("insertRnsTempInfo tempDesc   = " + templateVO.getTempDesc());
		logger.debug("insertRnsTempInfo channel    = " + templateVO.getChannel());
		logger.debug("insertRnsTempInfo status     = " + templateVO.getStatus());
		logger.debug("insertRnsTempInfo deptNo     = " + templateVO.getDeptNo());
		logger.debug("insertRnsTempInfo userId     = " + templateVO.getUserId());
		logger.debug("insertRnsTempInfo tempVal    = " + templateVO.getTempVal());

		templateVO.setRegId((String)session.getAttribute("NEO_USER_ID"));
		templateVO.setRegDt(StringUtil.getDate(Code.TM_YMDHMS));
		
		if (CrossScriptingFilter.existScript(request, templateVO.getTempVal())) {
			// jsonView 생성
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("result", "filter");
			 
			ModelAndView modelAndView = new ModelAndView("jsonView", map);
			
			return modelAndView;
		}
		
		// 템플릿 파일 생성
		String basePath = properties.getProperty("FILE.UPLOAD_PATH") + "/template";
		String deptPath = basePath + "/" + templateVO.getDeptNo();
		long curTime = System.currentTimeMillis();
		String filePath = deptPath + "/" + curTime + ".tmp";
		String tempFlPath = "template/" + templateVO.getDeptNo() + "/" + curTime + ".tmp";
		templateVO.setTempFlPath(tempFlPath);
		logger.debug("insertRnsTempInfo tempFlPath = " + templateVO.getTempFlPath());
		
		// 파일 쓰기
		FileOutputStream fos = null;
		OutputStreamWriter writer = null;
		try {
			File baseDir = new File(basePath);
			if(!baseDir.isDirectory()) {
				baseDir.mkdir();
			}
			
			File deptDir = new File(deptPath);
			if(!deptDir.isDirectory()) {
				deptDir.mkdir();
			}
			
			File file = new File(filePath);
			fos = new FileOutputStream(file);
			writer = new OutputStreamWriter(fos, "UTF-8");
			writer.write(templateVO.getTempVal());
			writer.flush();
		} catch(Exception e) {
			logger.error("insertRnsTempInfo file write error = " + e);
		} finally {
			if(writer != null) try { writer.close(); } catch(Exception e) {};
			if(fos != null) try { fos.close(); } catch(Exception e) {};
		}
		
		int result = 0;
		try {
			result = rnsTemplateService.insertTemplateInfo(templateVO);
		} catch(Exception e) {
			logger.error("rnsTemplateService.insertTemplateInfo error = " + e);
		}
		
		if(result > 0) {
			model.addAttribute("result", "Success");
		} else {
			model.addAttribute("result", "Fail");
		}
		
		// jsonView 생성
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(result > 0) {
			map.put("result", "Success");
		} else {
			map.put("result", "Fail");
		}
		ModelAndView modelAndView = new ModelAndView("jsonView", map);
		
		return modelAndView;
	}
	
	/**
	 * 템플릿 정보 수정화면을 출력한다.
	 * @param templateVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/rnsTempUpdateP")
	public String goRnsTempUpdateP(@ModelAttribute RnsTemplateVO templateVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goRnsTempUpdateP tempNo = " + templateVO.getTempNo());
		
		// 부서목록(코드성) 조회
		CodeVO dept = new CodeVO();
		dept.setStatus("000"); // 정상
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(dept);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		// 사용자 목록 조회
		CodeVO user = new CodeVO();
		user.setDeptNo( "Y".equals((String)session.getAttribute("NEO_ADMIN_YN")) ? 0 : (int)session.getAttribute("NEO_DEPT_NO") );
		user.setStatus("000");
		List<CodeVO> userList = null;
		try {
			userList = codeService.getUserList(user);
		} catch(Exception e) {
			logger.error("codeService.getUserList error = " + e);
		}
		
		// 탬플릿상태 목록
		CodeVO status = new CodeVO();
		status.setUilang((String)session.getAttribute("NEO_UILANG"));
		status.setCdGrp("C016");
		status.setUseYn("Y");
		List<CodeVO> statusList = null;
		try {
			statusList = codeService.getCodeList(status);
		} catch(Exception e) {
			logger.error("codeService.getCodeList[C016] error = " + e);
		}
		
		
		templateVO.setUilang((String)session.getAttribute("NEO_UILANG"));
		try {
			templateVO = rnsTemplateService.getTemplateInfo(templateVO);
		} catch(Exception e) {
			logger.error("rnsTemplateService.getTemplateInfo error = " + e);
		}

		model.addAttribute("searchVO",templateVO);			// 검색 항목
		model.addAttribute("deptList", deptList);			// 부서 목록
		model.addAttribute("userList", userList);			// 사용자 목록
		model.addAttribute("statusList", statusList);		// 탬플릿상태 목록
		model.addAttribute("tempInfo", templateVO);			// 탬플릿 정보
		
		return "rns/tmp/rnsTempUpdateP";
	}
	
	/**
	 * 템플릿 정보 수정
	 * @param templateVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/rnsTempUpdate")
	public ModelAndView updateRnsTempInfo(@ModelAttribute RnsTemplateVO templateVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goRnsTempUpdateP tempNo     = " + templateVO.getTempNo());
		logger.debug("goRnsTempUpdateP tempNm     = " + templateVO.getTempNm());
		logger.debug("goRnsTempUpdateP tempDesc   = " + templateVO.getTempDesc());
		logger.debug("goRnsTempUpdateP channel    = " + templateVO.getChannel());
		logger.debug("goRnsTempUpdateP status     = " + templateVO.getStatus());
		logger.debug("goRnsTempUpdateP deptNo     = " + templateVO.getDeptNo());
		logger.debug("goRnsTempUpdateP userId     = " + templateVO.getUserId());
		logger.debug("goRnsTempUpdateP tempVal    = " + templateVO.getTempVal());
		logger.debug("goRnsTempUpdateP tempFlPath = " + templateVO.getTempFlPath());

		templateVO.setUpId((String)session.getAttribute("NEO_USER_ID"));
		templateVO.setUpDt(StringUtil.getDate(Code.TM_YMDHMS));
		
		if (CrossScriptingFilter.existScript(request, templateVO.getTempVal())) {
			// jsonView 생성
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("result", "filter");
			 
			ModelAndView modelAndView = new ModelAndView("jsonView", map);
			
			return modelAndView;
		}
		
		// 기존 템플릿 파일 삭제
		String oldFilePath = properties.getProperty("FILE.UPLOAD_PATH") + "/" + templateVO.getTempFlPath();
		File oldFile = new File(oldFilePath);
		if(oldFile.exists()) {
			oldFile.delete();
		}

		// 템플릿 파일 생성
		String basePath = properties.getProperty("FILE.UPLOAD_PATH") + "/template";
		String deptPath = basePath + "/" + templateVO.getDeptNo();
		long curTime = System.currentTimeMillis();
		String filePath = deptPath + "/" + curTime + ".tmp";
		String tempFlPath = "template/" + templateVO.getDeptNo() + "/" + curTime + ".tmp";
		templateVO.setTempFlPath(tempFlPath);
		logger.debug("goRnsTempUpdateP new tempFlPath = " + templateVO.getTempFlPath());
		
		// 파일 쓰기
		FileOutputStream fos = null;
		OutputStreamWriter writer = null;
		try {
			
			File baseDir = new File(basePath);
			if(!baseDir.isDirectory()) {
				baseDir.mkdir();
			}
			
			File deptDir = new File(deptPath);
			if(!deptDir.isDirectory()) {
				deptDir.mkdir();
			}
			
			File file = new File(filePath);
			fos = new FileOutputStream(file);
			writer = new OutputStreamWriter(fos, "UTF-8");
			writer.write(templateVO.getTempVal());
			writer.flush();
		} catch(Exception e) {
			logger.error("goRnsTempUpdateP file write error = " + e);
		} finally {
			if(writer != null) try { writer.close(); } catch(Exception e) {};
			if(fos != null) try { fos.close(); } catch(Exception e) {};
		}
		
		int result = 0;
		try {
			result = rnsTemplateService.updateTemplateInfo(templateVO);
		} catch(Exception e) {
			logger.error("rnsTemplateService.updateTemplateInfo error = " + e);
		}
		
		// jsonView 생성
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(result > 0) {
			map.put("result", "Success");
		} else {
			map.put("result", "Fail");
		}
		ModelAndView modelAndView = new ModelAndView("jsonView", map);
		
		return modelAndView;
	}
}
