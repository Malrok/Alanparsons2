package com.MRK.alanparsons2.controllers;

import com.MRK.alanparsons2.helpers.CircleHelper;
import com.MRK.alanparsons2.models.RotatingCamera;
import com.MRK.alanparsons2.models.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Controlleur du vaisseau<BR>
 */
public class ShipController {

	private static float MIN_ANGLE = .01f;
	private static float MAX_ANGLE = .1f;
	private static long SPEED_DOWN_DELAY = 100;
	private static float SHIP_DISTANCE_FROM_FOE;
	private static float LAPINY = .75f;
	
	private static float screenMiddle;
	private static Vector2 rotationCenter;
	
	private Ship ship;
	private float currentDirection = 0;
	private long lastTime = TimeUtils.millis();
	
	public static float currentAngle;
	
	/** 
	 * constructeur
	 */
	public ShipController(Ship ship) { 
		this.ship = ship;
	}
	
	/**
	 * Initialise l'instance de la clase
	 * @param screenWidth  - float : largeur de l'écran
	 * @param screenHeight - float : hauteur de l'écran
	 * @param center       - {@link Vector2} : centre de rotation du monde
	 */
	public void init(Vector2 center) {
		screenMiddle = RotatingCamera.VIEWPORT_WIDTH / 2;
		rotationCenter = center;
		
		SHIP_DISTANCE_FROM_FOE = Math.abs(center.y - (RotatingCamera.VIEWPORT_HEIGHT * LAPINY + ship.getHeight() / 2));
		
//		System.out.println("SHIP_DISTANCE_FROM_FOE : " + SHIP_DISTANCE_FROM_FOE + "=" + center.y + "-(" + RotatingCamera.VIEWPORT_HEIGHT + "*" + LAPINY + "+" + ship.getHeight() + "/ 2)");
	}
	
	/**
	 * Réagit aux inputs utilisateur pour calculer la direction à donner au vaisseau
	 */
	public void getDirection() {
		boolean touched = false;
		boolean touchLeft = false;
		boolean touchRight = false;
		
		if(Gdx.input.isTouched()) {
			int x = Gdx.input.getX();
			
			touchLeft = x < screenMiddle;
			touchRight = x >= screenMiddle;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) || touchLeft) {
			touched = true;
			if (currentDirection == 0 || currentDirection > 0){
				currentDirection = -MIN_ANGLE;
			} else {
				currentDirection -= MIN_ANGLE;
			}
			ship.setDirection(Ship.TURNING_LEFT);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT) || touchRight) {
			touched = true;
			if (currentDirection == 0 || currentDirection < 0) {
				currentDirection = MIN_ANGLE;
			} else {
				currentDirection += MIN_ANGLE;
			}
			ship.setDirection(Ship.TURNING_RIGHT);
		}
		
		if (Math.abs(currentDirection) > MAX_ANGLE) currentDirection = (Math.abs(currentDirection) / currentDirection) * MAX_ANGLE;
		
		if (!touched && (TimeUtils.millis() > lastTime + SPEED_DOWN_DELAY)) {
			lastTime = TimeUtils.millis();
			if (currentDirection != 0) currentDirection -= (Math.abs(currentDirection) / currentDirection) * MIN_ANGLE;
		}
			
		if (!touched && Math.abs(currentDirection) <= MIN_ANGLE) {
			currentDirection = 0;
			ship.setDirection(Ship.STILL);
		}
	}

	/**
	 * Calcule la nouvelle position du vaissaue en fonction des inputs récupérés depuis {@link #getDirection() getDirection}
	 */
	public void update() {
		getDirection();
		
		Vector2 newShipPos = CircleHelper.getPointOnCircle(rotationCenter.x, rotationCenter.y, SHIP_DISTANCE_FROM_FOE, currentAngle);

		ship.update(currentDirection, rotationCenter.y, rotationCenter.y);
		ship.setPosition(newShipPos.x - ship.getWidth() / 2, newShipPos.y - ship.getHeight() / 2);
	}
	
	/**
	 * Dernière angle de rotation calculé
	 */
	public float getLastRotateValue() {
		return currentDirection;
	}

	/**
	 * centre de rotation du monde
	 */
	public Vector2 getRotationCenter() {
		return rotationCenter;
	}
}
