package com.MRK.alanparsons2.models;

import java.util.Map;
import java.util.Map.Entry;

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
	private Weapon weapon;	
	private int currentDirection = STILL;
		
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
	public void setWeapon(Map<String, Weapon> weapons) {
		for (Entry<String, Weapon> entry : weapons.entrySet()) {
			if (entry.getValue().getEmitter().equals(this)) {
				weapon = entry.getValue();
				weapon.setAimAt(new Vector2(0, 1));
				weapon.setEnabled(true);
			}
		}
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
	
	public void addTexture(int phase, TextureRegion texture) {
		if (texture != null)
			textures[phase] = texture;
	}
	
	/**
	 * rendering du vaisseau
	 */
	public void draw(SpriteBatch batch) {
		batch.draw(textures[currentDirection], getX(), getY(), getOriginX(), getOriginY(), 
				getWidth(), getHeight(), 1, 1, (float)Math.toDegrees(ShipController.currentAngle) + 90); 
	}
}
