/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.14
 * 설명 :  클린메일 VO
 */
package kr.co.enders.ums.ems.inf.vo;
 
import kr.co.enders.ums.com.vo.CommonVO;
 
public class IfCleanVO extends CommonVO {

	private String cleanYm;
	private String workDate;
	private String sendDate;
	
	private String ifMakeDate;
	private String ifCampId;
	private String recevSeqno;
	private String ifMakeSeqno;
	
	private String mid;
	private String ifId;
	private String ifName;
	private String ifEmail;
	private String recevTime;
	private String sendTime;
	private String sendResCd;
	
	private String errCnt;
	
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
	
	private String  searchRecevDate;
	private String  searchIfCampId;
	private String  searchIfCampName;
	private String  searchErrCnt;
	private String  searchCleanYm;
	private String  searchCleanYY;
	private String  searchCleanMM;
	
	private int     searchDeptNo;
	
	private int     deptNo;
	private String  ifCampName;
	private String  sendResNm;
	
	private String  uilang;
	
	private String  searchIfName;
	private String  searchIfId;
	private String  searchIfEmail;
	private String  searchStartDt;
	private String  searchEndDt;
	
	private String  searchPopIfId;

	public String getCleanYm() {
		return cleanYm;
	}

	public void setCleanYm(String cleanYm) {
		this.cleanYm = cleanYm;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getIfMakeDate() {
		return ifMakeDate;
	}

	public void setIfMakeDate(String ifMakeDate) {
		this.ifMakeDate = ifMakeDate;
	}

	public String getIfCampId() {
		return ifCampId;
	}

	public void setIfCampId(String ifCampId) {
		this.ifCampId = ifCampId;
	}

	public String getRecevSeqno() {
		return recevSeqno;
	}

	public void setRecevSeqno(String recevSeqno) {
		this.recevSeqno = recevSeqno;
	}

	public String getIfMakeSeqno() {
		return ifMakeSeqno;
	}

	public void setIfMakeSeqno(String ifMakeSeqno) {
		this.ifMakeSeqno = ifMakeSeqno;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getIfId() {
		return ifId;
	}

	public void setIfId(String ifId) {
		this.ifId = ifId;
	}

	public String getIfName() {
		return ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
	}

	public String getIfEmail() {
		return ifEmail;
	}

	public void setIfEmail(String ifEmail) {
		this.ifEmail = ifEmail;
	}

	public String getRecevTime() {
		return recevTime;
	}

	public void setRecevTime(String recevTime) {
		this.recevTime = recevTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendResCd() {
		return sendResCd;
	}

	public void setSendResCd(String sendResCd) {
		this.sendResCd = sendResCd;
	}

	public String getErrCnt() {
		return errCnt;
	}

	public void setErrCnt(String errCnt) {
		this.errCnt = errCnt;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public String getUpdDate() {
		return updDate;
	}

	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	public String getSearchRecevDate() {
		return searchRecevDate;
	}

	public void setSearchRecevDate(String searchRecevDate) {
		this.searchRecevDate = searchRecevDate;
	}

	public String getSearchIfCampId() {
		return searchIfCampId;
	}

	public void setSearchIfCampId(String searchIfCampId) {
		this.searchIfCampId = searchIfCampId;
	}

	public String getSearchIfCampName() {
		return searchIfCampName;
	}

	public void setSearchIfCampName(String searchIfCampName) {
		this.searchIfCampName = searchIfCampName;
	}

	public String getSearchErrCnt() {
		return searchErrCnt;
	}

	public void setSearchErrCnt(String searchErrCnt) {
		this.searchErrCnt = searchErrCnt;
	}

	public String getSearchCleanYm() {
		return searchCleanYm;
	}

	public void setSearchCleanYm(String searchCleanYm) {
		this.searchCleanYm = searchCleanYm;
	}

	public String getSearchCleanYY() {
		return searchCleanYY;
	}

	public void setSearchCleanYY(String searchCleanYY) {
		this.searchCleanYY = searchCleanYY;
	}

	public String getSearchCleanMM() {
		return searchCleanMM;
	}

	public void setSearchCleanMM(String searchCleanMM) {
		this.searchCleanMM = searchCleanMM;
	}

	public int getSearchDeptNo() {
		return searchDeptNo;
	}

	public void setSearchDeptNo(int searchDeptNo) {
		this.searchDeptNo = searchDeptNo;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getIfCampName() {
		return ifCampName;
	}

	public void setIfCampName(String ifCampName) {
		this.ifCampName = ifCampName;
	}

	public String getSendResNm() {
		return sendResNm;
	}

	public void setSendResNm(String sendResNm) {
		this.sendResNm = sendResNm;
	}

	public String getUilang() {
		return uilang;
	}

	public void setUilang(String uilang) {
		this.uilang = uilang;
	}

	public String getSearchIfName() {
		return searchIfName;
	}

	public void setSearchIfName(String searchIfName) {
		this.searchIfName = searchIfName;
	}

	public String getSearchIfId() {
		return searchIfId;
	}

	public void setSearchIfId(String searchIfId) {
		this.searchIfId = searchIfId;
	}

	public String getSearchIfEmail() {
		return searchIfEmail;
	}

	public void setSearchIfEmail(String searchIfEmail) {
		this.searchIfEmail = searchIfEmail;
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

	public String getSearchPopIfId() {
		return searchPopIfId;
	}

	public void setSearchPopIfId(String searchPopIfId) {
		this.searchPopIfId = searchPopIfId;
	}

}
