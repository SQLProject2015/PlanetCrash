package GUI.Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.GameGUI;

public class JRoundedButton extends JPanel{
	Color borderColor;
	Color backgroundColor;
	int width,height, borderThickness, arc=30;
	
	String text;
	JLabel label;
	Font font;
	
	public JRoundedButton(String text,int width, int height, int borderThickness) {
		this(width,height,borderThickness);
		this.text=text;
		
		label.setText(text);
	}
	
	public JRoundedButton(int width, int height, int borderThickness) {
		this(Color.orange, Color.decode("#3B5998"), width,height,borderThickness);
	}
	
	public JRoundedButton(Color borderColor, Color backgroundColor, int width, int height, int borderThickness) {
		super();
		
		this.borderColor=borderColor;
		this.backgroundColor=backgroundColor;
		this.width=width;
		this.height=height;
		this.borderThickness=borderThickness;
		
		//init font
		font = new Font(null, Font.BOLD, 36);
		
		//init label
		label = new JLabel(text);
		label.setFont(font);
		label.setForeground(borderColor);
//		label.setBounds(0, 0, 10, 10);
		add(label);
		
		setOpaque(false);
		setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color pastC = g.getColor();
		g.setColor(borderColor);
		g.fillRoundRect(0, 0, width, height, arc, arc);
		g.setColor(backgroundColor);
		g.fillRoundRect(borderThickness, borderThickness, width-(borderThickness*2), height-(borderThickness*2), arc, arc);
		g.setColor(borderColor);
		//label.paint(g);
		g.setColor(pastC);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width,height);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setText(String text) {
		this.text=text;
	}
	
	public void setBorderColor(Color c) {
		this.borderColor=c;
		label.setForeground(borderColor);
	}
	
	public void setBackgroundColor(Color c) {
		this.backgroundColor=c;
	}
}
