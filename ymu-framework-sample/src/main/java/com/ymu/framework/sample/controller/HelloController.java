package com.ymu.framework.sample.controller;

import com.ymu.framework.web.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController extends BaseController {

	@RequestMapping("/hello")
	public String hello() {
		return "hello world";
	}
}
