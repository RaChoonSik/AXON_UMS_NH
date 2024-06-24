/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.15
 * 설명 : 연계정보 서비스 구현  
 */
package kr.co.enders.ums.ems.inf.service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.enders.ums.ems.cam.dao.CampaignDAO;
import kr.co.enders.ums.ems.cam.vo.CampaignVO;
import kr.co.enders.ums.ems.seg.dao.SegmentDAO;
import kr.co.enders.ums.ems.seg.vo.SegmentVO;
import kr.co.enders.ums.ems.inf.dao.InterfaceDAO;
import kr.co.enders.ums.ems.inf.vo.IfBasicVO;
import kr.co.enders.ums.ems.inf.vo.IfCleanVO;
import kr.co.enders.ums.ems.inf.vo.IfFormVO;
import kr.co.enders.ums.ems.inf.vo.IfMappVO;
import kr.co.enders.ums.ems.inf.vo.IfTaskVO; 
import kr.co.enders.util.Code;
import kr.co.enders.util.PropertiesUtil;
import kr.co.enders.util.StringUtil;

@Service
public class InterfaceServiceImpl implements InterfaceService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	@Autowired
	private InterfaceDAO interfaceDAO;
	
	@Autowired
	private CampaignDAO campaignDAO;
	
	@Autowired
	private SegmentDAO segmentDAO;
	
	@Autowired
	private PropertiesUtil properties;
	
	
	@Override
	public List<IfBasicVO> getIfBasicList(IfBasicVO ifBasicInfoVO) throws Exception {
		return interfaceDAO.getIfBasicList(ifBasicInfoVO);
	}
	
	@Override
	public IfBasicVO getIfBasicInfo(IfBasicVO ifBasicVO) throws Exception {
		return interfaceDAO.getIfBasicInfo(ifBasicVO);
	}

	@Override
	public int insertIfBasicInfo(IfBasicVO ifBasicVO) throws Exception {
		return interfaceDAO.insertIfBasicInfo(ifBasicVO);
	}

	@Override
	public int updateIfBasicInfo(IfBasicVO ifBasicVO) throws Exception {
		return  interfaceDAO.updateIfBasicInfo(ifBasicVO);
	}
	
	@Override
	public int updateIfBasic(IfBasicVO ifBasicVO) throws Exception {
		return  interfaceDAO.updateIfBasicInfo(ifBasicVO);
	}
	
	@Override
	public int insertIfForm(IfFormVO ifFormVO) throws Exception {
		
		IfBasicVO ifBasicVO =  ifFormVO.getIfBasicVO();
		List<IfBasicVO> ifBasicList = new ArrayList<IfBasicVO>();
		IfBasicVO searchBasicVo = new IfBasicVO(); 
		searchBasicVo.setExistCheckCampId(ifBasicVO.getIfCampId());
		searchBasicVo.setPage(1);
		searchBasicVo.setRows(2000);
		
		ifBasicVO.setStatus("000");
		int result = 0;
		ifBasicList = interfaceDAO.getIfBasicList(searchBasicVo);

		if( ifBasicList.size() > 0 ) {
			result = -1;
			logger.error("이미 연계아이디가 존재합니다.");
		}else {
			result = interfaceDAO.insertIfBasicInfo(ifBasicVO);
			
			IfMappVO delIfMappVO = new IfMappVO();
			delIfMappVO.setIfCampId( ifBasicVO.getIfCampId() );
			interfaceDAO.deleteIfMappList(delIfMappVO);
			
			IfMappVO ifMappVO = null;
			List<IfMappVO> ifMappVOList = ifFormVO.getIfMappVOList();
			for(int i=0;i < ifMappVOList.size();i++ ) {
				
				ifMappVO =  ifMappVOList.get(i);
				if( ifMappVO.getIfCol() ==null || "".equals(ifMappVO.getIfCol()) ) {
					continue;
				}
				ifMappVO.setIfCampId( ifBasicVO.getIfCampId() );
				ifMappVO.setRegId(ifBasicVO.getRegId());
				result = interfaceDAO.insertIfMappInfo(ifMappVO);
			}
			//campaignVO = interfaceDAO.getCampaignNo();
			CampaignVO campaignVO = new CampaignVO();
			campaignVO.setCampNm(ifBasicVO.getIfCampName() );
			campaignVO.setCampTy("000" );//판매유치
			campaignVO.setStatus( ifBasicVO.getStatus() );
			campaignVO.setDeptNo( ifBasicVO.getDeptNo() );
			campaignVO.setUserId( ifBasicVO.getUserId() );
			campaignVO.setEaiCampNo( ifBasicVO.getIfCampId() );
			campaignVO.setRegDt(StringUtil.getDate(Code.TM_YMDHMS));
			
			result = campaignDAO.insertCampaignInfo(campaignVO);
			if(campaignVO.getCampNo() != 0 ) {
				if( result > 0 ) {
					ifBasicVO.setCampNo(campaignVO.getCampNo());
					interfaceDAO.updateIfBasicCampNo(ifBasicVO);
				}
			} 


			//segmentVO  = interfaceDAO.getSegmentNo();
			SegmentVO segmentVO = new SegmentVO();
			segmentVO.setSegNm(ifBasicVO.getIfCampName());
			segmentVO.setCreateTy("002");//
			String query = makeMapSelectSQL( ifMappVOList, ifBasicVO.getIfCampId()  );
			String mergeCol = makeMapMergeColSQL( ifMappVOList, ifBasicVO.getIfCampId()  );
			segmentVO.setMergeKey(mergeCol);
			segmentVO.setMergeCol(mergeCol);
			segmentVO.setQuery(query);
			segmentVO.setUserId( ifBasicVO.getUserId() );
			segmentVO.setStatus(ifBasicVO.getStatus());
			segmentVO.setServiceGb(10);
			segmentVO.setDbConnNo(0);
			segmentVO.setDeptNo( ifBasicVO.getDeptNo() ); //추가됨
			segmentVO.setRegDt(StringUtil.getDate(Code.TM_YMDHMS) );
			segmentVO.setRegId(ifBasicVO.getRegId());
			result = segmentDAO.insertSegmentInfo(segmentVO);
			
			if(segmentVO.getSegNo() != 0 ) {
				if( result > 0 ) {
					query = makeMapSelectSQLReal( ifMappVOList, ifBasicVO.getIfCampId()  );
					segmentVO.setQuery(query);
					segmentDAO.insertSegmentRealInfo(segmentVO);
					
					query = makeMapSelectSQLRetry( ifMappVOList, ifBasicVO.getIfCampId()  );
					segmentVO.setQuery(query);
					segmentDAO.insertSegmentRetryInfo(segmentVO);
					
					// update 
					ifBasicVO.setSegNo(segmentVO.getSegNo());
					interfaceDAO.updateIfBasicSegNo(ifBasicVO);
				}
			} 
			 
		
		}
		
		return result;
	}
	
	
	
	@Override
	public int updateIfForm(IfFormVO ifFormVO) throws Exception {
		
		IfBasicVO ifBasicVO =  ifFormVO.getIfBasicVO();
		List<IfBasicVO> ifBasicList = new ArrayList<IfBasicVO>();
		IfBasicVO searchBasicVo = new IfBasicVO(); 
		//searchBasicVo.setExistCheckCampId(ifBasicVO.getIfCampId());
		searchBasicVo.setPage(1);
		searchBasicVo.setRows(2000);
		
		SegmentVO  segmentVO = null;
		CampaignVO campaignVO = null;
		
		int count  = 0;
		int	result = interfaceDAO.updateIfBasicInfo(ifBasicVO);
		
		IfMappVO delIfMappVO = new IfMappVO();
		delIfMappVO.setIfCampId( ifBasicVO.getIfCampId() );
		
		IfMappVO ifMappVO = null;
		
		interfaceDAO.deleteIfMappList(delIfMappVO);
		List<IfMappVO> ifMappVOList = ifFormVO.getIfMappVOList();
		for(int i=0;i < ifMappVOList.size();i++ ) {
			ifMappVO =  ifMappVOList.get(i);
			if( ifMappVO.getIfCol() ==null || "".equals(ifMappVO.getIfCol()) ) {
				continue;
			}
			ifMappVO.setIfCampId( ifBasicVO.getIfCampId() );
			ifMappVO.setRegId(ifBasicVO.getUpdId());
			ifMappVO.setUpdId(ifBasicVO.getUpdId());
			result = interfaceDAO.insertIfMappInfo(ifMappVO);
			logger.debug("ifMappVO.getIfMailColNm() " + ifMappVO.getIfMailColNm() );
		}
		
		ifBasicVO = interfaceDAO.getIfBasicInfo(ifBasicVO); // 나머지 값 전부가져오기
		if( ifBasicVO.getCampNo() == 0 ) {
			//값이 없음 insert
			campaignVO = interfaceDAO.getCampaignNo();
			
			campaignVO.setCampNm(ifBasicVO.getIfCampName() );
			campaignVO.setCampTy("000" );//판매유치
			campaignVO.setStatus( ifBasicVO.getStatus() );
			campaignVO.setDeptNo( ifBasicVO.getDeptNo() );
			campaignVO.setUserId( ifBasicVO.getUserId() );
			campaignVO.setEaiCampNo( ifBasicVO.getIfCampId() );
			
			campaignVO.setRegId(ifBasicVO.getUpdId());
			campaignVO.setRegDt(StringUtil.getDate(Code.TM_YMDHMS));
			campaignDAO.insertCampaignInfo(campaignVO);
			
			ifBasicVO.setCampNo(campaignVO.getCampNo());
			interfaceDAO.updateIfBasicCampNo(ifBasicVO);

		}else {	
		
			campaignVO = new CampaignVO();
			campaignVO.setCampNo( ifBasicVO.getCampNo() );
			campaignVO.setCampNm(ifBasicVO.getIfCampName() );
			campaignVO.setCampTy("000" );//판매유치
			campaignVO.setStatus( ifBasicVO.getStatus() );
			campaignVO.setDeptNo( ifBasicVO.getDeptNo() );
			campaignVO.setUserId( ifBasicVO.getUserId() );
			campaignVO.setEaiCampNo( ifBasicVO.getIfCampId() );
			
			campaignVO.setUpId(ifBasicVO.getUpdId());
			campaignVO.setUpDt(StringUtil.getDate(Code.TM_YMDHMS));
			
			
			campaignDAO.updateCampaignInfo(campaignVO);
		}
		
		if( ifBasicVO.getSegNo() == 0 ) {
			segmentVO  = interfaceDAO.getSegmentNo();
			
			segmentVO.setSegNm(ifBasicVO.getIfCampName());
			segmentVO.setCreateTy("002");//
			String query = makeMapSelectSQL( ifMappVOList, ifBasicVO.getIfCampId()  );
			String mergeCol = makeMapMergeColSQL( ifMappVOList, ifBasicVO.getIfCampId()  );
			segmentVO.setMergeKey(mergeCol);
			segmentVO.setMergeCol(mergeCol);
			segmentVO.setQuery(query);
			segmentVO.setUserId( ifBasicVO.getUserId() );
			segmentVO.setStatus(ifBasicVO.getStatus());
			segmentVO.setServiceGb(10);
			segmentVO.setDbConnNo(0);
			segmentVO.setDeptNo( ifBasicVO.getDeptNo() ); //추가됨
			
			segmentVO.setRegId(ifBasicVO.getRegId());
			segmentVO.setRegDt(StringUtil.getDate(Code.TM_YMDHMS));
			segmentDAO.insertSegmentInfo(segmentVO);
			
			query = makeMapSelectSQLReal( ifMappVOList, ifBasicVO.getIfCampId()  );
			segmentVO.setQuery(query);
			segmentDAO.insertSegmentRealInfo(segmentVO);
			
			query = makeMapSelectSQLRetry( ifMappVOList, ifBasicVO.getIfCampId()  );
			segmentVO.setQuery(query);
			segmentDAO.insertSegmentRetryInfo(segmentVO);

			// update 
			ifBasicVO.setSegNo(segmentVO.getSegNo());
			interfaceDAO.updateIfBasicSegNo(ifBasicVO);
		}else {
			segmentVO = new SegmentVO();
			
			segmentVO.setSegNo( ifBasicVO.getSegNo());
			segmentVO.setSegNm(ifBasicVO.getIfCampName());
			segmentVO.setCreateTy("002");//
			String query = makeMapSelectSQL( ifMappVOList, ifBasicVO.getIfCampId()  );
			String mergeCol = makeMapMergeColSQL( ifMappVOList, ifBasicVO.getIfCampId()  );
			segmentVO.setMergeKey(mergeCol);
			segmentVO.setMergeCol(mergeCol);
			segmentVO.setQuery(query);
			segmentVO.setUserId( ifBasicVO.getUserId() );
			segmentVO.setStatus(ifBasicVO.getStatus());
			segmentVO.setServiceGb(10);
			segmentVO.setDbConnNo(0);
			segmentVO.setDeptNo( ifBasicVO.getDeptNo() ); //추가됨

			segmentVO.setUpId(ifBasicVO.getRegId());
			segmentVO.setUpDt(StringUtil.getDate(Code.TM_YMDHMS));
			segmentDAO.updateSegmentInfo(segmentVO);
			
			query = makeMapSelectSQLReal( ifMappVOList, ifBasicVO.getIfCampId()  );
			segmentVO.setQuery(query);
			count = segmentDAO.getSegmentRealCount(  segmentVO   );
			if( count > 0 ) {
				segmentDAO.updateSegmentRealInfo(segmentVO);
			}else {
				segmentDAO.insertSegmentRealInfo(segmentVO);
			}
			
			query = makeMapSelectSQLRetry( ifMappVOList, ifBasicVO.getIfCampId()  );
			segmentVO.setQuery(query);
			count = segmentDAO.getSegmentRetryCount(  segmentVO   );
			if( count > 0 ) {
				segmentDAO.updateSegmentRetryInfo(segmentVO);
			}else {
				segmentDAO.insertSegmentRetryInfo(segmentVO);
			}
			
			
		}
		return result;
	}
	
	
	@Override
	public int deleteIfBasicInfo(IfBasicVO ifBasicVO) throws Exception {
		
		int result = 0; 

		String[] ifCampId = ifBasicVO.getIfCampIds().split(",");
		
		IfMappVO delIfMappVO = null;
		IfMappVO searchIfMappVO = null;
		CampaignVO campaignVO = null;
		SegmentVO segmentVO = null;
		IfBasicVO selIfBasicVO = null;
		int       usedCount = 0;
		
		for (int i = 0; i < ifCampId.length; i++) {
			
			searchIfMappVO = new IfMappVO(); 
			searchIfMappVO.setIfCampId( ifCampId[i]);
			
			ifBasicVO.setIfCampId( ifCampId[i] );
			
			usedCount = interfaceDAO.getUsedIfCampNoCount(ifBasicVO);
			if( usedCount > 0) {
				result = -1;
				logger.error("메일 발송 데이터가 존재해서 삭제할수 없습니다.");
			} else {
				selIfBasicVO = interfaceDAO.getIfBasicInfo(ifBasicVO);
				
				result = interfaceDAO.deleteIfBasicInfo(ifBasicVO);
				result = interfaceDAO.deleteIfMappInfoByIfCampId(ifBasicVO);
				
				campaignVO = new CampaignVO();
				campaignVO.setCampNo( selIfBasicVO.getCampNo());
				campaignVO = campaignDAO.getCampaignInfo(campaignVO);
				campaignVO.setStatus("002");
				campaignDAO.updateCampaignInfo(campaignVO);
				
				segmentVO = new SegmentVO();
				segmentVO.setSegNo( selIfBasicVO.getSegNo() );
				segmentVO.setStatus("002");
				segmentVO.setUpId(ifBasicVO.getUpdDate());
				segmentVO.setUpDt(ifBasicVO.getUpdDate());
				
				segmentDAO.updateSegmentStatus(segmentVO);
				
			}

		}
		return result;
	}
	
	///////////////////  매핑 /////////////////////
	@Override
	public List<IfMappVO> getIfMappList(IfMappVO ifMappVO) throws Exception {
		return interfaceDAO.getIfMappList(ifMappVO);
	}
	
	
	@Override
	public List<IfMappVO> getIfMailColList() throws Exception {
		return interfaceDAO.getIfMailColList();
	}
	
	@Override
	public IfMappVO getIfMappInfo(IfMappVO ifMappVO) throws Exception {
		return interfaceDAO.getIfMappInfo(ifMappVO);
	}
	
	@Override
	public int insertIfMappInfo(IfMappVO ifMappVO) throws Exception {
		return interfaceDAO.insertIfMappInfo(ifMappVO);
	}
	
	
	@Override
	public int mergeIfMappInfo(List<IfMappVO> mappList) throws Exception {
		
		IfMappVO ifMappVO = null;
		int  ret = 0;
		
		if( mappList.size() > 0) {
			ifMappVO = mappList.get(0);
			interfaceDAO.deleteIfMappList(ifMappVO);
		}
		
		for(int i=0;i < mappList.size();i++ ) {
			ifMappVO = mappList.get(i);
			ret += interfaceDAO.mergeIfMappInfo(ifMappVO);
		}
		return ret; 
	}
	


	@Override
	public int updateIfMappInfo(IfMappVO ifMappVO) throws Exception {
		return  interfaceDAO.updateIfMappInfo(ifMappVO);
	}
	
	@Override
	public int deleteIfMappInfo(IfMappVO ifMappVO) throws Exception {
		return  interfaceDAO.deleteIfMappInfo(ifMappVO);
	}
	
	@Override
	public IfMappVO getIfMappCount(IfMappVO ifMappVO) throws Exception {
		return interfaceDAO.getIfMappCount(ifMappVO);
	}
	
	@Override
	public List<IfMappVO> getIfMappNewList(IfMappVO ifMappVO) throws Exception {
		return interfaceDAO.getIfMappNewList(ifMappVO);
	}
	
	
	public  String  makeMapSelectSQL(List<IfMappVO> mapList, String campId ) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT  ");
		IfMappVO mapinfo = null;
		
		
		int lastIdx = 0;
		for(int i=0; i< mapList.size(); i++) {
			mapinfo = (IfMappVO)mapList.get(i);
			if( mapinfo.getIfCol() ==null || "".equals(mapinfo.getIfCol()) 
					||  null == mapinfo.getMetaCol()   || "".equals( mapinfo.getMetaCol()) || "@".equals( mapinfo.getMetaCol() ) ){
				continue;
			}
			lastIdx = i;
		}
		
		for(int i=0; i< mapList.size(); i++) {
			mapinfo = (IfMappVO)mapList.get(i);
			
			if( mapinfo.getIfCol() ==null || "".equals(mapinfo.getIfCol()) 
				|| null == mapinfo.getMetaCol()  || "".equals( mapinfo.getMetaCol()) || "@".equals( mapinfo.getMetaCol() ) ){
				continue;
			}
			
			if( "IF_ID".equals( mapinfo.getMetaCol()) ){
				sqlBuf.append(   mapinfo.getMetaCol()  );
				sqlBuf.append(   "||'-'||RECEV_SEQNO" );
			}else {
				sqlBuf.append(   mapinfo.getMetaCol()  );
			}
			
			sqlBuf.append( " AS " );
			sqlBuf.append( mapinfo.getIfCol());
			
			if(i != lastIdx ) {
				sqlBuf.append( ","  );
			}else {
				sqlBuf.append( " "  );
			}
		}
		sqlBuf.append("  , IF_MAKE_DATE || IF_CAMP_ID ||'.'|| RECEV_SEQNO   BIZKEY "  );
		sqlBuf.append("  FROM  IF_MAIL_INFO ");
		sqlBuf.append("  WHERE IF_MAKE_DATE = TO_CHAR(SYSDATE  ,'YYYYMMDD')  ");
		
		String serverRunMode = properties.getProperty("SERVER_RUN_MODE");

		//logger.debug("serverRunMode============" + serverRunMode );
		
		
		if( "DEV".equals(serverRunMode) ){
			sqlBuf.append("  AND   IF_CAMP_ID  = '" + campId  + "'  ");
			sqlBuf.append("  AND   WORK_STATUS = '002' AND USE_STATUS = '000'  ");
			sqlBuf.append("  AND   IF_STATUS   = '999'  ");
		}else {
			sqlBuf.append("  AND   IF_CAMP_ID = '" + campId  + "'  ");
			sqlBuf.append("  AND   WORK_STATUS = '002' AND USE_STATUS = '000'  ");
		}
		return sqlBuf.toString();
	}
	
	
	
	
	
	
	public  String  makeMapSelectSQLReal(List<IfMappVO> mapList, String campId ) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT  ");
		IfMappVO mapinfo = null;
		
		int lastIdx = 0;
		for(int i=0; i< mapList.size(); i++) {
			mapinfo = (IfMappVO)mapList.get(i);
			if( mapinfo.getIfCol() ==null || "".equals(mapinfo.getIfCol()) 
					||  null == mapinfo.getMetaCol()   || "".equals( mapinfo.getMetaCol()) || "@".equals( mapinfo.getMetaCol() ) ){
				continue;
			}
			lastIdx = i;
		}
		
		for(int i=0; i< mapList.size(); i++) {
			mapinfo = (IfMappVO)mapList.get(i);
			
			if( mapinfo.getIfCol() ==null || "".equals(mapinfo.getIfCol()) 
				|| null == mapinfo.getMetaCol()  || "".equals( mapinfo.getMetaCol()) || "@".equals( mapinfo.getMetaCol() ) ){
				continue;
			}
			
			if( "IF_ID".equals( mapinfo.getMetaCol()) ){
				sqlBuf.append(   mapinfo.getMetaCol()  );
				sqlBuf.append(   "||'-'||RECEV_SEQNO" );
			}else {
				sqlBuf.append(   mapinfo.getMetaCol()  );
			}
			sqlBuf.append( " AS " );
			sqlBuf.append( mapinfo.getIfCol());
			
			if(i != lastIdx ) {
				sqlBuf.append( ","  );
			}else {
				sqlBuf.append( " "  );
			}
		}
		sqlBuf.append("  , IF_MAKE_DATE || IF_CAMP_ID ||'.'|| RECEV_SEQNO   BIZKEY "  );
		sqlBuf.append("  FROM  IF_MAIL_INFO ");
		sqlBuf.append("  WHERE BIZKEY  = $:BIZKEY:$  ");
		
		String serverRunMode = properties.getProperty("SERVER_RUN_MODE");
		logger.debug("serverRunMode============" + serverRunMode );
		if( "DEV".equals(serverRunMode) ){
			sqlBuf.append("  AND   IF_STATUS   = '999'  ");
		}
		
		return sqlBuf.toString();
	}
	
	
	
	
	
	
	public  String  makeMapSelectSQLRetry(List<IfMappVO> mapList, String campId ) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT  ");
		IfMappVO mapinfo = null;
		String serverRunMode = properties.getProperty("SERVER_RUN_MODE");
		
		
		int lastIdx = 0;
		for(int i=0; i< mapList.size(); i++) {
			mapinfo = (IfMappVO)mapList.get(i);
			if( mapinfo.getIfCol() ==null || "".equals(mapinfo.getIfCol()) 
					||  null == mapinfo.getMetaCol()   || "".equals( mapinfo.getMetaCol()) || "@".equals( mapinfo.getMetaCol() ) ){
				continue;
			}
			lastIdx = i;
		}
		
		for(int i=0; i< mapList.size(); i++) {
			mapinfo = (IfMappVO)mapList.get(i);
			if( mapinfo.getIfCol() ==null || "".equals(mapinfo.getIfCol()) 
				|| null == mapinfo.getMetaCol()  || "".equals( mapinfo.getMetaCol()) || "@".equals( mapinfo.getMetaCol() ) ){
				continue;
			}
			if( "IF_ID".equals( mapinfo.getMetaCol()) ){
				sqlBuf.append(   mapinfo.getMetaCol()  );
				sqlBuf.append(   "||'-'||RECEV_SEQNO" );
			}else {
				sqlBuf.append(   mapinfo.getMetaCol()  );
			}
			sqlBuf.append( " AS " );
			sqlBuf.append( mapinfo.getIfCol());
			
			if(i != lastIdx ) {
				sqlBuf.append( ","  );
			}else {
				sqlBuf.append( " "  );
			}
		}
		sqlBuf.append("  , IF_MAKE_DATE || IF_CAMP_ID ||'.'|| RECEV_SEQNO   BIZKEY "  );
		sqlBuf.append("  FROM  IF_MAIL_INFO T1, ");
		sqlBuf.append("       (SELECT DISTINCT BIZKEY  "); 
		sqlBuf.append("        FROM   NEO_SENDLOG      ");
		sqlBuf.append("        WHERE  TASK_NO = $:TASK_NO:$           ");
		sqlBuf.append("        AND SUB_TASK_NO = $:SUB_TASK_NO:$      ");
		sqlBuf.append("        AND SEND_RCODE IN ( $:RCODE:$ )  )T2   ");
		sqlBuf.append("  WHERE IF_MAKE_DATE = TO_CHAR(SYSDATE  ,'YYYYMMDD')  ");
		sqlBuf.append("   AND T1.BIZKEY = T2.BIZKEY                          ");
		if( "DEV".equals(serverRunMode) ){
			sqlBuf.append("  AND   IF_CAMP_ID  = '" + campId  + "'  ");
			sqlBuf.append("  AND   IF_STATUS   = '999'  ");
		}else {
			sqlBuf.append("  AND   IF_CAMP_ID = '" + campId  + "'  ");
		}
		return sqlBuf.toString();
	}
	
	
	
	public  String  makeMapMergeColSQL(List<IfMappVO> mapList, String campId ) {
		StringBuffer sqlBuf = new StringBuffer();
		IfMappVO mapinfo = null;
		
		int lastIdx = 0;
		for(int i=0; i< mapList.size(); i++) {
			mapinfo = (IfMappVO)mapList.get(i);
			if( mapinfo.getIfCol() ==null || "".equals(mapinfo.getIfCol()) 
				|| null == mapinfo.getMetaCol()   || "".equals( mapinfo.getMetaCol()) || "@".equals( mapinfo.getMetaCol() ) ){
				continue;
			}
			lastIdx = i;
		}
		
		for(int i=0; i< mapList.size(); i++) {
			mapinfo = (IfMappVO)mapList.get(i);
			if( mapinfo.getIfCol() ==null || "".equals(mapinfo.getIfCol()) 
				|| null == mapinfo.getMetaCol()  || "".equals( mapinfo.getMetaCol()) || "@".equals( mapinfo.getMetaCol() ) ){
				continue;
			}
			sqlBuf.append( mapinfo.getIfCol());
			if(i != lastIdx ) {
				sqlBuf.append( ","  );
			}else {
				//sqlBuf.append( ""  );
			}
		}
		sqlBuf.append(",BIZKEY"  );
		
		return sqlBuf.toString();
	}
	
	
	@Override
	public List<IfTaskVO> getIfTaskList(IfTaskVO ifTaskVO) throws Exception {
		return interfaceDAO.getIfTaskList(ifTaskVO);
	}
	
	
	@Override
	public List<IfCleanVO> getIfCleanList(IfCleanVO ifCleanVO) throws Exception {
		return interfaceDAO.getIfCleanList(ifCleanVO);
	}
	
	
	@Override
	public List<IfCleanVO> getIfCleanPopList(IfCleanVO ifCleanVO) throws Exception {
		return interfaceDAO.getIfCleanPopList(ifCleanVO);
	}

}
