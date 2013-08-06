package com.MRK.alanparsons2.controllers;

import java.util.List;

import com.MRK.alanparsons2.factories.LevelBuilder;
import com.MRK.alanparsons2.factories.WeaponFactory;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.MRK.alanparsons2.models.RotatingCamera;
import com.MRK.alanparsons2.models.Ship;
import com.MRK.alanparsons2.models.Weapon;
import com.MRK.alanparsons2.templates.TouchInputTemplate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Controlleur du vaisseau<BR>
 */
public class ShipController {

	private static float SHIP_DISTANCE_FROM_FOE;
	private static float LAPINY = 1.0f;
	
	private static float screenMiddle;
	private static Vector2 rotationCenter;
	
	private Ship ship;
	private TouchInputTemplate touchTemplate;
	private float[] speedLimits = new float[3];
	private float currentDirection = 0;
	private long lastTime = TimeUtils.millis();
	
	public static float currentAngle;
	public static float deltaX, deltaY;
	
	/** 
	 * constructeur
	 */
	public ShipController(Ship ship, TouchInputTemplate touchTemplate) { 
		this.ship = ship;
		this.touchTemplate = touchTemplate;
	}
	
	/**
	 * Initialise l'instance de la clase
	 * @param screenWidth  - float : largeur de l'écran
	 * @param screenHeight - float : hauteur de l'écran
	 * @param center       - {@link Vector2} : centre de rotation du monde
	 */
	public void init(Vector2 center) {
		screenMiddle = Gdx.graphics.getWidth() / 2;
		rotationCenter = center;
		
		speedLimits[0] = Gdx.graphics.getHeight() * touchTemplate.getUpperSpeedLimit();  // ordonnée à l'écran de la vitesse mini
		speedLimits[1] = Gdx.graphics.getHeight() * touchTemplate.getNormalSpeedLimit(); // ordonnée à l'écran de la vitesse normale
		speedLimits[2] = Gdx.graphics.getHeight() * touchTemplate.getLowerSpeedLimit();  // ordonnée à l'écran de la vitesse maxi
		
		System.out.println("speedLimits 0=" + speedLimits[0] + " 1=" + speedLimits[1] + " 2=" + speedLimits[2]);
		
		SHIP_DISTANCE_FROM_FOE = Math.abs(center.y - (RotatingCamera.VIEWPORT_HEIGHT * LAPINY) + ship.getHeight() / 2);
	}
	
	/**
	 * Réagit aux inputs utilisateur pour calculer la direction à donner au vaisseau
	 */
	public void getDirection() {
		float newDirection = 0;
		
		boolean touched = false;
		boolean touchLeft = false;
		boolean touchRight = false;
		
		if(Gdx.input.isTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			
			touchLeft = x < screenMiddle;
			touchRight = x >= screenMiddle;
			
			if (y > speedLimits[0]) {// vitesse mini
				newDirection = touchTemplate.getMinSpeed();
			} else if (y < speedLimits[2]) {// vitesse maxi
				newDirection = touchTemplate.getMaxSpeed();
			} else {
				if (speedLimits[0] > y && y >= speedLimits[1]) // entre mini et normal
					newDirection = touchTemplate.getMinSpeed() + (speedLimits[0] - y) * (touchTemplate.getNormalSpeed() - touchTemplate.getMinSpeed()) / ((speedLimits[0] - speedLimits[1]));
				else // entre normal et maxi
					newDirection = touchTemplate.getNormalSpeed() + (y - speedLimits[1]) * (touchTemplate.getMaxSpeed() - touchTemplate.getNormalSpeed()) / ((speedLimits[2] - speedLimits[1]));
			}
		}
		if (!touchLeft && !touchRight) {
			newDirection = touchTemplate.getMinSpeed();
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) || touchLeft) {
			touched = true;
			if (currentDirection == 0 || currentDirection > 0){
				currentDirection -= newDirection;
			} else {
				currentDirection = -newDirection;
			}
			ship.setDirection(Ship.TURNING_LEFT);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT) || touchRight) {
			touched = true;
			if (currentDirection == 0 || currentDirection < 0) {
				currentDirection += newDirection;
			} else {
				currentDirection = newDirection;
			}
			ship.setDirection(Ship.TURNING_RIGHT);
		}
		
		if (Math.abs(currentDirection) > touchTemplate.getMaxSpeed()) currentDirection = (Math.abs(currentDirection) / currentDirection) * touchTemplate.getMaxSpeed();
		
		if (!touched && (TimeUtils.millis() > lastTime + touchTemplate.getSpeedDownDelay())) {
			lastTime = TimeUtils.millis();
			if (currentDirection != 0) currentDirection -= (Math.abs(currentDirection) / currentDirection) * touchTemplate.getMinSpeed();
		}
		
		if (!touched && Math.abs(currentDirection) <= touchTemplate.getMinSpeed()) {
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

		deltaX = newShipPos.x - ship.getWidth() / 2;
		deltaY = newShipPos.y - ship.getHeight() / 2;
		
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
	
	public void setWeapon(List<Weapon> weapons, WeaponFactory weaponFactory) {
		Weapon weapon = weaponFactory.createWeapon(LevelBuilder.SHIP, ship.getLevel());
		
		weapons.add(weapon);
		ship.setWeapon(weapon);
	}
}
