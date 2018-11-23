package com.xxx.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxx.model.JsonResponse;
import com.xxx.model.UserModel;
import com.xxx.service.UserService;
import com.xxx.util.MD5;
import com.xxx.util.StringUtil;
import com.xxx.util.ValidateCode;

@Controller
@RequestMapping
public class IndexController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "getCode", method = RequestMethod.GET)
	@ResponseBody
	public void getCode(HttpServletRequest request,HttpServletResponse response){
		ValidateCode validate = new ValidateCode();
		String code = validate.getCode();
		request.getSession().setAttribute("code",code);
		try {
			validate.write(response.getOutputStream());
		} catch (Exception e){
		}
	}
	
	@RequestMapping(value = "toLogin", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse toLogin(HttpServletRequest request,@RequestParam Map<String, String> paramMap) {
		JsonResponse response = new JsonResponse();
		String name = paramMap.get("loginName");
		String password = paramMap.get("password");
		String code = paramMap.get("code");
		HttpSession session = request.getSession();
		String sessionCode = session.getAttribute("code").toString();
		if(StringUtil.isNotNullString(code) && sessionCode.equalsIgnoreCase(code)){
			UserModel userModel = new UserModel();
			userModel.setName(name);
			UserModel user = userService.getUser(userModel);
			if (null == user){
				response.setFailed("用户不存在");
			} else {
				String password2 = user.getPassword();
				if (StringUtil.isNotNullString(password2) && (MD5.encode(password).equals(password2))){
					session.setAttribute("user",user);
					response.setSuccessed();
				} else {
					response.setFailed("密码错误");
				}
			}
		} else {
			response.setFailed("验证码错误");
		}
		return response;
	}
}
