package com.MRK.alanparsons2.factories;

import com.MRK.alanparsons2.models.EnemyShip;
import com.MRK.alanparsons2.models.Ship;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ShipFactory {
	
	public Sprite createShip(String type) {
		Sprite ship = null;
		
		if (type.equals(LevelBuilder.SHIP)) ship = new Ship();
		if (type.equals(LevelBuilder.ENEMY)) ship = new EnemyShip();
		
		return ship;
	}
	
}
