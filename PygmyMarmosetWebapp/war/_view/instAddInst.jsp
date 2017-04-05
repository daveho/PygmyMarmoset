<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}" scope="request"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Add Instructor"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<h1>Add instructor in ${courseDisplayName}</h1>
			<form action="${pageContext.servletContext.contextPath}/i/addInst/${course.id}" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Instructor username:</td>
						<td><pm:input obj="inst" field="username"/></td>
					</tr>
					<tr>
						<td class="formlabel">Section:</td>
						<td><pm:input obj="role" field="section"/></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Add instructor"></input></td>
					</tr>
				</table>
				
			</form>
			<pm:notification/>
		</div>
	</body>
</html>
