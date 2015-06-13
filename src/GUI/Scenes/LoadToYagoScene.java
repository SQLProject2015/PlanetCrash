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

import com.sun.org.apache.xml.internal.security.Init;

import config.config;
import Database.DatabaseHandler;
import Database.Updates.Importer;
import Database.Users.User;
import Database.Users.UserException;
import Database.Users.UserHandler;
import GUI.GameGUI;
import GUI.Objects.JImage;
import GUI.Objects.JRoundedButton;
import GUI.Objects.JRoundedEditText;
import GUI.Objects.StarryBackground;
import Game.Game;

public class LoadToYagoScene extends Scene{
	
	public LoadToYagoScene(GameGUI gameGUI, Game game) {
		super(gameGUI, game);
	}
	
	Font userpass = new Font(null, Font.BOLD, 14);
	JRoundedEditText passwordField;

	
	JRoundedButton perBtn = new JRoundedButton("percentage", 220, 60, 2);
	JRoundedEditText usernameField = new JRoundedEditText(userpass,"Test", 220, 60, 2, false);
	
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
		
		

		
		//Add login button
		JRoundedButton importBtn = new JRoundedButton("Import from Yago", 500, 60, 2);
		importBtn.setBorderColor(Color.green);
		importBtn.setBounds((GameGUI.WINDOW_WIDTH-importBtn.getWidth())/2, 375, importBtn.getWidth(), importBtn.getHeight());
		panel.add(importBtn, new Integer(2), 2);
	
				
		//Register action listeners
		ImportListener lml = new ImportListener();
		importBtn.addMouseListener(lml);

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
	
	class ImportListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			DatabaseHandler dbh = new DatabaseHandler(gameGUI.mConnPool);
			config config = new config();
			
			new Thread(new Runnable(){
				public void run(){
					Importer i = new Importer(dbh, config);	
					MainMenuScene mms = new MainMenuScene(gameGUI,game);
					gameGUI.switchScene(mms);		
					gameGUI.fadeSwitchScene(mms);
				}
				
				}).start();
			
			
			new Thread(new Runnable(){
				public void run(){
					int per = 0;
					
					while (per < 99){
						try {
						    Thread.sleep(1000);  
						    int temp = Math.round((Importer.finished * 100.0f) / 180000);		
						    if (temp!= per){
						    	per = temp;
						    	System.out.println(String.format("finished %d percenteges", (per)));
						    }
						    
						    
						    //1000 milliseconds is one second.
						} catch(InterruptedException ex) {
						    Thread.currentThread().interrupt();
						}
					}
				}
				
				}).start();
			
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
