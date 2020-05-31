package data;

import java.util.ArrayList;

import beans.Bike;
import beans.Customer;



public interface GenericDAO<T> {
	
	public Integer add(T t);
	public T getById(Integer id);
	public ArrayList<T> getAll();
	public void update(T t);
	public void delete(T t);
	ArrayList<Bike> getAll(T t);
//	void updateCustomer(T t, T t1);
	void removePendingBike(Bike t);
	
	
}
