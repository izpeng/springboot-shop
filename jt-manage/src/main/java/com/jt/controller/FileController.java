package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.EasyUIFile;

@RestController
public class FileController {
	
	/**
	 * 1.准备文件存储目录
	 * 2.获取文件名称
	 * 3.利用工具API实现文件上传
	 * @param fileImage
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		File fileDir = new File("D:/JT-SOFTWARE/images");
		if(!fileDir.exists()) {
			//如果没有目录应该先创建目录
			fileDir.mkdirs();
		}
		//获取图片名称
		String fileName = fileImage.getOriginalFilename();
		String path = "D:/JT-SOFTWARE/images/"+fileName;
		
		//文件实现上传
		fileImage.transferTo(new File(path));
		
		return "文件上传成功!!!!";
	}
	
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 实现商品文件上传
	 */
	@RequestMapping("/pic/upload")
	public EasyUIFile fileUpload(MultipartFile uploadFile) {
		
		return fileService.fileUpload(uploadFile);
	}
	
	
	
	
	
	
}
