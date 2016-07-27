<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>	
<!-- begin #header -->
<div id="header">
	<div class="icons">
		<ul>
			<li><c:out value="${sessionScope.userid}" /></li>
			<li <c:if test="${menu=='index'}">id="active"</c:if> ><a href="<c:url value="/"/>">返回主页</a></li>
			<c:choose>
				<c:when test="${not empty sessionScope.userid}">
					<li <c:if test="${menu=='signout'}">id="active"</c:if> ><a href="<c:url value="/user/signout"/>">退出</a></li>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
		<div class="clearfloat"></div>
</div>
<!-- end #header -->