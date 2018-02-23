package com.uploader;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UploaderController {
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(String fileName, @RequestParam("file") File file, ModelMap model) {
		model.addAttribute("displayFileName", fileName);
		return "index";
	}

}
