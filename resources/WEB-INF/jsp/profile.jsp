<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Profile</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <form method="post" action="/profile">
                <c:if test="${requestScope.profile.id != null}">
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
                <input type="hidden" name="id" value="${requestScope.profile.id}">
                <table>
                    <tr>
                        <td><h3>${requestScope.wordBundle.getWord("email")}</h3></td>
                        <td><input type="email" name="email" value="${requestScope.profile.email}"></td>
                    </tr>
                    <tr>
                        <td><h3>${requestScope.wordBundle.getWord("name")}</h3></td>
                        <td><input type="text" name="name" value="${requestScope.profile.name}"></td>
                    </tr>
                    <tr>
                        <td><h3>${requestScope.wordBundle.getWord("surname")}</h3></td>
                        <td><input type="text" name="surname" value="${requestScope.profile.surname}"></td>
                    </tr>
                    <tr>
                        <td><h3>${requestScope.wordBundle.getWord("birth-date")}</h3></td>
                        <td><input type="date" name="birthDate" value="${requestScope.profile.birthDate}"></td>
                    </tr>
                    <tr>
                        <td><h3>${requestScope.wordBundle.getWord("age")}</h3></td>
                        <td><h3>${requestScope.profile.age}</h3></td>
                    </tr>
                    <tr>
                        <td><h3>${requestScope.wordBundle.getWord("about")}</h3></td>
                        <td><input type="text" name="about" value="${requestScope.profile.about}"></td>
                    </tr>
                    <tr>
                        <td><h3>${requestScope.wordBundle.getWord("gender")}</h3></td>
                        <td>
                            <select name="gender">
                                <option value="${requestScope.profile.gender}" selected hidden>
                                    ${requestScope.profile.gender}
                                </option>
                                <c:forEach var="gender" items="${applicationScope.genders}">
                                    <option value="${gender}">${requestScope.wordBundle.getWord(gender)}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
                <button type="submit">${requestScope.wordBundle.getWord("save")}</button>
            </form>
            <c:if test="${requestScope.profile.id != null}">
                <form method="post" action="/profile">
                    <input type="hidden" name="_method" value="delete"/>
                    <input type="hidden" name="id" value="${requestScope.profile.id}">
                    <button type="submit">${requestScope.wordBundle.getWord("delete")}</button>
                </form>
            </c:if>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>