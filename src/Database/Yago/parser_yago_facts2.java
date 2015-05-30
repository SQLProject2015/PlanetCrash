package Database.Yago;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import entities.entity_country;
import entities.entity_country_city;
import entities.entity_person;
import entities.entity_university;

public class parser_yago_facts2 extends AbstractYagoParser{
	
	HashSet<String> cities_set;
	HashMap<String, entity_country> countries_map;
	HashMap<String, Set<String>> countries_cities_map;
	HashMap<String, entity_university> universities_map;
	HashMap<String, entity_person> persons_map;
	Set<String> awards_set = new HashSet<String>(Arrays.asList("Grammy Award", "Grammy Lifetime Achievement Award", "Academy Award for Best Actor",
			"Academy Award for Best Actress", "Nobel Prize in Physics", "Nobel Prize in Chemistry", "Nobel Peace Prize", "FIFA World Player of the Year"));
	HashMap<String, entity_person> lite_persons_map = new HashMap<String, entity_person>();
	HashSet<String> lite_city_set;

	
	public parser_yago_facts2(String filepath, HashMap<String, entity_country> countries_map,
			HashMap<String, Set<String>> countries_cities_map, HashSet<String> cities_set,
			HashMap<String, entity_university> universities_map, HashMap<String, entity_person> persons_map, HashMap<String, entity_person> lite_persons_map, HashSet<String> lite_city_set) {		
		super(filepath);
		
		this.countries_map = countries_map;
		this.countries_cities_map = countries_cities_map;
		this.cities_set = cities_set;
		this.universities_map = universities_map;
		this.persons_map = persons_map;
		this.lite_persons_map = lite_persons_map;
		this.lite_city_set = lite_city_set;
	}

	@Override
	public boolean parse(YagoEntry toParse) {
		boolean flag=false;
		
		String clean_lentity = entity_cleaner(toParse.lentity);
		String clean_rentity = entity_cleaner(toParse.rentity);
						
		if (toParse.relation.equals(properties.get_yago_tag_capital_city())){
			if (countries_map.containsKey(clean_lentity) && cities_set.contains(clean_rentity)){
				lite_city_set.add(clean_rentity);
			}
		}
		
		if (toParse.relation.equals(properties.get_yago_tag_birth_place())){
			if (lite_persons_map.containsKey(clean_lentity) && cities_set.contains(clean_rentity)){
				lite_city_set.add(clean_rentity);
			}
		}
		
		
		
		return flag;
		
		
	}
	

}
