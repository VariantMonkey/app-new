<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="aboutUs">
	<h1>登录</h1>


	<form:form method="post" action="signin" commandName="signin">
		<table>
			<tr>
				<td width="20%">账号 : </td><td width="20%"> <form:input path="userid" /> </td><td> <font color="red"><form:errors path="userid" /></font></td>
			</tr>
			<tr>
				<td>密码 : </td><td> <form:password path="password"  /> </td><td>  <font color="red"><form:errors path="password" /></font></td>
			</tr>
			<tr>
				<td> </td><td><input type="submit" value="登录" /> </td><td> </td>
			</tr>
		</table>
		<input type="hidden" name="currentUrl" value="${currentUrl}"/>
	</form:form>
</div>

<script>
	document.getElementById("userid").focus();
</script>

