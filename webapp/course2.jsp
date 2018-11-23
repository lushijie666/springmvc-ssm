<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<div class="layui-tab layui-tab-brief main-tab-container">
    <ul class="layui-tab-title main-tab-title">
        <div class="main-tab-item">课程管理</div>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
                <div class="layui-form-item" style="float: right;">
                    <div class="layui-input-inline">
                        <input type="text" value="" lay-verify="" placeholder="课程名" autocomplete="off" class="layui-input" id="query">
                    </div>
                    <button class="layui-btn" id="search">搜索</button>
                </div>
            <button id="add" class="layui-btn">
                <i class="layui-icon">&#xe654;</i>新增
            </button>
            <table class="list-table" id="data" lay-filter="data"></table>
            <script type="text/html" id="operBar">
                <a class="layui-btn layui-btn-sm" lay-event="edit"><i class='layui-icon'>&#xe642;</i>编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-sm" lay-event="del"><i class='layui-icon'>&#xe640;</i>删除</a>
            </script>
        </div>
    </div>
</div>

<div class="layui-tab-content" id="content" hidden>
    <div class="layui-tab-item layui-show">
        <form class="layui-form">
            <input type="text" id="id" name="id" class="layui-input hide">

            <div class="layui-form-item">
                <label class="layui-form-label">课程名</label>
                <div class="layui-input-inline input-custom-width">
                    <input type="text" id="name" name="name" value="" lay-verify="required" autocomplete="off" placeholder="请输入课程名" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">类型</label>
                <div class="layui-input-inline input-custom-width">
                    <select id="type" name="type" lay-verify="required" lay-search="">
                        <option value="0">语文</option>
                        <option value="1">数学</option>
                        <option value="2">英语</option>
                        <option value="3">化学</option>
                    </select>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">分数</label>
                <div class="layui-input-inline input-custom-width">
                    <input type="text" id="score" name="score" value="" lay-verify="required" autocomplete="off" placeholder="请输入分数" class="layui-input">
                </div>
            </div>
            
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="save">保存</button>
                    <button type="reset" class="layui-btn layui-btn-small layui-btn-danger" id="resetButton">重置</button>
                </div>
            </div>

        </form>
    </div>
</div>


<script type="text/javascript">
    var query = "";
    var url = _basePath + "/course2";
    layui.use(['element', 'laypage', 'layer', 'form', 'table', 'laydate', 'upload'], function(){
        var jq = layui.jquery,form = layui.form,laypage = layui.laypage,table = layui.table,laydate = layui.laydate,upload= layui.upload;

        jq(function (){
            initData();
        });

        jq('#search').click(function(){
            query = jq("#query").val();
            initData();
        });

        function initData(){
            var getUrl = url + "/list.do?query=" + query ;
            table.render({
                id: 'id',
                elem: '#data',
                url: getUrl,
                limit: 5,
                page: {"limits":[5,10,15]},
                cols: [[
                    {field: 'left', title: '序号', type: 'numbers'},
                    {field: 'name', title: '课程名'},
                    {field: 'type', title: '类型' , templet : function(d){
                        var temp = d.type;
                        if(temp == 0){
                        	return "语文";
                        } else if (temp == 1){
                        	return "数学";
                        } else if (temp == 2){
                        	return "英语";
                        } else if (temp == 3){
                        	return "化学";
                        }
                    }},
                    {field: 'score', title: '分数'},
                    {fixed: 'right', title: '操作', width: 165, align:'center', toolbar: '#operBar'}
                    
                ]]
            });
        }

        jq('#add').click(function(){
            jq('#resetButton').click();
            layer.open({
                title : '添加',
                type: 1,
                content: jq('#content'),
                area: ['500px','490px']
            });
        });
        
       table.on('tool(data)',function(obj){
            var data = obj.data,event = obj.event;
            if(event == 'del'){
                 jq.ajax({
                     dataType: "json",
                     url: url + "/" + data.id + ".do",
                     type: 'DELETE',
                     success: function (result) {
                     	initData();
                     }
                 });
            }
            if(event == 'edit'){
                jq('#resetButton').click();
                layer.open({
                    title : '编辑',
                    type: 1,
                    content: jq('#content'),
                    area: ['500px','490px']
                });
                jq('#content #id').val(data.id);
                jq('#content #name').val(data.name);
                jq('#content #type').val(data.type);
                jq('#content #score').val(data.score);
            }
        });

        form.on('submit(save)', function(data){
            var param = data.field;
            loading = layer.load(2, {shade: [0.2,'#000']});
            jq.post(url + ".do", param, function(result){
                var data = result.data
                if(result.code == 0){
                    layer.close(loading);
                    location.reload();
                } else {
                    layer.close(loading);
                    layer.msg(result.msg);
                }
            },"json");
            return false;
        });
    })
</script>

</body>
</html>