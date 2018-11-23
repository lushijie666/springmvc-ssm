package com.xxx.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xxx.model.ImagesModel;
import com.xxx.model.UserModel;


@Repository
public interface IImagesDAO {
	
	int insertImage(ImagesModel imageModel);
	
	
	List<ImagesModel> getImageList();
	
	
}
