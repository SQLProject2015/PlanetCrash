package GUI;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Database.ConnectionPool;
import GUI.Scenes.LoginScene;
import GUI.Scenes.Scene;
import Game.Game;

public class GameGUI {

	public static final int WINDOW_WIDTH=800;
	public static final int WINDOW_HEIGHT=600;
	
	public static final String ASSETS = System.getProperty("user.dir")+"\\assets\\";
	
	public ConnectionPool mConnPool;
	
	protected Game game;
	
	public JFrame mainFrame;
	
	public GameGUI(ConnectionPool cpool) {
		this.mConnPool=cpool;
		this.game=new Game();
	}
	
	public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private void createAndShowGUI() {
        mainFrame = new JFrame("Planet Crash");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        
        //Show main screen
        LoginScene mms = new LoginScene(this,game);//MainMenuScene();
        switchScene(mms);
        //        mainFrame.setContentPane(mms.create());
       
        mainFrame.setBackground(Color.red);
        
        mainFrame.pack();
        
        mainFrame.setLocationRelativeTo(null); //middle of screen
        mainFrame.setVisible(true);
    }
    
    public void switchScene(Scene scene) {
    	//mainFrame.removeAll(); //TODO this shiat doesn't work
    	mainFrame.setContentPane(Scene.emptyMainJPanel());
    	mainFrame.add(scene.create());
    	mainFrame.pack();
    }
    
	
}
