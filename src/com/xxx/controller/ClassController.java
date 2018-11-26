package com.xxx.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import com.xxx.model.ClassModel;
import com.xxx.model.JsonResponse;
import com.xxx.model.UserModel;
import com.xxx.service.ClassService;
import com.xxx.service.ClassService;
import com.xxx.util.StringUtil;

@Controller
@RequestMapping("/class")
public class ClassController {
	
	@Resource
	private ClassService classService;

	@RequestMapping(value="/list",method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getClassList(@RequestParam Map<String, String> paramMap) throws UnsupportedEncodingException{
		ClassModel classModel = new ClassModel();
		String pageIndex = paramMap.get("page");
		if (StringUtil.isNotNullString(pageIndex)) {
			classModel.setPageIndex(Integer.parseInt(pageIndex));
		}
		String pageSize = paramMap.get("limit");
		if (StringUtil.isNotNullString(pageSize)) {
			classModel.setPageSize(Integer.parseInt(pageSize));
		}
		classModel.setQuery(paramMap.get("query"));
		long totalCount = classService.getClassCount(classModel);
		List<ClassModel> pageList = classService.getClassList(classModel);
		JsonResponse response = new JsonResponse();
		response.setCount(totalCount);
		response.setSuccessed(pageList);
		return response;
	}
	
	@RequestMapping(value="/{ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResponse deleteClass(@PathVariable(value = "ids") String ids) {
		classService.deleteClass(ids);
		JsonResponse response = new JsonResponse();
		response.setSuccessed();
		return response;
	}
	
	
}
