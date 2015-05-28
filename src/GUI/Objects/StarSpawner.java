package GUI.Objects;

import java.util.ArrayList;
import java.util.List;

public class StarSpawner {
	
	List<Star> stars;
	int starLimit;
	
	//A star spawner to which all stars will relate
	public StarSpawner(int starLimit) {
		
		this.starLimit=starLimit;
		this.stars = new ArrayList<Star>();
		
	}

	
	private void spawnStar() {
		
	}
}
