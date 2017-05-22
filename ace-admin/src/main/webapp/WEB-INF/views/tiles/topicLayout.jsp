<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/import.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><tiles:insertAttribute name="title"/></title>
		<%@ include file="/WEB-INF/views/common/cssjs.jsp" %>
	</head>
<body>
	<tiles:insertAttribute name="header"/>
 <div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>

	<div class="main-container-inner">
		<a class="menu-toggler" id="menu-toggler" href="#">
			<span class="menu-text"></span>
		</a>	
	<tiles:insertAttribute name="topicMenu"/>
	
	<tiles:insertAttribute name="breadcrumbs"/>
			
	<tiles:insertAttribute name="topicList"/>
		
	<tiles:insertAttribute name="footer"/>
    </div>
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="icon-double-angle-up icon-only bigger-110"></i>
	</a>
 </div>
</body>
</html>