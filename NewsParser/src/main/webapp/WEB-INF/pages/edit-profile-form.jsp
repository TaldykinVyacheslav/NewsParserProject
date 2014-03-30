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
    <title>Edit profile</title>
    <link rel="stylesheet" type="text/css" href="/resources/stylesheets/styles3.css"/>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <%--<div id="#templateScript">
        <script>
            $( document ).ready(function() {

                $("#templateArea").trigger('keyup');

                $("#templateArea").on('keydown keyup', function (e) {
                    adaptiveheight(this);
                });
            })

            function adaptiveheight(a) {
                $(a).height(0);
                var scrollval = $(a)[0].scrollHeight;
                $(a).height(scrollval);
                if (parseInt(a.style.height) > $(window).height()) {
                    $(document).scrollTop(parseInt(a.style.height));
                }
            }
        </script>
    </div>--%>
</head>
<body>




<table id="staticPanel" >
    <tbody>
    <col width="70px">
    <tr>
    <div id="homePage">
        <td id="someLink" >
            <a href="<c:url value="/settings/"/>" alt="Return to settings" title="Return to settings">
                <img name="imageSome" src="/resources/images/cogwheel.png" width="60" height="60"
                     onMouseOver="return changeImage()"
                     onMouseOut= "return changeImageBack()"
                     onMouseDown="return handleMDown()"
                     onMouseUp="return handleMUp()"
                     id="someSettings"> </a>Return to settings

        </td>
        <td>
            <p id="profset">Profile Edit</p>
        </td>
        <td></td>
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
    </tbody>

    </tr>
</table>

<form:form method="POST" commandName="edit-profile-form.html" modelAttribute="profile" action="/settings/edit/${profileID}">

    <table id="allInTable" >
        <tbody>
        <tr>
            <td><p id="nameP">Name:</p></td>
            <td><form:input id="nameInput" path="name" name="name" placeholder="Enter profile name" /></td>
        </tr>
        <tr>
            <td><p id="introduction" >Introduction:</p></td>
            <c:forEach items="${profile.templates}" var="templateVar" varStatus="status">
                <c:if test="${templateVar.event.name == 'Intro'}">
                    <td><textarea id="templateArea" placeholder="template text" name="templates[${status.index}].eventText" class="templateArea">${templateVar.eventText}</textarea></td>
                </c:if>
            </c:forEach>
        </tr>
        </tbody>
    </table>
    <p id="name" class="center">Available tokens:</p>
    <table id="eventTemplate"  class="center" border="2">
        <tr>
            <th id="tokenName" >Name of token</th>
            <th id="tokenDescription">Description</th>
        </tr>
        <c:forEach items="${tokenList}" var="templateVar" varStatus="status">
            <tr>
                <td>${templateVar.name}</td>
                <td>${templateVar.description}</td>
            </tr>
        </c:forEach>
    </table>
    <div id="content">
        <table id="tableEvent" border="2">
            <thead>
            <tr>
                <th id="Event" >Event</th>
                <th id="Template" >Template</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${profile.templates}" var="templateVar" varStatus="status">
                <c:if test="${templateVar.event.name != 'Intro'}">
                    <tr>
                        <td>${templateVar.event.name}</td>
                        <td><textarea name="templates[${status.index}].eventText" placeholder="template text">${templateVar.eventText}</textarea></td>
                    </tr>
                </c:if>
            </c:forEach>
            <tr>
                <td><input id="SumSubmit"  type="submit" value="Save profile"></td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>
</form:form>
</body>
</html>