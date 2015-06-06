package Game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import Database.DatabaseHandler;

public class QuestionsGenerator {
	private ArrayList<Question> possibleQuestions;
	private String countryName;
	private int countryId;
	private DatabaseHandler dbh;
	private String dbname;
	
	public QuestionsGenerator(String countryName,DatabaseHandler dbh,String dbname){
		this.countryName=countryName;
		this.dbh = dbh;
		this.dbname = dbname;
		possibleQuestions = new ArrayList<Question>();
		String query = "SELECT Country.idCountry " +
                "FROM "+this.dbname+".Country "+
                "WHERE Country.Name='"+countryName+"';";
		
		try {
			ResultSet rs =this.dbh.executeQuery(query);//jdbc
			if(rs.next())
				countryId=rs.getInt("idCountry");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		generatePopulationSizeQuestion();
		generateContinentQuestion();
		generateOfficialLanguageQuestion();
		generateOfficialCurrencyQuestion();
		generateCapitalCityQuestion();
		//generateBornInQuestions();//up to 3 questions of this type
		//generatePrizeWinnerQuestions("Grammy Award");//up to 2 questions of this type
		//generatePrizeWinnerQuestions("Grammy Lifetime Achievement Award");//up to 2 questions of this type
		//generatePrizeWinnerQuestions("Academy Award for Best Actor");//up to 2 questions of this type
		//generatePrizeWinnerQuestions("Academy Award for Best Actress");//up to 2 questions of this type
		//generatePrizeWinnerQuestions("Nobel Peace Prize");//up to 2 questions of this type
		//generatePrizeWinnerQuestions("FIFA World Player of the Year");//up to 2 questions of this type
		//generatePrizeWinnerQuestions("Nobel Prize in Physics");//up to 2 questions of this type
		//generatePrizeWinnerQuestions("Nobel Prize in Chemistry");//up to 2 questions of this type

		
	}
	public void generateQuestionForCountry(String countryName, int numOfQuestions){

		
	}
	
	private void generateProffesionQuestion(){
		String query = "SELECT Person.idPerson, Person.Name, Person " +
                "FROM"+this.dbname+".City, "+this.dbname+".Person_Proffesion, "+this.dbname+".Person "+
                "WHERE Country.idCountry='"+countryId+"';";
	}
	private void generateYearOfBirthQuestions(){
		String query = "SELECT Person.Name, Person.yearOfBirth " +
                "FROM "+this.dbname+".City, "+this.dbname+".Person "+
                "WHERE City.idCountry='"+countryId+"' and Person.idPlaceOfBirth=City.idCity"+
                " and Person.Name IS NOT NULL and Person.yearOfBirth IS NOT NULL"+
                "ORDER BY RAND()"+
                "LIMIT 3;";
	}
	private void generateBornInQuestions(){
		String query_out = "Person.Name" +
                "FROM "+this.dbname+".City, "+this.dbname+".Person "+
                "WHERE City.idCountry!='"+countryId+"'"+"Person.idBirthPlace=City.idCountry "+
                "and Person.Name IS NOT NULL "+
                "ORDER BY RAND()"+
                "LIMIT 3";
		String query_in = "Person.Name" +
                "FROM "+this.dbname+".City, "+this.dbname+".Person "+
                "WHERE City.idCountry='"+countryId+"'"+"Person.idBirthPlace=City.idCountry "+
                "and Person.Name IS NOT NULL "+
                "ORDER BY RAND()"+
                "LIMIT 15";

		ArrayList<String> born_in= new ArrayList<String>();
		try {
			ResultSet rs_in =this.dbh.executeQuery(query_in);//jdbc
			ResultSet rs_out =this.dbh.executeQuery(query_out);//jdbc
			while(rs_in.next()){
				born_in.add(rs_in.getString("Name"));
			}
			while(rs_out.next()){
				Question q = new Question("Who of the following was not born in"+countryName+"?");
				q.setCorrectAnswer(new Answer(rs_out.getString("Name")));
				q.addPossibleAnswers(new Answer(rs_out.getString("Name")));
				ArrayList<Integer> indexes = randomIndexes(born_in.size(), 3);
				int i;
				for (i=0;i<3;i++){
					q.addPossibleAnswers(new Answer(born_in.get(indexes.get(i))));
				}
				q.setQuestionAsReady();
				possibleQuestions.add(q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void generatePopulationSizeQuestion(){
		Question q = new Question("What is the population size of "+countryName+"?");
		String query = "SELECT Country.PopulationSize " +
                   "FROM "+this.dbname+".Country "+
                   "WHERE Country.idCountry='"+countryId+"'"+
                   " and Country.PopulationSize IS NOT NULL;";
		
		try {
			ResultSet rs =this.dbh.executeQuery(query);//jdbc
			if (rs.next()){				
				Integer populationSize = rs.getInt("PopulationSize");
				q.setCorrectAnswer(new Answer(populationSize.toString()));
				q.addPossibleAnswers(new Answer(populationSize.toString()));
				q.addPossibleAnswers(new Answer(((Integer)(populationSize/2)).toString()));
				q.addPossibleAnswers(new Answer(((Integer)(populationSize*2)).toString()));
				q.addPossibleAnswers(new Answer(((Integer)(populationSize*4)).toString()));
				q.setQuestionAsReady();
				possibleQuestions.add(q);
			}
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void generateContinentQuestion(){
		Question q = new Question("In which continent "+countryName+" located?");
		String query = "SELECT Continent.Name " +
                   "FROM "+this.dbname+".Country,"+this.dbname+".Continent "+
                   "WHERE Country.idCountry='"+countryId+"' and Country.idContinent=Continent.idContinent"
                   +" and Continent.Name IS NOT NULL;";

		try {
			ResultSet rs =this.dbh.executeQuery(query);//jdbc
			if (rs.next()){
				String locatedContinent = rs.getString("Name");
				q.setCorrectAnswer(new Answer(locatedContinent));
				q.addPossibleAnswers(new Answer(locatedContinent));
				query = "SELECT Distinct Continent.Name " +
			               "FROM "+this.dbname+".Continent "+
			               "WHERE Continent.Name!='"+locatedContinent+"'"+
			               " and Continent.Name IS NOT NULL "+
			               "ORDER BY RAND()"+
			               " LIMIT 3;";
				rs =this.dbh.executeQuery(query);//jdbc
				int i =0;
				while (rs.next()){
					i++;
					String continent = rs.getString("Name");
					q.addPossibleAnswers(new Answer(continent));
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
	private void generateOfficialLanguageQuestion(){
		Question q = new Question("What is the official language in "+countryName);
		String query = "SELECT Language.Name " +
                   "FROM "+this.dbname+".Country,"+this.dbname+".Language "+
                   "WHERE Country.idCountry='"+countryId+"' and Country.idLanguage=Language.idLanguage"+
                   " and Language.Name IS NOT NULL;";

		try {
			ResultSet rs =this.dbh.executeQuery(query);//jdbc
			if (rs.next()){
				String officialLanguage = rs.getString("Name");
				q.setCorrectAnswer(new Answer(officialLanguage));
				q.addPossibleAnswers(new Answer(officialLanguage));
				query = "SELECT DISTINCT Language.Name " +
			               "FROM "+this.dbname+".Language "+
			               "WHERE Language.Name!='"+officialLanguage+"' and Language.Name IS NOT NULL"+               
			               "ORDER BY RAND()"+
			               " LIMIT 3;";
				rs =this.dbh.executeQuery(query);//jdbc
				int i =0;
				while (rs.next()){
					i++;
					String language = rs.getString("Name");
					q.addPossibleAnswers(new Answer(language));
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
	private void generateOfficialCurrencyQuestion(){
		Question q = new Question("What is the official currency of "+countryName);
		String query = "SELECT Currency.Name " +
                   "FROM "+this.dbname+".Country,"+this.dbname+".Currency "+
                   "WHERE Country.idCountry='"+countryId+"' and Country.idCurrency=Currency.idCurrency"+
                   " and Currency.Name IS NOT NULL;";
		
		try {
			ResultSet rs =this.dbh.executeQuery(query);//jdbc
			if (rs.next()){
				String officialCurrency = rs.getString("Name");
				q.setCorrectAnswer(new Answer(officialCurrency));
				q.addPossibleAnswers(new Answer(officialCurrency));
				query = "SELECT DISTINCT Currency.Name " +
			               "FROM "+this.dbname+".Currency "+
			               "WHERE Currency.Name!='"+officialCurrency+"'"+
			               " and Currency.Name IS NOT NULL"+
			               " ORDER BY RAND()"+
			               " LIMIT 3;";
				rs =this.dbh.executeQuery(query);//jdbc
				int i =0;
				while (rs.next()){
					i++;
					String currency = rs.getString("Name");
					q.addPossibleAnswers(new Answer(currency));
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
	private void generateCapitalCityQuestion(){
		Question q = new Question("What is the capital city of "+countryName);
		String query = "SELECT City.Name " +
                   "FROM "+this.dbname+".Country, "+this.dbname+".City "+
                   "WHERE City.idCountry='"+countryId+"' and Country.idCapital=City.idCity"+
                   " and City.Name IS NOT NULL;";
		
		try {
			ResultSet rs =this.dbh.executeQuery(query);//jdbc
			if (rs.next()){
				String capital = rs.getString("Name");
				q.setCorrectAnswer(new Answer(capital));
				q.addPossibleAnswers(new Answer(capital));
				query = "SELECT DISTINCT City.Name " +
			               "FROM "+this.dbname+".City "+
			               "WHERE City.idCountry='"+countryId+"' and City.Name!='"+capital+
			               "and City.Name IS NOT NULL "+
			               "ORDER BY RAND()"+
			               "' LIMIT 3;";
				rs =this.dbh.executeQuery(query);//jdbc
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
	private void generatePrizeWinnerQuestions(String prizeName){
		String query ="SELECT Person.Name "+
					"FROM "+this.dbname+".Award, "+this.dbname+".AwardWinners, "+this.dbname+".Person, "+this.dbname+".Country_City "+
				"WHERE Country_City.idCountry ='"+countryId+"' and"+
					"Person.idPlaceOfBirth = Country_City.idCity and"+
				"Person.idPerson = AwardWinners.idPerson and"+
					"AwardWinners.idAward = Award.idAward and"+
					"Award.Name ='"+prizeName+"' and Person.Name IS NOT NULL;";
		
		ArrayList<String> winners = new ArrayList<String>();
		ArrayList<String> nonWinners = new ArrayList<String>();
		try {
			ResultSet rs =this.dbh.executeQuery(query);//jdbc
			int i = 0;
			while(rs.next()){
				i++;
				winners.add(rs.getString("Name"));
				if(i==2){
					break;
				}
			}
			if (i==0){
				return;
			}
			query ="SELECT Person.Name "+
					"FROM "+this.dbname+".Award, "+this.dbname+".AwardWinners, "+this.dbname+".Person, "+this.dbname+".Country_City "+
				"WHERE Country_City.idCountry ='"+countryId+"' and"+
					"Person.idPlaceOfBirth = Country_City.idCity and"+
				"Person.idPerson = AwardWinners.idPerson and"+
				"AwardWinners.idAward = Award.idAward and"+
				"Award.Name ='"+prizeName+"' and Person.Name IS NOT NULL;";
			rs =this.dbh.executeQuery(query);//jdbc
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
