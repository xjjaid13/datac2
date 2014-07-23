<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="login-bg">
<head>
	<title>Sign in</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/static/css/login.css" />
    <style>
    </style>

</head>
<body>
	<div style="height:500px;background-image:linear-gradient(to bottom, #f5f5f5 0px, #e8e8e8 100%);margin-top:100px;">
	    <div class="login-wrapper">
	        <div class="box">
	            <div class="content-wrap">
	                <h6>登陆</h6>
	                <input class="form-control" id="username" value="taylor" type="text" placeholder="E-mail address">
	                <input class="form-control" id="password" value="111111" type="password" placeholder="Your password">
	                <a href="#" class="forgot">Forgot password?</a>
	                <div class="remember">
	                    <input id="remember-me" type="checkbox" checked="checked">
	                    <label for="remember-me">Remember me</label>
	                </div>
	                <button id="login" class="btn btn-primary">Log in</button>
	            </div>
	        </div>
	
	        <div class="no-account">
	            <p>Don't have an account?</p>
	            <a href="signup.html">Sign up</a>
	        </div>
	    </div>
	</div>
</body>
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script>
	$(function(){
		$("#login").click(function(){
			$.ajax({
				url : '${base}/login/validLogin',
				data : 'username='+$("#username").val()+"&password="+$("#password").val(),
				dataType : 'json',
				success : function(ajaxData){
					if(ajaxData.result == 'success'){
						if(ajaxData.valid == true){
							location.href="${base}/rss/myIndex";
						}
					}
				}
			});
		});
	});
</script>
</html>