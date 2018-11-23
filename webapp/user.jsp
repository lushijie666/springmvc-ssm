<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<table id="userTable" width="80%" align="center" border="1">
	<tr>
		<td>id</td>
		<td>name</td>
		<td>age</td>
		<td>job</td>
		<td>编辑</td>
		<td>删除</td>
	</tr>
	<tbody id="userList">
	</tbody> 
</table>
<br/><br/>
<form action="" id="userForm" method="post">
	<table id="operUser" align="center" border="1">
			<tr>
				<td>id</td>
				<td><input type="number" id="id" name="id" readonly></td>
			</tr>
			<tr>
				<td>name</td>
				<td><input type="text" id="name" name="name"></td>
			</tr>
			<tr>
				<td>age</td>
				<td><input type="number" id="age" name="age"></td>
			</tr>
			<tr>
				<td>job</td>
				<td><input type="text" id="job" name="job"></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="保存" onclick="user.oper();"></td>
			</tr>
	</table>
</form>
<%@ include file="/include/footer.jsp"%>

<script>
	$(function(){
		user.init();
	})
	
	user = {
		init : function(){
			var tbody = window.document.getElementById("userList")
			var datas = {};
			base.loadScript("/user/list.do",datas,function(data){
				var str = "";
				for (i in data){
					str += '<tr>' +
                    '<td>' + data[i].id + '</td>' +
                    '<td>' + data[i].name + '</td>' +
                    '<td>' + data[i].age + '</td>' +
                    '<td>' + data[i].job + '</td>' +
                    '<td><input type="button" value="编辑" onclick="user.edit('+data[i].id+',\''+data[i].name+'\','+data[i].age+',\''+data[i].job+'\');"></td>'+
                    '<td><input type="button" value="删除" onclick="user.del('+data[i].id+');"></td>'+
                    '</tr>';
				}
				tbody.innerHTML  = str;
			});
		},
		edit : function(id, name, age, job){
			$("#userForm #id").val(id);
			$("#userForm #name").val(name);
			$("#userForm #age").val(age);
			$("#userForm #job").val(job);
		},
		oper : function(){
			var id = $("#userForm #id").val();
			var name = $("#userForm #name").val();
			var age = $("#userForm #age").val();
			var job = $("#userForm #job").val();
			var datas = {"id": id, "name": name, "job": job, "age": age};
			base.loadScript("/user/oper.do",datas,function(data){
				if(data) {
					$('form')[0].reset();
					user.init();
				}
			});
		},
		del : function(id){
			var datas = {"id": id};
			base.loadScript("/user/delete.do",datas,function(data){
				if(data) {
					$('form')[0].reset();
					user.init();
				}
			});
		}
	};
</script>
