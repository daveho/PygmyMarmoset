<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
	<img id="headerImg" alt="Pygmy Marmoset photo" src="${pageContext.servletContext.contextPath}/img/pygmyMarmoset-crop.jpg">
	<div id="headerItems">
		Welcome, ${user.username} |
		<a href="${pageContext.servletContext.contextPath}/index">Home</a> |
		<c:if test="${user.superUser}">
			<a href="${pageContext.servletContext.contextPath}/a/index">Admin</a> |
		</c:if>
		<a href="${pageContext.servletContext.contextPath}/logout">Logout</a>
	</div>
</div>
