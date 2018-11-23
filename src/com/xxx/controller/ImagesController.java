package com.xxx.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xxx.model.ImagesModel;
import com.xxx.model.JsonResponse;
import com.xxx.model.UserModel;
import com.xxx.service.ImagesService;
import com.xxx.util.StringUtil;

@Controller
@RequestMapping("/images")
public class ImagesController {
	
	@Resource
	private ImagesService imagesService;
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getImageList(@RequestParam Map<String, String> paramMap){
        List<ImagesModel> list = imagesService.getImageList();
        JsonResponse response = new JsonResponse();
        response.setSuccessed(list);
        return response;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse operUser(HttpServletRequest request, MultipartFile file) throws IllegalStateException, IOException {
        String path = request.getServletContext().getRealPath("/images/");
        String filename = file.getOriginalFilename();
        String url = path + File.separator + filename;
        file.transferTo(new File(url));
        String getUrl = "/images/" + filename;
        imagesService.insertImage(getUrl);
        JsonResponse response = new JsonResponse();
        response.setSuccessed();
        return response;
	}

}
