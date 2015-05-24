package entities;

public class entity_award_winner {
	private String award;
	private String person;
	
	public entity_award_winner(String award,String person){
		this.setAward(award);
		this.setPerson(person);
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}
}
