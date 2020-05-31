package services;
import beans.Employee;
import data.EmployeeDAO;
import data.EmployeeOracle;
import data.BikeOracle;
public class EmployeeServiceImpl implements EmployeeService{
	private static EmployeeDAO employeeDao;
	
	public EmployeeServiceImpl() {
		employeeDao = new EmployeeOracle(); 
	}
	
	
	@Override
	public Integer addEmployee(Employee e) {
		return employeeDao.add(e);
		
	}

	@Override
	public Employee getEmployeeById(Integer id) {
		return employeeDao.getById(id);
	}

	@Override
	public Employee getEmployeeByUsernameAndPassword(String username, String password) {
		return employeeDao.getEmployeeByUsernameAndPassword(username, password);
	}

	@Override
	public void updateEmployee(Employee e) {
		employeeDao.update(e);
		
	}

	@Override
	public void deleteEmployee(Employee e) {
		employeeDao.delete(e);
		
	}

}
