/// TANNER MECHAM 
/// MARIO GAME PROJECT
/// 10-23-2020


import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Goomba extends Sprites
{

	BufferedImage image;
	BufferedImage enflamedGoomba;
	boolean flameCollision;			// boolean that detects collision b/t fire and goomba
	Model model;
	int vert_vel;
	int goombaOnGround;				// sets the constant value where goomba will be on the ground
	int speed;						// sets the speed at which the goomba will move
	boolean tubeCollision;			// boolean set true if goomba collides with tube
	int framesInFlames;				// tracks the number of frames goomba burns before being removed
	int direction = 1;				// instructs direction of the goomba (left / right)
	
	Goomba(int x, int y, Model m)
	{
		this.x = x;
		this.y = y;
		width = 40;
		height = 48;
		this.model = m;
		this.type = "goomba";
		vert_vel = 10;
		direction = 1;
		speed = 3;
		tubeCollision = false;
		loadGoombaImage();			//Loads goombas image
		framesInFlames = 0;
		flameCollision = false;
	}
	
	void update()
	{
		speed = 3;
		goombaOnGround = 368;	
		
		// Makes goomba fall if he's dropped in the air
		if(y < goombaOnGround)		
		{
			y += vert_vel;
		}
		
		// Sets goombas constant position to ground if below
		if(y > goombaOnGround)		
			y = goombaOnGround;
		
		// increments number of frames goomba suffers
		if(flameCollision)			
			framesInFlames++;
		
		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprites tube = model.sprites.get(i);
			if(model.sprites.get(i).type.equals("tube"))
			{
				if(tube.x <= x + width && tube.x >= x)
				{
					tubeCollision = true;
					break;
				}
				if(tube.x + tube.width >= x && tube.x + tube.width <= x + width )
				{
					tubeCollision = true;
					break;
				}
				else
					tubeCollision = false;
					
			}
		}
		
		if(tubeCollision)
			direction = direction * -1;
		
		if(y == goombaOnGround && flameCollision == false)
			x += (speed * direction);
		
		enflamedGoomba();
		if(framesInFlames > 15)
		{
			removeGoomba();
		}
	}
	
	// loads burning goomba image
	// checks to see if there's a collision between goomba and fireball
	// removes used fireball from arraylist
	void enflamedGoomba()
	{

		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprites s = model.sprites.get(i);
			if(model.sprites.get(i).type.equals("fireball"))
			{
				Fireball fireball = (Fireball)model.sprites.get(i);
				if(s.collision(fireball, this))
				{
					enflamedGoomba = View.loadImage("Images/goomba_fire.png");
					flameCollision = true;
					model.sprites.remove(fireball);
					speed = 0;
					break;
				}
			}
		}
	}
	
	// removes goomba if he's been hit by fireball
	void removeGoomba()
	{
		for(int i = 0; i < model.sprites.size(); i++)
		{
			if(model.sprites.get(i).type.equals("goomba"))
			{
				Goomba temp = (Goomba)model.sprites.get(i);
				if(temp.x == this.x)
				{
					model.sprites.remove(temp);
					break;
				}

			}
			
		}

	}
	
	public Goomba(Json ob, Model m)
	{
		this.x = (int)ob.getLong("x");
		this.y = (int)ob.getLong("y");
		this.width = 40;
		this.height = 48;
		this.model = m;
		this.type = "goomba";
		loadGoombaImage();
	}
	
	Json marshal()
	{
	    Json ob = Json.newObject();
	    ob.add("x", this.x);
	    ob.add("y", this.y);
	    return ob;
	}


	void loadGoombaImage()
	{
		if (image == null)
		{
			image = View.loadImage("Images/goomba.png");
		}
	}

	

	void drawSprite(Graphics g, int scrollPos)
	{
		// if goomba isn't burning, load this image
		if(!flameCollision)
			g.drawImage(image, x - scrollPos,  this.y,  null);
		// if goomba is burning, load this image
		if(flameCollision)
			g.drawImage(enflamedGoomba, x-scrollPos, this.y, null);
	}
}