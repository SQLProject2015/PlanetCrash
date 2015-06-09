package GUI.Objects;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;

public class JRoundedEditText extends JRounded{
	JTextField textField;
	Font font; 

	public JRoundedEditText(Font font, String text, int width, int height, int borderThickness) {
		super(width,height,borderThickness);
		
		this.font=font;
		
		initTextField(text);
	}

	public JRoundedEditText(String text, int width, int height, int borderThickness) {
		super(width,height,borderThickness);

		//init font
		font = new Font(null, Font.BOLD, 36);

		initTextField(text);
	}

	public String getText() {
		return textField.getText();
	}

	public void setText(String text) {
		textField.setText(text);
	}

	private void initTextField(String text) {
		//Init textField
		textField = new JTextField(text);
		textField.setFont(font);
		textField.setBackground(backgroundColor);
		textField.setForeground(borderColor);
		textField.setSize(width-20, height-10);
		textField.setPreferredSize(new Dimension(width-20,height-10));
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBounds(0, 0, width, height);

		add(textField);
	}
}
