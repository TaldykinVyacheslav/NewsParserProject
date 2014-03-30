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
    <link rel="stylesheet" type="text/css" href="/resources/stylesheets/styles2.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/stylesheets/profilesSettings.css"/>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <div id="#templateScript">
        <script>
            $( document ).ready(function() {
                $("#profileSelect").on('change', function (e) {
                    var editLink = document.getElementById("editLink");
                    var deleteLink = document.getElementById("deleteLink");
                    editLink.href = editLink.href.substring(0, editLink.href.lastIndexOf('edit/') + 5) + $("#profileSelect").val() + '/';
                    deleteLink.href = deleteLink.href.substring(0, deleteLink.href.lastIndexOf('delete/') + 7) + $("#profileSelect").val() + '/';
                });
            });
        </script>
    </div>
    <title>
        Profiles settings
    </title>
</head>
<body>
<h6>
    Profile settings
</h6>
<table id="allInTable">
    <div class="center">
    <tr>
        <td>
            <p name="profile">
                Profile:
            </p>
        </td>
        <td id="mainLinks">
            <select multiple id="profileSelect" class="settingSelect" name="profileID">
                <c:if test="${!empty profileList}">
                    <c:forEach items="${profileList}" var="profileVar">
                        <option value="${profileVar.id}">${profileVar.name}</option>
                    </c:forEach>
                </c:if>
            </select>
            <a id=addLink href=<c:url value="/settings/add"/>><img
                    src="/resources/images/plus2.png"
                    alt="Add profile"
                     title="Add profile"
                     width="70" height="70"
                    /></a>
            <a id=deleteLink href=<c:url value="/settings/delete/${currentProfile.id}"/>><img
                    src="/resources/images/minus.png"
                    alt="Remove profile"
                     title="Remove profile"
                     width="70" height="70"
                    /></a>
            <a id=editLink href=<c:url value="/settings/edit/${currentProfile.id}"/>><img
                    src="/resources/images/edit.png"
                    alt="Edit profile"
                    title="Edit profile"
                    width="70" height="70"
                    /></a>
        </td>
        <td>

        </td>
    </tr>
    </div>
</table>
<br/>
<p style="color: red">${errorMessage}</p>
<p style="color: lawngreen">${infoMessage}</p>
<div id="homePage">
    <p><a href=<c:url value="/index"/>>
        <img name="imageSome" src="/resources/images/retHome.png" width="70" height="70"
             onMouseOver="return changeImage()"
             onMouseOut= "return changeImageBack()"
             onMouseDown="return handleMDown()"
             onMouseUp="return handleMUp()"
             id="someSettings"></a>Return to Home Page </p>
    <script language="JavaScript">
        upImage = new Image();
        upImage.src = "/resources/images/retHomeu.png";
        downImage = new Image();
        downImage.src = "/resources/images/retHomed.png"
        normalImage = new Image();
        normalImage.src = "/resources/images/retHome.png";
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
</body>
</html>