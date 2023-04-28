package com.blog.blogappapis.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.blogappapis.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//get file name
		String name = file.getOriginalFilename();
		String str=new String(".");
		
		String random = UUID.randomUUID().toString();
		String fileName1 = random.concat(name.substring(name.lastIndexOf(str)));
		
		//Full path
		String filePath = path + File.separator + fileName1;
		
		//create folder if not exist
		
		File f = new File(path);
		
		if(!f.exists())
		{
			f.mkdir();
		}
		
		//copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return name;

	}

	@Override
	public InputStream getResources(String path, String fileName) throws FileNotFoundException {

		//Full path
		String filePath = path + File.separator + fileName;
		
		InputStream is = new FileInputStream(filePath);
		
		return is;
	}

}
