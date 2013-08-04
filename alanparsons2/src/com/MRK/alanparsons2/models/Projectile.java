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

	private TextureRegion texture;
	private float x, y;
	private float angle;	
	private Vector2 speed;
	private float power;
	private float width, height;
	private float originX, originY;
	
	private Sprite emitter;

	/**
	 * Constructeur de la classe
	 * @param texture - {@link Texture}
	 * @param speed   - {@link Vector2}
	 * @param power   - int
	 */
	public Projectile(Sprite emitter, TextureRegion texture, Vector2 speed, float power) {
		this.texture = texture;
		this.emitter = emitter;
		this.speed = speed;
		this.power = power;
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
		
		this.originX = width / 2;
		this.originY = height / 2;
	}

	public Sprite getEmitter() {
		return emitter;
	}
	
	/**
	 * Calcul de la nouvelle position
	 */
	public void update() {
		float x = getX() + speed.x,
			  y = getY() + speed.y;
		setPosition(x, y);
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, x, y, originX, originY, width, height, 1.0f, 1.0f, angle);
	}
}
