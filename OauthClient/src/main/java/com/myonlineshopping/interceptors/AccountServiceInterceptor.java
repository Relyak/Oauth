package com.myonlineshopping.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
* -> implementar interceptor
	-> sólo para cuentas
  	-> apunte: métodoHTTP::ruta::queryparams::ip_origen
    -> añadir cabecera en response: “accounts-request-reviewed=true"
* */

@Component
@Profile("prod")
public class AccountServiceInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(request.getMethod()+"::"+request.getRequestURI()+"::"+request.getQueryString()+"::"+request.getRemoteAddr());
        response.addHeader("accounts-request-reviewed","true");
        if(request.getRequestURI().contains("/account")){
           return HandlerInterceptor.super.preHandle(request, response, handler);
        }else return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
