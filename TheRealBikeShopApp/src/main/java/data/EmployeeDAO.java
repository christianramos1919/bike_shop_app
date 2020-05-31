package data;

import beans.Employee;

public interface EmployeeDAO extends GenericDAO<Employee>{
	public Integer addEmployee(Employee e);

	public Employee getEmployeeById(Integer id);
	public Employee getEmployeeByUsernameAndPassword(String username, String password);
	public void updateEmployee(Employee e);
	public void deletePerson(Employee e);
	
}
