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
    <link rel="stylesheet" href="/resources/scripts/messi/messi.css" />
    <script src="/resources/scripts/messi/messi.js"></script>
    <script>
        $(document).ready(function() {
            $( "#addForm" ).submit(function(event) {
                if($("#nameInput").val().trim().length == 0) {
                    new Messi('You cannot save profile with empty name', {title: 'Error', titleClass: 'anim error', buttons: [{id: 0, label: 'Close', val: 'X'}], modal: true});
                    event.preventDefault();
                 }
            });
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
            background: url(/resources/images/background4.jpg) fixed;  /* Параметры фона */
        }
    </style>
</head>
<body>
<form:form method="POST" id="addForm" cssStyle="min-height: 100%; max-height: 100%; min-width: 100%; max-width: 100%" commandName="add-profile-form.html" modelAttribute="profile" action="/add">
    <nav  class="navbar navbar-inverse text-center navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Home page</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" style="color: #ffffff">News parser</a>
            </div>
            <div class="navbar-collapse collapse">
                <ol class="nav navbar-nav">
                    <li><a href="/" ><strong>Home page</strong></a></li>
                    <li  class="active" ><a  ><strong>Add profile</strong></a></li>
                </ol>
                <div class="navbar-form nav navbar-nav navbar-right">
                    <input style="width: 120px;height: 30px;" id="SumSubmit" type="submit" value="Add profile">
                </div>
            </div>
        </div>
    </nav>
    <div class="container-fluid" style=" position: relative; top: 60px; min-height: 200px; max-height: 200px; min-width: 100%; max-width: 100%;">
        <div class="input-group" style="position: absolute; top: 0; left: 50%; min-width:40%; max-width: 40%;">
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
        <div class="input-group" style="position: absolute; top: 0; left: 5%; min-width:40%; max-width: 40%;">
            <span class="input-group-addon">Name</span>
            <form:input id="nameInput" path="name" cssClass="form-control" style="min-height: 60px;" name="name" placeholder="Enter profile name" />
        </div>
        <div class="input-group" style="position: absolute; top: 70px; left: 15%; min-width:70%; max-width: 70%;" >
            <span class="input-group-addon">Introduction</span>
                <c:forEach items="${profile.templates}"  var="templateVar" varStatus="status">
                    <c:if test="${templateVar.event.name == 'Intro'}">
                        <textarea  id="templateArea" style="max-height: 300px; min-height: 200px; min-width:825px; max-width: 825px;" placeholder="Template text" name="templates[${status.index}].eventText" class="form-control">${templateVar.eventText}</textarea>
                    </c:if>
                </c:forEach>
        </div>
        <div  style="position: absolute; top: 376px; left: 61%; min-width:35%; max-width: 35%;" >
            <span id="name" class="input-group-addon">Available tokens</span>
            <table id="eventTemplate" class="table table-hover form-control " border="2" style="background-color: #ffffff">
                <thead>
                <tr>
                    <td id="tokenName" style="max-width: 223px; min-width: 223"><strong>Name of token</strong></td>
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
        <div  style="position: absolute; top: 376px; left: 30px; min-width:55%; max-width: 55%;">
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
                            <td><textarea style="width: 537px; height: 200px; min-width: 537px; min-height: 90px; max-width: 537px;" name="templates[${status.index}].eventText" placeholder="template text">${templateVar.eventText}</textarea></td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</form:form>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/bootstrap.js"></script>
</body>
</html>