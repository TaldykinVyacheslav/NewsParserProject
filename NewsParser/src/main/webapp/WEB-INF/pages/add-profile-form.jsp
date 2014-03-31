<%--
  User: Taldykin V.S.
  Date: 16.03.14
  Time: 22:17
--%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add profile</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <script src="./Profiles settings_files/jquery-latest.min.js"></script>
    <script src="./index_files/speakClient.js"></script>
    <script type="text/javascript" src="/resources/js/jQuery.js"></script>
    <script>
        $(document).ready(function() {
            document.getElementById( 'outputTextAreaShadow' ).style.visibility = 'hidden';
            $("#outputTextArea").height($("#outputTextArea")[0].scrollHeight);
            speak($("#outputTextAreaShadow").val(), { speed: 150 , pitch: 50, wordgap: 1 });

        });

        function adaptiveheight(a) {
            $(a).height(0);
            var scrollval = $(a)[0].scrollHeight;
            $(a).height(scrollval);
            if (parseInt(a.style.height) > $(window).height() - 30) {
                $(document).scrollTop(parseInt(a.style.height));
            }
        }
    </script>
    <style type="text/css">
        body
        {
            background: url(/resources/images/background4.jpg); /* Параметры фона */
        }
    </style>
</head>
<body>
<div style="height: 80px;" >
    <nav  class="navbar navbar-inverse text-center">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Home page</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#" style="color: #ffffff">News parser</a>
        </div>
        <div class="navbar-collapse collapse">
            <ol class="nav navbar-nav">
                <li><a href="/" ><strong>Home page</strong></a></li>
                <li  class="active" ><a href="#" ><strong>Add profile</strong></a></li>
            </ol>
        </div>
    </nav>
</div>
<form:form method="POST" commandName="add-profile-form.html" modelAttribute="profile" action="/add">

    <table class="table">
        <thead>
        <tr >
            <div class="input-group col-xs-4">
                <span class="input-group-addon">Name</span>
                <form:input id="nameInput" path="name" name="name" placeholder="Enter profile name" />
            </div>
        </tr>
        </thead>
    </table>
    <table class="table">
        <thead>
        <tr>
            <div class="input-group col-xs-8">
                <span class="input-group-addon">Introduction</span>
                <c:forEach items="${profile.templates}" var="templateVar" varStatus="status">
                    <c:if test="${templateVar.event.name == 'Intro'}">
                        <td><textarea style="height: 30px;min-height: 150px;" id="templateArea" placeholder="Template text" name="templates[${status.index}].eventText" class="form-control">${templateVar.eventText}</textarea></td>
                    </c:if>
                </c:forEach>
            </div>
        </tr>
        </thead>
    </table>
    <div class="input-group-sm col-xs-6 pre-scrollable">
        <span id="name" class="input-group-addon">Available tokens</span>
        <table id="eventTemplate" class="table table-hover form-control " border="2" style="background-color: #ffffff">
            <thead>
            <tr>
                <td id="tokenName" class="col-sm-3 "><strong>Name of token</strong></td>
                <td id="tokenDescription" class="form-control "><strong>Description</strong></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tokenList}" var="templateVar" varStatus="status">
                <tr>
                    <td>${templateVar.name}</td>
                    <td>${templateVar.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="content" class="col-xs-6 pre-scrollable">
        <table style="background-color: #ffffff" class="table table-hover small" id="tableEvent" border="2">
            <thead>
            <tr>
                <th id="Event" class="col-xs-3"><strong>Event</strong></th>
                <th id="Template"><strong>Template</strong></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${profile.templates}" var="templateVar" varStatus="status">
                <c:if test="${templateVar.event.name != 'Intro'}">
                    <tr>
                        <td>${templateVar.event.name}</td>
                        <td><textarea style="width: 100%; height: 100%" name="templates[${status.index}].eventText" placeholder="template text">${templateVar.eventText}</textarea></td>
                    </tr>
                </c:if>
            </c:forEach>
            <tr>
                <td><input style="" id="SumSubmit" type="submit" value="Add profile"></td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>
</form:form>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/bootstrap.js"></script>
</body>
</html>