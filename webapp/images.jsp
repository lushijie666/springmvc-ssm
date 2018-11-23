<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<div class="layui-tab-content" id="content">
    <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
        <div class="layui-upload-list" id="images"></div>
        <div class="layui-upload-drag" id="add" style="width:615px;">
            <i class="layui-icon"></i>
            <p>点击上传，或将文件拖拽到此处</p>
        </div>
    </blockquote>
    <script type="text/javascript">

    var baseUrl = _basePath + "/images";
    layui.use(['element', 'laypage', 'layer', 'form', 'table', 'laydate', 'upload'], function(){
        var jq = layui.jquery,form = layui.form,laypage = layui.laypage,table = layui.table,laydate = layui.laydate,upload= layui.upload;

        jq(function(){
            initData();
        })
       
        function initData(){
             var url = baseUrl + '/list.do';
             jq.get(url, function(result){
                var data = result.data
                if(result.code == 0){
                    jq.each(data, function(index, item){
                    	var imageUrl = _basePath + item.url;
                        jq('#images').append('<img src="'+ imageUrl +'" class="layui-upload-img" width="125px;" height="125px;">')
                   
                    });
                }
            },"json");
        }

        upload.render({
            elem: '#add',
            url : baseUrl + ".do",
            done : function(result){
                if(result.code == 0){
                    location.reload();
                } else {
                    layer.msg(result.msg);
                }

            }
        });
    })
</script>
</div>

</body>
</html>