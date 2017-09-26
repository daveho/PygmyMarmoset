<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Edit student ${student.username}"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>Edit student ${student.username} in ${courseDisplayName}</h1>
			<p>
				Leaving the password field blank will retain the current password.
			</p>
			<form action="${pageContext.servletContext.contextPath}/i/editStudent/${course.id}/${student.id}" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Username:</td>
						<td>${student.username}</td>
					</tr>
					<tr>
						<td class="formlabel">Password:</td>
						<td><pm:input obj="student" field="passwordHash" type="password"/></td>
					</tr>
					<tr>
						<td class="formlabel">First name:</td>
						<td><pm:input obj="student" field="firstName"/></td>
					</tr>
					<tr>
						<td class="formlabel">Last name:</td>
						<td><pm:input obj="student" field="lastName"/></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Edit account"></td>
					</tr>
				</table>
			</form>
			<pm:notification/>
		</div>
	</body>
</html>