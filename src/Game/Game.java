package Game;

import Database.Users.User;

public class Game {
	User user;
	
	public Game() {
		
	}
	
	public void setUser(User user) {
		this.user=user;
	}
	
	public User getUser() {
		return user;
	}
}
