package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.EasyUIFile;

@Service
public class FileServiceImpl implements FileService {
	private String  localDir = "E:/images/";
	EasyUIFile easyUIFile =new EasyUIFile();
	@Override
	public EasyUIFile fileUpload(MultipartFile uploadFile) {

		/**
		 * 1是否为图片
		 * 2防止恶意程序
		 * 3图片分文件保存
		 * 4修改文件名称
		 */
		//List <String>//1是否为图片
		String Filename = uploadFile.getOriginalFilename();
		if(!Filename.matches("^.+\\.(jpg|png|gif)$")) {
			EasyUIFile.fail();
		}


		//2防止恶意程序
		try {
			//转化为图片对象,拿属性判断
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if(width==0||height==0) {
				return EasyUIFile.fail();
			}
			//3.图片分文件保存 mulu
			//E:\images


			String dataDir = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());

			String fileDirPath = localDir+dataDir;
			File dirFile = new File(fileDirPath);
			if(!dirFile.exists()) {
				dirFile.mkdirs();
			}

			//4修改文件名称
			int index = Filename.lastIndexOf(".");
			String fileType = Filename.substring(index);
			String uuid = UUID.randomUUID().toString();
			String realFileName = uuid+fileType;

		} catch (IOException e) {
			e.printStackTrace();
			return EasyUIFile.fail();
		}

		//上传
		try {
			uploadFile.transferTo(new File("fileDirPath+realFileName"));
			
			easyUIFile.setHeight(10).setWidth(10).setUrl("https://img12.360buyimg.com/n7/jfs/t1/44566/10/12498/119136/5d8c905cEea456e2a/c34e3f95cc5bf38b.jpg");
			
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return easyUIFile;
	}

}
