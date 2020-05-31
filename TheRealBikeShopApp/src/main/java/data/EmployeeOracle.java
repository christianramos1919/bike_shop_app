package data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import beans.Bike;
import beans.Customer;
import beans.Employee;
import utils.ConnectionUtil;
import utils.LogUtil;
public class EmployeeOracle implements EmployeeDAO {
	ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	Logger log = Logger.getLogger(EmployeeOracle.class);
	
	@Override
	public Integer add(Employee t) {
		Integer key = 0;
		log.trace("Adding Employee " + t);
		Connection conn = cu.getConnection();
		try {
			String sql = "insert into employee (id, first_name, last_name, username, passwd)" +
					"values (?, ?, ?, ?, ?)";
			String[] keys = {"id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setInt(1, t.getEmployeeid());
			pstmt.setString(2, t.getFirstname());
			pstmt.setString(3, t.getLastname());
			pstmt.setString(4, t.getUsername());
			pstmt.setString(5, t.getPasswd());
			 
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				
				log.trace("Employee successfully added.");
				key = rs.getInt(1);
				conn.commit();
			} else {
				log.trace("Employee not added successfully.");
				conn.rollback();
			}
		} catch (SQLException e) {
			LogUtil.logException(e, EmployeeOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, EmployeeOracle.class);
			}
		}

		
		
