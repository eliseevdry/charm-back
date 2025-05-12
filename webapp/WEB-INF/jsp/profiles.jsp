<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Charm Profiles</title>
        <%@ include file="style.html" %>
        <c:url var="baseUrl" value="${requestScope['javax.servlet.forward.servlet_path']}">
            <c:param name="emailStartWith" value="${filter.emailStartWith}"/>
            <c:param name="nameStartWith" value="${filter.nameStartWith}"/>
            <c:param name="surnameStartWith" value="${filter.surnameStartWith}"/>
            <c:param name="status" value="${filter.status}"/>
            <c:param name="gteAge" value="${filter.gteAge}"/>
            <c:param name="ltAge" value="${filter.ltAge}"/>
            <c:param name="sort" value="id"/>
        </c:url>
        <c:url var="sortUrlWithId" value="${baseUrl}">
            <c:param name="sort" value="id"/>
        </c:url>
        <c:url var="sortUrlWithEmail" value="${baseUrl}">
            <c:param name="sort" value="email"/>
        </c:url>
        <c:url var="sortUrlWithName" value="${baseUrl}">
            <c:param name="sort" value="name"/>
        </c:url>
        <c:url var="sortUrlWithSurname" value="${baseUrl}">
            <c:param name="sort" value="surname"/>
        </c:url>
        <c:url var="sortUrlWithAge" value="${baseUrl}">
            <c:param name="sort" value="birth_date"/>
        </c:url>
        <c:url var="sortUrlWithStatus" value="${baseUrl}">
            <c:param name="sort" value="status"/>
        </c:url>
        <c:url var="sortUrlWithRole" value="${baseUrl}">
            <c:param name="sort" value="role"/>
        </c:url>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>
            <form action="/profiles" method="get">
                <input type="hidden" name="sort" value="${filter.sort}">
                <table>
                    <tr class="hiddenRow">
                        <td>
                            <input type="image" src="${pageContext.request.contextPath}/img/filter.png" width="75" alt="submit" class="icon"/>
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
                                    <td>${wordBundle.getWord("age-from")}</td>
                                    <td><input type="number" name="gteAge" min="18" max="100" value="${filter.gteAge}"
                                               style="width: 50px;"></td>
                                    <td>${wordBundle.getWord("age-to")}</td>
                                    <td><input type="number" name="ltAge" min="18" max="100" value="${filter.ltAge}"
                                               style="width: 50px;"></td>
                                </tr>
                                <tr class="hiddenRow">
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
                            </table>
                        </td>
                    </tr>
                </table>
            </form>
            <form action="/profiles" method="post">
                <input type="hidden" name="_method" value="put"/>
                <table>
                    <tr>
                        <td>
                            <a href="${sortUrlWithId}" class="hiddenLink">
                                <h3>
                                    id
                                    <c:if test="${filter.sort == 'id' or filter.sort == null}">
                                        ^
                                    </c:if>
                                </h3>
                            </a>
                        </td>
                        <td>
                            <a href="${sortUrlWithEmail}" class="hiddenLink">
                                <h3>
                                    ${wordBundle.getWord("email")}
                                    <c:if test="${filter.sort == 'email'}">
                                        ^
                                    </c:if>
                                </h3>
                            </a>
                        </td>
                        <td>
                            <a href="${sortUrlWithName}" class="hiddenLink">
                                <h3>
                                    ${wordBundle.getWord("name")}
                                    <c:if test="${filter.sort == 'name'}">
                                        ^
                                    </c:if>
                                </h3>
                            </a>
                        </td>
                        <td>
                            <a href="${sortUrlWithSurname}" class="hiddenLink">
                                <h3>
                                    ${wordBundle.getWord("surname")}
                                    <c:if test="${filter.sort == 'surname'}">
                                        ^
                                    </c:if>
                                </h3>
                            </a>
                        </td>
                        <td>
                            <a href="${sortUrlWithAge}" class="hiddenLink">
                                <h3>
                                    ${wordBundle.getWord("age")}
                                    <c:if test="${filter.sort == 'birth_date'}">
                                        ^
                                    </c:if>
                                </h3>
                            </a>
                        </td>
                        <td>
                            <a href="${sortUrlWithStatus}" class="hiddenLink">
                                <h3>
                                    ${wordBundle.getWord("status")}
                                    <c:if test="${filter.sort == 'status'}">
                                        ^
                                    </c:if>
                                </h3>
                            </a>
                        </td>
                        <td>
                            <a href="${sortUrlWithRole}" class="hiddenLink">
                                <h3>
                                    ${wordBundle.getWord("role")}
                                    <c:if test="${filter.sort == 'role'}">
                                        ^
                                    </c:if>
                                </h3>
                            </a>
                        </td>
                    </tr>
                    <c:forEach var="profile" items="${profiles}">
                        <tr>
                            <td><h4>${profile.id}</h4></td>
                            <td><h4>${profile.email}</h4></td>
                            <td><h4>${profile.name}</h4></td>
                            <td><h4>${profile.surname}</h4></td>
                            <td><h4>${profile.age}</h4></td>
                            <td>
                                <c:if test="${profile.role == 'ADMIN'}">
                                    <h4>${wordBundle.getWord(profile.status)}</h4>
                                </c:if>
                                <c:if test="${profile.role != 'ADMIN'}">
                                    <select name="statusesWithIds" class="filterInput">
                                        <c:forEach var="statusVar" items="${applicationScope.statuses}">
                                            <c:if test="${statusVar == profile.status}">
                                                <option value="skip" selected>
                                                    ${wordBundle.getWord(statusVar)}
                                                </option>
                                            </c:if>
                                            <c:if test="${statusVar != profile.status}">
                                                <option value="${statusVar}_${profile.id}_${profile.version}">
                                                    ${wordBundle.getWord(statusVar)}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </c:if>
                            </td>
                            <td><h4>${profile.role}</h4></td>
                        </tr>
                    </c:forEach>
                    <tr class="hiddenRow">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>
                            <input type="image" src="${pageContext.request.contextPath}/img/floppy-disk.png" width="75" alt="submit"
                                   class="icon"/>
                        </td>
                        <td></td>
                    </tr>
                </table>
            </form>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>