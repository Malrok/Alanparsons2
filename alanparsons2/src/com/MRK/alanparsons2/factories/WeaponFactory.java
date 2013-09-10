package com.MRK.alanparsons2.factories;

import com.MRK.alanparsons2.helpers.WeaponHelper;
import com.MRK.alanparsons2.models.Weapon;
import com.MRK.alanparsons2.templates.WeaponTemplate;

public class WeaponFactory {
	
	public Weapon createWeapon(WeaponHelper helper, String emitterName, int shipLevel) {
		Weapon weapon = new Weapon();
		WeaponTemplate template = helper.getMatchingTemplate(emitterName, shipLevel);
		
		if (template != null) {
			weapon.setName(template.getName());
			weapon.setTexture(template.getTexture());
			weapon.setSize(template.getWidth(), template.getHeight());
			weapon.setEmitterName(template.getEmitterName());
			weapon.setEmitter(template.getEmitter());
			weapon.setShootFrequency(template.getShootFrequency());
			weapon.setProjectileType(template.getProjectileType());
			weapon.setHps(template.getHps());
			weapon.setTouchPoints(template.getTouchPoints());
			weapon.setDestroyPoints(template.getDestroyPoints());
		}
		
		return weapon;
	}
}
