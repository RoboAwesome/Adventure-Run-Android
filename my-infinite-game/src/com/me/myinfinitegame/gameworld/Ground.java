package com.me.myinfinitegame.gameworld;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ground extends CollidableObject {
	private Vector2 position;
	private Rectangle bounds;
	private static final float GROUND_WIDTH = 1f;
	private static final float GROUND_HEIGHT= 1f;
	private Types type = Types.Ground; 
	
	public static float getGroundWidth() {
		return GROUND_WIDTH;
	}

	public static float getGroundHeight() {
		return GROUND_HEIGHT;
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

	public Ground(Vector2 position){
		this.position = position;
		this.bounds = new Rectangle(position.x,position.y, GROUND_WIDTH, GROUND_HEIGHT);
	}

}
