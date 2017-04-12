<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}" scope="request"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Create Project" ui="true"/>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#proj-ontime").datetimepicker(pm.dateTimeOptions);
				$("#proj-late").datetimepicker(pm.dateTimeOptions);
			});
		</script>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>Create project in ${courseDisplayName}</h1>
			<pm:editProject actionuri="/i/createProject/${course.id}" submitlabel="Create project"/>
			<pm:notification/>
		</div>
	</body>
</html>
