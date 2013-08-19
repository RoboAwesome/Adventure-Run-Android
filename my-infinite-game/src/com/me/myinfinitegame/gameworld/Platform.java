package com.me.myinfinitegame.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//This class is left over from an earlier version of the game that required more traditional platforming
//I may have use for it in the future
public class Platform extends CollidableObject{
	private Vector2 position;
	private Rectangle bounds;
	private Types type = Types.Platform;
	
	private static final float PLATFORM_WIDTH = 256f/(Gdx.graphics.getWidth()/5f);
	private static final float PLATFORM_HEIGHT = 70f/(Gdx.graphics.getHeight()/5f);
	
	public Platform(Vector2 position){
		this.position = position;
		this.bounds = new Rectangle(position.x, position.y,PLATFORM_WIDTH, PLATFORM_HEIGHT);
	}

	public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Types getType() {
		return type;
	}

	public static float getPlatformWidth() {
		return PLATFORM_WIDTH;
	}

	public static float getPlatformHeight() {
		return PLATFORM_HEIGHT;
	}
	
}
