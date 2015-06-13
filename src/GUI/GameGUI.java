package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import config.config;
import Database.ConnectionPool;
import GUI.Objects.Effects.Fader;
import GUI.Scenes.EditCountryScene;
import GUI.Scenes.LoadToYagoScene;
import GUI.Scenes.LoginScene;
import GUI.Scenes.Scene;
import GUI.Scenes.AddCountryScene;
import Game.Game;

public class GameGUI {

	public static final int WINDOW_WIDTH=800;
	public static final int WINDOW_HEIGHT=600;

	public static final String ASSETS = System.getProperty("user.dir")+"\\assets\\";

	public ConnectionPool mConnPool;

	protected Game game;

	public JFrame mainFrame;
	
	private JLayeredPane mJLPane;
	
	config config = new config();

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

		
		EditCountryScene mms = new EditCountryScene(this,game);//MainMenuScene();
		switchScene(mms);
		
//		
//		if (config.get_db_ready().equals("0")){
//			LoadToYagoScene mms = new LoadToYagoScene(this,game);//MainMenuScene();
//			switchScene(mms);	
//		}
//		else{
//			//Show main screen
//			LoginScene mms = new LoginScene(this,game);//MainMenuScene();
//			switchScene(mms);		
//		}
		
		

		
		
		//        mainFrame.setContentPane(mms.create());

//		fadeSwitchScene(mms);

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
	public void fadeSwitchScene(final Scene scene) {
		final long duration = 500;
		
		final Fader of = new Fader(duration, true);
		final Fader inf = new Fader(duration,false);
		
		final JPanel container = Scene.emptyMainJPanel();
		container.setOpaque(false);
		container.add(of);
		mJLPane.add(container,new Integer(1),0);
		
		new Thread(of).start();
		
		Timer t1 =new Timer((int)duration, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switchScene(scene);
				mJLPane.remove(container);

				final JPanel containerr = Scene.emptyMainJPanel();
				containerr.setOpaque(false);
				containerr.add(inf);
				mJLPane.add(containerr, new Integer(2),0);
				new Thread(inf).start();
				
				Timer t2 = new Timer((int)duration, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						mJLPane.remove(containerr);
					}
					
				});
				
				t2.setRepeats(false);
				t2.start();
			}
		});
		
		t1.setRepeats(false);
		t1.start();
	}

}
