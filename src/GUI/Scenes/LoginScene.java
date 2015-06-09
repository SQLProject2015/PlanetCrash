package GUI.Scenes;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import GUI.GameGUI;
import GUI.Objects.JImage;
import GUI.Objects.JRoundedButton;
import GUI.Objects.JRoundedEditText;
import GUI.Objects.StarryBackground;

public class LoginScene extends Scene{

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
		
		//init user/pass font
		Font userpass = new Font(null, Font.BOLD, 14);
		
		//Add username field
		JRoundedEditText usernameField = new JRoundedEditText(userpass,"Username", 220, userpass.getSize()+20, 2);
		usernameField.setBounds((GameGUI.WINDOW_WIDTH-usernameField.getWidth())/5, 280, usernameField.getWidth(), usernameField.getHeight());
		panel.add(usernameField, new Integer(2),0);
		
		//Add password field
		JRoundedEditText passwordField = new JRoundedEditText(userpass,"Password", 220, userpass.getSize()+20, 2);
		passwordField.setBounds((GameGUI.WINDOW_WIDTH-passwordField.getWidth())/5, 320, passwordField.getWidth(), passwordField.getHeight());
		panel.add(passwordField, new Integer(2),1);
		
		//Add login button
		JRoundedButton loginBtn = new JRoundedButton("Login", 220, 60, 2);
		loginBtn.setBounds((GameGUI.WINDOW_WIDTH-loginBtn.getWidth())/5, 365, loginBtn.getWidth(), loginBtn.getHeight());
		panel.add(loginBtn, new Integer(2), 2);
		
		//Add create user button
		JRoundedButton createBtn = new JRoundedButton("Register", 220, 60, 2);
		createBtn.setBounds(4*(GameGUI.WINDOW_WIDTH-createBtn.getWidth())/5, 280, createBtn.getWidth(), createBtn.getHeight());
		panel.add(createBtn, new Integer(2), 3);
		

		return panel;
	}

}
