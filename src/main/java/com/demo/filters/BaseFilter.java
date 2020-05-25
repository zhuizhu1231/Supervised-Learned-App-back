package com.demo.filters;

import com.demo.util.StaticFinal;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "BaseFilter", urlPatterns = "/*")
public class BaseFilter implements Filter {
    String[] includeUrls = new String[]{"/login", "/register"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        if (!isFilter(uri)) chain.doFilter(request, response);
        else if (session != null && session.getAttribute("user") != null) {
            // System.out.println("user:"+session.getAttribute("user"));
            session.getAttribute("user");
            chain.doFilter(request, response);
        } else {

            response.setStatus(StaticFinal.CODE_NEED_PERMISSION);
        }
    }

    public boolean isFilter(String uri) {
        for (String includeUrl : includeUrls) {
            if (includeUrl.equals(uri)) {
                return false;
            }
        }
        return true;
    }
}
