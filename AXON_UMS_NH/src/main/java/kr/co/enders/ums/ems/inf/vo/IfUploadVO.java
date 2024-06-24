/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.14
 * 설명 :  연계 JSP업로드 VO
 */
package kr.co.enders.ums.ems.inf.vo; 

import kr.co.enders.ums.com.vo.CommonVO;

public class IfUploadVO extends CommonVO {
	private String folder;		// 업로드 폴더
	private String inputType;	// 입력유형 -> text, textarea, select 등...(선택에 따라 uploadProcess의 ReturnValue가 달라짐)
	private String ext;			// 확장자
	private String formName;	// 폼이름
	private String title;		// 제목
	private String charset;		// 언어 변환
	private String rFileName;	// 실제파일명
	private String vFileName;	// 화면파일명
	
	private String uploadIfCampId;
	private String uploadNewYn;
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getrFileName() {
		return rFileName;
	}
	public void setrFileName(String rFileName) {
		this.rFileName = rFileName;
	}
	public String getvFileName() {
		return vFileName;
	}
	public void setvFileName(String vFileName) {
		this.vFileName = vFileName;
	}
	public String getUploadIfCampId() {
		return uploadIfCampId;
	}
	public void setUploadIfCampId(String uploadIfCampId) {
		this.uploadIfCampId = uploadIfCampId;
	}
	public String getUploadNewYn() {
		return uploadNewYn;
	}
	public void setUploadNewYn(String uploadNewYn) {
		this.uploadNewYn = uploadNewYn;
	}
}
