/// TANNER MECHAM 
/// MARIO GAME PROJECT
/// 10-23-2020


import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Mario extends Sprites
{

	int px;
	int py;
	int marioImageNum;
	double vert_vel;
	int marioStaticY = 320;
	int numFramesInAir;
	BufferedImage[] mario_images;
	boolean flip;

	Mario(int x, int y)
	{
		this.x = x;
		this.y = y;
		marioImageNum = 0;
 		vert_vel = 12;
		height = 95;
		width = 50;
		numFramesInAir = 0;
		mario_images = new BufferedImage[5];
		loadMarioImage();
		this.type = "mario";
	}
	
	// loads mario's images into an array
	void loadMarioImage()
	{
		mario_images[0] = View.loadImage("Images/mario1.png");
		mario_images[1] = View.loadImage("Images/mario2.png");
		mario_images[2] = View.loadImage("Images/mario3.png");
		mario_images[3] = View.loadImage("Images/mario4.png");
		mario_images[4] = View.loadImage("Images/mario5.png");
	}

	// updates mario's position vertically
	void MarioJump()
	{
		if(numFramesInAir < 5)
			vert_vel -= 20;
	}

	boolean isMario()
	{
		return true;
	}
	
	// saves mario's previous coordinates to allow him to exit a collision
	void savePreviousCoordinates()
	{
		px = x;
		py = y;
	}
	
	// uses previous coordinates to navigate out of a tube collision
	void getOutOfTube (Tube t)
	{
		int marioRight = x + width;
		int marioLeft = x;
		int tubeRight = t.x + t.width;
		int tubeLeft = t.x;
		int marioToes = y + height;
		int tubeTop = t.y;
		
		if(marioRight >= tubeLeft && px+width <= tubeLeft)
			x = t.x - width;
		if(marioLeft <= tubeRight && px >= tubeRight)
			x = tubeRight;
		if(marioToes >= tubeTop && py+height <= tubeTop)
		{
			y = t.y - height;
			vert_vel = 0;
			numFramesInAir = 0;
		}
	}
	
	void update()
	{
		vert_vel += 12;
		y += vert_vel;
		numFramesInAir++;
		
		if(y > 320)
		{
			vert_vel = 0;
			y = 320;
			numFramesInAir = 0;
		}
		if (y < 0)
		{
			y = 0;
		}
			
	
	}
	
	// cycles through mario's images for running
	void updateMarioImage()
	{
		marioImageNum++;
		if(marioImageNum > 4)    
			marioImageNum = 0;

	}
	
	Json marshal()
	{
	    Json ob = Json.newObject();
	    ob.add("x", x);
	    ob.add("y", y);
	    return ob;
	}

	
	void drawSprite(Graphics g, int scrollPos)
	{
		if(flip)
			g.drawImage(mario_images[marioImageNum], x - scrollPos + width, y, -width, height, null);
		else
			g.drawImage(mario_images[marioImageNum], x - scrollPos, y, width, height, null);

	}
}
