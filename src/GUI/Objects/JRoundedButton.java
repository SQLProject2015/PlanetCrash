package GUI.Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.GameGUI;

public class JRoundedButton extends JRounded {
	String text;
	JLabel label;
	Font font;

	public JRoundedButton(String text,int width, int height, int borderThickness) {
		super(width,height,borderThickness);

		//init font
		font = new Font(null, Font.BOLD, 36);

		//init label
		label = new JLabel(text);
		label.setFont(font);
		label.setForeground(borderColor);
		//				label.setBounds(0, 0, 10, 10);
		add(label);

		this.text=text;

		label.setText(text);
	}


	public void setText(String text) {
		this.text=text;
	}

	public void setBorderColor(Color c) {
		this.borderColor=c;
		label.setForeground(borderColor);
	}	
}
