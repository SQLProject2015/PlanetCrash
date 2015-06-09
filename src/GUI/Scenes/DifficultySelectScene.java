package GUI.Scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GUI.GameGUI;
import GUI.Objects.JImage;
import GUI.Objects.JRoundedButton;
import GUI.Objects.StarryBackground;
import Game.Game;

public class DifficultySelectScene extends Scene{

	public DifficultySelectScene(GameGUI gameGUI, Game game) {
		super(gameGUI, game);
	}

	@Override
	public Component create() {
		JLayeredPane panel = new JLayeredPane();
		panel.setPreferredSize(new Dimension(GameGUI.WINDOW_WIDTH,GameGUI.WINDOW_HEIGHT));

		JPanel s = emptyMainJPanel();
		panel.add(s, new Integer(0),0);

		//define font
		Font font = new Font(null, Font.PLAIN, 20);
		
		//Add background
		StarryBackground sb = new StarryBackground();
		s.add(sb);

		//Add logo
		JImage logo = new JImage(new File(GameGUI.ASSETS+"logo.png"));
		logo.setBounds((GameGUI.WINDOW_WIDTH-logo.getWidth())/2,80,logo.getWidth(),logo.getHeight());
		logo.setOpaque(false);
		panel.add(logo, new Integer(1), 0);

		//Add play button
		JRoundedButton easyBtn = new JRoundedButton("Land", 220,60, 2);
		easyBtn.setBounds((GameGUI.WINDOW_WIDTH-easyBtn.getWidth())/2, 280, easyBtn.getWidth(), easyBtn.getHeight());
		easyBtn.setBorderColor(Color.green);
//		easyBtn.setLabelFont(font);
		panel.add(easyBtn, new Integer(2), 0);

		//Add settings button
		JRoundedButton mediumBtn = new JRoundedButton("Bump", 220,60, 2);
		mediumBtn.setBounds((GameGUI.WINDOW_WIDTH-mediumBtn.getWidth())/2, 370, mediumBtn.getWidth(), mediumBtn.getHeight());
		mediumBtn.setBorderColor(Color.yellow);
//		mediumBtn.setLabelFont(font);
		panel.add(mediumBtn, new Integer(3), 0);

		//Add quit button
		JRoundedButton hardBtn = new JRoundedButton("Crash", 220,60, 2);
		hardBtn.setBounds((GameGUI.WINDOW_WIDTH-hardBtn.getWidth())/2, 460, hardBtn.getWidth(), hardBtn.getHeight());
		hardBtn.setBorderColor(Color.red);
//		hardBtn.setLabelFont(font);
		panel.add(hardBtn, new Integer(3), 0);

		//add welcome message
		JTextField welcomeMsg = new JTextField("Please choose difficulty:");
		welcomeMsg.setFont(font);
		welcomeMsg.setForeground(Color.CYAN);
		welcomeMsg.setBounds((GameGUI.WINDOW_WIDTH-(int)welcomeMsg.getPreferredSize().getWidth())/2, 240,
				(int)welcomeMsg.getPreferredSize().getWidth(), (int)welcomeMsg.getPreferredSize().getHeight());
		welcomeMsg.setOpaque(false);
		welcomeMsg.setBorder(BorderFactory.createEmptyBorder());
		panel.add(welcomeMsg, new Integer(3),0);

		return panel;
	}

}
