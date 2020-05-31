package data;

import java.util.ArrayList;

import beans.Bike;
import beans.Customer;

public interface BikeDAO extends GenericDAO<Bike> {
	
	public Integer addBike(Bike b);
	public Bike getBikeById(Integer id);
	public ArrayList<Bike> getBike();
	public ArrayList<Bike> getAvailableBikes();
	public void updateBike(Bike b);
	public void removeBike(Bike b);
	ArrayList<Bike> getAll(Customer C);
	public Bike getPendingId(Integer id);
	public void removePendingBike(Bike b);
	
	
}
