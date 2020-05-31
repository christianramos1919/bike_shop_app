package services;
import beans.Customer;
import beans.Employee;

import java.util.ArrayList;

import beans.Bike;
public interface BikeService {
	public Integer addBike(Bike b);
	public Bike getBikeById(Integer id);
	public ArrayList<Bike> getAvailableBike();
	public Bike updateBike(Bike b);
	public void buyBike(Customer c, Bike b);
	public void removeBike(Bike b);
	public void buyBike(Employee e, Bike b);
	public ArrayList<Bike> getAll();
	public void removePendingBike(Bike b);
	public Bike getPendingId(Integer id);
	
}
