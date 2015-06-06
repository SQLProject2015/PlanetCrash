package GUI.Scenes;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GUI.GameGUI;


public abstract class Scene {
	public abstract Component create();
	

	protected JPanel emptyMainJPanel() {
		JPanel ret = new JPanel();
		ret.setSize(GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);
		ret.setPreferredSize(new Dimension(GameGUI.WINDOW_WIDTH,GameGUI.WINDOW_HEIGHT));
		ret.setBorder(new EmptyBorder(-5, -5, -5, -5));

		return ret;
	}
}
