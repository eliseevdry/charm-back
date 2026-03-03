<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Not Found</title>
        <%@ include file="style.html" %>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <img src="${pageContext.request.contextPath}/img/search.png" width="75" class="icon">
            <h3>${wordBundle.getWord("next-no-more")}</h3>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>