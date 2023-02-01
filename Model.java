/// TANNER MECHAM 
/// MARIO GAME PROJECT
/// 10-23-2020

import java.io.File;
import java.util.ArrayList;


class Model
{
	int dest_x;
	int dest_y;
	ArrayList<Sprites> sprites;
	Mario mario;
	

	Model()
	{
		sprites = new ArrayList<Sprites>();
		mario = new Mario(70, 320);
		sprites.add(mario);
	}
	
	public void setDestination(int x, int y)
	{
		this.dest_x = x;
		this.dest_y = y;
	}
	
	// Loads map upon running game
	public void loadMap()
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
			unmarshal(j);
			System.out.println("map.json loaded...");
			
		}

	}
	
	public void update()
	{
		int spritesArraySize = sprites.size();
		for(int i = 0; i < spritesArraySize; i++)
		{
			Sprites s = sprites.get(i);
			if(sprites.get(i).type.equals("tube"))
			{
				Tube tube = (Tube)sprites.get(i);
				if(s.collision(tube, mario))
				{
					mario.getOutOfTube(tube);
				}
			}
			s.update();
			spritesArraySize = sprites.size(); // resets arraylist size value to avoid out of bounds error from removed objects
		}
		
		
	}
	
	// saves objects to json file
	Json marshal()
	{
		Json ob = Json.newObject();
		Json spritesOb = Json.newObject();
		Json tempList = Json.newList();
		ob.add("sprites", spritesOb);
		spritesOb.add("tubes", tempList);
		
		for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).type.equals("tube"))
			{
				Tube t = (Tube)sprites.get(i);
				tempList.add(t.marshal());
			}
		}
		
		tempList = Json.newList();
		spritesOb.add("goombas", tempList);
		for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).type.equals("goomba"))
			{
				Goomba g = (Goomba)sprites.get(i);
				tempList.add(g.marshal());
			}
		}

		return ob;
	}
	
	// loads objects from json file
	void unmarshal(Json ob)
	{
		sprites = new ArrayList<Sprites>();
		sprites.add(mario);
		Json jsonList = ob.get("sprites");
		Json tubesList = jsonList.get("tubes");
		Json goombasList = jsonList.get("goombas");
		for (int i = 0; i < tubesList.size(); i++)
		{
			sprites.add(new Tube(tubesList.get(i), this));
		}
		for (int i = 0; i < goombasList.size(); i++)
		{
			sprites.add(new Goomba(goombasList.get(i), this));
		}
		
	}
	
	// adds a new tube or removes if one is there already - code borrowed from Prof. Streeter
	public void addNewTube(int mouse_x, int mouse_y)
	{
		Tube t = new Tube(mouse_x, mouse_y, this);
		boolean tubeAlreadyExists = false;
		
		for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).type.equals("tube"))
			{
				Tube temp = (Tube)sprites.get(i);
				if(temp.IsThereATubeHere(mouse_x, mouse_y))
				{
					sprites.remove(temp);
					tubeAlreadyExists = true;
					break;
				}

			}
			
		}
		
		if(!tubeAlreadyExists)
			sprites.add(t);
	}
	
	// Adds a new goomba where the mouse is clicked
	public void addNewGoomba(int mouse_x, int mouse_y)
	{
		Goomba g = new Goomba(mouse_x, mouse_y, this);
		sprites.add(g);
			
		
	}
	
	// Adds a new fireball when 'ctrl' is clicked - at mario's location (from controller)
	public void addNewFireball(int x, int y)
	{
		Fireball f = new Fireball(x, y, this);
		sprites.add(f);
	}
	
}