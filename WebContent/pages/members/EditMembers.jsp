<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tags/ktm-libs.tld" prefix="ktm" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${ktm:getText("page.members.title")}</title>
</head>
<body>
<div>
    <h1>${ktm:getText("page.members.title")}</h1>
    <div>
        <form method="post" action="CRUDMembers">
        <fieldset>
            <input type="hidden" name="uniqueId" value="${bean.uniqueId}">
            <input type="hidden" name="method" value="save">
            <label for="username">Name</label>
            <input type="text" name="username" id="username" value="${bean.username}" class="text ui-widget-content ui-corner-all" />
            <br><label for="email">Email</label>
            <input type="text" name="email" id="email" value="${bean.email}" class="text ui-widget-content ui-corner-all" />
            <br><label for="tel">tel</label>
            <input type="text" name="tel" id="tel" value="${bean.tel}" class="text ui-widget-content ui-corner-all" />
            <br><label for="password">Password</label>
            <input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all" />
            <br><label for="password_confirm">Confirm password</label>
            <input type="password" name="password_confirm" id="password_confirm" value="" class="text ui-widget-content ui-corner-all" />
            <br>
            <label for="uniqueId">Division</label>
            <select name="divisionId" id="divisionId" size="1">
              <option value="0" selected="selected" disabled="disabled">${ktm:getText("page.choose")}</option>
              <ktm:options selected="divisionId" bean="bean" label="name" value="uniqueId" collection="divisionCollection" />
            </select>
        </fieldset>
        <input type="submit" value="save">
        </form>
    </div>
</div>
<script src="js/jquery-1.8.2.js"></script>
<script src="js/jquery-ui-1.9.0.custom.js"></script>
</body>
</html>