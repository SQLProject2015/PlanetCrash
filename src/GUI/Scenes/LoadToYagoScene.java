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
import java.util.concurrent.TimeUnit;

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
	
	int per = 0;

	
	//JRoundedButton perBtn = new JRoundedButton("percentage", 500, 60, 2);
	JRoundedButton importBtn = new JRoundedButton("Import from Yago", 500, 60, 2);
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
		importBtn.setBorderColor(Color.green);
		importBtn.setBounds((GameGUI.WINDOW_WIDTH-importBtn.getWidth())/2, 375, importBtn.getWidth(), importBtn.getHeight());
		panel.add(importBtn, new Integer(2), 2);
		
		//Add login button
		//JRoundedButton perBtn = new JRoundedButton("Loading...", 500, 60, 2);
//		perBtn.setBorderColor(Color.green);
//		perBtn.setBounds((GameGUI.WINDOW_WIDTH-perBtn.getWidth())/2, 450, perBtn.getWidth(), perBtn.getHeight());
//		panel.add(perBtn, new Integer(2), 2);
								
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
			final DatabaseHandler dbh = new DatabaseHandler(gameGUI.mConnPool);
			final config config = new config();
			
			new Thread(new Runnable(){
				public void run(){
					try {
						Importer i = new Importer(dbh, config);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					MainMenuScene mms = new MainMenuScene(gameGUI,game);
					gameGUI.switchScene(mms);		
					gameGUI.fadeSwitchScene(mms);
				}
				
				}).start();
			
			per = 0;			
			final Timer t2 = new Timer(1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
									
					if (Importer.uploading_finished != -1){
					   
					    int temp = Math.round((Importer.uploading_finished * 100.0f) / 180000);		
					    if (temp!= per){
					    	per = temp;
					    	System.out.println(String.format("finished %d percenteges", (per)));
					    	importBtn.setText(String.format("Uploading... %d%%", (per)));
					    }												    												   
					}
					else if(Importer.uploading_finished==-1){
					    	((Timer)e.getSource()).stop();
					}
				}
			});
			
						
			Timer t = new Timer(1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
									
					if (Importer.parsing_finished != -1){
					   
					    int temp = Math.round((Importer.parsing_finished * 100.0f) / config.get_files_size());		
					    if (temp!= per){
					    	per = temp;
					    	System.out.println(String.format("finished %d percenteges", (per)));
					    	importBtn.setText(String.format("Parsing... %d%%", (per)));
					    	//perBtn.setText(String.format("%d%%", (per)));
					    }												    												   
					}
					else if(Importer.parsing_finished==-1){
					    	((Timer)e.getSource()).stop();
					    	per = 0;
					    	importBtn.setText("Uploading...");
					    	t2.start();
					}
				}
			});
			
			importBtn.setText("Parsing...");
			t.start();

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
