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
	public void parse(YagoEntry toParse) {
		
		if (countries_map.containsKey(toParse.lentity)){
			if (toParse.relation.equals("<isLocatedIn>")){
				countries_map.get(toParse.lentity).setContinent(toParse.rentity);
			}
			else if (toParse.relation.equals("<hasCurrency>")){
				countries_map.get(toParse.lentity).setCurrency(toParse.rentity);
			}
			else if (toParse.relation.equals("<hasOfficialLanguage>")){
				countries_map.get(toParse.lentity).setLanguage(toParse.rentity);
			}
			else if (toParse.relation.equals("<hasOfficialLanguage>")){
				countries_map.get(toParse.lentity).setLanguage(toParse.rentity);
			}
			else if (toParse.relation.equals("<hasCapital>")){
				countries_map.get(toParse.lentity).setCapital(toParse.rentity);
			}
		}

		
		
		if(countries_map.containsKey(toParse.rentity)){
			if (toParse.relation.equals("<isLeaderOf>")){
				countries_map.get(toParse.rentity).setLeader(toParse.lentity);
			}
		}
		
		
		if (countries_cities_map.containsKey(toParse.rentity)){
			if (toParse.relation.equals("<isLocatedIn>") && cities_set.contains(toParse.lentity)){
				countries_cities_map.get(toParse.rentity).add(toParse.lentity);
			}
		}

	}
	

}
