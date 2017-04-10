<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Upload submission for ${project.name}"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>${courseDisplayName}: Upload submission for ${project.name}</h1>
			<form action="${pageContext.servletContext.contextPath}/u/submit/${course.id}/${student.id}/${project.id}"
				enctype="multipart/form-data" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Select file:</td>
						<td><input type="file" name="uploadData" required></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Upload submission"></td>
					</tr>
				</table>
			</form>
			<pm:notification/>
		</div>
	</body>
</html>
