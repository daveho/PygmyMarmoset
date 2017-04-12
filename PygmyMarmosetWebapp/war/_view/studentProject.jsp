<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Project ${project.name}"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>${courseDisplayName}: Project ${project.name}</h1>
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
							<td><a href="${pageContext.servletContext.contextPath}/u/submission/${course.id}/${student.id}/${project.id}/${p.first.id}">Submission ${p.first.submissionNumber}</a></td>
							<td><pm:timestamp val="${p.first.timestamp}"/></td>
							<td>${p.second}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<h2>Options</h2>
			<div class="navgroup">
				<ul>
				<li><a class="navlink" href="${pageContext.servletContext.contextPath}/u/submit/${course.id}/${student.id}/${project.id}">Upload new submission</a></li>
				</ul>
			</div>
			<pm:notification/>
		</div>
	</body>
</html>
