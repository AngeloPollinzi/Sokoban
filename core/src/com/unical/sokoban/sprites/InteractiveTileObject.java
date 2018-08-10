package com.unical.sokoban.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;

public class InteractiveTileObject {
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle rect;
	
	public InteractiveTileObject(TiledMap map, Rectangle rect) {
		this.map = map;
		this.rect = rect;

	}

	public float getX() {
		return rect.getX();
	}

	public float getY() {
		return rect.getY();
	}
	
	public void setX(float x) {
		rect.setX(x);
	}

	public void setY(float y) {
		rect.setY(y);
	}
}
