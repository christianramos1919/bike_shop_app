package services;

import beans.Employee;

public interface EmployeeService  {
	// create
	public Integer addEmployee(Employee e);
	// read
	public Employee getEmployeeById(Integer id);
	public Employee getEmployeeByUsernameAndPassword(String username, String password);
	// update
	public void updateEmployee(Employee e);
	// delete
	public void deleteEmployee(Employee e);
	
}
