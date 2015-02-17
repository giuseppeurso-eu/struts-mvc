package com.demo.hibernate;

import org.hibernate.Session;

import com.demo.persistence.CustomerDAO;
import com.demo.persistence.CustomerHibernateDAO;

public class DAOHibernateFactory extends DAOFactory {
   	
    private GenericHibernateDAO instantiateDAO(Class daoClass) {
        try {
            GenericHibernateDAO dao = (GenericHibernateDAO)daoClass.newInstance();
            dao.setSession(getCurrentSession());
            return dao;
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

    // You could override this if you don't want SessionUtil for lookup
    protected Session getCurrentSession() {
        return SessionUtil.getSessionFactory().getCurrentSession();
    }

    // Add your DAO here
    public CustomerDAO getCustomerDAO() {
		return (CustomerDAO)instantiateDAO(CustomerHibernateDAO.class);
	}

}

