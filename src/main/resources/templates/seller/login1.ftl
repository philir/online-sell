<!DOCTYPE html>
<html lang="zh_CN" xmlns:th="http://www.thymeleaf.org"
      xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<head>
    <meta charset="UTF-8"/>
    <title>微信登录二维码</title>
    <script src="https://code.jquery.com/jquery-2.0.3.min.js"></script>
    <script src="/sell/js/qrcode.js"></script>
    <script type="text/javascript">
        /*<![CDATA[*/

        function checkLogin(){
            var i = 0;
            var sessionId = $("#sessionId").val();
            if(sessionId!=""){
                $.ajax({
                    url: "http://philir.natapp1.cc/sell/wechat/polling",
                    type: "POST",
                    data:{sessionId:sessionId},
                    success: function (data) {
                        if(data.status=="200"){
                            window.clearInterval(c);
                            alert(data.message);
                            location.href = "/";
                        }else{
                            i++;
                            if(i>60){
                                window.clearInterval(c);
                                alert("二维码已失效！请刷新二维码。");
                            }
                        }
                    }
                });
            }
        }

        function wechatLogin(){
            $.ajax({
                url: "http://philir.natapp1.cc/sell/wechat/wxLoginPage",
                type: "POST",
                success: function (data) {
                    $("#sessionId").val(data.sessionId);

                    var qrcode = new QRCode(document.getElementById("code"), {
                        width : 200,//设置宽高
                        height : 200
                    });
                    alert(data.uri);
                    qrcode.makeCode(data.uri);
                    c = setInterval("checkLogin()",5000);//轮询查询
                }
            });
        }
        /*]]>*/
    </script>
</head>
<body>
<shiro:guest>
    <input type="button" value="微信登录" onclick="wechatLogin()" />
</shiro:guest>
<shiro:user>
    <shiro:principal property="username" />您已登录。
</shiro:user>

<input type="hidden" id="sessionId"/>
<br/><br/><br/><br/>
<div id="code"></div>
</body>
</html>