package com.myonlineshopping.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Profile("prod")
/*
* Añade un filtro en perfil prod que solo deje pasar IPs registradas en una lista determinada y si no devuelva un estado de respuesta
403.
* */
public class Filtro implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(Filter.class);
    private static final List<String> ip = List.of(
            "127.0.0.1",
            "0:0:0:0:0:0:0:1");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Inicializando::::");

        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        for (String ipVal : ip) {
            if ((ipVal.equals(servletRequest.getRemoteAddr()))) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.setStatus(403);

    }

    @Override
    public void destroy() {
        logger.info("Destrozando::::");
    }
}
