package Game;

import java.util.List;

public class Question {
	List<Answer> possibleAnswers;
	Answer correctAnswer;
	
	public Question(List<Answer> possibleAnswers, Answer correctAnswer) {
		this.possibleAnswers=possibleAnswers;
		this.correctAnswer=correctAnswer;
	}
	
	public List<Answer> getPossibleAnswers() {
		return this.possibleAnswers;
	}
	
	public Answer getCorrectAnswer() {
		return this.correctAnswer;
	}
	
	public boolean isCorrectAnswer(Answer answer) {
		return answer.equals(correctAnswer);
	}
}
