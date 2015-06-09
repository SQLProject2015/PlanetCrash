import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import config.config;
import entities.entity_city;
import entities.entity_country;
import entities.entity_currency;
import entities.entity_person;
import entities.entity_university;
import Database.ConnectionPool;
import Database.DatabaseHandler;
import Database.Users.UserHandler;
import Database.Yago.parser_transitive_types;
import Database.Yago.parser_yago_date_facts;
import Database.Yago.parser_yago_facts;
import Database.Yago.parser_yago_literal_facts;
import Database.Yago.Uploaders.AwardWinnersUploader;
import Database.Yago.Uploaders.CitiesUploader;
import Database.Yago.Uploaders.CountriesCitiesUploader;
import Database.Yago.Uploaders.CountriesUploader;
import Database.Yago.Uploaders.CurrenciesUploader;
import Database.Yago.Uploaders.LanguagesUploader;
import Database.Yago.Uploaders.PersonProfessionUploader;
import Database.Yago.Uploaders.PersonsUploader;
import Database.Yago.Uploaders.UniversitiesUploader;
import GUI.GameGUI;



public class Main {

	public static ConnectionPool mConnPool;

	public static void main(String args[]) {
		
		
		mConnPool = new ConnectionPool();
		//Init connection pool
		mConnPool.init();

		DatabaseHandler dbh = new DatabaseHandler(mConnPool);
		config properties = new config();
		
		
		//Game GUI
//		GameGUI gg = new GameGUI(mConnPool);
//		gg.start();

		
		Init.Init(dbh, properties);

		try{
			ManualUpdater.add_Person("tomer baruch", "1989", "0", "Rome", dbh);
		}
		catch(Exception ex){
		}
		
		
		try{
			UserHandler.add_new_user("osher", "1234", dbh);
		}catch(Exception ex){
		}


	}

}
