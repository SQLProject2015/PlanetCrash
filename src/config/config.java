package config;

import java.io.IOException;
import java.util.Properties;
 
/* cofiguration handler class*/ 
public class config {
 
	private Properties configFile = new Properties();
	
	public config() 
	{
		try {
			configFile.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** get the path of the file yago_transitive_types.tsv */
	public String get_yago_transitive_types_path(){		
		return configFile.getProperty("YagoTransitiveTypeFilePath");
	}
	
	/** get the path of the file yagoFacts.tsv */
	public String get_yago_facts_path(){
		return configFile.getProperty("YagoFactsFilePath");
	}

	/** get the path of the file yagoLiteralFacts.tsv */
	public String get_yago_literal_facts_path(){
		return configFile.getProperty("YagoLiteralFactsFilePath");
	}
	/** get the path of the file yagoDateFacts.tsv */
	public String get_yago_date_facts_path(){
		return configFile.getProperty("YagoDateFacts");
	}

	/** get the server address */
	public String get_host_address(){
		return configFile.getProperty("Host");
	}
	
	/** get the port of the server */
	public String get_port(){
		return configFile.getProperty("Port");
	}
	
	/** get the dbName(Schema name) */
	public String get_db_name(){
		return configFile.getProperty("DbName");
	}
	
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
	/** get the yago tag name for "city" type **/
	public String get_yago_tag_city(){
		return configFile.getProperty("CITY");
	}
	
	/** get the yago tag name for "language" type **/
	public String get_yago_tag_language(){
		return configFile.getProperty("LANGUAGE");
	}
	/** get the yago tag name for "currency" type **/
	public String get_yago_tag_currency(){
		return configFile.getProperty("CURRENCY");
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



//	/** get number of connections to create **/
//	public  int get_window_height(){
//		return Integer.parseInt(configFile.getProperty("WINDOW_HEIGHT"));
//	}
//	/** get number of connections to create **/
//	public int get_window_width(){
//		return Integer.parseInt(configFile.getProperty("WINDOW_WIDTH"));
//	}
//
//	public int get_default_small_limit() {
//		return Integer.parseInt(configFile.getProperty("DEFAULT_SMALL_LIMIT"));
//	}
//	
//	public int get_default_large_limit() {
//		String x= configFile.getProperty("DEFAULT_BIG_LIMIT");
//		return Integer.parseInt(x);
//	}
//
//	public int get_admin_userid() {
//		return Integer.parseInt(configFile.getProperty("ADMIN_USERNAME"));
//	}
//	
//	public int get_default_director_id() {
//		return Integer.parseInt(configFile.getProperty("DEFAULT_DIRECTOR_ID"));
//	}
}