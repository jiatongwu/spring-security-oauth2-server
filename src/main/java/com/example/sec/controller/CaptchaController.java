package com.example.sec.controller;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.google.code.kaptcha.Producer;

@RestController
public class CaptchaController {

	public static final String SESSION_IMAGECODE_KEY = "session_imagecode_key";
	@Resource
	private Producer captchaProducer;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@GetMapping(value = "/captchaImage")
	public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();// 生成验证码字符串
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_IMAGECODE_KEY, capText);
		// Cookie cookie = new Cookie(Constants.KAPTCHA_SESSION_KEY, capText); //
		// 生成cookie
		// cookie.setMaxAge(300); // 300秒生存期
		// response.addCookie(cookie); // 将cookie加入response
		BufferedImage bi = captchaProducer.createImage(capText);// 生成验证码图片
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return;
	}

}
