<%@ page import="java.util.*" %>
<%@ page import="com.byelex.newsparser.Models.Event" %>
<%--
  User: Taldykin V.S.
  Date: 31.01.14
  Time: 13:38
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>News parser</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <%--<script src="/resources/scripts/speak/speakClient.js"></script>--%>
    <script type="text/javascript" src="/resources/js/jQuery.js"></script>
    <link rel="stylesheet" href="/resources/scripts/messi/messi.css" />
    <script src="/resources/scripts/messi/messi.js"></script>
    <%--<script src="http://code.jquery.com/jquery-latest.min.js"></script>--%>
    <script>
        $(document).ready(function() {
            display_hide('#textAreaWithAudio');
            document.getElementById( 'outputTextAreaShadow' ).style.visibility = 'hidden';
            document.getElementById( 'outputTextArea').innerHTML = $("#outputTextAreaShadow").val().split("\n").join("<br/>");
            document.getElementById( 'outputTextAreaShadow').innerHTML = $("#outputTextAreaShadow").val().split("]}").join("");
            document.getElementById( 'outputTextAreaShadow').innerHTML = $("#outputTextAreaShadow").val().split("{[").join("");

            var item, url;
            <c:if test="${!empty urlMap}">
            <%
                List<Event> eventList = (List<Event>)request.getAttribute("eventsList");
                Map<String, String> urlMap = (Map<String, String>) request.getAttribute("urlMap");
                Map<String, String> sortedUrlMap = new HashMap<String, String>();
                Set<String> itemMapSet = urlMap.keySet();
                List<String> itemMapList = new ArrayList<String>();
                String reportName = (String)request.getAttribute("reportName");
                itemMapList.addAll(itemMapSet);

                // SORTING BY STRING LENGTH
                int j;
                boolean flag = true;   // set flag to true to begin first pass
                String temp;   //holding variable
                String currentItem, currentUrl;

                while ( flag )
                {
                    flag= false;    //set flag to false awaiting a possible swap
                    for( j=0;  j < itemMapList.size() -1;  j++ )
                    {
                        if ( itemMapList.get(j).length() < itemMapList.get(j+1).length() )   // change to > for ascending sort
                        {
                            temp = itemMapList.get(j);                //swap elements
                            itemMapList.set(j, itemMapList.get(j+1));
                            itemMapList.set(j+1, temp);
                            flag = true;              //shows a swap occurred
                        }
                    }
                }

                for(String item : itemMapList) {
                    sortedUrlMap.put(item, urlMap.get(item));
                }

                for(String item : itemMapList) {
                    currentUrl = urlMap.get(item);
                    currentItem = item;
                    currentItem = currentItem.replace("\"", "");
                    currentItem = currentItem.replace("\'", "");
                    currentItem = currentItem.replace("\n", "");
                    //
                    if(currentItem != null) {
            %>
            document.getElementById('outputTextArea').innerHTML = document.getElementById( 'outputTextArea').innerHTML.split("<%=currentItem%>").join("<a target=\"_blank\" href=\"" +  "<%=currentUrl%>" + "\">" + "<%=currentItem.replace("{[", "").replace("]}", "")%>" + "</a>")
            <%
                    }
                }
                for(Event event : eventList) {
            %>
            document.getElementById('outputTextArea').innerHTML = document.getElementById( 'outputTextArea').innerHTML.split("<%=event.getName().replace("&", "&amp;")%>").join("<i><b>" +  "<%=event.getName().replace("&", "&amp;")%>" + "</b></i>");
            <%
                }
           %>
            document.getElementById('outputTextArea').innerHTML = document.getElementById( 'outputTextArea').innerHTML.split("<%=reportName.replace("&", "&amp;")%>").join("<b><u>" + "<%=reportName.replace("&", "&amp;").replace("{[", "").replace("]}", "")%>" + "</u></b>");
            /*document.getElementById('outputTextArea').innerHTML = document.getElementById( 'outputTextArea').innerHTML.split("Fraud &amp; Forgery").join("<i><b>" +  "Fraud &amp; Forgery" + "</b></i>");
            document.getElementById('outputTextArea').innerHTML = document.getElementById( 'outputTextArea').innerHTML.split("Visits &amp; Talks").join("<i><b>" +  "Visits &amp; Talks" + "</b></i>");*/
            </c:if>
            //document.getElementById('outputTextArea').innerHTML = document.getElementById( 'outputTextArea').innerHTML.split("Amsterdam").join("<a href=\"http://vk.com/feed\">Amsterdam</a>");

            //speak($("#outputTextAreaShadow").val(), { speed: 170 , pitch: 25, wordgap: 7 });

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
            var curPos=$(document).scrollTop();
            var height=700;
            var scrollTime=(height-curPos)/1.73;
            $("body,html").animate({"scrollTop":height},scrollTime);
            $(blockId).animate({height: 'show'}, 500);
            </c:if>
        }
    </script>
    <style type="text/css">
        body
        {
            background: url(/resources/images/background4.jpg) fixed; /* Параметры фона */
        }
    </style>
    <%-- <style>
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
    </style>--%>
</head>
<body>
<div id="#templateScript">
    <script>
        $( document ).ready(function() {
            var editLink = document.getElementById("editLink");
            var deleteLink = document.getElementById("deleteLink");
            editLink.href = editLink.href.substring(0, editLink.href.lastIndexOf('edit/') + 5) + $("#profileSelect").val() + '/';
            deleteLink.href = deleteLink.href.substring(0, deleteLink.href.lastIndexOf('delete/') + 7) + $("#profileSelect").val() + '/';


//Необходимо прокрутить в конец страницы


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
                    new Messi('You cannot delete this profile', {title: 'Error', titleClass: 'anim error', buttons: [{id: 0, label: 'Close', val: 'X'}], modal: true});
                }
            });

            var submitActor = null;
            var $form = $( '#command' );
            var $submitActors = [$( '#voiceSearchButton' ), $( '#reportRefreshButton' )];

            $form.submit( function( event )
            {
                if ( null === submitActor )
                {
                    // If no actor is explicitly clicked, the browser will
                    // automatically choose the first in source-order
                    // so we do the same here
                    //submitActor = $submitActors[0];
                    alert('Null!');
                } else {
                    if(submitActor.name == 'voiceSearch') {
                        new Messi('Loading report, please wait.', {title: 'Loading', titleClass: 'anim warning', modal: true});
                    } else {
                        new Messi('Refreshing database, please wait.', {title: 'Loading', titleClass: 'anim warning', modal: true});
                    }
                }

            });

            $( '#voiceSearchButton').click(function( event )
            {
                submitActor = this;
            });

            $( '#reportRefreshButton' ).click( function( event )
            {
                submitActor = this;
            });
        });
    </script>
