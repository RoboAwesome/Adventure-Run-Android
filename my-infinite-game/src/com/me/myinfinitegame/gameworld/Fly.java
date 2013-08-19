package com.me.myinfinitegame.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Fly extends Enemy{
	private Vector2 position;
	private Rectangle bounds;
	private Vector2 velocity;
	
	private World world;
	
	//For use in animation
	private float stateTime = 0;
	
	private Difficulty diffuclty;
	
	public static final float FLY_WIDTH = 72/(Gdx.graphics.getWidth()/5f);
	public static final float FLY_HEIGHT = 36/(Gdx.graphics.getHeight()/5f);
	
	public Difficulty getDiffuclty() {
		return diffuclty;
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
	
	public float getStateTime() {
		return stateTime;
	}
	//Constructor
	public Fly(Vector2 position, Difficulty difficulty, World world){
		this.position = position;
		this.bounds = new Rectangle(position.x, position.y,FLY_WIDTH,FLY_HEIGHT);
		velocity = new Vector2();
		//Determine how the fly will move from difficulty setting
		switch (difficulty){
		case Easy:
			easyMove();
			break;
		default:
			easyMove();
			break;
		}	
	}
	//Update method that is called every frame
	public void update(){
		//Advance the position in x and y directions
		position.x += velocity.x*Gdx.graphics.getDeltaTime();
		position.y += velocity.y*Gdx.graphics.getDeltaTime();
		//Update the bounds to the new position
		bounds.x = position.x;
		bounds.y = position.y;
		//Update the state time
		stateTime += Gdx.graphics.getDeltaTime();
		
	}
	
	//Simple fly movement. Moves in a straight line to the left
	public void easyMove(){
		velocity.x = -1.25f;
		
	}

}
