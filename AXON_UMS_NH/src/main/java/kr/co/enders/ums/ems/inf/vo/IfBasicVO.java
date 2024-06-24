/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.14
 * 설명 :  연계정보 VO
 */
package kr.co.enders.ums.ems.inf.vo; 

import kr.co.enders.ums.com.vo.CommonVO;

public class IfBasicVO extends CommonVO {
	private String ifCampId;
	private String ifCampName;
	private String interfaceType;
	private String sendType;
	private String responseYn;
	private int    deptNo;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;
	private String status;
	private String secuMailYn; 
	private int    segNo;
	private int    campNo;
	private String campTy;
	private String mergeKey;
	private String mergeCol;
	
	private String respEaiId;
	private String itDevNm;//it담당 
	
	private String interfaceTypeNm;
	private String sendTypeNm;
	private String templetTypeNm;
	private String sendTermTypeNm;
	private String segNm;
	private String campNm;
	
	private String userId;
	private String popupYn;
	private String rnsYn;
	
	private String regNm;
	private String updNm;
	
	// 검색 데이터
	private String searchIfCampId;
	private String searchIfCampName;
	private String searchStatus;
	private int    searchDeptNo;
	private String existCheckCampId;

	private String uilang;
	private String ifCampIds;
	
	private int cnt;

	public String getIfCampId() {
		return ifCampId;
	}

	public void setIfCampId(String ifCampId) {
		this.ifCampId = ifCampId;
	}

	public String getIfCampName() {
		return ifCampName;
	}

	public void setIfCampName(String ifCampName) {
		this.ifCampName = ifCampName;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getResponseYn() {
		return responseYn;
	}

	public void setResponseYn(String responseYn) {
		this.responseYn = responseYn;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSecuMailYn() {
		return secuMailYn;
	}

	public void setSecuMailYn(String secuMailYn) {
		this.secuMailYn = secuMailYn;
	}

	public int getSegNo() {
		return segNo;
	}

	public void setSegNo(int segNo) {
		this.segNo = segNo;
	}

	public int getCampNo() {
		return campNo;
	}

	public void setCampNo(int campNo) {
		this.campNo = campNo;
	}

	public String getCampTy() {
		return campTy;
	}

	public void setCampTy(String campTy) {
		this.campTy = campTy;
	}

	public String getMergeKey() {
		return mergeKey;
	}

	public void setMergeKey(String mergeKey) {
		this.mergeKey = mergeKey;
	}

	public String getMergeCol() {
		return mergeCol;
	}

	public void setMergeCol(String mergeCol) {
		this.mergeCol = mergeCol;
	}

	public String getRespEaiId() {
		return respEaiId;
	}

	public void setRespEaiId(String respEaiId) {
		this.respEaiId = respEaiId;
	}

	public String getItDevNm() {
		return itDevNm;
	}

	public void setItDevNm(String itDevNm) {
		this.itDevNm = itDevNm;
	}

	public String getInterfaceTypeNm() {
		return interfaceTypeNm;
	}

	public void setInterfaceTypeNm(String interfaceTypeNm) {
		this.interfaceTypeNm = interfaceTypeNm;
	}

	public String getSendTypeNm() {
		return sendTypeNm;
	}

	public void setSendTypeNm(String sendTypeNm) {
		this.sendTypeNm = sendTypeNm;
	}

	public String getTempletTypeNm() {
		return templetTypeNm;
	}

	public void setTempletTypeNm(String templetTypeNm) {
		this.templetTypeNm = templetTypeNm;
	}

	public String getSendTermTypeNm() {
		return sendTermTypeNm;
	}

	public void setSendTermTypeNm(String sendTermTypeNm) {
		this.sendTermTypeNm = sendTermTypeNm;
	}

	public String getSegNm() {
		return segNm;
	}

	public void setSegNm(String segNm) {
		this.segNm = segNm;
	}

	public String getCampNm() {
		return campNm;
	}

	public void setCampNm(String campNm) {
		this.campNm = campNm;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPopupYn() {
		return popupYn;
	}

	public void setPopupYn(String popupYn) {
		this.popupYn = popupYn;
	}

	public String getRnsYn() {
		return rnsYn;
	}

	public void setRnsYn(String rnsYn) {
		this.rnsYn = rnsYn;
	}

	public String getRegNm() {
		return regNm;
	}

	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}

	public String getUpdNm() {
		return updNm;
	}

	public void setUpdNm(String updNm) {
		this.updNm = updNm;
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

	public int getSearchDeptNo() {
		return searchDeptNo;
	}

	public void setSearchDeptNo(int searchDeptNo) {
		this.searchDeptNo = searchDeptNo;
	}

	public String getExistCheckCampId() {
		return existCheckCampId;
	}

	public void setExistCheckCampId(String existCheckCampId) {
		this.existCheckCampId = existCheckCampId;
	}

	public String getUilang() {
		return uilang;
	}

	public void setUilang(String uilang) {
		this.uilang = uilang;
	}

	public String getIfCampIds() {
		return ifCampIds;
	}

	public void setIfCampIds(String ifCampIds) {
		this.ifCampIds = ifCampIds;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	
}
