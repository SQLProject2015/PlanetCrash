import java.io.FileNotFoundException;

import Database.Yago.parser_transitive_types;



public class Main {
	public static void main(String args[]) throws FileNotFoundException{
		parser_transitive_types c = new parser_transitive_types("D:\\db project\\yago3_tsv\\yagoTransitiveType.tsv");
		c.populate();
	}
	
}
