package com.unical.sokoban.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("move")
public class MoveBox {

	@Param(0)
	private int step;
	@Param(1)
	private int boxId;
	@Param(2)
	private String direction;

	public MoveBox() {
	}
	
	public MoveBox(int step, int boxId, String dir) {
		this.step = step;
		this.boxId = boxId;
		this.direction = dir;
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

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String dir) {
		this.direction = dir;
	}

	@Override
	public String toString() {
		String print;
		print = "move(" + this.step + "," + this.boxId + "," + this.direction+ ")";
		return print;
	}
}
