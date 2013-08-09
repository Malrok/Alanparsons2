package com.MRK.alanparsons2.helpers;

import java.util.List;

import com.MRK.alanparsons2.templates.WeaponTemplate;

public class WeaponHelper {

	private List<WeaponTemplate> weaponsTemplates;
	
	public WeaponHelper(List<WeaponTemplate> weaponsTemplates) {
		this.weaponsTemplates = weaponsTemplates;
	}
	
	public WeaponTemplate getMatchingTemplate(String emitterName, int shipLevel) {
		
		for (WeaponTemplate template : weaponsTemplates) {
//			System.out.println("getMatchingTemplate " + emitterName + " " + shipLevel + " template " + template.getEmitterName() + " " + template.getShipLevel());
			if (template.getEmitterName().equals(emitterName) && template.getShipLevel() == shipLevel)
				return template;
		}
		
		return null;
	}
	
}
