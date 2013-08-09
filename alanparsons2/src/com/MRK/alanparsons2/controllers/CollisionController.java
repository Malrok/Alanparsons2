package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.helpers.PixmapHelper;
import com.MRK.alanparsons2.models.EnemyShip;
import com.MRK.alanparsons2.models.Projectile;
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
	 * Ajoute une liste de cibles de type {@link Sprite} à la liste des objets gérés 
	 * @param weapon
	 */
	public void addTargets(List<EnemyShip> list) {
		this.targets.addAll(list);
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
//						((EnemyShip) target).screenCoordsToPixmap(position, (int) projectile.getX() + projectile.getWidth() / 2, (int) projectile.getY() + projectile.getHeight() / 2);
						pixHelper.screenCoordsToPixmap(((EnemyShip) target).getHull(), position, (int) projectile.getX() + projectile.getWidth() / 2, (int) projectile.getY() + projectile.getHeight() / 2);
//						if (((EnemyShip) target).collides(position)) {
						if (pixHelper.collides(((EnemyShip) target).getHull(), position)) {
							impacts.add(new Vector2(projectile.getX() - projectile.getWidth() / 2, projectile.getY() - projectile.getHeight() / 2));
//							((EnemyShip) target).eraseCircle(position, (int) projectile.getPower());
							pixHelper.eraseCircle(((EnemyShip) target).getHull(), position, (int) projectile.getPower());
							toBeRemoved.add(projectile);
						}
					} else {
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
}
