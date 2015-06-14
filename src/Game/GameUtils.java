package Game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Database.DatabaseHandler;

public class GameUtils {
	/*return question generator object for random country that the user did not completed yet,
	 * null in case that not found such country*/
	public static QuestionsGenerator generateCountry(int idUser, String dbName, DatabaseHandler dbh, int difficulty){
		QuestionsGenerator qg =null;
		ArrayList<Question> optionalQuestions=null;
		String query= "Select Country.Name "+
                "FROM "+dbName+".Country "+
				"WHERE Country.idCountry NOT IN ( SELECT user_country_completed.idCountry "+
                "FROM "+dbName+".user_country_completed "+
				"WHERE  user_country_completed.idUser='"+idUser+"')"+
                " ORDER BY RAND();";

		try {
			ResultSet rs = dbh.executeQuery(query);
			while(rs.next()){
				String countryName = rs.getString("Name");
				qg =  new QuestionsGenerator(countryName,dbh,dbName);
				optionalQuestions =qg.getPossibleQuestions();
				if(difficulty == 5 && optionalQuestions.size()>=5){
					return qg;
				}
				if(difficulty == 10 && optionalQuestions.size()>=10){
					return qg;
				}
				if(difficulty == 15 && optionalQuestions.size()>=15){
					return qg;
				}
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String funkName(String name) {
		StringBuilder funky = new StringBuilder();
		
		List<Character> vowels = new ArrayList<Character>(Arrays.asList(new Character[]{'a','e','i','o','u'}));
		List<String> garnish = new ArrayList<String>(Arrays.asList(new String[]{"X","Centauri","Y","1337","6","Flex","Zork","42","17","10"}));
		
		//funky vowels
		char[] ca = name.toCharArray();
		for(int i=0; i<ca.length; i++) {
			if(vowels.contains(ca[i]) && Math.random()>0.75)
				ca[i]=vowels.get((int)(Math.random()*vowels.size()));
			funky.append(ca[i]);
		}
		
		//Garnish
		for(int i=0;i<3;i++)
			if(Math.random()>0.3) {
				funky.append(" "+garnish.get((int)(Math.random()*garnish.size())));
			}
		
		return funky.toString();
	}
}
