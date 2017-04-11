<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Project ${project.name} submission ${submission.submissionNumber} for ${student.username}"/>
		<!-- Include syntaxhighlighter js/css -->
		<script src="${pageContext.servletContext.contextPath}/js/syntaxhighlighter.min.js"></script>
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/theme.css">
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>${courseDisplayName}: Project ${project.name} submission ${submission.submissionNumber} for ${student.username}</h1>
			<pm:viewEntry/>
			<pm:notification/>
		</div>
	</body>
</html>
