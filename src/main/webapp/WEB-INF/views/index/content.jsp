<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
  <div id="block_feature">
    <div id="work_list">
      <div id="search">
        <form  action="${pageContext.request.contextPath}" method="post" id="search_form_id">
          <img class="search_subtn" src="<c:url value="/images/beida/search.png"/>"/>
          <div id="search_input" >
            <input class="searchinput" style="border:0 none" type="text" name="keywd" value="搜索" />
          </div>
        </form>
      </div>
      <ul>
        <li val="cx" class="indexlistjs001" isclick="0"><a href="javascript:void(0);">信访事项查询</a></li>
        <li val="yg" class="indexlistjs001" isclick="0"><a href="javascript:void(0);">阳光信访</a></li>
        <li val="ldjd" class="indexlistjs001" isclick="0"><a href="javascript:void(0);">校领导接待日</a></li>
        <li val="zb" class="indexlistjs001" isclick="0"><a href="javascript:void(0);">专报（pass）</a></li>
        <li val="jb" class="indexlistjs001" isclick="0"><a href="javascript:void(0);">简报（pass）</a></li>
      </ul>
    </div>
    <div id="feature_sjxx"><a href="${pageContext.request.contextPath}/sjxx"> <img src="<c:url value="/images/beida/feature-sjxx.png"/>"/></a></div>
    <div id="feature_xzxx"><a href="${pageContext.request.contextPath}/xzxx"> <img  src="<c:url value="/images/beida/feature-xzxx.png"/>"/></a></div>
    <div id="feature_oa"><a href="http://bdoa.pku.edu.cn/"> <img src="<c:url value="/images/beida/feature-oa.png"/>"/></a></div>
    <div id="dcszz"><a href="${pageContext.request.contextPath}/jgjj"><img src="<c:url value="/images/beida/feature-dcszz.png"/>"/></a></div>
    <div id="xfbzz"><a href="${pageContext.request.contextPath}/jgjj"><img src="<c:url value="/images/beida/feature-xfbzz.png"/>"/></a></div>
  </div>
  <div id="block_content_up">
    <div class=" infobox" >
    	<div class="tiTou"><span>通知公告</span><a href="${pageContext.request.contextPath}/bbs/beida/list/1"></a></div>
    	<ul>
    	      <c:choose>
					<c:when test="${not empty notice.bbss}">
							<c:forEach var="detail" items="${notice.bbss}">  
								<li><a href="<c:url value="/bbs"/>/<c:out value="${notice.bbsnum}"/>/detail/<c:out value="${detail.num}"/>"><c:out value="${detail.subject}" /></a><span >
									   <fmt:formatDate value="${detail.regdate}" pattern="yyyy.MM.dd"/>
									</span>
								</li>
							</c:forEach>
				     </c:when>
				</c:choose>  
      </ul>
    </div>
    <div class=" infobox" >
    	<div class="tiTou"><span>法规制度</span><a href="${pageContext.request.contextPath}/bbs/beida/list/8"></a></div>
      <ul>
         <c:choose>
					<c:when test="${not empty fgzd.bbss}">
							<c:forEach var="detail" items="${fgzd.bbss}">  
								<li><a href="<c:url value="/bbs"/>/<c:out value="${fgzd.bbsnum}"/>/detail/<c:out value="${detail.num}"/>"><c:out value="${detail.subject}" /></a><span>
									   <fmt:formatDate value="${detail.regdate}" pattern="yyyy.MM.dd"/>
									</span>
								</li>
							</c:forEach>
				     </c:when>
				</c:choose>  
      </ul>
    </div>
    <div class="infobox_right infobox" >
    	<div class="tiTou"><span>服务之窗</span><a href="${pageContext.request.contextPath}/bbs/beida/list/6"></a></div>
      <ul>
        <c:choose>
					<c:when test="${not empty fwzc.bbss}">
							<c:forEach var="detail" items="${fwzc.bbss}">  
								<li><a href="<c:url value="/bbs"/>/<c:out value="${fwzc.bbsnum}"/>/detail/<c:out value="${detail.num}"/>"><c:out value="${detail.subject}" /></a><span >
									   <fmt:formatDate value="${detail.regdate}" pattern="yyyy.MM.dd"/>
									</span>
								</li>
							</c:forEach>
				     </c:when>
		</c:choose>  
      </ul>
    </div>
  </div>
  <div id="block_content_down">
    <div class=" infobox" >
    	<div class="tiTou"><span>督查信息</span><a  href="${pageContext.request.contextPath}/bbs/beida/list/12"></a></div>
      <ul>
           <c:choose>
					<c:when test="${not empty dc.bbss}">
							<c:forEach var="detail" items="${dc.bbss}">  
								<li><a href="<c:url value="/bbs"/>/<c:out value="${dc.bbsnum}"/>/detail/<c:out value="${detail.num}"/>"><c:out value="${detail.subject}" /></a><span >
									   <fmt:formatDate value="${detail.regdate}" pattern="yyyy.MM.dd"/>
									</span>
								</li>
							</c:forEach>
				     </c:when>
				</c:choose>  
      </ul>
    </div>
    <div class=" infobox" >
    	<div class="tiTou"><span>信访信息</span><a href="${pageContext.request.contextPath}/bbs/beida/list/13"></a></div>
      <ul>
         <c:choose>
					<c:when test="${not empty xf.bbss}">
							<c:forEach var="detail" items="${xf.bbss}">  
								<li><a href="<c:url value="/bbs"/>/<c:out value="${xf.bbsnum}"/>/detail/<c:out value="${detail.num}"/>"><c:out value="${detail.subject}" /></a><span>
									   <fmt:formatDate value="${detail.regdate}" pattern="yyyy.MM.dd"/>
									</span>
								</li>
							</c:forEach>
				     </c:when>
				</c:choose>  
      </ul>
    </div>
    <div class="infobox_right infobox" >
   		<div class="tiTou"><span>下载专区</span><a href="${pageContext.request.contextPath}/bbs/beida/list/9"></a></div>
      <ul>
        <c:choose>
					<c:when test="${not empty xzzq.bbss}">
							<c:forEach var="detail" items="${xzzq.bbss}">  
								<li><a href="<c:url value="/bbs"/>/<c:out value="${xzzq.bbsnum}"/>/detail/<c:out value="${detail.num}"/>"><c:out value="${detail.subject}" /></a><span>
									   <fmt:formatDate value="${detail.regdate}" pattern="yyyy.MM.dd"/>
									</span>
								</li>
							</c:forEach>
				     </c:when>
				</c:choose>  
      </ul>
    </div>
  </div>
</div>