<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://cs.ycp.edu/pygmymarmoset" prefix="pm" %>
<%@ attribute name="linkpfx" required="true" %>
<%@ attribute name="downloadpfx" required="true" %>
<table class="objtable">
	<thead>
		<tr>
			<th>Submission</th>
			<th>Date/time</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${submissions}" var="p">
			<tr>
				<td>
					<a href="${linkpfx}/${p.first.id}">Submission ${p.first.submissionNumber}</a>
					(<a href="${downloadpfx}/${p.first.id}">Download</a>)
				</td>
				<td><pm:timestamp val="${p.first.timestamp}"/></td>
				<td>${p.second}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
