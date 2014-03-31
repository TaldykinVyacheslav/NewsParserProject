<%--
  User: Taldykin V.S.
  Date: 31.01.14
  Time: 13:38
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>News parser</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <script src="/resources/scripts/speak/speakClient.js"></script>
    <script type="text/javascript" src="/resources/js/jQuery.js"></script>
    <link rel="stylesheet" href="/resources/scripts/messi/messi.css" />
    <script src="/resources/scripts/messi/messi.js"></script>
    <%--<script src="http://code.jquery.com/jquery-latest.min.js"></script>--%>
    <script>
        $(document).ready(function() {
            display_hide('#textAreaWithAudio');
            document.getElementById( 'outputTextAreaShadow' ).style.visibility = 'hidden';
            //$("#outputTextArea").height($("#outputTextArea")[0].scrollHeight);
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
    <script type="text/javascript">
        function display_hide (blockId)
        {
            <c:if test="${!empty resultText}">
            $(blockId).animate({height: 'show'}, 500);
            document.getElementById("audio").innerHTML=("<br/><div id=\"block_1\" style=\"margin-left: 45%;\" class=\"barlittle\"></div>"
            + "<div id=\"block_2\" style=\"margin-left: 10px;\" class=\"barlittle\"></div>"
            + "<div id=\"block_3\" style=\"margin-left: 10px;\" class=\"barlittle\"></div>"
            + "<div id=\"block_4\" style=\"margin-left: 10px;\" class=\"barlittle\"></div>"
            + "<div id=\"block_5\" style=\"margin-left: 10px;\" class=\"barlittle\"></div>");
            </c:if>
        }
    </script>
    <style type="text/css">
        body
        {
            background: url(/resources/images/background4.jpg) no-repeat; /* Параметры фона */
        }
    </style>
    <style>
        .barlittle {
            background-color: #2187e7;
            background-image: -moz-linear-gradient(45deg, #2187e7 25%, #a0eaff);
            background-image: -webkit-linear-gradient(45deg, #2187e7 25%, #a0eaff);
            border-left: 1px solid #111;
            border-top: 1px solid #111;
            border-right: 1px solid #333;
            border-bottom: 1px solid #333;
            width: 15px;
            height: 15px;
            float: left;

            opacity: 0.1;
            -moz-transform: scale(0.7);
            -webkit-transform: scale(0.7);
            -moz-animation: move 1s infinite linear;
            -webkit-animation: move 1s infinite linear;
        }

        #block_1 {
            -moz-animation-delay: .4s;
            -webkit-animation-delay: .4s;
        }

        #block_2 {
            -moz-animation-delay: .3s;
            -webkit-animation-delay: .3s;
        }

        #block_3 {
            -moz-animation-delay: .2s;
            -webkit-animation-delay: .2s;
        }

        #block_4 {
            -moz-animation-delay: .3s;
            -webkit-animation-delay: .3s;
        }

        #block_5 {
            -moz-animation-delay: .4s;
            -webkit-animation-delay: .4s;
        }

        @-moz-keyframes move {
            0% {
                -moz-transform: scale(1.2);
                opacity: 1;
            }

            100% {
                -moz-transform: scale(0.7);
                opacity: 0.1;
            }
        }

        @-webkit-keyframes move {
            0% {
                -webkit-transform: scale(1.2);
                opacity: 1;
            }

            100% {
                -webkit-transform: scale(0.7);
                opacity: 0.1;
            }
        }
    </style>
</head>
<body>
<div id="#templateScript">
    <script>
        $( document ).ready(function() {
            var editLink = document.getElementById("editLink");
            var deleteLink = document.getElementById("deleteLink");
            editLink.href = editLink.href.substring(0, editLink.href.lastIndexOf('edit/') + 5) + $("#profileSelect").val() + '/';
            deleteLink.href = deleteLink.href.substring(0, deleteLink.href.lastIndexOf('delete/') + 7) + $("#profileSelect").val() + '/';

            $("#profileSelect").on('change', function (e) {
                var editLink = document.getElementById("editLink");
                var deleteLink = document.getElementById("deleteLink");
                editLink.href = editLink.href.substring(0, editLink.href.lastIndexOf('edit/') + 5) + $("#profileSelect").val() + '/';
                //alert(editLink.href);
                deleteLink.href = deleteLink.href.substring(0, deleteLink.href.lastIndexOf('delete/') + 7) + $("#profileSelect").val() + '/';
            });

            $( "#deleteLink" ).click(function(e) {
                if($("#profileSelect").val() == 1) {
                    //alert('A!');
                    e.preventDefault();
                    new Messi('You cannot delete this profile', {title: 'Error', titleClass: 'anim error', buttons: [{id: 0, label: 'Close', val: 'X'}]});
                }
            });

            $('#command').submit(function(){
                $('#voiceSearchButton').prop("disabled",true);
            });
        });
    </script>
</div>
<nav class="navbar navbar-inverse text-center">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">News parser</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#" style="color: white">News parser</a>
    </div>
    <div class="navbar-collapse collapse">
        <ol class="nav navbar-nav">
            <li  class="active" ><a href="#" ><strong>Home page</strong></a></li>
        </ol>
    </div>
</nav>
<table id="allInTable">
    <div class="container-fluid">
        <form:form method="GET" id="command" commandName="main-form.html" action="/search" >
            <div >
                <div class="input-group col-md-12 text-center">
                    <span class="input-group-addon ">Profile</span>
                    <select name="profileID" multiple size="7" id="profileSelect" class="settingSelect form-control" for="addLink">
                        <c:if test="${!empty profileList}">
                            <c:forEach items="${profileList}" var="profileVar">
                                <c:choose>
                                    <c:when test="${profileVar.id == selectedProfileID}">
                                        <option value="${profileVar.id}" selected>${profileVar.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${profileVar.id}">${profileVar.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>

                <div class="col-md-12 text-center form-inline " style="height: 100px;">
                    <div class="col-md-5">
                        <a id="addLink" class="form-inline" href=<c:url value="/add"/>>
                            <img src="/resources/images/plus-darck.png" alt="Add profile" title="Add profile" width="90" height="90">
                        </a>
                    </div>
                    <div class="col-md-2 text-center">
                        <a id="deleteLink" class="form-inline" href=<c:url value="/delete/1"/>>
                            <img src="/resources/images/minus-darck.png" alt="Remove profile" title="Remove profile" width="90" height="90">
                        </a>
                    </div>
                    <div class="col-md-5">
                        <a id="editLink" class="form-inline" href=<c:url value="/edit/1"/>>
                            <img src="/resources/images/tools-darck.png" alt="Edit profile" title="Edit profile" width="90" height="90">
                        </a>
                    </div>
                </div>
                <div class="center-block  col-md-12 text-center" style="bottom: 0; height: 100px" >
                    <button style="width: 180px; height: 60px;"  id="voiceSearchButton" name="voiceSearch" class=" btn btn-lg btn-default text-center" <%--onclick="display_hide('#textAreaWithAudio'); /*return false;*/"--%>>
                        Get report!
                    </button>
                </div>
                <div id="textAreaWithAudio" style= "display: none;" class="col-md-13 col-md-pull-1">
                    <textarea   id="outputTextAreaShadow" >${resultText}</textarea>
                    <textarea   id="outputTextArea" class="text-left" readonly="readonly" style="vertical-align: middle; min-height: 30%; min-width: 80%;  max-width: 80%; overflow: auto" >${resultText}</textarea>
                    <div id="audio" class="text-center"></div>
                </div>
            </div>
        </form:form>
    </div>
</table>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/bootstrap.js"></script>
</body>
</html>