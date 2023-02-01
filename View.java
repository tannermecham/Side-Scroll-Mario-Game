/// TANNER MECHAM 
/// MARIO GAME PROJECT
/// 10-23-2020



import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;

class View extends JPanel
{

	BufferedImage tube_image;
	BufferedImage bricks;
	BufferedImage door;
	
	Model model;
	Controller controller;
	static int scrollPos;
	int marioOffset;
	
	// View Constructor
	View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
		
		marioOffset = 50;
		
		bricks = loadImage("Images/newbricks.png");
		tube_image = loadImage("Images/tube.png");
		door = loadImage("Images/mario_door.png");
	}
	
	// Method for loading images
	static BufferedImage loadImage(String filename)
	{
		BufferedImage temp = null;
		try
		{
			temp = ImageIO.read(new File(filename));
			
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return temp;
	}
		
	
	void update()
	{
	}
	


	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(128, 255,255));				
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// draws Mario's entrance door
		g.drawImage(this.door, 0 - scrollPos, 310, null);
		
		// cycles through sprite arraylist to draw all sprites in game
		for(int i = 0; i < model.sprites.size(); i++)
		{
			model.sprites.get(i).drawSprite(g, scrollPos);
		}
		
		//Draws bricks for mario as foundation
		int brickWidth = bricks.getWidth() - 1;
		g.drawImage(bricks, 0 - brickWidth - scrollPos,  415, null);
		g.drawImage(this.bricks, 0 - scrollPos, 415, null);
		g.drawImage(this.bricks,brickWidth - scrollPos,415,null);
		g.drawImage(this.bricks, 392 - scrollPos, 415, null);
		
		// makes bricks draw themselves and undraw themselves as needed
		if (scrollPos > 0)
		{
			for(int i = brickWidth * 3; i < scrollPos + brickWidth*3; i += brickWidth)
			{
				g.drawImage(this.bricks,i - scrollPos,415,null);

			}
		}		
	}
	
}
