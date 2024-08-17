<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
    <head>
        <title>Charm Profile</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <table>
                <tr>
                    <td><h3>Email</h3></td>
                    <td><h3>${requestScope.profile.email}</h3></td>
                </tr>
                <tr>
                    <td><h3>Name</h3></td>
                    <td><h3>${requestScope.profile.name}</h3></td>
                </tr>
                <tr>
                    <td><h3>Surname</h3></td>
                    <td><h3>${requestScope.profile.surname}</h3></td>
                </tr>
                <tr>
                    <td><h3>About</h3></td>
                    <td><h3>${requestScope.profile.about}</h3></td>
                </tr>
            </table>
        </div>
        <%@ include file="footer.html" %>
    </body>
</html>