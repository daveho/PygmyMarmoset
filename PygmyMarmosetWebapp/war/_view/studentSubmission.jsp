<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Submission ${submission.submissionNumber} in ${project.name}"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>${courseDisplayName}: Submission ${submission.submissionNumber} in ${project.name}</h1>
			<table class="objtable">
				<thead>
					<tr>
						<td>Entry</td>
						<td>Size</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${entries}" var="e">
						<tr>
							<td>${e.name}</td>
							<td>${e.size}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<pm:notification/>
		</div>
	</body>
</html>
