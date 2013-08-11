package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.controllers.ShipController;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Classe héritant de {@link Sprite}, définissant le vaisseau du joueur
 */
public class Ship extends Sprite implements Disposable {
	
	public static final int STILL = 0;
	public static final int TURNING_LEFT = 1;
	public static final int TURNING_RIGHT = 2;
	
	private TextureRegion[] textures = new TextureRegion[3];
//	private Shape shape;
	private Weapon weapon;
	private int level;
	private int currentDirection = STILL;
	private float yFromScreen;
		
	public void dispose() {
		weapon.dispose();
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		setOrigin(width / 2, height / 2);
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
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
		this.weapon.setAimAt(new Vector2(0, 1));
		this.weapon.setEnabled(true);
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/** 
	 * mise à jour du vaisseau<BR>
	 * réoriente l'arme<BR>
	 * @param lastRotateValue - float : dernier angle de rotation appliqué au vaisseau
	 */
	public void update(float lastRotateValue, float x, float y) {
		weapon.setAimAt(new Vector2(x, y));
		weapon.update();
	}
	
	/**
	 * mise à jour de la position du vaisseau et de son arme<BR>
	 */
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		weapon.setPosition(x + getWidth() / 2, y + getHeight() / 2);
	}
	
	/**
	 * Met à jour la direction donnée au vaisseau
	 */
	public void setDirection(int newDirection) {
		currentDirection = newDirection;
	}
	
	public float getyFromScreen() {
		return yFromScreen;
	}

	public void setyFromScreen(float yFromScreen) {
		this.yFromScreen = yFromScreen;
	}

	public void addTexture(int phase, TextureRegion texture) {
		if (texture != null)
			textures[phase] = texture;
	}
	
//	public Shape getShape() {
//		return shape;
//	}
//
//	public void setShape(Shape shape) {
//		this.shape = shape;
//	}

	/**
	 * rendering du vaisseau
	 */
	public void draw(SpriteBatch batch) {
//		if (shape == null) {
			batch.draw(textures[currentDirection], getX(), getY(), getOriginX(), getOriginY(), 
					getWidth(), getHeight(), 1, 1, (float)Math.toDegrees(ShipController.currentAngle) + 90);
//		} else {
//			
//		}
	}
}
