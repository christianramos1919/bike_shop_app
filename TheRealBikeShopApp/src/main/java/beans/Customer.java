package beans;

import java.util.ArrayList;

public class Customer {
	Integer customerId;
	String firstname;
	String lastname;
	String username;
	String passwd;
	private ArrayList<Bike> bikes;
	boolean isEmployee;
	boolean isCustomer;
	
	public Customer() {
		
		customerId = 0;
		firstname = "";
		lastname = "";
		username = "";
		passwd = "";
		bikes = new ArrayList<Bike>();
		isEmployee = false;
		isCustomer = true;
	}
 
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setBikes(ArrayList<Bike> bikes) {
		this.bikes = bikes;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerid(Integer customerid) {
		this.customerId = customerid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public boolean isCustomer() {
		return isCustomer;
	}

	public void setCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + (isCustomer ? 1231 : 1237);
		result = prime * result + (isEmployee ? 1231 : 1237);
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((passwd == null) ? 0 : passwd.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (isCustomer != other.isCustomer)
			return false;
		if (isEmployee != other.isEmployee)
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (passwd == null) {
			if (other.passwd != null)
				return false;
		} else if (!passwd.equals(other.passwd))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [customerid=" + customerId + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", username=" + username + ", passwd=" + passwd + ", isEmployee=" + isEmployee + ", isCustomer="
				+ isCustomer + "]";
	}

	public ArrayList<Bike> getBikes() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}
