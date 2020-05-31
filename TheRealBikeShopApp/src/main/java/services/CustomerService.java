package services;

import beans.Bike;
import beans.Customer;

public interface CustomerService {
	public Integer addCustomr(Customer c);
	public Customer getCustomerById(Integer id);
	public Customer getCustomerByUsernameAndPassword(String username, String password);
	public void updateCustomer(Customer c);
	public void deleteCustomer(Customer c);
	public String updateCustomer(String b, String c);
	
}
