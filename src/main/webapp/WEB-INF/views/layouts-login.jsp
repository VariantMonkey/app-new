<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<c:url value="/ckeditor/ckeditor.js"/>"></script>
<title>管理端</title>
<link rel="stylesheet" href="<c:url value="/css/response_table.css" />" type="text/css" />
<link rel="stylesheet" href="<c:url value="/css/reset.css" />" type="text/css" />
<link rel="stylesheet" href="<c:url value="/css/styles.css" />" type="text/css" />
<link rel="stylesheet" href="<c:url value="/css/media-queries.css" />" type="text/css" />
</head>
 
<body>
<div id="container">
    <tiles:insertAttribute name="header" />
    <tiles:insertAttribute name="content" />
    <tiles:insertAttribute name="sidebar" />
    <tiles:insertAttribute name="footer" />
</div>
</body>
</html>