<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>




<div class="aboutUs">
	<h1>注册</h1>


	<h2>文档管理</h2>

	<h3>添加一个新文档</h3>
	<form:form method="post" action="save" commandName="document"
		enctype="multipart/form-data">
		<form:errors path="*" cssClass="error" />
		<table>
			<tr>
				<td><form:label path="name">名称</form:label></td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td><form:label path="description">描述</form:label></td>
				<td><form:textarea path="description" /></td>
			</tr>
			<tr>
				<td><form:label path="content">文档内容</form:label></td>
				<td><input type="file" name="file" id="file"></input></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Add Document" /></td>
			</tr>
		</table>
	</form:form>

	<br />
	<h3>文档列表</h3>
	<c:if test="${!empty documentList}">
		<table class="data">
			<tr>
				<th>名称</th>
				<th>描述</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${documentList}" var="document">
				<tr>
					<td width="100px">${document.name}</td>
					<td width="250px">${document.description}</td>
					<td width="20px"><a
						href="${pageContext.request.contextPath}/doc/download/${document.id}"><img
							src="${pageContext.request.contextPath}/img/save_icon.gif"
							border="0" title="Download this document" /></a> <a
						href="${pageContext.request.contextPath}/doc/remove/${document.id}"
						onclick="return confirm('Are you sure you want to delete this document?')"><img
							src="${pageContext.request.contextPath}/img/delete_icon.gif"
							border="0" title="Delete this document" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>


</div>

<script>
	document.getElementById("userid").focus();
</script>

