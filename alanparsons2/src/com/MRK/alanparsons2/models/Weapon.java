package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Une arme<BR>
 * Modèle destiné à émettre des {@link Projectile}
 */
public class Weapon extends Sprite implements Disposable {

	private String name;
	private boolean enabled = false;
	private int projectileType;
//	private Vector2 position = new Vector2();
	private Vector2 aimAt;
	private int shootFrequency; // shots per second
	private long lastShoot;
	private boolean shouldEmitProjectile = false;
	private String emitterName;
	private Sprite emitter;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void dispose() {
		emitter = null;
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

//	public void setPosition(float x, float y) {
//		this.position.x = x;
//		this.position.y = y;
//	}
//	
//	public Vector2 getPosition() {
//		return position;
//	}
	
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
	
	public void upgrade(WeaponTemplate template) {
		this.projectileType = template.getProjectileType();
		this.shootFrequency = template.getShootFrequency();
	}
}
