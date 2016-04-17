import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener{

	public boolean mouseLeftClicked, mouseRightClicked, keyTyped;
	public MouseEvent mouseInput;
	public KeyEvent keyInput;
	
	public void clearInput(){
		mouseLeftClicked = false;
		mouseRightClicked = false;
		keyTyped = false;
		mouseInput = null;
		keyInput = null;
		
	}
	
	public InputHandler(){
		mouseLeftClicked = false;
		mouseRightClicked = false;
		mouseInput = null;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		switch(e.getButton()){
		case MouseEvent.BUTTON1:
			mouseLeftClicked = true;
			break;
		case MouseEvent.BUTTON2:
			mouseRightClicked = true;
			break;
		}
		
		mouseInput = e;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		keyTyped = true;
		keyInput = e;
		System.out.println(e.getKeyChar());
	
		
	}

}
