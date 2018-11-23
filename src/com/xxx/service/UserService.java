package com.xxx.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxx.dao.IUserDAO;
import com.xxx.model.UserModel;
import com.xxx.util.MD5;
import com.xxx.util.StringUtil;


@Service
public class UserService{

	@Resource
	private IUserDAO userDao;
	
	
	public boolean insertUser(UserModel userModel) {
		boolean result = false;
		UserModel user = userDao.getUser(userModel);
		if (null == user){
			String password = userModel.getPassword();
			if(StringUtil.isNotNullString(password)){
				userModel.setPassword(MD5.encode(password));
			}
			int i = userDao.insertUser(userModel);
			if (i == 1){
				result = true; 
			}	
		}
		return result;
	}
	
	public boolean insertUsers(List<UserModel> list){
		if(null != list && list.size() > 0){
			for (UserModel user : list){
				insertUser(user);
			}
		}
		return true;
	}
	
	public boolean deleteUser(long id) {
		boolean result = false;
		int i = userDao.deleteUser(id);
		if (i == 1){
			result = true;
		}
		return result;
	}
	
	public boolean updateUser(UserModel userModel) {
		String password = userModel.getPassword();
		if(StringUtil.isNotNullString(password)){
			userModel.setPassword(MD5.encode(password));
		}
		boolean result = false;
		int i = userDao.updateUser(userModel);
		if (i == 1){
			result = true;
		}
		return result;
	}
	
	public List<UserModel> getUserList() {
		return userDao.getUserList(new UserModel());
	}
	
	public long getUserCount(UserModel userModel){
		return userDao.getUserCount(userModel);
	}
	
	public List<UserModel> getUserList(UserModel userModel){
		return userDao.getUserList(userModel);
	}
	
	public UserModel getUser(UserModel userModel){
		return userDao.getUser(userModel);
	}
	
	public Map<String, Long> getData(){
		Map<String, Long> map = new HashMap();
		UserModel userModel = new UserModel();
		userModel.setEnd("20");
		Long count1 = userDao.getUserCount(userModel);
		map.put("<20",count1);
		userModel.setBegin("21");
		userModel.setEnd("40");
		Long count2 = userDao.getUserCount(userModel);
		map.put("21-40",count2);
		userModel.setBegin("41");
		userModel.setEnd(null);
		Long count3 = userDao.getUserCount(userModel);
		map.put(">40",count3);
		return map;
	}
	
}
