package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class RotatingCamera extends OrthographicCamera {
	
	public static float VIEWPORT_WIDTH;
	public static float VIEWPORT_HEIGHT;
	
	private static float radius;
	
	public RotatingCamera() {
		super();
	}
	
	public void init(float width, float height) {
		VIEWPORT_WIDTH = Level.LEVEL_WIDTH / 2;
		VIEWPORT_HEIGHT = height * VIEWPORT_WIDTH / width;
		setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
//		setToOrtho(false, width, height);
	}
	
	public void setRadius(float value) {
		radius = value;
	}
	
	@Override
	public void setToOrtho(boolean yDown, float viewportWidth, float viewportHeight) {
		super.setToOrtho(yDown, viewportWidth, viewportHeight);
		
		position.x = Level.LEVEL_WIDTH / 2;
		position.y = Level.LEVEL_HEIGHT / 2 - VIEWPORT_HEIGHT / 2;
		
		ShipController.currentAngle = (float) Math.toRadians(270);
	}
	
	public void rotateCameraAround(float originx, float originy, float angle) {
		ShipController.currentAngle += angle;
		
		Vector2 newPos = CircleHelper.getPointOnCircle(originx, originy, radius, ShipController.currentAngle);
		
		position.x = newPos.x;
		position.y = newPos.y;
		
		rotate(-(float) Math.toDegrees(angle));
	}	
}
