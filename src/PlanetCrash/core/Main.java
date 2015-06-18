package PlanetCrash.core;

import PlanetCrash.db.ConnectionPool;
import PlanetCrash.db.DatabaseHandler;
import PlanetCrash.ui.GameGUI;



public class Main {

	public static ConnectionPool mConnPool;


	
	public static void main(String args[]) {
		
		
		mConnPool = new ConnectionPool();
		//Init connection pool
		mConnPool.init();

		DatabaseHandler dbh = new DatabaseHandler(mConnPool);
		dbh.set_db_state();

		
		
		//Game GUI
		GameGUI gg = new GameGUI(mConnPool);
		gg.start();

		
		//Init init = new Init(dbh, properties);

//		try{
//			ManualUpdater.add_Country("China", "Asia", "Chinese yuan", "Chinese language", "Beijing", 1000000000, dbh);
//			ManualUpdater.add_Person("tomer baruch", 1989, 0, "Haifa","Musician", dbh);
//
//		}
//		catch(Exception ex){
//			System.out.println(ex.getMessage());
//		}
//		
//		
//		try{
//			UserHandler.add_new_user("osher", "1234", dbh);
//		}catch(Exception ex){
//		}


	}


}
