package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class RotatingCamera extends OrthographicCamera {
	
	private static RotatingCamera instance = new RotatingCamera();
	
	private static float radius;
	
	private RotatingCamera() {
		super();
	}
	
	public static RotatingCamera getInstance() {
		return instance;
	}
	
	public void setRadius(float value) {
		radius = value;
	}
	
	@Override
	public void setToOrtho(boolean yDown, float viewportWidth, float viewportHeight) {
		super.setToOrtho(yDown, viewportWidth, viewportHeight);
		
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
