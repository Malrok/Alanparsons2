package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;

import com.MRK.alanparsons2.models.Projectile;
import com.MRK.alanparsons2.models.Weapon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Controlleur des projectiles à l'écran<BR>
 * Gère les instance de {@link Weapon} et émet les projectiles<BR>
 * Modèle de type singleton, non instanciable<BR>
 * Appeler {@link #getInstance() getInstance} pour récupérer l'instance de la classe
 */
public class ProjectileController {

	private static ProjectileController instance = new ProjectileController();
	
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	
	private ProjectileController() { }
	
//	private boolean emit = true;
	
	/** 
	 * retourne l'instance unique de la classe
	 * @return {@type ProjectileController}
	 */
	public static ProjectileController getInstance() {
		return instance;
	}
	
	/**
	 * Ajoute un objet de type {@link Weapon} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}
	
	/**
	 * Mise à jour cyclique du controlleur
	 */
	public void update() {
		updateWeapons();
		updateProjectiles();
		fireWeapons();
	}
	
	public void fireWeapons() {
		for (Weapon weapon : weapons) {
			if (weapon.shouldEmitProjectile()) {
				Projectile projectile = new Projectile(weapon.getTexture(), new Vector2(weapon.getAimAt()), weapon.getShootPower());
				projectile.setPosition(weapon.getPosition().x, weapon.getPosition().y);
				projectiles.add(projectile);
				weapon.projectileEmitted();
//				emit = false;
			}
		}
	}
	
	public void updateWeapons() {
		for (Weapon weapon : weapons) {
			weapon.update();
		}
	}
	
	public void updateProjectiles() {
		for (Projectile projectile : projectiles) {
			projectile.update();
		}
	}
	
	public void drawProjectiles(SpriteBatch batch) {
		for (Projectile projectile : projectiles) {
//			System.out.println("Projectile x/y = " + projectile.getX() + "/" + projectile.getY());
			projectile.draw(batch);
		}
	}
}
