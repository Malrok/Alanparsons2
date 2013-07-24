package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.models.PixmapSprite;
import com.MRK.alanparsons2.models.Projectile;
import com.MRK.alanparsons2.models.Weapon;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Controlleur des projectiles à l'écran<BR>
 * Gère les instance de {@link Weapon} et émet les projectiles<BR>
 */
public class ProjectileController implements Disposable {

	private List<Weapon> weapons = new ArrayList<Weapon>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Sprite> targets = new ArrayList<Sprite>();
	private List<Projectile> toBeRemoved = new ArrayList<Projectile>();
	
	private final Vector2 position = new Vector2();
	
	private List<Vector2> impacts = new ArrayList<Vector2>();
	
//	public ProjectileController() { }
	
	public void dispose() {
		for (Weapon weapon : weapons)
			weapon.dispose();
		
		weapons.clear();
		
		for (Projectile projectile : projectiles)
			projectile.dispose();
		
		projectiles.clear();
	}
	
	/**
	 * Ajoute un objet de type {@link Weapon} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}
	
	/**
	 * Ajoute une cible de type {@link Sprite} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addTarget(Sprite target) {
		targets.add(target);
	}
	
	/**
	 * Mise à jour cyclique du controlleur
	 */
	public void update() {
		updateWeapons();
		updateProjectiles();
		fireWeapons();
//		impacts.clear();
	}
	
	public void fireWeapons() {
		for (Weapon weapon : weapons) {
			if (weapon.shouldEmitProjectile()) {
				Projectile projectile = new Projectile(weapon.getEmitter(), weapon.getProjectileTexture(), new Vector2(weapon.getAimAt()), weapon.getShootPower());
				projectile.setPosition(weapon.getPosition().x - Projectile.PROJECTILE_WIDTH / 2, weapon.getPosition().y);
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
		boolean collide;
		
		toBeRemoved.clear();
		
		for (Projectile projectile : projectiles) {
			collide = false;
			
			projectile.update();
			
			for (Sprite target : targets) {
				if (projectile.getEmitter() != target && target.getBoundingRectangle().contains(projectile.getX() - projectile.getWidth() / 2, projectile.getY() - projectile.getHeight() / 2)) {
					if (target instanceof PixmapSprite) {
						((PixmapSprite) target).project(position, (int) projectile.getX() + projectile.getWidth(), (int) projectile.getY() + projectile.getHeight() / 2);
//						((PixmapSprite) target).project(position, (int) projectile.getX(), (int) projectile.getY());
						collide = ((PixmapSprite) target).collides(position);
						if (collide) {
							impacts.add(new Vector2(projectile.getX() - projectile.getWidth() / 2, projectile.getY() - projectile.getHeight() / 2));
							((PixmapSprite) target).eraseCircle(position, projectile.getPower());
						}
					} else {
						collide = true;
					}
				}
			}
			
			if (collide) {
				toBeRemoved.add(projectile);
			}
		}
		
		projectiles.removeAll(toBeRemoved);
	}
	
	public void drawProjectiles(SpriteBatch batch) {
		for (Projectile projectile : projectiles) {
			projectile.draw(batch);
		}
	}
	
	public List<Vector2> getImpacts() {
		return impacts;
	}
	
	public void clearImpacts() {
		impacts.clear();
	}
}
