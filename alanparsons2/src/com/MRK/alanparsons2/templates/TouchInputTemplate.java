package com.MRK.alanparsons2.templates;

public class TouchInputTemplate {

	private float lowerNormalLimit, upperNormalLimit;
	private float minSpeed, normalSpeed, maxSpeed;
	private int speedDownDelay;
	
	public float getLowerNormalLimit() {
		return lowerNormalLimit;
	}
	
	public void setLowerNormalLimit(float lowerNormalLimit) {
		this.lowerNormalLimit = lowerNormalLimit;
	}
	
	public float getUpperNormalLimit() {
		return upperNormalLimit;
	}
	
	public void setUpperNormalLimit(float upperNormalLimit) {
		this.upperNormalLimit = upperNormalLimit;
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
