package com.MRK.alanparsons2.templates;

public class CameraTemplate {

	private float cameraWidth, cameraRadius, cameraZoomMin, cameraZoomMax, zoomUpdateValue, zoomCooldown;

	public float getCameraWidth() {
		return cameraWidth;
	}

	public void setCameraWidth(float cameraWidth) {
		this.cameraWidth = cameraWidth;
	}

	public float getCameraRadius() {
		return cameraRadius;
	}

	public void setCameraRadius(float cameraRadius) {
		this.cameraRadius = cameraRadius;
	}

	public float getCameraZoomMin() {
		return cameraZoomMin;
	}

	public void setCameraZoomMin(float cameraZoomMin) {
		this.cameraZoomMin = cameraZoomMin;
	}

	public float getCameraZoomMax() {
		return cameraZoomMax;
	}

	public void setCameraZoomMax(float cameraZoomMax) {
		this.cameraZoomMax = cameraZoomMax;
	}

	public float getZoomUpdateValue() {
		return zoomUpdateValue;
	}

	public void setZoomUpdateValue(float zoomCooldown) {
		this.zoomUpdateValue = zoomCooldown;
	}

	public float getZoomCooldown() {
		return zoomCooldown;
	}

	public void setZoomCooldown(float zoomCooldown) {
		this.zoomCooldown = zoomCooldown;
	}
	
}
