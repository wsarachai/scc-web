<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tags/ktm-libs.tld" prefix="ktm" %>

    <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
           <a class="brand" href="#"><img src="assets/img/logo.png" /> <span class="hidden-phone">- ${ktm:getText('project.name')}</span></a>
          <div class="btn-group pull-right">
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
              <i class="icon-user"></i> <ktm:ShowUserInfo info="username"/>
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="CRUDMembers?method=edit&module=member&uniqueId=<ktm:ShowUserInfo info="id"/>"><i class="icon-cog"></i>&nbsp;${ktm:getText('page.profile')}</a></li>
              <li class="divider"></li>
              <li><a href="logout"><i class="icon-off"></i>&nbsp;${ktm:getText('page.logout')}</a></li>
            </ul>
          </div>
          <div class="nav-collapse">
            <ul class="nav">
              <li><a href="start.jsp"><span class=" icon-home icon-white"></span>${ktm:getText('page.home') }</a></li>
              <li><a href="#about">${ktm:getText('page.about')}</a></li>
              <li><a href="#contact">${ktm:getText('page.contact')}</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>