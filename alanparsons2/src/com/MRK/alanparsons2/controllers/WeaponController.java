package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.helpers.WeaponHelper;
import com.MRK.alanparsons2.models.EnemyShip;
import com.MRK.alanparsons2.models.Weapon;

public class WeaponController {

//	private List<WeaponTemplate> weaponsTemplates;
	private List<Weapon> weapons;
	
	public WeaponController(List<Weapon> weapons) {
//		this.weaponsTemplates = weaponsTemplates;
		this.weapons = weapons;
	}
	
	public void update(WeaponHelper helper) {
		List<Weapon> toBeRemoved = new ArrayList<Weapon>();
		
		for (Weapon weapon : weapons) {
			if (weapon.getHps() < 0) {
				toBeRemoved.add(weapon);
				
				if (weapon.getEmitter() instanceof EnemyShip) {
					EnemyShip enemy = (EnemyShip)weapon.getEmitter();
					
					enemy.removeWeapon(weapon);
					enemy.addLevel(1);
					enemy.upgradeWeapons(helper.getMatchingTemplate(enemy, enemy.getLevel()));
				}
			}
		}
		
		weapons.removeAll(toBeRemoved);
	}
	
	
}
