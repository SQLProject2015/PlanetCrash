package GUI.Scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import GUI.GameGUI;
import Game.Game;

public class GameScene extends Scene{

	public GameScene(GameGUI gameGUI, Game game) {
		super(gameGUI, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component create() {
		JLayeredPane panel = new JLayeredPane();
		panel.setPreferredSize(new Dimension(GameGUI.WINDOW_WIDTH,GameGUI.WINDOW_HEIGHT));
		
		panel.setBackground(Color.GREEN);
		
		//Add background
		JPanel backdrop = emptyMainJPanel();
		backdrop.setBackground(game.getBackdrop());
		panel.add(backdrop, new Integer(0),0);
		
		//Add land
		JPanel land = emptyMainJPanel();
		land.setBackground(game.getLand());
		land.setBounds(0, 0, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT-200);
		panel.add(land, new Integer(0),0);
		
		//Add questions
		
		return panel;
	}

}
