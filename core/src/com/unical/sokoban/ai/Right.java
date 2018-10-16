package com.unical.sokoban.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

/*location (x2,y2) is right to (x1,y1)*/
@Id("right")
public class Right {
	
	@Param(0)
	private int x1;
	@Param(1)
	private int y1;
	@Param(2)
	private int x2;
	@Param(3)
	private int y2;
	
	public Right(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public Right() {
		
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
		return "right("+this.x1+","+this.y1+","+this.x2+","+this.y2+").";
	}
	
}
