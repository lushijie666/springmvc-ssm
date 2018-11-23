<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<table id="courseTable" width="80%" align="center" border="1">
	<tr>
		<td>id</td>
		<td>name</td>
		<td>type</td>
		<td>score</td>
		<td>编辑</td>
		<td>删除</td>
	</tr>
	<tbody id="courseList">
	</tbody> 
</table>
<br/><br/>
<form action="" id="courseForm" method="post">
	<table align="center" border="1">
			<tr>
				<td>id</td>
				<td><input type="number" id="id" name="id" readonly></td>
			</tr>
			<tr>
				<td>name</td>
				<td><input type="text" id="name" name="name"></td>
			</tr>
			<tr>
				<td>type</td>
				<td>
					<select id="type" name="type">
						<option value="1" selected="selected">语文</option>
						<option value="2">数学</option>
						<option value="3">英语</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>score</td>
				<td><input type="number" id="score" name="score"></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="保存" onclick="course.oper();"></td>
			</tr>
	</table>
</form>
<%@ include file="/include/footer.jsp"%>

<script>
	$(function(){
		course.init();
	})
	
	course = {
		init : function(){
			var tbody = window.document.getElementById("courseList")
			var datas = {};
			base.loadScript("/course/list.do",datas,function(data){
				var str = "";
				for (i in data){
					var typeName = "";
					var type = data[i].type;
					if(type == 1){
						typeName = '语文'; 	
            		} else if (type == 2){
            			typeName = '数学'; 	
            		} else if (type == 3){
            			typeName = '英语'; 	
            		}
					str += '<tr>' +
                    '<td>' + data[i].id + '</td>' +
                    '<td>' + data[i].name + '</td>' +
                    '<td>' + typeName +'</td>' +
                    '<td>' + data[i].score + '</td>' +
                    '<td><input type="button" value="编辑" onclick="course.edit('+data[i].id+',\''+data[i].name+'\','+data[i].type+','+data[i].score+');"></td>'+
                    '<td><input type="button" value="删除" onclick="course.del('+data[i].id+');"></td>'+
                    '</tr>';
				}
				tbody.innerHTML  = str;
			});
		},
		edit : function(id, name, type, score){
			$("#courseForm #id").val(id);
			$("#courseForm #name").val(name);
			$("#courseForm #type").val(type);
			$("#courseForm #score").val(score);
		},
		oper : function(){
			var id = $("#courseForm #id").val();
			var name = $("#courseForm #name").val();
			var type = $("#courseForm #type").val();
			var score = $("#courseForm #score").val();
			var datas = {"id": id, "name": name, "type": type, "score": score};
			base.loadScript("/course/oper.do",datas,function(data){
				if(data) {
					$('form')[0].reset();
					course.init();
				}
			});
		},
		del : function(id){
			var datas = {"id": id};
			base.loadScript("/course/delete.do",datas,function(data){
				if(data) {
					$('form')[0].reset();
					course.init();
				}
			});
		}
	};
</script>
