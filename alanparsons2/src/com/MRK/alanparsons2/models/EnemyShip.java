package com.MRK.alanparsons2.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class EnemyShip extends Sprite implements Disposable {
	
	private final Color color = new Color();
	
	private String name;
	private int shipLevel = 1;
	private Map<PixmapPosition, Weapon> shipWeapons = new HashMap<PixmapPosition, Weapon>();
	
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
		hull.dispose();
		structure.dispose();
	}

	public void draw(SpriteBatch batch) {
		structure.draw(batch);
		hull.draw(batch);
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
	
	public void setHull(Pixmap pixmap) {
		hull.init(pixmap);
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
	
	/**
	 * Recherche sur la texture du vaisseau les points magenta définissant la position d'une arme, et la stocke<BR>
	 * Les positions sont relatives au {@link Pixmap}
	 */
	public void initWeapons() {
		for (int x = 0; x < hull.getPixmapWidth(); x++) {
			for (int y = 0; y < hull.getPixmapHeight(); y++) {
				hull.getPixelColor(color, new Vector2(x, y));
				if (color.r == 1.0f && color.b == 1.0f) { // magenta
					System.out.println("initWeapons addingweapon x/y=" + x + "/" + y);
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
			Vector2 position = new Vector2(getX(), getY());
			
			entry.setValue(weapon);
			
			hull.unproject(position, entry.getKey().getX(), entry.getKey().getY());
			
			entry.getValue().setPosition(position.x, position.y);
		}
	}
	
	public void upgradeWeapons(WeaponTemplate weaponTemplate) {
		for (Entry<PixmapPosition, Weapon> entry : shipWeapons.entrySet()) {
			entry.getValue().upgrade(weaponTemplate);
		}
	}
	
	public void update() {
		hull.update();
	}
	
	public void screenCoordsToPixmap(Vector2 position, float onScreenX, float onScreenY) {
		hull.project(position, onScreenX, onScreenY);
	}
	
	public void pixmapCoordsToScreen(Vector2 position, float onPixmapX, float onPixmapY) {
		hull.unproject(position, onPixmapX, onPixmapY);
	}
	
	public void eraseCircle(Vector2 position, int radius) {
		hull.eraseCircle(position, radius);
	}
	
	public boolean collides(Vector2 position) {
		return hull.collides(position);
	}
}
