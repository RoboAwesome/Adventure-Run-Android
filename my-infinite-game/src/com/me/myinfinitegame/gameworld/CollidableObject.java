package com.me.myinfinitegame.gameworld;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//Abstract class extended by all  tile objects that can make contact with the player
public abstract class CollidableObject{
	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();
	
	public enum Types{Ground, Water, Platform};
	private Types type;
	
	public Vector2 getPosition(){
		return position;
	}
	public Rectangle getBounds(){
		return bounds;
	}
	public Types getType() {
		return type;
	}

}
