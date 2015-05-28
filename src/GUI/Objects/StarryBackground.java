package GUI.Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import GUI.GameGUI;

public class StarryBackground extends JPanel{

	public StarryBackground() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.BLACK);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(GameGUI.WINDOW_WIDTH,GameGUI.WINDOW_HEIGHT);
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        // Draw Text
        g.drawString("This is my custom Panel!",(int)(Math.random()*100),(int)(Math.random()*200));
    }  
}
