<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Student ${student.username}"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>${courseDisplayName}: Student ${student.username}</h1>
			<h2>Activity</h2>
			<table class="objtable">
				<thead>
					<tr>
						<th>Project</th>
						<th>Ontime</th>
						<th>Late</th>
						<th>Very late</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${studentActivity}" var="p">
						<tr>
							<td><a href="${pageContext.servletContext.contextPath}/i/submissions/${course.id}/${p.first.id}/${student.id}">${p.first.name}</a></td>
							<td>${p.second[0]}</td>
							<td>${p.second[1]}</td>
							<td>${p.second[2]}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>
