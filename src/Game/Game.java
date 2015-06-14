package Game;

import java.awt.Color;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Database.Users.User;

public class Game {
	User user;
	int difficulty, lives;
	Color backdrop,land;
	String country;
	List<Question> questions=null;
	int currentQuestion;
	String planet;

	File soldier;
	
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
		Collections.shuffle(this.questions);
	}
	
	public List<Question> getQuestions() {
		return questions;
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
	
	public void setSoldier(File soldier) {
		this.soldier=soldier;
	}
	
	public File getSoldier() {
		return soldier;
	}
	
	public void setLives(int lives) {
		this.lives=lives;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setCurrentQuestion(int crnt) {
		currentQuestion = crnt;
	}
	
	public int getCurrentQuestion() {
		return currentQuestion;
	}
	
	public void setPlanetName(String name) {
		this.planet=name;
	}
	
	public String getPlanetName() {
		return planet;
	}
	
	public void advanceQuestion() {
		currentQuestion++;
	}
}
