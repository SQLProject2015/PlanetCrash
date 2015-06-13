package Game;

import Database.ConnectionPool;
import Database.DatabaseHandler;
import Database.Updates.ManualUpdates;

public class QuestionsTester {
	public static void main(String[] args){
		ConnectionPool mConnPool = new ConnectionPool();
		mConnPool.init();
		DatabaseHandler dbh = new DatabaseHandler(mConnPool);
//		ManualUpdates.deleteAllYagoData(dbh, "DbMysql14");
		QuestionsGenerator qg = new QuestionsGenerator("Israel",dbh,"DbMysql14");
		for(int i=0 ;i<qg.getPossibleQuestions().size();i++){
			qg.getPossibleQuestions().get(i).printQuestion();
		}
	}
}
