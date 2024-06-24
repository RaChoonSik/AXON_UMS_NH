/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.15
 * 설명 : 연계정보 관리 Controller 
 */
package kr.co.enders.ums.ems.inf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

 
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cust.custinfo.cryptdata.CustInfoSafeData;
import kr.co.enders.ums.com.service.CodeService;
import kr.co.enders.ums.com.vo.CodeVO;
import kr.co.enders.ums.com.vo.DownloadVO;
import kr.co.enders.ums.ems.inf.service.InterfaceService;
import kr.co.enders.ums.ems.inf.vo.IfBasicVO;
import kr.co.enders.ums.ems.inf.vo.IfCleanVO;
import kr.co.enders.ums.ems.inf.vo.IfFormVO;
import kr.co.enders.ums.ems.inf.vo.IfMappVO;
import kr.co.enders.ums.ems.inf.vo.IfTaskVO;
import kr.co.enders.ums.ems.inf.vo.IfUploadVO; 
import kr.co.enders.util.Code;
import kr.co.enders.util.PageUtil;
import kr.co.enders.util.PropertiesUtil;
import kr.co.enders.util.StringUtil;


@JsonIgnoreProperties(ignoreUnknown = true )
@Controller
@RequestMapping("/ems/inf")
public class InterfaceController {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private CodeService codeService;

	@Autowired
	private InterfaceService interfaceService;

	@Autowired
	private PropertiesUtil properties;

