package GUI.Objects.Effects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import GUI.GameGUI;

public class Faders {
	
	public static class OutFader extends JPanel implements Runnable{
		public static final int DELAY=25;
		int width=GameGUI.WINDOW_WIDTH,height=GameGUI.WINDOW_HEIGHT;
		
		long duration;
		
		double percent=0;
		
		/**
		 * @param duration fade out duration in miliseconds
		 */
		public OutFader(long duration) {
			this.duration=duration;
			this.setOpaque(false);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Color c = g.getColor();
			g.setColor(new Color(0,0,0,(int)(255*percent)));
			g.fillRect(0, 0, width, height);
			g.setColor(c);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(width,height);
		}
		
		@Override
		public void run() {
			long start=System.currentTimeMillis();
			long end = start+duration;
			
			while(System.currentTimeMillis()<end) {
				percent=((double)System.currentTimeMillis()-start)/((double)end-start);
				this.repaint();
			}
			
		}
	}
}
