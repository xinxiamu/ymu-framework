package com.ymu.framework.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.UrlPathHelper;

@Configuration
@EnableWebMvc
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                .setUseSuffixPatternMatch(true)
                .setUseTrailingSlashMatch(false)
                .setUseRegisteredSuffixPatternMatch(true)
                .setPathMatcher(antPathMatcher())
                .setUrlPathHelper(urlPathHelper());
    }

    @Bean
    public UrlPathHelper urlPathHelper() {
        UrlPathHelper u = new UrlPathHelper();
        return  u;
    }

    @Bean
    public PathMatcher antPathMatcher() {
        AntPathMatcher p = new AntPathMatcher();
        return p;
    }
}
