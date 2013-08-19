package com.me.myinfinitegame.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.myinfinitegame.collision.CollisionManager;

public class Player {
	//Describing player movement
	private Vector2 position, velocity, acceleration;
	//Bounds is used for collision between world and player and hitbounds is used for player-enemy collisions
	private Rectangle bounds, hitBounds;
	
	//Player jumping speed and and dimensions
	public static final float JUMPING_SPEED = 2.5f;
	private static final float PLAYER_HEIGHT = 96f/(Gdx.graphics.getHeight()/5);
	private static final float PLAYER_WIDTH = 75f/(Gdx.graphics.getWidth()/5);
	
	public enum State{Walking,Jumping,Idle, Dead};
	private State state = State.Walking;
	
	private float stateTime = 0;
	private CollisionManager collisionManager;
	
	//Getters and setters
	public CollisionManager getCollisionManager() {
		return collisionManager;
	}

	public void setCollisionManager(CollisionManager collisionManager) {
		this.collisionManager = collisionManager;
	}

	public Vector2 getPosition() {
		return position;
	}

	public State getState() {
		return state;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public static float getPlayerHeight() {
		return PLAYER_HEIGHT;
	}

	public static float getPlayerWidth() {
		return PLAYER_WIDTH;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setState(State state) {
		this.state = state;
	}
	public Rectangle getHitBounds() {
		return hitBounds;
	}
	
	//Constructor
	public Player(Vector2 position, World world){
		//Set position and hit boxes
		this.position = position;
		this.bounds = new Rectangle(position.x, position.y, PLAYER_WIDTH, PLAYER_HEIGHT);
		//Make the enemy hitbox slightly smaller to avoid player rage quits
		this.hitBounds = new Rectangle(position.x +.2f, position.y -.2f, PLAYER_WIDTH - .2f, PLAYER_HEIGHT - .2f);
		//The player is constantly moving forward every frame
		velocity = new Vector2(2f,0);
		collisionManager = new CollisionManager(world, this);
		
	}
	//Jump method
	public void jump(){
		state = State.Jumping;
		velocity.y = 3f;
	}
	//Update method that will be called every frame
	public void update(){
		//Update state time
		stateTime+= Gdx.graphics.getDeltaTime();
		//If you're not dead
		if(state != State.Dead){
			//Process collisions
			collisionManager.handleCollisions();
		}
		//If you're dead 
		if(state == State.Dead){
			//Stop moving
			velocity.x = 0;
		}
		//Update position and bounds
		position.add(velocity.tmp().mul(Gdx.graphics.getDeltaTime()));
		bounds.x = position.x;
		bounds.y = position.y;
		hitBounds.x = position.x;
		hitBounds.y = position.y;
		//Check if you died
		if(bounds.y + (PLAYER_HEIGHT/2) < .5f  && state != State.Dead){
			state = State.Dead;
			
		}
		
	}

}
