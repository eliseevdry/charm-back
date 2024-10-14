<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Forbidden</title>
        <%@ include file="../style.html" %>
    </head>
    <body>
        <%@ include file="../header.jsp" %>
        <div>
            <h3>403 - ${wordBundle.getWord("forbidden")}</h3>
        </div>
        <%@ include file="../footer.jsp" %>
    </body>
</html>