package com.MRK.alanparsons2.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Une arme<BR>
 * Modèle destiné à émettre des {@link Projectile}
 */
public class Weapon implements Disposable {

	private String name;
	private boolean enabled = false;
	private int projectileType;
//	private TextureRegion projectileTexture;
	private Vector2 position = new Vector2();
//	private List<Vector2> aimAt = new ArrayList<Vector2>();
	private Vector2 aimAt;
	private int shootFrequency = 3; // shots per second
	private long lastShoot;
	private boolean shouldEmitProjectile = false;
	private String emitterName;
	private Sprite emitter;
	
//	/**
//	 * Constructeur
//	 * @param texture        - {@link Texture} : la texture à appliquer au projectile
//	 * @param aimAt          - {@link Vector2} :  la direction vers laquelle pointe l'arme
//	 * @param shootFrequency - int : le nombre de tirs par seconde
//	 * @param shootPower     - int : la puissance d'un {@link Projectile}
//	 */
//	public Weapon(Sprite emitter, Texture texture, Vector2 aimAt, int shootFrequency, int shootPower) {
//		this.emitter = emitter;
////		this.projectileTexture = texture;
//		this.position = new Vector2();
//		this.aimAt.add(aimAt);
//		this.shootFrequency = shootFrequency;
//		this.shootPower = shootPower;
//	}
	
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
	
//	public TextureRegion getProjectileTexture() {
//		return projectileTexture;
//	}
//	
//	public void setProjectileTexture(TextureRegion texture) {
//		projectileTexture = texture;
//	}
	
	public int getProjectileType() {
		return projectileType;
	}

	public void setProjectileType(int projectileType) {
		this.projectileType = projectileType;
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
//		return aimAt.get(0);
		return aimAt;
	}
	
	/**
	 * Définit la direction dans laquelle pointe l'arme
	 * @param aimAt - {@link Vector2}
	 */
	public void setAimAt(Vector2 aimAt) {
//		this.aimAt.get(0).set(aimAt);
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
}
