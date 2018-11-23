package com.xxx.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxx.model.CourseModel;
import com.xxx.model.JsonResponse;
import com.xxx.service.CourseService;
import com.xxx.service.UserService;

@Controller
@RequestMapping("/charts")
public class ChartsController {
	
	@Resource
	private UserService userService;

	@RequestMapping("/data")
	@ResponseBody
	public JsonResponse getData(){
		JsonResponse response = new JsonResponse();
		Map<String, Long> data = userService.getData();
		response.setSuccessed(data);
		return response;
	}
	
}
