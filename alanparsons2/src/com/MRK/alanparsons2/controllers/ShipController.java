package com.MRK.alanparsons2.controllers;

import java.util.List;

import com.MRK.alanparsons2.factories.LevelBuilder;
import com.MRK.alanparsons2.factories.WeaponFactory;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.MRK.alanparsons2.helpers.WeaponHelper;
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

	public static int MIN_SPEED = 1;
	public static int NORMAL_SPEED = 2;
	public static int MAX_SPEED = 3;
	
	private float distanceFromFoe;
	private float screenMiddle;
	private float rotationCenterx, rotationCentery;
	
	private Ship ship;
	private TouchInputTemplate touchTemplate;
	private float[] speedLimits = new float[5];
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
	public void init(float centerx, float centery, float distanceFromFoe) {
		screenMiddle = Gdx.graphics.getWidth() / 2;
		rotationCenterx = centerx;
		rotationCentery = centery;
		
		speedLimits[0] = touchTemplate.getUpperTouchLimit();
		speedLimits[MIN_SPEED] = Gdx.graphics.getHeight() * touchTemplate.getUpperSpeedLimit();  // ordonnée à l'écran de la vitesse mini
		speedLimits[NORMAL_SPEED] = Gdx.graphics.getHeight() * touchTemplate.getNormalSpeedLimit(); // ordonnée à l'écran de la vitesse normale
		speedLimits[MAX_SPEED] = Gdx.graphics.getHeight() * touchTemplate.getLowerSpeedLimit();  // ordonnée à l'écran de la vitesse maxi
		speedLimits[4] = touchTemplate.getLowerTouchLimit();
		
		this.distanceFromFoe = distanceFromFoe;
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
			
			if (y > speedLimits[0] && y < speedLimits[4]) { // on est dans la zone de touch
				touchLeft = x < screenMiddle;
				touchRight = x >= screenMiddle;
				
				if (y > speedLimits[1]) {// vitesse mini
					newDirection = touchTemplate.getMinSpeed();
				} else if (y < speedLimits[3]) {// vitesse maxi
					newDirection = touchTemplate.getMaxSpeed();
				} else {
					if (speedLimits[1] >= y && y >= speedLimits[2]) // entre mini et normal
						newDirection = touchTemplate.getMinSpeed() + (speedLimits[1] - y) * (touchTemplate.getNormalSpeed() - touchTemplate.getMinSpeed()) / ((speedLimits[1] - speedLimits[2]));
					else // entre normal et maxi
						newDirection = touchTemplate.getNormalSpeed() + (y - speedLimits[2]) * (touchTemplate.getMaxSpeed() - touchTemplate.getNormalSpeed()) / ((speedLimits[3] - speedLimits[2]));
				}
			}
		}
		if (!touchLeft && !touchRight) {
			newDirection = touchTemplate.getMinSpeed();
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) || touchLeft) {
			touched = true;
			if (currentDirection == 0 || currentDirection > 0 || !touchLeft){
				currentDirection -= newDirection;
			} else {
				currentDirection = -newDirection;
			}
			ship.setDirection(Ship.TURNING_LEFT);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT) || touchRight) {
			touched = true;
			if (currentDirection == 0 || currentDirection < 0  || !touchRight) {
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
		
		Vector2 newShipPos = CircleHelper.getPointOnCircle(rotationCenterx, rotationCentery, distanceFromFoe, currentAngle);

		deltaX = newShipPos.x - ship.getWidth() / 2;
		deltaY = newShipPos.y - ship.getHeight() / 2;
		
		ship.update(currentDirection, rotationCentery, rotationCentery);
		ship.setPosition(newShipPos.x - ship.getWidth() / 2, newShipPos.y - ship.getHeight() / 2);
	}
	
	/**
	 * Dernière angle de rotation calculé
	 */
	public float getLastRotateValue() {
		return currentDirection;
	}

	public float[] getShipSpeeds() {
		return new float[]{0, touchTemplate.getMinSpeed(), touchTemplate.getNormalSpeed(), touchTemplate.getMaxSpeed(), 0};
	}
	
	/**
	 * centre de rotation du monde
	 */
	public Vector2 getRotationCenter() {
		return new Vector2(rotationCenterx, rotationCentery);
	}
	
	public void setWeapon(WeaponHelper helper, List<Weapon> weapons, WeaponFactory weaponFactory) {
		Weapon weapon = weaponFactory.createWeapon(helper, LevelBuilder.SHIP, ship.getLevel());
		
		weapons.add(weapon);
		ship.setWeapon(weapon);
	}
}
