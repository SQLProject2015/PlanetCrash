package entities;

public class entity_person {
	private String name;
	private int yearOfBirth;
	private int yearOfDeath;
	private String placeOfBirth;
	
	
	public entity_person(String name,int yearOfBirth,int yearOfDeath,String placeOfBirth){
		this.name = name;
		this.yearOfBirth = yearOfBirth;
		this.yearOfDeath = yearOfDeath;
		this.placeOfBirth = placeOfBirth;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public int getYearOfDeath() {
		return yearOfDeath;
	}

	public void setYearOfDeath(int yearOfDeath) {
		this.yearOfDeath = yearOfDeath;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
}
