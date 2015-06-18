package PlanetCrash.core.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
 
/* cofiguration handler class*/ 
public class Config {
 
	private Properties configFile = new Properties();
	
	public Config() 
	{
		try {
			configFile.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
//	/** get the state of the DB */
//	public String get_db_ready(){		
//		return configFile.getProperty("DbReady");
//	}
//	/** set the state of the DB  */
//	public void set_db_ready(String state){		
//	    try {
//
//	    	configFile.setProperty("DbReady", state);
//	        File f = new File("src/config.properties");
//	        OutputStream out = new FileOutputStream( f );
//	        configFile.store(out, null);
//	        out.close();
//	    }
//	    catch (Exception e ) {
//	        e.printStackTrace();
//	    }
//	}
//	
	/** get the path of the file yago_transitive_types.tsv */
	public String get_yago_transitive_types_path(){		
		return configFile.getProperty("YagoTransitiveTypeFilePath");
	}
	/** set the path of the file yago_transitive_types.tsv */
//	public void set_yago_transitive_types_path(String path){		
//	    try {
//
//	    	configFile.setProperty("YagoTransitiveTypeFilePath", path);
//	        File f = new File("src/config.properties");
//	        OutputStream out = new FileOutputStream( f );
//	        configFile.store(out, null);
//	        out.close();
//	    }
//	    catch (Exception e ) {
//	        e.printStackTrace();
//	    }
//	}
	
	/** get the path of the file yagoFacts.tsv */
	public String get_yago_facts_path(){
		return configFile.getProperty("YagoFactsFilePath");
	}
//	/** set the path of the file yagoFacts.tsv */
//	public void set_yago_facts_path(String path){		
//	    try {
//
//	    	configFile.setProperty("YagoFactsFilePathh", path);
//	        File f = new File("src/config.properties");
//	        OutputStream out = new FileOutputStream( f );
//	        configFile.store(out, null);
//	        out.close();
//	    }
//	    catch (Exception e ) {
//	        e.printStackTrace();
//	    }
//	}

	/** get the path of the file yagoLiteralFacts.tsv */
	public String get_yago_literal_facts_path(){
		return configFile.getProperty("YagoLiteralFactsFilePath");
	}
	/** set the path of the file yagoLiteralFacts.tsv */
//	public void set_yago_literal_facts_path(String path){		
//	    try {
//
//	    	configFile.setProperty("YagoLiteralFactsFilePath", path);
//	        File f = new File("src/config.properties");
//	        OutputStream out = new FileOutputStream( f );
//	        configFile.store(out, null);
//	        out.close();
//	    }
//	    catch (Exception e ) {
//	        e.printStackTrace();
//	    }
//	}
	/** get the path of the file yagoDateFacts.tsv */
	public String get_yago_date_facts_path(){
		return configFile.getProperty("YagoDateFactsFilePath");
	}
//	/** set the path of the file yagoDateFacts.tsv */
//	public void set_yago_date_facts_path(String path){		
//	    try {
//
//	    	configFile.setProperty("YagoDateFactsFilePath", path);
//	        File f = new File("src/config.properties");
//	        OutputStream out = new FileOutputStream( f );
//	        configFile.store(out, null);
//	        out.close();
//	    }
//	    catch (Exception e ) {
//	        e.printStackTrace();
//	    }
//	}
	/** get the path of the file YagoWikipediaInfo.tsv */
	public String get_yago_wikipedia_info_path(){
		return configFile.getProperty("YagoWikipediaInfoPath");
	}
//	/** set the path of the file YagoWikipediaInfo.tsv */
//	public void set_yago_wikipedia_info_path(String path){		
//	    try {
//
//	    	configFile.setProperty("YagoWikipediaInfoPath", path);
//	        File f = new File("src/config.properties");
//	        OutputStream out = new FileOutputStream( f );
//	        configFile.store(out, null);
//	        out.close();
//	    }
//	    catch (Exception e ) {
//	        e.printStackTrace();
//	    }
//	}

	/** get the server address */
	public String get_host_address(){
		return configFile.getProperty("Host");
	}
//	/** set the server address */
//	public void set_host_address(String host){		
//	    try {
//
//	    	configFile.setProperty("Host", host);
//	        File f = new File("src/config.properties");
//	        OutputStream out = new FileOutputStream( f );
//	        configFile.store(out, null);
//	        out.close();
//	    }
//	    catch (Exception e ) {
//	        e.printStackTrace();
//	    }
//	}
	/** get the port of the server */
	public String get_port(){
		return configFile.getProperty("Port");
	}
//	/** set the port */
//	public void set_port(String port){		
//	    try {
//
//	    	configFile.setProperty("Port", port);
//	        File f = new File("src/config.properties");
//	        OutputStream out = new FileOutputStream( f );
//	        configFile.store(out, null);
//	        out.close();
//	    }
//	    catch (Exception e ) {
//	        e.printStackTrace();
//	    }
//	}
	/** get the dbName(Schema name) */
	public String get_db_name(){
		return configFile.getProperty("DbName");
	}
//	/** set the dbName(Schema name) */
//	public void set_db_name(String dbname){		
//	    try {
//
//	    	configFile.setProperty("DbName", dbname);
//	        File f = new File("src/config.properties");
//	        OutputStream out = new FileOutputStream( f );
//	        configFile.store(out, null);
//	        out.close();
//	    }
//	    catch (Exception e ) {
//	        e.printStackTrace();
//	    }
//	}
	
	/** get the user name */
	public String get_user_name(){
		return configFile.getProperty("UserName");
	}
	
	/** get user password */
	public String get_password(){
		return configFile.getProperty("Password");
	}
	
	/** get number of connections to create **/
	public int get_number_connection(){
		return Integer.parseInt(configFile.getProperty("NumOfConnections"));
	}
	
	/*parsing*/
	


	/** get the yago tag name for "country" type **/
	public String get_yago_tag_country(){
		return configFile.getProperty("COUNTRY");
	}
	/** get the yago tag name for "country" type **/
	public String get_yago_tag_university(){
		return configFile.getProperty("UNIVERSITY");
	}
	/** get the yago tag name for "city" type **/
	public String get_yago_tag_city(){
		return configFile.getProperty("CITY");
	}
	
	/** get the yago tag name for "language" type **/
	public String get_yago_tag_language(){
		return configFile.getProperty("LANGUAGE");
	}
	/** get the yago tag name for "language" type **/
	public String get_yago_tag_language2(){
		return configFile.getProperty("LANGUAGE2");
	}
	/** get the yago tag name for "currency" type **/
	public String get_yago_tag_currency(){
		return configFile.getProperty("CURRENCY");
	}
	/** get the yago tag name for "currency" type **/
	public String get_yago_tag_currency2(){
		return configFile.getProperty("CURRENCY2");
	}
	/** get the yago tag name for "population" type **/
	public String get_yago_tag_population(){
		return configFile.getProperty("POPULATION");
	}
	
	/** get the yago tag name for "musician" type **/
	public String get_yago_tag_musician(){
		return configFile.getProperty("MUSICIAN");
	}
	/** get the yago tag name for "scientist" type **/
	public String get_yago_tag_scientist(){
		return configFile.getProperty("SCIENTIST");
	}
	/** get the yago tag name for "politician" type **/
	public String get_yago_tag_politician(){
		return configFile.getProperty("POLITICIAN");
	}
	/** get the yago tag name for "actor" type **/
	public String get_yago_tag_actor(){
		return configFile.getProperty("ACTOR");
	}
	/** get the yago tag name for "athlete" type **/
	public String get_yago_tag_athlete(){
		return configFile.getProperty("ATHLETHE");
	}

	/** get the yago tag name for "date of birth" fact **/
	public String get_yago_tag_birth_date(){
		return configFile.getProperty("BORNDATE");
	}
	/** get the yago tag name for "date of death" fact **/
	public String get_yago_tag_death_date(){
		return configFile.getProperty("DEATHDATE");
	}
	/** get the yago tag name for "locatedin" fact **/
	public String get_yago_tag_located_in(){
		return configFile.getProperty("FACT_LOCATION");
	}
	/** get the yago tag name for "has currency" fact **/
	public String get_yago_tag_has_currency(){
		return configFile.getProperty("FACT_CURRENCY");
	}
	/** get the yago tag name for "official language" fact **/
	public String get_yago_tag_official_language(){
		return configFile.getProperty("FACT_LANGUAGE");
	}
	/** get the yago tag name for "capital city" fact **/
	public String get_yago_tag_capital_city(){
		return configFile.getProperty("FACT_CAPITAL");
	}
	/** get the yago tag name for "capital city" fact **/
	public String get_yago_tag_leader(){
		return configFile.getProperty("FACT_LEADER");
	}
	public String get_yago_tag_prize(){
		return configFile.getProperty("FACT_PRIZE");
	}
	public String get_yago_tag_birth_place(){
		return configFile.getProperty("FACT_BIRTHPLACE");
	}
	public String get_yago_tag_wiki_len(){
		return configFile.getProperty("FACT_WIKILEN");
	}
	public int get_min_wiki_len(){
		return Integer.parseInt(configFile.getProperty("MIN_WIKILEN"));
	}
	public int get_files_size(){
		return Integer.parseInt(configFile.getProperty("TOTAL_FILES_SIZE"));
	}
	
}