/// TANNER MECHAM 
/// MARIO GAME PROJECT
/// 10-23-2020


import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Tube extends Sprites 
{

	Model model;
	static BufferedImage image;
	
	
	public Tube (int x, int y, Model m) 
	{
		this.x = x;
		this.y = y;
		width = 55;
		height = 400;
		loadTubeImage();
		model = m;
		this.type = "tube";
	}
	
	public Tube(Json ob, Model m)
	{
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		width = 55;
		height = 400;
		model = m;
		type = "tube";
		loadTubeImage();
	}
	
	
	Json marshal()
	{
	    Json ob = Json.newObject();
	    ob.add("x", x);
	    ob.add("y", y);
	    return ob;
	}
	
	@Override
	boolean isTube()
	{
		return true;
	}

	void loadTubeImage()
	{
		if (image == null)
		{
			image = View.loadImage("Images/tube.png");
		}
	}

	void update()
	{
		
		
	}
	boolean IsThereATubeHere(int pos_x, int pos_y)
	{
		if (pos_x < x || x == 0)
		{
			return false;
		}	
		
		if (pos_x > x + width)
		{
			return false;
		}
		
		if (pos_y < y)
		{
			return false;
		}
		
		if (pos_y > y + height)
		{
			return false;
		}
		
		else
		{
			return true;
		}
	}
	
	void drawSprite(Graphics g, int scrollPos)
	{
		g.drawImage(image, x - scrollPos,  y,  null);
	}

	
}
