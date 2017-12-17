<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="ui" required="false" %>
<%@ attribute name="syntaxhighlight" required="false" %>
<title>Pygmy Marmoset: ${title}</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/pygmymarmoset.css">
<script src="${pageContext.servletContext.contextPath}/js/pygmymarmoset.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<c:if test="${ui}">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/jquery-ui-timepicker-addon.min.css">
<script src="${pageContext.servletContext.contextPath}/js/jquery-ui-timepicker-addon.min.js"></script>
</c:if>
<c:if test="${syntaxhighlight}">
<!-- Include prism js/css -->
<script src="${pageContext.servletContext.contextPath}/js/prism.js"></script>
<script src="${pageContext.servletContext.contextPath}/js/prism.clojure.js"></script>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/prism.css">
</c:if>
