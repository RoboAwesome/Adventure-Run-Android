package com.me.myinfinitegame.graphics;

import com.badlogic.gdx.Gdx;

//Class used to define a virtual area that the camera will show depending on the size of the screen
public class VirtualViewport {
	
	float virtualWidth;
	float virtualHeight;
	
	public float getVirtualWidth() {
		return virtualWidth;
	}
	public float getVirtualHeight() {
		return virtualHeight;
	}
	
	public VirtualViewport(float virtualWidth, float virtualHeight){
		this(virtualWidth, virtualHeight, false);
	}
	public VirtualViewport(float virtualWidth, float virtualHeight, boolean shrink){
		this.virtualWidth = virtualWidth;
		this.virtualHeight = virtualHeight;
	}
	
	//Method that returns the width of the virtual port to be shown on screen
	public float getWidth(){
		return getWidth(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
	}
	
	public float getWidth(float screenWidth, float screenHeight){
		float virtualAspect = virtualWidth / virtualHeight;
		
		float aspect = screenWidth / screenHeight;
		
		if(aspect > virtualAspect || (Math.abs(aspect - virtualAspect) < 0.01f))
			return virtualHeight * aspect;
		else
			return virtualWidth;
	}
	//Method that returns the height of the virtualviewport to be shown
	public float getHeight(){
		return getHeight(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public float getHeight(float screenWidth, float screenHeight){
		float  virtualAspect = virtualWidth / virtualHeight;
		
		float aspect = screenWidth / screenHeight;
		if(aspect > virtualAspect || (Math.abs(aspect - virtualAspect) < 0.01f))
			return virtualHeight;
		else
			return virtualWidth / aspect;
	}
	

}
