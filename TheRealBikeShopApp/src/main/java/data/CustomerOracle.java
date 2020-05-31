package data;

import java.security.Key;
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

public class CustomerOracle implements CustomerDAO {
	ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	Logger log = Logger.getLogger(CustomerOracle.class);
	
	public Integer add(Customer t) {
		
	Integer key = 0;
	log.trace("Adding Customer " + t);
	Connection conn = cu.getConnection();
	try {
		
		conn.setAutoCommit(false);
		String sql = "insert into customer (id, first_name, last_name, username, passwd)" +
		"values(?, ?, ?, ?, ?)";
		
		String[] keys = {"id"};
		PreparedStatement pstmt = conn.prepareStatement(sql, keys); 
		pstmt.setInt(1, t.getCustomerId());
		pstmt.setString(2, t.getFirstname());
		pstmt.setString(3, t.getLastname());
		pstmt.setString(4, t.getUsername());
		pstmt.setString(5, t.getPasswd());
		
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			
			log.trace("Customer successfully added.");
			key = rs.getInt(1);
			conn.commit();
		} else {
			log.trace("Customer not added successfully.");
			conn.rollback();
		}
	} catch (SQLException e) {
		LogUtil.logException(e, CustomerOracle.class);
	} finally {
		try {
			conn.close();
		} catch (SQLException e) {
			LogUtil.logException(e, CustomerOracle.class);
		}
	}
	return key;
	
}
 

	@Override
	public Customer getById(Integer id) {
		Customer c = null;
		log.trace("Getting a customer with id " + id);
		try (Connection conn = cu.getConnection()) {
			String sql = "select * from customer";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// the index of the SQL parameter (?) starts at 1
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				log.trace("Cat with id " + id + " found.");
				c = new Customer();
				c.setCustomerid(id);
				c.setFirstname(rs.getString("first_name"));
				c.setLastname(rs.getString("last_name"));
				
			} else {
				log.trace("No customer found with that id.");
			}
			
		} catch (Exception e) {
			LogUtil.logException(e, CustomerOracle.class);
		}
		return c;
	}

	@Override
	public ArrayList<Customer> getAll() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		log.trace("Getting all the customers");
		try(Connection conn = cu.getConnection()) {
			String sql = "select * from customer";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			Customer c = new Customer();
			c.getCustomerId();
			c.setFirstname(rs.getString("first_name"));
			c.setLastname(rs.getString("last_name"));
			
			
			
		}} catch (Exception e) {
			LogUtil.logException(e, CustomerOracle.class);
		}
		return customers;
	}

	@Override
	public void update(Customer t) {
		log.trace("Updating customer with id " + t.getCustomerId());
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "update customer set id = ?, first_name = ?, last_name = ?, username = ?, "
					+ " passwd = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, t.getCustomerId());
			pstmt.setString(2, t.getFirstname());
			pstmt.setString(3, t.getLastname());
			pstmt.setString(4, t.getUsername());
			pstmt.setString(5, t.getPasswd());
			
			int rowsAffected = pstmt.executeUpdate();
			
			if (rowsAffected > 0) {
				//updateSpecialNeeds(conn, t.getSpecialNeeds(), t.getId());
				log.trace("Customer successfully updated.");
				conn.commit();
			} else {
				log.trace("No Customer found with that id.");
				conn.rollback();
			}
			
			
		} catch (Exception e) {
			LogUtil.logException(e, CustomerOracle.class);
		}
		
	}
 
	@Override
	public void delete(Customer t) {
		log.trace("Removing Customer");
		try(Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "delete from customer where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, t.getCustomerId());
			int rowsAffected = pstmt.executeUpdate();
		if (rowsAffected > 0) {
				log.trace("Remove customer successfully. ");
				conn.commit();
		} else {
			log.trace("No customer found with that id.");
			conn.rollback();
		}
		}catch (Exception e) {
			LogUtil.logException(e, CustomerOracle.class);
			
		}
		
	}

	@Override
	public Integer addCustomer(Customer c) {
		 
		Integer key = 0;
		log.trace("Adding Customer " + c);
		Connection conn = cu.getConnection();
		try {
			
			conn.setAutoCommit(false);
			String sql = "insert into customer (id, first_name, last_name, username, passwd)" + //removed id
			"values(?, ?, ?, ?, ?)";
			
			String[] keys = {"id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setInt(1, c.getCustomerId());
			pstmt.setString(2, c.getFirstname());
			pstmt.setString(3, c.getLastname());
			pstmt.setString(4, c.getUsername());
			pstmt.setString(5, c.getPasswd());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				
				log.trace("Customer successfully added.");
				key = rs.getInt(1);
				conn.commit();
			} else {
				log.trace("Customer not added successfully.");
				conn.rollback();
			}
		} catch (SQLException e) {
			LogUtil.logException(e, CustomerOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, CustomerOracle.class);
			}
		}
		return key;
		
	}

	@Override
	public Customer getCustomerById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomerByUsernameAndPassword(String username, String password) {
		Customer c = null;
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "select * from customer "
					+ "where username=? and passwd=?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			
			ResultSet rs = pstmt.executeQuery();
		
			
		
			
		
			while (rs.next() ) {
			
			   c = new Customer();
				
				c.setCustomerId(rs.getInt("id"));
				c.setUsername(rs.getString("username"));
				c.setPasswd(rs.getString("passwd")); 
				c.setFirstname(rs.getString("first_name"));
				c.setLastname(rs.getString("last_name"));
			
			}
			
			} catch(Exception e) {
					LogUtil.logException(e, Customer.class);
			}
		
		return c;
	}
	
	@Override
	public String updateCustomer(String b, String c) {
		
		log.trace("Updating customer with id " + c);
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "insert into bike_table_invoice (bikename, customername)"
					+ " values(?, ?)";
			
			//String[] keys = {"id"};
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, b.getBikeId());
			pstmt.setString(1, b);
			//pstmt.setString(3, b.getColor());
			///pstmt.setDouble(4, b.getPrice());
			pstmt.setString(2, c);
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
		if(rs.next()) {
			log.trace("Updated pending request");
			
			conn.commit();
			
		}	else {
				
					log.trace("Pending request failed");
					conn.rollback();
		}
		
			
		} catch (Exception e) {
			LogUtil.logException(e, CustomerOracle.class);
		}
		return c; 
	}

	@Override
	public void deleteCustomer(Customer c) {
		log.trace("Removing Customer");
		try(Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "delete from customer where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, c.getCustomerId());
			int rowsAffected = pstmt.executeUpdate();
		if (rowsAffected > 0) {
				log.trace("Remove customer successfully. ");
				conn.commit();
		} else {
			log.trace("No customer found with that id.");
			conn.rollback();
		}
		}catch (Exception e) {
			LogUtil.logException(e, CustomerOracle.class);
			
		} 
	}


	@Override
	public ArrayList<Bike> getAll(Customer t) {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		log.trace("Getting all the customers");
		try(Connection conn = cu.getConnection()) {
			String sql = "select * from customer";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			Customer c = new Customer();
			c.getCustomerId();
			c.setFirstname(rs.getString("first_name"));
			c.setLastname(rs.getString("last_name"));
			
			
			
		}} catch (Exception e) {
			LogUtil.logException(e, CustomerOracle.class);
		}
		return null;
	}

 
	@Override
	public void update(String b, String c) {
		log.trace("Updating customer with name " + c);
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "insert into bike_table_invoice (bike_name, customername)"
					+ " values(?, ?)";
			
			//String[] keys = {"id"};
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, b.getBikeId());
			pstmt.setString(1, b);
			//pstmt.setString(3, b.getColor());
			//pstmt.setDouble(4, b.getPrice());
			pstmt.setString(2, c);
			
			pstmt.executeUpdate();
			//ResultSet rs = pstmt.getGeneratedKeys();
			int rowAffected = pstmt.executeUpdate();
			/*
			 * if(rs.next()) { log.trace("Updated pending request");
			 * 
			 * conn.commit();
			 * 
			 * } else {
			 * 
			 * log.trace("Pending request failed"); conn.rollback(); }
			 */
			if(rowAffected > 0) {
				log.trace("Updated pending request");
			} else {
				log.trace("Unsuccesful");
			}
			
		} catch (Exception e) {
			LogUtil.logException(e, CustomerOracle.class);
		} 
}


	@Override
	public void removePendingBike(Bike t) {
		// TODO Auto-generated method stub
		
	}
	
	

}