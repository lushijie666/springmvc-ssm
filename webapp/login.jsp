<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<div class="login_page">
    <h1>登陆</h1>
    <form class="layui-form">
        <div class="layui-form-item">
            <div class="layui-input-inline input-custom-width">
                <input type="text" name="loginName" lay-verify="required" placeholder="请输入登录账号" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline input-custom-width">
                <input type="password" name="password" lay-verify="required" placeholder="请输入登录密码" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline input-custom-width">
                <input type="text" name="code" lay-verify="required" placeholder="请输入验证码" autocomplete="off" class="layui-input" style="width:140px;display: inline-block;">
                <image id="codeImage" style="float:right;" onclick="reloadCode();"/>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline input-custom-width">
                <button class="layui-btn input-custom-width" lay-submit="" lay-filter="login">登录</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
	$(function(){
		$("#codeImage").attr("src",_basePath + "/getCode.do");
	});

	function reloadCode(){
		$("#codeImage").attr("src",_basePath + "/getCode.do?"+Math.random());
	}
    layui.use('form',function(){
        var form = layui.form,jq = layui.jquery;
        form.on('submit(login)', function(data){
            loading = layer.load(2, {shade: [0.2,'#000']});
            var param = data.field;
            var url = _basePath + '/toLogin.do';
            jq.post(url,param,function(result){
                if(result.code == 0){
                    layer.close(loading);
                    location.href = _basePath + '/user2.jsp';
                }else{
                    layer.close(loading);
                    layer.msg(result.msg);
                }
            },"json");
            return false;
        });
    });
</script>
</body>
</html>