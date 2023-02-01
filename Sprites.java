/// TANNER MECHAM 
/// MARIO GAME PROJECT
/// 10-23-2020


import java.awt.Graphics;

abstract class Sprites
{
	int x, y;
	int width, height;
	String type;
	abstract void update();
	abstract void drawSprite (Graphics g, int scrollPos);
	
	boolean isTube()	{	return false;	}
	boolean isMario()	{	return false;	}
	boolean isGoomba()	{	return false;	}
	boolean isFireball()	{	return false;	}
	boolean isBrick()	{	return false;	}
	
	boolean collision(Sprites a, Sprites b)
	{
		int spriteBRight = b.x + b.width;
		int spriteBLeft = b.x;
		int spriteARight = a.x + a.width;
		int spriteALeft = a.x;
		int spriteBTop = b.y;
		int spriteBBottom = b.y + b.height;
		int spriteATop = a.y;
		int spriteABottom = a.y + a.height;
		
		if(spriteBRight < spriteALeft)
			return false;
		if(spriteBLeft > spriteARight)
			return false;
		if(spriteBBottom < spriteATop)
			return false;
		if(spriteBTop > spriteABottom)
			return false;
		
		return true;
		
	}
}