package com.jt.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.EasyUIFile;


public interface FileService {

	EasyUIFile fileUpload(MultipartFile uploadFile);

}
