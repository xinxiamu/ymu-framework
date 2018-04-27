package com.ymu.framework.filter;

import com.ymu.framework.web.ModifyHttpServletRequestWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 功能简述:<br>
 *     在过滤器中为请求版本做处理。
 *
 * @author zmt
 * @create 2018-04-26 下午5:15
 * @updateTime
 * @since 1.0.0
 */
public class IndexFilter implements Filter {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug(">>>>indexFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug(">>>>indexFilter doFilter");

        //添加或者更改header信息
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String apiVersion = request.getHeader("Content-Version");
        if (null == apiVersion || "".equals(apiVersion)) {
            ModifyHttpServletRequestWrapper requestWrapper = new ModifyHttpServletRequestWrapper(request);
            requestWrapper.putHeader("Content-Version","-1");
            filterChain.doFilter(requestWrapper,servletResponse);
        } else {
            filterChain.doFilter(request,servletResponse);
        }
    }

    @Override
    public void destroy() {
        logger.debug(">>>>indexFilter destroy");
    }
}
