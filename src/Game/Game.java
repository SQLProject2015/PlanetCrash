package Game;

import java.awt.Color;
import java.util.List;

import Database.Users.User;

public class Game {
	User user;
	int difficulty;
	Color backdrop,land;
	String country;
	List<Question> questions;
	
	public static final Color[] BACKDROPS = {Color.CYAN, Color.pink, Color.gray, Color.blue};
	public static final Color[] LANDS = {Color.green, Color.magenta};
	
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
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void setBackdrop(Color color) {
		this.backdrop=color;
	}
	
	public void setLand(Color color) {
		this.land=color;
	}
	
	public Color getBackdrop() {
		return backdrop;
	}
	
	public Color getLand() {
		return land;
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions=questions;
	}
	
	public Question getQuestion(int i) {
		return questions.get(i);
	}
	
	public void setCountry(String countryName) {
		country=countryName;
	}
	
	public String getCountry() {
		return country;
	}
}
