<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<html>
	<head>
		<pm:headStuff title="Admin Home"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<h1>Admin Home</h1>
			<div class="navgroup">
				<ul>
				<li><a class="navlink" href="${pageContext.servletContext.contextPath}/a/createCourse">Create course</a></li>
				<li><a class="navlink" href="${pageContext.servletContext.contextPath}/a/createUser">Create user</a></li>
				<li><a class="navlink" href="${pageContext.servletContext.contextPath}/a/courses">List courses</a></li>
				</ul>
			</div>
		</div>
	</body>
</html>
