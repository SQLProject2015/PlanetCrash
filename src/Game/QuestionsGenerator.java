package Game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class QuestionsGenerator {
	private ArrayList<Question> possibleQuestions;
	
	public QuestionsGenerator(){

		possibleQuestions.add(new Question("","What is the official currency of $?"));
		possibleQuestions.add(new Question("","How much citizens $ has?"));
		possibleQuestions.add(new Question("","What is the official language in $?"));
		possibleQuestions.add(new Question("","In which continent $ located?"));
		possibleQuestions.add(new Question("","What is the proffesion of the folowing?"));
		possibleQuestions.add(new Question("","Who of the folowing?"));
		
	}
	public void generateQuestionForCountry(String countryName, int numOfQuestions){

		
	}
	private void generateOfficialCurrencyQuestion(String countryName, int countryId){
		Question q = new Question("What is the official currency of "+countryName);
		String query = "SELECT Currency.Name " +
                   "FROM Country,Currency "+
                   "WHERE Country.idCountry='"+countryId+"' and Country.idCurrency=Currency.idCurrency";
		ResultSet rs =null;//jdbc
		try {
			if (rs.next()){
				String capital = rs.getString("Name");
				q.setCorrectAnswer(new Answer(capital));
				q.addPossibleAnswers(new Answer(capital));
				query = "SELECT City.Name " +
			               "FROM Country_City, City "+
			               "WHERE Country_City.idCountry='"+countryId+"' and Country_City.idCity=City.idCity and City.Name!='"+capital+
			               "' LIMIT 3";
				rs =null;//jdbc
				int i =0;
				while (rs.next()){
					i++;
					String city = rs.getString("Name");
					q.addPossibleAnswers(new Answer(city));
				}
				if (i==3){
					q.setQuestionAsReady();
					possibleQuestions.add(q);
				}
				return;
			}
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void generateCapitalCityQuestion(String countryName, int countryId){
		Question q = new Question("What is the capital city of "+countryName);
		String query = "SELECT City.Name " +
                   "FROM Country,Country_City,City "+
                   "WHERE Country.idCountry='"+countryId+"' and Country.idCapital=Country_City.idCity"+
                   " and Country_City.idCity=city.idCity";
		ResultSet rs =null;//jdbc
		try {
			if (rs.next()){
				String capital = rs.getString("Name");
				q.setCorrectAnswer(new Answer(capital));
				q.addPossibleAnswers(new Answer(capital));
				query = "SELECT City.Name " +
			               "FROM Country_City, City "+
			               "WHERE Country_City.idCountry='"+countryId+"' and Country_City.idCity=City.idCity and City.Name!='"+capital+
			               "' LIMIT 3";
				rs =null;//jdbc
				int i =0;
				while (rs.next()){
					i++;
					String city = rs.getString("Name");
					q.addPossibleAnswers(new Answer(city));
				}
				if (i==3){
					q.setQuestionAsReady();
					possibleQuestions.add(q);
				}
				return;
			}
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void generatePrizeWinnerQuestion(String prizeName, int countryId){
		String query ="SELECT Person.Name "+
					"FROM Award, AwardWinners, Person, Country_City "+
				"WHERE Country_City.idCountry ='"+countryId+"' and"+
					"Person.idPlaceOfBirth = Country_City.idCity and"+
				"Person.idPerson = AwardWinners.idPerson and"+
					"AwardWinners.idAward = Award.idAward and"+
					"Award.Name ='"+prizeName+"'";
		ResultSet rs =null;//jdbc
		ArrayList<String> winners = new ArrayList<String>();
		ArrayList<String> nonWinners = new ArrayList<String>();
		try {
			int i = 0;
			while(rs.next()){
				i++;
				winners.add(rs.getString("Name"));
				if(i==5){
					break;
				}
			}
			if (i==0){
				return;
			}
			query ="SELECT Person.Name "+
					"FROM Award, AwardWinners, Person, Country_City "+
				"WHERE Country_City.idCountry ='"+countryId+"' and"+
					"Person.idPlaceOfBirth = Country_City.idCity and"+
				"Person.idPerson = AwardWinners.idPerson and"+
				"AwardWinners.idAward = Award.idAward and"+
				"Award.Name ='"+prizeName+"'";
			rs =null;//jdbc
			while(rs.next()){
				nonWinners.add(rs.getString("Name"));
			}
			if (nonWinners.size()<3){
				return;
			}
			int j;
			for ( i=0;i<winners.size();i++){
				if (i==5){
					break;
				}
				Question q = new Question("Who won the "+prizeName+"?");
				q.setCorrectAnswer(new Answer(winners.get(i)));
				q.addPossibleAnswers(new Answer(winners.get(i)));
				ArrayList<Integer> indexes = randomIndexes(nonWinners.size(), 3);
				for (j=0;j<3;j++){
					q.addPossibleAnswers(new Answer(nonWinners.get(indexes.get(j))));
				}
				q.setQuestionAsReady();
				possibleQuestions.add(q);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private ArrayList<Integer> randomIndexes(int range, int numToPick){
		ArrayList<Integer> picked = new ArrayList<Integer>();
		Random r = new Random(range);
		while(picked.size()<numToPick){
			int pick = r.nextInt();
			if (!picked.contains(pick)){
				picked.add(pick);
			}
		}
		return picked;
	}
}
