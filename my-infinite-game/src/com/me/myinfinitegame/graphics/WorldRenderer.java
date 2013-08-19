package com.me.myinfinitegame.graphics;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.myinfinitegame.gameworld.BackgroundObject;
import com.me.myinfinitegame.gameworld.BackgroundObject.ObjectType;
import com.me.myinfinitegame.gameworld.Block;
import com.me.myinfinitegame.gameworld.Coin;
import com.me.myinfinitegame.gameworld.Coin.Value;
import com.me.myinfinitegame.gameworld.CollidableObject;
import com.me.myinfinitegame.gameworld.CollidableObject.Types;
import com.me.myinfinitegame.gameworld.Enemy;
import com.me.myinfinitegame.gameworld.Fly;
import com.me.myinfinitegame.gameworld.Ground;
import com.me.myinfinitegame.gameworld.Platform;
import com.me.myinfinitegame.gameworld.Player;
import com.me.myinfinitegame.gameworld.Player.State;
import com.me.myinfinitegame.gameworld.Tile;
import com.me.myinfinitegame.gameworld.TileMap;
import com.me.myinfinitegame.gameworld.World;
import com.me.myinfinitegame.screens.TheGameScreen;
import com.me.myinfinitegame.screens.TheGameScreen.GameState;
import com.me.myinfinitegame.sound.SoundController.Sounds;

//This class renders all graphics in the GameScreen
public class WorldRenderer {
	// Camera properties
	private static final float CAMERA_HEIGHT = 5f;
	private static final float CAMERA_WIDTH = 5f;
	private Vector3 cameraPosition = new Vector3(CAMERA_WIDTH / 2f,
			CAMERA_HEIGHT / 2f, 0);
	private OrthographicCamera camera;
	//The font needs a separate camera in order to render correctly
	private OrthographicCamera fontCamera;
	
	// SpriteBatch
	public SpriteBatch spriteBatch;
	// Shape Renderer (for debugging)
	ShapeRenderer debugRenderer;
	private boolean debug = false;
	// World and player
	TheGameScreen game;
	World world;
	Player henson;
	// Fonts
	BitmapFont font = new BitmapFont(Gdx.files.internal("data/MostlyMono.fnt"),
			Gdx.files.internal("data/MostlyMono_0.png"), false);
	BitmapFont font32 = new BitmapFont(
			Gdx.files.internal("data/MostlyMono32pt.fnt"),
			Gdx.files.internal("data/MostlyMono32pt_0.png"), false);

	// Atlases and Textures for all graphics in the game screen
	TextureAtlas playerAtlas, objectAtlas, enemyAtlas;
	TextureRegion player, playerHurt, playerIdle, cloud, cloud2, cloud3,ground, blocker, longHill, shortHill;
	TextureRegion life, playerJumping, goldCoin, silverCoin, one, two, three;
	
	//Texture arrays for animated graphics
	TextureRegion[] playerWalkingFrames = new TextureRegion[11];
	TextureRegion[] flyFrames = new TextureRegion[2];

	// Animations
	Animation playerWalk;
	Animation fly;
	private static final float WALKING_FRAME_DURATION = .06f;
	private static final float FLYING_FRAME_DURATION = .3f;

	public Vector3 getCameraPosition() {
		return cameraPosition;
	}

	public WorldRenderer(World world, boolean debug, TheGameScreen game) {
		this.debug = debug;
		if (debug) {
			debugRenderer = new ShapeRenderer();
		}
		this.game = game;
		this.world = world;
		this.henson = world.getHenson();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		camera.position.set(cameraPosition.x, cameraPosition.y, 0);
		camera.update();
		fontCamera = new OrthographicCamera(1280, 800);
		spriteBatch = new SpriteBatch();
		loadTextures();

	}

