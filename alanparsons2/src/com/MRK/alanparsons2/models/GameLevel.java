package com.MRK.alanparsons2.models;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.controllers.BackgroundController;
import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.templates.CameraTemplate;
import com.MRK.alanparsons2.templates.ProjectileTemplate;
import com.MRK.alanparsons2.templates.TouchInputTemplate;
import com.MRK.alanparsons2.templates.WeakPointTemplate;
import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class GameLevel implements Disposable {

	private String name;	
	private float width = 100f;
	private float height = 100f;
	
	private Ship ship;
	private List<EnemyShip> enemies = new ArrayList<EnemyShip>();
	private List<WeaponTemplate> weaponsTemplates = new ArrayList<WeaponTemplate>();
	private List<WeakPointTemplate> weakPointsTemplates = new ArrayList<WeakPointTemplate>();
	private List<Weapon> weapons = new ArrayList<Weapon>();
	private List<WeakPoint> weakPoints = new ArrayList<WeakPoint>();
	private List<ProjectileTemplate> projectilesTemplates = new ArrayList<ProjectileTemplate>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private BackgroundController background;
	private CameraTemplate cameraTemplate;
	private TouchInputTemplate touchTemplate;

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

	public List<WeaponTemplate> getWeaponTemplates() {
		return weaponsTemplates;
	}

	public void setWeaponTemplates(List<WeaponTemplate> weaponsTemplates) {
		this.weaponsTemplates = weaponsTemplates;
	}
	
	public void addWeaponTemplate(WeaponTemplate weaponTemplate) {
		this.weaponsTemplates.add(weaponTemplate);
	}

	public List<WeakPointTemplate> getWeakPointsTemplates() {
		return weakPointsTemplates;
	}

	public void setWeakPointsTemplates(List<WeakPointTemplate> weakPointsTemplates) {
		this.weakPointsTemplates = weakPointsTemplates;
	}
	
	public void addWeakPointTemplate(WeakPointTemplate weakPointTemplate) {
		this.weakPointsTemplates.add(weakPointTemplate);
	}
	
	public List<Weapon> getWeapons() {
		return weapons;
	}
	
	public void addWeapon(Weapon weapon) {
		this.weapons.add(weapon);
	}

	public List<WeakPoint> getWeakPoints() {
		return weakPoints;
	}
	
	public void addWeakPoint(WeakPoint weakPoint) {
		this.weakPoints.add(weakPoint);
	}
	
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

	public BackgroundController getBackground() {
		return background;
	}

	public void setBackground(BackgroundController background) {
		this.background = background;
	}

	public float getLevelCenterX() {
		float minx = 999999999, maxx = 0;
		
		for (EnemyShip enemy : enemies) {
			minx = Math.min(minx, enemy.getX() + enemy.getWidth() / 2);
			maxx = Math.max(maxx, enemy.getX() + enemy.getWidth() / 2);
		}
		
		return minx + ((maxx - minx) / 2);
	}
	
	public float getLevelCenterY() {
		float miny = 999999999, maxy = 0;
		
		for (EnemyShip enemy : enemies) {
			miny = Math.min(miny, enemy.getY() + enemy.getHeight() / 2);
			maxy = Math.max(maxy, enemy.getY() + enemy.getHeight() / 2);
		}
		
		return miny + ((maxy - miny) / 2);
	}

	public CameraTemplate getCameraTemplate() {
		return cameraTemplate;
	}

	public void setCameraTemplate(CameraTemplate cameraTemplate) {
		this.cameraTemplate = cameraTemplate;
	}

	public TouchInputTemplate getTouchTemplate() {
		return touchTemplate;
	}

	public void setTouchTemplate(TouchInputTemplate touchTemplate) {
		this.touchTemplate = touchTemplate;
	}
	
	public void draw(SpriteBatch batch) {
		batch.setBlendFunction(GL11.GL_ACTIVE_TEXTURE, GL11.GL_ONE);
		background.draw(batch, ShipController.deltaX, ShipController.deltaY);
		
		ship.draw(batch);
		
		for (EnemyShip enemy : enemies)
			enemy.draw(batch);
		
		batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		for (Projectile projectile : projectiles)
			projectile.draw(batch);
//		batch.disableBlending();
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
