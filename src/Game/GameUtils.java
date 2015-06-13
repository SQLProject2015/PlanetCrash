package Game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.DatabaseHandler;

public class GameUtils {
	/*return question generator object for random country that the user did not completed yet,
	 * null in case that not found such country*/
	public static QuestionsGenerator generateCountry(int idUser, String dbName, DatabaseHandler dbh, int difficulty){
		QuestionsGenerator qg =null;
		ArrayList<Question> optionalQuestions=null;
		String query= "Select Country.Name "+
                "FROM "+dbName+".Country, "+dbName+".user_country_completed"+
                "WHERE user_country_completed.idUser='"+idUser+"'"+
                " and Country.idCountry!=user_country_completed.idCountry"+
                " ORDER BY RAND();";
		try {
			ResultSet rs = dbh.executeQuery(query);
			while(rs.next()){
				String countryName = rs.getString("Name");
				qg =  new QuestionsGenerator(countryName,dbh,dbName);
				optionalQuestions =qg.getPossibleQuestions();
				if(difficulty == 1 && optionalQuestions.size()>=5){
					return qg;
				}
				if(difficulty == 2 && optionalQuestions.size()>=10){
					return qg;
				}
				if(difficulty == 3 && optionalQuestions.size()>=15){
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
}
