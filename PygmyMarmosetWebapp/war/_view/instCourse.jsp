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
			<h1>${courseDisplayName}</h1>
			<h2>Projects</h2>
			<p>TODO: projects</p>
			<h2>Members</h2>
			<table class="objtable">
				<thead>
					<tr>
						<th>Name</th>
						<th>Username</th>
						<th>Section</th>
						<th>Role</th>
					</tr>
				</thead>
				<tbody>
					<!-- Roster consists of pairs of User,Role -->
					<c:forEach items="${roster}" var="pair">
						<tr>
							<td><a href="${pageContext.servletContext.contextPath}/i/course/student/${course.id}/${pair.first.id}">${pair.first.lastName}, ${pair.first.firstName}</a></td>
							<td><a href="${pageContext.servletContext.contextPath}/i/course/student/${course.id}/${pair.first.id}">${pair.first.username}</a></td>
							<td>${pair.second.section}</td>
							<td>${pair.second.type}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<h2>Admin</h2>
			<div class="navgroup">
				<ul>
				<li><a class="navlink" href="${pageContext.servletContext.contextPath}/i/course/regStudent/${course.id}">Register student</a></li>
				<li><a class="navlink" href="${pageContext.servletContext.contextPath}/i/course/addInst/${course.id}">Add instructor</a></li>
				</ul>
			</div>
			<pm:notification/>
		</div>
	</body>
</html>
