<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container">
	<div id="banner">
		<img src="<c:url value="/images/beida/img_2.jpg"/>" />
	</div>
	<div id="content" class="zhengWenbj">
		<div id="content-main" class="zhengWen"
			style="float: inherit !important;">
			<div class="biaoTi">
				<c:out value="${detail.subject}" />
			</div>
			<div id="content-main-title" style="display: none;">
				<img src="<c:url value="/images/beida/diamond.png"/>" alt="" />
			</div>
			<div id="content-main-breadcrumb"> <fmt:formatDate value="${detail.regdate}" pattern="yyyy.MM.dd  HH:mm:ss"/></div>
			<div id="content-main-line"></div>
			<div id="content-main-text" class="content_detail_bd"
				style="padding-top: 2px;">
				<c:forEach var="file" items="${filelist}">
					<p>
						<c:choose>
							<c:when test="${file.filetype == 'image'}">
								<img src="<c:out value="${file.url}" />" />
							</c:when>
							<c:otherwise>
					附件 : <a href="<c:out value="${file.url}" />"><c:out
										value="${file.filename}" /></a>
							</c:otherwise>
						</c:choose>
					</p>
					<br />
				</c:forEach>
				<p>
				<c:out value="${detail.content}" escapeXml="false" />
				</p>
			</div>

		</div>

	</div>
</div>