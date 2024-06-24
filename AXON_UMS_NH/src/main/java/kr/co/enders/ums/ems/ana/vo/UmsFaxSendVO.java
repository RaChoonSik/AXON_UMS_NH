/**
 * 작성자 : 김준희
 * 작성일시 : 2021.08.03
 * 설명 : 발송로그 VO
 */
package kr.co.enders.ums.ems.ana.vo;

import java.util.HashMap;
import java.util.List;

import kr.co.enders.ums.com.vo.CommonVO;

public class UmsFaxSendVO extends CommonVO {
	private int umsFaxSeq;         //
	private String toId;           //고객번호
	private String toName;         //수신자이름
	private String toPhone;        //수신자폰번호
	private String toFax;          //수신자FAX번호
	private String fromId;        //발신자사번
	private String fromDept;       //발신자부서코드
	private String fromCntr;       //발신자센터코드
	private String fromGrp;        //발신자그룹
	private String fromTeam;       //발신자팀코드
	private String fromDeptName;   //발신자부서명
	private String fromName;       //발신자이름
	private String fromFax;        //발신자FAX번호
	private String faxCode;        //팩스유형코드
	private String subject;        //팩스제목
	private String targetFlag;     //타케팅여부
	private String targetDate;     //발송예약일시
	private String regDate;        //타케팅시간
	private String sendTime;       //발송완료시간
	private String errorCode;      //발송결과코드
	private String workday;        //작업일자
	private int seqno;             //순번
	private int memberIdSeq;       //
	private String updateDate;     //최신변경시간
	private String toRegbigint;    //주민번호
	private String reserveGubun;   //예약구분
	private String sendType;       //발송구분
	private String channelCode;    //채널코드
	private String channelName;    //채널명
	private String jobType;        //개인업무용도구분
	private String jobDesc;        //업무내용
	private String content;        //메시지내용
	private int pageCount;         //페이지수
	private String attach01;       //첨부파일01경로
	private String attach01Name;   //첨부파일01명
	private String attach02;       //첨부파일02경로
	private String attach02Name;   //첨부파일02명
	private String attach03;       //첨부파일03경로
	private String attach03Name;   //첨부파일03명
	private String map1;
	private String map2;
	private String map3;
	private String map4;
	private String resultUrl;      //결과 URL
	private String sysDcd;         //전송시스템구분코드
	private String eaiStatus;      //EAI전송상태
	private String bsarRgFlag;     //기간계등록여부

	private String uilang;
	
	// 검색
    private String searchStartDt;
    private String searchEndDt;
	private String searchToId;
	private String searchToName;
	private String searchToFax;
	private String searchFaxCode;
	private String searchSysDcd;
	private int searchUmsFaxSeq;
	
	
    public int getUmsFaxSeq() {
        return umsFaxSeq;
    }
    public void setUmsFaxSeq(int umsFaxSeq) {
        this.umsFaxSeq = umsFaxSeq;
    }
    public String getToId() {
        return toId;
    }
    public void setToId(String toId) {
        this.toId = toId;
    }
    public String getToName() {
        return toName;
    }
    public void setToName(String toName) {
        this.toName = toName;
    }
    public String getToPhone() {
        return toPhone;
    }
    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }
    public String getToFax() {
        return toFax;
    }
    public void setToFax(String toFax) {
        this.toFax = toFax;
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
    public String getFaxCode() {
        return faxCode;
    }
    public void setFaxCode(String faxCode) {
        this.faxCode = faxCode;
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
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getWorkday() {
        return workday;
    }
    public void setWorkday(String workday) {
        this.workday = workday;
    }
    public int getSeqno() {
        return seqno;
    }
    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }
    public int getMemberIdSeq() {
        return memberIdSeq;
    }
    public void setMemberIdSeq(int memberIdSeq) {
        this.memberIdSeq = memberIdSeq;
    }
    public String getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
    public String getToRegbigint() {
        return toRegbigint;
    }
    public void setToRegbigint(String toRegbigint) {
        this.toRegbigint = toRegbigint;
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
    public String getChannelCode() {
        return channelCode;
    }
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
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
    public String getAttach01() {
        return attach01;
    }
    public void setAttach01(String attach01) {
        this.attach01 = attach01;
    }
    public String getAttach01Name() {
        return attach01Name;
    }
    public void setAttach01Name(String attach01Name) {
        this.attach01Name = attach01Name;
    }
    public String getAttach02() {
        return attach02;
    }
    public void setAttach02(String attach02) {
        this.attach02 = attach02;
    }
    public String getAttach02Name() {
        return attach02Name;
    }
    public void setAttach02Name(String attach02Name) {
        this.attach02Name = attach02Name;
    }
    public String getAttach03() {
        return attach03;
    }
    public void setAttach03(String attach03) {
        this.attach03 = attach03;
    }
    public String getAttach03Name() {
        return attach03Name;
    }
    public void setAttach03Name(String attach03Name) {
        this.attach03Name = attach03Name;
    }
    public String getMap1() {
        return map1;
    }
    public void setMap1(String map1) {
        this.map1 = map1;
    }
    public String getMap2() {
        return map2;
    }
    public void setMap2(String map2) {
        this.map2 = map2;
    }
    public String getMap3() {
        return map3;
    }
    public void setMap3(String map3) {
        this.map3 = map3;
    }
    public String getMap4() {
        return map4;
    }
    public void setMap4(String map4) {
        this.map4 = map4;
    }
    public String getResultUrl() {
        return resultUrl;
    }
    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
    public String getSysDcd() {
        return sysDcd;
    }
    public void setSysDcd(String sysDcd) {
        this.sysDcd = sysDcd;
    }
    public String getEaiStatus() {
        return eaiStatus;
    }
    public void setEaiStatus(String eaiStatus) {
        this.eaiStatus = eaiStatus;
    }
    public String getBsarRgFlag() {
        return bsarRgFlag;
    }
    public void setBsarRgFlag(String bsarRgFlag) {
        this.bsarRgFlag = bsarRgFlag;
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
    public String getSearchToId() {
        return searchToId;
    }
    public void setSearchToId(String searchToId) {
        this.searchToId = searchToId;
    }
    public String getSearchToName() {
        return searchToName;
    }
    public void setSearchToName(String searchToName) {
        this.searchToName = searchToName;
    }
    public String getSearchToFax() {
        return searchToFax;
    }
    public void setSearchToFax(String searchToFax) {
        this.searchToFax = searchToFax;
    }
    public String getSearchFaxCode() {
        return searchFaxCode;
    }
    public void setSearchFaxCode(String searchFaxCode) {
        this.searchFaxCode = searchFaxCode;
    }
    public String getSearchSysDcd() {
        return searchSysDcd;
    }
    public void setSearchSysDcd(String searchSysDcd) {
        this.searchSysDcd = searchSysDcd;
    }
	public int getSearchUmsFaxSeq() {
		return searchUmsFaxSeq;
	}
	public void setSearchUmsFaxSeq(int searchUmsFaxSeq) {
		this.searchUmsFaxSeq = searchUmsFaxSeq;
	}
	public String getUilang() {
		return uilang;
	}
	public void setUilang(String uilang) {
		this.uilang = uilang;
	}
    
}