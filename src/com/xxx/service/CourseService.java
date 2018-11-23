package com.xxx.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxx.dao.ICourseDAO;
import com.xxx.dao.ICourseDAO;
import com.xxx.model.CourseModel;


@Service
public class CourseService{

	@Resource
	private ICourseDAO courseDao;
	
	
	public boolean insertCourse(CourseModel CourseModel) {
		boolean result = false;
		int i = courseDao.insertCourse(CourseModel);
		if (i == 1){
			result = true; 
		}
		return result;
	}
	
	public boolean deleteCourse(long id) {
		boolean result = false;
		int i = courseDao.deleteCourse(id);
		if (i == 1){
			result = true;
		}
		return result;
	}
	
	public boolean updateCourse(CourseModel CourseModel) {
		boolean result = false;
		int i = courseDao.updateCourse(CourseModel);
		if (i == 1){
			result = true;
		}
		return result;
	}
	
	public List<CourseModel> getCourseList() {
		return courseDao.getCourseList();
	}
	
}
