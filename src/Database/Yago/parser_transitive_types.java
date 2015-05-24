package Database.Yago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.entity_country;



public class parser_transitive_types extends AbstractYagoParser{
	
	private final int max_list_size = 1000;
	
	HashMap<String, entity_country> countries_map;

	public parser_transitive_types(String filepath,  HashMap<String, entity_country> countries_map) {
		super(filepath);
		
		this.countries_map = countries_map;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse(YagoEntry toParse) {
		// TODO Auto-generated method stub
		if (toParse.rentity.equals("<wikicat_Countries>")){
			entity_country new_country = new entity_country();
			new_country.setYagoName(toParse.lentity);
			countries_map.put(toParse.lentity, new_country);
			System.out.println(toParse.lentity);
		}		
//		else if (toParse.rentity.equals("<wordnet_musician_110339966>")){
//			musicians_list.add(toParse.lentity);
//		}
//		else if (toParse.rentity.equals("<wordnet_scientist_110560637>")){
//			scientists_list.add(toParse.lentity);
//		}
//		else if (toParse.rentity.equals("<wordnet_politician_110450303>")){
//			politians_list.add(toParse.lentity);
//		}
//		else if (toParse.rentity.equals("<wordnet_actor_109765278>")){
//			actors_list.add(toParse.lentity);
//		}
//		else if (toParse.rentity.equals("<wordnet_athlete_109820263>")){
//			athletes_list.add(toParse.lentity);
//		}
//		
//		if (countries_list.size()==max_list_size){
//			//send to db
//			countries_list.removeAll(countries_list);
//		}
	}
	

}
