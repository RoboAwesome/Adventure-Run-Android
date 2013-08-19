package com.me.myinfinitegame.controller;

import java.util.HashMap;
import java.util.Map;

import com.me.myinfinitegame.collision.CollisionManager.CollisionTypes;
import com.me.myinfinitegame.gameworld.Player;
import com.me.myinfinitegame.gameworld.Player.State;
import com.me.myinfinitegame.gameworld.World;

//Class that processes controller inputs
public class WorldController {
	
	public enum Buttons{Jump};
	//Hashmap containing buttons and their current values
	public Map<Buttons, Boolean> controller;
	private World world;
	private Player henson;
	//Constructor
	public WorldController(World world){
		//Initialize hashmap
		controller = new HashMap<Buttons,Boolean>();
		controller.put(Buttons.Jump, false);
		this.world = world;
		this.henson = world.getHenson();
		
	}
	//Method for when the jump key is down
	public void jumpPressed(){
		controller.get(controller.put(Buttons.Jump, true));
		
	}
	//Method for when the jump key is up
	public void jumpReleased(){
		controller.get(controller.put(Buttons.Jump,false));
	}
	
	public void processInput(){
		//If the jump button is down
		if(controller.get(Buttons.Jump)){
			//Set the players state to jumping
			henson.setState(State.Jumping);
			//And if they're not already too high
			if(henson.getPosition().y <= 4.4)
				//Set upward velocity
				henson.getVelocity().y = Player.JUMPING_SPEED;
		}
		//If the jump button is up
		if(!controller.get(Buttons.Jump)){
			henson.setState(State.Jumping);
			//Once the player has hit the floor once again
			if(henson.getCollisionManager().collisions.get(CollisionTypes.Bottom)){
				//Set the player state back to walking
				henson.setState(State.Walking);
			}
		}
	}

}
