package com.me.myinfinitegame.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//Each tile map is made up of these objects which are all of equal size
//But can hold different sized objects
public class Tile {
	//Dimensions and bounds
	public static final float height = Gdx.graphics.getHeight()/5f;
	public static final float width = Gdx.graphics.getWidth()/5f;
	private Rectangle bounds;
	private Vector2 position;
	//Object held within tile. To keep things simple, only one collidable object is allowed in a tile
	private CollidableObject object;
	
	public CollidableObject getObject() {
		return object;
	}

	public void setObject(CollidableObject object) {
		this.object = object;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Vector2 getPosition() {
		return position;
	}
	//Constructor
	public Tile(Vector2 position){
		this.position = position;
		this.bounds = new Rectangle(position.x, position.y, Tile.width,Tile.height);
		object = null;
	}

}
