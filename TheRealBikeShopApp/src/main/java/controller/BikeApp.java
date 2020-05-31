package controller;
import oracle.jdbc.OracleTypes;

import data.GenericDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.log4j.Logger;
import beans.Bike;
import beans.Customer;
import beans.Employee;
import services.BikeServiceImpl;
import services.CustomerService;
import services.BikeService;
import services.CustomerServiceImpl;
import services.EmployeeService;
import services.EmployeeServiceImpl;
import utils.ConnectionUtil;
import utils.LogUtil;
import data.BikeOracle;
import data.BikeDAO;

public class BikeApp {
	private static BikeService bikeServ;
	private static CustomerService custServ;
	private static EmployeeService employServ;
	public static Scanner scan = new Scanner(System.in);
	public static Logger log = Logger.getLogger(BikeApp.class);
	
	public static void main(String[] args)
	{
		bikeServ = new BikeServiceImpl();
		custServ = new CustomerServiceImpl();
		employServ = new EmployeeServiceImpl();
		Customer loggedInCustomer = new Customer();
		Employee loggedInEmployee = new Employee();
		boolean userCustomer = false;
		boolean userEmployee = false;
		try { boolean apploop = true;
		apploop : while(apploop) {
			System.out.println("Would you like to:\n1. Register As Customer"
					+ "\n2. Log In As Customer \n3. log In As Employee "
					+ "\n4 Exit.");
			String input = scan.nextLine();
			
			switch(input) {
			
			case "1": loggedInCustomer = register();
			break;
			case "2": loggedInCustomer = LogInCustomer();
			 userCustomer = true;
			break;
			case "3": loggedInEmployee = LogInEmployee();
		    	userEmployee = true;
			break;
			case "4": 
				log.warn("User is exiting");
				System.exit(0);
			default:
				break;
			
			
		}
		/*
		 * if(userEmployee == true && loggedInEmployee.getFirstname() == "christian") {
		 * boolean managerLog = false; managerLog : while(!managerLog) {
		 * System.out.println("Welcome manager:"); System.out.
		 * println("What would you like to do: :\n1. Ban customer \n2. Add employee \n3. fire employee \n4. Add bike .\n5 log out"
		 * ); String input3 = scan.nextLine(); switch(input3) {
		 * 
		 * } } }
		 */
				if ( userEmployee == true && userCustomer == false) 
					{
						boolean notDone = false;
						notDone : while (!notDone) {
							System.out.println("Would you like to :\n1. Add bike \n2. Remove bike \n3. buy Cat \n4. View Pending Bicycles \n5. Log out");
							input  = scan.nextLine();
							switch(input) 
							{
								case "1":
									// TODO change this for new cat bean
									Bike b = new Bike(); 
									//Breed b = new Breed();
									System.out.println("Please enter Bike's name");
									b.setBikeName(scan.nextLine());
									System.out.println("Please enter your Bike's color");
									b.setColor(scan.nextLine());
									System.out.println("Please enter your Price price");
									b.setPrice(Double.valueOf(scan.nextLine()));
									
									bikeServ.addBike(b);
									break;
								case "2":
									for(Bike bike : bikeServ.getAvailableBike()) 
									{
										System.out.println(bike);
									}
									System.out.println("Which Bike would you like to remove?");
									Bike b2 = bikeServ.getBikeById(Integer.valueOf(scan.nextLine()));
									// will have the same result:
									//Bike b3 = new Bike();
									//b2.setBikeId(scan.nextInt());
									bikeServ.removeBike(b2);
									break;
								case "3":
									employeeBuy(loggedInEmployee);
									break;
								
								case "4":
									for(Bike bike : bikeServ.getAll())
									{
										System.out.println(bike);
									}
									//System.out.println("Which Bike would you like to remove?");
									System.out.println("Would you like to reject or accept some of these offers. y for accept, n for reject");
									String input4 = scan.nextLine();
									switch(input4) {
									case "y": 
										System.out.println("Which bike would you like to accept.");
										Bike b4 = bikeServ.getPendingId(Integer.valueOf(scan.nextInt()));
										if (b4 == null) {
											log.error("null pointer");
										}
										
										log.trace("User chose " + b4.getBikeName()); //where it dies
										//bikeServ.removePendingBike(b4); 
										
										Bike b3 = bikeServ.updateBike(b4);
										bikeServ.removePendingBike(b3);
										if(b3 == null) {
												System.out.println("User could not successfuly add bike to sold");
											} else {
												
												System.out.println("Bike is now officially sold");
												
											}
										
									break;
									
									
									case "n": 
										System.out.println("Which offer would you like to reject");
										Bike b5 = bikeServ.getPendingId(Integer.valueOf(scan.nextInt()));
										bikeServ.removePendingBike(b5);
										if(b5 == null) {
											System.out.println("Bike offer has been rejected");
											
										}
									
									
									
									default:
										break;
										
							
									}
									
									
									break;
								case "5": 
									
									break notDone;
								default:
									break;
							}
						} 
					} 
					else 
					{
						String input2 = ""; 
						boolean buy = false;
						buy: while (!buy) {
							System.out.println("Would you like to :\n1. Buy Bike \n2. View Bike \n3. Log out");
							input2 = scan.nextLine();
							switch(input2)
							{
							case "1": 
								customerBuy(loggedInCustomer);			
								break;
							case "2":
								for(Bike bike : bikeServ.getAvailableBike()) 
								{
									System.out.println(bike);
								}
								System.out.println("Which Bike would you like to remove?");
								Bike b2 = bikeServ.getBikeById(Integer.valueOf(scan.nextLine()));
								// will have the same result:
								//Bike b3 = new Bike();
								//b2.setBikeId(scan.nextInt());
								bikeServ.removeBike(b2);
								break;
							case "3":
								break buy;
							default:
								System.out.println("Invalid option");
								break;
							}
						}		
					}
				}
			} catch (Exception e) {
				log.warn(e + "\n" + Arrays.toString(e.getStackTrace()));
			}
			
			scan.close();

		}
		
