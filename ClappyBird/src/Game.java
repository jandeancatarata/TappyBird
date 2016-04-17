import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Game {

	public int score;
	public boolean isRunning;
	private int distance;
	private double fallSpeed;
	int nextlevel;
	int level;
	
	
	private BufferedImage bg = null;
	
	private BufferedImage goimg[] = new BufferedImage[3];
	
	int ticks;
	



	Player p;
	ArrayList<GameObject> obs;
	
	public Game(){
		score = 0;
		p = new Player();
		isRunning = true;
		obs = new ArrayList<GameObject>();
		distance = 600;
		fallSpeed = 1;
		ticks =0;
		level = 0;
		nextlevel = 1200;
		try{
			bg = ImageIO.read(new File("img/background final image.png"));
			goimg[0] =  ImageIO.read(new File("img/hot air balloon image.png"));

			goimg[1] =  ImageIO.read(new File("img/kite image.png"));

			goimg[2] =  ImageIO.read(new File("img/three balloons.png"));
		}catch (IOException e){
		}
		

		obs.add(new Barrier(-distance,fallSpeed,goimg[(int) (Math.random()*3)]));
		obs.add(new Coin(-distance,fallSpeed,obs.get(0).pos));
		
		Sound.sounds[0].loop();
		
	}
	
	
	public void update(InputHandler input, int dt){
		
		ArrayList<Integer> toDel = new ArrayList<Integer>();
		
		//testing code, useless
		if(input.mouseLeftClicked) {

			Sound.sounds[level].stop();
			isRunning = false;
		}
		
	
		//update all the things!
		p.update(input,dt);
		for(GameObject obj:obs){
			obj.update(input, dt);
		}
		
		//Delete objects past the bird
		if(obs.get(0).currentY > ClappyBird.HEIGHT){
			obs.remove(0);
			score++;
		}
		if(obs.get(1).currentY > ClappyBird.HEIGHT){
			obs.remove(1);
		}

		//Create next step
		GameObject last =  obs.get(obs.size()-1);
		if(last.currentY > 0){
			
			Barrier b = new Barrier(last.currentY - distance,fallSpeed,goimg[(int) (Math.random()*3)]); 
	
			obs.add(b);
			obs.add(new Coin(-distance,fallSpeed,b.pos));
		}
		
		//Collision Detection
		for(int i = 0; i < obs.size();i++){
			GameObject obj = obs.get(i);
			if((obj.currentY+obj.height-10)-dt > p.currentY && obj.currentY+20 < p.currentY+p.height-dt && obj.currentX < p.currentX+p.width-dt &&(obj.currentX+obj.width-dt) > p.currentX){
				if(obj.t == GameObject.Type.BARRIER){
					isRunning= false;
					Sound.sounds[level].stop();
				}
				if(obj.t == GameObject.Type.COIN){
					score += 2;
					toDel.add(i);
				}
			}
		}
		for(int i: toDel){
			obs.remove(i);
		}
		toDel.clear();
		ticks++;
		if(ticks == nextlevel && fallSpeed < 2.4){
			ticks = 0;
			nextlevel-=240;
			fallSpeed += .2;
			distance = (int) (600*fallSpeed);
			p.speed +=.2;
			if(level < 7){
			Sound.sounds[level].stop();
			level++;
			Sound.sounds[level].loop();
			}
		}
		
		//System.out.println(dt);
	
		
	}
	
	public void render(Graphics2D g){
		
		g.setColor(new Color(176,224,230));
		g.fillRect(0, 0, ClappyBird.WIDTH, ClappyBird.HEIGHT);
		g.drawImage(bg,0,0,null);
		
		
		p.render(g);
		
		for(GameObject obj: obs){
			obj.render(g);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("SanSerif",Font.BOLD,35));
		g.drawString("Score: "+score, 30, 40);
			
			
		
	}
	
}
