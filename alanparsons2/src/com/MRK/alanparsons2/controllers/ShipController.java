package com.MRK.alanparsons2.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.TimeUtils;

public class ShipController {

	public static float MIN_ANGLE = .01f;
	public static float MAX_ANGLE = .1f;
	public static long SPEED_DOWN_DELAY = 100;
	
	// touch direction zones
	public static float screenMiddle;
	
	private float currentDirection = 0;
	private long lastTime = TimeUtils.millis();
	
	public ShipController(float screenWidth) {
		screenMiddle = screenWidth / 2;
	}
	
	public float getDirection() {
		boolean touched = false;
		boolean touchLeft = false;
		boolean touchRight = false;
		
		if(Gdx.input.isTouched()) {
			int x = Gdx.input.getX();
			
			touchLeft = x < screenMiddle;
			touchRight = x >= screenMiddle;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) || touchLeft) {
			touched = true;
			if (currentDirection == 0 || currentDirection > 0){
				currentDirection = -MIN_ANGLE;
			} else {
				currentDirection -= MIN_ANGLE;
			}
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT) || touchRight) {
			touched = true;
			if (currentDirection == 0 || currentDirection < 0) {
				currentDirection = MIN_ANGLE;
			} else {
				currentDirection += MIN_ANGLE;
			}
		}
		
		if (Math.abs(currentDirection) > MAX_ANGLE) currentDirection = (Math.abs(currentDirection) / currentDirection) * MAX_ANGLE;
		
		if (!touched && (TimeUtils.millis() > lastTime + SPEED_DOWN_DELAY)) {
			lastTime = TimeUtils.millis();
			if (currentDirection != 0) currentDirection -= (Math.abs(currentDirection) / currentDirection) * MIN_ANGLE;
		}
			
		if (!touched && Math.abs(currentDirection) <= MIN_ANGLE) currentDirection = 0;
		
		return currentDirection;
	}
	
}
