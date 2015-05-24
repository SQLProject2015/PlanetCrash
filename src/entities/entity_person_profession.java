package entities;

public class entity_person_profession {
	private String person;
	private String profession;
	
	public entity_person_profession(String person,String profession){
		this.person=person;
		this.profession=profession;
	}
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getProfession() {
		return profession;
	}

	public void setPtofession(String profession) {
		this.profession = profession;
	}
}
