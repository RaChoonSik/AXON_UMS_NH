/**
 * 작성자 : 김준희
 * 작성일시 : 2021.08.02
 * 설명 : 통계분석 매퍼
 */
package kr.co.enders.ums.ems.ana.dao;

import java.util.List;

import kr.co.enders.ums.ems.ana.vo.MailDomainVO;
import kr.co.enders.ums.ems.ana.vo.MailErrorVO;
import kr.co.enders.ums.ems.ana.vo.MailSummVO;
import kr.co.enders.ums.ems.ana.vo.PeriodSummVO;
import kr.co.enders.ums.ems.ana.vo.RespLogVO;
import kr.co.enders.ums.ems.ana.vo.SendLogVO;
import kr.co.enders.ums.ems.ana.vo.UmsFaxMasterVO;
import kr.co.enders.ums.ems.ana.vo.UmsFaxSendVO;
import kr.co.enders.ums.ems.cam.vo.CampaignVO;
import kr.co.enders.ums.ems.cam.vo.TaskVO;

public interface AnalysisMapper {
	
	/**
	 * 메일별분석 메일 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<TaskVO> getMailList(TaskVO taskVO) throws Exception; 
	
	/**
	 * 메일별분석 메일 정보 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public TaskVO getMailInfo(TaskVO taskVO) throws Exception;
	
	/**
	 * 메일별분석 결과요약 메일 발송결과 조회
	 * @param taskVO
	 * @return
	 */
	public MailSummVO getMailSummResult(TaskVO taskVO) throws Exception;
	
