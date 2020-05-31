package services;

import java.util.ArrayList;

import beans.Bike;
import beans.Customer;
import beans.Employee;
import data.BikeDAO;
import data.BikeOracle;

import data.CustomerDAO;
import data.CustomerOracle;
import data.EmployeeDAO;
import data.EmployeeOracle;

public  class BikeServiceImpl implements BikeService {
	private static BikeDAO bikeDao;
	private static CustomerDAO customerDao;
	private static EmployeeDAO employeeDao;
	
	public BikeServiceImpl() {
		bikeDao = new BikeOracle();
		customerDao = new CustomerOracle();
		employeeDao = new EmployeeOracle();
	}

	@Override
	public Integer addBike(Bike b) {
		
		return bikeDao.add(b); 
	}

	@Override
	public Bike getBikeById(Integer id) {
		return bikeDao.getById(id);
	}

	//@Override
	public ArrayList<Bike> getAvailableBike() {
		
		return bikeDao.getAvailableBikes();
	}

	@Override
	public Bike updateBike(Bike b) {
		bikeDao.update(b);
		return b;
	}

	@Override
	public void buyBike(Customer c, Bike b) {
		b.setSold(true);
		bikeDao.update(b);
		ArrayList<Bike> bike = c.getBikes(); //look into this
		bike.add(b);
		customerDao.update(c);
	}
	@Override
	public void buyBike(Employee e, Bike b) {
		b.setSold(true);
		bikeDao.update(b);
		ArrayList<Bike> bike = e.getBikes(); //look into this
		bike.add(b);
		employeeDao.update(e);
	}
	@Override
	public void removeBike(Bike b) {
		bikeDao.delete(b);
		
	}

	@Override
	public ArrayList<Bike> getAll() {
		// TODO Auto-generated method stub
		return bikeDao.getAll();
	}

	@Override
	public void removePendingBike(Bike b) {
		bikeDao.removePendingBike(b);
	}

	@Override
	public Bike getPendingId(Integer id) {
		// TODO Auto-generated method stub
		return bikeDao.getPendingId(id);
	}
 
}
