package entities;

public class entity_country_city {
	private String country;
	private String city;
	
	public entity_country_city(String country,String city){
		this.country =country;
		this.city = city;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
