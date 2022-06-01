package com.filter;

import com.utils.JDBCUtil;

import javax.servlet.*;
import java.io.IOException;

public class CloseConnectionFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.releaseConnection();
        }
    }
}