	public void loadTextures() {
		//Atlas containing player images
		playerAtlas = new TextureAtlas(
				Gdx.files.internal("data/character/character.pack"));
		playerIdle = playerAtlas.findRegion("walk0001");
		//Populating animation arrays
		for (int i = 1; i < 12; i++) {
			if (i > 9)
				playerWalkingFrames[i - 1] = playerAtlas.findRegion("walk00"
						+ i);
			else
				playerWalkingFrames[i - 1] = playerAtlas.findRegion("walk000"
						+ i);
		}
		//Atlas containing enemy images
		enemyAtlas = new TextureAtlas(
				Gdx.files.internal("data/Enemy/Enemy.pack"));
		
		playerHurt = enemyAtlas.findRegion("playerhurt");
		//Populate enemy animation array
		for (int i = 1; i < 3; i++) {
			flyFrames[i - 1] = enemyAtlas.findRegion("flyFly" + i);
		}
		blocker = enemyAtlas.findRegion("blockerMad");
		//Atlas for all objects in the world
		objectAtlas = new TextureAtlas(
				Gdx.files.internal("data/Objects/Objects.pack"));
		cloud = objectAtlas.findRegion("cloud1");
		ground = objectAtlas.findRegion("ground");
		longHill = objectAtlas.findRegion("hilllong");
		shortHill = objectAtlas.findRegion("hillshort");
		cloud2 = objectAtlas.findRegion("cloud2");
		cloud3 = objectAtlas.findRegion("cloud3");
		life = objectAtlas.findRegion("extralife");
		playerJumping = objectAtlas.findRegion("jump");
		goldCoin = objectAtlas.findRegion("coingold");
		silverCoin = objectAtlas.findRegion("coinsilver");
		one = objectAtlas.findRegion("hud1");
		two = objectAtlas.findRegion("hud2");
		three = objectAtlas.findRegion("hud3");
		//Animations for moving actors
		playerWalk = new Animation(WALKING_FRAME_DURATION, playerWalkingFrames);
		fly = new Animation(FLYING_FRAME_DURATION, flyFrames);

	}
	//Render method that is called every frame in ApplicationListener() 
	public void render() {
		//Clear the screen
		Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 250 / 255f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//If the player isn't dead
		if (henson.getState() != State.Dead) {
			//Adjust the camera so that the player is always located left of where the camera is centered
			if (henson.getPosition().x + Player.getPlayerWidth() > cameraPosition.x) {
				cameraPosition.x += henson.getVelocity().x
						* Gdx.graphics.getDeltaTime();
			}
			if (henson.getPosition().x < cameraPosition.x - 2) {
				cameraPosition.x -= henson.getVelocity().x
						* Gdx.graphics.getDeltaTime();
			}
		}
		//Set camera coordinates to the world
		world.setCamerax(cameraPosition.x - (CAMERA_WIDTH / 2f));
		//Update camera properties and position
		this.camera.position.set(cameraPosition.x, cameraPosition.y, 0);
		this.camera.update();
		spriteBatch.begin();
		if (game.getState() == GameState.GameOver) {
			spriteBatch.setProjectionMatrix(fontCamera.combined);
			font.setColor(Color.BLACK);
			font.draw(spriteBatch, "Game Over", -168, 100);
			font.draw(spriteBatch, "Tap to Play Again", -230, 50);
		}
		spriteBatch.setProjectionMatrix(fontCamera.combined);
		font32.setColor(Color.BLACK);
		font32.draw(spriteBatch, "Score:" + world.getScore(), -620, 390);
		spriteBatch.setProjectionMatrix(camera.combined);
		if (game.getState() == GameState.Starting) {
			if (game.getCountdown() >= 7200) {
				drawCountdown(3);
				if (!Gdx.app.getType().equals(ApplicationType.Android))
					game.getSoundControl().playSound(Sounds.Countdown);

			}
			if (game.getCountdown() >= 3600 && game.getCountdown() <= 7200) {
				drawCountdown(2);
				if (!Gdx.app.getType().equals(ApplicationType.Android))
					game.getSoundControl().playSound(Sounds.Countdown);
			}
			if (game.getCountdown() <= 3600 && game.getCountdown() >= 1000) {
				drawCountdown(1);
				if (!Gdx.app.getType().equals(ApplicationType.Android))
					game.getSoundControl().playSound(Sounds.Countdown);
			}
			if (game.getCountdown() <= 1000) {
				game.getSoundControl().playSound(Sounds.Go);
			}
		}

		drawPlayer();
		drawEnemies();
		drawBlocks();
		drawBackgroundObjects();
		drawForegroundObjects();
		drawCoins();
		spriteBatch.end();
		if (debug) {
			for (TileMap map : world.getMaps()) {
				drawDebug(map);
			}
		}
	}

	public void drawPlayer() {
		//Draw player based on his current state
		if (henson.getState() == State.Walking) {
			player = playerWalk.getKeyFrame(henson.getStateTime(), true);
		}
		if (henson.getState() == State.Dead)
			player = playerHurt;
		if (henson.getState() == State.Jumping) {
			player = playerJumping;
		}
		spriteBatch.draw(player, henson.getPosition().x,
				henson.getPosition().y, Player.getPlayerWidth(),
				Player.getPlayerHeight());
	}

