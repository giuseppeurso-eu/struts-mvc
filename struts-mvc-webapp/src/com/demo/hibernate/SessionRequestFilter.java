package com.demo.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;

public class SessionRequestFilter implements Filter {

    private SessionFactory sf;

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        try {

            sf.getCurrentSession().beginTransaction();
            chain.doFilter(request, response);
            sf.getCurrentSession().getTransaction().commit();

        } catch (StaleObjectStateException staleEx) {
            throw staleEx;
        } catch (Throwable ex) {
            // Rollback only
            ex.printStackTrace();
            try {
                if (sf.getCurrentSession().getTransaction().isActive()) {
                    sf.getCurrentSession().getTransaction().rollback();
                }
            } catch (Throwable rbEx) {
            		System.out.print(rbEx.getStackTrace());
            }
            throw new ServletException(ex);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        sf = SessionUtil.getSessionFactory();
    }

    public void destroy() {}

}
