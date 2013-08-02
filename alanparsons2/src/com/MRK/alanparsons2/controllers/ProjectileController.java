package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.factories.ProjectileFactory;
import com.MRK.alanparsons2.models.EnemyShip;
import com.MRK.alanparsons2.models.PixmapSprite;
import com.MRK.alanparsons2.models.Projectile;
import com.MRK.alanparsons2.models.Weapon;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Controlleur des projectiles à l'écran<BR>
 * Gère les instance de {@link Weapon} et émet les projectiles<BR>
 */
public class ProjectileController implements Disposable {

	private ProjectileFactory projectileFactory;
	private List<Weapon> weapons = new ArrayList<Weapon>();
	private List<Projectile> projectiles;
	private List<Sprite> targets = new ArrayList<Sprite>();
	private List<Projectile> toBeRemoved = new ArrayList<Projectile>();
	private final Vector2 position = new Vector2();	
	private List<Vector2> impacts = new ArrayList<Vector2>();
	
	public ProjectileController(ProjectileFactory projectileFactory, List<Projectile> projectiles) {
		this.projectileFactory = projectileFactory;
		this.projectiles = projectiles;
	}
	
	public void dispose() {
		for (Weapon weapon : weapons)
			weapon.dispose();
		
		weapons.clear();
		
		for (Projectile projectile : projectiles)
			projectile.dispose();
	}
	
	/**
	 * Ajoute un objet de type {@link Weapon} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}
	
	/**
	 * Ajoute une liste d'objet de type {@link Weapon} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addWeapons(List<Weapon> weapons) {
		this.weapons.addAll(weapons);
	}
	
	/**
	 * Ajoute une cible de type {@link Sprite} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addTarget(Sprite target) {
		targets.add(target);
	}
	
	/**
	 * Ajoute une liste de cibles de type {@link Sprite} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addTargets(List<EnemyShip> list) {
		this.targets.addAll(list);
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
//				Projectile projectile = new Projectile(weapon.getEmitter(), weapon.getProjectileTexture(), new Vector2(weapon.getAimAt()), weapon.getShootPower());
//				projectile.setPosition(weapon.getPosition().x - Projectile.PROJECTILE_WIDTH / 2, weapon.getPosition().y);
				projectiles.add(projectileFactory.createProjectile(weapon));
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
//		boolean collide;
		
		toBeRemoved.clear();
		
		for (Projectile projectile : projectiles) {
			projectile.update();
			
			if (collide(projectile)) {
				toBeRemoved.add(projectile);
			}
		}
		
		projectiles.removeAll(toBeRemoved);
	}
	
	private boolean collide(Projectile projectile) {
		for (Sprite target : targets) {
//			System.out.println("Projectile from " + projectile.getEmitter().getClass().toString() + " @ x/y/w/h " +
//					projectile.getX() + "/" + projectile.getY() + "/" + projectile.getWidth() + "/" + projectile.getHeight() +
//					" target " + target.getClass().toString() + " rect = " + target.getBoundingRectangle().toString());
			if (projectile.getEmitter() != target && target.getBoundingRectangle().contains(projectile.getX() + projectile.getWidth() / 2, projectile.getY() + projectile.getHeight() / 2)) {
				if (target instanceof PixmapSprite) {
					((PixmapSprite) target).project(position, (int) projectile.getX() + projectile.getWidth() / 2, (int) projectile.getY() + projectile.getHeight() / 2);
//					System.out.println("projected position x/y " + position.x + "/" + position.y); 
					if (((PixmapSprite) target).collides(position)) {
						impacts.add(new Vector2(projectile.getX() - projectile.getWidth() / 2, projectile.getY() - projectile.getHeight() / 2));
						((PixmapSprite) target).eraseCircle(position, (int) projectile.getPower());
						return true;
					}
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<Vector2> getImpacts() {
		return impacts;
	}
	
	public void clearImpacts() {
		impacts.clear();
	}
}
