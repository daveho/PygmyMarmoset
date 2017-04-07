<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<html>
	<head>
		<pm:headStuff title="Course admin"/>
	</head>
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>All courses</h1>
			<table class="objtable">
				<thead>
					<tr>
						<th>Course</th><th>Term</th>
					</tr>
				</thead>
				<tbody>
					<!-- The items in ${coursesAndTerms} are Pair<Course,Term> -->
					<c:forEach items="${coursesAndTerms}" var="ct">
						<tr>
							<td><a href="${pageContext.servletContext.contextPath}/i/index/${ct.first.id}">${ct.first.name}: ${ct.first.title}</a></td>
							<td>${ct.second.name} ${ct.first.year}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<pm:notification/>
	</body>
</html>
