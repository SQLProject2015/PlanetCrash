package Database.Yago;

import java.util.ArrayList;
import java.util.List;



public class parse_countries extends AbstractYagoParser{
	
	private final int max_list_size = 1000;
	List<String> countries_list = new ArrayList<String>();

	public parse_countries(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse(YagoEntry toParse) {
		// TODO Auto-generated method stub
		if (toParse.rentity.equals("<wikicat_Countries>")){
			countries_list.add(toParse.lentity);
		}
		
		if (countries_list.size()==max_list_size){
			//send to db
			countries_list.removeAll(countries_list);
		}
	}
	

}
