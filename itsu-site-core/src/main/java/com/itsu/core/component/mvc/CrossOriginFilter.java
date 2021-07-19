package com.itsu.core.component.mvc;

import cn.hutool.core.collection.CollUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CrossOriginFilter implements Filter {

    private final ItsuSiteConfigProperties.CrossOrigin crossOrigin;

    public CrossOriginFilter(ItsuSiteConfigProperties siteConfigProperties) {
        this.crossOrigin = siteConfigProperties.getCrossOrigin();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin",
                CollUtil.join(CollUtil.newArrayList(crossOrigin.getAllowOrigins()), ","));
        response.setHeader("Access-Control-Allow-Methods", CollUtil.join(CollUtil.newArrayList(crossOrigin.getAllowMethods()), ","));
        response.setHeader("Access-Control-Max-Age", crossOrigin.getMaxAge().toString());
        response.setHeader("Access-Control-Allow-Headers", CollUtil.join(CollUtil.newArrayList(crossOrigin.getAllowHeaders()), ","));
        chain.doFilter(req, res);
    }

    // @Override
    // public void init(FilterConfig filterConfig) {
    // }

}