package com.MRK.alanparsons2.models;

/**
 * Classe définissant les changements non encore "comités" à la texture du PixmapSprite
 * @author malrok
 */
public class PixmapChange {

	int x, y;
	int width;

	void set(int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
	}
}

