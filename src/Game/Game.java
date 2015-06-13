package Game;

import Database.Users.User;

public class Game {
	User user;
	int difficulty;
	
	public Game() {
		
	}
	
	public void setUser(User user) {
		this.user=user;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty=difficulty;
	}
}
