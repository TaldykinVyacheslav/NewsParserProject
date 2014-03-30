<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/resources/stylesheets/styles.css"/>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="/resources/scripts/speak/speakClient.js"></script>
    <script>
        $(document).ready(function() {
            document.getElementById( 'outputTextAreaShadow' ).style.visibility = 'hidden';
            $("#outputTextArea").height($("#outputTextArea")[0].scrollHeight);
            speak($("#outputTextAreaShadow").val(), { speed: 150 , pitch: 50, wordgap: 1 });
            <c:if test="${empty resultText}">
            $("#audio").hide();
            </c:if>
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
</head>
<body>
<%--//todo
//добавить настройки для событий
//использовать документ в качестве шаблона
//добавить все токены из документа(сложные и составные)--%>
<div id="wholePanel">
    <div id="workingPanel">
        <div class="center">
            <h6>News parser</h6>
        </div>
        <div id="mainPanel">
            <form:form method="get" id="command" action="search">

                <br/>
                <div id="profileInfo" class="center">
                   <p> Profile: </p>
                    <c:if test="${!empty profileList}">
                        <select name="profileID">
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
                        </select>
                    </c:if>
                    <a href="<c:url value="/settings/"/>"
                        onMouseOver="return changeImage()"
                        onMouseOut= "return changeImageBack()"
                        onMouseDown="return handleMDown()"
                        onMouseUp="return handleMUp()"
                        id="someSettings">
                        <img name="imageSome" src="/resources/images/cogwheel.png" width="70" height="70" alt="Go to profile settings" title="Go to profile settings">
                    </a>
                    <script language="JavaScript">

                        upImage = new Image();
                        upImage.src = "/resources/images/cogwheelu.png";
                        downImage = new Image();
                        downImage.src = "/resources/images/cogwheeld.png"
                        normalImage = new Image();
                        normalImage.src = "/resources/images/cogwheel.png";

                        function changeImage()
                        {
                            document.images["imageSome"].src= upImage.src;
                            return true;
                        }
                        function changeImageBack()
                        {
                            document.images["imageSome"].src = normalImage.src;
                            return true;
                        }
                        function handleMDown()
                        {
                            document.images["imageSome"].src = downImage.src;
                            return true;
                        }
                        function handleMUp()
                        {
                            changeImage();
                            return true;
                        }
                    </script>
                </div>
                <br/>
                <div class="center">
                        <button id="voiceSearchButton" name="voiceSearch">
                            Get report!
                        </button>
                </div>
                <br>
            </form:form>
        </div>
        <div id="textAreaWithAudio" class="center">
            <textarea id="outputTextAreaShadow" >${resultText}</textarea>
            <textarea id="outputTextArea" class="heightByContent" readonly>  ${resultText} </textarea>
            <div id="audio"></div>
        </div>
    </div>
</div>
</body>
</html>