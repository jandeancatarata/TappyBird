import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ClappyBird implements Runnable{

	final static int WIDTH = 600;
	final static int HEIGHT = 1000;
	ArrayList<Score> scores;
	Score currentScore;
	
	public enum State{
		TITLE,GAME,NEWSCORE,SCORES,ABOUT,CALIBRATE
	}
	
	State STATE = State.TITLE;
	
	JFrame frame;
	Canvas canvas;
	BufferStrategy bs;
	InputHandler input;
	Game game;
	
	BufferedImage bg;
	BufferedImage about;
	BufferedImage highscore;
	BufferedImage play;
	BufferedImage title;
	BufferedImage bird;
	
	public ClappyBird(){
		frame = new JFrame("Clappy Bird");
		
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		panel.setLayout(null);
		
		canvas = new Canvas();
		canvas.setBounds(0,0,WIDTH,HEIGHT);
		canvas.setIgnoreRepaint(true);
		
		panel.add(canvas);
		
		
		input = new InputHandler();
		

		canvas.addMouseListener(input);
		canvas.addKeyListener(input);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setResizable(false);
		frame.pack();
		
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		
		canvas.requestFocus();
		
		currentScore = null;
		
		try{
			bg = ImageIO.read(new File("img/background final image.png"));
			about =  ImageIO.read(new File("img/about text.png"));

			play =  ImageIO.read(new File("img/play text.png"));
			highscore = ImageIO.read(new File("img/scores image.png"));

		
		}catch (IOException e){
			System.out.println(e);
		}
		
		try{

			
			title = ImageIO.read(new File("img/tappy bird image.png"));
			bird = ImageIO.read(new File("img/bird picture.png"));
		}catch(IOException e){
			System.out.println("HELP");
		}
		
		scores = new ArrayList<Score>();
		
	
	}

	
	long desiredFPS = 120;
	long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
	
	boolean running = true;
	
	public void run(){
		
		long beginLoopTime;
		long endLoopTime;
		long currentUpdateTime = System.nanoTime();
		long lastUpdateTime;
		long deltaLoop;
		
		while(running){
			beginLoopTime = System.nanoTime();
			
			render();
			
			lastUpdateTime = currentUpdateTime;
			currentUpdateTime = System.nanoTime();
			update((int)((currentUpdateTime - lastUpdateTime)/(1000*1000)));
			
			endLoopTime = System.nanoTime();
			
			deltaLoop = endLoopTime-beginLoopTime;
			
			if(deltaLoop > desiredDeltaLoop){
				//WERE LATE!!!!
			}else{
				try{
					Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
				}catch(InterruptedException e){
					//Shit went south but that's okay, this game probably sucks anyway
				}
				
			}
			
		}
		
	}
	
	private void render(){
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.clearRect(0,0,WIDTH,HEIGHT);
		render(g);
		g.dispose();
		bs.show();
	}
	
	
	protected void update(int deltaTime){
		switch(STATE){
		case TITLE:
			updateTitleScreen();
			break;
		case GAME:
			if(game.isRunning == false){
				STATE = State.NEWSCORE;
				currentScore = new Score("",game.score);
			}
			else{
				game.update(input, deltaTime);
			}
			break;
		case NEWSCORE:
			enterNewScore();			
			break;
		case SCORES:
			updateScore();
			break;
		case ABOUT:
			updateAbout();
			break;
		case CALIBRATE:
			updateCalibrate();
			break;
		}
		
		input.clearInput();
	}


	/*
	 * GRAPHICS GO HERE
	 */
	protected void render(Graphics2D g){
		
		switch(STATE){
		case TITLE:
			drawTitleScreen(g);
			break;
		case GAME:
			game.render(g);
			break;
		case NEWSCORE:
			game.render(g);
			renderNewScore(g);
			break;
		case SCORES:
			renderScores(g);
			break;
		case ABOUT:
			renderAbout(g);
			break;
		case CALIBRATE:
			renderCalibrate(g);
		}
		
	}

	private void updateTitleScreen() {
		
		if(input.mouseLeftClicked){
			int x = input.mouseInput.getX();
			int y = input.mouseInput.getY();
			

			
			if(x >= 100 && x <= 100+400 && y >= 400 && y <= 400+100){
				STATE = State.GAME;
				game = new Game();		
			}
			
			if(x >= 100 && x <= 100+400 && y >= 550 && y <= 550+100){
				STATE = State.SCORES;
			
			}
			
			if(x >= 100 && x <= 100+400 && y >= 700 && y <= 700+100){
				STATE = State.ABOUT;
			
			}
					
		}
		if(input.keyTyped){
			if(input.keyInput.getKeyChar() == ' '){
				STATE = State.GAME;
				game = new Game();
			}
		}
	}
	
	private void updateScore(){
		if(input.mouseLeftClicked) STATE = State.TITLE;
	}
	
	private void updateAbout(){
		if(input.mouseLeftClicked){
			
			int x = input.mouseInput.getX();
			int y = input.mouseInput.getY();
			
			if(x >= 150 && x <= 150+300 && y >= 770 && y <= 770+100){
				STATE = State.CALIBRATE;
			}else{
				STATE = State.TITLE;
		
			}
		}
	}
	
	private void updateCalibrate(){

		if(input.mouseLeftClicked) STATE = State.TITLE;
	}
	
	private void drawTitleScreen(Graphics2D g){
		g.drawImage(bg, 0, 0, WIDTH, HEIGHT, null);

		g.drawImage(title,50,50,500,(500*425/1549),null);
		
		g.setColor(new Color(240,248,255));
		
		g.fillRoundRect(100, 400, 400, 100, 100, 100);
		g.fillRoundRect(100, 550, 400, 100, 100, 100);
		g.fillRoundRect(100, 700, 400, 100, 100, 100);
		
		g.setColor(new Color(70,130,180));
	
		g.drawRoundRect(100, 400, 400, 100, 100, 100);
		g.drawRoundRect(100, 550, 400, 100, 100, 100);
		g.drawRoundRect(100, 700, 400, 100, 100, 100);
		
		//g.drawImage(bird, 140, 180, (200*bird.getWidth()/bird.getHeight()), 200, null);
		g.drawImage(play, 240, 420, (80*play.getWidth()/play.getHeight()), 80, null);
		g.drawImage(highscore, 210, 565, (65*highscore.getWidth()/highscore.getHeight()), 65, null);
		g.drawImage(about, 220, 710, (80*about.getWidth()/about.getHeight()), 80, null);
	
		
	}
	
	private void enterNewScore(){
		if(input.mouseLeftClicked || (input.keyTyped && input.keyInput.getKeyChar()==' ')){
			STATE = State.TITLE;
			if(currentScore.name.equals("")) currentScore.name = "ANON";
			scores.add(currentScore);
		}
		if(input.keyTyped){
			if(currentScore.name.length() < 6 && ((input.keyInput.getKeyChar() <= 'z' && input.keyInput.getKeyChar() >= 'a')||(input.keyInput.getKeyChar() <= 'Z' && input.keyInput.getKeyChar() >= 'A'))){
				currentScore.name = currentScore.name + Character.toUpperCase(input.keyInput.getKeyChar());
			}
			if(currentScore.name.length() > 0 && input.keyInput.getKeyChar() == '\b'){
				currentScore.name = currentScore.name.substring(0, currentScore.name.length()-1);
			}
			if(input.keyInput.getKeyChar() == '\n'){
				STATE = State.TITLE;

				if(currentScore.name.equals("")) currentScore.name = "ANON";
				scores.add(currentScore);
			}
		}
		
		Collections.sort((List<Score>) scores);
	}
	
	private void renderNewScore(Graphics2D g){
		g.setColor(new Color(240,248,255));
		g.fillRoundRect(75, 100, 450, 800, 50, 50);
		
		g.setColor(new Color(70,130,180));

		g.drawRoundRect(75, 100, 450, 800, 50, 50);
		
		g.setFont(new Font("SansSerif",Font.BOLD,60));
		g.drawString("High Scores", 120, 200);
		

		g.setFont(new Font("Courier",Font.BOLD,40));
		
		int y = 250;
		int i = 0;
		for(Score s: scores){
			if(y+(i*50) > 800) break;
			g.drawString(s.name, 135, y+(i*50));
			g.drawString(Integer.toString(s.score), 350, y+(i*50));
			i++;
		}
		g.setFont(new Font("Courier",Font.BOLD,50));
		g.drawString(currentScore.name+"|", 120, 850);
		g.drawString(Integer.toString(currentScore.score), 350, 850);
		
	}
	
	private void renderScores(Graphics2D g){
		g.setColor(new Color(176,224,230));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(new Color(240,248,255));
		g.fillRoundRect(75, 100, 450, 800, 50, 50);
		
		g.setColor(new Color(70,130,180));

		g.drawRoundRect(75, 100, 450, 800, 50, 50);
		
		g.setFont(new Font("SansSerif",Font.BOLD,60));
		g.drawString("High Scores", 120, 200);
		

		g.setFont(new Font("Courier",Font.BOLD,40));
		
		int y = 250;
		int i = 0;
		for(Score s: scores){
			if(y+(i*50) > 800) break;
			g.drawString(s.name, 135, y+(i*50));
			g.drawString(Integer.toString(s.score), 350, y+(i*50));
			i++;
		}
	}
	
	private void renderAbout(Graphics2D g){
		g.setColor(new Color(176,224,230));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(new Color(240,248,255));
		g.fillRoundRect(75, 100, 450, 800, 50, 50);
		
		g.setColor(new Color(70,130,180));

		g.drawRoundRect(75, 100, 450, 800, 50, 50);
		
		g.setFont(new Font("SansSerif",Font.BOLD,80));
		g.drawString("About", 190, 200);
		
		g.setFont(new Font("Courier",Font.BOLD,40));
		g.drawString("Flappy Bird", 170, 260);
		
		
		g.drawString("Rhythm Game", 170, 350);
		
		g.drawString("Jan Catarata", 160, 570);

		g.drawString("Krysti Leung", 160, 620);

		//g.drawString("Evan Amster", 170, 670);
		
		g.setFont(new Font("Courier",Font.BOLD,60));
		g.drawString("A Game By", 150, 520);
		
		g.setFont(new Font("Courier",Font.BOLD,30));
		g.drawString("meets", 260, 300);
		
		/*
		g.setFont(new Font("SansSerif",Font.BOLD,45));
		g.drawString("CALIBRATE", 170, 835);
		
		g.drawRoundRect(150, 770, 300, 100, 100, 100);

		*/
		
		
	}
	
	public void renderCalibrate(Graphics2D g){
		g.setColor(new Color(176,224,230));
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
	public static void main(String [] args){
		ClappyBird game = new ClappyBird();
		new Thread(game).start();
	}
}

