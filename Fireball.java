/// TANNER MECHAM 
/// MARIO GAME PROJECT
/// 10-23-2020


import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Fireball extends Sprites
{
	BufferedImage image;
	Model model;
	int vert_vel;
	int hor_vel;
	int count;
	int direction;
	
	Fireball(int x, int y, Model m)
	{
		this.model = m;
		this.x = x;
		this.y = y;
		height = 48;
		width = 40;
		vert_vel = 12;
		hor_vel = 10;
		this.type = "fireball";
		loadFireballImage();			
		fireballDirection();			// sets direction fireballs should travel depending on Mario's orientation
	}
	
	void loadFireballImage()
	{
		if(image == null)
			image = View.loadImage("Images/fireball.png");
	}
	
	void fireballDirection()
	{
		if (model.mario.flip)
			direction = -1;
		else
			direction = 1;
	}
	void update()
	{
		
		vert_vel += 1.2;				// updates velocity
		y += vert_vel;					// updates y value
		x+= (hor_vel * direction);		// updates x value in proper direction
		
		if(y > 370)
		{
			vert_vel = -24;
			y = 370;
		}
		
		// removes fireball if it navigates out of frame to the right
		if(this.x > 400 + width + View.scrollPos )
			model.sprites.remove(this);
		// removes fireball if it navigates out of frame to the left
		if(this.x < -10 + View.scrollPos)
			model.sprites.remove(this);
	}
	
	
	void drawSprite(Graphics g, int scrollPos)
	{
		g.drawImage(image, x - scrollPos,  y,  null);
	}
}
