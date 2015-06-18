package PlanetCrash.core.Game;

import java.util.LinkedHashMap;

import PlanetCrash.core.config.Config;
import PlanetCrash.db.ConnectionPool;
import PlanetCrash.db.DatabaseHandler;


public class QuestionsTester {
	public static void main(String[] args){
		Config con = GameUtils.getConfig();
//		con.set_db_name("blabla");
		ConnectionPool mConnPool = new ConnectionPool();
		mConnPool.init();
		LinkedHashMap<String,Integer> l =GameUtils.getHighScores(mConnPool, con);
//		DatabaseHandler dbh = new DatabaseHandler(mConnPool);
//		System.out.println(con.get_db_ready());
//		con.set_db_ready("1");
//		config con2 = new config();
//		System.out.println(con2.get_db_ready());
//		System.out.println(ManualUpdates.getIdFromDB("Language","idLanguage","Marshallese language",dbh));
//		ManualUpdates.backupManualUpdates(dbh,"DbMysql14");
//		ManualUpdates.deleteAllYagoData(dbh, "DbMysql14");
//		QuestionsGenerator qg = new QuestionsGenerator("Israel",dbh,"DbMysql14");
//		QuestionsGenerator qg = GameUtils.generateCountry(1, "DbMysql14", dbh, 3);
//		for(int i=0 ;i<qg.getPossibleQuestions().size();i++){
//			qg.getPossibleQuestions().get(i).printQuestion();
//		}
	}
}
