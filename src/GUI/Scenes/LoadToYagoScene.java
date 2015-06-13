package GUI.Scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
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
		
		//Add login button
		JRoundedButton perBtn = new JRoundedButton("Loading...", 500, 60, 2);
		perBtn.setBorderColor(Color.green);
		perBtn.setBounds((GameGUI.WINDOW_WIDTH-perBtn.getWidth())/2, 450, perBtn.getWidth(), perBtn.getHeight());
		panel.add(perBtn, new Integer(2), 2);
	
				
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
						
			Timer t = new Timer(1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int per = 0;
					System.out.println("Started parsing");
					
					if (Importer.parsing_finished != -1){

						    if (Importer.parsing_finished==-1){
						    	((Timer)e.getSource()).stop();
						    }
						    int temp = Math.round((Importer.parsing_finished * 100.0f) / config.get_files_size());		
						    if (temp!= per){
						    	per = temp;
						    	System.out.println(String.format("finished %d percenteges", (per)));
						    	perBtn.setText(String.format("finished %d percenteges", (per)));
						    }
					}
				
					
					System.out.println("Finished parsing");
				}
			});
			
			
			Timer t2 = new Timer(1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int per = 0;
					System.out.println("Started uploading");
					per=0;
					
					if (Importer.uploading_finished != -1){
					   
					    if (Importer.uploading_finished==-1){
					    	((Timer)e.getSource()).stop();
					    }
					    int temp = Math.round((Importer.uploading_finished * 100.0f) / 180000);		
					    if (temp!= per){
					    	per = temp;
					    	System.out.println(String.format("finished %d percenteges", (per)));
					    	perBtn.setText(String.format("finished %d percenteges", (per)));
					    }												    												   
					}
				
					
					System.out.println("Finished parsing");
				}
			});
					
			
			

			
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
