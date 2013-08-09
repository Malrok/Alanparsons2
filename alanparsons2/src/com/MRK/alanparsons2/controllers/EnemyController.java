package com.MRK.alanparsons2.controllers;

import java.util.List;
import java.util.Map.Entry;

import com.MRK.alanparsons2.factories.WeaponFactory;
import com.MRK.alanparsons2.helpers.PixmapHelper;
import com.MRK.alanparsons2.helpers.WeaponHelper;
import com.MRK.alanparsons2.models.EnemyShip;
import com.MRK.alanparsons2.models.PixmapPosition;
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

	public void setEnemiesWeakPoints(WeaponHelper helper, List<Weapon> weapons, WeaponFactory weaponFactory) {
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
	
	public void update(float aimX, float aimY) {
		for (EnemyShip enemy : enemies) {
			enemy.updateWeapons(aimX, aimY);
			pixHelper.update(enemy.getHull());
		}
	}
	
}
