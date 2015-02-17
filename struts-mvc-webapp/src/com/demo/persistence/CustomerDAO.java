package com.demo.persistence;

import com.demo.domain.Customer;
import com.demo.hibernate.GenericDAO;

public interface CustomerDAO extends GenericDAO<Customer, Long> {
}
