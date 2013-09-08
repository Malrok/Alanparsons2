package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class RotatingCamera extends OrthographicCamera {
	
	private final long SHAKE_DURATION = 700;
	
	private float rotateCenterx, rotateCentery;
	private float radius;
	private boolean isShaking = false;
	private long shakeStart = 0;
	
	public RotatingCamera(int width, int height) {
		super(width, height);
	}

	public RotatingCamera() {
		super();
	}
	
	public void initViewport(float screenWidth, float screenHeight, float viewportWidth) {
//		this.viewportWidth = screenWidth;
//		this.viewportHeight = screenHeight;
		this.viewportWidth = viewportWidth;
		this.viewportHeight = screenHeight * this.viewportWidth / screenWidth;
		
		System.out.println("camera initViewport " + this.viewportWidth + " " + this.viewportHeight);
		
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
	
	public void setShaking(boolean isShaking) {
		this.isShaking = isShaking;
		shakeStart = TimeUtils.millis();
	}
	
	public void updateShaking() {
		if (isShaking) {
			if (TimeUtils.millis() - shakeStart > SHAKE_DURATION) {
				isShaking = false;
			} else {
				position.x += Math.random();
				position.y += Math.random();
			}
		}
	}
}
