<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <h3>Charm <3</h3>
    <form method="post" action="/lang">
        <button type="submit" name="lang" value="ru">ru</button>
        <button type="submit" name="lang" value="en">en</button>
    </form>
    <h3>Your current language: ${cookie["lang"].value}</h3>
    <hr>
</div>