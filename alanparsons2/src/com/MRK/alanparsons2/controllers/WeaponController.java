package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.helpers.WeaponHelper;
import com.MRK.alanparsons2.models.EnemyShip;
import com.MRK.alanparsons2.models.Weapon;
import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.math.Vector2;

public class WeaponController {

	private List<Weapon> weapons;
	private List<Vector2> explodingWeapons = new ArrayList<Vector2>();
	
	public WeaponController(List<Weapon> weapons) {
		this.weapons = weapons;
	}
	
	public void update(List<Weapon> toBeRemoved, WeaponHelper helper) {
//		List<Weapon> toBeRemoved = new ArrayList<Weapon>();
		
		for (Weapon weapon : weapons) {
			if (weapon.getHps() < 0) {
				toBeRemoved.add(weapon);
				explodingWeapons.add(new Vector2(weapon.getX(), weapon.getY()));
				
				if (weapon.getEmitter() instanceof EnemyShip) {
					EnemyShip enemy = (EnemyShip)weapon.getEmitter();
					
					enemy.removeWeapon(weapon);
					enemy.addLevel(1);
					
					WeaponTemplate template = helper.getMatchingTemplate(enemy.getName(), enemy.getLevel());
					
					if (template != null) enemy.upgradeWeapons(template);
				}
			}
		}
		
		weapons.removeAll(toBeRemoved);
	}
	
	public List<Vector2> getExplodingWeapons() {
		List<Vector2> ret = new ArrayList<Vector2>();
		
		for (Vector2 pos : explodingWeapons)
			ret.add(new Vector2(pos));
		
		explodingWeapons.clear();
		return ret;
	}
}
