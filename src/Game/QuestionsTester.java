package Game;

import config.config;
import Database.ConnectionPool;
import Database.DatabaseHandler;
import Database.Updates.ManualUpdates;

public class QuestionsTester {
	public static void main(String[] args){
//		config con = new config();
//		con.set_db_name("blabla");
		ConnectionPool mConnPool = new ConnectionPool();
		mConnPool.init();
		DatabaseHandler dbh = new DatabaseHandler(mConnPool);
		ManualUpdates.backupManualUpdates(dbh,"DbMysql14");
//		ManualUpdates.deleteAllYagoData(dbh, "DbMysql14");
//		QuestionsGenerator qg = new QuestionsGenerator("Israel",dbh,"DbMysql14");
//		QuestionsGenerator qg = GameUtils.generateCountry(1, "DbMysql14", dbh, 3);
//		for(int i=0 ;i<qg.getPossibleQuestions().size();i++){
//			qg.getPossibleQuestions().get(i).printQuestion();
//		}
	}
}
