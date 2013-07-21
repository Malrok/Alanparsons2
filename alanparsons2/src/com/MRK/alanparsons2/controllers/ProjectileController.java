package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;

import com.MRK.alanparsons2.models.Projectile;
import com.MRK.alanparsons2.models.Weapon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Controlleur des projectiles à l'écran<BR>
 * Gère les instance de {@link Weapon} et émet les projectiles<BR>
 */
public class ProjectileController {

	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	
	public ProjectileController() { 
		
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
//				System.out.println("Emitting projectile at x/y = " + weapon.getPosition().x + "/" + weapon.getPosition().y);
				projectile.setPosition(weapon.getPosition().x, weapon.getPosition().y);
				projectiles.add(projectile);
				weapon.projectileEmitted();
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
