package com.demo.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.demo.domain.Customer;
import com.demo.form.CustomerForm;
import com.demo.hibernate.DAOFactory;
import com.demo.persistence.CustomerDAO;



public class CustomerAction extends DispatchAction {

	private Log log = LogFactory.getLog(this.getClass());
	private DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
	
	/**
	 * 
	 * Retrieve a list of Customer
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward multiRetrieve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {

			DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO CustomerDao = factory.getCustomerDAO();
			List<?> customers = CustomerDao.findAll();
			request.setAttribute("customerList", customers);

		} catch (Exception e) {
			log.error("customers not found", e);
		}
		return mapping.findForward("showMulti");
	}
	
	/**
	 * Retrieve a single Customer
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward singleRetrieve (	ActionMapping mapping,	ActionForm form, HttpServletRequest request, HttpServletResponse response){
		try {
			CustomerDAO cdao = factory.getCustomerDAO();
			Customer customer = new Customer();
			if (request.getParameter("id") != null)  {
				customer = cdao.findById(new Long(request.getParameter("id")),true);
			}
			
			BeanUtils.copyProperties(form, customer);
			request.setAttribute("customer", customer);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return mapping.findForward("showSingle");
	}

	/**
	 * 
	 * Create or update a Customer
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createOrUpdate (ActionMapping mapping,	ActionForm form,HttpServletRequest request,	HttpServletResponse response) {
		try {
			CustomerDAO cdao = factory.getCustomerDAO();
			Customer customer = new Customer();
			CustomerForm customerForm = (CustomerForm) form;
			BeanUtils.copyProperties(customer, customerForm);
			cdao.makePersistent(customer);
			request.setAttribute("id", customer.getId());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return mapping.findForward("successCreateOrUpdate");
		
	}
	
	/**
	 * 
	 * Delete a Customer
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delete (
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response){
		try {
			CustomerDAO cdao = factory.getCustomerDAO();
			Customer customer = cdao.findById(new Long(request.getParameter("id")),true);
			cdao.makeTransient(customer);
       		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return mapping.findForward("successDelete");
}
	

}


	

