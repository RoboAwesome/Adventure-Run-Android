package com.me.myinfinitegame.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//Blocker objects. Stationary and kill player upon contact
public class Block extends CollidableObject{
	
	private Vector2 position;
	//Bounds used for collision detection
	private Rectangle bounds;
	
	//Width and height of the block
	private static final float BLOCK_WIDTH = 51/(Gdx.graphics.getWidth()/5f);
	private static final float BLOCK_HEIGHT = 51/(Gdx.graphics.getHeight()/5f);
	
	//Constructor
	public Block(Vector2 position){
		this.position = position;
		this.bounds = new Rectangle(position.x,position.y,BLOCK_WIDTH,BLOCK_HEIGHT);
	}
	
	public boolean collisionDetect(Player henson){
		if(henson.getHitBounds().overlaps(bounds))
			return true;
		else
			return false;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public static float getBlockWidth() {
		return BLOCK_WIDTH;
	}

	public static float getBlockHeight() {
		return BLOCK_HEIGHT;
	}
	
}
