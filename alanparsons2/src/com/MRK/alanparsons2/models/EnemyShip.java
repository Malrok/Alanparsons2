package com.MRK.alanparsons2.models;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class EnemyShip extends PixmapSprite implements Disposable {
	
	private List<Weapon> weapons = new ArrayList<Weapon>();
	private List<PixmapPosition> weaponsPositions = new ArrayList<PixmapPosition>();
	
	private final Color color = new Color();
	private final Vector2 position = new Vector2();
	
	public EnemyShip(String texture) {
		super(texture);
	}

	public void dispose() {
		for (Weapon weapon : weapons)
			weapon.dispose();
		
		weapons.clear();
	}
	
	/**
	 * Initialisation des armes
	 * @param texture à appliquer aux tirs
	 */
	public void setWeapons(Texture texture) {
		initWeapons();
		setWeaponsPositionOnSprite(texture);
	}
	
	/**
	 * Mise à jour des armes afin qu'elles pointent dans la direction indiquée
	 */
	public void update(float aimX, float aimY) {
		super.update();
		for (Weapon weapon: weapons) {
			weapon.setAimAt(CircleHelper.getVectorAimingAtCenter(weapon.getPosition().x, weapon.getPosition().y, aimX, aimY, Ship.PROJECTILE_SPEED));
			weapon.update();
		}
	}
	
	public List<Weapon> getWeapons() {
		return weapons;
	}

	/**
	 * Recherche sur la texture du vaisseau les points magenta définissant la position d'une arme, et la stocke<BR>
	 * Les positions sont relatives au {@link Pixmap}
	 */
	private void initWeapons() {
		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = 0; y < pixmap.getHeight(); y++) {
				super.getPixelColor(color, new Vector2(x, y));
				
				if (color.r == 1.0f && color.b == 1.0f) { // magenta
					weaponsPositions.add(new PixmapPosition(x, y));
				}
			}
		}
	}
	
	/**
	 * Place les armes du vaisseau sur ce dernier, en positions réelles, à partir des {@link PixmapPosition}
	 */
	private void setWeaponsPositionOnSprite(Texture texture) {
		for (PixmapPosition pixPos : weaponsPositions) {
			Weapon weapon = new Weapon(this, texture, new Vector2(0, 1), 3, 1);
			weapon.setEnabled(true);
			unproject(position, pixPos.getX(), pixPos.getY());
			weapon.setPosition(position.x, position.y);
			weapons.add(weapon);
		}
	}
}
