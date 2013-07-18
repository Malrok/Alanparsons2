package com.MRK.alanparsons2.models;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Une arme<BR>
 * Modèle destiné à émettre des {@link Projectile}
 */
public class Weapon {

	private boolean enabled = false;
	private Texture texture;
	private Vector2 position;
	private ArrayList<Vector2> aimAt = new ArrayList<Vector2>();
	private int shootFrequency = 3; // shots per second
	private int shootPower = 1;
	private long lastShoot;
	private boolean shouldEmitProjectile = false;
	
	/**
	 * Constructeur
	 * @param texture        - {@link Texture} : la texture à appliquer au projectile
	 * @param aimAt          - {@link Vector2} :  la direction vers laquelle pointe l'arme
	 * @param shootFrequency - int : le nombre de tirs par seconde
	 * @param shootPower     - int : la puissance d'un {@link Projectile}
	 */
	public Weapon(Texture texture, Vector2 aimAt, int shootFrequency, int shootPower) {
		this.texture = texture;
		this.position = new Vector2();
		this.aimAt.add(aimAt);
		this.shootFrequency = shootFrequency;
		this.shootPower = shootPower;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (enabled)
			lastShoot = TimeUtils.millis();
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Renvoie la direction dans laquelle pointe l'arme
	 * @return {@link Vector2}
	 */
	public Vector2 getAimAt() {
		return aimAt.get(0);
	}
	
	/**
	 * Définit la direction dans laquelle pointe l'arme
	 * @param aimAt - {@link Vector2}
	 */
	public void setAimAt(Vector2 aimAt) {
		this.aimAt.get(0).set(aimAt);
	}
	
	public int getShootFrequency() {
		return shootFrequency;
	}
	
	public int getShootPower() {
		return shootPower;
	}
	
	/**
	 * Mise à jour de l'instance de la classe.<BR>
	 * Définit si un tir doit être effectué.
	 */
	public void update() {
//		System.out.println("Weapon update " + TimeUtils.millis() + " > " + lastShoot + " + " + (1000 / shootFrequency) + " = " + (TimeUtils.millis() > lastShoot + (1000 / shootFrequency)));
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
}
