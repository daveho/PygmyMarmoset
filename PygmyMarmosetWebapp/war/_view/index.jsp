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
			<p>Welcome to Pygmy Marmoset!</p>
			<p><pm:hello/></p>
		</div>
	</body>
</html>
