package GUI.Scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Database.DatabaseHandler;
import Database.Users.User;
import Database.Users.UserException;
import Database.Users.UserHandler;
import GUI.GameGUI;
import GUI.Objects.JImage;
import GUI.Objects.JRoundedButton;
import GUI.Objects.JRoundedEditText;
import GUI.Objects.StarryBackground;
import Game.Game;

public class LoginScene extends Scene{
	
	public LoginScene(GameGUI gameGUI, Game game) {
		super(gameGUI, game);
	}

	JRoundedEditText usernameField;
	JRoundedEditText passwordField;

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
		usernameField = new JRoundedEditText(userpass,"", 220, userpass.getSize()+20, 2, false);
		
		usernameLabel.isButton(false);
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
		passwordField = new JRoundedEditText(userpass,"", 220, userpass.getSize()+20, 2, true);
	
		passwordLabel.isButton(false);
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
		
		//Register action listeners
		LoginMouseListener lml = new LoginMouseListener();
		loginBtn.addMouseListener(lml);

		return panel;
	}
	
	//Confirms the user
	private User confirmUser(String username, String pass) {
		DatabaseHandler dbh = new DatabaseHandler(gameGUI.mConnPool);
		User user=null;
		
		try {
			user = UserHandler.validate_user(username, pass, dbh);
		} catch (UserException | SQLException e) {
			JOptionPane.showMessageDialog(gameGUI.mainFrame, e.getMessage());
		}
		try {
			dbh.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	class LoginMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(usernameField==null||passwordField==null)
				return;
			User user = confirmUser(usernameField.getText(), passwordField.getText());
			
			if(user==null)
				return;
			
			game.setUser(user);
			
			MainMenuScene mms = new MainMenuScene(gameGUI,game);
//			gameGUI.switchScene(mms);		
			gameGUI.fadeSwitchScene(mms);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

	
}
