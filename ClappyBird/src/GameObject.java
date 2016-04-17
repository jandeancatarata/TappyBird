import java.awt.Graphics2D;

public abstract class GameObject {

	public enum Type{
		BARRIER,
		COIN
	}
	
	enum Position{
		LEFT,
		CENTER,
		RIGHT;
	}
	
	Position pos;
	Type t;
	int currentX;
	int currentY;
	int expectedX;
	int expectedY;
	int width;
	int height;
	double speed;
	
	public abstract void update(InputHandler input, int dt);
	public abstract void render(Graphics2D g);
	
}
