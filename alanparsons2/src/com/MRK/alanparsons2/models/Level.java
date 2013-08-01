package com.MRK.alanparsons2.models;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.templates.ProjectileTemplate;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Level implements Disposable {

	private String name;	
	private float width = 100f;
	private float height = 100f;
	
	private Ship ship;
	private List<EnemyShip> enemies = new ArrayList<EnemyShip>();
	private List<Weapon> weapons = new ArrayList<Weapon>();
	private List<ProjectileTemplate> projectilesTemplates = new ArrayList<ProjectileTemplate>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private Background background;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public List<EnemyShip> getEnemies() {
		return enemies;
	}
	
	public void addEnemy(EnemyShip enemy) {
		this.enemies.add(enemy);
	}
	
	public void addEnemies(List<EnemyShip> enemies) {
		this.enemies.addAll(enemies);
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}
	
	public void addWeapon(Weapon weapon) {
		this.weapons.add(weapon);
	}
	
//	public void addWeapons(List<Weapon> weapons) {
//		this.weapons.addAll(weapons);
//	}
	
	public List<ProjectileTemplate> getProjectilesTemplates() {
		return projectilesTemplates;
	}

	public void setProjectilesTemplates(List<ProjectileTemplate> projectilesTemplates) {
		this.projectilesTemplates = projectilesTemplates;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(List<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	public Background getBackground() {
		return background;
	}

	public void setBackground(Background background) {
		this.background = background;
	}

	public float getLevelCenterX() {
		float minx = 999999999, maxx = 0;
		
		for (EnemyShip enemy : enemies) {
			minx = Math.min(minx, enemy.getX());
			maxx = Math.max(maxx, enemy.getX());
		}
		
		return minx + ((maxx - minx) / 2);
	}
	
	public float getLevelCenterY() {
		float miny = 999999999, maxy = 0;
		
		for (EnemyShip enemy : enemies) {
			miny = Math.min(miny, enemy.getY());
			maxy = Math.max(maxy, enemy.getY());
		}
		
		return miny + ((maxy - miny) / 2);
	}
	
	public void update() {
		for (EnemyShip enemy : enemies)
			enemy.update();
	}
	
	public void draw(SpriteBatch batch) {
		background.draw(batch, ShipController.deltaX, ShipController.deltaY);
		
		ship.draw(batch);
		
		for (EnemyShip enemy : enemies)
			enemy.draw(batch);
		
//		System.out.println("Drawing projectiles " + projectiles.size());
		for (Projectile projectile : projectiles)
			projectile.draw(batch);
	}
	
	public void dispose() {
		if (ship != null) ship.dispose();
		if (enemies != null)
			for (EnemyShip enemy : enemies)
				enemy.dispose();
		if (projectiles != null)
			for (Projectile projectile : projectiles)
				projectile.dispose();
	}
	
	public void resize() {
		if (background != null)
			background.init(this.width, this.height);
	}
}
