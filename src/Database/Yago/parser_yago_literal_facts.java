package Database.Yago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.entity_country;

public class parser_yago_literal_facts extends AbstractYagoParser{
	
	
	HashMap<String, entity_country> countries_map;
	
	public parser_yago_literal_facts(String filepath, HashMap<String, entity_country> countries_map) {		
		super(filepath);
		
		this.countries_map = countries_map;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse(YagoEntry toParse) {
		
		if (countries_map.containsKey(toParse.lentity)){
			if (toParse.relation.equals("<hasNumberOfPeople>")){
				int population = 0;
				Pattern p = Pattern.compile("\"([^\"]*)\"");
				Matcher m = p.matcher(toParse.rentity);
				while (m.find()) {
				  population = Integer.parseInt((m.group(1)));
				}
				countries_map.get(toParse.lentity).setPopulation_size(population);
			}
		}	
	}
	

}
