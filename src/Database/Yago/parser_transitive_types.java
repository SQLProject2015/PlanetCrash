package Database.Yago;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.entity_city;
import entities.entity_country;
import entities.entity_currency;
import entities.entity_person;
import entities.entity_university;

		


public class parser_transitive_types extends AbstractYagoParser{
	
	private final int persons_limit = 1000000;
	
	HashMap<String, entity_country> countries_map;
	HashMap<String, entity_city> cities_map;
	HashMap<String, entity_university> universities_map;
	HashMap<String, entity_person> persons_map;
	HashSet<String> currency_set = new HashSet<String>();
	HashSet<String> language_set = new HashSet<String>();
	HashMap<String, Set<String>> countries_cities_map;
	HashMap<String, String > profession_tags = new HashMap<String, String>(){{
        put("<wordnet_musician_110339966>","Musician");
        put("<wordnet_scientist_110560637>","Scientist");
        put("<wordnet_politician_110450303>","Politician");
        put("<wordnet_actor_109765278>","Actor");
        put("<wordnet_athlete_109820263>","Athlete");
    }};
	
	

	public parser_transitive_types(String filepath,  HashMap<String, entity_country> countries_map,
			HashMap<String,entity_person> persons_map,HashMap<String, entity_city> cities_map,
			HashSet<String> currency_set, HashSet<String> language_set,
			HashMap<String, Set<String>> countries_cities_map,
			HashMap<String, entity_university> universities_map) {
		super(filepath);
		
		this.countries_map = countries_map;
		this.persons_map = persons_map;
		this.cities_map = cities_map;
		this.currency_set = currency_set;
		this.language_set = language_set;
		this.countries_cities_map = countries_cities_map;
		this.universities_map = universities_map;
	}

	@Override
	public boolean parse(YagoEntry toParse) {
		// TODO Auto-generated method stub
		String clean_lentity = entity_cleaner(toParse.lentity);
		
		if (toParse.rentity.equals(properties.get_yago_tag_country())){
			entity_country new_country = new entity_country();
			new_country.setYagoName(clean_lentity);
			countries_map.put(clean_lentity, new_country);
			countries_cities_map.put(clean_lentity, new HashSet<String>());
			return true;
		}
		else if (toParse.rentity.equals(properties.get_yago_tag_musician()) || toParse.rentity.equals(properties.get_yago_tag_scientist()) ||
				toParse.rentity.equals(properties.get_yago_tag_politician()) || toParse.rentity.equals(properties.get_yago_tag_actor()) ||
				toParse.rentity.equals(properties.get_yago_tag_athlete())){
			if (persons_map.size() != persons_limit){
				entity_person person = persons_map.get(clean_lentity);
				if (person!=null){
					person.addProfession(profession_tags.get(toParse.rentity));
				}
				else{
					entity_person new_person = new entity_person();
					new_person.setName(clean_lentity);
					new_person.addProfession(profession_tags.get(toParse.rentity));
					persons_map.put(clean_lentity, new_person);					
				}
			}
			return true;
			//System.out.println(clean_lentity);
		}
		else if (toParse.rentity.equals(properties.get_yago_tag_currency())){
			currency_set.add(clean_lentity);
			return true;
		}
		else if (toParse.rentity.equals(properties.get_yago_tag_language())){
			language_set.add(clean_lentity);
			return true;
		}
		else if (toParse.rentity.equals(properties.get_yago_tag_city())){
			entity_city new_city = new entity_city(clean_lentity);
			cities_map.put(clean_lentity, new_city);
			return true;
		}
		else if(toParse.rentity.equals(properties.get_yago_tag_university())){
			entity_university new_university = new entity_university(clean_lentity,"");
			universities_map.put(clean_lentity, new_university);
			return true;
		}
		return false;
	}
	
	
	

}
