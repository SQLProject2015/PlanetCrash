package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import GUI.Objects.JImage;
import GUI.Objects.JRoundedButton;
import GUI.Objects.LayeredPaneLayout;
import GUI.Objects.StarryBackground;
import GUI.Scenes.LoginScene;
import GUI.Scenes.MainMenuScene;
import GUI.Scenes.Scene;

public class GameGUI {

	public static final int WINDOW_WIDTH=800;
	public static final int WINDOW_HEIGHT=600;
	
	public static final String ASSETS = System.getProperty("user.dir")+"\\assets\\";
	
	JFrame mainFrame;
	
	public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private void createAndShowGUI() {
        mainFrame = new JFrame("Planet Crash");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        
        //Show main screen
        LoginScene mms = new LoginScene();//MainMenuScene();
        switchScene(mms);
        //        mainFrame.setContentPane(mms.create());
       
        mainFrame.setBackground(Color.red);
        
        mainFrame.pack();
        
        mainFrame.setLocationRelativeTo(null); //middle of screen
        mainFrame.setVisible(true);
    }
    
    public void switchScene(Scene scene) {
    	//mainFrame.removeAll(); //TODO this shiat doesn't work
    	mainFrame.add(scene.create());
    }
    
	
}
