/**
 * 작성자 : 김준희
 * 작성일시 : 2021.07.06
 * 설명 : 사용자 정보 VO
 * 수정자 : 김준희
 * 수정일시 : 2021.08.10
 * 수정내역 : sys.vo -->sys.acc.vo 
 */
package kr.co.enders.ums.sys.acc.vo;

import java.io.Serializable;

public class DeptProgVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int progId;			// 프로그램아이디
	private String progNm;		// 프로그램명
	private int deptNo;			// 사용자그룹번호
	private String deptNm;		// 사용자그룹명
	
	private String progTarget;	// 프로그램대상
	private String progDomain;	// 프로그램도메인
	private String progScript;	// 수행스크립트
	
	//처리위한 추가정보 
	private String regDt;		// 등록일
	private String regId;		// 등록자
	public int getProgId() {
		return progId;
	}
	public void setProgId(int progId) {
		this.progId = progId;
	}
	public String getProgNm() {
		return progNm;
	}
	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getProgTarget() {
		return progTarget;
	}
	public void setProgTarget(String progTarget) {
		this.progTarget = progTarget;
	}
	public String getProgDomain() {
		return progDomain;
	}
	public void setProgDomain(String progDomain) {
		this.progDomain = progDomain;
	}
	public String getProgScript() {
		return progScript;
	}
	public void setProgScript(String progScript) {
		this.progScript = progScript;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
