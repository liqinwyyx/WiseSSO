<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="login-alone">
<head>
<title>登录</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/screen.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/login.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(
		function() {
			//focus username field
			$("input:visible:enabled:first").focus();

			$(".vcode").click(
				function() {
					$(".vcode").attr("src", "${pageContext.request.contextPath}/verify.do?t=" + new Date()); 
			});
		});

	function getParam(name) {
		var queryString = window.location.search;
		var param = queryString.substr(1, queryString.length - 1).split("&");
		for ( var i = 0; i < param.length; i++) {
			var keyValue = param[i].split("=");
			if (keyValue[0] == name)
				return keyValue[1];
		}
		return null;
	}

	function init() {
		// 显示异常信息
		var error = getParam("errorMessage");
		if (error) {
			document.getElementById("errorMessage").innerHTML = decodeURIComponent(error);
		}

		// 注入service
		var service = getParam("service");
		if (service) {
			document.getElementById("service").value = decodeURIComponent(service);
		} else {
			document.getElementById("service").value = location.href;
		}
	}
</script>
</head>
  <body>
      <div class="logina-logo" style="height: 55px">
        <a href="">
            <img src="${pageContext.request.contextPath}/static/toplogo.png" height="60" alt="">
        </a>
    </div>
    <div class="logina-main main clearfix">
        <div class="tab-con">
            <form id="form-login" method="post" action="${pageContext.request.contextPath}/noflow">
           		<input type="hidden" id="targetService" name="service" value="http://localhost:8080/wise_sso_client/sso/index.jsp">  
        		<input type="hidden" name="failpage" value="http://localhost:8080/wise_sso_client/index.jsp">  
                <div id='login-error' class="error-tip"></div>
                <table border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th>账户</th>
                            <td width="245">
                                <input type="text" name="username" placeholder="电子邮箱/手机号" autocomplete="off" required="required" />
                            </td>
                            <td>
                            </td>
                        </tr>
                        <tr>
                            <th>密码</th>
                            <td width="245">
                                <input type="password" name="password" placeholder="请输入密码" autocomplete="off" required="required" />
                            </td>
                            <td>
                            </td>
                        </tr>
                        <tr id="tr-vcode">
                            <th>验证码</th>
                            <td width="245">
                                <div class="valid">
                                    <input type="text" name="authcode" maxlength="4" required="required">
                                    <img class="vcode" src="${pageContext.request.contextPath}/verify.do" width="85" height="35" alt="验证码" />
                                </div>
                            </td>
                            <td>
                            </td>
                        </tr>
                        <tr class="find">
                            <th></th>
                            <td>
                                <div>
                                    <%--<label class="checkbox" for="chk11">
                                    <input style="height: auto;" id="chk11" type="checkbox" name="remember_me" >记住我</label>
                                    --%><a href="#">忘记密码？</a>
                                </div>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <th></th>
                            <td width="245"><input class="confirm" type="submit" value="登  录"></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" name="refer" value="site/">
            </form>
        </div>
        <div class="reg">
            <p>还没有账号？<br>赶快免费注册一个吧！</p>
            <a class="reg-btn" href="#">立即免费注册</a>
        </div>
    </div>
    <div id="footer">
        <div class="copyright">Copyright © 2015 群智合软件. All Rights Reserved.</div>
    </div>
</body>
</html>
