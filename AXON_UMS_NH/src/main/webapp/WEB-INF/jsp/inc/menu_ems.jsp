<%--
	/**********************************************************
	*	작성자 : 김준희
	*	작성일시 : 2021.08.05
	*	설명 : 좌측메뉴(EMS)
	**********************************************************/
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>

<script type="text/javascript">
function runMenu(pMenuId, menuId, menuUrl) {
	/*
	$("#pMenuId").val(pMenuId);
	$("#menuId").val(menuId);
	$("#runMenuForm").attr("target","").attr("action",menuUrl).submit();
	*/
	document.location.href = menuUrl;
}
</script>

<form id="runMenuForm" name="runMenuForm" method="post">
<input type="hidden" id="pMenuId" name="pMenuId"/>
<input type="hidden" id="menuId" name="menuId"/>
</form>

<%-- <span class="logo-ums"><a href="/ems/index.ums"><img src="<c:url value='/img/common/logo_ums.png'/>" alt="광주은행"></a></span>
<h1>이메일 시스템</h1>

<div class="btn-mail">
	<button type="button" class="btn fullblue" onclick="javascript:goNewMail('000');">신규발송</button>
</div> --%>

<!-- 메뉴// -->
<nav class="gnb-wrap">
	<ul class="gnb">
		<c:if test="${fn:length(NEO_MENU_LIST) > 0}">
			<c:forEach items="${NEO_MENU_LIST}" var="lvl1">
				<c:if test="${lvl1.menulvlVal == 1 && lvl1.serviceGb == 10}">
					<li<c:if test="${lvl1.menuId eq NEO_P_MENU_ID}"></c:if>>
						<c:set var="pMenuClass" value=""/>
						<c:choose>
							<c:when test="${'M1001000' eq lvl1.menuId}"><c:set var="pMenuClass" value="item01"/></c:when>
							<c:when test="${'M1002000' eq lvl1.menuId}"><c:set var="pMenuClass" value="item02"/></c:when>
							<c:when test="${'M1003000' eq lvl1.menuId}"><c:set var="pMenuClass" value="item03"/></c:when>
							<c:when test="${'M1005000' eq lvl1.menuId}"><c:set var="pMenuClass" value="item05"/></c:when>
							<c:when test="${'M1004000' eq lvl1.menuId}"><c:set var="pMenuClass" value="item06"/></c:when>
							<c:when test="${'M1006000' eq lvl1.menuId}"><c:set var="pMenuClass" value="item04"/></c:when>
						</c:choose>
						<a href="#" class="btn-menu '<c:if test="${lvl1.menuId eq NEO_P_MENU_ID}"> selected </c:if>'"><span class="<c:out value='${pMenuClass}'/>"><c:out value='${lvl1.menuNm}'/></span></a>
						<c:set var="hasLvl2" value="${false}"/>
						<c:forEach items="${NEO_MENU_LIST}" var="lvl2Check">
							<c:if test="${lvl1.menuId eq lvl2Check.parentmenuId}"><c:set var="hasLvl2" value="${true}"/></c:if>
						</c:forEach>
						<c:if test="${hasLvl2}">
							<div class="dep2"<c:if test="${lvl1.menuId eq NEO_P_MENU_ID}"> style="display:block;" </c:if>>
								<ul class="list-submenu">
									<c:forEach items="${NEO_MENU_LIST}" var="lvl2">
										<c:if test="${lvl2.menulvlVal == 2 && lvl1.menuId eq lvl2.parentmenuId}">
											<li><a class="btn-submenu '<c:if test="${lvl2.menuId eq NEO_MENU_ID}"> selected </c:if>'" href="javascript:runMenu('<c:out value='${lvl1.menuId}'/>','<c:out value='${lvl2.menuId}'/>','<c:url value='${lvl2.sourcePath}'/>');"><c:out value='${lvl2.menuNm}'/></a></li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</li>
				</c:if>
			</c:forEach>
		</c:if>
	</ul>
</nav>
<!-- //메뉴 -->
