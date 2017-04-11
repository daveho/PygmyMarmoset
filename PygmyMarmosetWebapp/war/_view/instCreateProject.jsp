<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}" scope="request"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Create Project"/>
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
			<form action="${pageContext.servletContext.contextPath}/i/createProject/${course.id}" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Name:</td>
						<td><pm:input obj="project" field="name"/></td>
					</tr>
					<tr>
						<td class="formlabel">Description:</td>
						<td><pm:input obj="project" field="description"/></td>
					</tr>
					<tr>
						<td class="formlabel">Ontime deadline:</td>
						<td><pm:input id="proj-ontime" obj="project" field="ontime"/></td>
					</tr>
					<tr>
						<td class="formlabel">Late deadline:</td>
						<td><pm:input id="proj-late" obj="project" field="late"/></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Create project"></td>
					</tr>
				</table>
				
			</form>
			<pm:notification/>
		</div>
	</body>
</html>
