package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class RotatingCamera extends OrthographicCamera {
	
	private float rotateCenterx, rotateCentery;
	private float radius;
	
	public RotatingCamera() {
		super();
	}
	
	public void initViewport(float screenWidth, float screenHeight, float viewportWidth) {
		this.viewportWidth = viewportWidth;
		this.viewportHeight = screenHeight * this.viewportWidth / screenWidth;
		setToOrtho(false, this.viewportWidth, this.viewportHeight);
	}
	
	@Override
	public void setToOrtho(boolean yDown, float viewportWidth, float viewportHeight) {
		super.setToOrtho(yDown, viewportWidth, viewportHeight);
		ShipController.currentAngle = (float) Math.toRadians(270);
	}

	public float getRadius() {
		return radius;
	}
	
	public void setRadius(float value) {
		radius = value;
	}
	
	public void setRotateCenter(float originx, float originy) {
		this.rotateCenterx = originx;
		this.rotateCentery = originy;
	}
	
	public void initPosition(float levelWidth) {
		position.x = levelWidth / 2;
		position.y = rotateCentery - radius - viewportHeight / 2;
	}
	
	public float getViewportHeight() {
		return viewportHeight;
	}
	
	public void rotateCameraAround(float angle) {
		ShipController.currentAngle += angle;
		
		Vector2 newPos = CircleHelper.getPointOnCircle(rotateCenterx, rotateCentery, radius, ShipController.currentAngle);
		
		position.x = newPos.x;
		position.y = newPos.y;
		
		rotate(-(float) Math.toDegrees(angle));
	}	
}
