//package com.selffun.joys4fellow.filter;
//
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Component
//@WebFilter(urlPatterns = "/*", filterName = "authFilter")
//public class AuthFilter implements Filter {
//
//    @Override
//    public void init (FilterConfig filterConfig) throws ServletException{}
//
//    @Override
//    public void destroy() {}
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest)request;
//        String path=httpRequest.getRequestURI();
//        if(path.indexOf("/joys")!=-1){
//            filterChain.doFilter(request, response);
//        }else{
//            path ="/joys"+path;
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
//            requestDispatcher.forward(request, response);
//            filterChain.doFilter(request, response);
//        }
//    }
//}
