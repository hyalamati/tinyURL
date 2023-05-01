package com.encodeURLApp.URLFormatterService;

import com.encodeURLApp.URLFormatterService.service.UrlFormatterService;
import com.encodeURLApp.URLFormatterService.utility.UrlEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UrlFormatterServiceApplication {

	@Autowired
	UrlFormatterService urlFormatterService;

	public static void main(String[] args) {
		SpringApplication.run(UrlFormatterServiceApplication.class, args);
	}
}
