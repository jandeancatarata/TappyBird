import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Player extends GameObject{

	BufferedImage img;

	
	public Player(){
		pos = Position.CENTER;
		currentX = 220;
		expectedX = 220;
		currentY = 850;
		width = 160;
		height = 104;
		speed = 1.5;
		try{
			img = ImageIO.read(new File("img/bird back picture.png"));
		}catch (IOException e){
			
		}
	}
	
	public void update(InputHandler input, int dt){
		
		if(input.keyTyped){
			switch(input.keyInput.getKeyChar()){
			case '1':
				pos = Position.LEFT;
				expectedX = 20;
				break;
			case '2':
				pos = Position.CENTER;
				expectedX =  220;
				break;
			case '3':
				pos = Position.RIGHT;
				expectedX = 420;
				break;
			default:
				break;
			}
		}
		
		if(currentX != expectedX){
			if(currentX < expectedX){
				currentX += dt*speed;
				if(currentX > expectedX){
					currentX=expectedX;
				}
			}
			if(currentX > expectedX){
				currentX -= dt*speed;
				if(currentX < expectedX){
					currentX=expectedX;
				}
			}
		}
		
	}

	public void render(Graphics2D g){
		g.drawImage(img,currentX-20,currentY,width+40,height+20,null);
	}
}