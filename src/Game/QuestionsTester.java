package Game;

import Database.ConnectionPool;
import Database.DatabaseHandler;

public class QuestionsTester {
	public static void main(String[] args){
		ConnectionPool mConnPool = new ConnectionPool();

		DatabaseHandler dbh = new DatabaseHandler(mConnPool);
		QuestionsGenerator qg = new QuestionsGenerator("Israel",dbh,"DbMysql14");
		int i;
		while(true){
			i=0;
		}
	}
}
