package com.MRK.alanparsons2.helpers;

import java.util.List;

import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class WeaponHelper {

	private List<WeaponTemplate> weaponsTemplates;
	
	public WeaponHelper(List<WeaponTemplate> weaponsTemplates) {
		this.weaponsTemplates = weaponsTemplates;
	}
	
	public WeaponTemplate getMatchingTemplate(Sprite emitter, int shipLevel) {
		for (WeaponTemplate template : weaponsTemplates) {
			if (template.getEmitter().equals(emitter) && template.getShipLevel() == shipLevel)
				return template;
		}
		
		return null;
	}
	
}