		/*
		 * if( userCustomer == true && userEmployee == false) { boolean notDone = false;
		 * while (!notDone) { System.out.
		 * println("Would you like to :\n1. Add Bike \n2. Remove Bike \n3. Buy Bike \n4. Log out"
		 * ); int input3 = Integer.valueOf(scan.nextLine()); switch(input3) { case 1:
		 * Bike b = new Bike(); //Breed b = new Breed();
		 * System.out.println("Please enter Bike's name");
		 * b.setBikeName(scan.nextLine());
		 * System.out.println("Please enter your Bike's color");
		 * b.setColor(scan.nextLine());
		 * System.out.println("Please enter your Price price");
		 * b.setPrice(Double.valueOf(scan.nextLine()));
		 * 
		 * bikeServ.addBike(b); break; case 2: for(Bike cat :
		 * bikeServ.getAvailableBike()) { System.out.println(cat); }
		 * System.out.println("Which Bike would you like to remove?"); Bike b2 =
		 * bikeServ.getBikeById(Integer.valueOf(scan.nextLine())); // will have the same
		 * result: //Bike b21 = new Bike(); //b21.setBikeId(scan.nextInt());
		 * bikeServ.removeBike(b2); break; case 3: customerBuy(loggedInCustomer); break;
		 * case 4: System.exit(0); continue; default: break; } } } else { int input2 =
		 * 0;
		 * 
		 * buy: while (input2 != 1 || input2 != 2) { System.out.
		 * println("Would you like to buy a bike (1) add a to the shop bike (2) or Log out (3)"
		 * ); input2 = Integer.valueOf(scan.nextLine()); switch(input2) { case 1:
		 * customerBuy(loggedInCustomer); break; case 2: Bike b = new Bike(); //Breed b
		 * = new Breed(); System.out.println("Please enter Bike's name");
		 * b.setBikeName(scan.nextLine());
		 * System.out.println("Please enter your Bike's color");
		 * b.setColor(scan.nextLine());
		 * System.out.println("Please enter your Price price");
		 * b.setPrice(Double.valueOf(scan.nextLine()));
		 * 
		 * bikeServ.addBike(b); break;
		 * 
		 * case 3:
		 * 
		 * break buy; default: System.out.println("Invalid option"); break; } } }
		 */

	
	public static Customer register() {
		Customer c = new Customer();
		log.info("User is registering an account.");
		
		System.out.println("Enter you first name");
		c.setFirstname(scan.nextLine());
		System.out.println("Enter your last name");
		c.setLastname(scan.nextLine());
		System.out.println("Enter your username");
		c.setUsername(scan.nextLine());
		System.out.println("Please enter you password");
		c.setPasswd(scan.nextLine());
		c.setCustomerid(custServ.addCustomr(c));
		return c;
	}
	
