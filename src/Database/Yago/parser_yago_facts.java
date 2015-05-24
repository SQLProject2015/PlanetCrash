package Database.Yago;

import java.util.HashMap;
import java.util.List;

import entities.entity_country;

public class parser_yago_facts extends AbstractYagoParser{
	
	private final int max_list_size = 1000;
	
	HashMap<String, entity_country> countries_map;
	
	public parser_yago_facts(String filepath, HashMap<String, entity_country> countries_map) {		
		super(filepath);
		
		this.countries_map = countries_map;
		// TODO Auto-generated constructor stub
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

	}
	

}
