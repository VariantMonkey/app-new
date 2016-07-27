<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script  type="application/javascript"  src="<c:url value="/ueditor/ueditor.config.js"/>"></script>
<script  type="application/javascript" src="<c:url value="/ueditor/ueditor.all.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/ueditor/lang/zh-cn/zh-cn.js"/>"></script>
<input type="button" value="返回分类列表" class="buttonStyle"
	   onClick="location.href='<c:url value='/bbs/'/>${bbsnum}/list/1'" />
<div class="aboutUs">
	<form:form method="post" action="form" commandName="bbs" enctype="multipart/form-data">
		<table>
			<tr>
				<td width="100%">主题 <font color="red"><form:errors path="subject" /></font></td>
			</tr>
			<tr>
				<td> <form:input path="subject" /> </td>
			</tr>
			<tr>
				<td>内容 <font color="red"><form:errors path="content" /></font></td>
			</tr>
			<tr>
				<td><form:textarea id="editor" path="content" style="height:500px;"/></td>
			</tr>
			<tr>
				<td>文件 : </td>
			</tr>
			
			<tr>
				<td><input type="file" name="file" id="file1"></input></td>
			</tr>
			
			<tr>
				<td><input type="file" name="file" id="file2"></input></td>
			</tr>
			<tr>
				<td><input type="submit" value="提交" /> </td>
			</tr>
		</table>
		<input type="hidden" name="bbsnum" id="bbsnum" value="${bbsnum }"></input>
	</form:form>
</div>

<script>
	document.getElementById("subject").focus();
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	var ue = UE.getEditor('editor',{toolbars: [[
		'undo', 'redo', '|',
		'bold', '|', 'forecolor','|',
		'customstyle', 'fontfamily', 'fontsize', '|',
		'justifyleft', 'justifycenter', '|',
		'link', 'unlink', '|',
		'insertimage', 'template','|',
		'horizontal', 'spechars',  '|',
		'print', 'preview', 'searchreplace', 'help'
	]]});
</script>

<!-- This clearing element should immediately follow the #mainContent div in order to force the #container div to contain all child floats -->
<br class="clearfloat" />
