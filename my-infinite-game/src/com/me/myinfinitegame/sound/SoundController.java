package com.me.myinfinitegame.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.me.myinfinitegame.screens.MenuScreen;
import com.me.myinfinitegame.screens.TheGameScreen;

//Class that controls music and sound effects for the game
public class SoundController {

	private Sound jumpEffect;
	private Sound countdown;
	private Sound go;
	private Sound coin1, coin2, coin3, coin4;
	private Music music;
	private Sound select;
	private Screen screen;
	private MenuScreen ms;
	private TheGameScreen gs;
	private boolean musicOn = true;
	private boolean soundOn = true;
	
	//Enum types
	public enum Sounds {
		Jump, Ding1, Select, Countdown, Go, Ding2, Ding3, Ding4
	};

	public Sound getGo() {
		return go;
	}
	//Constructor
	public SoundController(Screen screen) {
		//Loads required sound based on screen type
		this.screen = screen;
		if (screen.getClass().equals(TheGameScreen.class)) {
			gs = (TheGameScreen) screen;
			this.soundOn = gs.isSoundOn();
			System.out.println(soundOn);
			this.musicOn = gs.isMusicOn();
			if (gs.isSoundOn())
				loadGameSounds();
			if (gs.isMusicOn())
				loadGameMusic();
		}
		if (screen.getClass().equals(MenuScreen.class)) {
			ms = (MenuScreen) screen;
			this.soundOn = ms.isSoundOn();
			this.musicOn = ms.isMusicOn();
			if (ms.isMusicOn())
				loadMenuSounds();
		}

	}

	public void playSound(Sounds sound) {
		//This would work better with a switch case...
		if (soundOn) {
			if (sound == Sounds.Jump) {
				music.setVolume(.5f);
				jumpEffect.play(1);

			}
			if (sound == Sounds.Ding1) {
				music.setVolume(.5f);
				coin1.play(1);
			}
			if (sound == Sounds.Select) {
				music.setVolume(.5f);
			}
			if (sound == Sounds.Countdown) {
				music.setVolume(.5f);
				countdown.play(1);
			}
			if (sound == Sounds.Go) {
				music.setVolume(.5f);
				go.play(1);
			}
			if (sound == Sounds.Ding2) {
				music.setVolume(.5f);
				coin2.play(1);
			}
			if (sound == Sounds.Ding3) {
				music.setVolume(.5f);
				coin3.play(1);
			}
			if (sound == Sounds.Ding4) {
				music.setVolume(.5f);
				coin4.play(1);
			}
		}
		else{
			
		}

	}

	public void loadGameMusic() {
		music = Gdx.audio.newMusic(Gdx.files.internal("data/8bitloop.mp3"));
	}

	public void playMusic() {
		music.setVolume(1);
		music.setLooping(true);
		if (music != null && !music.isPlaying()) {
			music.play();
		}

	}

	public void stopMusic() {
		if (music != null && music.isPlaying()) {
			music.stop();
		}
	}

	public void loadGameSounds() {
		jumpEffect = Gdx.audio.newSound(Gdx.files.internal("data/jump_10.wav"));
		countdown = Gdx.audio.newSound(Gdx.files
				.internal("data/misc_menu_2.wav"));
		go = Gdx.audio.newSound(Gdx.files.internal("data/misc_menu_4.wav"));
		coin1 = Gdx.audio.newSound(Gdx.files.internal("data/coin1.wav"));
		coin2 = Gdx.audio.newSound(Gdx.files.internal("data/coin2.wav"));
		coin3 = Gdx.audio.newSound(Gdx.files.internal("data/coin3.wav"));
		coin4 = Gdx.audio.newSound(Gdx.files.internal("data/coin4.wav"));

	}

	public void loadMenuSounds() {
		music = Gdx.audio.newMusic(Gdx.files
				.internal("data/Rainbows and Unicorns.mp3"));
	}

	public void disposeSounds() {
		music.dispose();
	}

}
