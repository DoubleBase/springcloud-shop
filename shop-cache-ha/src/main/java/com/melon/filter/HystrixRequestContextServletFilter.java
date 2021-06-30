package com.melon.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author muskmelon
 * @since 1.0
 */
public class HystrixRequestContextServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Hystrix请求上下文
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            context.shutdown();
        }
    }

    @Override
    public void destroy() {

    }
}
