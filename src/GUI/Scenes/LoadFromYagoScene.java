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

import config.Config;
import Database.DatabaseHandler;
import Database.Updates.Importer;

import Database.Updates.ReloadYago;

import GUI.GameGUI;
import GUI.Objects.JImage;
import GUI.Objects.JRoundedButton;
import GUI.Objects.JRoundedEditText;
import GUI.Objects.StarryBackground;

import Game.Game;
import Game.GameUtils;

public class LoadFromYagoScene extends Scene{
	
	public static boolean backDisabled=false;
	JRoundedButton backBtn = new JRoundedButton("Back", 100, 60, 2);
	Config config = GameUtils.getConfig();
	
	public LoadFromYagoScene(GameGUI gameGUI, Game game) {
		super(gameGUI, game);
	}
	
	Font userpass = new Font(null, Font.BOLD, 14);
	JRoundedEditText passwordField;
	
	int per = 0;
	
	boolean isFirstLoad = true;

	
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
		
		if (config.get_db_ready().equals("1")){
			backBtn.setBorderColor(Color.green);
			backBtn.setBounds(30, 500, backBtn.getWidth(), backBtn.getHeight());
			panel.add(backBtn, new Integer(2), 2);
			isFirstLoad = false;
		}
		
		//Add login button
		//JRoundedButton perBtn = new JRoundedButton("Loading...", 500, 60, 2);
//		perBtn.setBorderColor(Color.green);
//		perBtn.setBounds((GameGUI.WINDOW_WIDTH-perBtn.getWidth())/2, 450, perBtn.getWidth(), perBtn.getHeight());
//		panel.add(perBtn, new Integer(2), 2);
								
		//Register action listeners		
		importBtn.addMouseListener(new MainListener(MainListener.IMPORT));
		backBtn.addMouseListener(new MainListener(MainListener.BACK));
		
//		if (isFirstLoad){
//			JOptionPane.showMessageDialog(gameGUI.mainFrame, "Seems like your database is not ready, you should import YAGO to start playing");
//		}
		
		return panel;
	}
	
	class MainListener implements MouseListener {
		
		public static final int BACK=0,IMPORT=1;

		int mode;
		public MainListener(int mode) {
			this.mode=mode;
		}	

		@Override
		public void mouseClicked(MouseEvent arg0) {
			switch(mode) {
			case BACK:
				if (backDisabled){
					break;
				}
				SettingsScene mms = new SettingsScene(gameGUI,game);	
				gameGUI.fadeSwitchScene(mms);		
				break;
			case IMPORT:
				backDisabled=true;
				final DatabaseHandler dbh = new DatabaseHandler(gameGUI.mConnPool);
				final Config config = GameUtils.getConfig();
				
				importBtn.removeMouseListener(this);
				importBtn.isButton(false);
				backBtn.removeMouseListener(this);
				backBtn.isButton(false);
				
		
				
				new Thread(new Runnable(){
					public void run(){
						try {
							if (isFirstLoad){
								Importer i = new Importer(dbh, config);
							}
							else{
								ReloadYago.updateFromYago(gameGUI.mConnPool, config);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						JOptionPane.showMessageDialog(gameGUI.mainFrame, "Database loaded successfully!");
						
						if (isFirstLoad){
							LoginScene mms = new LoginScene(gameGUI,game);		
							gameGUI.fadeSwitchScene(mms);
						}
						else{
							MainMenuScene mms = new MainMenuScene(gameGUI,game);		
							gameGUI.fadeSwitchScene(mms);						
						}
					}
					
					}).start();
				
				per = 0;			
				final Timer t2 = new Timer(1000, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
										
						if (Importer.uploading_finished != -1){
						   
						    int temp = Math.round((Importer.uploading_finished * 100.0f) / 190200);		
						    if (temp!= per){
						    	per = temp;
						    	System.out.println(String.format("finished %d percenteges", (per)));
						    	importBtn.setText(String.format("Uploading... %d%%", (per)));
						    }												    												   
						}
						else if(Importer.uploading_finished==-1){
						    	((Timer)e.getSource()).stop();
						    	importBtn.setText("Uploading... 100%");
						}
					}
				});
				
							
				Timer t = new Timer(1000, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
										
						if (Importer.parsing_finished != -1){
						   importBtn.isButton(false);
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
				
				break;
				}	
				
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
