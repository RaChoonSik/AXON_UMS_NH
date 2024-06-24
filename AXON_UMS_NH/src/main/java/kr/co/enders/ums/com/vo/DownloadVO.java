/**
 * 작성자 : 김준희
 * 작성일시 : 2021.07.19
 * 설명 : 파일다운로드 VO
 */
package kr.co.enders.ums.com.vo;

public class DownloadVO {
	private String divVal;
	private int seqNo;				// 순번
	private String downType;		// 다운로드 유형
	private String tempFlPath;		// 임시파일경로명
	private String segFlPath;		// 실제파일경로명
	private String attachNm;		// 첨부파일명
	private String attachPath;		// 첨부파일경로
	private String requestKey;		// 다운로드 요청 키 
	private String requestSeq;		// 다운로드 요청 키의 순번
	
	public String getDivVal() {
		return divVal;
	}
	public void setDivVal(String divVal) {
		this.divVal = divVal;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getDownType() {
		return downType;
	}
	public void setDownType(String downType) {
		this.downType = downType;
	}
	public String getTempFlPath() {
		return tempFlPath;
	}
	public void setTempFlPath(String tempFlPath) {
		this.tempFlPath = tempFlPath;
	}
	public String getSegFlPath() {
		return segFlPath;
	}
	public void setSegFlPath(String segFlPath) {
		this.segFlPath = segFlPath;
	}
	public String getAttachNm() {
		return attachNm;
	}
	public void setAttachNm(String attachNm) {
		this.attachNm = attachNm;
	}
	public String getAttachPath() {
		return attachPath;
	}
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getRequestSeq() {
		return requestSeq;
	}
	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
	}
	
	
}
