package GUI.Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.GameGUI;

public class JRoundedButton extends JRounded {
	String text;
	JLabel label;
	Font font;
	Color origBGC, origBRDC;

	MouseListener ml;
	
	public JRoundedButton(Font font, String text, int width, int height, int borderThickness) {
		super(width, height,borderThickness);

		//init font
		this.font = font;

		//init label
		label = new JLabel(text);
		label.setFont(font);
		label.setForeground(borderColor);
		//				label.setBounds(0, 0, 10, 10);
		add(label);

		this.text=text;

		label.setText(text);
		
		registerGraphicListener();
	}

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
		
		registerGraphicListener();
	}


	public void isButton(boolean isButton) {
		if(ml!=null)
			removeMouseListener(ml);
		if(isButton)
			registerGraphicListener();
	}
	
	public void setText(String text) {
		this.text=text;
	}

	public void setBorderColor(Color c) {
		super.setBorderColor(c);
		label.setForeground(borderColor);
	}	
	
	@Override
	public void setBackgroundColor(Color c) {
		super.setBackgroundColor(c);
	}
	
	public void setLabelFont(Font font) {
		this.label.setFont(font);
	}
	
	private void registerGraphicListener() {
		origBGC=getBackgroundColor();
		//origBRDC=getBorderColor();
		
		ml = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBackgroundColor(origBGC);
				if(label != null)
					label.setForeground(getBorderColor());
				repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackgroundColor(origBRDC);
				if(label != null)
					label.setForeground(origBGC);
				repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		
		this.addMouseListener(ml);
	}
}
