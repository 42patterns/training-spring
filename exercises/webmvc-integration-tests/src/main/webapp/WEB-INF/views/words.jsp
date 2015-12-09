<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home</title>
</head>
<body>

<ul>
    <c:forEach items="${translations}" var="t">
    <li>${t.polishWord} <blockquote>${t.englishWord}</blockquote>
        </c:forEach>
</ul>

</body>
</html>
