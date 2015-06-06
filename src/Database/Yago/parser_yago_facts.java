package Database.Yago;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.entity_city;
import entities.entity_country;
import entities.entity_country_city;
import entities.entity_person;
import entities.entity_university;

public class parser_yago_facts extends AbstractYagoParser{
	
	HashMap<String, entity_city> cities_map;
	HashMap<String, entity_country> countries_map;
	HashMap<String, Set<String>> countries_cities_map;
	HashMap<String, entity_university> universities_map;
	HashMap<String, entity_person> persons_map;
	Set<String> awards_set = new HashSet<String>(Arrays.asList("Grammy Award", "Grammy Lifetime Achievement Award", "Academy Award for Best Actor",
			"Academy Award for Best Actress", "Nobel Prize in Physics", "Nobel Prize in Chemistry", "Nobel Peace Prize", "FIFA World Player of the Year"));
	HashMap<String, entity_person> lite_persons_map = new HashMap<String, entity_person>();

	
	public parser_yago_facts(String filepath, HashMap<String, entity_country> countries_map,
			HashMap<String, Set<String>> countries_cities_map, HashMap<String, entity_city> cities_map,
			HashMap<String, entity_university> universities_map, HashMap<String, entity_person> persons_map, HashMap<String, entity_person> lite_persons_map) {		
		super(filepath);
		
		this.countries_map = countries_map;
		this.countries_cities_map = countries_cities_map;
		this.cities_map = cities_map;
		this.universities_map = universities_map;
		this.persons_map = persons_map;
		this.lite_persons_map = lite_persons_map;
	}

	@Override
	public boolean parse(YagoEntry toParse) {
		boolean flag=false;
		
		String clean_lentity = entity_cleaner(toParse.lentity);
		String clean_rentity = entity_cleaner(toParse.rentity);
		
		entity_country country = countries_map.get(clean_lentity);
		
		if (country != null){
			if (toParse.relation.equals(properties.get_yago_tag_located_in())){
				country.setContinent(clean_rentity);
				flag=true;
			}
			else if (toParse.relation.equals(properties.get_yago_tag_has_currency())){
				country.setCurrency(clean_rentity);
				flag=true;
			}
			else if (toParse.relation.equals(properties.get_yago_tag_official_language())){
				country.setLanguage(clean_rentity);
				flag=true;
			}
			else if (toParse.relation.equals(properties.get_yago_tag_capital_city())){
				country.setCapital(clean_rentity);
				flag=true;
			}
		}

		
		country = countries_map.get(clean_rentity);
		if(country != null){
			if (toParse.relation.equals(properties.get_yago_tag_leader())){
				country.setLeader(clean_lentity);
				flag=true;
			}
		}
		
				
		entity_city city = cities_map.get(clean_lentity);
		if (city!=null){
			if (toParse.relation.equals(properties.get_yago_tag_located_in())){
				city.setCountry(clean_rentity);
				flag=true;
			}
		}
		
//		Set<String> list = countries_cities_map.get(clean_rentity);
//		if (list!=null){
//			if (toParse.relation.equals(properties.get_yago_tag_located_in()) && cities_set.contains(clean_lentity)){
//				list.add(clean_lentity);
//				flag=true;
//			}
//		}
		
		entity_university university = universities_map.get(clean_lentity);
		if(university != null){
			if (toParse.relation.equals(properties.get_yago_tag_located_in())){
				university.setCountry(clean_lentity);
				flag=true;
			}
		}
		
		entity_person per = persons_map.get(clean_lentity);
		if (per != null){
			if (toParse.relation.equals(properties.get_yago_tag_prize())){
				if (awards_set.contains(clean_rentity)) {
					per.addAward(clean_rentity);
					lite_persons_map.put(clean_lentity,per);
					flag=true;
				}
			}
			if (toParse.relation.equals(properties.get_yago_tag_wiki_len())) {
				int population = 0;
				Pattern p = Pattern.compile("\"([^\"]*)\"");
				Matcher m = p.matcher(toParse.rentity);
				while (m.find()) {
				  population = Integer.parseInt((m.group(1)));
				}
				if (population > 20000){
					lite_persons_map.put(clean_lentity,per);
					flag=true;
				}
				
			}
			if (toParse.relation.equals(properties.get_yago_tag_birth_place())){
				per.setPlaceOfBirth(clean_rentity);
				flag = true;
			}
		}
		

		
		
		
		
		return flag;
		
		
	}
	

}
