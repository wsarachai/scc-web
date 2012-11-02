<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tags/ktm-libs.tld" prefix="ktm" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${ktm:getText("page.members.title")}</title>
<link rel="stylesheet" href="css/smoothness/jquery-ui-1.9.0.custom.css" type="text/css"/>
</head>
<body>
<div>
    <h1>${ktm:getText("page.members.title")}</h1>
    <div>
        <ul>
          <ktm:if>
            <ktm:condition>${ktm:isEmptyCollection(bean.formCollection)}</ktm:condition>
          <ktm:then>
            <li>${ktm:getText("data.norecord")}</li>
          </ktm:then>
          <ktm:else>
            <ktm:iterate name="bean" property="formCollection" id="member">
              <li>
                <div>${id}. ${member.username} ${member.tel} ${member.email}
                  <input type="button" value="${ktm:getText('page.btn.edit')}" onclick="goTo('CRUDMembers?method=edit&uniqueId=${member.uniqueId}')">
                  <input type="button" value="${ktm:getText('page.btn.delete')}" onclick="goToConfirm('CRUDMembers?method=del&uniqueId=${member.uniqueId}')">
                </div>
              </li>
            </ktm:iterate>
            </ktm:else>
          </ktm:if>
        </ul>
        <p>
            <input type="button" value="add" onclick="goTo('CRUDMembers?method=new')">
            <input type="button" value="home" onclick="goTo('index')">
        </p>
    </div>
</div>
<div id="dialog-confirm" title='${ktm:getText("page.confirm.delete")} ${ktm:getText("page.members.title")} ?'>
  <p style="font-size: 0.9em; margin-top:5px;">${ktm:getText("page.confirm.delete.info")}</p>
</div>
<script src="js/jquery-1.8.2.js"></script>
<script src="js/jquery-ui-1.9.0.custom.js"></script>
<script src="js/ktm-lib.js"></script>
<script>
$(function() {
    $( "#dialog-confirm" ).dialog({
        autoOpen: false,
        resizable: false,
        height:250,
        modal: true,
        buttons: {
            '${ktm:getText("page.btn.delete")}': function() {
                goTo(_url);
                $( this ).dialog( "close" );
            },
            '${ktm:getText("page.btn.cancel")}': function() {
                $( this ).dialog( "close" );
            }
        }
    });
});
var _url = "";
function goToConfirm(url) {
    _url = url;
    $( "#dialog-confirm" ).dialog("open"); 
}
</script>
</body>
</html>