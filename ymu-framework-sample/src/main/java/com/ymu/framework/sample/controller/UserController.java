package com.ymu.framework.sample.controller;

import com.ymu.framework.sample.vo.VUser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	@PostMapping("/save")
	public boolean saveUser(@RequestBody @Valid  VUser vUser) {
		return true;
	}

	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public VUser getUser(@Valid VUser vUser,BindingResult result) {
//		if (result.hasErrors()) {
//			result.reject("409","错误");
//		}
		return vUser;
	}
}
