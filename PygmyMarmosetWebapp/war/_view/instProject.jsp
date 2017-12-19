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
			<!--
			id=${project.id} courseId=${project.courseId} name=${project.name} description=${project.description} visible=${project.visible}
			-->
			<p><strong>Deadlines</strong>: Ontime <pm:timestamp val="${project.ontime}"/>, Late <pm:timestamp val="${project.late}"/>
			<h2>Activity</h2>
			<table class="objtable">
				<thead>
					<tr>
						<th><a href="${pageContext.servletContext.contextPath}/i/project/${course.id}/${project.id}?sort=last_name">Student</a><c:if test="${sort == 'last_name'}">&#x25b2;</c:if></th>
						<th>Ontime</th>
						<th>Late</th>
						<th>Very late</th>
						<th><a href="${pageContext.servletContext.contextPath}/i/project/${course.id}/${project.id}?sort=section">Section</a><c:if test="${sort == 'section'}">&#x25b2;</c:if></th>
						<th><a href="${pageContext.servletContext.contextPath}/i/project/${course.id}/${project.id}?sort=role_type">Role</a><c:if test="${sort == 'role_type'}">&#x25b2;</c:if></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${studentActivity}" var="p">
						<c:set var="submissionSum" value="${p.second[0] + p.second[1]}"/>
						<c:if test="${submissionSum == 0}"><c:set var="rowClass" value="missing"/></c:if>
						<c:if test="${submissionSum != 0}"><c:set var="rowClass" value=""/></c:if>
						<tr class="${rowClass}">
							<td><a href="${pageContext.servletContext.contextPath}/i/submissions/${course.id}/${project.id}/${p.first.id}">${p.first.lastName}, ${p.first.firstName}</a></td>
							<td style="text-align: center;">${p.second[0]}</td>
							<td style="text-align: center;">${p.second[1]}</td>
							<td style="text-align: center;">${p.second[2]}</td>
							<td style="text-align: center;">${p.third.section}</td>
							<td>${p.third.type}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<h2>Visibility</h2>
			<div>
				Project is ${project.visible ? "visible" : "not visible"}
				<form style="display: inline;" action="${pageContext.servletContext.contextPath}/i/project/${course.id}/${project.id}" method="post">
					<input type="submit" name="toggle" value="Toggle visibility">
				</form>
			</div>
			<h2>Options</h2>
			<div class="navgroup">
				<ul>
				<li><a class="navlink" href="${pageContext.servletContext.contextPath}/i/editProject/${course.id}/${project.id}">Edit project</a></li>
				<li><a class="navlink" href="${pageContext.servletContext.contextPath}/i/downloadOntime/${course.id}/${project.id}">Download ontime and late submissions</a>
				<li><a class="navlink" href="${pageContext.servletContext.contextPath}/i/downloadAll/${course.id}/${project.id}">Download all submissions</a>
				</ul>
			</div>
		</div>
	</body>
</html>
