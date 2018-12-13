package com.example.sec.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
	public void whenQuerySuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.param("name", "吴佳同").param("page","10").param("size", "20").param("sort","name,desc")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
	}
}
