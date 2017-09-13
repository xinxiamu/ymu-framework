package com.ymu.framework.sample.controller;

import com.ymu.framework.sample.vo.valid.VUserValidator;
import com.ymu.framework.web.AbstractBaseController;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseController extends AbstractBaseController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new VUserValidator());
    }
}
