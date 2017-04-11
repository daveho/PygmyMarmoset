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
			<h2>Activity</h2>
			<table class="objtable">
				<thead>
					<tr>
						<th>Student</th>
						<th>Submissions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${studentActivity}" var="p">
						<tr>
							<td><a href="${pageContext.servletContext.contextPath}/i/submissions/${course.id}/${project.id}/${p.first.id}">${p.first.lastName}, ${p.first.firstName}</a></td>
							<td style="text-align: right;">${p.second}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>
