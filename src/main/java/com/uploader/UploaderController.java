package com.uploader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3ObjectInputStream;


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
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listObjects(ModelMap model) {
		List<String> objects = s3Utils.s3ListObjects();
		model.addAttribute("objects", objects);
		return "index";
	}
	
	@RequestMapping(value ="/download", method = RequestMethod.GET)
	public String downloadObject(@RequestParam("objectKey") String keyName, HttpServletResponse response) {
		System.out.println(keyName);
		try {
			S3ObjectInputStream s3is = s3Utils.s3DownloadObject(keyName);
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=" + keyName);
		    IOUtils.copy(s3is, response.getOutputStream());
		    s3is.close();
		    response.flushBuffer();
	    } catch (IOException ex) {
	      throw new RuntimeException("IOError writing file to output stream");
	    }
		return "index";
	}
	
	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteObject(@RequestParam("objectKey") String keyName) {
		s3Utils.s3DeleteObject(keyName);
		return "index";
	}

}
