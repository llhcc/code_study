<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.llh.common.WebApp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
hello world!!!----------${name }
<table>  
    <tr>  
        <td>id</td>  
        <td><input id="id" value="100" /></td>  
    </tr>  
    <tr>  
        <td>name</td>  
        <td><input id="name" value="snowolf" /></td>  
    </tr>  
    <tr>  
        <td>status</td>  
        <td><input id="status" value="true" /></td>  
    </tr>  
    <tr>  
        <td><input type="button" id="profile" value="Profile——GET" /></td>  
        <td><input type="button" id="login" value="Login——POST" /></td>  
    </tr>  
</table> 
<!-- http://snowolf.iteye.com/blog/1628861/ -->
<script type="text/javascript" src="<%=WebApp.PATH %>/js/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {  
    $("#profile").click(function() {  
        profile();  
    });  
    $("#login").click(function() {  
        login();  
    });  
});  
function profile() {  
    var url = '<%=WebApp.PATH%>/rest/person/profile/';  
    var query = $('#id').val() + '/' + $('#name').val() + '/'  
            + $('#status').val();  
    url += query;  
    alert(url);  
    $.get(url, function(data) {  
        alert("id: " + data.id + "\nname: " + data.name + "\nstatus: "  
                + data.status);  
    });  
}  
function login() {  
    var mydata = '{"name":"' + $('#name').val() + '","id":"'  
            + $('#id').val() + '","status":"' + $('#status').val() + '"}';  
    alert(mydata);  
    $.ajax({  
        type : 'POST',  
        contentType : 'application/json',  
        url : '<%=WebApp.PATH%>/rest/person/login',  
        processData : false,  
        dataType : 'json',  
        data : mydata,  
        success : function(data) {  
            alert("id: " + data.id + "\nname: " + data.name + "\nstatus: "  
                    + data.status);  
        },  
        error : function() {  
            alert('Err...');  
        }  
    });  
}
</script>
</body>
</html>