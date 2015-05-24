package Database.Yago;

import java.io.FileNotFoundException;

public abstract class AbstractYagoParser {
	
	String filepath; //path of the yago TSV file

	/*
	 * Gets a TSV filepath and parses all its rows to populate the DB
	 */
	public AbstractYagoParser(String filepath) {
		this.filepath = filepath;
	}
	
	public void populate() throws FileNotFoundException {
		YagoLexer lexer = new YagoLexer(filepath);
		
		while(lexer.hasNext())
			parse(lexer.next());
				
	}
	
	/*
	 * This function gets a yago entry and uses it to populate the database
	 */
	abstract public void parse(YagoEntry toParse);

}
