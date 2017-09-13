package com.ymu.framework.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan(value = "com.ymu")
public class YmuFrameworkSampleApp {

	public static void main(String[] args) {
		SpringApplication.run(YmuFrameworkSampleApp.class, args);
	}
}
