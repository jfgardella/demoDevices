package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HeaderInterceptor implements HandlerInterceptor {

    @Autowired
    private Logger logger;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {

        Long startTime = Long.valueOf(System.currentTimeMillis());
  
        logger.info("PREHANDLE started on {} - {} {}", startTime, req.getMethod(), req.getRequestURL());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) {

        Long endTime = Long.valueOf(System.currentTimeMillis());
        logger.info("POSTHANDLE ended on {} - {} {}", endTime, req.getMethod(), req.getRequestURL());

    }
}
