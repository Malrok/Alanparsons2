package com.MRK.alanparsons2.factories;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.helpers.CircleHelper;
import com.MRK.alanparsons2.models.Projectile;
import com.MRK.alanparsons2.models.Weapon;
import com.MRK.alanparsons2.templates.ProjectileTemplate;

public class ProjectileFactory {

	private List<ProjectileTemplate> projectilesTemplates = new ArrayList<ProjectileTemplate>();
	
	public ProjectileFactory(List<ProjectileTemplate> projectilesTemplates) {
		this.projectilesTemplates = projectilesTemplates;
	}
	
	public Projectile createProjectile(Weapon weapon) {
		ProjectileTemplate template = getTemplateFromType(weapon.getProjectileType());
		
		if (template == null) {
			System.out.println("ProjectileFactory.createProjectile template null " + weapon.getProjectileType());
			return null;
		}
		
		Projectile projectile = new Projectile(
				weapon.getEmitter(), template.getTexture(), 
				CircleHelper.getVectorAimingAtCenter(
						weapon.getX() - template.getWidth() / 2, weapon.getY() - template.getHeight() / 2, 
						weapon.getAimAt().x, weapon.getAimAt().y, template.getSpeed()),
				template.getPower());
		projectile.setPosition(weapon.getX(), weapon.getY());		
		projectile.setSize(template.getWidth(), template.getHeight());
		
		return projectile;
	}
	
	private ProjectileTemplate getTemplateFromType(int type) {
		for (ProjectileTemplate template : projectilesTemplates)
			if (template.getType() == type)
				return template;
		
		return null;
	}
}
