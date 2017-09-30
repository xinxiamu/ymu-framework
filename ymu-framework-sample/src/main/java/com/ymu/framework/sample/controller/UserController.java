package com.ymu.framework.sample.controller;

import com.ymu.framework.sample.vo.VUser;
import com.ymu.framework.sample.vo.valid.VUserValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new VUserValidator());
	}

	@PostMapping("/save")
	public boolean saveUser(@RequestBody @Valid  VUser vUser) {
		return true;
	}

	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public VUser getUser(@Valid VUser vUser,BindingResult result) {
		if (result.hasErrors()) {
			result.reject("409","错误");
		}
		return vUser;
	}

	@Value("${index.index}")
	private String str;

	@GetMapping("${index.index}")
	public String getStr() {
		return str;
	}

}