	public void drawDebug(TileMap map) {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		debugRenderer.setColor(Color.BLACK);
		for (int i = 0; i < map.getWidth(); i++) {
			for (int j = 0; j < map.getHeight(); j++) {
				Tile t = map.getTile(i, j);
				debugRenderer.rect(t.getPosition().x, t.getPosition().y,
						Tile.width, Tile.height);
			}
		}
		debugRenderer.end();
	}

	//This method isn't currently being used
	public void drawBackgroundObjects() {
		for (BackgroundObject b : world.getBackgroundObjects()) {
			TextureRegion object;
			if (b.getObjectType() == ObjectType.Cloud) {
				if (b.isFlipped()) {
					object = cloud;
					object.flip(true, false);
				} else {
					object = cloud;
				}
				spriteBatch.draw(object, b.getPosition().x, b.getPosition().y,
						129 / 144f, 63 / 96f);
			}
			if (b.getObjectType() == ObjectType.LongHill) {
				object = longHill;
				spriteBatch.draw(object, b.getPosition().x, b.getPosition().y,
						.5f, 2);
			}
			if (b.getObjectType() == ObjectType.ShortHill) {
				object = shortHill;
				spriteBatch.draw(object, b.getPosition().x, b.getPosition().y,
						.5f, 1);
			}
			if (b.getObjectType() == ObjectType.Cloud2) {
				if (b.isFlipped()) {
					object = cloud;
					object.flip(true, false);
				} else {
					object = cloud;
				}
				spriteBatch.draw(object, b.getPosition().x, b.getPosition().y,
						128 / 144f, 63 / 96f);
			}
			if (b.getObjectType() == ObjectType.Cloud3) {
				if (b.isFlipped()) {
					object = cloud;
					object.flip(true, false);
				} else {
					object = cloud;
				}
				spriteBatch.draw(object, b.getPosition().x, b.getPosition().y,
						128 / 144f, 63 / 96f);
			}
		}
	}

	public void drawForegroundObjects() {
		for (CollidableObject c : world.getCollidableObjects()) {
			TextureRegion cObject;
			if (c.getType() == Types.Ground) {
				cObject = ground;
				Ground g = (Ground) c;
				spriteBatch.draw(cObject, g.getPosition().x, g.getPosition().y,
						g.getGroundWidth(), g.getGroundHeight());
			}
			// if(c.getType() == Types.Platform){
			// cObject = block;
			// Platform p = (Platform) c;
			// spriteBatch.draw(cObject, p.getPosition().x, p.getPosition().y,
			// p.getPlatformWidth(), p.getPlatformHeight());
			// }
			//
		}
	}

	public void drawCoins() {
		TextureRegion coin = null;
		for (Coin c : world.getCoins()) {
			if (c.getValue() == Value.Gold) {
				coin = goldCoin;
			}
			if (c.getValue() == Value.Silver) {
				coin = silverCoin;
			}
			spriteBatch.draw(coin, c.getPosition().x, c.getPosition().y,
					Coin.COIN_WIDTH, Coin.COIN_HEIGHT);
		}
	}

	public void drawEnemies() {
		TextureRegion enemyFrame;
		for (Enemy e : world.getEnemies()) {
			enemyFrame = fly.getKeyFrame(e.getStateTime(), true);
			spriteBatch.draw(enemyFrame, e.getPosition().x, e.getPosition().y,
					Fly.FLY_WIDTH, Fly.FLY_HEIGHT);
		}
	}

	public void drawBlocks() {
		// TextureRegion blockFrame;
		for (Block b : world.getBlocks()) {
			spriteBatch.draw(blocker, b.getPosition().x, b.getPosition().y,
					Block.getBlockWidth(), Block.getBlockHeight());
		}
	}

	public void drawCountdown(int number) {
		TextureRegion num = null;
		switch (number) {
		case 1:
			num = one;
			break;
		case 2:
			num = two;
			break;
		case 3:
			num = three;
			break;
		}

		spriteBatch.draw(num, 2.3984375f, 2.26875f, num.getRegionWidth()
				/ (Gdx.graphics.getWidth() / 5f), num.getRegionHeight()
				/ (Gdx.graphics.getHeight() / 5f));

	}
}