	/**
	 * 메일별분석 결과요약 메일 세부에러 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<MailErrorVO> getMailSummDetail(TaskVO taskVO) throws Exception;
	
	/**
	 * 발송 실패 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getFailList(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 메일별분석 세부에러 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<MailErrorVO> getMailErrorList(TaskVO taskVO) throws Exception;
	
	/**
	 * 메일별분석 도메인별 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<MailDomainVO> getMailDomainList(TaskVO taskVO) throws Exception;
	
	/**
	 * 메일별분석 발송시간별 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getMailSendHourList(TaskVO taskVO) throws Exception;
	
	/**
	 * 메일별분석 발송시간별 합계 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public SendLogVO getMailSendHourSum(TaskVO taskVO) throws Exception;
	
	/**
	 * 메일별분석 응답시간별 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<RespLogVO> getMailRespHourList(TaskVO taskVO) throws Exception;
	
	/**
	 * 메일별분석 응답시간별 합계 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public RespLogVO getMailRespHourSum(TaskVO taskVO) throws Exception;

	
	/**
	 * 정기메일별분석 고객별로그 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getTaskCustLogList(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 메일별분석 고객별로그 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getCustLogList(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 메일별분석 병합분석 메일 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<TaskVO> getJoinMailList(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 메일별분석 병합분석 발송결과 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public RespLogVO getJoinSendResult(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 메일별분석 병합분석 세부에러 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<MailErrorVO> getJoinErrorList(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 정기메일분석 메일 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<TaskVO> getTaskList(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일분석 정기메일 차수별 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<TaskVO> getTaskStepList(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일분석 메일 정보 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public TaskVO getTaskInfo(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일분석 결과요약 메일 발송결과 조회
	 * @param taskVO
	 * @return
	 */
	public MailSummVO getTaskSummResult(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일분석 결과요약 메일 세부에러 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<MailErrorVO> getTaskSummDetail(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일별분석 세부에러 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<MailErrorVO> getTaskErrorList(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일분석 도메인별 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<MailDomainVO> getTaskDomainList(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일분석 발송시간별 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getTaskSendHourList(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일분석 발송시간별 합계 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public SendLogVO getTaskSendHourSum(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일분석 응답시간별 목록 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public List<RespLogVO> getTaskRespHourList(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일분석 응답시간별 합계 조회
	 * @param taskVO
	 * @return
	 * @throws Exception
	 */
	public RespLogVO getTaskRespHourSum(TaskVO taskVO) throws Exception;
	
	/**
	 * 정기메일분석 병합분석 메일 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<TaskVO> getJoinTaskList(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 정기메일분석 병합분석 발송결과 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public RespLogVO getJoinSendResultTask(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 정기메일분석 병합분석 세부에러 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<MailErrorVO> getJoinErrorListTask(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 캠페인별분석 캠페인 정보 조회
	 * @param campaignVO
	 * @return
	 * @throws Exception
	 */
	public CampaignVO getCampaignInfo(CampaignVO campaignVO) throws Exception;
	
	/**
	 * 캠페인별분석 캠페인 메일 목록 조회
	 * @param campaignVO
	 * @return
	 * @throws Exception
	 */
	public List<MailSummVO> getCampMailList(CampaignVO campaignVO) throws Exception;
	
	/**
	 * 캠페인별분석 캠페인 메일 합계 조회
	 * @param campaignVO
	 * @return
	 * @throws Exception
	 */
	public MailSummVO getCampMailTotal(CampaignVO campaignVO) throws Exception;
	
	/**
	 * 캠페인별분석 병합분석 캠페인 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<TaskVO> getJoinCampList(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 캠페인별분석 병합분석 발송결과 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public RespLogVO getJoinSendResultCamp(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 캠페인별분석 병합분석 세부에러 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<MailErrorVO> getJoinErrorListCamp(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 기간별누적분석 월별 데이터 조회
	 * @param periodSummVO
	 * @return
	 * @throws Exception
	 */
	public List<PeriodSummVO> getPeriodSummMonthList(PeriodSummVO periodSummVO) throws Exception;
	
	/**
	 * 기간별누적분석 요일별 데이터 조회
	 * @param periodSummVO
	 * @return
	 * @throws Exception
	 */
	public List<PeriodSummVO> getPeriodSummWeekList(PeriodSummVO periodSummVO) throws Exception;
	
	/**
	 * 기간별누적분석 일자별 데이터 조회
	 * @param periodSummVO
	 * @return
	 * @throws Exception
	 */
	public List<PeriodSummVO> getPeriodSummDateList(PeriodSummVO periodSummVO) throws Exception;
	
	/**
	 * 기간별누적분석 도메인별 데이터 조회
	 * @param periodSummVO
	 * @return
	 * @throws Exception
	 */
	public List<PeriodSummVO> getPeriodSummDomainList(PeriodSummVO periodSummVO) throws Exception;
	
	/**
	 * 기간별누적분석 그룹(부서)별 데이터 조회
	 * @param periodSummVO
	 * @return
	 * @throws Exception
	 */
	public List<PeriodSummVO> getPeriodSummDeptList(PeriodSummVO periodSummVO) throws Exception;
	
	/**
	 * 기간별누적분석 사용자별 데이터 조회
	 * @param periodSummVO
	 * @return
	 * @throws Exception
	 */
	public List<PeriodSummVO> getPeriodSummUserList(PeriodSummVO periodSummVO) throws Exception;
	
	/**
	 * 기간별누적분석 캠페인별 데이터 조회
	 * @param periodSummVO
	 * @return
	 * @throws Exception
	 */
	public List<PeriodSummVO> getPeriodSummCampList(PeriodSummVO periodSummVO) throws Exception;
	
	/**
	 * 대량메일 및 실시간이메일 상세 로그목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getSendLogList(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 대량메일 상세 로그목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getSendLogListEms(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 실시간이메일 상세 로그목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getSendLogListRns(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 실시간이메일 DB 컨텐츠 보기 
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public SendLogVO getTSContentsInfo(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 대량메일 및 실시간이메일 상세 로그목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getSendLogListExcel(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 대량메일 상세 로그목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getSendLogListEmsExcel(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 실시간이메일 상세 로그목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getSendLogListRnsExcel(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 캠페인별 메일 발송 대용량 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getCampMailSendListEms(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 캠페인별 메일 발송 실시간 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getCampMailSendListRns(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 메일 발송 대용량 상세 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public SendLogVO getCampMailEms(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * 메일 발송 대용량 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getCampMailEmsList(SendLogVO sendLogVO) throws Exception;	
	
	/**
	 * 메일 발송 실시간 목록 조회
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getCampMailRnsList(SendLogVO sendLogVO) throws Exception;
	
	/**
	 * UMS FAX 발송 목록 조회
	 * @param umsFaxSendVO
	 * @return
	 * @throws Exception
	 */
	public List<UmsFaxSendVO> getUmsFaxSendList(UmsFaxSendVO umsFaxSendVO) throws Exception ;
	
	/**
	 * UMS FAX 발송(대량) 목록 조회
	 * @param umsFaxSendVO
	 * @return
	 * @throws Exception
	 */
	public List<UmsFaxMasterVO> getUmsFaxMasterList(UmsFaxMasterVO umsFaxMasterVO) throws Exception ;
	
	/**
	 * UMS FAX 발송 정보
	 * @param umsFaxSendVO
	 * @return
	 * @throws Exception
	 */
	public UmsFaxSendVO getUmsFaxInfo(UmsFaxSendVO umsFaxSendVO) throws Exception ;
	
	/**
	 * UMS FAX 발송 정보
	 * @param umsFaxMasterVO
	 * @return
	 * @throws Exception
	 */
	public UmsFaxMasterVO getUmsFaxMasterInfo(UmsFaxMasterVO umsFaxMasterVO) throws Exception ;
	
	/**
	 * 실시간 메일 발송 결과
	 * @param sendLogVO
	 * @return
	 * @throws Exception
	 */
	public List<SendLogVO> getSendResultRnsByKey(String requestKey) throws Exception;
}

