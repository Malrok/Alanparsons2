package com.MRK.alanparsons2.controllers;

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

public class EnemyController {

	private PixmapHelper pixHelper;
	private List<EnemyShip> enemies;
	
	public EnemyController(List<EnemyShip> enemies, PixmapHelper pixHelper) {
		this.enemies = enemies;
		this.pixHelper = pixHelper;
	}
	
	public void setEnemiesWeapons(WeaponHelper helper, List<Weapon> weapons, WeaponFactory weaponFactory) {
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

	public void setEnemiesWeakPoints(WeakPointHelper helper, List<WeakPoint> weakPoints, WeakPointFactory weakPointFactory) {
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
			enemy.enableWeapons();
		}
	}
	
	public void update(float aimX, float aimY) {
		for (EnemyShip enemy : enemies) {
			enemy.updateWeapons(aimX, aimY);
			pixHelper.update(enemy.getHull());
		}
	}
	
}
