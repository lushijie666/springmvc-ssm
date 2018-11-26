package com.xxx.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xxx.model.ClassModel;


@Repository
public interface IClassDAO {
	
	List<ClassModel> getClassList(ClassModel classModel);
	
	Long getClassCount(ClassModel classModel);
	
	int deleteClass(String ids);
}
