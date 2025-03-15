<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Matches</title>
        <%@ include file="style.html" %>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <form action="/matches" method="get">
                <table>
                    <tr class="hiddenRow">
                        <td>
                            <input type="image" src="content/app/img/filter.png" width="75" alt="submit" class="icon"/>
                        </td>
                        <td>${wordBundle.getWord("page")}</td>
                        <td><input type="number" name="page" min="1" value="${filter.page}"
                                   style="width: 50px;"></td>
                        <td>${wordBundle.getWord("page-size")}</td>
                        <td>
                            <select name="pageSize" class="filterInput">
                                <c:forEach var="size" items="${applicationScope.availablePageSizes}">
                                    <c:if test="${size == filter.pageSize}">
                                        <option value="${size}" selected>${size}</option>
                                    </c:if>
                                    <c:if test="${size != filter.pageSize}">
                                        <option value="${size}">${size}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><h3>${wordBundle.getWord("email")}</h3></td>
                        <td><h3>${wordBundle.getWord("name")}</h3></td>
                        <td><h3>${wordBundle.getWord("surname")}</h3></td>
                        <td><h3>${wordBundle.getWord("age")}</h3></td>
                        <td><h3>${wordBundle.getWord("photo")}</h3></td>
                        <td><h3>${wordBundle.getWord("about")}</h3></td>
                    </tr>
                    <c:forEach var="match" items="${matches}">
                        <tr>
                            <td><h4>${match.email}</h4></td>
                            <td><h4>${match.name}</h4></td>
                            <td><h4>${match.surname}</h4></td>
                            <td><h4>${match.age}</h4></td>
                            <td>
                                <c:if test="${match.photo != null}">
                                    <details>
                                        <summary>${wordBundle.getWord("show")}</summary>
                                        <img src="content${match.photo}" height="250">
                                    </details>
                                </c:if>
                            </td>
                            <td>
                                <details>
                                    <summary>${wordBundle.getWord("show")}</summary>
                                    <textarea cols="100" maxlength="1000" rows="5" wrap="soft"
                                              disabled>${match.about}</textarea>
                                </details>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>