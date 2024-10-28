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
            <form method="post" action="/profile?id=${profile.id}" enctype="multipart/form-data">
                <input type="hidden" name="_method" value="put"/>
                <input type="hidden" name="id" value="${profile.id}">
                <table>
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
                        <td><textarea name="about" cols="100" maxlength="1000" rows="5" wrap="soft">${profile.about}</textarea></td>
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
                    <tr>
                        <td><h3>${wordBundle.getWord("photo")}</h3></td>
                        <td>
                            <c:if test="${profile.photo != null}">
                                <img src="content${profile.photo}" height="500">
                                <br>
                            </c:if>
                            <input type="button" value="${wordBundle.getWord('upload')}" onclick="document.getElementById('file').click();" />
                            <input type="file" name="photo" id="file" style="display:none;">
                        </td>
                    </tr>
                </table>
                <table>
                    <tr class="hiddenRow">
                        <td>
                            <input type="image" src="content/app/img/floppy-disk.png" width="75" alt="submit" class="icon"/>
                        </td>
                        <td>
                            <a href="/credentials?id=${profile.id}"><img src="content/app/img/at-sign.png" width="75"></a>
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