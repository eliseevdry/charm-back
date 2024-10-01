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
            <table>
                <tr class="hiddenRow">
                    <form method="post" action="/email?id=${profile.id}" enctype="multipart/form-data">
                        <input type="hidden" name="_method" value="put"/>
                        <input type="hidden" name="id" value="${profile.id}">
                        <table>
                            <tr>
                                <td><h3>${wordBundle.getWord("email")}</h3></td>
                                <td><input type="email" name="email" value="${profile.email}"></td>
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
                </tr>
                <tr class="hiddenRow">
                    <div style="color: red">
                        <c:forEach var="error" items="${errors}">
                            <span>${wordBundle.getWord(error)}</span>
                            <br>
                        </c:forEach>
                    </div>
                </tr>
                <tr class="hiddenRow">
                    <form method="post" action="/registration">
                        <input type="hidden" name="_method" value="delete"/>
                        <input type="hidden" name="id" value="${profile.id}">
                        <input type="image" src="content/app/img/cross.png" width="75" alt="submit" class="icon"/>
                    </form>
                </tr>
            </table>


        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>