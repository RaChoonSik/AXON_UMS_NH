/**
 * 작성자 : 김준희
 * 작성일시 : 2024.05.14
 * 설명 :  연계정보 폼 VO
 */
 
package kr.co.enders.ums.ems.inf.vo;

import java.util.List;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.co.enders.ums.com.vo.CommonVO;
 
public class IfFormVO  extends CommonVO {
	
	private IfBasicVO ifBasicVO;
	private List<IfMappVO> ifMappVOList;
	private String uilang; // 언어권
	public IfBasicVO getIfBasicVO() {
		return ifBasicVO;
	}
	public void setIfBasicVO(IfBasicVO ifBasicVO) {
		this.ifBasicVO = ifBasicVO;
	}
	public List<IfMappVO> getIfMappVOList() {
		return ifMappVOList;
	}
	public void setIfMappVOList(List<IfMappVO> ifMappVOList) {
		this.ifMappVOList = ifMappVOList;
	}
	public String getUilang() {
		return uilang;
	}
	public void setUilang(String uilang) {
		this.uilang = uilang;
	}

}
