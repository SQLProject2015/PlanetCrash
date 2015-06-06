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
        mainFrame.add(displayMainScreen());
       // mainFrame.setContentPane(displayMainScreen());
       
        mainFrame.setBackground(Color.red);
        
        mainFrame.pack();
        
        mainFrame.setLocationRelativeTo(null); //middle of screen
        mainFrame.setVisible(true);
    }
    
    private JLayeredPane displayMainScreen() {
    	JLayeredPane panel = new JLayeredPane();
    	panel.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
    	
    	JPanel s = emptyMainJPanel();
    	panel.add(s, new Integer(0),0);
    	
    	//Add background
        StarryBackground sb = new StarryBackground();
        s.add(sb);
        
        //Add logo
        JImage logo = new JImage(new File(ASSETS+"logo.png"));
        logo.setBounds((WINDOW_WIDTH-logo.getWidth())/2,80,logo.getWidth(),logo.getHeight());
        logo.setOpaque(false);
        panel.add(logo, new Integer(1), 0);
        
        //Add play button
        JRoundedButton playBtn = new JRoundedButton("Play", 220,60, 2);
        playBtn.setBounds((WINDOW_WIDTH-playBtn.getWidth())/2, 280, playBtn.getWidth(), playBtn.getHeight());
        panel.add(playBtn, new Integer(2), 0);
        
        //Add settings button
        JRoundedButton settingBtn = new JRoundedButton("Settings", 220,60, 2);
        settingBtn.setBounds((WINDOW_WIDTH-settingBtn.getWidth())/2, 370, settingBtn.getWidth(), settingBtn.getHeight());
        settingBtn.setBorderColor(Color.GREEN);
        panel.add(settingBtn, new Integer(3), 0);
        
        //Add quit button
        JRoundedButton quitBtn = new JRoundedButton("Quit", 220,60, 2);
        quitBtn.setBounds((WINDOW_WIDTH-quitBtn.getWidth())/2, 460, quitBtn.getWidth(), quitBtn.getHeight());
        quitBtn.setBorderColor(Color.decode("#bf00af"));
        panel.add(quitBtn, new Integer(3), 0);
        
        return panel;
    }
    
    private JPanel emptyMainJPanel() {
    	JPanel ret = new JPanel();
    	ret.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    	ret.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
    	ret.setBorder(new EmptyBorder(-5, -5, -5, -5));
    	
    	return ret;
    }
	
}
