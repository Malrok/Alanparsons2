package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.helpers.CircleHelper;
import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Une arme<BR>
 * Modèle destiné à émettre des {@link Projectile}
 */
public class Weapon extends Sprite implements Disposable {

	private String name;
	private TextureRegion texture;
	private boolean enabled = false;
	private int projectileType;
	private Vector2 aimAt;
	private float angle;
	private int shootFrequency; // shots per second
	private long lastShoot;
	private boolean shouldEmitProjectile = false;
	private String emitterName;
	private Sprite emitter;
	private int hps;
	private int touchPoints, destroyPoints;

	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if (texture != null) {
			batch.draw(texture, getX() - getWidth() / 2, getY() - getHeight() / 2, getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, angle, true);
		}
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		if (getWidth() != 0 && getHeight() != 0) {
			setOrigin(getWidth() / 2, getHeight() / 2);
		}
	}
	
	public void dispose() {
		emitter = null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (enabled)
			lastShoot = TimeUtils.millis();
	}
	
	public int getProjectileType() {
		return projectileType;
	}

	public void setProjectileType(int projectileType) {
		this.projectileType = projectileType;
	}
	
	/**
	 * Renvoie la direction dans laquelle pointe l'arme
	 * @return {@link Vector2}
	 */
	public Vector2 getAimAt() {
		return aimAt;
	}
	
	/**
	 * Définit la direction dans laquelle pointe l'arme
	 * @param aimAt - {@link Vector2}
	 */
	public void setAimAt(Vector2 aimAt) {
		this.aimAt = aimAt;
		angle = CircleHelper.getDAngle(getX() - aimAt.x, getY() - aimAt.y);
	}
	
	public int getShootFrequency() {
		return shootFrequency;
	}
	
	public void setShootFrequency(int shootFrequency) {
		this.shootFrequency = shootFrequency;
	}
	
	/**
	 * Mise à jour de l'instance de la classe.<BR>
	 * Définit si un tir doit être effectué.
	 */
	public void update() {
		if (TimeUtils.millis() > (long)(lastShoot + (1000 / shootFrequency)))
			shouldEmitProjectile = true;
	}
	
	public boolean shouldEmitProjectile() {
		return enabled && shouldEmitProjectile;
	}
	
	public void projectileEmitted() {
		shouldEmitProjectile = false;
		lastShoot = TimeUtils.millis();
	}

	public String getEmitterName() {
		return emitterName;
	}

	public void setEmitterName(String emitterName) {
		this.emitterName = emitterName;
	}

	public Sprite getEmitter() {
		return emitter;
	}
	
	public void setEmitter(Sprite emitter) {
		this.emitter = emitter;
	}
	
	public int getHps() {
		return hps;
	}

	public void setHps(int hps) {
		this.hps = hps;
	}

	public int getTouchPoints() {
		return touchPoints;
	}

	public void setTouchPoints(int touchPoints) {
		this.touchPoints = touchPoints;
	}

	public int getDestroyPoints() {
		return destroyPoints;
	}

	public void setDestroyPoints(int destroyPoints) {
		this.destroyPoints = destroyPoints;
	}

	public void upgrade(WeaponTemplate template) {
		this.projectileType = template.getProjectileType();
		this.shootFrequency = template.getShootFrequency();
		this.hps = template.getHps();
	}
}
