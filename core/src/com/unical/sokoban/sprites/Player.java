package com.unical.sokoban.sprites;


import com.badlogic.gdx.graphics.Texture;

public class Player {

	
	public enum Action {
		RUNNING, STANDING
	};

	public enum Direction {
		UP, DOWN, RIGHT, LEFT
	};

	public Action action;
	public Direction direction;
	public Texture playerUp;
	public Texture playerDown;
	public Texture playerRight;
	public Texture playerLeft;
	public float x;
	public float y;
	public float px;
	public float py;

	public Player() {
		action = Action.STANDING;
		direction = Direction.DOWN;
		x=64;
		y=64;
		px=64;
		py=64;
		playerDown = new Texture("Character4.png");
		playerUp = new Texture("Character7.png");
		playerRight = new Texture("Character2.png");
		playerLeft = new Texture("Character1.png");
	}
	
}
