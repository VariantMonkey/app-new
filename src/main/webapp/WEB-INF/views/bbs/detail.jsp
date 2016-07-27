<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script src="<c:url value="/js/jquery-1.7.2.min.js"/>"></script>
<script src="<c:url value="/js/bbs.js"/>"></script>

<script type="text/javascript">
	function doAjaxPost(num,contextPath) {
		
		// get the form values
		var content = $('#content').val();

		$.ajax({
			type : "POST",
			url : contextPath + "bbs/detail/"+num+"/reply/add",
			data : "content=" + content,
			beforeSend: function() {
	             $('#ajax_indicator').show().fadeIn('fast'); 
	        },
	        complete: function() {
	             $('#ajax_indicator').fadeOut();
	        },
			success : function(response) {
				// we have the response
				if(response.status == "SUCCESS"){
// 					$('#info').html(response);
					$('#content').val('');
				}else{
					$('#info').html(response);
				}
				//call refresh
				refreshReply(num,contextPath);
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
	
	function removeReply(num,contextPath) {
		$.ajax({
			type : "GET",
			url : contextPath + "bbs/detail/"+num+"/reply/add",
			data : "content=" + content,
			success : function(response) {
				// we have the response
				if(response.status == "SUCCESS"){
					$('#info').html(response);
					$('#content').val('');
				}else{
					$('#info').html(response);
				}
				//call refresh
				refreshReply(num,contextPath);
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
	function refreshReply(num,contextPath) {
		
		$.ajax({
			type : "GET",
			//http://localhost:8080/FlowerPaper/bbs/data/detail/1.json
			url : contextPath + "bbs/data/detail/"+num+".json",
// 			data : "content=" + content,
			success : function(response) {
				
				var replys = document.getElementById("replys");
				
				//all child remove
				if ( replys.hasChildNodes() )
				{
				    while ( replys.childNodes.length >= 1 )
				    {
				    	replys.removeChild( replys.firstChild );       
				    } 
				}
				
				//replyList add
				var replyList = response.bbs.replys;
				for(i=0; i<replyList.length;i++){
					var div = document.createElement("div");
					div.setAttribute("class","memo");
					
					var rnum = replyList[i].rnum;
					var userid = replyList[i].userid;
					var regdate = replyList[i].regdate;
// 					regdate = regdate.date + regdate.day;
					var a = document.createElement("a");
					a.setAttribute("href","javascript:removeReply('"+rnum+","+contextPath+"')");
					a.innerHTML = "删除";
					var br = document.createElement("br");
					var content = replyList[i].content;
					
					div.innerHTML = rnum + " | " + userid + " | " + regdate + " | ";// + a + br + content;
					div.appendChild(a);
					div.appendChild(br);
					div.innerHTML += content;
					
					replys.appendChild(div);
				}
				
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
</script>

<c:if test="${sessionScope.userid == detail.userid }">
	<input type="button" value="修改"
		class="buttonStyle"
		onClick="location.href='<c:url value='/bbs/'/>${bbsnum}/updateform/${detail.num}'" />
	<input type="button" value="删除"
		class="buttonStyle"
		onClick="location.href='<c:url value='/bbs/'/>${bbsnum}/delete/${detail.num}'" />
</c:if>
<input type="button" value="返回分类列表"
	class="buttonStyle"
	onClick="location.href='<c:url value='/bbs/'/>${bbsnum}/list/1'" />


<div class="memo">
	<h1>
		<c:out value="${detail.num}" />
		|
		<c:out value="${detail.subject}" />
		|
		<c:out value="${detail.userid}" />
		|
		<c:out value="${detail.regdate}" />
	</h1>

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


<div class="memo" id="bottom">


<div id="info"></div>

<!-- Ajax 加载图像 -->
<div class="memo" id="ajax_indicator" style="display: none">
    <p
        style="text-align: center; padding: 0 0 0 0; left: 50%; top: 50%;">
        <img src="${pageContext.request.contextPath}/images/viewLoading.gif" />
    </p>
</div>

</div>

<div class="clearfloat"></div>
