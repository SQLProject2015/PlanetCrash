package GUI.Scenes;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import GUI.GameGUI;
import Game.Game;

public class PlanetScene extends Scene {

	public PlanetScene(GameGUI gameGUI, Game game) {
		super(gameGUI, game);
	}

	@Override
	public Component create() {
		JLayeredPane panel = new JLayeredPane();
		panel.setPreferredSize(new Dimension(GameGUI.WINDOW_WIDTH,GameGUI.WINDOW_HEIGHT));

		JPanel s = emptyMainJPanel();
		s.setLayout(null);
		panel.add(s, new Integer(0),0);
		
		
		
		return panel;
	}
	

}
