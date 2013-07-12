package com.MRK.alanparsons2.helpers;

import com.badlogic.gdx.math.Vector3;

public class CircleHelper {

	public static float currentAngle;
	
	public static Vector3 getPointOnCircle(Vector3 origin, float radius, float angle) {
		double x = origin.x + (radius * Math.cos(angle));
		double y = origin.y + (radius * Math.sin(angle));

//		System.out.println("getPointOnCircle origin x/y = " + origin.x + "/" + origin.y +
//		" radius = " + radius + " angle = " + angle +
//		"\nnew x/y = " + x + "/" + y);
		
		return new Vector3((float) x, (float) y, 0);
	}
	
}
