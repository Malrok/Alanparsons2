package com.MRK.alanparsons2.templates;

public class TouchInputTemplate {

	private float lowerTouchLimit, upperTouchLimit;
	private float lowerSpeedLimit, normalSpeedLimit, upperSpeedLimit;
	private float minSpeed, normalSpeed, maxSpeed;
	private int speedDownDelay;
	
	public float getLowerTouchLimit() {
		return lowerTouchLimit;
	}

	public void setLowerTouchLimit(float lowerTouchlLimit) {
		this.lowerTouchLimit = lowerTouchlLimit;
	}

	public float getUpperTouchLimit() {
		return upperTouchLimit;
	}

	public void setUpperTouchLimit(float upperTouchLimit) {
		this.upperTouchLimit = upperTouchLimit;
	}

	public float getLowerSpeedLimit() {
		return lowerSpeedLimit;
	}

	public void setLowerSpeedLimit(float lowerSpeedLimit) {
		this.lowerSpeedLimit = lowerSpeedLimit;
	}

	public float getNormalSpeedLimit() {
		return normalSpeedLimit;
	}

	public void setNormalSpeedLimit(float normalSpeedLimit) {
		this.normalSpeedLimit = normalSpeedLimit;
	}

	public float getUpperSpeedLimit() {
		return upperSpeedLimit;
	}

	public void setUpperSpeedLimit(float upperSpeedLimit) {
		this.upperSpeedLimit = upperSpeedLimit;
	}
	
	public float getMinSpeed() {
		return minSpeed;
	}
	
	public void setMinSpeed(float minSpeed) {
		this.minSpeed = minSpeed;
	}
	
	public float getNormalSpeed() {
		return normalSpeed;
	}
	
	public void setNormalSpeed(float normalSpeed) {
		this.normalSpeed = normalSpeed;
	}
	
	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getSpeedDownDelay() {
		return speedDownDelay;
	}

	public void setSpeedDownDelay(int speedDownDelay) {
		this.speedDownDelay = speedDownDelay;
	}
	
}
