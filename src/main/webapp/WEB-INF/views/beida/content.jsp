<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ taglib prefix="fmt" uri=http://java.sun.com/jsp/jstl/fmt %> --%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container">
	<div id="banner">
		<img src="<c:url value="/images/beida/img_2.jpg"/>" />
	</div>
	<div id="content">
		<img src="<c:url value="/images/beida/imag6.jpg"/>" alt=""
			class="zuoTu" />
		<div id="sidebar">
			<div id="sidebar-title">${bbsname}</div>
		</div>
		<div id="content-main">
			<div id="content-main-title">
				<img src="<c:url value="/images/beida/diamond.png"/>" alt="" />${bbsname}
			</div>
			<div id="content-main-breadcrumb">首页 &gt; ${bbsname}</div>
			<div id="content-main-line"></div>
			<div id="content-main-list">
				<ul>
					<c:choose>
						<c:when test="${not empty bbss}">
							<c:forEach var="detail" items="${bbss}">
								<li><a
									href="<c:url value="/bbs"/>/<c:out value="${bbsnum}"/>/detail/<c:out value="${detail.num}"/>"><c:out
											value="${detail.subject}" /></a><span
									style="float: right; margin-right: 100px;"> <fmt:formatDate
											value="${detail.regdate}" pattern="yyyy.MM.dd  HH:mm:ss" />
								</span></li>
							</c:forEach>
						</c:when>
					</c:choose>
				</ul>
			</div>
			<br class="clearfloat" />
			<div
				style="border: 1px; width: 660px; height: 30px; color: #9999CC; margin-top: 50px;text-align: center">
				<span>共 ${pageSize} 页 / 第 ${curPage} 页 </span>&nbsp;&nbsp; 
				<span><a id="firstPage" href="${pageContext.request.contextPath}/bbs/beida/list/${bbsnum}?pageNum=${firstPage}"> 首页 </a></span>
				<span><a id="prePage" href="${pageContext.request.contextPath}/bbs/beida/list/${bbsnum}?pageNum=${prePage}"> 上一页 </a></span>
				<span><a id="nextPage" href="${pageContext.request.contextPath}/bbs/beida/list/${bbsnum}?pageNum=${nextPage}">下一页 </a></span>
				<span><a id="endPage" href="${pageContext.request.contextPath}/bbs/beida/list/${bbsnum}?pageNum=${lastPage}">尾页</a></span>
			</div>
			<br class="clearfloat" />
			<script type="text/javascript">
			 if('${curPage}'==1){
				 $("#firstPage").removeAttr('href');
				 $("#prePage").removeAttr('href');
				 $("#firstPage").css("color","#9999CC");
				 $("#prePage").css("color","#9999CC")
			 };
			 if('${prePage}'<1){
				 $("#prePage").removeAttr('href');
				 $("#prePage").css("color","#9999CC");
			 }
			 if('${nextPage}'>'${pageSize}'||'${nextPage}'=='${curPage}'){
				 $("#nextPage").removeAttr('href');
				 $("#nextPage").css("color","#9999CC");
			 }
			 if('${lastPage}'>'${pageSize}'||'${lastPage}'=='${curPage}'||${lastPage}==0){
				 $("#endPage").removeAttr('href');
				 $("#endPage").css("color","#9999CC");
			 }
			</script>
		</div>

	</div>
</div>

