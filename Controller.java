/// TANNER MECHAM 
/// MARIO GAME PROJECT
/// 10-23-2020



import java.awt.event.MouseListener;
import java.io.File;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean space;
	boolean control;
	boolean addTubesEditor = false;
	boolean addGoombasEditor = false;
	boolean fireballAllow = true;
	
	Controller(Model m)
	{
		model = m;
	}
	
	void setView(View v)
	{
		view = v;
	}

	
	public void actionPerformed(ActionEvent e)
	{

	}
	
	public void mousePressed(MouseEvent e)
	{
		if(addTubesEditor)
			model.addNewTube(e.getX() + View.scrollPos, e.getY());
		if(addGoombasEditor)
			model.addNewGoomba(e.getX() + View.scrollPos, e.getY());

	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_SPACE: space = true; break;
			case KeyEvent.VK_CONTROL: control = true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE: space = false; break;
			case KeyEvent.VK_CONTROL: control = false; fireballAllow = true; break;
		}
		
		char c = e.getKeyChar();
		if(c == 's')
		{
			// System.out.println("Game Saved.");
			model.marshal().save("map.json");
			System.out.println("map.json saved...");
		}
		
		if(c == 'l')
		{
			System.out.println("Loading game...");
			
			File f = new File("map.json");
			if(!f.exists())
				{
					System.out.println("There is no file available to load. Please press 's' to save a map, then"
							+ " reload using the load command.");
				}
			else
			{
				Json j = Json.load("map.json");
				model.unmarshal(j);
				System.out.println("map.json loaded...");
				
			}
		
		}
		if(c == 'g')
		{
			addGoombasEditor = true;
			addTubesEditor = false;
		}
		if(c == 't')
		{
			addTubesEditor = true;
			addGoombasEditor = false;
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}

	void update()
	{
		model.mario.savePreviousCoordinates();
		if(keyRight) 
		{
			model.mario.x += 5;
			if(model.mario.x > 50 + View.scrollPos)
				View.scrollPos += 5;
			model.mario.updateMarioImage();
			model.mario.flip = false;
		}
		if(keyLeft && model.mario.x > 30) 
		{
			model.mario.x -= 5;
			if (model.mario.x < 100 + View.scrollPos)
				View.scrollPos -= 5;
			model.mario.updateMarioImage();
			model.mario.flip = true;
		}
//		if(keyDown) model.dest_y++;
//		if(keyUp) model.dest_y--;
		if(space)
		{
			model.mario.MarioJump();
			model.mario.updateMarioImage();
		}
		if(control && fireballAllow)
		{
			model.addNewFireball(model.mario.x, model.mario.y);
			fireballAllow = false;
		}
	}
	
}
