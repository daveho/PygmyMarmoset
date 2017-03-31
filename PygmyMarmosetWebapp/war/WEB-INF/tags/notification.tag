<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty errmsg}">
	
	<div class="errmsg">
		<img class="errmsg-icon" alt="error icon" src="${pageContext.servletContext.contextPath}/img/error-icon-sm.png">
		<span class="msgtext">${errmsg.text}</span>
	</div>
</c:if>
<c:if test="${not empty resultmsg}">
	<div class="resultmsg">
		<img class="resultmsg-icon" alt="checkmark icon" src="${pageContext.servletContext.contextPath}/img/check-mark-icon-sm.png">
		<span class="msgtext">${resultmsg}</span>
	</div>
</c:if>
