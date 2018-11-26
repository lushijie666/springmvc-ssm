<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<div class="layui-tab layui-tab-brief main-tab-container">
    <ul class="layui-tab-title main-tab-title">
        <div class="main-tab-item">班级管理</div>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
                <div class="layui-form-item" style="float: right;">
                    <div class="layui-input-inline">
                        <input type="text" value="" lay-verify="" placeholder="班级名/老师姓名/学生姓名" autocomplete="off" class="layui-input" id="query">
                    </div>
                    <button class="layui-btn" id="search">搜索</button>
                </div>
            <table class="list-table" id="data" lay-filter="data"></table>
            
           <script type="text/html" id="operBar">
           		<div class="layui-btn-container">
    				<button class="layui-btn layui-btn-sm" lay-event="del">删除</button>
  				</div>
            </script>

        </div>
    </div>
</div>

<script type="text/javascript">
    var query = "";
    var url = _basePath + "/class";
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
               	toolbar: '#operBar',
                limit: 5,
                page: {"limits":[5,10,15]},
                cols: [[
					{type: 'checkbox'},
                    {field: 'id', title: '序号', sort: true},
                    {field: 'className', title: '班级名'},
                    {field: 'teacherName', title: '老师姓名'},
                    {field: 'userId', title: '学生id'},
                    {field: 'userName', title: '学生姓名'}
                ]]
            });
        }
        
        table.on('toolbar(data)',function(obj){
            var event = obj.event;
            var checkStatus = table.checkStatus(obj.config.id);
            if(event == 'del'){
            	var data = checkStatus.data;
               	var idsArray = new Array();
               	for(var p in data){
               		idsArray.push(data[p].id);
               	}
               	if (idsArray.length > 0) {
               		var ids = idsArray.join(",");
               		jq.ajax({
                        dataType: "json",
                        url: url + "/" + ids + ".do",
                        type: 'DELETE',
                        success: function (result) {
                        	initData();
                        }
                    });
               	}
            }
        });

    })
</script>

</body>
</html>