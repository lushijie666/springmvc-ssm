package com.xxx.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxx.model.CourseModel;
import com.xxx.service.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {
	
	@Resource
	private CourseService courseService;

	@RequestMapping("/list")
	@ResponseBody
	public List<CourseModel> getCourseList(HttpServletRequest request,HttpServletResponse response){
		return courseService.getCourseList(null);
	}
	
	@RequestMapping("/oper")
	@ResponseBody
	public boolean operCourse(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("name");
		int type = 0;
		String typeStr = request.getParameter("type");
		if (null != typeStr){
			type = Integer.parseInt(typeStr);
		}
		int score = 0;
		String scoreStr = request.getParameter("score");
		if (null != scoreStr){
			score = Integer.parseInt(scoreStr);
		}
		CourseModel courseModel = new CourseModel(name, type, score);
		String id = request.getParameter("id");
		if(null != id && id.trim().length() > 0){
			courseModel.setId(Long.parseLong(id));
			return courseService.updateCourse(courseModel);
		} else {
			return courseService.insertCourse(courseModel);
		}
	}
	@RequestMapping("/delete")
	@ResponseBody
	public boolean deleteCourse(HttpServletRequest request,HttpServletResponse response) {
		long id = Long.parseLong(request.getParameter("id"));
		return courseService.deleteCourse(id);
	}
	
	
}