	/****************************** 연계정보 관리 ******************************/
	/**  
	 * 연계 목록 화면을 출력한다.
	 * 
	 * @param deptVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifBasicListP")
	public String goIfBasicListP(@ModelAttribute IfBasicVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		properties.getProperty("FILE.UPLOAD_PATH");
		
		searchVO.setUilang((String) session.getAttribute("NEO_UILANG"));
		
		// 페이지 설정
		int page = StringUtil.setNullToInt(searchVO.getPage(), 1);
		searchVO.setPage(page);
		
		// 사용자그룹(부서) 목록
		CodeVO deptVO = new CodeVO();
		deptVO.setStatus("000");
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(deptVO);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		if(searchVO.getSearchDeptNo() == 0) {
			if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
				searchVO.setSearchDeptNo((int)session.getAttribute("NEO_DEPT_NO"));
			}else {
				//첫번째 부서 설정
				if( deptList != null ) {
					if( deptList.size() >0 ) {
						searchVO.setSearchDeptNo( deptList.get(0).getDeptNo() );
					}
				}
			}
		}
		
		// 발송형태 코드조회 202
		CodeVO code014VO = new CodeVO();
		code014VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code014VO.setCdGrp("C014"); 
		code014VO.setUseYn("Y");
		List<CodeVO> code014List = null;
		try {
			code014List = codeService.getCodeList(code014VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		model.addAttribute("searchVO", searchVO); 	// 검색 항목
		model.addAttribute("deptList", deptList);	// 사용자그룹 목록
		model.addAttribute("code014List", code014List);

		return "ems/inf/ifBasicListP";
	}
	

	/**
	 * 연계 목록을 조회한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ifBasicList")
	public String goIfBasicList(@ModelAttribute IfBasicVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goIfBasicList getSearchIfCampId = " + searchVO.getSearchIfCampId());
		logger.debug("goIfBasicList getSearchIfCampName = " + searchVO.getSearchIfCampName());
		logger.debug("goIfBasicList getSearchIfCampName = " + searchVO.getSearchIfCampName());

		searchVO.setUilang((String) session.getAttribute("NEO_UILANG"));
		if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
			searchVO.setSearchDeptNo((int) session.getAttribute("NEO_DEPT_NO")  );
		}

		// 페이지 설정
		int page = StringUtil.setNullToInt(searchVO.getPage(), 1);
		//int rows = StringUtil.setNullToInt(searchVO.getRows(),Integer.parseInt(properties.getProperty("LIST.ROW_PER_PAGE")));
        int rows = StringUtil.setNullToInt(searchVO.getRows(), (int)session.getAttribute("NEO_PER_PAGE"));
        
        
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
        
		
		searchVO.setPage(page);
		searchVO.setRows(rows);
		int totalCount = 0;

		// 사용자그룹 목록 조회
		
		List<IfBasicVO> ifBasicList = new ArrayList<IfBasicVO>();

		try {
			ifBasicList = interfaceService.getIfBasicList(searchVO);
		} catch (Exception e) {
			logger.error("interfaceService.getIfBasicList error = " + e);
		}

		if (ifBasicList != null && ifBasicList.size() > 0) {
			totalCount = ifBasicList.get(0).getTotalCount();
		}

		PageUtil pageUtil = new PageUtil();
		pageUtil.init(request, searchVO.getPage(), totalCount, rows);

		model.addAttribute("perPageList", perPageList);			// 페이지항목
		model.addAttribute("searchVO", searchVO); // 사용자그룹 목록
		model.addAttribute("ifBasicList", ifBasicList); // 사용자그룹 목록
		model.addAttribute("pageUtil", pageUtil); // 페이징

		return "ems/inf/ifBasicList";
	}
 
	/**
	 * 연계 열을 추가한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ifBasicListAddRow")
	public String goIfBasicListAddRow(@ModelAttribute IfMappVO ifMappVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		//logger.debug("goIfBasicList getSearchIfCampId = " + searchVO.getSearchIfCampId());
		//logger.debug("goIfBasicList getSearchIfCampName = " + searchVO.getSearchIfCampName());
		//logger.debug("goIfBasicList getSearchIfCampName = " + searchVO.getSearchIfCampName());

		// 템플릿유형 코드조회 203
		CodeVO code203VO = new CodeVO();
		code203VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code203VO.setCdGrp("C203"); 
		code203VO.setUseYn("Y");
		List<CodeVO> code203List = null;
		try {
			code203List = codeService.getCodeList(code203VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 019
		CodeVO code204VO = new CodeVO();
		code204VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code204VO.setCdGrp("C204"); 
		code204VO.setUseYn("Y");
		List<CodeVO> code204List = null;
		try {
			code204List = codeService.getCodeList(code204VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		List<IfMappVO> ifMailColNmList = null;
		try {
			ifMailColNmList = interfaceService.getIfMailColList();
		} catch (Exception e) {
			logger.error("interfaceService.getIfMailColList error = " + e);
		}

				
		
		model.addAttribute("code203List", code203List);
		model.addAttribute("code204List", code204List);
		model.addAttribute("code204List", code204List);
		model.addAttribute("ifMappVO", ifMappVO);
		model.addAttribute("ifMailColNmList", ifMailColNmList);
		
		return "ems/inf/ifBasicListAddRow";
	}
	 
	/**
	 * 연계기본 등록 화면을 출력한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifBasicAddP")
	public String goIfBasicAddP(@ModelAttribute IfBasicVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		// 인터페이스형태 코드조회 201
		CodeVO code201VO = new CodeVO();
		code201VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		
		// 사용자그룹(부서) 목록
		CodeVO deptVO = new CodeVO();
		deptVO.setStatus("000");
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(deptVO);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
			searchVO.setDeptNo((int)session.getAttribute("NEO_DEPT_NO"));
		}else {
			//첫번째 부서 설정
			if( deptList != null ) {
				if( deptList.size() >0 ) {
					searchVO.setDeptNo( deptList.get(0).getDeptNo() );
				}
			}
		}
		
		code201VO.setCdGrp("C201");
		code201VO.setUseYn("Y");
		List<CodeVO> code201List = null;
		try {
			code201List = codeService.getCodeList(code201VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 발송형태 코드조회 202
		CodeVO code202VO = new CodeVO();
		code202VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code202VO.setCdGrp("C202"); 
		code202VO.setUseYn("Y");
		List<CodeVO> code202List = null;
		try {
			code202List = codeService.getCodeList(code202VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 203
		CodeVO code203VO = new CodeVO();
		code203VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code203VO.setCdGrp("C203"); 
		code203VO.setUseYn("Y");
		List<CodeVO> code203List = null;
		try {
			code203List = codeService.getCodeList(code203VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 019
		CodeVO code204VO = new CodeVO();
		code204VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code204VO.setCdGrp("C204"); 
		code204VO.setUseYn("Y");
		List<CodeVO> code204List = null;
		try {
			code204List = codeService.getCodeList(code204VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		
		List<IfMappVO> ifMappList = new ArrayList<IfMappVO>();
		IfMappVO ifMappVO = new IfMappVO();
		try {
			ifMappList = interfaceService.getIfMappNewList(ifMappVO);
		} catch (Exception e) {
			logger.error("interfaceService.getIfMappNewList error = " + e);
		}
		
		
		// 사용자 목록 조회
		int deptNo =  (int)session.getAttribute("NEO_DEPT_NO");
		CodeVO user = new CodeVO();
		user.setDeptNo(deptNo);
		user.setStatus("000");
		List<CodeVO> userList = null;
		try {
			userList = codeService.getUserList(user);
		} catch(Exception e) {
			logger.error("codeService.getUserList error = " + e);
		}
		
		List<IfMappVO> ifMailColNmList = null;
		try {
			ifMailColNmList = interfaceService.getIfMailColList();
		} catch (Exception e) {
			logger.error("interfaceService.getIfMailColList error = " + e);
		}
		
		
		String userId = (String) session.getAttribute("NEO_USER_ID");
		searchVO.setUserId(userId);
		
		model.addAttribute("searchVO",   searchVO); 	// 검색 항목
		model.addAttribute("deptList",   deptList);	// 사용자그룹 목록
		model.addAttribute("ifMappList", ifMappList);	// 사용자그룹 목록
		model.addAttribute("code201List", code201List);
		model.addAttribute("code202List", code202List);
		model.addAttribute("code203List", code203List);
		model.addAttribute("code204List", code204List);
		model.addAttribute("userList", userList);			// 사용자 목록
		model.addAttribute("ifMailColNmList", ifMailColNmList);
		
		return "ems/inf/ifBasicAddP";
	}
	
	
	/**
	 * 연계기본 정보를 등록 한다
	 * 
	 * @param ifBasicVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifBasicAdd")
	public ModelAndView insertIfBasic(@ModelAttribute IfFormVO ifFormVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
//		logger.debug("===============================================================" );
//		logger.debug("insertIfBasic deptNo = " +  ifFormVO.getIfBasicVO().getDeptNo()            );
//		logger.debug("===============================================================" );
//		logger.debug("insertIfBasic sendType = " + ifFormVO.getIfBasicVO().getSendType());
		
		IfBasicVO ifBasicVO =  ifFormVO.getIfBasicVO();
		ifBasicVO.setRegId((String) session.getAttribute("NEO_USER_ID"));
		
		int result = 0;
		String resultMsg = "";
		
		try {
			result = interfaceService.insertIfForm(ifFormVO);
		} catch (Exception e) {
			resultMsg = "등록 처리중 오류가 발생하였습니다.";
			logger.error("Exception" ,e );
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (result > 0) {
			map.put("result", "Success");
		} else {
			map.put("result", "Fail");
			map.put("resultMessage", resultMsg);
		}
		ModelAndView modelAndView = new ModelAndView("jsonView", map);

		return modelAndView;
	}
	
	
	/**
	 * 연계정보 수정 화면을 출력한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifBasicUpdateP")
	public String goIfBasicUpdateP(@ModelAttribute IfBasicVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		// 사용자그룹(부서) 목록
		CodeVO deptVO = new CodeVO();
		deptVO.setStatus("000");
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(deptVO);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		IfMappVO schIfMappVo = new IfMappVO();
		schIfMappVo.setIfCampId( searchVO.getIfCampId() );
		
		IfBasicVO ifBasic = null;
		List<IfMappVO> ifMappList = null;
		try {
			ifBasic = interfaceService.getIfBasicInfo(searchVO); 
			
		} catch (Exception e) {
			logger.error("interfaceService.getIfBasicInfo error = " + e);
		}
		try { 
			ifMappList = interfaceService.getIfMappList(schIfMappVo);
			
		} catch (Exception e) {
			logger.error("interfaceService.getIfMappList error = " + e);
		}

		// 인터페이스형태 코드조회 201
		CodeVO code201VO = new CodeVO();
		code201VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code201VO.setCdGrp("C201"); // 그룹상태
		code201VO.setUseYn("Y");
		List<CodeVO> code201List = null;
		try {
			code201List = codeService.getCodeList(code201VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 발송형태 코드조회 202
		CodeVO code202VO = new CodeVO();
		code202VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code202VO.setCdGrp("C202"); 
		code202VO.setUseYn("Y");
		List<CodeVO> code202List = null;
		try {
			code202List = codeService.getCodeList(code202VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 203
		CodeVO code203VO = new CodeVO();
		code203VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code203VO.setCdGrp("C203"); 
		code203VO.setUseYn("Y");
		List<CodeVO> code203List = null;
		try {
			code203List = codeService.getCodeList(code203VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 204
		CodeVO code204VO = new CodeVO();
		code204VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code204VO.setCdGrp("C204"); 
		code204VO.setUseYn("Y");
		List<CodeVO> code204List = null;
		try {
			code204List = codeService.getCodeList(code204VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 012
		CodeVO code012VO = new CodeVO();
		code012VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code012VO.setCdGrp("C012"); 
		code012VO.setUseYn("Y");
		List<CodeVO> code012List = null;
		try {
			code012List = codeService.getCodeList(code012VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 사용자 목록 조회
		int deptNo =  0;
		if( ifBasic != null ) {
			deptNo = ifBasic.getDeptNo();
		}
		
		CodeVO user = new CodeVO();
		user.setDeptNo(deptNo);
		user.setStatus("000");
		List<CodeVO> userList = null;
		try {
			userList = codeService.getUserList(user);
		} catch(Exception e) {
			logger.error("codeService.getUserList error = " + e);
		}
		
		List<IfMappVO> ifMailColNmList = null;
		try {
			ifMailColNmList = interfaceService.getIfMailColList();
		} catch (Exception e) {
			logger.error("interfaceService.getIfMailColList error = " + e);
		}
		

		model.addAttribute("searchVO", searchVO); 				// 검색항목
		model.addAttribute("ifBasic", ifBasic); 				//
		model.addAttribute("ifMappList", ifMappList); 			//
		model.addAttribute("deptList", deptList);			// 사용자그룹 목록
		model.addAttribute("userList", userList);			// 사용자 목록
		
		model.addAttribute("code201List", code201List);
		model.addAttribute("code202List", code202List);
		model.addAttribute("code203List", code203List);
		model.addAttribute("code204List", code204List);
		model.addAttribute("code012List", code012List);
		
		model.addAttribute("ifMailColNmList", ifMailColNmList);

		return "ems/inf/ifBasicUpdateP";
	}
	
	/**
	 * 연계정보를 수정한다
	 * 
	 * @param deptVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifBasicUpdate")
	public ModelAndView updateIfBasic(@ModelAttribute IfFormVO ifFormVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		logger.debug("updateIfBasic ifCampId = " + ifBasicVO.getIfCampId());
//		logger.debug("updateIfBasic ifCampName = " + ifBasicVO.getIfCampName());
//		logger.debug("updateIfBasic interfaceType = " + ifBasicVO.getInterfaceType());
//		logger.debug("updateIfBasic sendType = " + ifBasicVO.getSendType());
		
		IfBasicVO ifBasicVO = ifFormVO.getIfBasicVO();
		ifBasicVO.setUpdId((String) session.getAttribute("NEO_USER_ID"));
		ifBasicVO.setUpdDate(StringUtil.getDate(Code.TM_YMDHMS));
		
		int result = 0;
		String resultMsg = "";
		
		try {
			result = interfaceService.updateIfForm(ifFormVO);
		} catch (Exception e) {
			logger.error("interfaceService.updateIfForm error = " + e);
			resultMsg = "수정 처리중 오류가 발생하였습니다.";
			
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		if (result > 0) {
			map.put("result", "Success");
		} else {
			map.put("result", "Fail");
			map.put("resultMessage", resultMsg);
		}
		ModelAndView modelAndView = new ModelAndView("jsonView", map);
		
		return modelAndView;
	}

	/**
	 * 연계정보를 삭제한다
	 * 
	 * @param deptVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifBasicDelete")
	public ModelAndView deleteIfBasic(@ModelAttribute IfBasicVO ifBasicVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		logger.debug("deleteIfBasic getIfCampIds      = " + ifBasicVO.getIfCampIds());

		ifBasicVO.setUpdId((String) session.getAttribute("NEO_USER_ID"));
		ifBasicVO.setUpdDate(StringUtil.getDate(Code.TM_YMDHMS));
		
		int result = 0;
		String successYn = "";
		String resultMsg = "";
		try {
			result = interfaceService.deleteIfBasicInfo(ifBasicVO);
			if (result >= 0) {
				successYn = "Y";
			}else {
				successYn = "N";
				resultMsg = "삭제 처리중 오류가 발생하였습니다.";
			}
		} catch (Exception e) {
			successYn = "N";
			resultMsg = "삭제 처리중 오류가 발생하였습니다.";
			logger.error("interfaceService.deleteIfBasicInfo error = " + e);
		}

		// jsonView 생성
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("successYn",  successYn);
		map.put("resultMsg", resultMsg);
		ModelAndView modelAndView = new ModelAndView("jsonView", map);
		return modelAndView;
	}
	
	
	/**
	 * 연계 맵 목록 화면을 출력한다.
	 * 
	 * @param deptVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifMappListP")
	public String goIfMappListP(@ModelAttribute IfMappVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goIfMappList getSearchIfCampId = " + searchVO.getSearchIfCampId());
		logger.debug("goIfMappList getSearchIfCol = " + searchVO.getSearchIfCol());
		logger.debug("goIfMappList getSearchDeptNo = " + searchVO.getSearchDeptNo());
		
		
		IfBasicVO searchBasicVO = new IfBasicVO();
		searchBasicVO.setUilang((String) session.getAttribute("NEO_UILANG"));
		searchBasicVO.setDeptNo((int) session.getAttribute("NEO_DEPT_NO")  );
		
		// 페이지 설정
		searchBasicVO.setPage(1);
		searchBasicVO.setRows(1000);
		
		// 사용자그룹(부서) 목록
		CodeVO deptVO = new CodeVO();
		deptVO.setStatus("000");
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(deptVO);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		
		if(searchVO.getSearchDeptNo() == 0) {
			if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
				searchVO.setSearchDeptNo((int)session.getAttribute("NEO_DEPT_NO"));
				searchBasicVO.setSearchDeptNo( (int)session.getAttribute("NEO_DEPT_NO"));
			}else {
				//첫번째 부서 설정
				if(deptList  !=null ) {
					if(  deptList.size() >0 ) {
						searchVO.setSearchDeptNo( deptList.get(0).getDeptNo() );
						searchBasicVO.setSearchDeptNo( deptList.get(0).getDeptNo() );
					}
				}
			}
		}else {
			searchBasicVO.setSearchDeptNo(searchVO.getSearchDeptNo());
		}
		
		// 사용자그룹 상태 코드 목록을 조회한다.
		List<IfBasicVO> ifBasicList = new ArrayList<IfBasicVO>();
		try {
			ifBasicList = interfaceService.getIfBasicList(searchBasicVO);
		} catch (Exception e) {
			logger.error("interfaceService.getIfBasicList error = " + e);
		}
		
		
		//logger.debug("ifBasicList size = " + ifBasicList.size()  );
		model.addAttribute("searchVO", searchVO); 				// 검색 항목
		model.addAttribute("ifBasicList", ifBasicList);         // 사용자그룹 목록
		model.addAttribute("deptList", deptList);				// 사용자그룹목록

		return "ems/inf/ifMappListP";
	}
	
	
	/**
	 * 연계 맵 목록을 조회한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ifMappList")
	public String goIfMappList(@ModelAttribute IfMappVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goIfMappList getSearchIfCampId = " + searchVO.getSearchIfCampId());
		logger.debug("goIfMappList getSearchIfCol = " + searchVO.getSearchIfCol());
		logger.debug("goIfMappList getSearchDeptNo = " + searchVO.getSearchDeptNo() );

		searchVO.setUilang((String) session.getAttribute("NEO_UILANG")); 

		// 페이지 설정
		int page = StringUtil.setNullToInt(searchVO.getPage(), 1);
		int rows = StringUtil.setNullToInt(searchVO.getRows(), Integer.parseInt(properties.getProperty("LIST.ROW_PER_PAGE")));
		rows = 200;
		searchVO.setPage(page);
		searchVO.setRows(rows);
		int totalCount = 0;
		List<IfMappVO> ifMappList = new ArrayList<IfMappVO>();
		try {
			ifMappList = interfaceService.getIfMappList(searchVO);
		} catch (Exception e) {
			logger.error("interfaceService.getIfMappList error = " + e);
		}

		if (ifMappList != null && ifMappList.size() > 0) {
			totalCount = ifMappList.get(0).getTotalCount();
		}

		PageUtil pageUtil = new PageUtil();
		pageUtil.init(request, searchVO.getPage(), totalCount, rows);

		model.addAttribute("ifMappList", ifMappList); // 사용자그룹 목록
		model.addAttribute("pageUtil", pageUtil); // 페이징

		return "ems/inf/ifMappList";
	}
	 
	
	/**
	 * 연계 맵 목록을 조회한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveIfMappList")
	public String saveIfMappList(@ModelAttribute IfFormVO ifFormVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goIfMappList getSearchIfCampId = " + ifFormVO.getIfMappVOList());

		//searchVO.setUilang((String) session.getAttribute("NEO_UILANG")); 
		/*
		List<IfMappVO> ifMappList = new ArrayList<IfMappVO>();

		try {
			ifMappList = interfaceService.getIfMappList(searchVO);
		} catch (Exception e) {
			logger.error("interfaceService.getIfMappList error = " + e);
		}

		if (ifMappList != null && ifMappList.size() > 0) {
			totalCount = ifMappList.get(0).getTotalCount();
		}
		PageUtil pageUtil = new PageUtil();
		pageUtil.init(request, searchVO.getPage(), totalCount, rows);
		model.addAttribute("ifMappList", ifMappList); // 사용자그룹 목록
		model.addAttribute("pageUtil", pageUtil); // 페이징
		*/

