package com.unical.sokoban.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("boxPosition")
public class MoveBox {

	@Param(0)
	private int step;
	@Param(1)
	private int boxId;
	@Param(2)
	private int x;
	@Param(3)
	private int y;

	public MoveBox() {
	}
	
	public MoveBox(int step, int boxId, int x, int y) {
		this.step = step;
		this.boxId = boxId;
		this.x = x;
		this.y = y;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getBoxId() {
		return boxId;
	}

	public void setBoxId(int boxId) {
		this.boxId = boxId;
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
		print = "boxPosition(" + this.step + "," + this.boxId + "," + this.x + "," + this.y + ")";
		return print;
	}
}
