package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 * 文件
 * @author 000
 *
 */

import com.jt.service.FileService;
import com.jt.vo.EasyUIFile;
@RestController
public class FileController {
	@Autowired
	private FileService fileService;
	//例子
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) {

		File fileDir = new File("E:/images");
		//没目录创建
		if(!fileDir.exists()) {
			fileDir.mkdirs();
		}
		//名称
		String Filename = fileImage.getOriginalFilename();
		String path = "E:/images/"+Filename;
		 //上传
		try {
			fileImage.transferTo(new File(path));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return "文件上传成功!!!";

	}
	
	@RequestMapping("/pic/upload")
	public EasyUIFile fileUpload(MultipartFile uploadFile) {
		
		return fileService.fileUpload(uploadFile);
		
	}
	
}
