package GUI;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

import GUI.Objects.StarryBackground;

public class GameGUI {

	public static final int WINDOW_WIDTH=800;
	public static final int WINDOW_HEIGHT=600;
	
	public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        
        //Add background
        StarryBackground sb = new StarryBackground();
        f.add(sb);
        f.pack();
        
        f.setVisible(true);
    }
	
}
