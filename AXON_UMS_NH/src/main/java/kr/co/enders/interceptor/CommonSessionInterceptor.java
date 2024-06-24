/**
 * 작성자 : 김준희
 * 작성일시 : 2021.07.07
 * 설명 : 공통 인터셉터 정의(로그인 체크)
 */
package kr.co.enders.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.enders.ums.ems.apr.service.SecuApprovalLineService;
import kr.co.enders.ums.lgn.service.LoginService;
import kr.co.enders.ums.lgn.vo.LoginVO;
import kr.co.enders.ums.main.service.MainService;
import kr.co.enders.ums.sys.acc.service.AccountService;
import kr.co.enders.ums.sys.acc.vo.SysMenuVO;
import kr.co.enders.ums.sys.acc.vo.UserVO;
import kr.co.enders.ums.sys.log.service.SystemLogService;
import kr.co.enders.ums.sys.log.vo.ActionLogVO;
import kr.co.enders.util.Code;
import kr.co.enders.util.EncryptAccUtil;
import kr.co.enders.util.EncryptUtil;
import kr.co.enders.util.PropertiesUtil;
import kr.co.enders.util.StringUtil;

public class CommonSessionInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SystemLogService systemService;
	
	@Autowired
	private MainService mainService;
	
	@Autowired
	private SecuApprovalLineService apprService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PropertiesUtil properties;	

	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String contextRoot = request.getContextPath();
		String requestUri = request.getRequestURI();
		
		logger.debug("## preHandle requestUri = " + requestUri);
		
		boolean result = false;
		HttpSession session = request.getSession();
		
		// 사용자 세션 체크
		if(session.getAttribute("NEO_USER_ID") == null || "".equals((String)session.getAttribute("NEO_USER_ID"))) {

			String useFrameYn = properties.getProperty("USE_FRAME_YN");
			if ("Y".equals(useFrameYn) &&  request.getQueryString() != null && request.getQueryString().indexOf("loginid") > -1   && request.getQueryString().indexOf("frame") > -1) {	
				String userId =  EncryptAccUtil.getEncryptedSHA256(request.getParameter("loginid"), properties.getProperty("SSO.KEYSTRING"));
				if(userId != null) {
					result = createSession(request, userId); 
				} else {
					result = false;
				}
				
			} else {
				if(requestUri.indexOf("/index.ums") >= 0 || requestUri.indexOf("/service.ums") >= 0) {
					response.sendRedirect(contextRoot + "/lgn/lgnP.ums");
				} else {
					response.sendRedirect(contextRoot + "/lgn/timeout.ums");
				}
				result = false;
			}
		} 
		else {
			// Disable browser caching
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			
			// 메뉴ID/상위메뉴ID 확인
			String pMenuId = (String)request.getParameter("pMenuId");
			String menuId = (String)request.getParameter("menuId");
			if(pMenuId == null || "".equals(pMenuId)) {
				pMenuId = (String)session.getAttribute("NEO_P_MENU_ID");
			}
			if(menuId == null || "".equals(menuId)) {
				menuId = (String)session.getAttribute("NEO_MENU_ID");
			}
			
			// 페이지 권한 확인
			if(!(requestUri.indexOf("index.ums") >= 0 || requestUri.indexOf("service.ums") >= 0 )) {		// index.ums, service.ums 페이지는 제외
				SysMenuVO sysMenu = mainService.getSourcePathMenu(requestUri);
				// 시스템메뉴로 등록된 URI인 경우
				if(sysMenu != null) {
					pMenuId = sysMenu.getParentmenuId();
					menuId = sysMenu.getMenuId();
					
 
					//09.23 세션에서 페이지 권한 가져오는것으로 변경
					List<SysMenuVO> menuList = (List<SysMenuVO>) session.getAttribute("NEO_MENU_LIST");
					SysMenuVO userMenuVO = new SysMenuVO();
					boolean hasMenuAuth = false; 
					for (int idx = 0; idx < menuList.size(); idx++) {
						userMenuVO = (SysMenuVO) menuList.get(idx);
						if (requestUri.equals(userMenuVO.getSourcePath())){
							hasMenuAuth = true;
						}
					}
					
					// 페이지권한 O
					if(hasMenuAuth) {
						// 사용자 활동이력 등록(Success)
						ActionLogVO logVO = new ActionLogVO();
						logVO.setStatusGb("Success");
						logVO.setContentType("001"); // 공통코드(C112) = 001:MENU:메뉴접근
						logVO.setContent(menuId);
						logVO.setContentPath(requestUri);
						logVO.setExtrYn("N");
						logVO.setMobilYn("N");
						systemService.insertActionLog(request, session, logVO);
						
					// 페이지권한 X
					} else {
						// 사용자 활동이력 등록(Failure:권한없음)
						ActionLogVO logVO = new ActionLogVO();
						logVO.setStatusGb("Failure");
						logVO.setContentType("001"); // 공통코드(C112) = 001:MENU:메뉴접근
						logVO.setContent(menuId);
						logVO.setContentPath(requestUri);
						logVO.setMessage("메뉴 권한 없음.");
						logVO.setExtrYn("N");
						logVO.setMobilYn("N");
						systemService.insertActionLog(request, session, logVO);
						
						response.sendRedirect(contextRoot + "/err/access.ums");
						result = false;
					}
				}
			}
			
			// 단기메일,정기메일 권한확인(등록/수정:좌측메뉴 외 접근통제:신규발송/일정)
			if(requestUri.indexOf("mailAddP.ums") >= 0 || requestUri.indexOf("taskAddP.ums") >= 0 || requestUri.indexOf("mailUpdateP.ums") >= 0 && requestUri.indexOf("taskUpdateP.ums") >= 0) {
				// 사용자 권한 체크
				SysMenuVO menuVO = new SysMenuVO();
				if(requestUri.indexOf("mailAddP.ums") >= 0 || requestUri.indexOf("mailUpdateP.ums") >= 0) {
					menuVO.setSourcePath("/ems/cam/mailListP.ums");
				} else {
					menuVO.setSourcePath("/ems/cam/taskListP.ums");
				}
				menuVO.setUserId((String)session.getAttribute("NEO_USER_ID"));
				//SysMenuVO userMenu =  mainService.getUserMenuAuth(menuVO);
				//09.23 세션에서 페이지 권한 가져오는것으로 변경
				List<SysMenuVO> menuList = (List<SysMenuVO>) session.getAttribute("NEO_MENU_LIST");
				SysMenuVO userMenuVO = new SysMenuVO();
				boolean hasMenuAuth = false; 
				String menuSoucePath = menuVO.getSourcePath();
				for (int idx = 0; idx < menuList.size(); idx++) {
					userMenuVO = (SysMenuVO) menuList.get(idx);
					if (menuSoucePath.equals(userMenuVO.getSourcePath())){
						hasMenuAuth = true;
					}
				}
				
				// 페이지권한 O
				if(hasMenuAuth) {
					// 사용자 활동이력 등록(Success)
					ActionLogVO logVO = new ActionLogVO();
					logVO.setStatusGb("Success");
					logVO.setContentType("001"); // 공통코드(C112) = 001:MENU:메뉴접근
					logVO.setContent(menuId);
					logVO.setContentPath(requestUri);
					logVO.setExtrYn("N");
					logVO.setMobilYn("N");
					systemService.insertActionLog(request, session, logVO);
					
				// 페이지권한 X
				} else {
					// 사용자 활동이력 등록(Failure:권한없음)
					ActionLogVO logVO = new ActionLogVO();
					logVO.setStatusGb("Failure");
					logVO.setContentType("001"); // 공통코드(C112) = 001:MENU:메뉴접근
					logVO.setContent(menuId);
					logVO.setContentPath(requestUri);
					logVO.setMessage("메뉴 권한 없음.");
					logVO.setExtrYn("N");
					logVO.setMobilYn("N");
					systemService.insertActionLog(request, session, logVO);
					
					response.sendRedirect(contextRoot + "/err/access.ums");
					result = false;
				}
			}
			
			// 메일결재건수 조회
			request.setAttribute("apprMailCnt",apprService.getApprCount((String)session.getAttribute("NEO_USER_ID")));
			
			session.setAttribute("NEO_P_MENU_ID", pMenuId);
			session.setAttribute("NEO_MENU_ID", menuId);
			
			result = true;
		}
		
		return result;
	}

	private boolean createSession(HttpServletRequest request, String userId) {
		
		boolean result = false;
		LoginVO loginVO = new LoginVO();
		loginVO.setpUserId(userId);
		logger.debug("loginProcess pUserId = " + loginVO.getpUserId()); 
		
		UserVO userVO = null;
		String userStatus = "999";
		
		HttpSession session = request.getSession();
		
		try {
			userVO = loginService.isSSOUser(loginVO);
			
			if(userVO != null ) {
				if(userVO.getStatus() != null && !"".equals(userVO.getStatus())) {
					userStatus = userVO.getStatus();
					if( "000".equals(userStatus)) {
						result = true;
					}
				} 
			} else {
				if("Y".equals(properties.getProperty("ACCOUNT.AUTO_CREATE"))){
					String ssoDefaultUser = properties.getProperty("ACCOUNT.DEFAULT_USER");
					LoginVO defaultUserVO = new LoginVO();
					defaultUserVO.setpUserId(ssoDefaultUser);
					
					try {
						UserVO copyUserVO = new UserVO();
						copyUserVO = loginService.isSSOUser(defaultUserVO);
						
						if (copyUserVO != null) { 
							copyUserVO.setCopyUserId(loginVO.getpUserId()); 
							String strInitPwd =  StringUtil.makeRandomString(Code.RAND_TYPE_D);
							strInitPwd.toUpperCase();
							copyUserVO.setUserPwd(EncryptUtil.getEncryptedSHA256(strInitPwd));
							copyUserVO.setUserNm(loginVO.getpUserId());
							 
							if (accountService.copyUserInfo(copyUserVO)  < 0  ) {
								userVO = loginService.isSSOUser(loginVO); 
								if(copyUserVO.getCopyUserId() == null || "".equals(loginVO.getpUserId())) {
									ActionLogVO logVO = new ActionLogVO();
									logVO.setStatusGb("Failure");
									logVO.setContent("SSOLogin");
									logVO.setContentPath("/lgn/ssolgn.ums");
									logVO.setMessage("SSO사용자 자동 생성 실패");
									insertLoginActionLog(request, session, logVO);  
								} 
							} else {
								result = true;
							}
						} 
					} catch (Exception e) {
						logger.error("loginService.copyUserInfoVO  Info error  = " + e);
					} 
				}
			}
		} catch (Exception e) {
			logger.error("loginService.isSSOUser  error  = " + e);
		} 
	
		
		if( result) {
			// 세션값 설정
			session.setAttribute("NEO_SSO_LOGIN", "N");						// SSO 세션으로 넘어온 사용자 아님
			session.setAttribute("NEO_USER_ID", userVO.getUserId());		// 사용자ID
			session.setAttribute("NEO_USER_NM", userVO.getUserNm());		// 사용자명
			session.setAttribute("NEO_DEPT_NO", userVO.getDeptNo());		// 부서번호
			session.setAttribute("NEO_DEPT_NM", userVO.getDeptNm());		// 부서명
			session.setAttribute("NEO_TZ_CD", userVO.getTzCd());			// 타임존코드
			session.setAttribute("NEO_TZ_TERM", userVO.getTzTerm());		// 타임존시간차
			session.setAttribute("NEO_UILANG", userVO.getUilang());			// 언어권
			session.setAttribute("NEO_CHARSET", userVO.getCharset());		// 문자셋
			session.setAttribute("NEO_FONT", userVO.getFont());				// 폰트
			session.setAttribute("NEO_ORG_CD", userVO.getOrgCd());			// 조직코드
			session.setAttribute("NEO_ORG_NM", userVO.getOrgKorNm());		// 조직명
			session.setAttribute("NEO_JOB_GB", userVO.getJobGb());			// 직책코드
			session.setAttribute("NEO_JOB_NM", userVO.getJobNm());			// 직책명
			session.setAttribute("NEO_LINK", userVO.getLinkService());		// 사용자 링크 서비스
			
			session.setAttribute("NEO_MAIL_FROM_NM", userVO.getMailFromNm()); 	// 발송자명 
			session.setAttribute("NEO_MAIL_FROM_EM", userVO.getMailFromEm()); 	// 발송자이메일주소
			session.setAttribute("NEO_USER_TEL", userVO.getUserTel());			// 연락처
			session.setAttribute("NEO_USER_EM", userVO.getUserEm());			// 이메일
			session.setAttribute("NEO_REPLY_TO_EM", userVO.getReplyToEm());		// 회신이메일
			session.setAttribute("NEO_RETURN_EM", userVO.getReturnEm()); 		// return 이메일 
			session.setAttribute("NEO_PER_PAGE", StringUtil.setNullToInt(userVO.getPerPage(), Integer.parseInt(properties.getProperty("LIST.ROW_PER_PAGE")))); // 사용자별 목록 조회 페이지
			
			session.setAttribute("NEO_USE_EMS", "N");			// EMS 사용여부
			session.setAttribute("NEO_USE_RNS", "N");			// RNS 사용여부
			session.setAttribute("NEO_USE_SMS", "N");			// SMS 사용여부
			session.setAttribute("NEO_USE_PUSH", "N");			// PUSH 사용여부
			
			session.setAttribute("NEO_FRAME_YN", "Y");			// 프레임사용여부
			session.setAttribute("NEO_USE_EMS", "Y");			// EMS 사용여부
			
			// 사용자 프로그램 사용권한 조회(데이터 등록 환경에 따라 쿼리 변동 가능성 있음)
			// 사용자 메뉴
			List<SysMenuVO> menuList = null;
			try {
				menuList = loginService.getUserMenuList(userVO); 
				session.setAttribute("NEO_MENU_LIST", menuList);
				result = true;
			} catch(Exception e) {
				result = false;
				logger.error("loginService.getUserMenuList Error = " + e);
			}
		}
		
		return result;
	}
	
	public void insertLoginActionLog(HttpServletRequest req, HttpSession session, ActionLogVO actionLogVO) {
		
		try {
			if("Success".equals(actionLogVO.getStatusGb())) {
				actionLogVO.setContentType("000"); // 000: 인증
				actionLogVO.setExtrYn("N");
				actionLogVO.setMobilYn("N");
				systemService.insertActionLog(req, session, actionLogVO);
			} else {
				
				actionLogVO.setLogDt(StringUtil.getDate(Code.TM_YMDHMSM) );		// 로그일시
				actionLogVO.setSessionId( session.getId() );					// 세션ID
				actionLogVO.setIpAddr(req.getRemoteAddr() );					// IP주소 
				String pUserId = ""; 
				if(req.getParameter("pUserId") != null && !"".equals(req.getParameter("pUserId"))){
					pUserId = EncryptAccUtil.getEncryptedSHA256(req.getParameter("pUserId"), properties.getProperty("ACCOUNT.KEYSTRING"));
				}
				actionLogVO.setUserId(pUserId);	// 사용자ID
				actionLogVO.setDeptNo(0);	// 사용자그룹
				actionLogVO.setOrgCd("");	// 조직코드
				
				actionLogVO.setStatusGb("Failure");  // 000: 인증
				actionLogVO.setContentType("000");
				if(actionLogVO.getMessage() == null || "".equals(actionLogVO)) {
					actionLogVO.setMessage("인증정보 불일치");
				}
				actionLogVO.setExtrYn("N");
				actionLogVO.setMobilYn("N");
				
				systemService.insertActionLog(actionLogVO);
			}
		} catch(Exception e) {
			logger.error("systemService.insertActionLog error = " + e);
		} 
	}
	
}
