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
            <form action="/profiles" method="get">
                <table>
                    <tr class="hiddenRow">
                        <td>
                            <input type="image" src="content/app/img/filter.png" width="75" alt="submit" class="icon"/>
                        </td>
                        <td>
                            <table>
                                <tr class="hiddenRow">
                                    <td>${wordBundle.getWord("email")}</td>
                                    <td><input type="text" name="emailStartWith" value="${filter.emailStartWith}"
                                               class="filterInput"></td>
                                </tr>
                                <tr class="hiddenRow">
                                    <td>${wordBundle.getWord("name")}</td>
                                    <td><input type="text" name="nameStartWith" value="${filter.nameStartWith}"
                                               class="filterInput"></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table>
                                <tr class="hiddenRow">
                                    <td>${wordBundle.getWord("surname")}</td>
                                    <td><input type="text" name="surnameStartWith" value="${filter.surnameStartWith}"
                                               class="filterInput"></td>
                                </tr>
                                <tr class="hiddenRow">
                                    <td>${wordBundle.getWord("status")}</td>
                                    <td>
                                        <select name="status" class="filterInput">
                                            <option selected value></option>
                                            <c:forEach var="statusVar" items="${applicationScope.statuses}">
                                                <c:if test="${statusVar == filter.status}">
                                                    <option value="${statusVar}" selected>
                                                        ${wordBundle.getWord(statusVar)}
                                                    </option>
                                                </c:if>
                                                <c:if test="${statusVar != filter.status}">
                                                    <option value="${statusVar}">${wordBundle.getWord(statusVar)}
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table>
                                <tr class="hiddenRow">
                                    <td>${wordBundle.getWord("age")}</td>
                                    <td><input type="number" name="gteAge" min="18" max="100" value="${filter.gteAge}"
                                               style="width: 50px;"></td>
                                    <td>-</td>
                                    <td><input type="number" name="ltAge" min="18" max="100" value="${filter.ltAge}"
                                               style="width: 50px;"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </form>
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
                            <form action="/profile" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="_method" value="put"/>
                                <input type="hidden" name="id" value="${profile.id}">
                                <select name="status" class="filterInput">
                                    <c:forEach var="statusVar" items="${applicationScope.statuses}">
                                        <c:if test="${statusVar == profile.status}">
                                            <option value="${statusVar}" selected>
                                                ${wordBundle.getWord(statusVar)}
                                            </option>
                                        </c:if>
                                        <c:if test="${statusVar != profile.status}">
                                            <option value="${statusVar}">${wordBundle.getWord(statusVar)}
                                            </option>
                                        </c:if>
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