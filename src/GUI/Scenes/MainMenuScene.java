package GUI.Scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GUI.GameGUI;
import GUI.Objects.JImage;
import GUI.Objects.JRoundedButton;
import GUI.Objects.StarryBackground;

public class MainMenuScene extends Scene {

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

		return panel;
	}

}

