package com.unical.sokoban.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

/*at step S move box with Id in direction D from location (x1,y1) to (x2,y2)*/
@Id("move")
public class MoveBox {

	@Param(0)
	private int step;
	@Param(1)
	private String direction;
	@Param(2)
	private int x1;
	@Param(3)
	private int y1;
	@Param(4)
	private int x2;
	@Param(5)
	private int y2;

	public MoveBox() {
	}

	public MoveBox(int step, String direction, int x1, int y1, int x2, int y2) {
		this.step = step;
		this.direction = direction;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	@Override
	public String toString() {
		String print;
		print = "move(" + this.step + "," + this.direction + "," + this.x1 + "," + this.y1 + "," + this.x2 + ","
				+ this.y2 + ")";
		return print;
	}
}
