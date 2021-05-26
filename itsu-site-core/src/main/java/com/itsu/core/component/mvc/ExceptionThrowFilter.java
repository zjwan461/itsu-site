package com.itsu.core.component.mvc;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Jerry Su
 * @Date 2021/5/25 16:51
 */
public class ExceptionThrowFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            request.setAttribute("filter.exception", e);
            request.getRequestDispatcher("/filterThrowException").forward(request, response);
        }
    }
}
