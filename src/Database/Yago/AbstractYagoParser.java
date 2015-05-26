package Database.Yago;

import java.io.FileNotFoundException;

import config.config;

public abstract class AbstractYagoParser implements Runnable {
	
	String filepath; //path of the yago TSV file
	config properties;
	public static final int VARCHAR_LIMIT = 45;
	/*
	 * Gets a TSV filepath and parses all its rows to populate the DB
	 */
	public AbstractYagoParser(String filepath) {
		this.filepath = filepath;
		this.properties = new config();
	}
	
	public void populate() throws FileNotFoundException {
		YagoLexer lexer = new YagoLexer(filepath);
		
		while(lexer.hasNext()) {
			YagoEntry next = lexer.next();
			if(next.lentity.length()>VARCHAR_LIMIT || next.rentity.length()>VARCHAR_LIMIT)
				continue;
			parse(next);
		}
				
	}
	public void run(){
		try {
			populate();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String entity_cleaner(String entity){
		return entity.replaceAll("<", "").replaceAll(">", "").replaceAll("_", " ");
	}
	/*
	 * This function gets a yago entry and uses it to populate the database
	 */
	abstract public void parse(YagoEntry toParse);

}