		return "ems/inf/ifMappList";
	}
	
	
	
	/**
	 * 연계 맵 등록 화면을 출력한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifMappAddP")
	public String goIfMappAddP(@ModelAttribute IfMappVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		//logger.debug(  "getIfCampId===========" +  searchVO.getIfCampId() );
		
		// 인터페이스형태 코드조회 C204
		CodeVO code204VO = new CodeVO();
		code204VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code204VO.setCdGrp("C204"); // 컬럼유형
		code204VO.setUseYn("Y");
		List<CodeVO> code204List = null;
		try {
			code204List = codeService.getCodeList(code204VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 발송형태 코드조회 202
		CodeVO code205VO = new CodeVO();
		code205VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code205VO.setCdGrp("C205"); 
		code205VO.setUseYn("Y");
		List<CodeVO> code205List = null;
		try {
			code205List = codeService.getCodeList(code205VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		List<IfMappVO> ifMailColNmList = null;
		try {
			ifMailColNmList = interfaceService.getIfMailColList();
		} catch (Exception e) {
			logger.error("interfaceService.getIfMailColList error = " + e);
		}
		
		
		IfBasicVO searchBasicVO = new IfBasicVO();
		searchBasicVO.setIfCampId(searchVO.getIfCampId());
		
		IfBasicVO ifBasic = null;
		try {
			ifBasic = interfaceService.getIfBasicInfo(searchBasicVO);
		} catch (Exception e) {
			logger.error("interfaceService.getIfBasicInfo error = " + e);
		}
				
		model.addAttribute("searchVO", searchVO); 				// 검색항목
		model.addAttribute("code204List", code204List);
		model.addAttribute("code205List", code205List);
		model.addAttribute("ifMailColNmList", ifMailColNmList);  //ifMailColNmList
		
		return "ems/inf/ifMappAddP";
	}
	
	
	/**
	 * 연계 맵항목을 등록한다
	 * 
	 * @param ifFormVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifMappAdd")
	public ModelAndView insertIfMapp(@ModelAttribute IfFormVO ifFormVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		logger.debug("getIfMappVOList ifCampId = " + ifFormVO.getIfMappVOList().size()  );
//		logger.debug("insertDeptInfo ifCampName = " + ifBasicVO.getIfCampName());
//		logger.debug("insertDeptInfo interfaceType = " + ifBasicVO.getInterfaceType());
//		logger.debug("insertDeptInfo sendType = " + ifBasicVO.getSendType());
//		logger.debug("insertDeptInfo templetType = " + ifBasicVO.getTempletType());
//		logger.debug("insertDeptInfo sendTermType = " + ifBasicVO.getSendTermType());
//		logger.debug("insertDeptInfo path = " + ifBasicVO.getPath());
//		logger.debug("insertDeptInfo fileName = " + ifBasicVO.getFileName());
//		logger.debug("insertDeptInfo responseYn = " + ifBasicVO.getResponseYn());
//		logger.debug("insertDeptInfo rtTemplPath = " + ifBasicVO.getRtTemplPath());
//		logger.debug("insertDeptInfo rtSubject = " + ifBasicVO.getRtSubject());
//		logger.debug("insertDeptInfo memo = " + ifBasicVO.getMemo());
		
		
		//ifMappVO.setRegId((String) session.getAttribute("NEO_USER_ID"));
		//ifMappVO.setRegDate(StringUtil.getDate(Code.TM_YMDHMS));
		
		
//		if( ifMappVO.getIfListCol() == null || "".equals( ifMappVO.getIfListCol())) {
//			ifMappVO.setIfListCol("@");
//		}
		
		IfMappVO ifMappVO = null;
		
		for(int i=0;i < ifFormVO.getIfMappVOList().size();i++ ) {
			ifMappVO = ifFormVO.getIfMappVOList().get(i);
			logger.debug("ifMappVO.getIfMailColNm() " + ifMappVO.getIfMailColNm() );
		}
		
		
		int result = 0;
		/*
		
		try {
			result = interfaceService.insertIfMappInfo(ifMappVO);
		} catch (Exception e) {
			logger.error("interfaceService.insertIfMappInfo error = " + e);
		}
		*/

		HashMap<String, Object> map = new HashMap<String, Object>();
		if (result > 0) {
			map.put("result", "Success");
		} else {
			map.put("result", "Fail");
		}
		ModelAndView modelAndView = new ModelAndView("jsonView", map);

		return modelAndView;
	}
	
	
	/**
	 * 연계 기본 목록 조회
	 * @param metaJoinVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getIfBasicList")
	public ModelAndView getIfBasicList(@ModelAttribute IfBasicVO searchVo, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("getIfBasicList getSearchDeptNo = " + searchVo.getSearchDeptNo()  );
		
		//IfBasicVO searchBasicVO = new IfBasicVO();
		searchVo.setUilang((String) session.getAttribute("NEO_UILANG"));
		//searchBasicVO.setSearchIfDeptNo( codeVO.getDeptNo() );
		
		// 페이지 설정
		searchVo.setPage(1);
		searchVo.setRows(2000);
		
		// 사용자그룹 상태 코드 목록을 조회한다.
		List<IfBasicVO> ifBasicList = new ArrayList<IfBasicVO>();
		try {
			ifBasicList = interfaceService.getIfBasicList(searchVo);
		} catch (Exception e) {
			logger.error("interfaceService.getIfBasicList error = " + e);
		}
		
		// jsonView 생성
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ifBasicList", ifBasicList);
		ModelAndView modelAndView = new ModelAndView("jsonView", map);
		
		return modelAndView;
	}
	
	
	
	/**
	 * 작업 목록 화면을 출력한다.
	 * 
	 * @param deptVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifTaskListP")
	public String goIfTaskListP(@ModelAttribute IfTaskVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		searchVO.setUilang((String) session.getAttribute("NEO_UILANG"));
		
		// 검색 기본값 설정
		if(searchVO.getSearchStartDt() == null || "".equals(searchVO.getSearchStartDt())) {
			searchVO.setSearchStartDt(StringUtil.getCalcDateFromCurr(0, "D", "yyyyMMdd"));
		} else {
			searchVO.setSearchStartDt(searchVO.getSearchStartDt().replaceAll("\\.", ""));
		}
		if(searchVO.getSearchEndDt() == null || "".equals(searchVO.getSearchEndDt())) {
			searchVO.setSearchEndDt(StringUtil.getCalcDateFromCurr(0, "D", "yyyyMMdd"));
		} else {
			searchVO.setSearchEndDt(searchVO.getSearchEndDt().replaceAll("\\.", ""));
		}

		// 페이지 설정
		int page = StringUtil.setNullToInt(searchVO.getPage(), 1);
		searchVO.setPage(page);
		
		
		// 사용자그룹(부서) 목록
		CodeVO deptVO = new CodeVO();
		deptVO.setStatus("000");
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(deptVO);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		if(searchVO.getSearchDeptNo() == 0) {
			if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
				searchVO.setSearchDeptNo((int)session.getAttribute("NEO_DEPT_NO"));
			}else {
				//첫번째 부서 설정
				if( deptList != null ) {
					if( deptList.size() >0 ) {
						searchVO.setSearchDeptNo( deptList.get(0).getDeptNo() );
					}
				}
			}
		}
		
		
		// 발송형태 코드조회 205
		CodeVO code205VO = new CodeVO();
		code205VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code205VO.setCdGrp("C205"); 
		code205VO.setUseYn("Y");
		List<CodeVO> code205List = null;
		try {
			code205List = codeService.getCodeList(code205VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		
		// 작업 코드조회 206
		CodeVO code206VO = new CodeVO();
		code206VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code206VO.setCdGrp("C206"); 
		code206VO.setUseYn("Y");
		List<CodeVO> code206List = null;
		try {
			code206List = codeService.getCodeList(code206VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		model.addAttribute("searchVO", searchVO); 	// 검색 항목
		model.addAttribute("deptList", deptList);	// 사용자그룹 목록
		model.addAttribute("code205List", code205List);
		model.addAttribute("code206List", code206List);

		return "ems/inf/ifTaskListP";
	}
	
	
	
	
	
	/**
	 * 연계작업 목록을 조회한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ifTaskList")
	public String goIfTaskList(@ModelAttribute IfTaskVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		logger.debug("goIfBasicList getSearchIfCampId = " + searchVO.getSearchIfCampId());
//		logger.debug("goIfBasicList getSearchIfCampName = " + searchVO.getSearchIfCampName());
//		logger.debug("goIfBasicList getSearchIfCampName = " + searchVO.getSearchIfCampName());
		
		
		logger.debug("getUseStatusNm==================================>>> run ----1 "   );
		
		
		searchVO.setSearchStartDt(searchVO.getSearchStartDt().replaceAll("\\.", ""));
		searchVO.setSearchEndDt(searchVO.getSearchEndDt().replaceAll("\\.", ""));
		
		logger.debug("getUseStatusNm==================================>>> run ----2 "   );
		
		
		searchVO.setUilang((String) session.getAttribute("NEO_UILANG"));
		if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
			//searchVO.setSearchDeptNo((int) session.getAttribute("NEO_DEPT_NO")  );
		}
		
		
		logger.debug("getUseStatusNm==================================>>> run ----3 "   );
		

		// 페이지 설정
		int page = StringUtil.setNullToInt(searchVO.getPage(), 1);
		//int rows = StringUtil.setNullToInt(searchVO.getRows(),Integer.parseInt(properties.getProperty("LIST.ROW_PER_PAGE")));
        int rows = StringUtil.setNullToInt(searchVO.getRows(), (int)session.getAttribute("NEO_PER_PAGE"));
		searchVO.setPage(page);
		searchVO.setRows(rows);
		int totalCount = 0;
		
		
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
		
		
		
		logger.debug("getUseStatusNm==================================>>> run ----4 "   );

		// 사용자그룹 목록 조회
		List<IfTaskVO> ifTaskList = new ArrayList<IfTaskVO>();
		
		try {
			ifTaskList = interfaceService.getIfTaskList(searchVO);
		} catch (Exception e) {
			logger.error("interfaceService.getIfTaskList error = " + e);
		}

		if (ifTaskList != null && ifTaskList.size() > 0) {
			totalCount = ifTaskList.get(0).getTotalCount();
		}
		
		for(int i=0;i<ifTaskList.size();i++) {
			logger.debug("getUseStatusNm==================================>>> "  +       ifTaskList.get(i).getUseStatusNm() );
		}
		
		

		PageUtil pageUtil = new PageUtil();
		pageUtil.init(request, searchVO.getPage(), totalCount, rows);

		
		model.addAttribute("searchVO", searchVO); // 사용자그룹 목록
		model.addAttribute("ifTaskList", ifTaskList); // 사용자그룹 목록
		model.addAttribute("pageUtil", pageUtil); // 페이징
		model.addAttribute("perPageList", perPageList);			// 페이지항목


		return "ems/inf/ifTaskList";
	}
	
	 
	/**
	 * 파일을 지정된 경로로 업로드 한다.(디자인 적용으로 추가)
	 * @param multipartRequest
	 * @param uploadVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/uploadFile")
	public ModelAndView goUploadFile(MultipartHttpServletRequest multipartRequest, @ModelAttribute IfUploadVO uploadVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goUploadFile folder = " + uploadVO.getFolder());
		logger.debug("goUploadFile rFileName = " + uploadVO.getrFileName());
		logger.debug("goUploadFile vFileName = " + uploadVO.getvFileName());
		
		logger.debug("goUploadFile getUpIfCampId = " + uploadVO.getUploadIfCampId() );
		logger.debug("goUploadFile getUploadNewYn = " + uploadVO.getUploadNewYn() );
		
		logger.debug("goUploadFile getTitle = " + uploadVO.getTitle() );
		
		String ifCampId = uploadVO.getUploadIfCampId();
		String newYn = uploadVO.getUploadNewYn();
		
		
		String userId =(String) session.getAttribute("NEO_USER_ID");
		String successYn = "";
		String resultMsg = "";
		FileInputStream fileIS = null;
		
		
		// 업로드 폴더
		String folder = uploadVO.getFolder();
		
		// 파일 디렉토리 체크, 없으면 생성
		String htmlFileDirStr  = properties.getProperty("FILE.UPLOAD_PATH") + "/" + folder;
		
		if(htmlFileDirStr !=null && ! "".equals(htmlFileDirStr)) {
			htmlFileDirStr.replaceAll(".", "");
		}
		logger.debug("goUploadFile htmlFileDirStr = " + htmlFileDirStr);
		
		File htmlFileDir = new File(htmlFileDirStr);
		if(!htmlFileDir.exists()) {
			htmlFileDir.mkdirs();
		}
		
		File file = null;
		
		// 업로드 파일 처리, 파일이름을 yyyyMMddHHmmss_idx 형식으로 변경하여 저장.
		Iterator<String> files = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;
		String oldFileName = null;
		String newFileName = null;
		String newFileLong	=null;
		String fileLocNm	=null;
		
		while(files.hasNext()) {
			multipartFile = multipartRequest.getFile(files.next());
			if(!multipartFile.isEmpty()) {
				oldFileName		= multipartFile.getOriginalFilename();
				newFileLong	= Long.toString(System.currentTimeMillis());
				newFileName = newFileLong + "-" + oldFileName;
				fileLocNm	= htmlFileDirStr + "/" + newFileName;
				if(fileLocNm !=null && ! "".equals(fileLocNm)) {
					fileLocNm.replaceAll(".", "");
					fileLocNm.replaceAll("..", "");
				}
				logger.debug("goUploadFile oldFileName = " + oldFileName);
				logger.debug("goUploadFile newFileName = " + newFileName);
				try {
					
					file = new File(fileLocNm);
					multipartFile.transferTo(file);
				} catch(Exception e) {
					logger.error("goUploadFile error = " + e);
				}
			}
		}
		
		List<IfMappVO> ifMappList = new ArrayList<IfMappVO>();
		
		try {
			if(  ifCampId != null && ifCampId.equals("") ) {
				logger.error("기본정보의 연계ID는 입력해야 합니다."); 
			}
			
			IfBasicVO searchBasicVo = new IfBasicVO(); 
			searchBasicVo.setExistCheckCampId(ifCampId);
			searchBasicVo.setPage(1);
			searchBasicVo.setRows(2000);
			
			
			List<IfBasicVO> ifBasicList = interfaceService.getIfBasicList(searchBasicVo);
			if( "Y".equals(newYn) && ifBasicList.size() > 0 ) {
				logger.error("연계 ID가 이미 저장되어 있습니다. 수정화명에서 업로드 해주세요. ");
			}
			if( "N".equals(newYn) && ifBasicList.size() <= 0 ) {
				logger.error("연계 ID 기본정보가 없습니다. 신규등록 에서 업로드 해주세요.");
			}
			
			fileIS = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fileIS);
			
			int rowNo = 0;
			int cellIndex = 0;
			XSSFSheet sheet= workbook.getSheetAt(0);//0번째 시트를 가져온다
			
			IfMappVO ifMappVo = null;// colMap
			
			int rows = sheet.getPhysicalNumberOfRows();//사용자가 입력한 엑셀 Row수를 가져온다.
			String value = "";
			
			for(rowNo =0 ;rowNo < rows; rowNo++) {
				
				if( rowNo == 0 ) continue;
				
				ifMappVo = new IfMappVO();
				XSSFRow row  = sheet.getRow(rowNo);
				if(row != null) {
					int cells = 9; // 해당 Row에 사용자가 입력한 셀의 수를 가져온다.
					for( cellIndex = 0; cellIndex < cells; cellIndex++) {
						
						value = "";
						XSSFCell cell = row.getCell(cellIndex);// 셀의 값을 가져온다.
						if(cell == null|| "".equals(cell ) ) { // 빈셀체크
							value = "";
						}else {
							// 타입 별로 내용을 읽는다.
							CellType cellType = cell.getCellType();
							switch(  cell.getCellType()  ) {
							case STRING :
								value = cell.getStringCellValue();
								break;
							case NUMERIC :	
								value = String.valueOf( cell.getNumericCellValue() );
								break;
							}
						}
						logger.debug( rowNo+ "번행:" + cellIndex + "번 열 값은 : " + value );
	
						switch( cellIndex ) {
						case 0:
							if(ifCampId !=null &&  ! ifCampId.equals( value ) ) {
								logger.error("기본정보의 연계ID와 엑셀파일의 연계ID는 같아야 합니다.");
							}
							ifMappVo.setIfCampId(value);
							break;
						case 1:
							ifMappVo.setIfCol(value);
							break;
						case 2:
							if(value == null || "".equals(value) ) {
								value = "@";
							}
							ifMappVo.setIfListCol(value);
							break;
						case 3:
							ifMappVo.setIfColName(value);
							break;
						case 4:
							ifMappVo.setMetaCol(value);
							break;
						case 5:
							ifMappVo.setIfColType(value);
							break;
						case 6:
							ifMappVo.setEncType(value);
							break;
						case 7:
							ifMappVo.setReturnYn(value);
							break;	
						case 8:	
							ifMappVo.setSortNum(StringUtil.setNullToInt(value));
							break;	
						}
					}//cell
					
					ifMappVo.setRegId(userId);
					ifMappVo.setUpdId(userId);
					ifMappList.add(ifMappVo);
					
				}//row
			}//endFor
			
			if( fileIS != null ) {
				fileIS.close();
			}
			int resultIdx = 0;
			int result = 0;
			
			result = interfaceService.mergeIfMappInfo(ifMappList);
			successYn = "Y";
			resultMsg = "";
		}catch(Exception ex) {
			successYn = "N";
			resultMsg ="엑셀 업로드 에러가 발생했습니다.";
			logger.error("interfaceService.mergeIfMappInfo error = " + ex);
		}finally {
			
			if( fileIS != null ) {
				try{ fileIS.close(); } catch(Exception ex){   }
			}
		}
		
		// jsonView 생성
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("successYn", successYn);
		map.put("resultMsg", resultMsg);
		
		map.put("oldFileName", oldFileName);
		map.put("newFileName", newFileName);
		ModelAndView modelAndView = new ModelAndView("jsonView", map);

		return modelAndView;
	}
	
	
	
	
	/**
	 * 연계기본 등록 화면을 출력한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifBasicAddList")
	public String goIfBasicAddList(@ModelAttribute IfFormVO ifFormVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		logger.debug("============================================= ");
//		logger.debug("goIfBasicAddP searchVO.getPopupYn = " + searchVO.getPopupYn());
		logger.debug("============================================= ");
//		if( searchVO.getIfBasicVO() == null ) {
//			searchVO.setIfBasicVO(new IfBasicVO() );
//		}

		// 인터페이스형태 코드조회 201
		CodeVO code201VO = new CodeVO();
		code201VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		
		// 사용자그룹(부서) 목록
		CodeVO deptVO = new CodeVO();
		deptVO.setStatus("000");
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(deptVO);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		/*
		if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
			searchVO.setDeptNo((int)session.getAttribute("NEO_DEPT_NO"));
		}else {
			//첫번째 부서 설정
			if( deptList.size() >0 ) {
				searchVO.setDeptNo( deptList.get(0).getDeptNo() );
			}
		}
		*/
		
		code201VO.setCdGrp("C201");
		code201VO.setUseYn("Y");
		List<CodeVO> code201List = null;
		try {
			code201List = codeService.getCodeList(code201VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 발송형태 코드조회 202
		CodeVO code202VO = new CodeVO();
		code202VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code202VO.setCdGrp("C202"); 
		code202VO.setUseYn("Y");
		List<CodeVO> code202List = null;
		try {
			code202List = codeService.getCodeList(code202VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 203
		CodeVO code203VO = new CodeVO();
		code203VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code203VO.setCdGrp("C203"); 
		code203VO.setUseYn("Y");
		List<CodeVO> code203List = null;
		try {
			code203List = codeService.getCodeList(code203VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 019
		CodeVO code204VO = new CodeVO();
		code204VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code204VO.setCdGrp("C204"); 
		code204VO.setUseYn("Y");
		List<CodeVO> code204List = null;
		try {
			code204List = codeService.getCodeList(code204VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		
		List<IfMappVO> ifMappList = new ArrayList<IfMappVO>();
		IfMappVO ifMappVO = new IfMappVO();
		ifMappVO.setIfCampId(ifFormVO.getIfBasicVO().getIfCampId() );
		
		
		try {
			ifMappList = interfaceService.getIfMappList(ifMappVO);
		} catch (Exception e) {
			logger.error("interfaceService.getIfMappList error = " + e);
		}
		
		
		// 사용자 목록 조회
		int deptNo =  (int)session.getAttribute("NEO_DEPT_NO");
		CodeVO user = new CodeVO();
		user.setDeptNo(deptNo);
		user.setStatus("000");
		List<CodeVO> userList = null;
		try {
			userList = codeService.getUserList(user);
		} catch(Exception e) {
			logger.error("codeService.getUserList error = " + e);
		}
		
		List<IfMappVO> ifMailColNmList = null;
		try {
			ifMailColNmList = interfaceService.getIfMailColList();
		} catch (Exception e) {
			logger.error("interfaceService.getIfMailColList error = " + e);
		}
		
		String userId = (String) session.getAttribute("NEO_USER_ID");
		ifFormVO.getIfBasicVO().setUserId(userId);
		
		model.addAttribute("searchVO",   ifFormVO.getIfBasicVO()); 	// 검색 항목
		model.addAttribute("deptList",   deptList);	// 사용자그룹 목록
		model.addAttribute("ifMappList", ifMappList);	// 사용자그룹 목록
		model.addAttribute("code201List", code201List);
		model.addAttribute("code202List", code202List);
		model.addAttribute("code203List", code203List);
		model.addAttribute("code204List", code204List);
		model.addAttribute("userList", userList);			// 사용자 목록
		model.addAttribute("ifMailColNmList", ifMailColNmList);
		
		return "ems/inf/ifBasicAddList";
	}
	
	
	/**
	 * 사용자그룹 수정 화면을 출력한다
	 * 
	 * @param deptVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifBasicUpdateList")
	public String goIfBasicUpdateList(@ModelAttribute IfFormVO ifFormVo, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		logger.debug("============================================= ");
		//logger.debug("goIfBasicAddP searchVO.getPopupYn = " + searchVO.getPopupYn());
		logger.debug("============================================= ");
		
		// 사용자그룹(부서) 목록
		CodeVO deptVO = new CodeVO();
		deptVO.setStatus("000");
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(deptVO);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		IfMappVO schIfMappVo = new IfMappVO();
		schIfMappVo.setIfCampId( ifFormVo.getIfBasicVO().getIfCampId() );
		
		IfBasicVO ifBasic = null;
		List<IfMappVO> ifMappList = null;
		try {
			ifBasic = interfaceService.getIfBasicInfo(ifFormVo.getIfBasicVO()); 
			
		} catch (Exception e) {
			logger.error("interfaceService.getIfBasicInfo error = " + e);
		}
		
		try { 
			ifMappList = interfaceService.getIfMappList(schIfMappVo);
			
		} catch (Exception e) {
			logger.error("interfaceService.getIfMappList error = " + e);
		}

		// 인터페이스형태 코드조회 201
		CodeVO code201VO = new CodeVO();
		code201VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code201VO.setCdGrp("C201"); // 그룹상태
		code201VO.setUseYn("Y");
		List<CodeVO> code201List = null;
		try {
			code201List = codeService.getCodeList(code201VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 발송형태 코드조회 202
		CodeVO code202VO = new CodeVO();
		code202VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code202VO.setCdGrp("C202"); 
		code202VO.setUseYn("Y");
		List<CodeVO> code202List = null;
		try {
			code202List = codeService.getCodeList(code202VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 203
		CodeVO code203VO = new CodeVO();
		code203VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code203VO.setCdGrp("C203"); 
		code203VO.setUseYn("Y");
		List<CodeVO> code203List = null;
		try {
			code203List = codeService.getCodeList(code203VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 204
		CodeVO code204VO = new CodeVO();
		code204VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code204VO.setCdGrp("C204"); 
		code204VO.setUseYn("Y");
		List<CodeVO> code204List = null;
		try {
			code204List = codeService.getCodeList(code204VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		// 템플릿유형 코드조회 012
		CodeVO code012VO = new CodeVO();
		code012VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code012VO.setCdGrp("C012"); 
		code012VO.setUseYn("Y");
		List<CodeVO> code012List = null;
		try {
			code012List = codeService.getCodeList(code012VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		
		// 사용자 목록 조회
		int deptNo = ifBasic.getDeptNo();
		CodeVO user = new CodeVO();
		user.setDeptNo(deptNo);
		user.setStatus("000");
		List<CodeVO> userList = null;
		try {
			userList = codeService.getUserList(user);
		} catch(Exception e) {
			logger.error("codeService.getUserList error = " + e);
		}
		
		List<IfMappVO> ifMailColNmList = null;
		try {
			ifMailColNmList = interfaceService.getIfMailColList();
		} catch (Exception e) {
			logger.error("interfaceService.getIfMailColList error = " + e);
		}
		

		model.addAttribute("searchVO", ifFormVo.getIfBasicVO()); 				// 검색항목
		model.addAttribute("ifBasic", ifBasic); 				//
		model.addAttribute("ifMappList", ifMappList); 			//
		model.addAttribute("deptList", deptList);			// 사용자그룹 목록
		model.addAttribute("userList", userList);			// 사용자 목록
		
		model.addAttribute("code201List", code201List);
		model.addAttribute("code202List", code202List);
		model.addAttribute("code203List", code203List);
		model.addAttribute("code204List", code204List);
		model.addAttribute("code012List", code012List);
		
		model.addAttribute("ifMailColNmList", ifMailColNmList);

		return "ems/inf/ifBasicUpdateList";
	}
	
	
	
	/**
	 * 파일을 다운로드 한다.
	 * @param downloadVO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/down")
	public void fileDownload(@ModelAttribute DownloadVO downloadVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("fileDownload downType = " + downloadVO.getDownType());
		
		String fileName = "";
		String filePath = "";
		
		// 파일연동 샘플 다운로드
		fileName = "if_mapp_sample.xlsx";
		filePath = properties.getProperty("FILE.UPLOAD_PATH") + "/sample/" + fileName; 
		
		
		logger.debug("fileDownload fileName = " + fileName);
		logger.debug("fileDownload filePath = " + filePath);
		
		byte fileBytes[] = FileUtils.readFileToByteArray(new File(filePath));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileBytes.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8") + "\";");
		response.setHeader("Content-Trasfer-Encoding", "binary");
		response.getOutputStream().write(fileBytes);
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	/**
	 * 파일을 지정된 경로로 업로드 한다.(디자인 적용으로 추가)
	 * @param multipartRequest
	 * @param uploadVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/uploadJspFile")
	public ModelAndView uploadJspFile(MultipartHttpServletRequest multipartRequest, @ModelAttribute IfUploadVO uploadVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goUploadFile folder = " + uploadVO.getFolder());
		logger.debug("goUploadFile rFileName = " + uploadVO.getrFileName());
		logger.debug("goUploadFile vFileName = " + uploadVO.getvFileName());
		
		logger.debug("goUploadFile getUpIfCampId = " + uploadVO.getUploadIfCampId() );
		logger.debug("goUploadFile getUploadNewYn = " + uploadVO.getUploadNewYn() );
		
		logger.debug("goUploadFile getTitle = " + uploadVO.getTitle() );
		
		
		String userId =(String) session.getAttribute("NEO_USER_ID");
		String successYn = "";
		String resultMsg = "";
		FileInputStream fileIS = null;
		
		
		// 업로드 폴더
		String folder = uploadVO.getFolder();
		
		// 파일 디렉토리 체크, 없으면 생성
		String htmlFileDirStr  = properties.getProperty("JSP_FILE_UPLOAD_PATH") ;
		if(htmlFileDirStr !=null && ! "".equals(htmlFileDirStr)) {
			htmlFileDirStr.replaceAll(".", "");
		}
		String htmlFileDirBackStr  = properties.getProperty("JSP_FILE_UPLOAD_PATH") + "/back" ;
		if(htmlFileDirBackStr !=null && ! "".equals(htmlFileDirBackStr)) {
			htmlFileDirBackStr.replaceAll(".", "");
		}
		
		logger.debug("goUploadFile htmlFileDirStr = " + htmlFileDirStr);
		File htmlFileDir = new File(htmlFileDirStr);
		if(!htmlFileDir.exists()) {
			htmlFileDir.mkdirs();
		}
		
		File htmlFileBackDir = new File(htmlFileDirBackStr);
		if(!htmlFileBackDir.exists()) {
			htmlFileBackDir.mkdirs();
		}
		
		
		File file = null;
		
		// 업로드 파일 처리, 파일이름을 yyyyMMddHHmmss_idx 형식으로 변경하여 저장.
		Iterator<String> files = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;
		String oldFileName = null;
		String newFileName = null;
		String newFileLong	=null;
		String fileLocNm	=null;
		String fileLocBackNm	=null;
		
		
		while(files.hasNext()) {
			multipartFile = multipartRequest.getFile(files.next());
			if(!multipartFile.isEmpty()) {
				oldFileName		= multipartFile.getOriginalFilename();
				newFileLong	= Long.toString(System.currentTimeMillis());
				//newFileName = newFileLong + "-" + oldFileName;
				newFileName = oldFileName;
				fileLocNm	= htmlFileDirStr + "/" + newFileName;
				if(fileLocNm !=null && ! "".equals(fileLocNm)) {
					fileLocNm.replaceAll(".", "");
					fileLocNm.replaceAll("..", "");
				}
				fileLocBackNm = htmlFileBackDir + "/" + newFileName;
				if(fileLocBackNm !=null && ! "".equals(fileLocBackNm)) {
					fileLocBackNm.replaceAll(".", "");
					fileLocBackNm.replaceAll("..", "");
				}
				
				logger.debug("goUploadFile oldFileName = " + oldFileName);
				logger.debug("goUploadFile newFileName = " + newFileName);
				try {
					
					file = new File(fileLocNm);
					if( file.exists() ) {
						fileMove(  fileLocNm , fileLocBackNm  );
					}
					multipartFile.transferTo(file);
				} catch(Exception e) {
					logger.error("goUploadFile error = " + e);
				}
			}
		}
		
		successYn= "Y";
		
		// jsonView 생성
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("successYn", successYn);
		map.put("resultMsg", resultMsg);
		
		map.put("oldFileName", oldFileName);
		map.put("newFileName", newFileName);
		ModelAndView modelAndView = new ModelAndView("jsonView", map);

		return modelAndView;
	}
	
	
	public void fileMove(String dirfile,String dirNewfile) throws Exception{
		
		Path srcPath = null;
		Path dstPath = null;
		String m_curTime = "";
		String newDirfile = "";
		
		SimpleDateFormat Format = new SimpleDateFormat("yyyyMMddHHmmss");
	    Calendar cal = Calendar.getInstance();
	    m_curTime = Format.format(cal.getTime());
		
		logger.debug("dirfile===========" + dirfile );
		newDirfile = dirNewfile + "_" + m_curTime;
		logger.debug("newDirfile===========" + newDirfile );
		
		srcPath = Paths.get(dirfile);
		dstPath = Paths.get(newDirfile);		
		Files.move(srcPath, dstPath, StandardCopyOption.REPLACE_EXISTING);
		
		
	}
	
	
	/**
	 * 작업 목록 화면을 출력한다.
	 * 
	 * @param deptVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifCleanListP")
	public String goIfCleanListP(@ModelAttribute IfCleanVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		searchVO.setUilang((String) session.getAttribute("NEO_UILANG"));
		
		// 검색 기본값 설정
		if(searchVO.getSearchStartDt() == null || "".equals(searchVO.getSearchStartDt())) {
			searchVO.setSearchStartDt(StringUtil.getCalcDateFromCurr(-30, "D", "yyyyMM") + "01" );
		} else {
			searchVO.setSearchStartDt(searchVO.getSearchStartDt().replaceAll("\\.", ""));
		}
		if(searchVO.getSearchEndDt() == null || "".equals(searchVO.getSearchEndDt())) {
			searchVO.setSearchEndDt(StringUtil.getCalcDateFromCurr(0 , "D", "yyyyMMdd"));
		} else {
			searchVO.setSearchEndDt(searchVO.getSearchEndDt().replaceAll("\\.", ""));
		}
		
		if(searchVO.getSearchCleanYY() == null || "".equals(searchVO.getSearchCleanYY())) {
			searchVO.setSearchCleanYY(StringUtil.getCalcDateFromCurr(0 , "D", "yyyy"));
		} else {
			searchVO.setSearchCleanYY(searchVO.getSearchCleanYY().replaceAll("\\.", ""));
		}
		
		
		if(searchVO.getSearchCleanMM() == null || "".equals(searchVO.getSearchCleanMM())) {
			searchVO.setSearchCleanMM(StringUtil.getCalcDateFromCurr(0 , "D", "MM"));
		} else {
			searchVO.setSearchCleanMM(searchVO.getSearchCleanMM().replaceAll("\\.", ""));
		}
		
		logger.debug("========================================");
		logger.debug("===============" + searchVO.getSearchCleanMM() );
		logger.debug("========================================");
		
		
		
		// 페이지 설정
		int page = StringUtil.setNullToInt(searchVO.getPage(), 1);
		searchVO.setPage(page);
		
		
		// 사용자그룹(부서) 목록
		CodeVO deptVO = new CodeVO();
		deptVO.setStatus("000");
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(deptVO);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		if(searchVO.getSearchDeptNo() == 0) {
			if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
				searchVO.setSearchDeptNo((int)session.getAttribute("NEO_DEPT_NO"));
			}else {
				//첫번째 부서 설정
				if( deptList != null ) {
					if( deptList.size() >0 ) {
						searchVO.setSearchDeptNo( deptList.get(0).getDeptNo() );
					}
				}
			}
		}
		
		
		// 발송형태 코드조회 205
		CodeVO code205VO = new CodeVO();
		code205VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code205VO.setCdGrp("C205"); 
		code205VO.setUseYn("Y");
		List<CodeVO> code205List = null;
		try {
			code205List = codeService.getCodeList(code205VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		
		// 작업 코드조회 206
		CodeVO code206VO = new CodeVO();
		code206VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code206VO.setCdGrp("C206"); 
		code206VO.setUseYn("Y");
		List<CodeVO> code206List = null;
		try {
			code206List = codeService.getCodeList(code206VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		
		List<CodeVO> yearList = new ArrayList();
		CodeVO yearCode = null;
		for(int i = 2022; i< 2075; i++ ) {
			yearCode = new CodeVO();
			yearCode.setCd(i+"");
			yearCode.setCdNm(i+"");
			yearList.add(yearCode);
		}
		
		
		List<CodeVO> monthList = new ArrayList();
		CodeVO monthCode = null;
		for(int i = 1; i<= 12; i++ ) {
			monthCode = new CodeVO();
			if( i < 10 ) {
				monthCode.setCd("0"+ i);
				monthCode.setCdNm("0"+i);
			}else {
				monthCode.setCd(i+"");
				monthCode.setCdNm(i+"");
			}
			monthList.add(monthCode);
		}

		model.addAttribute("searchVO", searchVO); 	// 검색 항목
		model.addAttribute("deptList", deptList);	// 사용자그룹 목록
		model.addAttribute("code205List", code205List);
		model.addAttribute("code206List", code206List);
		
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);

		return "ems/inf/ifCleanListP";
	}
	
	
	/**
	 * 연계작업 목록을 조회한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ifCleanList")
	public String goIfCleanList(@ModelAttribute IfCleanVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		logger.debug("goIfBasicList getSearchIfCampId = " + searchVO.getSearchIfCampId());
		logger.debug("goIfCleanList getSearchCleanYY = " + searchVO.getSearchCleanYY() );
		logger.debug("goIfCleanList getSearchCleanMM = " + searchVO.getSearchCleanMM() );
		
		//searchVO.setSearchStartDt(searchVO.getSearchStartDt().replaceAll("\\.", ""));
		//searchVO.setSearchEndDt(searchVO.getSearchEndDt().replaceAll("\\.", ""));
		
		searchVO.setUilang((String) session.getAttribute("NEO_UILANG"));
		if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
			//searchVO.setSearchDeptNo((int) session.getAttribute("NEO_DEPT_NO")  );
		}
		
		
		searchVO.setSearchCleanYm( searchVO.getSearchCleanYY() +  searchVO.getSearchCleanMM() );

		// 페이지 설정
		int page = StringUtil.setNullToInt(searchVO.getPage(), 1);
		//int rows = StringUtil.setNullToInt(searchVO.getRows(),Integer.parseInt(properties.getProperty("LIST.ROW_PER_PAGE")));
        int rows = StringUtil.setNullToInt(searchVO.getRows(), (int)session.getAttribute("NEO_PER_PAGE"));
		searchVO.setPage(page);
		searchVO.setRows(rows);
		int totalCount = 0;
		
		
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
		
		
		
		// 사용자그룹 목록 조회
		List<IfCleanVO> ifCleanList = new ArrayList<IfCleanVO>();
		
		CustInfoSafeData custInfoSafeData = new CustInfoSafeData();
		IfCleanVO ifCleanVO = null;
		String strTmp = "";
		
		try {
			//searchVO
			if( searchVO.getSearchIfEmail() != null &&  ! "".equals(searchVO.getSearchIfEmail() ) ){
				strTmp = custInfoSafeData.getEncrypt(  searchVO.getSearchIfEmail(), "NOT_RNNO");
				searchVO.setSearchIfEmail(strTmp);
			}
			ifCleanList = interfaceService.getIfCleanList(searchVO);
			if( ifCleanList != null ) {
				for(int i=0;i< ifCleanList.size(); i++ ) {
					ifCleanVO = ifCleanList.get(i);
					strTmp = custInfoSafeData.getDecrypt(ifCleanVO.getIfEmail(), "NOT_RNNO");
					ifCleanVO.setIfEmail(strTmp);
				}
			}
		} catch (Exception e) {
			logger.error("interfaceService.getIfCleanList error = " + e);
		}

		if (ifCleanList != null && ifCleanList.size() > 0) {
			totalCount = ifCleanList.get(0).getTotalCount();
		}
		
		PageUtil pageUtil = new PageUtil();
		pageUtil.init(request, searchVO.getPage(), totalCount, rows);

		
		model.addAttribute("searchVO", searchVO); // 사용자그룹 목록
		model.addAttribute("ifCleanList", ifCleanList); // 사용자그룹 목록
		model.addAttribute("pageUtil", pageUtil); // 페이징
		model.addAttribute("perPageList", perPageList);			// 페이지항목

		return "ems/inf/ifCleanList";
	}
	
	
	
	/**
	 * 작업 목록 화면을 출력한다.
	 * 
	 * @param deptVO
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ifCleanPopListP")
	public String goIfCleanPopListP(@ModelAttribute IfCleanVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		searchVO.setUilang((String) session.getAttribute("NEO_UILANG"));
		
		String cleanYm =  searchVO.getSearchCleanYm();
		
		if( cleanYm != null &&  cleanYm.length() == 6 ) {
			searchVO.setSearchCleanYY( cleanYm.substring(0, 4) );
			searchVO.setSearchCleanMM( cleanYm.substring(4, 6) );
		}
			
		
		// 검색 기본값 설정
		if(searchVO.getSearchStartDt() == null || "".equals(searchVO.getSearchStartDt())) {
			searchVO.setSearchStartDt(StringUtil.getCalcDateFromCurr(-30, "D", "yyyyMM") + "01" );
		} else {
			searchVO.setSearchStartDt(searchVO.getSearchStartDt().replaceAll("\\.", ""));
		}
		if(searchVO.getSearchEndDt() == null || "".equals(searchVO.getSearchEndDt())) {
			searchVO.setSearchEndDt(StringUtil.getCalcDateFromCurr(0 , "D", "yyyyMMdd"));
		} else {
			searchVO.setSearchEndDt(searchVO.getSearchEndDt().replaceAll("\\.", ""));
		}
		
		if(searchVO.getSearchCleanYY() == null || "".equals(searchVO.getSearchCleanYY())) {
			searchVO.setSearchCleanYY(StringUtil.getCalcDateFromCurr(0 , "D", "yyyy"));
		} else {
			searchVO.setSearchCleanYY(searchVO.getSearchCleanYY().replaceAll("\\.", ""));
		}
		
		
		if(searchVO.getSearchCleanMM() == null || "".equals(searchVO.getSearchCleanMM())) {
			searchVO.setSearchCleanMM(StringUtil.getCalcDateFromCurr(0 , "D", "MM"));
		} else {
			searchVO.setSearchCleanMM(searchVO.getSearchCleanMM().replaceAll("\\.", ""));
		}
		
		
		
		// 페이지 설정
		int page = StringUtil.setNullToInt(searchVO.getPage(), 1);
		searchVO.setPage(page);
		
		
		// 사용자그룹(부서) 목록
		CodeVO deptVO = new CodeVO();
		deptVO.setStatus("000");
		List<CodeVO> deptList = null;
		try {
			deptList = codeService.getDeptList(deptVO);
		} catch(Exception e) {
			logger.error("codeService.getDeptList error = " + e);
		}
		
		if(searchVO.getSearchDeptNo() == 0) {
			if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
				searchVO.setSearchDeptNo((int)session.getAttribute("NEO_DEPT_NO"));
			}else {
				//첫번째 부서 설정
				if( deptList != null ) {
					if( deptList.size() >0 ) {
						searchVO.setSearchDeptNo( deptList.get(0).getDeptNo() );
					}
				}
			}
		}
		
		
		// 발송형태 코드조회 205
		CodeVO code205VO = new CodeVO();
		code205VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code205VO.setCdGrp("C205"); 
		code205VO.setUseYn("Y");
		List<CodeVO> code205List = null;
		try {
			code205List = codeService.getCodeList(code205VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		
		// 작업 코드조회 206
		CodeVO code206VO = new CodeVO();
		code206VO.setUilang((String) session.getAttribute("NEO_UILANG"));
		code206VO.setCdGrp("C206"); 
		code206VO.setUseYn("Y");
		List<CodeVO> code206List = null;
		try {
			code206List = codeService.getCodeList(code206VO);
		} catch (Exception e) {
			logger.error("codeService.getCodeList error[C009] = " + e);
		}
		
		
		List<CodeVO> yearList = new ArrayList();
		CodeVO yearCode = null;
		for(int i = 2022; i< 2075; i++ ) {
			yearCode = new CodeVO();
			yearCode.setCd(i+"");
			yearCode.setCdNm(i+"");
			yearList.add(yearCode);
		}
		
		
		List<CodeVO> monthList = new ArrayList();
		CodeVO monthCode = null;
		for(int i = 1; i<= 12; i++ ) {
			monthCode = new CodeVO();
			if( i < 10 ) {
				monthCode.setCd("0"+ i);
				monthCode.setCdNm("0"+i);
			}else {
				monthCode.setCd(i+"");
				monthCode.setCdNm(i+"");
			}
			monthList.add(monthCode);
		}

		model.addAttribute("searchVO", searchVO); 	// 검색 항목
		model.addAttribute("deptList", deptList);	// 사용자그룹 목록
		model.addAttribute("code205List", code205List);
		model.addAttribute("code206List", code206List);
		
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);

		return "ems/inf/ifCleanPopListP";
	}
	
	
	
	/**
	 * 연계작업 목록을 조회한다
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ifCleanPopList")
	public String goIfCleanPopList(@ModelAttribute IfCleanVO searchVO, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.debug("goIfBasicList getSearchCleanYY = " + searchVO.getSearchCleanYY() );
		logger.debug("goIfBasicList getSearchCleanMM = " + searchVO.getSearchCleanMM() );
		
		searchVO.setSearchStartDt(searchVO.getSearchStartDt().replaceAll("\\.", ""));
		searchVO.setSearchEndDt(searchVO.getSearchEndDt().replaceAll("\\.", ""));
		
		searchVO.setUilang((String) session.getAttribute("NEO_UILANG"));
		if(!"Y".equals((String)session.getAttribute("NEO_ADMIN_YN"))) {
			//searchVO.setSearchDeptNo((int) session.getAttribute("NEO_DEPT_NO")  );
		}
		
		
		searchVO.setSearchCleanYm( searchVO.getSearchCleanYY() +  searchVO.getSearchCleanMM() );

		// 페이지 설정
		int page = StringUtil.setNullToInt(searchVO.getPage(), 1);
		//int rows = StringUtil.setNullToInt(searchVO.getRows(),Integer.parseInt(properties.getProperty("LIST.ROW_PER_PAGE")));
		int rows = StringUtil.setNullToInt(searchVO.getRows(), (int)session.getAttribute("NEO_PER_PAGE"));
		searchVO.setPage(page);
		searchVO.setRows(rows);
		int totalCount = 0;
		
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

		
		// 사용자그룹 목록 조회
		List<IfCleanVO> ifCleanList = new ArrayList<IfCleanVO>();
		
		CustInfoSafeData custInfoSafeData = new CustInfoSafeData();
		IfCleanVO ifCleanVO = null;
		String strTmp = "";
		
		try {
			//searchVO
			if( searchVO.getSearchIfEmail() != null &&  ! "".equals(searchVO.getSearchIfEmail() ) ){
				strTmp = custInfoSafeData.getEncrypt(  searchVO.getSearchIfEmail(), "NOT_RNNO");
				searchVO.setSearchIfEmail(strTmp);
			}
			ifCleanList = interfaceService.getIfCleanPopList(searchVO);
			if( ifCleanList != null ) {
				for(int i=0;i< ifCleanList.size(); i++ ) {
					ifCleanVO = ifCleanList.get(i);
					strTmp = custInfoSafeData.getDecrypt(ifCleanVO.getIfEmail(), "NOT_RNNO");
					ifCleanVO.setIfEmail(strTmp);
				}
			}
		} catch (Exception e) {
			logger.error("interfaceService.getIfCleanPopList error = " + e);
		}

		if (ifCleanList != null && ifCleanList.size() > 0) {
			totalCount = ifCleanList.get(0).getTotalCount();
		}
		
		PageUtil pageUtil = new PageUtil();
		pageUtil.init(request, searchVO.getPage(), totalCount, rows);

		
		model.addAttribute("searchVO", searchVO); // 사용자그룹 목록
		model.addAttribute("ifCleanList", ifCleanList); // 사용자그룹 목록
		model.addAttribute("pageUtil", pageUtil); // 페이징
		model.addAttribute("perPageList", perPageList);			// 페이지항목

		return "ems/inf/ifCleanPopList";
	}
	
}
