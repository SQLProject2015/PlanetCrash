package PlanetCrash.core;
import PlanetCrash.db.ConnectionPool;
import PlanetCrash.ui.GameGUI;


public class TestMain {

	public static void main(String[] args) {
		ConnectionPool mConnPool = new ConnectionPool();
		
		//Game GUI
		GameGUI gg = new GameGUI(mConnPool);
		gg.start();

		String username;
		System.out.println("Enter username");

		//Init connection pool
		mConnPool.init();
	}
}
