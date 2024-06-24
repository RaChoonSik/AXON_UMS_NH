<%--
	/**********************************************************
	*	작성자 김준희
	*	작성일시 : 2024.05.15
	*	설명 : 연계정보  팝업 목록화면
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>

	<!-- 팝업// -->
	<div id="popup_ifBasicListP" class="poplayer "    ><!-- id값 수정 가능 -->
		<div class="inner"  style="width:1220px;height:780px;max-width:1220px;"   >
			<header>
				<h2>연계정보선택</h2>
			</header>
			<div class="popcont"  style="width:100%;height:100%;overflow-x:hidden;overflow-y:scroll"   >
				<iframe  id="iframeIfBasicListP"  src="" style="width:100%;height:820px" scrolling="no" border="no" maginwidth="0" frameborder="0" >
				</iframe>
			</div>
			<button type="button" class="btn_popclose" onclick="fn.popupClose('#popup_ifBasicListP');"><span class="hidden">팝업닫기</span></button>
		</div>
		<span class="poplayer-background" onclick="fn.popupClose('#popup_ifBasicListP');"></span>
	</div> 
	<!-- //팝업 -->
