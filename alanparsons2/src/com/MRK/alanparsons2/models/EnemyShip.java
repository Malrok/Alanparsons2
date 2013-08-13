package com.MRK.alanparsons2.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class EnemyShip extends Sprite implements Disposable {
	
	private String name;
	private int shipLevel = 1;
	private Map<PixmapPosition, Weapon> shipWeapons = new HashMap<PixmapPosition, Weapon>();
	private Map<PixmapPosition, WeakPoint> shipWeakPoints = new HashMap<PixmapPosition, WeakPoint>();
	
	private PixmapSprite hull = new PixmapSprite();
	private PixmapSprite structure = new PixmapSprite();
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		hull.setSize(width, height);
		structure.setSize(width, height);
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		hull.setPosition(x, y);
		structure.setPosition(x, y);
	}
	
	public void dispose() {
		shipWeapons.clear();
		shipWeakPoints.clear();
		hull.dispose();
		structure.dispose();
	}

	public void draw(SpriteBatch batch) {
		structure.draw(batch);
		for (Entry<PixmapPosition, WeakPoint> entry : shipWeakPoints.entrySet()) {
			((WeakPoint)entry.getValue()).draw(batch);
		}
		hull.draw(batch);
		for (Entry<PixmapPosition, Weapon> entry : shipWeapons.entrySet()) {
			((Weapon)entry.getValue()).draw(batch);
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return shipLevel;
	}
	
	public void addLevel(int levelInc) {
		shipLevel += levelInc;
	}
	
	public PixmapSprite getHull() {
		return hull;
	}
	
	public void setHull(Pixmap pixmap) {
		hull.init(pixmap);
	}

	public PixmapSprite getStructure() {
		return structure;
	}
	
	public void setStructure(Pixmap pixmap) {
		structure.init(pixmap);
	}
	
	/**
	 * Mise à jour des armes afin qu'elles pointent dans la direction indiquée
	 */
	public void updateWeapons(float aimX, float aimY) {
		for (Entry<PixmapPosition, Weapon> entry : shipWeapons.entrySet()) {
			entry.getValue().setAimAt(new Vector2(aimX, aimY));
			entry.getValue().update();
		}
	}

	/**
	 * Active les armes du vaisseau
	 */
	public void enableWeapons() {
		for (Entry<PixmapPosition, Weapon> entry : shipWeapons.entrySet()) {
			entry.getValue().setEnabled(true);
		}
	}
	
	public void removeWeapon(Weapon weapon) {
		shipWeapons.remove(weapon);
	}
	
	/**
	 * Renvoie la liste de positions d'armes construite en cherchant les points magenta sur la texture
	 * @return Map<PixmapPosition, Weapon>
	 */
	public Map<PixmapPosition, Weapon> getWeaponsPosition() {
		return shipWeapons;
	}
	
	public void upgradeWeapons(WeaponTemplate weaponTemplate) {
		for (Entry<PixmapPosition, Weapon> entry : shipWeapons.entrySet()) {
			entry.getValue().upgrade(weaponTemplate);
		}
	}
	
	public void removeWeakPoint(WeakPoint weakPoint) {
		shipWeakPoints.remove(weakPoint);
	}
	
	/**
	 * Renvoie la liste de positions des points faibles construite en cherchant les points magenta sur la texture
	 * @return Map<PixmapPosition, Weapon>
	 */
	public Map<PixmapPosition, WeakPoint> getWeakPointsPosition() {
		return shipWeakPoints;
	}
}
