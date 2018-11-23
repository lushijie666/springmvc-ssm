package com.xxx.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxx.dao.IImagesDAO;
import com.xxx.dao.IUserDAO;
import com.xxx.model.ImagesModel;
import com.xxx.model.UserModel;


@Service
public class ImagesService{

	@Resource
	private IImagesDAO imagesDao;
	
	
	public boolean insertImage(String url) {
		ImagesModel image = new ImagesModel();
		image.setUrl(url);
		boolean result = false;
		int i = imagesDao.insertImage(image);
		if (i == 1){
			result = true; 
		}
		return result;
	}
	
	public List<ImagesModel> getImageList(){
		return imagesDao.getImageList();
	}
	
}