</div>
<form:form method="GET" id="command" cssStyle="min-height: 100%; max-height: 100%; min-width: 100%; max-width: 100%"  commandName="main-form.html" action="/search" >
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
            <li  class="active" ><a href="#" ><strong>Home page</strong></a></li>
        </ol>
        <ol class="nav navbar-nav navbar-right">
            <li  style="right: 20;"><a style="color: white" href=<c:url value="/refresh"/>  id="reportRefreshButton" ><strong>Refresh report</strong></a></li>
        </ol>
    </div>
</nav>
    <div class="container-fluid" style=" background-image: url(/resources/images/background3.jpg); position: relative; top: 100px; min-height: 200px; max-height: 200px; min-width: 80%; max-width: 80%;">
        <div class="input-group" style="position: absolute; top: 30; left: 90; min-width:45%; max-width: 45%;">
            <span class="input-group-addon ">Profile</span>
            <select name="profileID" style="min-height: 60px;" id="profileSelect" class="settingSelect form-control" for="addLink">
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
        <a id="addLink" style="right: 60; top: 10; position: absolute;" class="form-inline" href=<c:url value="/add"/>>
            <img src="/resources/images/plus-darck.png" alt="Add profile" title="Add profile" width="60" height="60">
        </a>
        <a id="deleteLink" style="right: 60; top: 140; position: absolute;" class="form-inline" href=<c:url value="/delete/1"/>>
            <img src="/resources/images/minus-darck.png" alt="Remove profile" title="Remove profile" width="60" height="60">
        </a>
        <a id="editLink" style="right: 60; top: 74; position: absolute;" class="form-inline" href=<c:url value="/edit/1"/>>
            <img src="/resources/images/tools-darck.png" alt="Edit profile" title="Edit profile" width="60" height="60">
        </a>
       <div class="input-group" style="position: absolute;  bottom: 7%;left: 20%;min-width: 35%;max-width: 35%;">
           <a href="#headerLink">
                <input name="voiceSearch" type="submit" class="btn btn-lg btn-default text-center" for="addLink" value="Get report!" style="width: 150px; height: 50px;"  id="voiceSearchButton"  />
           </a>
       </div>
    </div>
    <div id="headerLink"  style="position: absolute; top: 300px; left: 10%; max-width: 80%; min-width: 80%; right: 10%; min-height: 80%">
        <div id="textAreaWithAudio" style= "display: none;">
            <h1 class="page-header text-center col-md-12" style="padding-top: 3%; background-image: url(/resources/images/background3.jpg); color: #ffffff;">Report</h1>
            <textarea id="outputTextAreaShadow" >${resultText}</textarea>
            <div  id="outputTextArea" class="text-left" readonly="readonly" style="vertical-align: middle; background-color: #ffffff; min-height: 100%; min-width: 100%;  max-width: 100%; overflow: auto" >${resultText}</div>
            <c:if test="${!empty resultText}">
                <div id="audio" class="text-center">
                    <audio controls="controls" autoplay="autoplay">
                        <source src="${mp3Uri}" type="audio/mpeg" />
                    </audio>
                </div>
            </c:if>
        </div>
    </div>
</form:form>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/bootstrap.js"></script>

</body>
</html>