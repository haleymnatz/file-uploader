package com.uploader;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UploaderController {
	
	@Autowired
	S3Utils s3Utils;
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(String fileName, @RequestParam("file") MultipartFile multipartFile, ModelMap model) {
		s3Utils.s3UploadFile(fileName, multipartFile);
		model.addAttribute("displayFileName", fileName);
		return "index";
	}

}
