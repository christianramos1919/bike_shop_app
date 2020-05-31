package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import beans.Bike;
import beans.Customer;
import beans.Employee;
import utils.ConnectionUtil;
import utils.LogUtil;
import oracle.jdbc.OracleTypes;


public class BikeOracle implements BikeDAO {
ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
Logger log = Logger.getLogger(BikeOracle.class);

public Integer add(Bike t) {
	Integer key = 0;
	log.trace("Adding bike " + t);
	
	Connection conn = cu.getConnection();
	try {
		conn.setAutoCommit(false);
		String sql = "insert into bike_table (bikeid, bike_name, color, price)" +
		"values (?, ?, ?, ?)";
		
		String keys[] = {"bikeid"};
		PreparedStatement pstmt = conn.prepareStatement(sql, keys);
		pstmt.setInt(1, t.getBikeId());
		pstmt.setString(2, t.getBikeName());
		pstmt.setString(3, t.getColor());
		pstmt.setDouble(4, t.getPrice());
		
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if(rs.next()) {
			log.trace("Bike successfully added.");
			key = rs.getInt(1);
			conn.commit();
		} else {
			log.trace("Bike not added successfully.");
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

public Bike getById(Integer id) {
	Bike b = null;
	log.trace("Getting a bike with id " + id);
	try(Connection conn = cu.getConnection()) {
		String sql = "select * FROM bike_table where bikeid=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			log.trace("Bike with id " + id + " found.");
			b = new Bike();
			b.setBikeId(rs.getInt("bikeid")); // or bikeid from database, we will see
			b.setBikeName(rs.getString("bike_name"));
			
			
		} else {
			log.trace("No bike found with that id");
		}
		
	} catch (Exception e) {
		LogUtil.logException(e, BikeOracle.class);
	}
	
	return b;
}

@Override
public ArrayList<Bike> getAll(Customer C) {
	ArrayList<Bike> bikes = new ArrayList<Bike>();
	 Customer c = new Customer();
	log.trace("Retrieveing all bikes owned by + " + c.getFirstname());
	try(Connection conn = cu.getConnection()) {
		String sql = "select * from bike_table_invoice where customername = ?";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, C.getCustomerId());
		
		  while(rs.next()) {
		  Bike b = new Bike(); 
		 
		  b.setBikeId(rs.getInt("bikeid"));
		  b.setBikeName(rs.getString("bike_name"));
		  //b.setColor(rs.getString("color"));
		  //b.setPrice(rs.getDouble("price")); 
		  b.setFirstname(rs.getString("customername"));
		  bikes.add(b);
		  }
		 
	} catch (Exception e) {
		LogUtil.logException(e, BikeOracle.class);
	}
	
	return bikes;
}

@Override
public void update(Bike t) {
	log.trace("Inserting sold bike " + t.getBikeId());
	try(Connection conn = cu.getConnection()) {
		conn.setAutoCommit(false);
		String sql = "insert into bike_table_sold (bikeid, bike_name) values (?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, t.getBikeId());
		pstmt.setString(2,  t.getBikeName());
		//pstmt.setString(3, "grey");
		//pstmt.setDouble(4,  t.getPrice());
		//pstmt.setString(5, "sold");
		int rowsAffected = pstmt.executeUpdate();
		
		if(rowsAffected > 0) {
			log.trace("Bike updated successfully");
			conn.commit();
		}
	} catch (Exception e) {
		LogUtil.logException(e, BikeOracle.class);
	}
}

@Override
public void delete(Bike t) {
	log.trace("Removing bike " + t);
	try (Connection conn = cu.getConnection()) {
		conn.setAutoCommit(false);
		
		
		 
		String sql = "delete from bike_table where bikeid = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, t.getBikeId());
		
		int rowsAffected = pstmt.executeUpdate();
		if (rowsAffected > 0) {
			log.trace("Removed Bike successfully.");
			conn.commit();
		} else {
			log.trace("No Bike found with that id.");
			conn.rollback();
		} 
	} catch (Exception e) {
		LogUtil.logException(e, BikeOracle.class);
	}
}

public void buyBike(Bike b, Customer c ) {
	log.trace("Customer with id" + c.getCustomerId() + " is buying " + b.getBikeName());
	try(Connection conn = cu.getConnection()) {
		String sql = "call buy_bike(?,?,?)";
		CallableStatement cstmt = conn.prepareCall(sql);
		cstmt.setInt(1, b.getBikeId());
		cstmt.setInt(2, c.getCustomerId());
		cstmt.registerOutParameter(3, OracleTypes.CURSOR);
		cstmt.executeUpdate();
		
		ResultSet rs = (ResultSet) cstmt.getObject(3);
		if(rs.next()) {
			log.trace("Bike" + rs.getInt("bikeid") + " sold to person " + rs.getInt("rs.getInt"));
			
		} else { log.trace("fail");}}
		
		catch (Exception e) {
			LogUtil.logException(e, BikeOracle.class);
		}
	
}


@Override
public Integer addBike(Bike b) {
	Integer key = 0;
	log.trace("Adding bike " + b);
	
	Connection conn = cu.getConnection();
	try {
		conn.setAutoCommit(false);
		String sql = "insert into bike_table (bikeid, bike_name, color, price)" +
		"values (?, ?, ?, ?)";
		
		String keys[] = {"bikeid"};
		PreparedStatement pstmt = conn.prepareStatement(sql, keys);
		b.setBikeId(b.getBikeId());
		pstmt.setString(2, b.getBikeName());
		pstmt.setString(3, b.getColor());
		pstmt.setDouble(4, b.getPrice());
		
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if(rs.next()) {
			key = rs.getInt(1);
			conn.commit();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return key;
}

@Override
public Bike getBikeById(Integer id) {
	Bike b = null;
	log.trace("Getting a bike with id " + id);
	try(Connection conn = cu.getConnection()) {
		String sql = "select bikeid, bike_name, color, price FROM bike_table where bikeid =?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) { 
			log.trace("Bike with id " + id + " found.");
			b = new Bike();
			b.setBikeId(id); // or bikeid from database, we will see
			b.setBikeName(rs.getString("bike_name"));
			b.setColor(rs.getString("color"));
			b.setPrice(rs.getDouble("price"));
			
		} else {
			log.trace("No bike found with that id");
		}
		
	} catch (Exception e) {
		LogUtil.logException(e, BikeOracle.class);
	}
	
	return b;
}

@Override
public ArrayList<Bike> getBike() {
 return null;
}

@Override
public ArrayList<Bike> getAvailableBikes() {
	ArrayList<Bike> bikes = new ArrayList<Bike>();
	log.trace("Retrieveing all bikes.");
	try(Connection conn = cu.getConnection()) {
		String sql = "select bikeid, bike_name, color, price from bike_table";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		
		
		  while(rs.next()) { Bike b = new Bike(); 
		  b.setBikeId(rs.getInt("bikeid"));
		  b.setBikeName(rs.getString("bike_name")); 
		  b.setColor(rs.getString("color"));
		  b.setPrice(rs.getDouble("price")); 
		  bikes.add(b);
		  }
		 
		
	} catch (Exception e) {
		LogUtil.logException(e, BikeOracle.class);
	}
	
	return bikes;
	
}

@Override
public void updateBike(Bike b) {
	//Integer key = 0;
	log.trace("Storing sold bike " + b.getBikeId());
	
	Connection conn = cu.getConnection();
	try {
		conn.setAutoCommit(false);
		String sql = "insert into bike_table_sold (bikeid, bike_name) values (?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, b.getBikeId());
		pstmt.setString(2,  b.getBikeName());
		//pstmt.setString(3, "grey");
		//pstmt.setDouble(4, b.getPrice());
		//pstmt.setString(5, "sold");
		int rowsAffected = pstmt.executeUpdate();
		
		
		if(rowsAffected > 0) {
			log.trace("Bike successfully stored");
			conn.commit();
		} else {
			log.trace("Unsuccessful");
		}
	} catch (Exception e) {
		LogUtil.logException(e, BikeOracle.class);
	}

	//return key;
	
}

@Override
public void removeBike(Bike b) {
	
try (Connection conn = cu.getConnection()) {
	log.trace("Bike is being deleted.");
	String sql = "delete from bike_table where bikeid=?";
	PreparedStatement pstmt = conn.prepareStatement(sql);
	pstmt.setInt(1, b.getBikeId());
	
	int rowsAffected  = pstmt.executeUpdate();
	if(rowsAffected > 0) {
		log.trace("Bike has been sold!");
	} else {
		log.trace("No bike can be sold cause that bike does not exist");
	}
	
} catch (Exception e) {
	LogUtil.logException(e, BikeOracle.class);
}

}

@Override
public ArrayList<Bike> getAll() {
	ArrayList<Bike> bikes = new ArrayList<Bike>();
	log.trace("Retrieving all pending bikes");
	try(Connection conn = cu.getConnection()) {
		String sql = "select * from bike_table_invoice";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
		//Customer c = new Customer();
		Bike b = new Bike();
		b.setBikeId(rs.getInt("bikeId"));
		b.setBikeName(rs.getString("bike_name"));
		b.setFirstname(rs.getString("customername"));
		bikes.add(b);	
		}} catch (Exception e) {
			LogUtil.logException(e, BikeOracle.class);
		}
		
		
	return bikes;
	}
	
	


@Override
public ArrayList<Bike> getAll(Bike t) {
	ArrayList<Bike> bikes = new ArrayList<Bike>();
	log.trace("Retrieving all pending bikes");
	try(Connection conn = cu.getConnection()) {
		String sql = "select * from bike_table_invoice";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
		//Customer c = new Customer();
		Bike b = new Bike();
		
		b.setBikeId(rs.getInt("bikeid"));
		b.setBikeName(rs.getString("bike_name"));
		b.setFirstname(rs.getString("customername"));
		bikes.add(b);	
		
		}} catch (Exception e) {
			LogUtil.logException(e, BikeOracle.class);
		}
		
		
	return bikes;
}

@Override
public void removePendingBike(Bike b)
{
	try (Connection conn = cu.getConnection())	 {						{
	log.trace("Removing a bike from offer table");
	String sql = "delete from bike_table_invoice where bikeid =?";
	PreparedStatement pstmt = conn.prepareStatement(sql);
	pstmt.setInt(1, b.getBikeId());
	int rowsAffected = pstmt.executeUpdate();
	if(rowsAffected > 0) {
		
		log.trace("Bike offer has been approved!");
	} else {
		log.trace("This offer does not exist");
	} 
	 
}} catch (Exception e) {
	LogUtil.logException(e, BikeOracle.class);
}

}

public Bike getPendingId(Integer id) {
	Bike b = null;
	log.trace("Gettin a bike with id " + id);
	try(Connection conn = cu.getConnection()) {
		String sql = "select * from bike_table_invoice where bikeid =?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			log.trace("Bike with id " + id + " found.");
			b = new Bike();
			b.setBikeId(rs.getInt("bikeid"));
			b.setBikeName(rs.getString("bike_name"));
		} else {
			log.trace("No bike with that id found");
		}} catch (Exception e) {
			LogUtil.logException(e, BikeOracle.class);
		}
		
	return b;
	
}


}
 