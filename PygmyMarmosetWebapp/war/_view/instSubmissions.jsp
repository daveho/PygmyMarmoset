<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Project ${project.name} submissions for ${student.username}"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>${courseDisplayName}: Project ${project.name} submissions for ${student.username}</h1>
			<h2>Submissions</h2>
			<table class="objtable">
				<thead>
					<tr>
						<th>Submission</th>
						<th>Date/time</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${submissions}" var="p">
						<tr>
							<td><a href="${pageContext.servletContext.contextPath}/i/submission/${course.id}/${project.id}/${student.id}/${p.first.id}">Submission ${p.first.submissionNumber}</a></td>
							<td><pm:timestamp val="${p.first.timestamp}"/></td>
							<td>${p.second}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>
