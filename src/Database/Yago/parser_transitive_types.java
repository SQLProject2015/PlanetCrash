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
		if (toParse.rentity.equals(properties.get_yago_tag_country())){
			entity_country new_country = new entity_country();
			new_country.setYagoName(toParse.lentity);
			countries_map.put(toParse.lentity, new_country);
			countries_cities_map.put(toParse.lentity, new HashSet<String>());
		}
		else if (toParse.rentity.equals(properties.get_yago_tag_musician()) || toParse.rentity.equals(properties.get_yago_tag_scientist()) ||
				toParse.rentity.equals(properties.get_yago_tag_politician()) || toParse.rentity.equals(properties.get_yago_tag_actor()) ||
				toParse.rentity.equals(properties.get_yago_tag_athlete())){
			if (persons_map.size() != persons_limit){
				entity_person person = persons_map.get(toParse.lentity);
				if (person!=null){
					person.addProfession(toParse.rentity);
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
		else if (toParse.rentity.equals(properties.get_yago_tag_currency())){
			currency_set.add(toParse.lentity);
		}
		else if (toParse.rentity.equals(properties.get_yago_tag_language())){
			language_set.add(toParse.lentity);

		}
		else if (toParse.rentity.equals(properties.get_yago_tag_city())){
			cities_set.add(toParse.lentity);
		}
	}
	

}
