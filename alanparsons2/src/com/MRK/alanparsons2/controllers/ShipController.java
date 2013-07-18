package com.MRK.alanparsons2.controllers;

import com.MRK.alanparsons2.helpers.CircleHelper;
import com.MRK.alanparsons2.models.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Controlleur du vaisseau<BR>
 * Modèle de type singleton, non instanciable<BR>
 * Appeler {@link #getInstance() getInstance} pour récupérer l'instance de la classe
 */
public class ShipController {

	private static ShipController instance = new ShipController();
	
	private static float MIN_ANGLE = .01f;
	private static float MAX_ANGLE = .1f;
	private static long SPEED_DOWN_DELAY = 100;
	private static float SHIP_DISTANCE_FROM_FOE;
	private static float LAPINY = .05f;
	
	private static float screenMiddle;
	private static Vector2 rotationCenter;
	private float currentDirection = 0;
	private long lastTime = TimeUtils.millis();
	
	public static float currentAngle;
	
	/** 
	 * constructeur
	 */
	private ShipController() { }
	
	/** 
	 * Retourne l'instance unique de la classe
	 * @return {@type ShipController}
	 */
	public static ShipController getInstance() {
		return instance;
	}
	
	/**
	 * Initialise l'instance de la clase
	 * @param screenWidth  - float : largeur de l'écran
	 * @param screenHeight - float : hauteur de l'écran
	 * @param center       - {@link Vector2} : centre de rotation du monde
	 */
	public void init(float screenWidth, float screenHeight, Vector2 center) {
		screenMiddle = screenWidth / 2;
		rotationCenter = center;
		
		SHIP_DISTANCE_FROM_FOE = Math.abs(center.y - (screenHeight * LAPINY + Ship.getInstance().getHeight() / 2));
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
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT) || touchRight) {
			touched = true;
			if (currentDirection == 0 || currentDirection < 0) {
				currentDirection = MIN_ANGLE;
			} else {
				currentDirection += MIN_ANGLE;
			}
		}
		
		if (Math.abs(currentDirection) > MAX_ANGLE) currentDirection = (Math.abs(currentDirection) / currentDirection) * MAX_ANGLE;
		
		if (!touched && (TimeUtils.millis() > lastTime + SPEED_DOWN_DELAY)) {
			lastTime = TimeUtils.millis();
			if (currentDirection != 0) currentDirection -= (Math.abs(currentDirection) / currentDirection) * MIN_ANGLE;
		}
			
		if (!touched && Math.abs(currentDirection) <= MIN_ANGLE) currentDirection = 0;
	}

	/**
	 * Calcule la nouvelle position du vaissaue en fonction des inputs récupérés depuis {@link #getDirection() getDirection}
	 */
	public void setPosition() {
		Vector2 newLapinPos = CircleHelper.getPointOnCircle(
				rotationCenter.x, rotationCenter.y, 
				SHIP_DISTANCE_FROM_FOE,
				currentAngle);

		Ship.getInstance().setPosition(newLapinPos.x - Ship.getInstance().getWidth() / 2, newLapinPos.y - Ship.getInstance().getHeight() / 2);
		Ship.getInstance().rotate((float) Math.toDegrees(getLastRotateValue()));
	}
	
	/**
	 * Dernière angle de rotation calculé
	 * @return
	 */
	public float getLastRotateValue() {
		return currentDirection;
	}

	/**
	 * centre de rotation du monde
	 * @return
	 */
	public Vector2 getRotationCenter() {
		return rotationCenter;
	}
}
