<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Profile</title>
        <%@ include file="style.html" %>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <form method="post" action="/profile">
                <input type="hidden" name="_method" value="put"/>
                <input type="hidden" name="id" value="${profile.id}">
                <table>
                    <tr>
                        <td><h3>${wordBundle.getWord("email")}</h3></td>
                        <td><a href="/email?id=${profile.id}">${profile.email}</a></td>
                    </tr>
                    <tr>
                        <td><h3>${wordBundle.getWord("name")}</h3></td>
                        <td><input type="text" name="name" value="${profile.name}"></td>
                    </tr>
                    <tr>
                        <td><h3>${wordBundle.getWord("surname")}</h3></td>
                        <td><input type="text" name="surname" value="${profile.surname}"></td>
                    </tr>
                    <tr>
                        <td><h3>${wordBundle.getWord("birth-date")}</h3></td>
                        <td><input type="date" name="birthDate" value="${profile.birthDate}"></td>
                    </tr>
                    <tr>
                        <td><h3>${wordBundle.getWord("about")}</h3></td>
                        <td><input type="text" name="about" value="${profile.about}"></td>
                    </tr>
                    <tr>
                        <td><h3>${wordBundle.getWord("gender")}</h3></td>
                        <td>
                            <select name="gender">
                                <option value="${profile.gender}" selected hidden>
                                    ${wordBundle.getWord(profile.gender)}
                                </option>
                                <c:forEach var="gender" items="${applicationScope.genders}">
                                    <option value="${gender}">${wordBundle.getWord(gender)}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
                <button type="submit">${wordBundle.getWord("save")}</button>
            </form>
            <form method="post" action="/registration">
                <input type="hidden" name="_method" value="delete"/>
                <input type="hidden" name="id" value="${profile.id}">
                <button type="submit">${wordBundle.getWord("delete")}</button>
            </form>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>