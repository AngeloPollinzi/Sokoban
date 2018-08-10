package com.unical.sokoban.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public class EndPoint extends InteractiveTileObject {

	public int x;
	public int y;

	private Texture tex;

	public EndPoint(TiledMap map, Rectangle rect) {
		super(map, rect);
		tex = new Texture("EndPoint_Yellow.png");
	}

	public Texture getTexture() {
		return tex;
	}

	public void update() {
		// TODO Auto-generated method stub
		this.x = (int) (this.getX() / 64);
		this.y = (int) (this.getY() / 64);
	}

}
