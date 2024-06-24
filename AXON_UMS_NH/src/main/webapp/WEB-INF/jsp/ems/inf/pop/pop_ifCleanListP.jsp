<%--
	/**********************************************************
	*	작성자 김준희
	*	작성일시 : 2024.05.15
	*	설명 : 연계그룹 메일 클린 팝업화면
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>

	<!-- 팝업// -->
	<div id="popup_ifCleanListP" class="poplayer "    ><!-- id값 수정 가능 -->
		<div class="inner"  style="width:1220px;height:780px;max-width:1220px;"   >
			<header>
				<h2>클린정보상세</h2>
			</header>
			<div class="popcont"  style="width:100%;height:100%;overflow-x:hidden;overflow-y:scroll"   >
				<iframe  id="iframeIfCleanListP"  src="" style="width:100%;height:820px" scrolling="no" border="no" maginwidth="0" frameborder="0" >
				</iframe>
			</div>
			<button type="button" class="btn_popclose" onclick="fn.popupClose('#popup_ifCleanListP');"><span class="hidden">팝업닫기</span></button>
		</div>
		<span class="poplayer-background" onclick="fn.popupClose('#popup_ifCleanListP');"></span>
	</div> 
	<!-- //팝업 -->
