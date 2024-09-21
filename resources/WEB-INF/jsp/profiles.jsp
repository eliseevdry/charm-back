<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Profiles</title>
        <%@ include file="style.html" %>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <table>
                <tr>
                    <td><h3>id</h3></td>
                    <td><h3>${wordBundle.getWord("email")}</h3></td>
                    <td><h3>${wordBundle.getWord("name")}</h3></td>
                    <td><h3>${wordBundle.getWord("surname")}</h3></td>
                    <td><h3>${wordBundle.getWord("age")}</h3></td>
                    <td><h3>${wordBundle.getWord("status")}</h3></td>
                </tr>
                <c:forEach var="profile" items="${profiles}">
                    <tr>
                        <td><h4>${profile.id}</h4></td>
                        <td><h4>${profile.email}</h4></td>
                        <td><h4>${profile.name}</h4></td>
                        <td><h4>${profile.surname}</h4></td>
                        <td><h4>${profile.age}</h4></td>
                        <td>
                            <form action="/profile" method="post">
                                <input type="hidden" name="_method" value="put"/>
                                <input type="hidden" name="id" value="${profile.id}">
                                <select name="status">
                                    <option value="${profile.status}" selected hidden>
                                        ${wordBundle.getWord(profile.status)}
                                    </option>
                                    <c:forEach var="status" items="${applicationScope.statuses}">
                                        <option value="${status}">${wordBundle.getWord(status)}</option>
                                    </c:forEach>
                                </select>
                                <button type="submit">${wordBundle.getWord("save")}</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>