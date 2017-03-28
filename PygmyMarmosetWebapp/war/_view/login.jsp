<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<html>
	<head>
		<title>Pygmy Marmoset: Login</title>
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/pygmymarmoset.css">
	</head>
	
	<body>
		<img style="margin-left: 20px; float: right;" alt="Pygmy Marmoset photo" src="${pageContext.servletContext.contextPath}/img/pygmyMarmoset.jpg">
		<p>Welcome to Pygmy Marmoset!</p>
		<p>Please enter your username and password:</p>
		<form action="${pageContext.servletContext.contextPath}/login" method="post">
			<table>
				<tr>
					<td class="formlabel">Username:</td>
					<td><pm:input obj="creds" field="username"/></td>
				</tr>
				<tr>
					<td class="formlabel">Password:</td>
					<td><pm:input obj="creds" field="password" type="password"/></td>
				</tr>
				<tr>
					<td class="formlabel"></td>
					<td><input type="submit" name="submit" value="Log in"></input></td>
				</tr>
				
			</table>
		</form>
	</body>
</html>
