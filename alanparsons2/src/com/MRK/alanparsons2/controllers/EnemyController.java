package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.MRK.alanparsons2.factories.WeakPointFactory;
import com.MRK.alanparsons2.factories.WeaponFactory;
import com.MRK.alanparsons2.helpers.PixmapHelper;
import com.MRK.alanparsons2.helpers.WeakPointHelper;
import com.MRK.alanparsons2.helpers.WeaponHelper;
import com.MRK.alanparsons2.models.EnemyShip;
import com.MRK.alanparsons2.models.PixmapPosition;
import com.MRK.alanparsons2.models.WeakPoint;
import com.MRK.alanparsons2.models.Weapon;
import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.math.Vector2;

public class EnemyController {

	private PixmapHelper pixHelper;
	private List<EnemyShip> enemies;
	private List<Weapon> weapons;
	private List<WeakPoint> weakPoints;
	private List<Vector2> explodingParts = new ArrayList<Vector2>();
	
	public EnemyController(List<EnemyShip> enemies, List<Weapon> weapons, List<WeakPoint> weakPoints, PixmapHelper pixHelper) {
		this.enemies = enemies;
		this.weapons = weapons;
		this.weakPoints = weakPoints;
		this.pixHelper = pixHelper;
	}
	
	public void setEnemiesWeapons(WeaponHelper helper, WeaponFactory weaponFactory) {
		int rank = 0;
		
		for (EnemyShip enemy : enemies) {
			pixHelper.setOnPixmapPosition(enemy.getHull(), enemy.getWeaponsPosition());
			
			for (Entry<PixmapPosition, Weapon> entry : enemy.getWeaponsPosition().entrySet()) {
			
				Weapon weapon = weaponFactory.createWeapon(helper, enemy.getName(), enemy.getLevel());
				weapon.setName(enemy.getName() + rank++);
				weapons.add(weapon);
				
				entry.setValue(weapon);
				
				pixHelper.setOnScreenPosition(enemy.getHull(), entry);
			}
			enemy.enableWeapons();
		}
	}

	public void setEnemiesWeakPoints(WeakPointHelper helper, WeakPointFactory weakPointFactory) {
		int rank = 0;
		
		for (EnemyShip enemy : enemies) {
			pixHelper.setOnPixmapPosition(enemy.getStructure(), enemy.getWeakPointsPosition());
			
			for (Entry<PixmapPosition, WeakPoint> entry : enemy.getWeakPointsPosition().entrySet()) {

				WeakPoint weakPoint = weakPointFactory.createWeakPoint(helper, enemy.getName());
				weakPoint.setName(enemy.getName() + rank ++);
				weakPoints.add(weakPoint);
				
				entry.setValue(weakPoint);
				
				pixHelper.setOnScreenPosition(enemy.getStructure(), entry);
			}
		}
	}
	
	public void updateEnemies(float aimX, float aimY) {
		for (EnemyShip enemy : enemies) {
			if (enemy.getWeakPointsPosition().size() > 0) {
				enemy.updateWeapons(aimX, aimY);
				pixHelper.update(enemy.getHull());
			}
		}
	}
	
	public void updateWeapons(List<Weapon> toBeRemoved, WeaponHelper helper) {
		for (Weapon weapon : weapons) {
			if (weapon.getEmitter() instanceof EnemyShip && weapon.getHps() <= 0) {
				// TODO : improve that bitch
				updatedScore(50, (int) weapon.getX(), (int) weapon.getY());
				shakeCamera();
				toBeRemoved.add(weapon);
				explodingParts.add(new Vector2(weapon.getX(), weapon.getY()));
				
				if (weapon.getEmitter() instanceof EnemyShip) {
					EnemyShip enemy = (EnemyShip)weapon.getEmitter();
					
					if (enemy != null) {
						enemy.removeWeapon(weapon);
						enemy.addLevel(1);
					
						WeaponTemplate template = helper.getMatchingTemplate(enemy.getName(), enemy.getLevel());
						
						if (template != null) enemy.upgradeWeapons(template);
					}
				}
			}
		}
		
		weapons.removeAll(toBeRemoved);
	}
	
	public void updateWeakPoint(List<WeakPoint> toBeRemoved, WeaponHelper helper) {
		for (WeakPoint weakPoint : weakPoints) {
			if (weakPoint.getEnergy() <= 0) {
				// TODO : improve that bitch
				updatedScore(50, (int) weakPoint.getX(), (int) weakPoint.getY());
				shakeCamera();
				toBeRemoved.add(weakPoint);
				explodingParts.add(new Vector2(weakPoint.getX(), weakPoint.getY()));
				
				EnemyShip enemy = (EnemyShip)weakPoint.getHost();
				
				if (enemy != null) {
					enemy.removeWeakPoint(weakPoint);
					enemy.addLevel(1);
				
					WeaponTemplate template = helper.getMatchingTemplate(enemy.getName(), enemy.getLevel());
				
					if (template != null) enemy.upgradeWeapons(template);
				}
			}
		}
		
		weakPoints.removeAll(toBeRemoved);
	}
	
	public List<Vector2> getExplodingParts() {
		List<Vector2> ret = new ArrayList<Vector2>();
		
		for (Vector2 pos : explodingParts)
			ret.add(new Vector2(pos));
		
		explodingParts.clear();
		return ret;
	}
	
	public int getEnemiesNumber() {
		int ret = 0;
		
		for (EnemyShip enemy : enemies)
			ret += enemy.getWeakPointsPosition().size();
		
		return ret;
	}
	
	protected void updatedScore(int newScore, int startx, int starty) {}
	
	protected void shakeCamera() {}
}
