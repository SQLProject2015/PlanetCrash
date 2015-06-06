package Game;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private List<Answer> possibleAnswers;
	private Answer correctAnswer;
	private String question;
	private boolean questionReady;

	
	public Question(String question) {
		this.question=question;
		this.questionReady=false;
		this.possibleAnswers=new ArrayList<Answer>();
	}
	
	public String getQuestion(){
		return this.question;
	}
	public List<Answer> getPossibleAnswers() {
		return this.possibleAnswers;
	}
	
	public Answer getCorrectAnswer() {
		return this.correctAnswer;
	}
	
	public void setQuestion(String question){
		this.question=question;
	}
	public void setPossibleAnswers(List<Answer> answers) {
		this.possibleAnswers=answers;
	}
	public void addPossibleAnswers(Answer answer) {
		this.possibleAnswers.add(answer);
	}
	public void setCorrectAnswer(Answer answer) {
		this.correctAnswer=answer;
	}
	
	public boolean isCorrectAnswer(Answer answer) {
		return answer.equals(correctAnswer);
	}
	
	public boolean isQuestionReady() {
		return this.questionReady;
	}
	public void setQuestionAsReady() {
		this.questionReady=true;
	}
}
