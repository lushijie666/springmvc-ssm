package com.xxx.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.xxx.model.JsonResponse;
import com.xxx.model.UserModel;
import com.xxx.service.UserService;
import com.xxx.util.ExecelUtil;
import com.xxx.util.MD5;
import com.xxx.util.StringUtil;

@Controller
@RequestMapping("/user2")
public class User2Controller {
	
	@Resource
	private UserService userService;

	@RequestMapping(value="/list",method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getUserList(@RequestParam Map<String, String> paramMap){
		UserModel userModel = new UserModel();
		userModel.setRequirePage(true);
        String pageIndex = paramMap.get("page");
        if(StringUtil.isNotNullString(pageIndex)){
        	userModel.setPageIndex(Integer.parseInt(pageIndex));
        }
        String pageSize = paramMap.get("limit");
        if(StringUtil.isNotNullString(pageSize)){
        	userModel.setPageSize(Integer.parseInt(pageSize));
        }
        userModel.setQuery(paramMap.get("query"));
        long totalCount = userService.getUserCount(userModel);
        List<UserModel> pageList = userService.getUserList(userModel);
        JsonResponse response = new JsonResponse();
        response.setCount(totalCount);
        response.setSuccessed(pageList);
        return response;
	}
	
	@RequestMapping(value="/list/export",method = RequestMethod.GET)
	@ResponseBody
	public void getUserListExport(HttpServletResponse response, @RequestParam Map<String, String> paramMap){
		UserModel userModel = new UserModel();
        userModel.setQuery(paramMap.get("query"));
        List<UserModel> pageList = userService.getUserList(userModel);
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=user.xls");
        
        try {
            ExecelUtil.write(response.getOutputStream(), ExecelUtil.EXECEL_TYPE_XLS, getUserColumnMapper(),new String[]{"姓名", "年龄", "工作"},
            		pageList, UserModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse operUser(@RequestParam Map<String, String> paramMap) {
		String name = paramMap.get("name");
		String password = paramMap.get("password");
		int age = 0;
		String ageStr = paramMap.get("age");
		if (null != ageStr){
			age = Integer.parseInt(ageStr);
		}
		String job = paramMap.get("job");
		UserModel userModel = new UserModel(name, age, job);
		userModel.setPassword(password);
		String id = paramMap.get("id");
		boolean result = false;
		if(null != id && id.trim().length() > 0){
			userModel.setId(Long.parseLong(id));
			result = userService.updateUser(userModel);
		} else {
			result = userService.insertUser(userModel);
		}
		JsonResponse response = new JsonResponse();
		if(result){
			response.setSuccessed();
		}
		return response;
	}
	
	@RequestMapping(value="/read", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse readUser(MultipartFile file) throws IOException, Exception {
		List<UserModel> userList = ExecelUtil.read(file.getInputStream(), ExecelUtil.EXECEL_TYPE_XLS, getUserColumnMapper(), UserModel.class, 1);
		userService.insertUsers(userList);
		return new JsonResponse();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResponse deleteUser(@PathVariable(value = "id") Long id, @RequestParam Map<String, String> paramMap) {
		userService.deleteUser(id);
		JsonResponse response = new JsonResponse();
		response.setSuccessed();
		return response;
	}
	
	private Map<String, Class> getUserColumnMapper(){
		Map<String, Class> columnMapper = new LinkedHashMap<>(4);
        columnMapper.put("name", String.class);
        columnMapper.put("age", Integer.class);
        columnMapper.put("job", String.class);
        return columnMapper;
	}
	
}
