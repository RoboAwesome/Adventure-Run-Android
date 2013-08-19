package com.me.myinfinitegame.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.myinfinitegame.screens.MenuScreen;
import com.me.myinfinitegame.screens.MenuScreen.MenuState;

//This class renders all the graphics for the menu screen that is seen when the game is booted up
public class MenuRenderer {
	MenuScreen menu;

	private OrthographicCameraVirtualView virtualCamera;
	private VirtualViewport virtualViewport;
	private MultipleVirtualViewportBuilder viewportBuilder;
	
	private SpriteBatch spriteBatch;
	private BitmapFont font52, font44, font32;
	//Texture atlas and texture regions for all graphics on this screen
	private TextureAtlas objectAtlas;
	private TextureRegion ground, fence, midFence, openFence, endFence, fenceBroken, shortHill, longHill;
	private TextureRegion cloud1, cloud2, cloud3;
	//Initializing the colors for menu items
	public Color startColor = Color.WHITE, instructColor = Color.WHITE,
			optionsColor = Color.WHITE, creditsColor = Color.WHITE;
	public Color backColor = Color.WHITE, musicColor = Color.WHITE, soundColor = Color.WHITE , backColor2 = Color.WHITE;

	CharSequence title = "Adventure Run!";
	CharSequence credits = "Me, for making this damn thing \nKenNL for the Art";
	
	public MultipleVirtualViewportBuilder getViewportBuilder() {
		return viewportBuilder;
	}
	
	public OrthographicCameraVirtualView getVirtualCamera() {
		return virtualCamera;
	}

	//Constructor
	public MenuRenderer(MenuScreen menu) {
		this.menu = menu;
		//Set up cameras and viewports
		viewportBuilder = new MultipleVirtualViewportBuilder(640, 480, 1366, 768);
		virtualViewport = viewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		virtualCamera = new OrthographicCameraVirtualView(virtualViewport);
		//Have camera look at center
		virtualCamera.position.set(virtualViewport.getWidth()/2, virtualViewport.getHeight()/2, 0f);
		//Used for drawing all sprites
		spriteBatch = new SpriteBatch();
		//Load fonts and other assets
		font52 = new BitmapFont(Gdx.files.internal("data/MostlyMono.fnt"),
				Gdx.files.internal("data/MostlyMono_0.png"), false);
		font32 = new BitmapFont(Gdx.files.internal("data/MostlyMono32pt.fnt"),
				Gdx.files.internal("data/MostlyMono32pt_0.png"), false);
		font44 = new BitmapFont(Gdx.files.internal("data/MostlyMono44pt.fnt"),
				Gdx.files.internal("data/MostlyMono44pt_0.png"), false);
		loadTextures();
	}
	//Getters and Setters
	public BitmapFont getFont52() {
		return font52;
	}

	public BitmapFont getFont32() {
		return font32;
	}

	public void setStartColor(Color startColor) {
		this.startColor = startColor;
	}

	public void setInstructColor(Color instructColor) {
		this.instructColor = instructColor;
	}

	public void setOptionsColor(Color optionsColor) {
		this.optionsColor = optionsColor;
	}

	public void setCreditsColor(Color creditsColor) {
		this.creditsColor = creditsColor;
	}

	public void loadTextures() {
		objectAtlas = new TextureAtlas(
				Gdx.files.internal("data/Objects/Objects.pack"));
		ground = objectAtlas.findRegion("ground");
		fence = objectAtlas.findRegion("fence");
		shortHill = objectAtlas.findRegion("hillshort");
		longHill = objectAtlas.findRegion("hilllong");
		fenceBroken = objectAtlas.findRegion("fenceBroken");
		openFence = objectAtlas.findRegion("openfence");
		endFence = objectAtlas.findRegion("endfence");
		midFence = objectAtlas.findRegion("fencecontinuous");
		cloud1 = objectAtlas.findRegion("cloud1");
		cloud2 = objectAtlas.findRegion("cloud2");
		cloud3 = objectAtlas.findRegion("cloud1");
		cloud3.flip(true, false);
	}

	public void render() {
		Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 250 / 255f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		virtualCamera.update();
		spriteBatch.begin();
		spriteBatch.setProjectionMatrix(virtualCamera.combined);
		drawTrees();
		//Drawing text on screen
		font52.draw(spriteBatch, "Adventure Run!", virtualViewport.getWidth()/2 - (font52.getBounds(title).width/2), 
				virtualViewport.getHeight() - 20);
		font44.draw(spriteBatch, "Tap to Begin", virtualViewport.getWidth()/2 - (font44.getBounds("Tap to Begin").width/2),
				virtualViewport.getHeight() - 50 - font52.getBounds(title).height);
		spriteBatch.enableBlending();
	//	drawFence();
		drawGround();
	//	drawClouds();
		spriteBatch.end();
	}
	
	//Method to be called whenever the window is resized
	public void resize(){
		//Obtain viewport from new width and height, then set it
		virtualViewport = viewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		virtualCamera.setVirtualViewport(virtualViewport);
		virtualCamera.updateViewport();
		//Centers the camera
		virtualCamera.position.set(virtualViewport.getWidth()/2,virtualViewport.getHeight()/2,0f);
	}

	public void update() {
	}

	public void drawFence() {
		spriteBatch.draw(openFence, .8f, .9f,
				70 / (Gdx.graphics.getWidth() / 5f),
				61 / (Gdx.graphics.getHeight() / 5f));
		for (float i = .8f + 70 / (Gdx.graphics.getWidth() / 5f); i < 2.5; i += 70 / (Gdx.graphics
				.getWidth() / 5f)) {
			spriteBatch.draw(midFence, i, .9f,
					70 / (Gdx.graphics.getWidth() / 5f),
					61 / (Gdx.graphics.getHeight() / 5f));
		}
		spriteBatch.draw(endFence,
				.8f + 6 * (70 / (Gdx.graphics.getWidth() / 5f)), .9f,
				70 / (Gdx.graphics.getWidth() / 5f),
				61 / (Gdx.graphics.getHeight() / 5f));
	}
	//Method for drawing ground pieces
	public void drawGround() {
		//Calculates how many tiles are needed to fit the screen's width
		int numberOfTiles = (int) Math.ceil(virtualViewport.getWidth() / ground.getRegionWidth());
		//Draws each piece side by side
		for(int i = 0; i < numberOfTiles; i++){
			spriteBatch.draw(ground,0 + i * ground.getRegionWidth(), -5, ground.getRegionWidth(),ground.getRegionHeight());
		}
		

	}

	public void drawTrees() {
		spriteBatch.draw(shortHill, 10, ground.getRegionHeight() -5, shortHill.getRegionWidth(),shortHill.getRegionHeight());
		spriteBatch.draw(longHill, 10 + shortHill.getRegionWidth(), ground.getRegionHeight() -5, longHill.getRegionWidth(),longHill.getRegionHeight());
		
		spriteBatch.draw(shortHill,virtualViewport.getWidth() - shortHill.getRegionWidth() - 10 , 
				ground.getRegionHeight() -5, shortHill.getRegionWidth(),shortHill.getRegionHeight());
		//spriteBatch.draw(longHill, 10 + shortHill.getRegionWidth(), ground.getRegionHeight() -5, longHill.getRegionWidth(),longHill.getRegionHeight());
	}

}
