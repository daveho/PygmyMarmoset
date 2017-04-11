<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}" scope="request"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Register Student"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>Register student in ${courseDisplayName}</h1>
			<form action="${pageContext.servletContext.contextPath}/i/regStudent/${course.id}" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Username:</td>
						<td><pm:input obj="student" field="username"/></td>
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
						<td class="formlabel">Section:</td>
						<td><pm:input obj="role" field="section"/></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Register user"></td>
					</tr>
				</table>
				
			</form>
			<pm:notification/>
		</div>
	</body>
</html>
