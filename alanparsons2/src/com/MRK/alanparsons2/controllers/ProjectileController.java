package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.factories.ProjectileFactory;
import com.MRK.alanparsons2.models.Projectile;
import com.MRK.alanparsons2.models.Weapon;
import com.badlogic.gdx.utils.Disposable;

/**
 * Controlleur des projectiles à l'écran<BR>
 * Gère les instance de {@link Weapon} et émet les projectiles<BR>
 */
public class ProjectileController implements Disposable {

	private ProjectileFactory projectileFactory;
	private List<Projectile> projectiles;
	private List<Projectile> toBeRemoved = new ArrayList<Projectile>();
	
	public ProjectileController(ProjectileFactory projectileFactory, List<Projectile> projectiles) {
		this.projectileFactory = projectileFactory;
		this.projectiles = projectiles;
	}
	
	public void dispose() {
		for (Projectile projectile : projectiles)
			projectile.dispose();
	}
	
	/**
	 * Mise à jour cyclique du controlleur
	 */
	public void update(List<Weapon> weapons) {
		updateProjectiles();
		fireWeapons(weapons);
	}
	
	private void fireWeapons(List<Weapon> weapons) {
		for (Weapon weapon : weapons) {
			if (weapon.shouldEmitProjectile()) {
				Projectile projectile = projectileFactory.createProjectile(weapon);
				if (projectile != null) projectiles.add(projectile);
				weapon.projectileEmitted();
			}
		}
	}
	
	private void updateProjectiles() {
		for (Projectile projectile : projectiles) {
			projectile.update();
		}
	}
	
	public List<Projectile> getToBeRemovedList() {
		return toBeRemoved;
	}
	
	public void refreshProjectilesList() {
		projectiles.removeAll(toBeRemoved);
		toBeRemoved.clear();
	}
}
