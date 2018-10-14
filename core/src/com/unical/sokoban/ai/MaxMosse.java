package com.unical.sokoban.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("maxmosse")
public class MaxMosse {
	
	@Param(0)
	private int mosse;

	public MaxMosse() {
		
	}
	
	public MaxMosse(int mosse) {
		this.mosse = mosse;
	}

	public int getMosse() {
		return mosse;
	}

	public void setMosse(int mosse) {
		this.mosse = mosse;
	}
	

}
