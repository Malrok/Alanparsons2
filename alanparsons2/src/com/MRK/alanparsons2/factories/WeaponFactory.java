package com.MRK.alanparsons2.factories;

import java.util.List;

import com.MRK.alanparsons2.models.Weapon;
import com.MRK.alanparsons2.templates.WeaponTemplate;

public class WeaponFactory {

	private List<WeaponTemplate> weaponsTemplates;
	
	public WeaponFactory(List<WeaponTemplate> weaponsTemplates) {
		this.weaponsTemplates = weaponsTemplates;
	}
	
	public Weapon createWeapon(String emitterName, int shipLevel) {
		Weapon weapon = new Weapon();
		WeaponTemplate template = getMatchingTemplate(emitterName, shipLevel);
		
		if (template != null) {
			weapon.setName(template.getName());
			weapon.setTexture(template.getTexture());
			weapon.setSize(template.getWidth(), template.getHeight());
			weapon.setEmitterName(template.getEmitterName());
			weapon.setEmitter(template.getEmitter());
			weapon.setShootFrequency(template.getShootFrequency());
			weapon.setProjectileType(template.getProjectileType());
		}
		
		return weapon;
	}
	
	private WeaponTemplate getMatchingTemplate(String emitterName, int shipLevel) {
		for (WeaponTemplate template : weaponsTemplates) {
			if (template.getEmitterName().equalsIgnoreCase(emitterName) && template.getShipLevel() == shipLevel)
				return template;
		}
		
		return null;
	}
}
