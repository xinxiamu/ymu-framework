package com.ymu.framework.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseController {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
    }

    @ModelAttribute
    public void setReqAndRes() {
    }

}
