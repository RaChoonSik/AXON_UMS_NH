/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.15
 * 설명 : 연계정보 데이터 처리 
 */
package kr.co.enders.ums.ems.inf.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.enders.ums.ems.cam.vo.CampaignVO;
import kr.co.enders.ums.ems.seg.vo.SegmentVO;
import kr.co.enders.ums.ems.inf.vo.IfBasicVO;
import kr.co.enders.ums.ems.inf.vo.IfCleanVO;
import kr.co.enders.ums.ems.inf.vo.IfMappVO;
import kr.co.enders.ums.ems.inf.vo.IfTaskVO;

@Repository
public class InterfaceDAO implements InterfaceMapper {
	
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	
	@Autowired
	private SqlSession sqlSessionEms;

	@Override
	public List<IfBasicVO> getIfBasicList(IfBasicVO ifBasicInfoVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getIfBasicList(ifBasicInfoVO);
	}

	@Override
	public IfBasicVO getIfBasicInfo(IfBasicVO ifBasicVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getIfBasicInfo(ifBasicVO);
	}

	@Override
	public int insertIfBasicInfo(IfBasicVO ifBasicVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).insertIfBasicInfo(ifBasicVO);
	}

	@Override
	public int updateIfBasicInfo(IfBasicVO ifBasicVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).updateIfBasicInfo(ifBasicVO);
	}

	@Override
	public int deleteIfBasicInfo(IfBasicVO ifBasicVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).deleteIfBasicInfo(ifBasicVO);
	}
	
	
	@Override
	public int deleteIfMappInfoByIfCampId(IfBasicVO ifBasicVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).deleteIfMappInfoByIfCampId(ifBasicVO);
	}
	
	
	
	@Override
	public int deleteNeoCampaign(CampaignVO campaignVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).deleteNeoCampaign(campaignVO);
	}
	
	
	@Override
	public int deleteNeoSegment(SegmentVO segmentVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).deleteNeoSegment(segmentVO);
	}
	
	
	
	@Override
	public List<IfMappVO> getIfMappList(IfMappVO ifMappInfoVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getIfMappList(ifMappInfoVO);
	}
	
	@Override
	public List<IfMappVO> getIfMailColList() throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getIfMailColList();
	}
	
	@Override
	public IfMappVO getIfMappInfo(IfMappVO ifMappVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getIfMappInfo(ifMappVO);
	}
	
	@Override
	public int insertIfMappInfo(IfMappVO ifMappVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).insertIfMappInfo(ifMappVO);
	}

	
	@Override
	public int updateIfMappInfo(IfMappVO ifMappVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).updateIfMappInfo(ifMappVO);
	}
	
	
	@Override
	public int deleteIfMappInfo(IfMappVO ifMappVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).deleteIfMappInfo(ifMappVO);
	}
	
	
	@Override
	public int mergeIfMappInfo(IfMappVO ifMappVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).mergeIfMappInfo(ifMappVO);
	}
	
	
	
	
	
	@Override
	public IfMappVO getIfMappCount(IfMappVO ifMappVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getIfMappInfo(ifMappVO);
	}
	
	
	@Override
	public List<IfMappVO> getIfMappNewList(IfMappVO ifMappInfoVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getIfMappNewList(ifMappInfoVO);
	}
	
	@Override
	public int deleteIfMappList(IfMappVO ifMappVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).deleteIfMappList(ifMappVO);
	}
	
	@Override
	public int insertKjSegmentInfo(SegmentVO segmentVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).insertKjSegmentInfo(segmentVO);
	}
	
	
	@Override
	public int insertKjSegmentRealInfo(SegmentVO segmentVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).insertKjSegmentRealInfo(segmentVO);
	}
	
	
	@Override
	public int insertKjSegmentRetryInfo(SegmentVO segmentVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).insertKjSegmentRetryInfo(segmentVO);
	}
	
	
	@Override
	public int updateKjSegmentInfo(SegmentVO segmentVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).updateKjSegmentInfo(segmentVO);
	}
	
	
	@Override
	public int updateKjSegmentRealInfo(SegmentVO segmentVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).updateKjSegmentRealInfo(segmentVO);
	}
	
	
	@Override
	public int updateKjSegmentRetryInfo(SegmentVO segmentVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).updateKjSegmentRetryInfo(segmentVO);
	}	
	
	@Override
	public int getKjSegmentRealCount(SegmentVO segmentVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getKjSegmentRealCount(segmentVO);
	}
	
	
	@Override
	public int getKjSegmentRetryCount(SegmentVO segmentVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getKjSegmentRetryCount(segmentVO);
	}
	 
	
	@Override
	public int deleteCampaignByIf(SegmentVO segmentVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).deleteCampaignByIf(segmentVO);
	}
	
	@Override
	public SegmentVO getSegmentNo() throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getSegmentNo();
	}
	
	
	@Override
	public CampaignVO getCampaignNo() throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getCampaignNo();
	}
	
	
	@Override
	public int updateIfBasicSegNo(IfBasicVO ifBasicVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).updateIfBasicSegNo(ifBasicVO);
	}
	
	@Override
	public int updateIfBasicCampNo(IfBasicVO ifBasicVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).updateIfBasicCampNo(ifBasicVO);
	}
	
	@Override
	public int insertKjCampaignInfo(CampaignVO campaignVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).insertKjCampaignInfo(campaignVO);
	}
	
	
	@Override
	public List<IfTaskVO> getIfTaskList(IfTaskVO ifTaskInfoVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getIfTaskList(ifTaskInfoVO);
	}
	
	
	@Override
	public int getUsedIfCampNoCount(IfBasicVO ifBasicVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getUsedIfCampNoCount(ifBasicVO);
	}
	
	
	@Override
	public List<IfCleanVO> getIfCleanList(IfCleanVO ifCleanInfoVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getIfCleanList(ifCleanInfoVO);
	}
	
	
	@Override
	public List<IfCleanVO> getIfCleanPopList(IfCleanVO ifCleanInfoVO) throws Exception {
		return sqlSessionEms.getMapper(InterfaceMapper.class).getIfCleanPopList(ifCleanInfoVO);
	}
	
}
