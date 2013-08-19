package com.me.myinfinitegame.texturepacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

//This class isn't used in the game at all but is used to compress numerous images together into a single pack file
//Which significantly improves performance
public class TexturePacker {
	public static void main(String[] arg){
		TexturePacker2.process("/GameDev/myinfintegame/my-infinite-game-android/assets/data/Enemy","/GameDev/myinfintegame/my-infinite-game-android/assets/data/Enemy","Enemy.pack");
	}

}
