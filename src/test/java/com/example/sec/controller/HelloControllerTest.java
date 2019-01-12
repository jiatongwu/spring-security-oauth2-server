package com.example.sec.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest {

	@Autowired
	private WebApplicationContext web;
	private MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(web).build();
	}
	
	@Test
	public void whenUploadSuccess()throws Exception{
		String contentAsString = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file").file(new MockMultipartFile("file", "a.txt","multipart/-data","hello".getBytes("UTF-8")))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println(contentAsString);
	}
	
	

	@Test
	public void whenQuerySuccess() throws Exception {
		String result=mockMvc.perform(MockMvcRequestBuilders.get("/user").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.param("name", "吴佳同").param("page","10").param("size", "20").param("sort","name,desc")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
		.andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	@Test
	public void whenGetInfoSuccess() throws Exception {
		String result=mockMvc.perform(MockMvcRequestBuilders.get("/user/1")//.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom")).andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	@Test
	public void whenGetInfoFail() throws Exception {
		String result=mockMvc.perform(MockMvcRequestBuilders.get("/user/a")//.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				).andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void whenCreateSuccess() throws Exception {
		Date date=new Date();
		long time = date.getTime();
		String content="{\"username\":\"wu\",\"password\":null,\"birthday\":"+time+"}";
		String result=mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(content)				
				).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1)).andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	@Test
	public void whenUpdateSuccess() throws Exception {
		//Date date=new Date();
		//long time = date.getTime();
		Date date=new Date(LocalDateTime.now().plusYears(1l).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		long time=date.getTime();
		String content="{\"id\":1,\"username\":\"wu2\",\"password\":null,\"birthday\":"+time+"}";
		String result=mockMvc.perform(MockMvcRequestBuilders.put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(content)				
				).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1)).andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	@Test
	public void whenDeleteSuccess() throws Exception {		
		String result=mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")//.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
}
