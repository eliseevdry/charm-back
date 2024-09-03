<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Profiles</title>
        <style>
            table, td {
                border: 1px solid;
                border-collapse: collapse;
            }
        </style>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <table>
                <tr>
                    <td><h3>id</h3></td>
                    <td><h3>${requestScope.wordBundle.getWord("email")}</h3></td>
                    <td><h3>${requestScope.wordBundle.getWord("name")}</h3></td>
                    <td><h3>${requestScope.wordBundle.getWord("surname")}</h3></td>
                    <td><h3>${requestScope.wordBundle.getWord("age")}</h3></td>
                </tr>
                <c:forEach var="profile" items="${requestScope.profiles}">
                    <tr>
                        <td><h3>${profile.id}</h3></td>
                        <td><h3>${profile.email}</h3></td>
                        <td><h3>${profile.name}</h3></td>
                        <td><h3>${profile.surname}</h3></td>
                        <td><h3>${profile.age}</h3></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>