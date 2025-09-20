<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Credentials</title>
        <%@ include file="style.html" %>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <small style="color: red">${wordBundle.getWord("email-warning")}</small>
            <table>
                <tr class="hiddenRow">
                    <form method="post" action="/credentials">
                        <input type="hidden" name="_method" value="put"/>
                        <input type="hidden" name="id" value="${profile.id}">
                        <input type="hidden" name="version" value="${profile.version}">
                        <table>
                            <tr>
                                <td><h3>${wordBundle.getWord("email")}</h3></td>
                                <td><input type="email" name="email" value="${profile.email}"></td>
                            </tr>
                            <tr>
                                <td><h3>${wordBundle.getWord("new-password")}</h3></td>
                                <td><input type="password" name="newPassword"></td>
                            </tr>
                            <tr>
                                <td><h3>${wordBundle.getWord("confirm-password")}</h3></td>
                                <td><input type="password" name="confirm"></td>
                            </tr>
                            <tr>
                                <td><h3>${wordBundle.getWord("password")}</h3></td>
                                <td><input type="password" name="password"></td>
                            </tr>
                        </table>
                        <table>
                            <tr class="hiddenRow">
                                <td>
                                    <input type="image" src="${pageContext.request.contextPath}/img/floppy-disk.png" width="75" alt="submit"
                                           class="icon" onclick="return confirm()"/>
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
                    <form method="post" action="/profile">
                        <input type="hidden" name="_method" value="delete"/>
                        <input type="hidden" name="id" value="${profile.id}">
                        <input type="image" src="${pageContext.request.contextPath}/img/cross.png" width="75" alt="submit" class="icon"
                               onclick="return confirm()"/>
                    </form>
                </tr>
            </table>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>