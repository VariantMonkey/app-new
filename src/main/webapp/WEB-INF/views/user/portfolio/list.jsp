<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<input type="button" value="Write" class="buttonStyle" onClick="location.href='<c:url value='/portfolio/form'/>'" />
	
<div class="boxes">

	<c:forEach var="detail" items="${list}">
		<div class="box">
			<h3><c:out value="${detail.num}"/> <c:out value="${detail.subject}"/></h3>
			<img width="273" height="198" src="<c:url value="/file/image"/>/portfolio/1/<c:out value="${detail.num}"/>" alt="" />
			<p>
				<c:out value="${detail.content}"/>
			</p>
			<div class="boxButton">
				<input type="button" value="详情..." class="buttonStyle" onClick="location.href='<c:url value="/portfolio/detail"/>/<c:out value="${bbsnum}"/>/<c:out value="${detail.num}"/>'"/>
			</div>
		</div>
	</c:forEach>
	
</div>
<br class="clearfloat" />
