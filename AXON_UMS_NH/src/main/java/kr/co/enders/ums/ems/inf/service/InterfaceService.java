/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.15
 * 설명 : 연계정보 서비스 인터페이스 
 */
package kr.co.enders.ums.ems.inf.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.enders.ums.ems.inf.vo.IfBasicVO;
import kr.co.enders.ums.ems.inf.vo.IfCleanVO;
import kr.co.enders.ums.ems.inf.vo.IfFormVO;
import kr.co.enders.ums.ems.inf.vo.IfMappVO;
import kr.co.enders.ums.ems.inf.vo.IfTaskVO;
import kr.co.enders.ums.sys.acc.vo.DeptVO;
import kr.co.enders.ums.sys.acc.vo.UserVO;
import kr.co.enders.ums.sys.acc.vo.OrganizationVO;
import kr.co.enders.ums.sys.acc.vo.ServiceVO;
import kr.co.enders.ums.sys.acc.vo.UserProgVO;
import kr.co.enders.ums.sys.acc.vo.UserOrgVO;

@Service
public interface InterfaceService {
	/**
	 * 그룹 목록 조회
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfBasicVO> getIfBasicList(IfBasicVO ifBasicInfoVO) throws Exception;

	/**
	 * 그룹 정보 조회
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public IfBasicVO getIfBasicInfo(IfBasicVO ifBasicVO) throws Exception;

	/**
	 * 그룹 정보 등록
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="transactionManagerEms")
	public int insertIfBasicInfo(IfBasicVO ifBasicVO) throws Exception;

	/**
	 * 그룹 정보 수정
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="transactionManagerEms")
	public int updateIfBasicInfo(IfBasicVO ifBasicVO) throws Exception;

	/**
	 * 그룹 정보 삭제
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="transactionManagerEms")
	public int deleteIfBasicInfo(IfBasicVO ifBasicVO) throws Exception;
	
	/**
	 * 그룹 정보 수정
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="transactionManagerEms")
	public int updateIfBasic(IfBasicVO ifBasicVO) throws Exception;
	
	
	/**
	 * 그룹 정보 등록
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="transactionManagerEms")
	public int insertIfForm(IfFormVO ifFormVO) throws Exception;
	
	
	/**
	 * 그룹 정보 등록
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="transactionManagerEms")
	public int updateIfForm(IfFormVO ifFormVO) throws Exception;	
	
	
	
	
	
	
	/**
	 * 그룹 목록 조회
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfMappVO> getIfMappList(IfMappVO ifMappInfoVO) throws Exception;
	
	
	
	
	/**
	 * 그룹 목록 조회
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfMappVO> getIfMailColList() throws Exception;
	
	
	
	/**
	 * 그룹 정보 조회
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public IfMappVO getIfMappInfo(IfMappVO ifMappVO) throws Exception;

	/**
	 * 그룹 정보 등록
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="transactionManagerEms")
	public int insertIfMappInfo(IfMappVO ifMappVO) throws Exception;
	
	
	/**
	 * 그룹 정보 등록
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="transactionManagerEms")
	public int mergeIfMappInfo(List<IfMappVO> mappList) throws Exception;
	
	
	/**
	 * 그룹 정보 수정
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="transactionManagerEms")
	public int updateIfMappInfo(IfMappVO ifMappVO) throws Exception;
	
	
	
	/**
	 * 그룹 정보 삭제
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="transactionManagerEms")
	public int deleteIfMappInfo(IfMappVO ifMappVO) throws Exception;
	
	
	/**
	 * 그룹 정보 조회
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public IfMappVO getIfMappCount(IfMappVO ifMappVO) throws Exception;
	
	
	/**
	 * 그룹 목록 조회
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfMappVO> getIfMappNewList(IfMappVO ifMappInfoVO) throws Exception;
	
	
	/**
	 * 그룹 목록 조회
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfTaskVO> getIfTaskList(IfTaskVO ifTaskInfoVO) throws Exception;
	
	
	
	/**
	 * 그룹 목록 조회
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfCleanVO> getIfCleanList(IfCleanVO ifCleanInfoVO) throws Exception;
	
	
	/**
	 * 그룹 목록 조회
	 * 
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfCleanVO> getIfCleanPopList(IfCleanVO ifCleanInfoVO) throws Exception;

		
		
}
