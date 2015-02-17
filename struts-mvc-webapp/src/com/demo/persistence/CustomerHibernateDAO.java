package com.demo.persistence;

import org.hibernate.criterion.Restrictions;

import com.demo.domain.Customer;
import com.demo.hibernate.GenericHibernateDAO;

public class CustomerHibernateDAO  extends  GenericHibernateDAO<Customer, Long> implements  CustomerDAO {
	
	/**
	 * An Example of a customized query to retrieve a customer by name
	 * @param name
	 * @return
	 */
	public Customer findByName(String name){
		 return  (Customer)getSession().createCriteria(Customer.class).add( Restrictions.eq("name",name)).uniqueResult(); 
	 }
	
}

