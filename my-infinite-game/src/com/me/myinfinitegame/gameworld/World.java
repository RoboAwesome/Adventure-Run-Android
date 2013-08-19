package com.me.myinfinitegame.gameworld;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.myinfinitegame.collision.CollisionManager.CollisionTypes;
import com.me.myinfinitegame.gameworld.BackgroundObject.ObjectType;
import com.me.myinfinitegame.gameworld.Enemy.Difficulty;
import com.me.myinfinitegame.gameworld.Player.State;
import com.me.myinfinitegame.screens.TheGameScreen;
import com.me.myinfinitegame.screens.TheGameScreen.GameState;

//Class that holds all map, player, item and enemy to specify game state and behavior 

public class World {
	
	private Player henson;
	private Array<BackgroundObject> backgroundObjects;
	private Array<CollidableObject> collidableObjects;
	private Array<Block> blocks;
	private Array<Coin> coins;
	private Array<Enemy> enemies;
	private Random r = new Random();
	//Used to ensure a single instance of the game is used consistently throughout
	private TheGameScreen game;
	private int score;
	private static final float GRAVITY = 10f;
	
	// Keeps track of where the camera is located
	private float camerax;
	//The game world up is made up of a collection randomly generated maps. These maps are
	//added and removed as the player progresses
	private Array<TileMap> maps;
	//Getters and Setters
	public Player getHenson() {
		return henson;
	}

	public void setHenson(Player henson) {
		this.henson = henson;
	}

	public Array<TileMap> getMaps() {
		return maps;
	}

	public float getCamerax() {
		return camerax;
	}

	public void setCamerax(float camerax) {
		this.camerax = camerax;
	}

	public Array<BackgroundObject> getBackgroundObjects() {
		return backgroundObjects;
	}

	public Array<CollidableObject> getCollidableObjects() {
		return collidableObjects;
	}
	
	public Array<Block> getBlocks() {
		return blocks;
	}
	
	public void setBlocks(Array<Block> blocks) {
		this.blocks = blocks;
	}

	public Array<Coin> getCoins() {
		return coins;
	}

	public Array<Enemy> getEnemies() {
		return enemies;
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public TheGameScreen getGame() {
		return game;
	}
	//Constructor
	public World(TheGameScreen game) {
		this.game = game;
		score = 0;
		maps = new Array<TileMap>();
		backgroundObjects = new Array<BackgroundObject>();
		collidableObjects = new Array<CollidableObject>();
		coins = new Array<Coin>();
		enemies = new Array<Enemy>();
		blocks = new Array<Block>();
		maps.add(new TileMap(100,5,0,0,this));
		createWorld();
	}
	//Update method that is called every frame
	public void update() {
		//Update the player
		henson.update();
		//Check player state
		if(henson.getState() == State.Dead){
				game.setState(GameState.GameOver);
		}
		applyGravity();
		//Creates a new map when you near the end of the current one
		if((henson.getPosition().x%90) <=.1 && henson.getPosition().x > 1){
			createNewMap(getCurrentMap().getX() + 100);
		}
			
		//Delete the maps you've passed
		for(TileMap map: maps){
			if(map.getTile(99, 0).getPosition().x < camerax - 1){
				cleanMap(map);
			}
		}
		//Update and delete/add enemies as they pass
		for(Enemy e: enemies){
			e.update();
			if(e.getPosition().x + e.getBounds().width < camerax){
				enemies.removeValue(e, false);
				enemies.add(new Fly(new Vector2(camerax + 40, r.nextInt(3) + 1), Difficulty.Easy, this));
			}
		}
	}
	//Finds which map the player is currently in
	public TileMap getCurrentMap(){
		TileMap currentMap = null;
		for(TileMap map: maps){
			if(map.getTile(0, 0).getPosition().x <= henson.getPosition().x && map.getTile(99, 0).getPosition().x > henson.getPosition().x){
				currentMap = map;

			}

		}
		return currentMap;
	}
	//Creates a map at the specfied x coordinate
	public void createNewMap(int x){
		if(!hasX(x)){
			maps.add(new TileMap(100,5,x,0,this));
			System.out.println("New map at ("+x+", 0)");
		}
		
		
	}
	//Helper function to avoid creation of duplicate maps
	public boolean hasX(int x){
		boolean alreadyHasX = false;
		for(TileMap map: maps){
			if(map.getX() == x){
				alreadyHasX = true;
			}
			else{
				alreadyHasX = false;
			}
		}
		return alreadyHasX;
	}
	//Removes the past map from memory 
	public void cleanMap(TileMap map){
		map.removeAllObjects();
		System.out.println("removed map at"+map.getX());
		maps.removeValue(map, false);
		
	}
	//Applies gravity to the world
	public void applyGravity() {
		if (!henson.getCollisionManager().collisions.get(CollisionTypes.Bottom)) {
			henson.getVelocity().y -= GRAVITY*Gdx.graphics.getDeltaTime();
		}

	}

	// Creates the intial world (To ensure you start on a ground tile)
	public void createWorld() {
		henson = new Player(new Vector2(0, .9f), this);
		collidableObjects.add(new Ground(new Vector2(-1,-.1f)));
		collidableObjects.add(new Ground(new Vector2(-2, -.1f)));
		//Removed enemies, their presence just made the game to unbalanced
		//Will likely reimplement in the future once fixed
		enemies.add(new Fly(new Vector2(10,2.5f), Difficulty.Easy, this));
//		enemies.add(new Fly(new Vector2(7,3), Difficulty.Easy, this));

	}

}
