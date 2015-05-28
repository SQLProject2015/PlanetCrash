package GUI.Objects;

import java.awt.Color;
import java.awt.Graphics;

public class Star {
	double angle;
	int startX,startY,endX,endY,startVelocity,acceleration;
	int x,y;
	long starttime;
	
	public Star(double angle,int startX,int startY, int startVelocity, int acceleration) {
		this.angle=angle;
		this.x=this.startX=startX;
		this.y=this.startY=startY;
		this.startVelocity=startVelocity;
		this.acceleration=acceleration;
		this.starttime=System.currentTimeMillis();
	}
	
	public void update() {
		long time = System.currentTimeMillis()-starttime;
		int dist = (int) (startVelocity*time+0.5*acceleration*time*time);
		x = (int) (Math.cos(angle)*dist);
		y = (int) (Math.sin(angle)*dist);
	}
	
	public void paintStar(Graphics g) {
		g.setColor(Color.WHITE);
	}
}
