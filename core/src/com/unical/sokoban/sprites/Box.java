package com.unical.sokoban.sprites;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public class Box extends InteractiveTileObject {

	private int x;
	private int y;
	private int id=0;
	public float px;
	public float py;
	public boolean moved;
	private Texture tex;
	
	public Box(TiledMap map, Rectangle rect) {
		super(map, rect);
		px=rect.x;
		py=rect.y;
		tex = new Texture("Crate_Yellow.png");
		moved=false;
	}
	
	public Texture getTexture() {
		return tex;
	}

	public void update() {
		// TODO Auto-generated method stub
		x=(int) (this.getX()/64);
		y=(int) (this.getY()/64);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