	public static Employee LogInEmployee() {
		//Employee e = null;
		log.info("Employee is logging in.");
		//while(e == null);
		//{
		Employee e = null;	
		System.out.println("Please enter Username");
			String username = scan.nextLine();
			System.out.println("Please enter Password");
			String password = scan.nextLine();
			 
			e = employServ.getEmployeeByUsernameAndPassword(username, password);
		
			if(e == null)
			{
				System.out.println("User not found, please try again");
				System.exit(0);
			}
		//}
		
		return e;
	}
	
	public static Customer LogInCustomer() {
		//Customer c = null;
		log.info("Customer is logging in.");
		//while(c == null);
		//{
		Customer c = null;	
		System.out.println("Please enter Username");
			String username = scan.nextLine();
			System.out.println("Please enter password");
			String password = scan.nextLine();
			
			c = custServ.getCustomerByUsernameAndPassword(username, password);
		
			if(c == null)
			{
				System.out.println("User not found, please try again");
				System.exit(0);
			}
		
		return c;
}
 
	
	public static void employeeBuy(Employee loggedInEmployee) {
		
		log.info(loggedInEmployee.getFirstname() + " is buying a bike.");
		//System.out.println("How much money you have: ");
		
		for (Bike bike : bikeServ.getAvailableBike()) {
			System.out.println(bike);
		}
		System.out.println("Which bike would you like to buy?");
		Bike b3 = bikeServ.getBikeById(Integer.valueOf(scan.nextInt()));
		if (b3 == null) {
			log.error("null pointer");
		}
		log.trace("User chose " + b3.getBikeName());
		
		
		
		Bike b4 = bikeServ.updateBike(b3);
		bikeServ.removeBike(b3);
	  
	    if(b4 == null) {
			System.out.println("User could not successfuly add bike to sold");
		} else {
			
			System.out.println("Bike is now officially sold");
			
		}
		
		
	} 

		public static void customerBuy(Customer loggedInCustomer) {
			String c1 = null;
			log.info(loggedInCustomer.getFirstname() + " is buying a bike.");
			for (Bike bike : bikeServ.getAvailableBike()) {
				System.out.println(bike);
			}
			System.out.println("Which Bike would you like to Buy?");
			Bike b3 = bikeServ.getBikeById(Integer.valueOf(scan.nextInt()));
			if (b3 == null) {
				log.error("null pointer");
			}
			log.trace("User chose " + b3.getBikeName());
			Bike b4 = bikeServ.updateBike(b3);
			String c = loggedInCustomer.getFirstname();
			String b = b4.getBikeName();
			c1 = custServ.updateCustomer(b, c);
			bikeServ.removeBike(b3);
		   // money = 0;
			//b4.isSold();
		    if(b4 == null) {
				System.out.println("User could not successfuly add bike to sold");
			} else {
				
				System.out.println("Bike is now pending");
				
			}
		} 
		 
		 
		



}



 