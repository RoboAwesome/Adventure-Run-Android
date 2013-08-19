package com.me.myinfinitegame.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.me.myinfinitegame.controller.WorldController;
import com.me.myinfinitegame.gameworld.World;
import com.me.myinfinitegame.graphics.WorldRenderer;
import com.me.myinfinitegame.sound.SoundController;
import com.me.myinfinitegame.sound.SoundController.Sounds;

//ApplicationListener() object that controls the game while the GameScreen is active
public class TheGameScreen implements Screen, InputProcessor{
	//Game variable used so only one GameScreen is ever created
	Game game;
	WorldRenderer renderer;
	World world;
	WorldController controller;
	private SoundController soundControl;
	public enum GameState{Running, Paused, Starting, Restarting, GameOver};
	private GameState state;
	private float countdown = 10800;
	private boolean soundOn = true;
	private boolean musicOn = true;
	

	public float getCountdown() {
		return countdown;
	}
	
	public SoundController getSoundControl() {
		return soundControl;
	}
	
	public boolean isSoundOn() {
		return soundOn;
	}

	public boolean isMusicOn() {
		return musicOn;
	}

	public GameState getState() {
		return state;
	}
	public void setState(GameState state) {
		this.state = state;
	}
	
	//Constructor 
	public TheGameScreen(Game game, boolean soundOn, boolean musicOn){
		this.game = game;
		//The game world
		world = new World(this);
		//The input
		controller = new WorldController(world);
		//The renderer which draws and updates the game world in response to input
		renderer = new WorldRenderer(world, false, this);
		//Inital game states
		state = GameState.Starting;
		this.soundOn = soundOn;
		this.musicOn = musicOn;
		//Sound control
		soundControl = new SoundController(this);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		//Render and update according to game state
		//In normal running state update the world, process input and play music
		if(state == GameState.Running){
			if(musicOn)
				soundControl.playMusic();
			controller.processInput();
			world.update();
		}
		//If the game is just starting run the countdown method
		if(state == GameState.Starting){
			if(countdown >= 0){
				countdown -= 2000*Gdx.graphics.getDeltaTime();
			}
			if(countdown < 0){
				state = GameState.Running;
				countdown = 10800;
			}
		}
		//Don't render anything while restarting
		if(state != GameState.Restarting){
			renderer.render();
		}
		//To restart create new instances of the world, controller and renderer then set state to starting
		if(state == GameState.Restarting){
			world = new World(this);
			controller = new WorldController(world);
			renderer = new WorldRenderer(world, false, this);
			state = GameState.Starting;
		}
		
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		soundControl.stopMusic();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	//InputProcessor methods
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Keys.SPACE){
			if(state == GameState.Running){
				soundControl.playSound(Sounds.Jump);
				controller.jumpPressed();
			}	
		}	
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Keys.SPACE){
			if(state == GameState.Running){
				controller.jumpReleased();
			}
			if(state == GameState.GameOver)
				state = GameState.Restarting;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		soundControl.playSound(Sounds.Jump);
		controller.jumpPressed();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if(state == GameState.Running){
			controller.jumpReleased();
		}
		if(state == GameState.GameOver)
			state = GameState.Restarting;
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
