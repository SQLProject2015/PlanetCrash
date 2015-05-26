package Database.Yago;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.entity_country;
import entities.entity_currency;
import entities.entity_person;
		


public class parser_transitive_types extends AbstractYagoParser{
	
	private final int persons_limit = 100000;
	
	HashMap<String, entity_country> countries_map;
	HashMap<String, entity_person> persons_map;
	HashSet<String> cities_set = new HashSet<String>();
	HashSet<String> currency_set = new HashSet<String>();
	HashSet<String> language_set = new HashSet<String>();
	HashMap<String, Set<String>> countries_cities_map;
	

	public parser_transitive_types(String filepath,  HashMap<String, entity_country> countries_map,
			HashMap<String,entity_person> persons_map,HashSet<String> cities_set, HashSet<String> currency_set, HashSet<String> language_set, HashMap<String, Set<String>> countries_cities_map) {
		super(filepath);
		
		this.countries_map = countries_map;
		this.persons_map = persons_map;
		this.cities_set = cities_set;
		this.currency_set = currency_set;
		this.language_set = language_set;
		this.countries_cities_map = countries_cities_map;
	}

	@Override
	public void parse(YagoEntry toParse) {
		// TODO Auto-generated method stub
		if (toParse.rentity.equals("<wikicat_Countries>")){
			entity_country new_country = new entity_country();
			new_country.setYagoName(toParse.lentity);
			countries_map.put(toParse.lentity, new_country);
			countries_cities_map.put(toParse.lentity, new HashSet<String>());
		}
		else if (toParse.rentity.equals("<wordnet_musician_110339966>") || toParse.rentity.equals("<wordnet_scientist_110560637>") ||
				toParse.rentity.equals("<wordnet_politician_110450303>") || toParse.rentity.equals("<wordnet_actor_109765278>") ||
				toParse.rentity.equals("<wordnet_athlete_109820263>")){
			if (persons_map.size() != persons_limit){
				if (persons_map.containsKey(toParse.lentity)){
					persons_map.get(toParse.lentity).addProfession(toParse.rentity);
				}
				else{
					entity_person new_person = new entity_person();
					new_person.setName(toParse.lentity);
					new_person.addProfession(toParse.rentity);
					persons_map.put(toParse.lentity, new_person);					
				}
			}
			//System.out.println(toParse.lentity);
		}
		else if (toParse.rentity.equals("<wordnet_currency_113385913>")){
			if (!currency_set.contains(toParse.lentity)){
				currency_set.add(toParse.lentity);
			}
		}
		else if (toParse.rentity.equals("<wikicat_Languages>")){
			if (!language_set.contains(toParse.lentity)){
				language_set.add(toParse.lentity);
			}
		}
		else if (toParse.rentity.equals("<wordnet_city_108524735>")){
			if (!cities_set.contains(toParse.lentity)){
				cities_set.add(toParse.lentity);
			}
		}
	}
	

}
