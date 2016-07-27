<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="memo">
    <h3>分类列表</h3>
    <c:choose>
        <c:when test="${not empty configs}">
            <c:forEach var="detail" items="${configs}">

                <c:if test="${bbsnum==detail.bbsnum}">
                    <b>
                </c:if>
                <a href="<c:url value="/bbs/"/><c:out value="${detail.bbsnum}"/>/list/1"/>
                <c:out value="${detail.bbsname}"/>
                </a>
                <c:if test="${bbsnum==detail.bbsnum}">
                    </b>
                </c:if>
                |
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>没有分类</p>
        </c:otherwise>
    </c:choose>
</div>

<input type="button" value="新建" class="buttonStyle"
       onClick="location.href='<c:url value='/bbs/'/>${bbsnum}/form'" />
<c:choose>
    <c:when test="${listtypenum == 1}">
        <div class="content">
            <h3>
                <c:out value="${bbsname}"/>
            </h3>
            <c:choose>
                <c:when test="${not empty bbss}">
                    <table>
                        <tbody>
                        <tr>
                            <th>编号</th>
                            <th width="60%">标题</th>
                            <th>日期</th>
                            <th>作者</th>
                            <th>浏览次数</th>
                        </tr>
                        <c:forEach var="detail" items="${bbss}">
                            <tr>
                                <td><c:out value="${detail.num}"/></td>
                                <td>[<a
                                        href="<c:url value="/bbs/admin"/>/<c:out value="${bbsnum}"/>/detail/<c:out value="${detail.num}"/>"><c:out
                                        value="${detail.config.bbsname}"/></a>] <a
                                        href="<c:url value="/bbs/admin"/>/<c:out value="${bbsnum}"/>/detail/<c:out value="${detail.num}"/>"><c:out
                                        value="${detail.subject}"/></a>
                                </td>
                                <td><fmt:formatDate
                                        value="${detail.regdate}" pattern="yyyy.MM.dd  HH:mm:ss"/></td>
                                <td><c:out value="${detail.userid}"/></td>
                                <td><c:out value="${detail.count}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    没有文章
                </c:otherwise>
            </c:choose>
        </div>
    </c:when>
</c:choose>
<br class="clearfloat"/>
<div
        style="border: 1px; width: 660px; height: 30px; color: #9999CC; margin-top: 50px;text-align: center">
    <span>共 ${pageSize} 页 / 第 ${curPage} 页 </span>&nbsp;&nbsp;
    <span><a id="firstPage" href="${pageContext.request.contextPath}/bbs/${bbsnum}/list/${firstPage}"> 首页 </a></span>
    <span><a id="prePage" href="${pageContext.request.contextPath}/bbs/${bbsnum}/list/${prePage}"> 上一页 </a></span>
    <span><a id="nextPage" href="${pageContext.request.contextPath}/bbs/${bbsnum}/list/${nextPage}">下一页 </a></span>
    <span><a id="endPage" href="${pageContext.request.contextPath}/bbs/${bbsnum}/list/${lastPage}">尾页</a></span>
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
