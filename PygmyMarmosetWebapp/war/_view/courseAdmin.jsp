<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<html>
	<head>
		<pm:headStuff title="Course admin"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<h1>All courses</h1>
			<table class="objtable">
				<tr>
					<th>Course</th><th>Term</th>
				</tr>
				<c:forEach items="${courses}" var="c">
					<tr>
						<td><a href="${pageContext.servletContext.contextPath}/a/course/${c.id}">${c.name}: ${c.title}</a></td>
						<td>${c.term}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<pm:notification/>
	</body>
</html>
