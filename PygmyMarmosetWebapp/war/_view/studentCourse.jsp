<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>${courseDisplayName}</h1>
			<h2>Projects</h2>
			<table class="objtable">
				<thead>
					<tr>
						<th>Project</th>
						<th>Ontime</th>
						<th>Late</th>
						<th>Submissions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${studentProjects}" var="sp">
						<tr>
							<td><a href="${pageContext.servletContext.contextPath}/u/project/${course.id}/${student.id}/${sp.first.id}">${sp.first.name}: ${sp.first.description}</a></td>
							<td><pm:timestamp val="${sp.first.ontime}"/></td>
							<td><pm:timestamp val="${sp.first.late}"/></td>
							<td>${sp.second}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<pm:notification/>
		</div>
	</body>
</html>
