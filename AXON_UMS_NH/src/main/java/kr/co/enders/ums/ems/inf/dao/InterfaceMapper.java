/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.15
 * 설명 : 연계정보 매퍼 
 */
package kr.co.enders.ums.ems.inf.dao;

import java.util.List;

import kr.co.enders.ums.ems.cam.vo.CampaignVO;
import kr.co.enders.ums.ems.seg.vo.SegmentVO;
import kr.co.enders.ums.ems.inf.vo.IfBasicVO;
import kr.co.enders.ums.ems.inf.vo.IfCleanVO;
import kr.co.enders.ums.ems.inf.vo.IfMappVO;
import kr.co.enders.ums.ems.inf.vo.IfTaskVO;

public interface InterfaceMapper {
	/**
	 * 부서 목록 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfBasicVO> getIfBasicList(IfBasicVO ifBasicVO) throws Exception;
	
	/**
	 * 부서 정보 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public IfBasicVO getIfBasicInfo(IfBasicVO ifBasicVO) throws Exception;
	
	/**
	 * 부서 정보 등록
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int insertIfBasicInfo(IfBasicVO ifBasicVO) throws Exception;
	
	/**
	 * 부서 정보 수정
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int updateIfBasicInfo(IfBasicVO ifBasicVO) throws Exception;
	
	/**
	 * 부서 정보 삭제
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int deleteIfBasicInfo(IfBasicVO ifBasicVO) throws Exception;
	
	
	/**
	 * 부서 정보 삭제
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int deleteIfMappInfoByIfCampId(IfBasicVO ifBasicVO) throws Exception;
	
	
	/**
	 * 부서 정보 삭제
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int deleteNeoCampaign(CampaignVO campaignVO) throws Exception;
	
	
	/**
	 * 
	 * @param segmentVO
	 * @return
	 * @throws Exception
	 */
	public int deleteNeoSegment(SegmentVO segmentVO) throws Exception;
	
	
	/////////////////////////////////////////////////////////////////////////
	
	/**
	 * 부서 목록 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfMappVO> getIfMappList(IfMappVO ifMappVO) throws Exception;
	
	
	
	/**
	 * 부서 목록 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfMappVO> getIfMailColList() throws Exception;
	
	

	/**
	 * 부서 정보 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public IfMappVO getIfMappInfo(IfMappVO ifMappVO) throws Exception;
	
	
	
	/**
	 * 부서 정보 수정
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int updateIfMappInfo(IfMappVO ifMappVO) throws Exception;
	
	
	/**
	 * 부서 정보 등록
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int insertIfMappInfo(IfMappVO ifMappVO) throws Exception;
	
	/**
	 * 부서 정보 등록
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int mergeIfMappInfo(IfMappVO ifMappVO) throws Exception;
	
	
	/**
	 * 부서 정보 삭제
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int deleteIfMappInfo(IfMappVO ifMappVO) throws Exception;
	
	
	/**
	 * 부서 정보 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public IfMappVO getIfMappCount(IfMappVO ifMappVO) throws Exception;
	
	
	/**
	 * 부서 목록 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfMappVO> getIfMappNewList(IfMappVO ifMappVO) throws Exception;
	
	
	/**
	 * 부서 정보 삭제
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int deleteIfMappList(IfMappVO ifMappVO) throws Exception;
	
	
	/**
	 * 발송대상(세그먼트) 정보 등록
	 * @param segmentVO
	 * @return
	 * @throws Exception
	 */
	public int insertKjSegmentInfo(SegmentVO segmentVO) throws Exception;
	
	
	/**
	 * 발송대상(세그먼트) 정보 등록
	 * @param segmentVO
	 * @return
	 * @throws Exception
	 */
	public int insertKjSegmentRealInfo(SegmentVO segmentVO) throws Exception;
	

	/**
	 * 발송대상(세그먼트) 정보 등록
	 * @param segmentVO
	 * @return
	 * @throws Exception
	 */
	public int insertKjSegmentRetryInfo(SegmentVO segmentVO) throws Exception;
	
	
	/**
	 * 발송대상(세그먼트) 정보 등록
	 * @param segmentVO
	 * @return
	 * @throws Exception
	 */
	public int updateKjSegmentInfo(SegmentVO segmentVO) throws Exception;
	
	
	
	/**
	 * 발송대상(세그먼트) 정보 등록
	 * @param segmentVO
	 * @return
	 * @throws Exception
	 */
	public int updateKjSegmentRealInfo(SegmentVO segmentVO) throws Exception;
	
	
	
	/**
	 * 발송대상(세그먼트) 정보 등록
	 * @param segmentVO
	 * @return
	 * @throws Exception
	 */
	public int updateKjSegmentRetryInfo(SegmentVO segmentVO) throws Exception;

	

	/**
	 * getIfMappCount
	 * @param ifBasicVO
	 * @return
	 * @throws Exception
	 */
	public int getKjSegmentRealCount(SegmentVO segmentVO) throws Exception;
	
	
	/**
	 * getIfMappCount
	 * @param ifBasicVO
	 * @return
	 * @throws Exception
	 */
	public int getKjSegmentRetryCount(SegmentVO segmentVO) throws Exception;
	
	
	
	
	
	/**
	 * 발송대상(세그먼트) 정보 등록
	 * @param segmentVO
	 * @return
	 * @throws Exception
	 */
	public int insertKjCampaignInfo(CampaignVO campaignVO) throws Exception;
	
	
	
	/**
	 * 발송대상(세그먼트) 정보 등록
	 * @param segmentVO
	 * @return
	 * @throws Exception
	 */
	public int deleteCampaignByIf(SegmentVO segmentVO) throws Exception;
	
	
	/**
	 * 부서 정보 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public SegmentVO getSegmentNo() throws Exception;
	
	
	/**
	 * 부서 정보 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public CampaignVO getCampaignNo() throws Exception;
	
	
	/**
	 * 부서 정보 수정
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int updateIfBasicSegNo(IfBasicVO ifBasicVO) throws Exception;
	
	
	/**
	 * 부서 정보 수정
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public int updateIfBasicCampNo(IfBasicVO ifBasicVO) throws Exception;
	
	
	/**
	 * 부서 목록 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfTaskVO> getIfTaskList(IfTaskVO ifTaskVO) throws Exception;
	
	
	/**
	 * getIfMappCount
	 * @param ifBasicVO
	 * @return
	 * @throws Exception
	 */
	public int getUsedIfCampNoCount(IfBasicVO ifBasicVO) throws Exception;
	
	
	/*
	 * 부서 목록 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfCleanVO> getIfCleanList(IfCleanVO ifCleanVO) throws Exception;
	
	
	/*
	 * 부서 목록 조회
	 * @param deptVO
	 * @return
	 * @throws Exception
	 */
	public List<IfCleanVO> getIfCleanPopList(IfCleanVO ifCleanVO) throws Exception;
	
	
	

}
