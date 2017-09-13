package com.ymu.framework.sample.vo.valid;

import com.ymu.framework.sample.vo.VUser;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class VUserValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return VUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors e) {
        ValidationUtils.rejectIfEmpty(e, "name", "name.empty");
        VUser vUser = (VUser) target;
        if (vUser.getAge() < 0) {
            e.rejectValue("age", "年龄不能小于0");
//            throw new NullPointerException("ddddddd");
        } else if (vUser.getAge() > 110) {
            e.rejectValue("age", "活不到110岁吧");
        }
    }
}
