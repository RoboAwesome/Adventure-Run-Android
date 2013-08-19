package com.me.myinfinitegame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.myinfinitegame.screens.MenuScreen;
import com.me.myinfinitegame.screens.TheGameScreen;

//Game object 
public class MyGdxGame extends Game{
	
	@Override
	public void create() {	
		setScreen(new MenuScreen(this, true, true));
	}


}
