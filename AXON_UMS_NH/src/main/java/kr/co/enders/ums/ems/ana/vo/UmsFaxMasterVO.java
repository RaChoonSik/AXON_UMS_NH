/**
 * 작성자 : 김준희
 * 작성일시 : 2021.08.03
 * 설명 : 발송로그 VO
 */
package kr.co.enders.ums.ems.ana.vo;

import java.util.HashMap;
import java.util.List;

import kr.co.enders.ums.com.vo.CommonVO;

public class UmsFaxMasterVO extends CommonVO {
	private int umsFaxMasterSeq;         //
	private String fromId;        //발신자사번
	private String fromDept;       //발신자부서코드
	private String fromCntr;       //발신자센터코드
	private String fromGrp;        //발신자그룹
	private String fromTeam;       //발신자팀코드
	private String fromDeptName;   //발신자부서명
	private String fromName;       //발신자이름
	private String fromFax;        //발신자FAX번호 
	private String subject;        //팩스제목
	private String targetFlag;     //타케팅여부
	private String targetDate;     //발송예약일시
	private String regDate;        //타케팅시간
	private String sendTime;       //발송완료시간   
	private String updateDate;     //최신변경시간 
	private String reserveGubun;   //예약구분
	private String sendType;       //발송구분 
	private String jobType;        //개인업무용도구분
	private String jobDesc;        //업무내용
	private String content;        //메시지내용
	private int pageCount;         //페이지수
	private int targetCnt;         //대상건수
	private int successCnt;         //성공건수
	private int failCnt;         //실패건수
	private int waitCnt;         //대기건수
	private String attach01;       //첨부파일01경로 
	private String attach02;       //첨부파일02경로 
	private String attach03;       //첨부파일03경로 
	 
	private String resultUrl;      //결과 URL 

	private String uilang;
	
	// 검색
    private String searchStartDt;
    private String searchEndDt;
	private String searchFromId;
	private String searchFromName;
	private String searchFromFax;
	private int searchUmsFaxMasterSeq;
	public int getUmsFaxMasterSeq() {
		return umsFaxMasterSeq;
	}
	public void setUmsFaxMasterSeq(int umsFaxMasterSeq) {
		this.umsFaxMasterSeq = umsFaxMasterSeq;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getFromDept() {
		return fromDept;
	}
	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}
	public String getFromCntr() {
		return fromCntr;
	}
	public void setFromCntr(String fromCntr) {
		this.fromCntr = fromCntr;
	}
	public String getFromGrp() {
		return fromGrp;
	}
	public void setFromGrp(String fromGrp) {
		this.fromGrp = fromGrp;
	}
	public String getFromTeam() {
		return fromTeam;
	}
	public void setFromTeam(String fromTeam) {
		this.fromTeam = fromTeam;
	}
	public String getFromDeptName() {
		return fromDeptName;
	}
	public void setFromDeptName(String fromDeptName) {
		this.fromDeptName = fromDeptName;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getFromFax() {
		return fromFax;
	}
	public void setFromFax(String fromFax) {
		this.fromFax = fromFax;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTargetFlag() {
		return targetFlag;
	}
	public void setTargetFlag(String targetFlag) {
		this.targetFlag = targetFlag;
	}
	public String getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getReserveGubun() {
		return reserveGubun;
	}
	public void setReserveGubun(String reserveGubun) {
		this.reserveGubun = reserveGubun;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getTargetCnt() {
		return targetCnt;
	}
	public void setTargetCnt(int targetCnt) {
		this.targetCnt = targetCnt;
	}
	public int getSuccessCnt() {
		return successCnt;
	}
	public void setSuccessCnt(int successCnt) {
		this.successCnt = successCnt;
	}
	public int getFailCnt() {
		return failCnt;
	}
	public void setFailCnt(int failCnt) {
		this.failCnt = failCnt;
	}
	public int getWaitCnt() {
		return waitCnt;
	}
	public void setWaitCnt(int waitCnt) {
		this.waitCnt = waitCnt;
	}
	public String getAttach01() {
		return attach01;
	}
	public void setAttach01(String attach01) {
		this.attach01 = attach01;
	}
	public String getAttach02() {
		return attach02;
	}
	public void setAttach02(String attach02) {
		this.attach02 = attach02;
	}
	public String getAttach03() {
		return attach03;
	}
	public void setAttach03(String attach03) {
		this.attach03 = attach03;
	}
	public String getResultUrl() {
		return resultUrl;
	}
	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}
	public String getUilang() {
		return uilang;
	}
	public void setUilang(String uilang) {
		this.uilang = uilang;
	}
	public String getSearchStartDt() {
		return searchStartDt;
	}
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}
	public String getSearchEndDt() {
		return searchEndDt;
	}
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}
	public String getSearchFromId() {
		return searchFromId;
	}
	public void setSearchFromId(String searchFromId) {
		this.searchFromId = searchFromId;
	}
	public String getSearchFromName() {
		return searchFromName;
	}
	public void setSearchFromName(String searchFromName) {
		this.searchFromName = searchFromName;
	}
	public String getSearchFromFax() {
		return searchFromFax;
	}
	public void setSearchFromFax(String searchFromFax) {
		this.searchFromFax = searchFromFax;
	}
	public int getSearchUmsFaxMasterSeq() {
		return searchUmsFaxMasterSeq;
	}
	public void setSearchUmsFaxMasterSeq(int searchUmsFaxMasterSeq) {
		this.searchUmsFaxMasterSeq = searchUmsFaxMasterSeq;
	}
 
	
	
}