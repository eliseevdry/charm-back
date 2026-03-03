<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Unauthorized</title>
        <%@ include file="../style.html" %>
    </head>
    <body>
        <%@ include file="../header.jsp" %>
        <div>
            <h3>401 - ${wordBundle.getWord("unauthorized")}</h3>
        </div>
        <%@ include file="../footer.jsp" %>
    </body>
</html>