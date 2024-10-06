<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Registration</title>
        <%@ include file="style.html" %>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <form method="post" action="/registration">
                <table>
                    <tr>
                        <td><h3>${wordBundle.getWord("email")}</h3></td>
                        <td><input type="email" name="email" placeholder="user@charm.ru"></td>
                    </tr>
                    <tr>
                        <td><h3>${wordBundle.getWord("password")}</h3></td>
                        <td><input type="password" name="password"></td>
                    </tr>
                    <tr>
                        <td><h3>${wordBundle.getWord("confirm-password")}</h3></td>
                        <td><input type="password" name="confirm"></td>
                    </tr>
                </table>
                <table>
                    <tr class="hiddenRow">
                        <td>
                            <input type="image" src="content/app/img/floppy-disk.png" width="75" alt="submit"
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