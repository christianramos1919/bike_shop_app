package data;

import beans.Bike;
import beans.Customer;

public interface CustomerDAO extends GenericDAO<Customer> {
	
	public Integer addCustomer(Customer c);
	public Customer getCustomerById(Integer id);
	public Customer getCustomerByUsernameAndPassword(String username, String password);
	public String updateCustomer(String b, String c);
	public void deleteCustomer(Customer c);
	public void update(String b, String c);
	
	
}
