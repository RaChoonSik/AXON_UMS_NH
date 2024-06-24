/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.14
 * 설명 :  연계 작업 VO
 */
package kr.co.enders.ums.ems.inf.vo;
 
import kr.co.enders.ums.com.vo.CommonVO;
 
public class IfTaskVO extends CommonVO {
	private String recevDate;
	private String taskSeqno;
	private String ifMakeDate;
	private String ifCampId;
	private String recevTime;
	private String fileName;
	private String fileCreateTime;
	private String workStatus;
	public String  useStatus;
	private String errMsg;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
	
	private String workKind;
	private String workKindNm;
	private String workTime;
	private String workTime2;
	
	private String workStatusNm;
	private String useStatusNm;
	private String recevTime2;
	
	private String  searchRecevDate;
	private String  searchIfCampId;
	private String  searchIfCampName;
	private String  searchStatus;
	private String  searchWorkStatus;
	
	private int    searchDeptNo;
	
	private int     deptNo;
	private String  ifCampName;
	private String uilang;
	
	private String searchStartDt;
	private String searchEndDt;
	public String getRecevDate() {
		return recevDate;
	}
	public void setRecevDate(String recevDate) {
		this.recevDate = recevDate;
	}
	public String getTaskSeqno() {
		return taskSeqno;
	}
	public void setTaskSeqno(String taskSeqno) {
		this.taskSeqno = taskSeqno;
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
	public String getRecevTime() {
		return recevTime;
	}
	public void setRecevTime(String recevTime) {
		this.recevTime = recevTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileCreateTime() {
		return fileCreateTime;
	}
	public void setFileCreateTime(String fileCreateTime) {
		this.fileCreateTime = fileCreateTime;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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
	public String getWorkKind() {
		return workKind;
	}
	public void setWorkKind(String workKind) {
		this.workKind = workKind;
	}
	public String getWorkKindNm() {
		return workKindNm;
	}
	public void setWorkKindNm(String workKindNm) {
		this.workKindNm = workKindNm;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getWorkTime2() {
		return workTime2;
	}
	public void setWorkTime2(String workTime2) {
		this.workTime2 = workTime2;
	}
	public String getWorkStatusNm() {
		return workStatusNm;
	}
	public void setWorkStatusNm(String workStatusNm) {
		this.workStatusNm = workStatusNm;
	}
	public String getUseStatusNm() {
		return useStatusNm;
	}
	public void setUseStatusNm(String useStatusNm) {
		this.useStatusNm = useStatusNm;
	}
	public String getRecevTime2() {
		return recevTime2;
	}
	public void setRecevTime2(String recevTime2) {
		this.recevTime2 = recevTime2;
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
	public String getSearchStatus() {
		return searchStatus;
	}
	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}
	public String getSearchWorkStatus() {
		return searchWorkStatus;
	}
	public void setSearchWorkStatus(String searchWorkStatus) {
		this.searchWorkStatus = searchWorkStatus;
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
	
	
}
