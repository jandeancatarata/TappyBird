import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Barrier extends GameObject{

	
	BufferedImage img;
	
	public Barrier(int y,double fallSpeed,BufferedImage img){
	
		this.img = img;
		currentX = (200-150)/2 + 20;
		switch((int)(Math.random()*3)){
		case 0:
			pos = Position.LEFT;
			
			break;
		case 1:
			pos = Position.CENTER;
			currentX+= 220;
			break;
		case 2:
			pos = Position.RIGHT;
			currentX+=420;
			break;
		}
		
		currentY = y;
		speed =fallSpeed;
	
		t = Type.BARRIER;
		width = 150;
		height = 150;
		
	}
	
	
	
	@Override
	public void update(InputHandler input, int dt) {
		// TODO Auto-generated method stub
		currentY += dt*speed;
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(img,currentX-20 , currentY, 150, 200,null);
		
	}

}
