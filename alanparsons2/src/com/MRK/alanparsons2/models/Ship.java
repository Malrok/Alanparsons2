package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe héritant de {@link Sprite}, définissant le vaisseau du joueur
 * Modèle de type singleton, non instanciable<BR>
 * Appeler {@link #getInstance() getInstance} pour récupérer l'instance de la classe
 */
public class Ship extends Sprite {

	private static Ship instance = new Ship();
	private Weapon weapon;
	
	private Ship() {
		super(new Texture(Gdx.files.internal("data/lapin.png")));
	}
	
	/** 
	 * retourne l'instance unique de la classe
	 * @return {@type Ship}
	 */
	public static Ship getInstance() {
		return instance;
	}
	
	/** 
	 * retourne l'arme de la classe
	 * @return {@type Weapon}
	 */
	public Weapon getWeapon() {
		return weapon;
	}
	
	/** 
	 * Définit l'arme de la classe
	 * @param texture - {@type Texture}
	 */
	public void setWeapon(Texture texture) {
		weapon = new Weapon(texture, new Vector2(0, 1), 3, 1);
		weapon.setEnabled(true);
	}
	
	/** 
	 * mise à jour du vaisseau<BR>
	 * réoriente l'arme<BR>
	 * @param lastRotateValue - float : dernier angle de rotation appliqué au vaisseau
	 */
	public void update(float lastRotateValue) {
		weapon.setAimAt(CircleHelper.getVectorAimingAtCenter(super.getX(), super.getY(), ShipController.getInstance().getRotationCenter().x, ShipController.getInstance().getRotationCenter().y, ShipController.currentAngle));
		weapon.update();
	}
	
	/**
	 * mise à jour de la position du vaisseau et de son arme<BR>
	 */
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		weapon.setPosition(x, y);
	}
}
