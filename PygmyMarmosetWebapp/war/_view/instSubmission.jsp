<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Project ${project.name} submission ${submission.submissionNumber} for ${student.username}"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>${courseDisplayName}: Project ${project.name} submission ${submission.submissionNumber} for ${student.username}</h1>
			<table class="objtable">
				<thead>
					<tr>
						<th>Entry</th>
						<th>Size</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${entries}" var="e">
						<tr>
							<td><a href="${pageContext.servletContext.contextPath}/i/entry/${course.id}/${project.id}/${student.id}/${submission.id}/${e.index}">${e.name}</a></td>
							<td style="text-align: right;">${e.size}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<pm:notification/>
		</div>
	</body>
</html>
