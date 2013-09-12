package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class RotatingCamera extends OrthographicCamera {
	
	private final long SHAKE_DURATION = 700;
	
	private float rotateCenterx, rotateCentery;
	private float radius;
	private float zoomMin, zoomMax, zoomUpdateValue, zoomCooldown;
	private boolean isShaking = false;
	private long shakeStart = 0;
	private long zoomEnd = 0;
	private float[] shipSpeeds;
	private float currentZoom = 1.0f;
	private float fromCenterToShip = .0f;
	
	public RotatingCamera(int width, int height) {
		super(width, height);
	}

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
	
	public float getZoomUpdateValue() {
		return zoomUpdateValue;
	}

	public void setZoomValues(float zoomMin, float zoomMax, float zoomUpdateValue, float zoomCooldown) {
		this.zoomMin = (zoomMin == 0 ? 1 : zoomMin);
		this.zoomMax = (zoomMax == 0 ? 1 : zoomMax);
		this.zoomUpdateValue = zoomUpdateValue;
		this.zoomCooldown = zoomCooldown;
	}

	public void setRotateCenter(float originx, float originy) {
		this.rotateCenterx = originx;
		this.rotateCentery = originy;
	}
	
	public void initPosition(float levelWidth) {
		position.x = levelWidth / 2;
		position.y = rotateCentery - radius - viewportHeight / 2;
	}
	
	public float getFromCenterToShip() {
		return fromCenterToShip;
	}

	public void setFromCenterToShip(float fromCenterToShip) {
		this.fromCenterToShip = fromCenterToShip;
	}

	public float getViewportHeight() {
		return viewportHeight;
	}
	
	public void rotateCameraAround(float angle) {
		float radius = this.radius + (fromCenterToShip - fromCenterToShip * currentZoom);
		
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
	
	public void setShipSpeeds(float[] speeds) {
		shipSpeeds = speeds;
	}

	public void updateZoomValue(float shipSpeed) {
		if (shipSpeed == 0) {
			if (zoomEnd == 0) {
				zoomEnd = TimeUtils.millis();
			} else if (zoomEnd + (long)(zoomCooldown * 1000) < TimeUtils.millis()) {
				if (currentZoom < 1.0f) {
					currentZoom += zoomUpdateValue * Gdx.graphics.getDeltaTime();
					if (currentZoom > 1.0f) {
						currentZoom = 1.0f;
						zoomEnd = 0;
					}
				} else if (currentZoom > 1.0f) {
					currentZoom -= zoomUpdateValue * Gdx.graphics.getDeltaTime();
					if (currentZoom < 1.0f) {
						currentZoom = 1.0f;
						zoomEnd = 0;
					}
				}
			}
		} else {
			zoomEnd = 0;
			if (shipSpeed <= shipSpeeds[ShipController.NORMAL_SPEED]) {
				currentZoom -= zoomUpdateValue * Gdx.graphics.getDeltaTime();
				if (currentZoom < zoomMin) currentZoom = zoomMin;
			} else if (shipSpeed >= shipSpeeds[ShipController.NORMAL_SPEED]) {
				currentZoom += zoomUpdateValue * Gdx.graphics.getDeltaTime();
				if (currentZoom > zoomMax) currentZoom = zoomMax;
			}
		}
		
		this.zoom = currentZoom;
	}
}
