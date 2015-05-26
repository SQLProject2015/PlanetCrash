package Database.Yago;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.entity_person;
public class parser_yago_date_facts extends AbstractYagoParser{
	
	
	HashMap<String, entity_person> persons_map;
	
	public parser_yago_date_facts(String filepath, HashMap<String, entity_person> persons_map) {		
		super(filepath);
		
		this.persons_map = persons_map;
	}

	@Override
	public void parse(YagoEntry toParse) {
		
		if (persons_map.containsKey(toParse.lentity)){
			if (toParse.relation.equals(properties.get_yago_tag_birth_date())){
				persons_map.get(toParse.lentity).setYearOfBirth(getYearFromYagoDate(toParse.rentity));
			}
			else if (toParse.relation.equals(properties.get_yago_tag_death_date())){
				persons_map.get(toParse.lentity).setYearOfDeath(getYearFromYagoDate(toParse.rentity));
			}
		}
	}
	
	public int getYearFromYagoDate(String toParse){
		int year = 0;
		String date = "";
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(toParse);
		while (m.find()) {
			date = m.group(1);
		}
		
		try{
		year = Integer.parseInt(date.split("-")[0]);
		} catch(NumberFormatException e) {
			return -1;
		}
		return year;
	}
	

}
