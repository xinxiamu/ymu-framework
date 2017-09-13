package com.ymu.framework.sample.controller;

import com.ymu.framework.sample.vo.VUser;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("user")
public class UserController extends BaseController {

	@PostMapping("/save")
	public boolean saveUser(@RequestBody @Valid  VUser vUser) {
		return true;
	}

	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public VUser getUser( VUser vUser) {
		return vUser;
	}

}
