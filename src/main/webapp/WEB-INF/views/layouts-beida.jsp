<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>beida</title>
<link rel="stylesheet" href="<c:url value="/css/beida/style.css" />" type="text/css" />
<link rel="stylesheet" href="<c:url value="/css/beida/fgzd.css" />" type="text/css" />
<link rel="stylesheet" href="<c:url value="/css/beida/ji_gou_jian_jie.css" />" type="text/css" />
<style type="text/css">
.yybc, .wsYy {
	padding: 10px;
	line-height: 25px;
	text-align: center;
}

.yybc a, .wsYy a {
	color: #555;
}

.yybc a:hover, .wsYy a:hover {
	color: #149FF1;
}

#navi {
	background: none !important;
	background-color: #FFF !important;
}

#navi a {
	padding-bottom: 10px !important;
}
</style>
</head>
 
<body>
<div class="container">
    <tiles:insertAttribute name="header" />
    <tiles:insertAttribute name="content" />
    <tiles:insertAttribute name="sidebar" />
    <tiles:insertAttribute name="footer" />
</div>
</body>
</html>