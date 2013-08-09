package com.MRK.alanparsons2.factories;

import com.MRK.alanparsons2.helpers.WeaponHelper;
import com.MRK.alanparsons2.models.Weapon;
import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class WeaponFactory {

//	private List<WeaponTemplate> weaponsTemplates;
//	
//	public WeaponFactory(List<WeaponTemplate> weaponsTemplates) {
//		this.weaponsTemplates = weaponsTemplates;
//	}
	
	public Weapon createWeapon(WeaponHelper helper, Sprite emitter, int shipLevel) {
		Weapon weapon = new Weapon();
		WeaponTemplate template = helper.getMatchingTemplate(emitter, shipLevel);
		
		if (template != null) {
			weapon.setName(template.getName());
			weapon.setTexture(template.getTexture());
			weapon.setSize(template.getWidth(), template.getHeight());
			weapon.setEmitterName(template.getEmitterName());
			weapon.setEmitter(template.getEmitter());
			weapon.setShootFrequency(template.getShootFrequency());
			weapon.setProjectileType(template.getProjectileType());
			weapon.setHps(template.getHps());
		}
		
		return weapon;
	}
}
