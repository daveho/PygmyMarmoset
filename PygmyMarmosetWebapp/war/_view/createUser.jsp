<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<html>
	<head>
		<pm:headStuff title="Create user"/>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<h1>Create course</h1>
			<form action="${pageContext.servletContext.contextPath}/a/createUser" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Username:</td>
						<td><pm:input obj="newUser" field="username"/></td>
					</tr>
					<tr>
						<td class="formlabel">First name:</td>
						<td><pm:input obj="newUser" field="firstName"/></td>
					</tr>
					<tr>
						<td class="formlabel">Last name:</td>
						<td><pm:input obj="newUser" field="lastName"/></td>
					</tr>
					<tr>
						<td class="formlabel">Password:</td>
						<td><pm:input obj="newUser" field="passwordHash" type="password"/></td>
					</tr>
					<tr>
						<td class="formlabel">Superuser:</td>
						<td><pm:input obj="newUser" field="superUser"/></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Create user"></input></td>
					</tr>
				</table>
			</form>
			<c:if test="${not empty resultmsg}">
				<span class="resultmsg">${resultmsg}</span>
			</c:if>
			<c:if test="${not empty errmsg}">
				<span class="errmsg">${errmsg.text}</span>
			</c:if>
		</div>
	</body>
</html>
