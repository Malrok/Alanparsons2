package com.MRK.alanparsons2.models;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Définit un {@link Sprite} gérant un {@link Pixmap} afin de pouvoir le détruire au fur et à mesure
 * @author malrok
 *
 */
public class PixmapSprite extends Sprite implements Disposable {

	private Pixmap pixmap;
	private Texture texture;
	private List<Weapon> weapons = new ArrayList<Weapon>();
	
	/**
	 * Classe interne définissant les changements non encore "comités" à la texture du PixmapSprite
	 * @author malrok
	 *
	 */
	private class PixmapChange {

		int x, y;
		int width;

		void set(int x, int y, int width) {
			this.x = x;
			this.y = y;
			this.width = width;
		}
	}

	private final Color color = new Color();
	private int lastModification = 0;
	private PixmapChange[] modifications = new PixmapChange[10];	
	
	/**
	 * Constructeur<BR>
	 * Prend le chemin d'une texture en paramètre
	 * @param texture
	 */
	public PixmapSprite(String texture) {
		pixmap = new Pixmap(Gdx.files.internal(texture));

		this.texture = new Texture(new PixmapTextureData(pixmap, pixmap.getFormat(), false, false));
		this.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		for (int i = 0; i < modifications.length; i++)
			modifications[i] = new PixmapChange();

	}
	
	public void dispose() {
		for (Weapon weapon : weapons)
			weapon.dispose();
		
		weapons.clear();
	}
	
	public Pixmap getPixmap() {
		return pixmap;
	}
	
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}
	
	public List<Weapon> getWeapons() {
		return weapons;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}
	
	/**
	 * Efface un cercle de la texture dont le centre est position, de rayon radius
	 * @param position
	 * @param radius
	 */
	public void eraseCircle(Vector2 position, int radius) {
		if (lastModification == modifications.length)
			return;

		float scaleX = pixmap.getWidth() / getWidth();

		int newRadius = Math.round(radius * scaleX);

		if (position.x + newRadius < 0 || position.y + newRadius < 0)
			return;

		if (position.x - newRadius > pixmap.getWidth() || position.y - newRadius > pixmap.getHeight())
			return;

		Blending blending = Pixmap.getBlending();
		pixmap.setColor(0f, 0f, 0f, 0f);
		Pixmap.setBlending(Blending.None);

		int newX = Math.round(position.x);
		int newY = Math.round(position.y);

		pixmap.fillCircle(newX, newY, newRadius);
		Pixmap.setBlending(blending);

		modifications[lastModification++].set(newX, newY, newRadius * 2);
	}

	/**
	 * Vérifie si le pixel dont la position est passée en paramètre est "rempli"
	 * @param position
	 * @return
	 */
	public boolean collides(Vector2 position) {
		Color.rgba8888ToColor(color, pixmap.getPixel((int) position.x, (int) position.y));
		
		return (color.a != 0);
	}

	/**
	 * Projette le point onScreenX / onScreenY dans le système du pixmap, et srocke le résultat dans le Vector2 position
	 * @param position
	 * @param onScreenX
	 * @param onScreenY
	 */
	public void project(Vector2 position, float onScreenX, float onScreenY) {
		position.set(onScreenX, onScreenY);

		position.x -= getX();
		position.y = getHeight() - (position.y - getY());

		float scaleX = pixmap.getWidth() / getWidth();
		float scaleY = pixmap.getHeight() / getHeight();

		position.x *= scaleX;
		position.y *= scaleY;

//		System.out.println("x = (" + onScreenX + "-" + getX() + ") * (" + pixmap.getWidth() + "/" + getWidth() + ")");
//		System.out.println("y = (" + getHeight() + "-(" + onScreenY + "-" + getY() + ") * (" + pixmap.getHeight() + "/" + getHeight() + ")");
	}
	
	/**
	 * "Commit" des modifications effectuées sur le pixmap
	 */
	public void update() {

		if (lastModification == 0)
			return;

		Gdx.gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getTextureObjectHandle());

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		for (int i = 0; i < lastModification; i++) {

			PixmapChange pixmapChange = modifications[i];

			Pixmap renderPixmap = new Pixmap(pixmapChange.width, pixmapChange.width, Format.RGBA8888);

			int dstWidth = renderPixmap.getWidth();
			int dstHeight = renderPixmap.getHeight();

			Pixmap.setBlending(Blending.None);

			int x = Math.round(pixmapChange.x) - dstWidth / 2;
			int y = Math.round(pixmapChange.y) - dstHeight / 2;

			if (x + dstWidth > width)
				x = width - dstWidth;
			else if (x < 0)
				x = 0;

			if (y + dstHeight > height)
				y = height - dstHeight;
			else if (y < 0) {
				y = 0;
			}

			renderPixmap.drawPixmap(pixmap, 0, 0, x, y, dstWidth, dstHeight);
			
			Gdx.gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, x, y, dstWidth, dstHeight, //
					renderPixmap.getGLFormat(), renderPixmap.getGLType(), renderPixmap.getPixels());

		}

		lastModification = 0;
	}

}
