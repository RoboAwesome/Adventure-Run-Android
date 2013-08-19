package com.me.myinfinitegame.graphics;

//This class builds a virtual viewport based on the maximum and minimum sizes wwe want to support
public class MultipleVirtualViewportBuilder {
	private final float minWidth;
	private final float minHeight;
	private final float maxWidth;
	private final float maxHeight;
	
	public MultipleVirtualViewportBuilder(float minWidth, float minHeight, float maxWidth, float maxHeight){
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}
	
	public VirtualViewport getVirtualViewport(float width, float height){
		if(width >= minWidth && width <= maxWidth &&  height >= minHeight && height <= maxHeight)
			return new VirtualViewport(width, height, false);
		
		float aspect = width / height;
		
		float scaleForMinSize = minWidth / width;
		float scaleForMaxSize = maxWidth / width;
		
		float virtualViewportWidth = width * scaleForMaxSize;
		float virtualViewportHeight = virtualViewportWidth / aspect;
		
		if(insideBounds(virtualViewportWidth, virtualViewportHeight))
			return new VirtualViewport(virtualViewportWidth, virtualViewportHeight, false);
		
		virtualViewportWidth = width * scaleForMinSize;
		virtualViewportHeight = virtualViewportWidth / aspect;
		if(insideBounds(virtualViewportWidth, virtualViewportHeight))
			return new VirtualViewport(virtualViewportWidth, virtualViewportHeight, false);
		//If the aspect ratio is not supported, just return the minimum area
		return new VirtualViewport(minWidth, minHeight, true);
	}
	public boolean insideBounds(float width, float height){
		if(width < minWidth ||  width > maxWidth)
			return false;
		if(height < minHeight || height > maxHeight)
			return false;
		return true;
	}

}
