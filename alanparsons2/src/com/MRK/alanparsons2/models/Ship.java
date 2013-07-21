package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe héritant de {@link Sprite}, définissant le vaisseau du joueur
 */
public class Ship extends Sprite {
	
	private static final float PROJECTILE_SPEED = 1.0f;
	public static final int STILL = 0;
	public static final int TURNING_LEFT = 1;
	public static final int TURNING_RIGHT = 2;
	public static final int SHIP_WIDTH = 4;
	public static final int SHIP_HEIGHT= 4;
	
	private Texture[] textures = new Texture[3];
	private Weapon weapon;
	
	private int currentDirection = STILL;
	
	public Ship() {
		super();
		textures[STILL] = new Texture(Gdx.files.internal("data/ship.png"));
		textures[TURNING_LEFT] = new Texture(Gdx.files.internal("data/ship_turning_left.png"));
		textures[TURNING_RIGHT] = new Texture(Gdx.files.internal("data/ship_turning_right.png"));
		
		setSize(SHIP_WIDTH, SHIP_HEIGHT);
		setOrigin(SHIP_WIDTH / 2, SHIP_HEIGHT / 2);
	}
	
	/** 
	 * retourne l'arme de la classe
	 * @return {@type Weapon}
	 */
	public Weapon getWeapon() {
		return weapon;
	}
	
	/** 
	 * Définit l'arme de la classe
	 * @param texture - {@type Texture}
	 */
	public void setWeapon(Texture texture) {
		weapon = new Weapon(texture, new Vector2(0, 1), 3, 1);
		weapon.setEnabled(true);
	}
	
	/** 
	 * mise à jour du vaisseau<BR>
	 * réoriente l'arme<BR>
	 * @param lastRotateValue - float : dernier angle de rotation appliqué au vaisseau
	 */
	public void update(float lastRotateValue, float x, float y) {
		weapon.setAimAt(CircleHelper.getVectorAimingAtCenter(getX() + getOriginX(), getY() + getOriginY(), x, y, PROJECTILE_SPEED));
		weapon.update();
	}
	
	/**
	 * mise à jour de la position du vaisseau et de son arme<BR>
	 */
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		weapon.setPosition(x - getOriginX(), y - getOriginY());
	}
	
	/**
	 * Met à jour la direction donnée au vaisseau
	 */
	public void setDirection(int newDirection) {
		currentDirection = newDirection;
	}
	
	/**
	 * Récupération de la texture du vaisseau
	 */
	public Texture getTexture() {
		return textures[currentDirection];
	}
	
	/**
	 * rendering du vaisseau
	 */
	public void draw(SpriteBatch batch) {
		Texture texture = getTexture();
		
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), 
				Ship.SHIP_WIDTH, Ship.SHIP_HEIGHT, 1, 1, (float)Math.toDegrees(ShipController.currentAngle) + 90, 
				0, 0, texture.getWidth(), texture.getHeight(), false, false);
	}
}
