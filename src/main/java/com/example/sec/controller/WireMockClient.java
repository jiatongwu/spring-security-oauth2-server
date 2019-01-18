package com.example.sec.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.core.io.ClassPathResource;

import com.github.tomakehurst.wiremock.client.WireMock;

public class WireMockClient {
	//private Logger logger=LoggerFactory.getLogger(getClass());
	public static void main(String[] args) throws IOException {
		WireMock.configureFor("127.0.0.1", 9090);
		WireMock.removeAllMappings();
		
		mock("/user/1","get");
	}

	private static void mock(String url, String file) throws IOException {
		ClassPathResource classPathResource = new ClassPathResource("wiremock/user/"+file+".txt");
		classPathResource.getClass();
		List<String> readLines = FileUtils.readLines(classPathResource.getFile(), "UTF-8");
		String join = StringUtils.join(readLines);
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
				.willReturn(WireMock.aResponse().withBody(join).withStatus(200)));
	}

}
