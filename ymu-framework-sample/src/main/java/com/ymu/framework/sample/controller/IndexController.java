package com.ymu.framework.sample.controller;

import com.ymu.framework.spring.mvc.api.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${base-url}")
public class IndexController extends BaseController {

	@GetMapping("${index.index}")
	public String index() {
		return "index";
	}

	@GetMapping("${index.index}")
	@ApiVersion(2)
	public String index2() {
		return "index2";
	}

	@GetMapping("${index.index}")
	@ApiVersion(3)
	public String index3() {
		return "index3";
	}

	@GetMapping("${index.index}")
	@ApiVersion(5)
	public String index5() {
		return "index5";
	}
}