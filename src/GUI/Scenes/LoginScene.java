package GUI.Scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Database.DatabaseHandler;
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
		
		//login x,y
		int loginy = 250;
		
		//init user/pass font
		Font userpass = new Font(null, Font.BOLD, 14);
		
		//Add username field
		JPanel usernamePanel = new JPanel();
		JRoundedButton usernameLabel = new JRoundedButton(userpass,"Username:", 100, userpass.getSize()+20, 2);
		JRoundedEditText usernameField = new JRoundedEditText(userpass,"", 220, userpass.getSize()+20, 2, false);
		
		usernameLabel.setBorderColor(Color.cyan);
		usernameField.setBorderColor(Color.cyan);
		
		usernamePanel.setPreferredSize(new Dimension(usernameLabel.getWidth()+usernameField.getWidth()+50, usernameField.getHeight()));
		usernamePanel.setBounds((GameGUI.WINDOW_WIDTH-(int)usernamePanel.getPreferredSize().getWidth())/2,
				loginy, usernameLabel.getWidth()+usernameField.getWidth()+50, usernameField.getHeight());
		usernamePanel.setOpaque(false);
		usernamePanel.setBorder(new EmptyBorder(-5, -25, -5, -5));
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);
		panel.add(usernamePanel, new Integer(2),0);
		
		//Add password field
		JPanel passwordPanel = new JPanel();
		JRoundedButton passwordLabel = new JRoundedButton(userpass,"Password:", 100, userpass.getSize()+20, 2);
		JRoundedEditText passwordField = new JRoundedEditText(userpass,"", 220, userpass.getSize()+20, 2, true);
	
		passwordLabel.setBorderColor(Color.cyan);
		passwordField.setBorderColor(Color.cyan);
		
		passwordPanel.setPreferredSize(new Dimension(usernameLabel.getWidth()+usernameField.getWidth()+50, usernameField.getHeight()));
		passwordPanel.setBounds((GameGUI.WINDOW_WIDTH-(int)passwordPanel.getPreferredSize().getWidth())/2,
				loginy+10+(int)passwordPanel.getPreferredSize().getHeight(), usernameLabel.getWidth()+usernameField.getWidth()+50, usernameField.getHeight());
		passwordPanel.setOpaque(false);
		passwordPanel.setBorder(new EmptyBorder(-5, -25, -5, -5));
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		panel.add(passwordPanel, new Integer(2),1);
		
		//Add login button
		JRoundedButton loginBtn = new JRoundedButton("Login", 220, 60, 2);
		loginBtn.setBorderColor(Color.green);
		loginBtn.setBounds((GameGUI.WINDOW_WIDTH-loginBtn.getWidth())/2, 375, loginBtn.getWidth(), loginBtn.getHeight());
		panel.add(loginBtn, new Integer(2), 2);
		
		//Add create user button
		JRoundedButton createBtn = new JRoundedButton("Register", 220, 60, 2);
		createBtn.setBounds((GameGUI.WINDOW_WIDTH-loginBtn.getWidth())/2, 375+60+20, createBtn.getWidth(), createBtn.getHeight());
		panel.add(createBtn, new Integer(2), 3);
		

		return panel;
	}
	
	//Confirms the user
	private void confirmUser(String user, String pass) throws SQLException {
		DatabaseHandler dbh = new DatabaseHandler(GameGUI.mConnPool);
		
		
		
		dbh.close();
	}

}
