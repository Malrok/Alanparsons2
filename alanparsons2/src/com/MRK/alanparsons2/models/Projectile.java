package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Classe héritant de {@link Sprite}, définissant un projectile
 */
public class Projectile implements Disposable {

//	public static float PROJECTILE_WIDTH = 1;
//	public static float PROJECTILE_HEIGHT = 0.4f;
	
	private TextureRegion texture;
//	private Vector2 position;
	private float x, y;
	private float angle;
	
	private Vector2 speed;
	private float power;
	private float width, height;
	
	private Sprite emitter;

	/**
	 * Constructeur de la classe
	 * @param texture - {@link Texture}
	 * @param speed   - {@link Vector2}
	 * @param power   - int
	 */
	public Projectile(Sprite emitter, TextureRegion texture, Vector2 speed, float power) {
//		super(texture.getTexture());
//		super.setSize(PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
//		super.setOrigin(PROJECTILE_WIDTH / 2, PROJECTILE_HEIGHT / 2);
//		System.out.println("Projectile speed x/y = " + speed.x + "/" + speed.y);
		
		this.texture = texture;
		this.emitter = emitter;
		this.speed = speed;
		this.power = power;
//		super.rotate(CircleHelper.getDAngle(speed.x, speed.y));
		this.angle = CircleHelper.getDAngle(speed.x, speed.y);
	}
	
	public void dispose() {
		emitter = null;
	}
	
	public Vector2 getSpeed() {
		return speed;
	}
	
	public float getPower() {
		return power;
	}
	
	public void setPower(float power) {
		this.power = power;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
//		super.setSize(width, height);
//		super.setOrigin(width / 2, height / 2);
	}

	public Sprite getEmitter() {
		return emitter;
	}
	
	/**
	 * Calcul de la nouvelle position
	 */
	public void update() {
//		float x = getX() + speed.x,
//			  y = getY() + speed.y;
		setPosition(x, y);
	}
	
	public void draw(SpriteBatch batch) {
//		System.out.println("drawing projectile x/y/w/h " + x + "/" + y + "/" + width + "/" + height);
//		batch.draw(texture, x - width / 2, y - height / 2, x, y, 
//				width, height, 1, 1, angle);
		batch.draw(texture, x, y, x, y, 
				width, height, 1, 1, angle);
	}
}
