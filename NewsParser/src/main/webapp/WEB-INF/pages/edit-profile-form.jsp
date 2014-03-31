<%--
  User: Taldykin V.S.
  Date: 31.01.14
  Time: 13:38
--%>

<!--
Начнем с каталога <Home page>
В данном случае Слава используй для редактирования страницу "Profiles settings.jsp"
Блок <head> ... </head> если правильно помню копируй полностью с заменой( если хочешь можешь проверить и построчно добавлять и удалять скрипты и стили)

Блок <body>:
В блок <div id="#templateScript"> ... </div> вроде я брал у тебя и он находился в начале странице оставляй его.

Появляется новый блок
<nav class="navbar navbar-inverse text-center"> ... </nav>
Копиру его и вставляй вместо
<h6>Profile settings</h6>
В этом блоке <nav>...</nav> проверь ссылку на главную страницу у меня ее нет добавь, и еще в конце твоего файла будет
блок <div id="homePage"> </div> удаляем его безвозвратно, т.к. ссылка будет в <nav> панели

Идем дальше
19:54:30

Alexander
Дальше делаем так чтобы не сильно тебе замарачиваться копируешь весь блок
<div class="container-fluid"> ... </div>
и заменяешь им <div class="center">...</div> блок
и еще подключение скриптов в моем коде которое идет в конце добавь в свой блок <body>...</body> ТОЖЕ В КОНЕЦ.

так теперь короче так
находишь блоки
1. <select multiple size="7" id="profileSelect" class="settingSelect form-control" for="addLink" name="profileID">...</select>
2.<a id="addLink" ..... , <a id="deleteLink" .... , <a id="editLink" .....
3. <textarea id="outputTextAreaShadow" >
4. <textarea id="outputTextArea" class="text-left" readonly="" style="vertical-align: middle; min-height: 30%; min-width: 80%; max-width: 80%; overflow: auto" >
5. <div id="audio" class="text-center">

И делаешь с ними следующее(сейчас будет использоваться нумерация определенная ранее и описания что надо изменить добавить):
1. Заменяешь на свою выборку select, т.е. копируешь все что
<select multiple size="7" id="profileSelect" class="settingSelect form-control"
for="addLink" name="profileID">...</select> находится вместо троеточия в код

2. Замени ссылки на те которые будут работать
3,4,5 сделать тоже что и в первом пункте замени то какие данные будут выводиться
20:08:16
✌ вот с первой страницой я и закончил 😎 ✌

Alexander
Страница <add.edit page>
Короче как и на предыдущей странице копируешь <head>...</head> и подключение js файлов конце блока <body>

Так тут делаешь так заменяешь весть блок
<table id="staticPanel">
на
мой блок <div style="height: 80px;" >...</div>
-->



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
    <title>Edit profile</title>
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
                <li  class="active" ><a href="#" ><strong>Edit profile</strong></a></li>
            </ol>
        </div>
    </nav>
</div>
<form:form method="POST" commandName="edit-profile-form.html" modelAttribute="profile" action="/edit/${profileID}">

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
                <td><input style="" id="SumSubmit" type="submit" value="Save profile"></td>
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