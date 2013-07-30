package com.MRK.alanparsons2.models;

import java.util.Map;
import java.util.Map.Entry;

import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Classe héritant de {@link Sprite}, définissant le vaisseau du joueur
 */
public class Ship extends Sprite implements Disposable {
	
	public static final float PROJECTILE_SPEED = .1f;
	public static final int STILL = 0;
	public static final int TURNING_LEFT = 1;
	public static final int TURNING_RIGHT = 2;
//	public static final int SHIP_WIDTH = 4;
//	public static final int SHIP_HEIGHT= 4;
	
	private TextureRegion[] textures = new TextureRegion[3];
	private Weapon weapon;
	
	private int currentDirection = STILL;
	
	public Ship() {
		super();
//		textures[STILL] = new Texture(Gdx.files.internal("data/ship.png"));
//		textures[TURNING_LEFT] = new Texture(Gdx.files.internal("data/ship_turning_left.png"));
//		textures[TURNING_RIGHT] = new Texture(Gdx.files.internal("data/ship_turning_right.png"));
		
//		setSize(SHIP_WIDTH, SHIP_HEIGHT);
//		setOrigin(SHIP_WIDTH / 2, SHIP_HEIGHT / 2);
	}
	
//	public Ship clone() {
//		Ship newShip = new Ship();
//		newShip.set(this);
//		for (int phase = 0; phase < 5; phase++)
//			newShip.setTexture(phase, textures[phase]);
//	}
	
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
//		weapon = new Weapon(this, texture, new Vector2(0, 1), 3, 1);
		for (Entry<String, Weapon> entry : weapons.entrySet()) {
			if (entry.getValue().getEmitter().equals(this)) {
				weapon = entry.getValue();
				weapon.setEnabled(true);
				weapon.setAimAt(new Vector2(0, 1));
			}
		}
	}
	
	/** 
	 * mise à jour du vaisseau<BR>
	 * réoriente l'arme<BR>
	 * @param lastRotateValue - float : dernier angle de rotation appliqué au vaisseau
	 */
	public void update(float lastRotateValue, float x, float y) {
		weapon.setAimAt(CircleHelper.getVectorAimingAtCenter(getX() + getWidth() / 2, getY() + getHeight() / 2, x, y, PROJECTILE_SPEED));
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
	
//	/**
//	 * Récupération de la texture du vaisseau
//	 */
//	public TextureRegion getTexture() {
//		return textures[currentDirection];
//	}
	
	public void addTexture(int phase, TextureRegion texture) {
		if (texture != null)
			textures[phase] = texture;
	}
	
//	public void setTexture(int phase, Texture texture) {
//		textures[phase] = texture;
//	}
	
	/**
	 * rendering du vaisseau
	 */
	public void draw(SpriteBatch batch) {
//		Texture texture = getTexture();
//		System.out.println("drawing ship -- texture is null = " + (textures[currentDirection] == null) + " x/y/origx/origy/w/h " +
//				getX() + "/" + getY() + "/" + getOriginX() + "/" + getOriginY() + "/" +  
//				getWidth() + "/" + getHeight());
		batch.draw(textures[currentDirection], getX(), getY(), getOriginX(), getOriginY(), 
				getWidth(), getHeight(), 1, 1, (float)Math.toDegrees(ShipController.currentAngle) + 90); 
//				0, 0, texture.getWidth(), texture.getHeight(), false, false);
	}
}
