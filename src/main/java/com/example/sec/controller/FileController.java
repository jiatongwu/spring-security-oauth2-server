package com.example.sec.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

@RestController
@RequestMapping("/file")
public class FileController {
	@PostMapping
	public String uploadFile(MultipartFile file) throws IOException {
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getName());
		String currentPathString = this.getClass().getResource(System.currentTimeMillis() + ".txt").toString();
		System.out.println(currentPathString);
		IOUtils.copy(file.getInputStream(), Files.newOutputStream(Paths.get(currentPathString)));
		return "ok";
	}

	@GetMapping("/{id}")
	public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String currentPathString = this.getClass().getResource(System.currentTimeMillis() + ".txt").toString();
		try(InputStream is=Files.newInputStream(Paths.get(currentPathString));
				OutputStream os=response.getOutputStream();){
			response.setContentType("application/x-download");
			 //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UriUtils.encode("简易程序.docx", "UTF-8") + "\";\nfilename*=utf-8''" + UriUtils.encode("简易程序.docx", "UTF-8"))
			//Content-Disposition: attachment;filename="encoded_text";filename*=utf-8''encoded_text
			response.addHeader("Content-Disposition", "attachment;filename="+UriUtils.encode("测试.txt", "UTF-8")+";filename*=utf-8''"+UriUtils.encode("测试.txt", "UTF-8"));
			IOUtils.copy(is, os);
			os.flush();
		}
	}

}
