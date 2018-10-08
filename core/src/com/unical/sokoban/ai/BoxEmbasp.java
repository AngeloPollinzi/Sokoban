package com.unical.sokoban.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("box")
public class BoxEmbasp {

	@Param(0)
	private int id;
	@Param(1)
	private int x;
	@Param(2)
	private int y;

	public BoxEmbasp() {
	}
	
	public BoxEmbasp(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		String print;
		print = "box(" + this.id+ "," + this.x+ "," + this.y + ").";
		return print;
	}

}
