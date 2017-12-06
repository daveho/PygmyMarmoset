<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<html>
	<head>
		<pm:headStuff title="Log in as another user" ui="true"/>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#username_field").focus();
			pm.autocompleteOn("#username_field", "${pageContext.servletContext.contextPath}/a/suggestUsernames");
		});
		</script>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>Log in as another user</h1>
			<form action="${pageContext.servletContext.contextPath}/a/userLogin" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Username:</td>
						<td><pm:input id="username_field" obj="creds" field="username"/></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Log in as user"></input></td>
					</tr>
				</table>
			</form>
			<pm:notification/>			
		</div>
	</body>
</html>
