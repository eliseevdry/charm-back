<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Login</title>
        <%@ include file="style.html" %>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <form method="post" action="/login">
                <table>
                    <tr>
                        <td><h3>${wordBundle.getWord("email")}</h3></td>
                        <td><input type="email" name="email" placeholder="user@charm.ru"></td>
                    </tr>
                    <tr>
                        <td><h3>${wordBundle.getWord("password")}</h3></td>
                        <td><input type="password" name="password"></td>
                    </tr>
                </table>
                <table>
                    <tr class="hiddenRow">
                        <td>
                            <a href="/registration"><img src="${pageContext.request.contextPath}/img/pencil.png" width="75"></a>
                        </td>
                        <td>
                            <input type="image" src="${pageContext.request.contextPath}/img/arrow-right.png" width="75" alt="submit"
                                   class="icon"/>
                        </td>
                    </tr>
                </table>
            </form>
            <div style="color: red">
                <c:forEach var="error" items="${errors}">
                    <span>${wordBundle.getWord(error)}</span>
                    <br>
                </c:forEach>
            </div>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>