		return key;
	}

	@Override
	public Employee getById(Integer id) {
		Employee e = null;
		log.trace("Getting employee with id" + id);
		try(Connection conn = cu.getConnection()) {
			String sql = "select * from employee";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				log.trace("Employee with id " + id + " found.");
				e = new Employee();
				e.setEmployeeid(id);
				e.setFirstname(rs.getString("first_name"));
				e.setLastname(rs.getString("last_name"));
				e.setUsername(rs.getString("username"));
				e.setPasswd(rs.getString("passwd"));
			} else { log.trace("No employee with that id found");}
		}	catch (Exception e1) { 
			LogUtil.logException(e1, EmployeeOracle.class);
			}
		return e;
	}

	@Override
	public ArrayList<Employee> getAll() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try(Connection conn = cu.getConnection()) {
			String sql = "select id, first_name, last_name, username from employee";
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next() ) {
				
				Employee e = new Employee();
				e.setEmployeeid(rs.getInt("id"));
				e.setFirstname(rs.getString("first_name"));
				e.setLastname(rs.getString("last_name"));
				e.setUsername(rs.getString("username"));
				employees.add(e);
			
			
			}
			
			} catch(Exception e) {
					LogUtil.logException(e, Employee.class);
			}
		return employees;
			
	}

	@Override
	public void update(Employee t) {
		log.trace("Updating cat with id " + t.getEmployeeid());
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "update bikeshop.employee set id = ?, "
					+ "first_name = ?, last_name = ?, username = ? where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, t.getEmployeeid());
			pstmt.setString(2, t.getFirstname());
			pstmt.setString(3, t.getLastname());
			pstmt.setString(4, t.getUsername());
			pstmt.setString(5, t.getPasswd());
			
			// an executeUpdate call can return the number
			// of rows affected by the statement
			int rowsAffected = pstmt.executeUpdate();
			// this should be 1 since we were updating based on id (unique)
			// so we can just check that it affected more than zero
			if (rowsAffected > 0) {
				
				log.trace("Employee successfully updated.");
				conn.commit();
			} else {
				log.trace("No Employee found with that id.");
				conn.rollback();
			}
		} catch (Exception e) {
			LogUtil.logException(e, EmployeeOracle.class);
		}
	}

	@Override
	public void delete(Employee t) {
		log.trace("Removing employee " + t);
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			
			
			
			String sql = "delete from employee where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, t.getEmployeeid());
			
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				log.trace("Removed Emplopyee successfully.");
				conn.commit();
			} else {
				log.trace("No Employee found with that id.");
				conn.rollback();
			}
		} catch (Exception e) {
			LogUtil.logException(e, EmployeeOracle.class);
		}
		
	}
	
	public Employee getUsernameAndPassword(Employee username, Employee password) {
		Employee e = null;
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "select * from employee "
					+ "where username like '" + username +"' and passwd like '" +password+"'";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(sql);
			
			
			while (rs.next() ) {
				//log.trace("Employer with username " + username + "and password" + password + " found");
				Employee e1 = new Employee();
				
				e1.setEmployeeid(rs.getInt("id"));
				e1.setUsername(rs.getString("username"));
				e1.setPasswd(rs.getString("passwd"));
				e1.setFirstname(rs.getString("first_name"));
				e1.setLastname(rs.getString("last_name"));
				//log.trace("Employee with username " + username + " and password " + password + " found");
				
			}
			
			} catch(Exception e2) {
					LogUtil.logException(e2, Employee.class);
			}
		
		return e;
		
	
		
	}

	@Override
	public Integer addEmployee(Employee e) {
		Integer key = 0;
		log.trace("Adding Emplopyee " + e);
		Connection conn = cu.getConnection();
		try {
			String sql = "insert into employee (id, first_name, last_name, username, passwd)" +
					"values (?, ?, ?, ?, ?)";
			String[] keys = {"id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setInt(1, e.getEmployeeid());
			pstmt.setString(2, e.getFirstname());
			pstmt.setString(3, e.getLastname());
			pstmt.setString(4, e.getUsername());
			pstmt.setString(5, e.getPasswd());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				
				log.trace("Employee successfully added.");
				key = rs.getInt(1);
				conn.commit();
			} else {
				log.trace("Employee not added successfully.");
				conn.rollback();
			}
		} catch (SQLException e1) {
			LogUtil.logException(e1, EmployeeOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e2) {
				LogUtil.logException(e2, EmployeeOracle.class);
			}
		}

		
		
		return key;
	}

	@Override
	public Employee getEmployeeById(Integer id) {
		Employee e = null;
		log.trace("Getting employee with id" + id);
		try(Connection conn = cu.getConnection()) {
			String sql = "select * from employee";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				log.trace("Employee with id " + id + " found.");
				e = new Employee();
				e.setEmployeeid(id);
				e.setFirstname(rs.getString("first_name"));
				e.setLastname(rs.getString("last_name"));
				e.setUsername(rs.getString("username"));
				e.setPasswd(rs.getString("passwd"));
			} else { log.trace("No employee with that id found");}
		}	catch (Exception e1) { 
			LogUtil.logException(e1, EmployeeOracle.class);
			}
		return e;
	}
	
	public Employee getEmployeeByUsernameAndPassword(String username, String password) {
		Employee e = null;
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "select * from employee "
					+ "where username=? and passwd =?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			
			while (rs.next() ) {
				//log.trace("Employer with username " + username + "and password" + password + " found");
				
				e = new Employee();
				e.setEmployeeid(rs.getInt("id"));
				e.setUsername(rs.getString("username"));
				e.setPasswd(rs.getString("passwd"));
				e.setFirstname(rs.getString("first_name"));
				e.setLastname(rs.getString("last_name"));
				//log.trace("Employee with username " + username + " and password " + password + " found");
				
			}
			
			} catch(Exception e2) {
					LogUtil.logException(e2, Employee.class);
			}
		
		return e;
	
	}

	@Override
	public void updateEmployee(Employee e) {
				log.trace("Updating Employee with id " + e.getEmployeeid());
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "update employee set id = ?, "
					+ "first_name = ?, last_name = ?, username = ? where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, e.getEmployeeid());
			pstmt.setString(2, e.getFirstname());
			pstmt.setString(3, e.getLastname());
			pstmt.setString(4, e.getUsername());
			pstmt.setString(5, e.getPasswd());
			
			// an executeUpdate call can return the number
			// of rows affected by the statement
			int rowsAffected = pstmt.executeUpdate();
			// this should be 1 since we were updating based on id (unique)
			// so we can just check that it affected more than zero
			if (rowsAffected > 0) {
				
				log.trace("Employee successfully updated.");
				conn.commit();
			} else {
				log.trace("No Employee found with that id.");
				conn.rollback();
			}
		} catch (Exception e1) {
			LogUtil.logException(e1, EmployeeOracle.class); 
		}
		
	}

	@Override
	public void deletePerson(Employee e) {
		log.trace("Removing employee " + e);
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			
			
			
			
			String sql = "delete from employee where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, e.getEmployeeid());
			
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				log.trace("Removed Emplopyee successfully.");
				conn.commit();
			} else {
				log.trace("No Employee found with that id.");
				conn.rollback();
			}
		} catch (Exception e1) {
			LogUtil.logException(e1, EmployeeOracle.class);
		}
		
	}

	@Override
	public ArrayList<Bike> getAll(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePendingBike(Bike t) {
		// TODO Auto-generated method stub
		
	}


}
