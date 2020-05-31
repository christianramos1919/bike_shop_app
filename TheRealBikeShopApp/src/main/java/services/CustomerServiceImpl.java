package services;
import beans.Bike;
import beans.Customer;
import data.CustomerDAO;
import data.CustomerOracle;
public class CustomerServiceImpl implements CustomerService {
	private static CustomerDAO customerDao;
	
	public CustomerServiceImpl() {
		customerDao = new CustomerOracle();
		
	}

	@Override
	public Integer addCustomr(Customer c) {
		
		return customerDao.add(c);
	}

	@Override
	public Customer getCustomerById(Integer id) {
		return customerDao.getById(id);
	}

	@Override
	public Customer getCustomerByUsernameAndPassword(String username, String password) {
		return customerDao.getCustomerByUsernameAndPassword(username, password);
		
	}

	@Override
	public String updateCustomer(String b, String c) {
		customerDao.update(b, c);
		return c;
		
	}

	@Override
	public void deleteCustomer(Customer c) {
		customerDao.delete(c);
		
	}

	@Override
	public void updateCustomer(Customer c) {
		// TODO Auto-generated method stub
		
	}
	
}
