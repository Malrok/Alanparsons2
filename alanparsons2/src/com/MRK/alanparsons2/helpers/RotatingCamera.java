package com.MRK.alanparsons2.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class RotatingCamera extends OrthographicCamera {

	private float currentAngle;
	
//	private Vector2 oldPos;
	
	public RotatingCamera() {
		super();
	}
	
	@Override
	public void setToOrtho(boolean yDown, float viewportWidth, float viewportHeight) {
		super.setToOrtho(yDown, viewportWidth, viewportHeight);
		
		currentAngle = (float) Math.toRadians(270);
		
//		oldPos = new Vector2(position.x, position.y);
	}
	
	public void rotateCameraAround(Vector3 origin, float radius, float angle) {
		double x, y;
		
		currentAngle += angle;

		x = origin.x + (radius * Math.cos(currentAngle));
		y = origin.y + (radius * Math.sin(currentAngle));
		
//		System.out.println("rotateCameraAround origin x/y = " + origin.x + "/" + origin.y +
//				" radius = " + radius + " angle = " + angle +
//				"\nnew x/y = " + x + "/" + y);
		
		position.x = (float) x;
		position.y = (float) y;
		
//		translate((float) (x - oldPos.x),(float) (y - oldPos.y));
		
//		oldPos = new Vector2((float)x, (float)y);
		
		rotate(-(float) Math.toDegrees(angle));
	}
}
