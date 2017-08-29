<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}" scope="request"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Bulk registration"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>Bulk registration in ${courseDisplayName}</h1>
			<form action="${pageContext.servletContext.contextPath}/i/bulkReg/${course.id}"
				enctype="multipart/form-data" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Section:</td>
						<td><pm:input obj="sectionNumber" field="section"/></td>
					</tr>
					<tr>
						<td class="formlabel">Roster file:</td>
						<td><input type="file" name="uploadData" required></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Register students"></td>
					</tr>
				</table>
			</form>
			<pm:notification/>
			<c:if test="${hasOutcomes}">
			<p>Bulk registration outcomes are as follows (tuples are username, new user, generated password):</p>
<pre>
<c:forEach items="${outcomes}" var="o">${o.username},${o.newUser},${o.generatedPassword}<br></c:forEach>
</pre>
			</c:if>
		</div>
	</body>
</html>