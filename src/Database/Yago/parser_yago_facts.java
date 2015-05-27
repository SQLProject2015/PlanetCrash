package Database.Yago;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import entities.entity_country;
import entities.entity_country_city;

public class parser_yago_facts extends AbstractYagoParser{
	
	HashSet<String> cities_set;
	HashMap<String, entity_country> countries_map;
	HashMap<String, Set<String>> countries_cities_map;
	
	public parser_yago_facts(String filepath, HashMap<String, entity_country> countries_map, HashMap<String, Set<String>> countries_cities_map, HashSet<String> cities_set) {		
		super(filepath);
		
		this.countries_map = countries_map;
		this.countries_cities_map = countries_cities_map;
		this.cities_set = cities_set;
	}

	@Override
	public boolean parse(YagoEntry toParse) {
		boolean flag=false;
		
		entity_country country = countries_map.get(toParse.lentity);
		if (country != null){
			if (toParse.relation.equals(properties.get_yago_tag_located_in())){
				country.setContinent(toParse.rentity);
				flag=true;
			}
			else if (toParse.relation.equals(properties.get_yago_tag_has_currency())){
				country.setCurrency(toParse.rentity);
				flag=true;
			}
			else if (toParse.relation.equals(properties.get_yago_tag_official_language())){
				country.setLanguage(toParse.rentity);
				flag=true;
			}
			else if (toParse.relation.equals(properties.get_yago_tag_capital_city())){
				country.setCapital(toParse.rentity);
				flag=true;
			}
		}

		
		country = countries_map.get(toParse.rentity);
		if(country != null){
			if (toParse.relation.equals("<isLeaderOf>")){
				country.setLeader(toParse.lentity);
				flag=true;
			}
		}
		
		
		
		Set<String> list = countries_cities_map.get(toParse.rentity);
		if (list!=null){
			if (toParse.relation.equals("<isLocatedIn>") && cities_set.contains(toParse.lentity)){
				list.add(toParse.lentity);
				flag=true;
			}
		}
		
		return flag;
	}
	

}
