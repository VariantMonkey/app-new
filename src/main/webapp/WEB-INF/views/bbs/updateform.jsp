<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script src="<c:url value="/js/jquery-1.7.2.min.js"/>"></script>
<script src="<c:url value="/js/bbs.js"/>"></script>

<input type="button" value="返回分类列表" class="buttonStyle"
       onClick="location.href='<c:url value='/bbs/'/>${bbsnum}/list/1'" />
<div class="aboutUs">
    <form:form method="post" commandName="bbs" enctype="multipart/form-data">
        <table>
            <tr>
                <td width="100%">主题 <font color="red"><form:errors path="subject"/></font></td>
            </tr>
            <tr>
                <td><form:input path="subject"/></td>
            </tr>
            <tr>
                <td>内容 <font color="red"><form:errors path="content"/></font></td>
            </tr>
            <tr>
                <td><form:textarea class="ckeditor" path="content"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="提交"/></td>
            </tr>
        </table>
        <br/>
        <br/>
        <div id="file">
            附件 :
            <br/>
            <br/>
            <p id="file1">
                <input type="file" name="file" id="file"></input>
                <input type="button" value="添加" class="buttonStyle"
                       onClick="addFileNode()"/>
            </p>
        </div>
        <input type="hidden" name="bbsnum" id="bbsnum" value="${bbsnum }"></input>
        <form:hidden path="num"/>
    </form:form>
    <c:forEach var="file" items="${filelist}">
        <p id="<c:out value="${file.num}" />">
            <c:choose>
                <c:when test="${file.filetype == 'image'}">
                    <img src="<c:out value="${file.url}" />"/>
                </c:when>
                <c:otherwise>
                    附件 : <a href="<c:out value="${file.url}" />"><c:out value="${file.filename}"/></a>
                </c:otherwise>
            </c:choose>
            <input type="button" value="删除" class="buttonStyle"
                   onClick="javascript:deleteFile('<c:url value="/"/>','<c:out value="${file.num}"/>', '删除')"/>
            <br/>
            <br/>
        </p>
    </c:forEach>
</div>
<script>
    document.getElementById("subject").focus();
</script>
<br class="clearfloat"/>
