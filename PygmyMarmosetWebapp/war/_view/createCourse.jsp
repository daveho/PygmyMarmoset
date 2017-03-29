<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<html>
	<head>
		<pm:headStuff title="Create course"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<h1>Create course</h1>
			<form action="${pageContext.servletContext.contextPath}/a/createCourse" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Course name:</td>
						<td><pm:input obj="course" field="name"/></td>
					</tr>
					<tr>
						<td class="formlabel">Term:</td>
						<td><pm:input obj="course" field="term"/></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Create course"></input></td>
					</tr>
				</table>
			</form>
			<c:if test="${not empty resultmsg}">
				<span class="resultmsg">${resultmsg}</span>
			</c:if>
			<c:if test="${not empty errmsg}">
				<span class="errmsg">${errmsg}</span>
			</c:if>
		</div>
	</body>
</html>
