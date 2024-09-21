<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Email</title>
        <%@ include file="style.html" %>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <h3 style="color: red">${wordBundle.getWord("email-warning")}</h3>
            <form method="post" action="/email">
                <input type="hidden" name="_method" value="put"/>
                <input type="hidden" name="id" value="${profile.id}">
                <table>
                    <tr>
                        <td><h3>${wordBundle.getWord("email")}</h3></td>
                        <td><input type="email" name="email" value="${profile.email}"></td>
                    </tr>
                </table>
                <button type="submit">${wordBundle.getWord("save")}</button>
            </form>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>