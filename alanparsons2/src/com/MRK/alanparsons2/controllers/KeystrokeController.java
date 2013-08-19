package com.MRK.alanparsons2.controllers;

import com.badlogic.gdx.Gdx;

public class KeystrokeController {

	private boolean[] keysPressed = new boolean[155];
	private boolean[] keysReleased = new boolean[155];
	
	public boolean keyReleased(int key) {
		boolean ret = keysReleased[key];
		
		keysReleased[key] = false;
		
		return ret;
	}
	
	public void update() {
		for (int rank = 0; rank < 155; rank ++) {
			if (keysPressed[rank] && ! Gdx.input.isKeyPressed(rank)) {
				keysReleased[rank] = true;
				keysPressed[rank] = false;
			} else if (Gdx.input.isKeyPressed(rank)) 
				keysPressed[rank] = true;
		}
	}
}
