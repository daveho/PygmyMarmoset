<%@ attribute name="actionuri" required="true" %>
<%@ attribute name="submitlabel" required="true" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<form action="${pageContext.servletContext.contextPath}${actionuri}" method="post">
	<table class="formtab">
		<tr>
			<td class="formlabel">Name:</td>
			<td><pm:input obj="project" field="name"/></td>
		</tr>
		<tr>
			<td class="formlabel">Description:</td>
			<td><pm:input obj="project" field="description"/></td>
		</tr>
		<tr>
			<td class="formlabel">Ontime deadline:</td>
			<td><pm:input id="proj-ontime" obj="project" field="ontime"/></td>
		</tr>
		<tr>
			<td class="formlabel">Late deadline:</td>
			<td><pm:input id="proj-late" obj="project" field="late"/></td>
		</tr>
		<tr>
			<td class="formlabel">Visible:</td>
			<td><pm:input obj="project" field="visible"/></td>
		</tr>
		<tr>
			<td class="formlabel"></td>
			<td><input type="submit" name="submit" value="${submitlabel}"></td>
		</tr>
	</table>
</form>
