<%--
  Created by IntelliJ IDEA.
  User: Slava
  Date: 16.04.2014
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <link rel="stylesheet" href="/resources/scripts/messi/messi.css" />
    <script src="/resources/scripts/messi/messi.js"></script>
    <script type="text/javascript" src="/resources/js/jQuery.js"></script>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <title>Refresh data</title>
    <script>
        $( document ).ready(function() {
            $('#command').submit( function( event )
            {
                new Messi('Refreshing database, please wait.', {title: 'Loading', titleClass: 'anim warning', modal: true});
            });
        });
    </script>
    <style type="text/css">
        body
        {
            background: url(/resources/images/background4.jpg) fixed; /* Параметры фона */
        }
    </style>
</head>
<body>
<nav class="navbar navbar-inverse text-center navbar-fixed-top">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">News parser</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" style="color: white">News parser</a>
    </div>
    <div class="navbar-collapse collapse">
        <ol class="nav navbar-nav">
            <li><a href="/"  ><strong>Home page</strong></a></li>
        </ol>
        <ol class="nav navbar-nav">
            <li class="active"><a><strong>Refresh </strong></a></li>
        </ol>
    </div>
</nav>
<div class="container-fluid" style=" position: relative; top: 60px; min-height: 200px; max-height: 200px; min-width: 100%; max-width: 100%;">
<form:form method="POST" id="command" cssStyle="min-height: 90%; max-height: 90%; min-width: 100%; max-width: 100%"  commandName="refresh-form.html" action="/refresh" >
    <div class="input-group" style="position: absolute; top: 200; left: 25%; min-width:50%; max-width: 50%;">
        <span class="input-group-addon">Report</span>
        <select name="reportID" style="min-height: 60px;" id="reportSelect" class="settingSelect form-control" for="addLink">
            <c:if test="${!empty reportList}">
                <c:forEach items="${reportList}" var="reportVar">
                    <c:choose>
                        <c:when test="${reportVar.id == selectedReportID}">
                            <option value="${reportVar.id}" selected>${reportVar.name}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${reportVar.id}">${reportVar.name}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>
        </select>
    </div>
    <div class="navbar-form nav navbar-nav navbar-right" style="position: absolute; top: 375; left: 40%; min-width:50%; max-width: 50%;">
        <input class="btn btn-lg btn-default text-center" name="reportRefresh" type="submit" value="Refresh report" style="width: 215px; height: 62px;"  id="reportRefreshButton" />
    </div>
</form:form>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/bootstrap.js"></script>
</body>
</html>
