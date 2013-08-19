package com.me.myinfinitegame.gameworld;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.myinfinitegame.gameworld.Coin.Value;

//The game world is made up of tilemaps which hold all of the game data and are randomly generated 
//as the player progresses through the world
public class TileMap {
	//2D array of tiles
	public Tile[][] map;
	//The width and height of the map in tiles and the coordinates that represent the maps origin
	private int width, height, x, y;
	private World world;
	private Random r = new Random();
	private Array<Coin> coins;
	private Array<Block> blocks;
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getX() {
		return x;
	}
	//Constructor
	public TileMap(int width, int height, int x, int y, World world){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.world = world;
		//Start with empty array of coins, blocks and tiles
		coins = new Array<Coin>();
		blocks = new Array<Block>();
		map = new Tile[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				map[i][j] = new Tile(new Vector2(i + x,j));
			}
		}
		//Then randomly generate tile pieces, coins and blocks
		generateGround();
		generateCoins();
		generateBlocks();
	}
	//Place an object somewhere in the map
	public void placeObject(CollidableObject object, int x, int y){
		map[x][y].setObject(object);
		world.getCollidableObjects().add(object);
		
	}
	//Removes all coins, objects and blocks from the map
	public void removeAllObjects(){
		Array<CollidableObject> objects = new Array<CollidableObject>();
		for(int i =0;i < width; i++){
			for(int j = 0; j < height; j++){
				if(map[i][j].getObject() != null){
					objects.add(map[i][j].getObject());
				}
				
			}
		}
		world.getCollidableObjects().removeAll(objects, false);
		//Remove old coins
		world.getCoins().removeAll(coins, false);
		world.getBlocks().removeAll(blocks, false);
	}
	//Returns a tile at a specfic location in the array
	public Tile getTile(int x, int y){
		return map[x][y];
	}
	//Randomly generates the ground tiles for a map
	public void generateGround(){
		for(int i = 0; i < width; i++){
			placeObject(new Ground(new Vector2(i + x,-.1f)),i,0);
		}
		generatePits();
	}
	//Randomly generates pit falls
	public void generatePits(){
		//Randomly decides how many pits the map will have total. Between 4 and 15
		int numberOfPits = r.nextInt(15) + 4;
		int makeCoin;
		for(int i = 6; i < numberOfPits; i++){
			int location = r.nextInt(95) + 4;
			world.getCollidableObjects().removeValue(map[location][0].getObject(),false);
			map[location][0].setObject(null);
			world.getCollidableObjects().removeValue(map[location + 1][0].getObject(),false);
			map[location + 1][0].setObject(null);
			//50/50 chance of generating a gold coin over the pit
			makeCoin = r.nextInt(50);
			if(makeCoin >= 25){
				world.getCoins().add(new Coin(new Vector2(location + (Tile.width/2), .95f), Value.Gold));
			}
			
		}
		
	}
	public void generateBlocks(){
		int locationX;
		int locationY;
		int length;
		for(int i = 0; i < 20; i++){
			//Choose an X coordinate greater than 4 (to avoid starting on a block)
			locationX = r.nextInt(95) + 4;
			//Choose a Y coordinate between 1 and 4
			locationY = r.nextInt(4) + 1;
			//Determines how many blocks to create in that location
			length = r.nextInt(2) + 1;
			for(int j = 0; j < length;j++){
				blocks.add(new Block(new Vector2(locationX + x, locationY + j*Block.getBlockHeight())));
			}
		}
		world.getBlocks().addAll(blocks);
	}
	//Not used currently
	public void generatePlatforms(){
		int numberOfPlatforms = r.nextInt(11) + 4;
		int platformLength;
		int locationX;
		int locationY;
		for(int i = 0; i < numberOfPlatforms; i++){
			platformLength = r.nextInt(9) + 1;
			locationX = r.nextInt(96) + 4;
			locationY = r.nextInt(2) + 1;
			for(int j = 0; j < platformLength; j++){
				placeObject(new Platform(new Vector2(locationX + x + (j*Platform.getPlatformWidth()), locationY)), locationX, locationY);
			}
			
			
		}
	}
	//Generates coins
	public void generateCoins(){
		int instances = r.nextInt(15) + 5;
		int length;
		int location;
		int value;
		float x = 0,y = 0;
		for(int i = 0; i < instances; i++){
			length  = r.nextInt(5) + 1;
			location =  r.nextInt(2);
			if(location == 0){
				x = 2;
				y = .9f;
			}
			else if(location == 1){
				x = 3;
				y = 3;
			}
			else if (location == 2){
				x = 1;
				y = 2;
			}
			for(int j = 0; j < length; j++){
				coins.add(new Coin(new Vector2(x + (j*Coin.COIN_WIDTH) + (i*5) + this.x, y), Value.Silver));
			}
		}
		world.getCoins().addAll(coins);
	}
	

}
