package beans;

public class Bike {
private Integer bikeId;
private String bikeName;
private String color;
private Double price;
boolean sold;
private String firstname;
public Bike() {
	
	bikeId = 0;
	bikeName = "";
	color = "";
	firstname = "";
	price = 0.0;
	sold = false;
	
}

public Integer getBikeId() {
	return bikeId;
}

public void setBikeId(Integer bikeId) {
	this.bikeId = bikeId;
}

public String getBikeName() {
	return bikeName;
}

public String getFirstname() {
	return firstname;
}

public void setBikeName(String bikeName) {
	this.bikeName = bikeName;
}

public String getColor() {
	return color;
}

public boolean isSold() {
	return sold;
}

public void setSold(boolean sold) {
	this.sold = sold;
}

public void setColor(String color) {
	this.color = color;
}

public Double getPrice() {
	return price;
}

public void setPrice(Double price) {
	this.price = price;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((bikeId == null) ? 0 : bikeId.hashCode());
	result = prime * result + ((bikeName == null) ? 0 : bikeName.hashCode());
	result = prime * result + ((color == null) ? 0 : color.hashCode());
	result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
	result = prime * result + ((price == null) ? 0 : price.hashCode());
	result = prime * result + (sold ? 1231 : 1237);
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
	Bike other = (Bike) obj;
	if (bikeId == null) {
		if (other.bikeId != null)
			return false;
	} else if (!bikeId.equals(other.bikeId))
		return false;
	if (bikeName == null) {
		if (other.bikeName != null)
			return false;
	} else if (!bikeName.equals(other.bikeName))
		return false;
	if (color == null) {
		if (other.color != null)
			return false;
	} else if (!color.equals(other.color))
		return false;
	if (firstname == null) {
		if (other.firstname != null)
			return false;
	} else if (!firstname.equals(other.firstname))
		return false;
	if (price == null) {
		if (other.price != null)
			return false;
	} else if (!price.equals(other.price))
		return false;
	if (sold != other.sold)
		return false;
	return true;
}

@Override
public String toString() {
	return "Bike [bikeId=" + bikeId + ", bikeName=" + bikeName + ", color=" + color + ", price=" + price + ", sold="
			+ sold + ", firstname=" + firstname + "]";
}

public void setFirstname(String firstname) {
	this.firstname = firstname;
	
}





}
