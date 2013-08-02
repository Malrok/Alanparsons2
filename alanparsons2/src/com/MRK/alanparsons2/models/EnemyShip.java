package com.MRK.alanparsons2.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class EnemyShip extends PixmapSprite implements Disposable {
	
	private final Color color = new Color();
	
	private String name;
//	private Vector2 position = new Vector2();
	
//	private List<Weapon> weapons = new ArrayList<Weapon>();
//	private List<PixmapPosition> weaponsPositions = new ArrayList<PixmapPosition>();
	
	private Map<PixmapPosition, Weapon> shipWeapons = new HashMap<PixmapPosition, Weapon>();
	
	
//	public EnemyShip() {
//		super();
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Vector2 getPosition() {
//		return position;
//	}
	
//	public void setPosition(Vector2 position) {
//		if (getWidth() != 0 && getHeight() != 0) {
//			System.out.println("width = " + getWidth() + " height = " + getHeight());
//			super.setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
//		} else {
//			super.setPosition(position.x, position.y);
//		}
////		this.position = position;
//	}
	
//	/**
//	 * Définit la texture à appliquer
//	 */
//	public void setTexture(String texture) {
//		super.setTexture(texture);
//	}
	
	public void dispose() {
//		for (Entry<PixmapPosition, Weapon> entry : shipWeapons.entrySet()) {
//			entry.getValue().dispose();
//		}
		
		shipWeapons.clear();
	}
	
	/**
	 * Initialisation des armes
	 * @param texture à appliquer aux tirs
	 */
	public void setWeapons(Map<String, Weapon> weapons) {
		initWeapons();
		setWeaponsPositionOnSprite(weapons);
	}
	
	/**
	 * Mise à jour des armes afin qu'elles pointent dans la direction indiquée
	 */
	public void update(float aimX, float aimY) {
		super.update();
//		for (Weapon weapon: shipWeapons) {
		for (Entry<PixmapPosition, Weapon> entry : shipWeapons.entrySet()) {
//			entry.getValue().setAimAt(CircleHelper.getVectorAimingAtCenter(entry.getValue().getPosition().x, entry.getValue().getPosition().y, aimX, aimY, Ship.PROJECTILE_SPEED));
			entry.getValue().setAimAt(new Vector2(aimX, aimY));
//			entry.getValue().setEnabled(true);
//			entry.getValue().update();
		}
	}
	
//	public List<Weapon> getWeapons() {
//		return weapons;
//	}

	/**
	 * Recherche sur la texture du vaisseau les points magenta définissant la position d'une arme, et la stocke<BR>
	 * Les positions sont relatives au {@link Pixmap}
	 */
	private void initWeapons() {
		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = 0; y < pixmap.getHeight(); y++) {
				super.getPixelColor(color, new Vector2(x, y));
				
				if (color.r == 1.0f && color.b == 1.0f) { // magenta
//					weaponsPositions.add(new PixmapPosition(x, y));
					shipWeapons.put(new PixmapPosition(x, y), null);
				}
			}
		}
	}
	
	/**
	 * Place les armes du vaisseau sur ce dernier, en positions réelles, à partir des {@link PixmapPosition}
	 */
	private void setWeaponsPositionOnSprite(Map<String, Weapon> weapons) {
//		for (PixmapPosition pixPos : weaponsPositions) {
//			Weapon weapon = new Weapon(this, texture, new Vector2(0, 1), 3, 1);
//			weapon.setEnabled(true);
//			unproject(position, pixPos.getX(), pixPos.getY());
//			weapon.setPosition(position.x, position.y);
//			weapons.add(weapon);
//		}
		for (Entry<String, Weapon> weaponEntry : weapons.entrySet()) {
			if (weaponEntry.getValue().getEmitter().equals(this)) {
				for (Entry<PixmapPosition, Weapon> entry : shipWeapons.entrySet()) {		
					if (weaponEntry.getValue() == null)
						weaponEntry.setValue(entry.getValue());
				}
			}
		}
	}
}
