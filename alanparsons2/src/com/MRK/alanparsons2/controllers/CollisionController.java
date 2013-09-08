package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.helpers.PixmapHelper;
import com.MRK.alanparsons2.models.EnemyShip;
import com.MRK.alanparsons2.models.Projectile;
import com.MRK.alanparsons2.models.Ship;
import com.MRK.alanparsons2.models.WeakPoint;
import com.MRK.alanparsons2.models.Weapon;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class CollisionController {

	private PixmapHelper pixHelper;
	private final Vector2 position = new Vector2();
	private List<Projectile> projectiles;
	private List<Sprite> targets = new ArrayList<Sprite>();
	private List<Vector2> impacts = new ArrayList<Vector2>();
	
	public CollisionController(List<Projectile> projectiles, PixmapHelper pixHelper) {
		this.projectiles = projectiles;
		this.pixHelper = pixHelper;
	}
	
	/**
	 * Ajoute une cible de type {@link Sprite} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addTarget(Sprite target) {
		targets.add(target);
	}
	
	/**
	 * Ajoute une liste de cibles de type {@link EnemyShip} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addEnemyTargets(List<EnemyShip> list) {
		this.targets.addAll(list);
	}

	/**
	 * Ajoute une liste de cibles de type {@link Weapon} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addWeaponTargets(List<Weapon> list) {
		this.targets.addAll(list);
	}
	
	/**
	 * Ajoute une liste de cibles de type {@link WeakPoint} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addWeakPointsTargets(List<WeakPoint> list) {
		this.targets.addAll(list);
	}
	
	public void removeWeaponsTargets(List<Weapon> list) {
		targets.removeAll(list);
	}
	
	public void removeWeakPointsTargets(List<WeakPoint> list) {
		targets.removeAll(list);
	}
	
	/**
	 * Vérifie si les projectiles passés en paramètre touche une cible<BR>
	 * Génère un impact<BR>
	 * @param projectile
	 * @return
	 */
	public void computeCollisions(List<Projectile> toBeRemoved) {
		for (Projectile projectile : projectiles) {
			for (Sprite target : targets) {
				if (projectile.getEmitter() != target && target.getBoundingRectangle().contains(projectile.getX() + projectile.getWidth() / 2, projectile.getY() + projectile.getHeight() / 2)) {
					if (target instanceof EnemyShip) {
						pixHelper.screenCoordsToPixmap(((EnemyShip) target).getHull(), position, (int) projectile.getX() + projectile.getWidth() / 2, (int) projectile.getY() + projectile.getHeight() / 2);
						if (pixHelper.collides(((EnemyShip) target).getHull(), position)) {
							impacts.add(new Vector2(projectile.getX() - projectile.getWidth() / 2, projectile.getY() - projectile.getHeight() / 2));
							pixHelper.eraseCircle(((EnemyShip) target).getHull(), position, (int) projectile.getPower());
							toBeRemoved.add(projectile);
							
							updatedScore(10, (int) (projectile.getX() - projectile.getWidth() / 2), (int) (projectile.getY() - projectile.getHeight() / 2));
						}
					} else if (target instanceof Weapon) {
						if (((Weapon)target).getEmitter() != projectile.getEmitter()) {
							((Weapon)target).setHps((int) (((Weapon)target).getHps() - projectile.getPower()));
							impacts.add(new Vector2(projectile.getX() - projectile.getWidth() / 2, projectile.getY() - projectile.getHeight() / 2));
							toBeRemoved.add(projectile);
						}
					} else if (target instanceof WeakPoint && !(projectile.getEmitter() instanceof EnemyShip)) {
						((WeakPoint)target).setEnergy((int) (((WeakPoint)target).getEnergy() - projectile.getPower()));
						impacts.add(new Vector2(projectile.getX() - projectile.getWidth() / 2, projectile.getY() - projectile.getHeight() / 2));
						toBeRemoved.add(projectile);
					} else if (target instanceof Ship) {
						toBeRemoved.add(projectile);
					}
				}
			}
		}
	}
	
	public List<Vector2> getImpacts() {
		return impacts;
	}
	
	public void clearImpacts() {
		impacts.clear();
	}
	
	protected void updatedScore(int newScore, int startx, int starty) {}
}
