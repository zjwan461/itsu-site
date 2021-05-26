package com.itsu.core.component.mvc;

import cn.hutool.core.collection.CollUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {

    @Autowired
    private ItsuSiteConfigProperties kProperties;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin",
                CollUtil.join(CollUtil.newArrayList(kProperties.getAllowOrigins()), ","));
        response.setHeader("Access-Control-Allow-Methods", "POST, DELETE, PUT, GET");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        chain.doFilter(req, res);
    }

    // @Override
    // public void init(FilterConfig filterConfig) {
    // }

}