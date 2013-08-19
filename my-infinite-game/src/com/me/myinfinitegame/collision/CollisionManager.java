package com.me.myinfinitegame.collision;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.me.myinfinitegame.gameworld.Block;
import com.me.myinfinitegame.gameworld.Coin;
import com.me.myinfinitegame.gameworld.Enemy;
import com.me.myinfinitegame.gameworld.Ground;
import com.me.myinfinitegame.gameworld.Player;
import com.me.myinfinitegame.gameworld.Player.State;
import com.me.myinfinitegame.gameworld.Tile;
import com.me.myinfinitegame.gameworld.TileMap;
import com.me.myinfinitegame.gameworld.World;
import com.me.myinfinitegame.sound.SoundController.Sounds;

//Class that manages processes all collisions between the player and the world
public class CollisionManager {
	World world;
	Player henson;

	public enum CollisionTypes {
		Bottom, Top
	};

	private int consecutiveCoinCounter = 0;
	// Counts time between consecutive coin collections
	private float coinTimer = 0;
	// Hashmap of all collision types and their values
	public Map<CollisionTypes, Boolean> collisions;

	// Constructor
	public CollisionManager(World world, Player henson) {
		this.henson = henson;
		this.world = world;
		collisions = new HashMap<CollisionTypes, Boolean>();
		// Initialize hashmap values
		collisions.put(CollisionTypes.Bottom, false);
		collisions.put(CollisionTypes.Top, false);
	}

	// Collision processing algorithm
	public void handleCollisions() {
	  //Checks player-tile collisions
		//For each map in the world
		for (TileMap map : world.getMaps()) {
			//And each row in that map
			for (Tile[] row : map.map) {
				//And each tile in that row
				for (Tile t : row) {
					// Only check tiles that are below the player
					if (t.getPosition().y <= henson.getPosition().y) {
						//Calculate distance from the left
						float xdistLeft = henson.getBounds().x - t.getBounds().x;
						//Calculate distance from the right
						float xdistRight = (henson.getBounds().x + Player.getPlayerWidth())
								- (t.getBounds().x + Ground.getGroundWidth());
					//If the ground tile is reasonably close to the player in the x direction
						if (xdistLeft >= 0 && xdistRight <= 1) {
						//If the tile has a collidable object in it
							if (t.getObject() != null) {
							 //If it does check if it overlaps with the player
								if (t.getObject().getBounds()
										.overlaps(henson.getBounds())) {
									//If it is then set any downward motion to zero
									if (henson.getVelocity().y <= 0) {
										henson.getVelocity().y = 0;
									}
									//And don't allow position to drop below .9 (ground level)
									if (henson.getPosition().y <= .9f) {
										henson.getPosition().y = .9f;
									}
									//Set the value for Bottom in the hashmap to true
									collisions.get(collisions.put(
											CollisionTypes.Bottom, true));
								}
							 //If the player and tile are not overlapping
								else {
									//Set the value for Bottom in the hashmap to false
									collisions.get(collisions.put(
											CollisionTypes.Bottom, false));
								}
							} 
						//If there are no collidable objects close to the player
							else {
								//Set the value for Bottom in the Hashmap to false
								collisions.get(collisions.put(
										CollisionTypes.Bottom, false));
							}
						}
					}

				}
			}
		}
		//Checks player - coin collisions
		//Check each coin in the current world
		for (Coin c : world.getCoins()) {
			//If any of them overlap with the player
			if (c.getBounds().overlaps(henson.getBounds())) {
				//Add one to the coin counter and start the coin timer
				consecutiveCoinCounter++;
				startTimer();
				//Switch case to determine which sound to play
				switch (consecutiveCoinCounter) {
				default:
					world.getGame().getSoundControl().playSound(Sounds.Ding1);
					break;
				case (1):
					world.getGame().getSoundControl().playSound(Sounds.Ding1);
					break;
				case (2):
					world.getGame().getSoundControl().playSound(Sounds.Ding2);
					break;
				case (3):
					world.getGame().getSoundControl().playSound(Sounds.Ding3);
					break;
				case (4):
					world.getGame().getSoundControl().playSound(Sounds.Ding4);
					break;
				}
				//If the player goes longer than .5 seconds without getting a coin the timer resets
				if (coinTimer >= .5) {
					resetTimer();
				}
				//Remove coin from the world and add points
				world.getCoins().removeValue(c, false);
				world.setScore(world.getScore() + 100);
			}
		}
		//Check for player - enemy collisions
		for (Enemy e : world.getEnemies()) {
			if (e.getBounds().overlaps(henson.getHitBounds())) {
				henson.setState(State.Dead);
			}
		}
		//Check player - block collisions
		for (Block b : world.getBlocks()) {
			if (b.collisionDetect(henson))
				henson.setState(State.Dead);
		}
	}
	//Method that starts the coin timer
	public void startTimer() {
		coinTimer += Gdx.graphics.getDeltaTime();
	}
	//Method that resets the coin timer and coun counter
	public void resetTimer() {
		coinTimer = 0;
		consecutiveCoinCounter = 0;
	}

}
