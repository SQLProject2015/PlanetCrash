package entities;

public class entity_country {
	/*private members*/
	private String yago_name;
	private String label; 
	private String continent; 
	private String currency;
	private String language;
	private String capital; 
	private int population_size; 
	private String leader;
	
	public entity_country(){
		
	}
	
	public entity_country(String yago_name,String label,String continent,String currency,String language,String capital,int population_size,String leader){
		yago_name = yago_name;
		label = label;
		continent = continent;
		currency = currency;
		language = language;
		capital = capital;
		population_size = population_size;
		leader = leader;
	}

	public String getName() {
		return yago_name;
	}
	public void setName(String name) {
		this.yago_name = name;
	}
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public int getPopulation_size() {
		return population_size;
	}
	public void setPopulation_size(int population_size) {
		this.population_size = population_size;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

}
