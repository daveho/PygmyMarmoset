<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<c:set var="courseDisplayName" value="${course.name}, ${term.name} ${course.year}" scope="request"/>
<html>
	<head>
		<pm:headStuff title="${courseDisplayName}: Add Instructor" ui="true"/>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#inst-username").autocomplete({
				source: function(req, resp) {
					$.post(
							// URL
							"${pageContext.servletContext.contextPath}/i/suggestUsernames/${course.id}",
							// Data to send
							{ term: req.term },
							// Success function
							function(data) {
								resp(data);
							},
							// Data type expected from server
							'json'
					);
				}
			});
		});
		</script>
	</head>
	
	<body>
		<pm:header/>
		<div id="content">
			<pm:crumbs/>
			<h1>Add instructor in ${courseDisplayName}</h1>
			<form action="${pageContext.servletContext.contextPath}/i/addInst/${course.id}" method="post">
				<table class="formtab">
					<tr>
						<td class="formlabel">Instructor username:</td>
						<td><pm:input obj="inst" field="username" id="inst-username"/></td>
					</tr>
					<tr>
						<td class="formlabel">Section:</td>
						<td><pm:input obj="role" field="section"/></td>
					</tr>
					<tr>
						<td class="formlabel"></td>
						<td><input type="submit" name="submit" value="Add instructor"></td>
					</tr>
				</table>
				
			</form>
			<pm:notification/>
		</div>
	</body>
</html>
