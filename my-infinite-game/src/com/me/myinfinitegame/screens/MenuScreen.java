package com.me.myinfinitegame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.myinfinitegame.gameworld.BackgroundObject;
import com.me.myinfinitegame.gameworld.CollidableObject;
import com.me.myinfinitegame.gameworld.Ground;
import com.me.myinfinitegame.graphics.MenuRenderer;
import com.me.myinfinitegame.sound.SoundController;
//This is the ApplicationListener object that controls the game when the Menu screen is active
public class MenuScreen implements Screen, InputProcessor {
	Game game;
	MenuRenderer renderer;
	SoundController soundControl;

	public enum MenuState {
		Main, Instructions, Options, Credits
	};

	private MenuState state = MenuState.Main;
	private Array<CollidableObject> ground = new Array<CollidableObject>();
	private Array<BackgroundObject> objects = new Array<BackgroundObject>();
	private boolean soundOn = true;
	private boolean musicOn = true;

	//Getters and Setters
	public MenuState getState() {
		return state;
	}

	public boolean isSoundOn() {
		return soundOn;
	}

	public boolean isMusicOn() {
		return musicOn;
	}
	
	//Constructor
	public MenuScreen(Game game, boolean soundOn, boolean musicOn) {
		this.game = game;
		renderer = new MenuRenderer(this);
		soundControl = new SoundController(this);
	}

	// Generates one of several predetermined screens (feature not implemented)
	public void generateScreen(int screen) {
		if (screen == 1) {
			for (int i = 0; i < 5; i++) {
				ground.add(new Ground(new Vector2(i, -.1f)));
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Keys.ENTER)
			game.setScreen(new TheGameScreen(game, soundOn, musicOn));
		if (keycode == Keys.R) {
			System.out.println(renderer.getViewportBuilder().getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()).getWidth());
			System.out.println(renderer.getViewportBuilder().getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()).getHeight());
			System.out.println(renderer.getVirtualCamera().viewportHeight);
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
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if (state == MenuState.Main) {
//			if ((screenX >= 560 && screenX <= 720)
//					&& (screenY <= 220 && screenY >= 200))
				game.setScreen(new TheGameScreen(game, soundOn, musicOn));
//			if ((screenX >= 545 && screenX <= 735)
//					&& (screenY <= 275 && screenY >= 250))
//				// state = MenuState.Instructions;
//				state = MenuState.Options;
//			if ((screenX >= 590 && screenX <= 690)
//					&& (screenY <= 325 && screenY >= 300))
//				state = MenuState.Credits;
//		}
//		if(state == MenuState.Options){
//			if ((screenX > 430 && screenX < 530)
//					&& (screenY < 320 && screenY > 300)) {
//				state = MenuState.Main;
//			}
//			if ((screenX > 430 && screenX < 575)
//					&& (screenY < 220 && screenY > 200)) {
//				if(musicOn){
//					musicOn = false;
//					soundControl.stopMusic();
//				}
//					
//				else{
//					musicOn = true;
//					soundControl.playMusic();
//				}
//					
//			}
//			if ((screenX > 785 && screenX < 940)
//					&& (screenY < 220 && screenY > 200)) {
//				if(soundOn)
//					soundOn = false;
//				else
//					soundOn = true;
//			}
//		}
//		if(state == MenuState.Credits){
//			if((screenX > 345 && screenX < 400) && (screenY < 325 && screenY > 300))
//				state = MenuState.Main;
		}
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
		if (state == MenuState.Main) {
			if ((screenX >= 560 && screenX <= 720)
					&& (screenY <= 220 && screenY >= 200)) {
				renderer.setStartColor(Color.BLACK);
			}

			else
				renderer.setStartColor(Color.WHITE);
			if ((screenX >= 545 && screenX <= 735)
					&& (screenY <= 275 && screenY >= 250)) {
				renderer.setOptionsColor(Color.BLACK);
			}

			else
				renderer.setOptionsColor(Color.WHITE);
			if ((screenX >= 590 && screenX <= 690)
					&& (screenY <= 325 && screenY >= 300)) {
				renderer.setCreditsColor(Color.BLACK);
			}

			else
				renderer.setCreditsColor(Color.WHITE);
			// if((screenX >= 585 && screenX <= 690) && (screenY <= 375 &&
			// screenY >= 350)){
			// renderer.setCreditsColor(Color.BLACK);
			// }
			//
			// else
			// renderer.setCreditsColor(Color.WHITE);
		}
		if (state == MenuState.Options) {
			if ((screenX > 430 && screenX < 530)
					&& (screenY < 320 && screenY > 300)) {
				renderer.backColor = Color.BLACK;
			} else
				renderer.backColor = Color.WHITE;
			if ((screenX > 430 && screenX < 575)
					&& (screenY < 220 && screenY > 200)) {
				renderer.musicColor = Color.BLACK;
			} else
				renderer.musicColor = Color.WHITE;
			if ((screenX > 785 && screenX < 940)
					&& (screenY < 220 && screenY > 200)) {
				renderer.soundColor = Color.BLACK;
			} else
				renderer.soundColor = Color.WHITE;
		}
		if( state == MenuState.Credits){
			if((screenX > 345 && screenX < 400) && (screenY < 325 && screenY > 300)){
				renderer.backColor2 = Color.BLACK;
			}
			else
				renderer.backColor2 = Color.WHITE;
		}

		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	//Called every frame
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		renderer.render();
		renderer.update();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		renderer.resize();
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(this);
		if (musicOn)
			soundControl.playMusic();
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
		soundControl.stopMusic();

	}

	public Array<CollidableObject> getGround() {
		return ground;
	}

	public Array<BackgroundObject> getObjects() {
		return objects;
	}

}
