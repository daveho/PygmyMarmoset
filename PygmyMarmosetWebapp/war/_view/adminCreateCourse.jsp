<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<html>
	<head>
		<pm:headStuff title="Create course"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>Create course</h1>
			<form action="${pageContext.servletContext.contextPath}/a/createCourse" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Course name:</td>
						<td><pm:input obj="course" field="name"/></td>
					</tr>
					<tr>
						<td class="formlabel">Course title:</td>
						<td><pm:input obj="course" field="title"/></td>
					</tr>
					<tr>
						<td class="formlabel">Term:</td>
						<td><pm:chooseTerm obj="course" field="termId"/></td>
					</tr>
					<tr>
						<td class="formlabel">Year:</td>
						<td><pm:input obj="course" field="year"/></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Create course"></input></td>
					</tr>
				</table>
			</form>
			<pm:notification/>			
		</div>
	</body>
</html>
