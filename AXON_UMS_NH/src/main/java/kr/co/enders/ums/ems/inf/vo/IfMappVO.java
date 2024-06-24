/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.14
 * 설명 :  연계정보 매핑 VO
 */
 
package kr.co.enders.ums.ems.inf.vo; 

import kr.co.enders.ums.com.vo.CommonVO;
 
public class IfMappVO extends CommonVO {
	
	private String ifCampId;
	private String ifListCol;
	private String ifCol;
	private String ifColName;
	private String metaCol;
	private String encType;
	private String ifColType;
	private int sortNum;
	private String regId;
	private String regDate;
	private String updId;
	private String updDate;	
	
	private String ifColOld;
	private String ifCampName;
	private String encTypeNm;
	private String ifColTypeNm;
	
	private String regNm;
	private String updNm;
	
	private String ifMappIds;
	private String ifMailColNm;
	private String ifMailCol;
	
	// 검색	
	private String searchIfCampId;
	private String searchIfCol;
	private int    searchDeptNo;
	
	private String uilang;
	
	private int cnt;
	private int rowCount;
	private String returnYn;
	public String getIfCampId() {
		return ifCampId;
	}
	public void setIfCampId(String ifCampId) {
		this.ifCampId = ifCampId;
	}
	public String getIfListCol() {
		return ifListCol;
	}
	public void setIfListCol(String ifListCol) {
		this.ifListCol = ifListCol;
	}
	public String getIfCol() {
		return ifCol;
	}
	public void setIfCol(String ifCol) {
		this.ifCol = ifCol;
	}
	public String getIfColName() {
		return ifColName;
	}
	public void setIfColName(String ifColName) {
		this.ifColName = ifColName;
	}
	public String getMetaCol() {
		return metaCol;
	}
	public void setMetaCol(String metaCol) {
		this.metaCol = metaCol;
	}
	public String getEncType() {
		return encType;
	}
	public void setEncType(String encType) {
		this.encType = encType;
	}
	public String getIfColType() {
		return ifColType;
	}
	public void setIfColType(String ifColType) {
		this.ifColType = ifColType;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
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
	public String getIfColOld() {
		return ifColOld;
	}
	public void setIfColOld(String ifColOld) {
		this.ifColOld = ifColOld;
	}
	public String getIfCampName() {
		return ifCampName;
	}
	public void setIfCampName(String ifCampName) {
		this.ifCampName = ifCampName;
	}
	public String getEncTypeNm() {
		return encTypeNm;
	}
	public void setEncTypeNm(String encTypeNm) {
		this.encTypeNm = encTypeNm;
	}
	public String getIfColTypeNm() {
		return ifColTypeNm;
	}
	public void setIfColTypeNm(String ifColTypeNm) {
		this.ifColTypeNm = ifColTypeNm;
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
	public String getIfMappIds() {
		return ifMappIds;
	}
	public void setIfMappIds(String ifMappIds) {
		this.ifMappIds = ifMappIds;
	}
	public String getIfMailColNm() {
		return ifMailColNm;
	}
	public void setIfMailColNm(String ifMailColNm) {
		this.ifMailColNm = ifMailColNm;
	}
	public String getIfMailCol() {
		return ifMailCol;
	}
	public void setIfMailCol(String ifMailCol) {
		this.ifMailCol = ifMailCol;
	}
	public String getSearchIfCampId() {
		return searchIfCampId;
	}
	public void setSearchIfCampId(String searchIfCampId) {
		this.searchIfCampId = searchIfCampId;
	}
	public String getSearchIfCol() {
		return searchIfCol;
	}
	public void setSearchIfCol(String searchIfCol) {
		this.searchIfCol = searchIfCol;
	}
	public int getSearchDeptNo() {
		return searchDeptNo;
	}
	public void setSearchDeptNo(int searchDeptNo) {
		this.searchDeptNo = searchDeptNo;
	}
	public String getUilang() {
		return uilang;
	}
	public void setUilang(String uilang) {
		this.uilang = uilang;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public String getReturnYn() {
		return returnYn;
	}
	public void setReturnYn(String returnYn) {
		this.returnYn = returnYn;
	}
	
	
}
