import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Coin extends GameObject{

	BufferedImage img;
	
	public Coin(int y,double fallSpeed,Position barrierPos){
		
		currentX = (200-100)/2;
		
		int p = (int)(Math.random()*3);
		while(p == barrierPos.ordinal()) p = (int)(Math.random()*3);
		
		switch(p){
		case 0:
			pos = Position.LEFT;
			
			break;
		case 1:
			pos = Position.CENTER;
			currentX+= 200;
			break;
		case 2:
			pos = Position.RIGHT;
			currentX+=400;
			break;
		}
		
		currentY = y+25;
		speed =fallSpeed;
		
		t=Type.COIN;
		
		width = 100;
		height = 100;
		
		try{
			img = ImageIO.read(new File("img/coin image with text.png"));
		}catch (IOException e){
		} 
	}
	
	
	
	@Override
	public void update(InputHandler input, int dt) {
		// TODO Auto-generated method stub
		currentY += dt*speed;
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.YELLOW);
		g.drawImage(img,currentX , currentY, 100, 100,null);
		
	}


}
