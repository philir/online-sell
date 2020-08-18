<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<#include "../common/header.ftl">
	
	<!--图标-->
	<link rel="stylesheet" type="text/css" href="/sell/css/font-awesome.min.css">
	
	<!--布局框架-->
	<link rel="stylesheet" type="text/css" href="/sell/css/util.css">
	
	<!--主要样式-->
	<link rel="stylesheet" type="text/css" href="/sell/css/main.css">
</head>

<body>

<div class="login">
	<div class="container-login100">
		<div class="wrap-login100">
			<div class="login100-pic js-tilt" data-tilt>
				<img src="http://philir.natapp1.cc/sell/image/img.jpg" alt="IMG">
			</div>

			<form class="login100-form validate-form" role="form" method="post" action="/sell/seller/login">
				<span class="login100-form-title">
					卖家端登陆
				</span>

				<div class="wrap-input100 validate-input">
					<input class="input100" type="text" name="username" placeholder="用户名">
					<span class="focus-input100"></span>
					<span class="symbol-input100">
						<i class="fa fa-user-circle-o" aria-hidden="true"></i>
					</span>
				</div>

				<div class="wrap-input100 validate-input">
					<input class="input100" type="password" name="password" placeholder="密码">
					<span class="focus-input100"></span>
					<span class="symbol-input100">
						<i class="fa fa-lock" aria-hidden="true"></i>
					</span>
				</div>
				
				<div class="container-login100-form-btn">
					<button class="login100-form-btn">
						登陆
					</button>
				</div>

				<div class="text-center p-t-12">
					<a class="txt2" href="/sell/weixin/signUp">
						注册
					</a>
				</div>

                <div class="text-center p-t-136">

                </div>

			</form>
		</div>
	</div>
</div>

</body>
</html>