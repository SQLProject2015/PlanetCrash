package GUI.Scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import GUI.GameGUI;
import GUI.Objects.JImage;
import GUI.Objects.JRoundedButton;
import Game.Game;
import Game.Answer;

public class GameScene extends Scene{
	JRoundedButton[] answersButtons;
	List<Answer> answers;
	
	public GameScene(GameGUI gameGUI, Game game) {
		super(gameGUI, game);
		this.answersButtons = new JRoundedButton[4];
	}

	@Override
	public Component create() {
//		while(game.getQuestions()==null)
//			try {
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//			}
//		this.answers = game.getQuestion(game.getCurrentQuestion()).getPossibleAnswers();
		
		JLayeredPane panel = new JLayeredPane();
		panel.setPreferredSize(new Dimension(GameGUI.WINDOW_WIDTH,GameGUI.WINDOW_HEIGHT));
		
		panel.setBackground(Color.GREEN);
		
		//Add background
		JPanel backdrop = emptyMainJPanel();
		backdrop.setBackground(game.getBackdrop());
		panel.add(backdrop, new Integer(0),0);
		
		//Add land
		JPanel land = emptyMainJPanel();
		land.setBackground(game.getLand());
		land.setBounds(0, GameGUI.WINDOW_HEIGHT-200, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);
		panel.add(land, new Integer(1),0);
		
		//Add question
		
		
		//Add answers
		Font ansfont = new Font(null, Font.PLAIN, 18);
		int ah = 100;
		JPanel answersP = emptyMainJPanel();
		answersP.setBounds(0, GameGUI.WINDOW_HEIGHT-ah, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);
		panel.add(answersP,new Integer(3),0);
		answersP.setOpaque(false);
		answersP.setLayout(null);
		
		int ax=0,ay=0;
		for(int i=0; i<answersButtons.length; i++) {
			JRoundedButton ans = new JRoundedButton(ansfont,"ans"+i/*answers.get(i).getAnswer()*/, GameGUI.WINDOW_WIDTH/2, ah/2, 2);
			ans.setOpaque(false);
			ans.setBounds(ax, ay, GameGUI.WINDOW_WIDTH/2, ah/2);
			
			answersButtons[i]=ans;
			answersP.add(ans);
			
			ax=((ax+GameGUI.WINDOW_WIDTH/2)%GameGUI.WINDOW_WIDTH)*((i+1)%2);
			if(i==1) ay=(ay+ah/2);
		}
		
		
		//Add soldiers
		JPanel soldiers = emptyMainJPanel();
		soldiers.setBounds(0, GameGUI.WINDOW_HEIGHT-200-20, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);
		panel.add(soldiers,new Integer(2),0);
		soldiers.setOpaque(false);
		soldiers.setLayout(null);
		
		int sx=50,sy=20;
		for(int i=0; i<game.getLives(); i++) {
			JImage s = new JImage(game.getSoldier());
			s.setOpaque(false);
			s.setBounds(sx,sy,s.getWidth(),s.getHeight());
			soldiers.add(s);
			
			sx+=s.getWidth()/2;
			sy=(sy+s.getHeight()/2)%s.getHeight();
		}
		
		//Add aliens
		
		return panel;
	}

}
