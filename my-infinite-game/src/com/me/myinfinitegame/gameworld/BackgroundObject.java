package com.me.myinfinitegame.gameworld;

import com.badlogic.gdx.math.Vector2;

//Class describing an object the player cannot interact with
public class BackgroundObject{
	//Position of the object in world coordinates(y up)
	private Vector2 position;
	//ObjectType determines which image to use
	public enum ObjectType {Cloud,LongHill,ShortHill, Cloud2, Cloud3, Bush, Grass, AlienGrass};
	private ObjectType objectType;
	//Boolean to indicate if image is flipped on the x axis
	private boolean flipped = false;
	
	public Vector2 getPosition() {
		return position;
	}

	public ObjectType getObjectType() {
		return objectType;
	}
	public boolean isFlipped() {
		return flipped;
	}
	//Constructor 
	public BackgroundObject(Vector2 position, ObjectType objectType, Boolean flipped){
		this.position = position;
		this.objectType = objectType;
		this.flipped = flipped;
	}

}
