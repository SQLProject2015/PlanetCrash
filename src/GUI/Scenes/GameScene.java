package GUI.Scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GUI.GameGUI;
import GUI.Objects.JImage;
import GUI.Objects.JRounded;
import GUI.Objects.JRoundedButton;
import Game.Answer;
import Game.Game;

public class GameScene extends Scene{
	JRoundedButton[] answersButtons;
	List<Answer> answers;
	JLayeredPane panel;

	public GameScene(GameGUI gameGUI, Game game) {
		super(gameGUI, game);
		this.answersButtons = new JRoundedButton[4];
	}

	@Override
	public Component create() {
		while(game.getQuestions()==null)
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		this.answers = game.getQuestion(game.getCurrentQuestion()).getPossibleAnswers();

		panel = new JLayeredPane();
		panel.setPreferredSize(new Dimension(GameGUI.WINDOW_WIDTH,GameGUI.WINDOW_HEIGHT));

		panel.setBackground(Color.GREEN);

		//Add background
		JPanel backdrop = emptyMainJPanel();
		backdrop.setBackground(game.getBackdrop());
		panel.add(backdrop, new Integer(0),0);
		//Background overlay
		JImage bdOverlay = new JImage(randomBackdropOverlay());
		bdOverlay.setOpaque(false);
		backdrop.add(bdOverlay);

		//Add land
		JPanel land = emptyMainJPanel();
		land.setBackground(game.getLand());
		land.setBounds(0, GameGUI.WINDOW_HEIGHT-200, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);
		panel.add(land, new Integer(1),0);
		//Land overlay
		JImage landOverlay = new JImage(randomLandOverlay());
		landOverlay.setOpaque(false);
		land.add(landOverlay);

		//Add question label
		Font ansfont = new Font(null, Font.PLAIN, 18);
		JPanel questionP = new JPanel();
		questionP.setLayout(null);
		questionP.setPreferredSize(new Dimension(3*GameGUI.WINDOW_WIDTH/4,100));
		panel.add(questionP, new Integer(4),0);
		questionP.setOpaque(false);
		questionP.setBounds(GameGUI.WINDOW_WIDTH/8,150,3*GameGUI.WINDOW_WIDTH/4,100);

		JRoundedButton qlabel = new JRoundedButton(ansfont, "The aliens challenge you:", 3*GameGUI.WINDOW_WIDTH/8, 40, 1);
		qlabel.isButton(false);
		qlabel.setBounds(0,0,(int)qlabel.getPreferredSize().getWidth(),(int)qlabel.getPreferredSize().getHeight());
		questionP.add(qlabel);

		//Add question
		JRoundedButton qst = new JRoundedButton(ansfont, game.getQuestion(game.getCurrentQuestion()).getQuestion(),3*GameGUI.WINDOW_WIDTH/4, 40, 1);
		qst.setBounds(0,40,3*GameGUI.WINDOW_WIDTH/4,40);
		qst.isButton(false);
		questionP.add(qst);

		//Add answers
		int ah = 100;
		JPanel answersP = emptyMainJPanel();
		answersP.setBounds(0, GameGUI.WINDOW_HEIGHT-ah, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);
		panel.add(answersP,new Integer(3),0);
		answersP.setOpaque(false);
		answersP.setLayout(null);

		int ax=0,ay=0;
		for(int i=0; i<answersButtons.length; i++) {
			JRoundedButton ans = new JRoundedButton(ansfont,answers.get(i).getAnswer(), GameGUI.WINDOW_WIDTH/2, ah/2, 1);
			ans.setOpaque(false);
			ans.setBounds(ax, ay, GameGUI.WINDOW_WIDTH/2, ah/2);

			//register listener
			ans.addMouseListener(new AnswerListener(answers.get(i)));

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
		JPanel aliens = emptyMainJPanel();
		File alienFile = randomAlien(); //the alien type for this screen
		aliens.setBounds(0, GameGUI.WINDOW_HEIGHT-200-2*(new JImage(alienFile)).getHeight()/3, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);
		panel.add(aliens,new Integer(2),0);
		aliens.setOpaque(false);
		aliens.setLayout(null);

		int alx=GameGUI.WINDOW_WIDTH-(int)((new JImage(alienFile)).getWidth()*1.4),aly=20;
		for(int i=0; i<game.getLives(); i++) {
			JImage s = new JImage(alienFile);
			s.setOpaque(false);
			s.setBounds(alx,aly,s.getWidth(),s.getHeight());
			aliens.add(s);

			alx-=s.getWidth()/2;
			aly=(aly+s.getHeight()/2)%s.getHeight();
		}

		//Add status bar
		JPanel statusbar = new JPanel();
		statusbar.setPreferredSize(new Dimension(GameGUI.WINDOW_WIDTH,30));
		statusbar.setBounds(0,-1,GameGUI.WINDOW_WIDTH,30);
		statusbar.setBorder(BorderFactory.createEmptyBorder(-5, -5, -5, -5));
		panel.add(statusbar, new Integer(4),2);
		JRoundedButton sbbg = new JRoundedButton("", (int)(GameGUI.WINDOW_WIDTH*1.3), 30, 1);
		sbbg.setPreferredSize(new Dimension((int)(GameGUI.WINDOW_WIDTH*1.3),30));
		sbbg.setBackground(Color.decode("#3B5998"));
		sbbg.setBounds((int)(-GameGUI.WINDOW_WIDTH*0.15), 0, (int)(GameGUI.WINDOW_WIDTH*1.3), 30);
		sbbg.isButton(false);
		statusbar.add(sbbg);
		JTextField stext = new JTextField();
		stext.setText("Lives: "+game.getLives() +"\t\t\t Questions: "+(game.getCurrentQuestion()+1)+"\\"+game.getDifficulty()
				+"\t\t\t Planet: "+game.getPlanetName());
		stext.setEditable(false);
		stext.setBackground(sbbg.getBackgroundColor());
		stext.setBorder(BorderFactory.createEmptyBorder());
		stext.setForeground(Color.cyan);
		sbbg.add(stext);


		return panel;
	}

	private File randomFile(String dirp) {
		File dir = new File(dirp);
		if(!dir.exists() || !dir.isDirectory())
			return null;
		File[] flist = dir.listFiles();
		return flist[(int)(Math.random()*flist.length)];

	}

	private File randomBackdropOverlay() {
		return randomFile(GameGUI.ASSETS+"Overlays\\Backdrop");
	}

	private File randomLandOverlay() {
		return randomFile(GameGUI.ASSETS+"Overlays\\Land");
	}

	private File randomAlien() {
		return randomFile(GameGUI.ASSETS+"Aliens");
	}

	class AnswerListener implements MouseListener {
		Answer answer;

		public AnswerListener(Answer answer) {
			this.answer=answer;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			//disable answer button listers
			for(int i=0;i<answersButtons.length;i++) {
				for(int j=0; j<answersButtons[i].getMouseListeners().length;j++)
					answersButtons[i].removeMouseListener(answersButtons[i].getMouseListeners()[j]);
			}
			
			JLayeredPane dlg = new JLayeredPane();
			JLayeredPane mask = emptyMainJLayeredPane();

			//mask.setLayout(null);
			mask.setOpaque(false);
			JPanel bg = emptyMainJPanel();

			if (game.getQuestion(game.getCurrentQuestion()).isCorrectAnswer(answer)) {
				//Correct answer
				bg.setBackground(new Color(6,186,17,100));
				dlg = createCorrectDialog(true);

			} else {
				//Wrong answer
				bg.setBackground(new Color(255,0,0,50));
				dlg = createCorrectDialog(false);
			}
			mask.add(bg, new Integer(0),0);
			dlg.setBounds((int)(GameGUI.WINDOW_WIDTH-dlg.getPreferredSize().getWidth())/2,
					(int)(GameGUI.WINDOW_HEIGHT-dlg.getPreferredSize().getHeight())/2,
					(int)dlg.getPreferredSize().getWidth(),
					(int)dlg.getPreferredSize().getHeight());
			mask.add(dlg,new Integer(1),0);

			mask.setOpaque(false);

			panel.add(mask, new Integer(5),0);
		}

		private JLayeredPane createCorrectDialog(boolean isCorrect) {
			JLayeredPane master = emptyMainJLayeredPane();
			JPanel containerT = emptyMainJPanel();
			containerT.setLayout(null);
			containerT.setOpaque(false);
			
			//JTextField title = new JTextField("Correct!");
			JRoundedButton title = new JRoundedButton(new Font(null, Font.BOLD, 30), (isCorrect?"Correct!":"Wrong!"), 150, 50, 2);
			JTextField msg = new JTextField("The answer is:\n"+game.getQuestion(game.getCurrentQuestion()).getCorrectAnswer().getAnswer());
			msg.setBackground(Color.decode("#3B5998"));
			msg.setEditable(false);
			msg.setBorder(BorderFactory.createEmptyBorder());
			title.setBorderColor(isCorrect?Color.green:Color.red);
			title.isButton(false);
			msg.setFont(new Font(Font.SANS_SERIF, Font.BOLD,18));
			msg.setForeground(isCorrect?Color.green:Color.red);
			
			title.setBounds((int)(GameGUI.WINDOW_WIDTH-title.getPreferredSize().getWidth())/2,
					220, (int)title.getPreferredSize().getWidth(), (int)title.getPreferredSize().getHeight());
			containerT.add(title);
			containerT.setBounds(0,0,GameGUI.WINDOW_WIDTH,GameGUI.WINDOW_HEIGHT);
			
			JRounded containerM = new JRounded((int)msg.getPreferredSize().getWidth()+50,
					(int)msg.getPreferredSize().getHeight()+50,2) {

						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;};
						
			containerM.add(msg);
			containerM.setLayout(null);
			containerM.setBounds((int)(GameGUI.WINDOW_WIDTH-containerM.getPreferredSize().getWidth())/2,
					250, (int)containerM.getPreferredSize().getWidth(), (int)containerM.getPreferredSize().getHeight());
			containerM.setBorderColor(isCorrect?Color.green:Color.red);
			
			msg.setBounds((int)(containerM.getPreferredSize().getWidth()-msg.getPreferredSize().getWidth())/2,
					(int)(containerM.getPreferredSize().getHeight()-msg.getPreferredSize().getHeight())/2, 
					(int)msg.getPreferredSize().getWidth(), (int)msg.getPreferredSize().getHeight());
			
			master.add(containerM,new Integer(0),0);
			master.add(containerT,new Integer(1),0);
			
			return master;
		}


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
