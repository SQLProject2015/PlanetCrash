package GUI.Scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import GUI.GameGUI;
import GUI.Objects.JImage;
import GUI.Objects.JRoundedButton;
import GUI.Objects.StarryBackground;
import Game.Game;

public class MainMenuScene extends Scene {

	public MainMenuScene(GameGUI gameGUI, Game game) {
		super(gameGUI, game);
	}

	@Override
	public Component create() {
		JLayeredPane panel = new JLayeredPane();
		panel.setPreferredSize(new Dimension(GameGUI.WINDOW_WIDTH,GameGUI.WINDOW_HEIGHT));

		JPanel s = emptyMainJPanel();
		panel.add(s, new Integer(0),0);

		//Add background
		StarryBackground sb = new StarryBackground();
		s.add(sb);

		//Add logo
		JImage logo = new JImage(new File(GameGUI.ASSETS+"logo.png"));
		logo.setBounds((GameGUI.WINDOW_WIDTH-logo.getWidth())/2,80,logo.getWidth(),logo.getHeight());
		logo.setOpaque(false);
		panel.add(logo, new Integer(1), 0);

		//Add play button
		JRoundedButton playBtn = new JRoundedButton("Play", 220,60, 2);
		playBtn.setBounds((GameGUI.WINDOW_WIDTH-playBtn.getWidth())/2, 280, playBtn.getWidth(), playBtn.getHeight());
		panel.add(playBtn, new Integer(2), 0);

		//Add settings button
		JRoundedButton settingBtn = new JRoundedButton("Settings", 220,60, 2);
		settingBtn.setBounds((GameGUI.WINDOW_WIDTH-settingBtn.getWidth())/2, 370, settingBtn.getWidth(), settingBtn.getHeight());
		settingBtn.setBorderColor(Color.GREEN);
		panel.add(settingBtn, new Integer(3), 0);

		//Add quit button
		JRoundedButton quitBtn = new JRoundedButton("Quit", 220,60, 2);
		quitBtn.setBounds((GameGUI.WINDOW_WIDTH-quitBtn.getWidth())/2, 460, quitBtn.getWidth(), quitBtn.getHeight());
		quitBtn.setBorderColor(Color.decode("#bf00af"));
		panel.add(quitBtn, new Integer(3), 0);

		//add welcome message
		Font font = new Font(null, Font.PLAIN, 20);
		JTextField welcomeMsg = new JTextField("Welcome back, "+game.getUser().getName()+"!");
		welcomeMsg.setFont(font);
		welcomeMsg.setForeground(Color.CYAN);
		welcomeMsg.setBounds((GameGUI.WINDOW_WIDTH-(int)welcomeMsg.getPreferredSize().getWidth())/2, 240,
				(int)welcomeMsg.getPreferredSize().getWidth(), (int)welcomeMsg.getPreferredSize().getHeight());
		welcomeMsg.setOpaque(false);
		welcomeMsg.setBorder(BorderFactory.createEmptyBorder());
		panel.add(welcomeMsg, new Integer(3),0);
		
		//register listeners
		playBtn.addMouseListener(new MainMenuMouseListener(MainMenuMouseListener.PLAY));
		settingBtn.addMouseListener(new MainMenuMouseListener(MainMenuMouseListener.SETTINGS));
		quitBtn.addMouseListener(new MainMenuMouseListener(MainMenuMouseListener.QUIT));
		
		return panel;
	}

	
	class MainMenuMouseListener implements MouseListener {
		public static final int PLAY=0,SETTINGS=1,QUIT=2;
		int mode;
		public MainMenuMouseListener(int mode) {
			this.mode=mode;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			switch(mode) {
			case PLAY:
				gameGUI.fadeSwitchScene(new DifficultySelectScene(gameGUI, game));
				break;
			case SETTINGS:
				break;
			case QUIT:
				break;
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}

