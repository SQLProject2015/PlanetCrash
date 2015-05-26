package Database.Yago;

import java.io.FileNotFoundException;

public abstract class AbstractYagoParser {
	
	String filepath; //path of the yago TSV file
	public static final int VARCHAR_LIMIT = 45;
	/*
	 * Gets a TSV filepath and parses all its rows to populate the DB
	 */
	public AbstractYagoParser(String filepath) {
		this.filepath = filepath;
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
	
	/*
	 * This function gets a yago entry and uses it to populate the database
	 */
	abstract public void parse(YagoEntry toParse);

}
