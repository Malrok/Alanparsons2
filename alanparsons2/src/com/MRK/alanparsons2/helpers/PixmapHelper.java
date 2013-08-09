package com.MRK.alanparsons2.helpers;

import java.util.Map;
import java.util.Map.Entry;

import com.MRK.alanparsons2.models.PixmapPosition;
import com.MRK.alanparsons2.models.PixmapSprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class PixmapHelper {

	private final Color color = new Color();
	
	/**
	 * Recherche sur la texture du vaisseau les points magenta définissant la position d'une arme, et la stocke<BR>
	 * Les positions sont relatives au {@link Pixmap}
	 */
	public void setOnPixmapPosition(PixmapSprite pixSprite, Map<PixmapPosition, ? extends Object> objectsPos) {
		for (int x = 0; x < pixSprite.getPixmapWidth(); x++) {
			for (int y = 0; y < pixSprite.getPixmapHeight(); y++) {
				pixSprite.getPixelColor(color, new Vector2(x, y));
				if (color.r == 1.0f && color.b == 1.0f) { // magenta
					objectsPos.put(new PixmapPosition(x, y), null);
				}
			}
		}
	}
	
	/**
	 * Place les armes du vaisseau sur ce dernier, en positions réelles, à partir des {@link PixmapPosition}
	 */
	public void setOnScreenPosition(PixmapSprite pixSprite, Entry<PixmapPosition, ? extends Sprite> entry) {
		Vector2 position = new Vector2(pixSprite.getX(), pixSprite.getY());
		
		pixSprite.unproject(position, entry.getKey().getX(), entry.getKey().getY());
		
		entry.getValue().setPosition(position.x, position.y);
	}	
}
