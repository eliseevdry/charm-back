<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm</title>
        <%@ include file="style.html" %>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <form method="post" action="/charm">
                <input type="hidden" name="toProfile" value="${next.id}">
                <div>
                    <h3>${wordBundle.getWord("how-about")} ${next.name} ${next.surname}, ${next.age} ?</h3>
                </div>
                <table>
                    <tr class="hiddenRow">
                        <td>
                            <c:if test="${next.photo != null}">
                                <img src="content${next.photo}" height="500">
                                <br>
                            </c:if>
                            <c:if test="${next.photo == null}">
                                <img src="${pageContext.request.contextPath}/img/empty_profile.png" height="500"/>
                                <br>
                            </c:if>
                        </td>
                    </tr>
                    <tr class="hiddenRow">
                        <td><h4>${next.about}</h4></td>
                    </tr>
                </table>
                <table>
                    <tr class="hiddenRow">
                        <td>
                            <button type="submit" name="action" value="LIKE" class="hiddenButton">
                                <img src="${pageContext.request.contextPath}/img/thumb-up.png" width="75" class="icon"/>
                            </button>
                        </td>
                        <td>
                            <button type="submit" name="action" value="SKIP" class="hiddenButton">
                                <img src="${pageContext.request.contextPath}/img/arrow-right.png" width="75" class="icon"/>
                            </button>
                        </td>
                        <td>
                            <button type="submit" name="action" value="DISLIKE" class="hiddenButton">
                                <img src="${pageContext.request.contextPath}/img/thumb-down.png" width="75" class="icon"/>
                            </button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>