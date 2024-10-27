<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <table>
        <tr class="hiddenRow">
            <td>
                <a href="/login"><img src="content/app/img/heart.png" width="75"></a>
            </td>
            <td>
                <form method="post" action="/lang">
                    <button type="submit" name="lang" value="ru" class="langButton">
                        <img src="content/app/img/ru.png" class="langImg">
                    </button>
                </form>
            </td>
            <td>
                <form method="post" action="/lang">
                    <button type="submit" name="lang" value="en" class="langButton">
                        <img src="content/app/img/en.png" class="langImg">
                    </button>
                </form>
            </td>
            <td>
                <c:if test="${sessionScope.userDetails == null}">
                    <a href="/login"><img src="content/app/img/key.png" width="75"></a>
                </c:if>
                <c:if test="${sessionScope.userDetails != null}">
                    <form method="post" action="/logout">
                        <input type="image" src="content/app/img/key.png" width="75" alt="submit" class="icon"
                               onclick="return confirm()"/>
                    </form>
                </c:if>
            </td>
        </tr>
    </table>
    <hr>
</div>