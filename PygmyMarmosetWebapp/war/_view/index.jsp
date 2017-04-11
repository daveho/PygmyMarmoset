<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<html>
	<head>
		<pm:headStuff title="Home"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>Courses</h1>
			<table class="objtable">
				<thead>
					<tr>
						<th>Course</th>
						<th>Term</th>
						<th>Role</th>
					</tr>
				</thead>
				<tbody>
					<!-- items are Triple<Course, Term, RoleType> -->
					<c:forEach items="${courses}" var="t">
						<tr>
							<td>
								<c:if test="${t.third.instructor}">
									<!-- instructor course link -->
									<a href="${pageContext.servletContext.contextPath}/i/course/${t.first.id}">${t.first.name}: ${t.first.title}</a>
								</c:if>
								<c:if test="${not t.third.instructor}">
									<!-- student course link -->
									<a href="${pageContext.servletContext.contextPath}/u/course/${t.first.id}/${user.id}">${t.first.name}: ${t.first.title}</a>
								</c:if>
							</td>
							<td>${t.second.name} ${t.first.year}</td>
							<td>${t.third}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>
