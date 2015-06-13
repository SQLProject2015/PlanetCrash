package GUI.Scenes;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLayeredPane;

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
		
		return panel;
	}

}
