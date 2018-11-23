package com.xxx.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxx.model.UserModel;
import com.xxx.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;

	@RequestMapping("/list")
	@ResponseBody
	public List<UserModel> getUserList(HttpServletRequest request,HttpServletResponse response){
		return userService.getUserList();
	}
	
	@RequestMapping("/oper")
	@ResponseBody
	public boolean operUser(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("name");
		int age = 0;
		String ageStr = request.getParameter("age");
		if (null != ageStr){
			age = Integer.parseInt(ageStr);
		}
		String job = request.getParameter("job");
		UserModel userModel = new UserModel(name, age, job);
		String id = request.getParameter("id");
		if(null != id && id.trim().length() > 0){
			userModel.setId(Long.parseLong(id));
			return userService.updateUser(userModel);
		} else {
			return userService.insertUser(userModel);
		}
	}
	@RequestMapping("/delete")
	@ResponseBody
	public boolean deleteUser(HttpServletRequest request,HttpServletResponse response) {
		long id = Long.parseLong(request.getParameter("id"));
		return userService.deleteUser(id);
	}
	
	
}
