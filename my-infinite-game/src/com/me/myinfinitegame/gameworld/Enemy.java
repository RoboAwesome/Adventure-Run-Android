package com.me.myinfinitegame.gameworld;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//Abstract class extended by all enemies in the game
public abstract class Enemy {
	
	private Vector2 position;
	private Rectangle bounds;
	private Vector2 velocity;
	
	private float stateTime;
	//Each enemy should have different movement behavior depending on how
	//well the player has been performing (not fully implemented yet)
	public enum Difficulty {Easy, Medium, Hard, VeryHard, FckThis};
	
	private Difficulty diffuclty;
	
	public Difficulty getDiffuclty() {
		return diffuclty;
	}
	public float getStateTime() {
		return stateTime;
	}
	public Vector2 getPosition() {
		return position;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public Vector2 getVelocity() {
		return velocity;
	}
	public abstract void update();
	
	

}
