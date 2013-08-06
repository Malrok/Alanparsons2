package com.MRK.alanparsons2.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class EnemyShip extends PixmapSprite implements Disposable {
	
	private final Color color = new Color();
	
	private String name;
	private Vector2 position = new Vector2();
	private int shipLevel = 1;
	private Map<PixmapPosition, Weapon> shipWeapons = new HashMap<PixmapPosition, Weapon>();
	private TextureRegion structure;
	
	public void dispose() {
		shipWeapons.clear();
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(structure, getX(), getY(), getWidth(), getHeight());
		super.draw(batch);
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
	
	public void addLevel(int levelInc, WeaponTemplate weaponTemplate) {
		shipLevel += levelInc;
	}
	
	/**
	 * Mise à jour des armes afin qu'elles pointent dans la direction indiquée
	 */
	public void updateWeapons(float aimX, float aimY) {
		super.update();
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
	
	/**
	 * Recherche sur la texture du vaisseau les points magenta définissant la position d'une arme, et la stocke<BR>
	 * Les positions sont relatives au {@link Pixmap}
	 */
	public void initWeapons() {
		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = 0; y < pixmap.getHeight(); y++) {
				super.getPixelColor(color, new Vector2(x, y));
				
				if (color.r == 1.0f && color.b == 1.0f) { // magenta
					shipWeapons.put(new PixmapPosition(x, y), null);
				}
			}
		}
	}
	
	/**
	 * Renvoie la liste de positions d'armes construite en cherchant les points magenta sur la texture
	 * @return Map<PixmapPosition, Weapon>
	 */
	public Map<PixmapPosition, Weapon> getWeaponsPosition() {
		return shipWeapons;
	}
	
	/**
	 * Place les armes du vaisseau sur ce dernier, en positions réelles, à partir des {@link PixmapPosition}
	 */
	public void setWeaponsPositionOnSprite(Entry<PixmapPosition, Weapon> entry, Weapon weapon) {
		if (entry.getValue() == null) {
			entry.setValue(weapon);
			
			unproject(position, entry.getKey().getX(), entry.getKey().getY());
			
			entry.getValue().setPosition(position.x, position.y);
		}
	}
	
	public void upgradeWeapons(WeaponTemplate weaponTemplate) {
		for (Entry<PixmapPosition, Weapon> entry : shipWeapons.entrySet()) {
			entry.getValue().upgrade(weaponTemplate);
		}
	}

	public TextureRegion getStructure() {
		return structure;
	}

	public void setStructure(TextureRegion structure) {
		this.structure = structure;
	}
}
