import java.io.FileNotFoundException;

import Database.Yago.parse_countries;



public class Main {
	public static void main(String args[]) throws FileNotFoundException{
		parse_countries c = new parse_countries("D:\\db project\\yago3_tsv\\yagoTransitiveType.tsv");
		c.populate();
	}
	
}
