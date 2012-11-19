<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tags/ktm-libs.tld" prefix="ktm" %>
<%@ taglib uri="http://granule.com/tags" prefix="g" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>RGB7 Backoffice V4 Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }
    </style>
    <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
     <link href="assets/css/docs.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#"><img src="assets/img/logo.png" /> <span class="hidden-phone">- ${ktm:getText('project.name')}</span></a>
        </div>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row-fluid">
        
        <div class="span12">

                  <!-- =======alert Box=========-->
                <div id="login-error" class="alert alert-block alert-error" style="display:none">
                  <h4 class="alert-heading">Username or Password not correct!!</h4>
                  <p id="alert-assist">Plese contact administrator or check username / password in document </p>
                </div>
                    <!-- =======Content=========-->
                    
                 <div class="well">
                 
                 <form id="frmLogin" class="form-horizontal">
                   <div class="control-group">
                     <label class="control-label" for="loginuser">${ktm:getText('page.login.user')}</label>
                     <div class="controls">
                       <input type="text" id="loginuser" name="loginuser" placeholder="${ktm:getText('page.login.user')}">
                     </div>
                   </div>
                   <div class="control-group">
                     <label class="control-label" for="loginpassword">${ktm:getText('page.login.password')}</label>
                     <div class="controls">
                       <input type="password" id="loginpassword" name="loginpassword" placeholder="${ktm:getText('page.login.password')}">
                     </div>
                   </div>
                   <div class="control-group">
                     <div class="controls">
                       <button id="btnSubmitLogin" type="submit" class="btn  btn-large btn-danger">${ktm:getText('page.login.login')}</button>
                     </div>
                   </div>
                 </form>
                 
                 </div>
                 
                   <!-- =======Callcenter=========-->
                    
                  <p> <span class="label label-important label-large">CALL CENTER</span> Tel:  081-8825509  / Email : support@rgb7.com</p>
                 
              </div><!--/span-->
      </div><!--/row-->

      <hr>

      <footer>
        <p>&copy; RGB7 version 4.02</p>
      </footer>

    </div><!--/.fluid-container-->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/bootstrap-transition.js"></script>
    <script src="assets/js/bootstrap-alert.js"></script>
    <script src="assets/js/bootstrap-modal.js"></script>
    <script src="assets/js/bootstrap-dropdown.js"></script>
    <script src="assets/js/bootstrap-scrollspy.js"></script>
    <script src="assets/js/bootstrap-tab.js"></script>
    <script src="assets/js/bootstrap-tooltip.js"></script>
    <script src="assets/js/bootstrap-popover.js"></script>
    <script src="assets/js/bootstrap-button.js"></script>
    <script src="assets/js/bootstrap-collapse.js"></script>
    <script src="assets/js/bootstrap-carousel.js"></script>
    <script src="assets/js/bootstrap-typeahead.js"></script>
    <g:compress method="jsfastmin">
    <script src="assets/js/ktm-lib.js"></script>
    <script>
    $(function() {
      $("#frmLogin").submit(function() {
        var bValid = true;
        
        $(".control-group").removeClass("error");

        bValid = bValid && checkLength($("#loginuser"), "${ktm:getText('page.login.input')}${ktm:getText('page.login.user')}", 1, 32);
        bValid = bValid && checkLength($("#loginpassword"), "${ktm:getText('page.login.input')}${ktm:getText('page.login.password')}", 1, 32);

        $("#alert-assist").text("");

        if (bValid) {
          $.post("login", $("#frmLogin").serialize(),
              function(data) {
                var jsonData = eval('(' + data + ')');
                if (jsonData.result == "fail") {
                  $(".alert-heading").text(jsonData.message);
                  $("#alert-assist").text("${ktm:getText('page.login.assist')}");
                  $("#login-error").fadeIn();
                } else if (jsonData.result == "success") {
                  goTo(jsonData.forwardUri);
                }
              });
        } else {
          $("#login-error").fadeIn();
        }
        return false;
      });
      $("#loginuser").focus();
    });
    </script>
    </g:compress>
  </body>
</html>
