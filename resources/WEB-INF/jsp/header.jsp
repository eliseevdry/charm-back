<%@ page contentType="text/html;charset=UTF-8" %>
<div>
    <form method="post" action="/lang">
        <table>
            <tr class="hiddenRow">
                <td>
                    <a href="/"><img src="content/app/img/heart.png" alt="" width="75"></a>
                </td>
                <td>
                    <button type="submit" name="lang" value="ru" class="langButton">
                        <img src="content/app/img/ru.png" class="langImg">
                    </button>
                </td>
                <td>
                    <button type="submit" name="lang" value="en" class="langButton">
                        <img src="content/app/img/en.png" class="langImg">
                    </button>
                </td>
            </tr>
        </table>
    </form>
    <hr>
</div>