package com.xxx.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xxx.dao.IClassDAO;
import com.xxx.model.ClassModel;


@Service
public class ClassService{

	@Resource
	private IClassDAO classDao;
	
	public List<ClassModel> getClassList(ClassModel ClassModel){
		return classDao.getClassList(ClassModel);
	}
	
	public Long getClassCount(ClassModel ClassModel){
		return classDao.getClassCount(ClassModel);
	}
	
	public void deleteClass(String ids) {
		classDao.deleteClass(ids);
	}
	
}
