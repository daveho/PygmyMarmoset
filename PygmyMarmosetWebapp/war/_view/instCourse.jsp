<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}: ${course.title}, ${term.name}, ${course.year}"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<h1>${courseDisplayName}</h1>
			<h2>Projects</h2>
			<p>TODO: projects</p>
			<h2>Members</h2>
			<p>TODO: members</p>
			<h2>Admin</h2>
			<div class="navgroup">
				<ul>
				<li><a class="navlink" href="${pageContext.servletContext.contextPath}/i/registerStudent">Register student</a></li>
				</ul>
			</div>
		</div>
		<pm:notification/>
	</body>
</html>
