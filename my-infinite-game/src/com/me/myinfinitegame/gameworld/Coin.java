package com.me.myinfinitegame.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//Coins collectible by the player for points
public class Coin {
	public enum Value{Gold, Silver, Bronze};
	private Value value;
	private Vector2 position;
	private Rectangle bounds;
	
	public static final float COIN_WIDTH = 35/(Gdx.graphics.getWidth()/5f);
	public static final float COIN_HEIGHT = 35/(Gdx.graphics.getHeight()/5f);
	
	public Value getValue() {
		return value;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	//Constructor
	public Coin(Vector2 position, Value value){
		this.position = position;
		this.value = value;
		bounds = new Rectangle(position.x, position.y,COIN_WIDTH, COIN_HEIGHT);
	}

}
