package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Database.ConnectionPool;
import GUI.Objects.Effects.Faders;
import GUI.Objects.Effects.Faders.OutFader;
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
	
	private JLayeredPane mJLPane;

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

		fadeSwitchScene(mms);

		mainFrame.pack();

		mainFrame.setLocationRelativeTo(null); //middle of screen
		mainFrame.setVisible(true);
	}

	public void switchScene(Scene scene) {
		//mainFrame.removeAll(); //TODO this shiat doesn't work
		mJLPane = new JLayeredPane();
		mJLPane.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		
		JPanel jp = Scene.emptyMainJPanel();
		jp.add(scene.create());
		
		mJLPane.add(jp, new Integer(0),0);
		
		mainFrame.setContentPane(Scene.emptyMainJPanel());
		mainFrame.add(mJLPane);
		
		mainFrame.pack();
	}

	//Switches scenes with a fade effect
	public void fadeSwitchScene(Scene scene) {
		
		Faders.OutFader of = new Faders.OutFader(1000);
		JPanel shit = Scene.emptyMainJPanel();
		shit.setOpaque(false);
		shit.add(of);
		mJLPane.add(shit,new Integer(1),0);
		new Thread(of).start();
	}

}
