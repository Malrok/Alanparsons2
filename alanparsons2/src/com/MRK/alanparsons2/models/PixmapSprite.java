package com.MRK.alanparsons2.models;

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
 * Définit un {@link Sprite} gérant un {@link Pixmap}
 * @author malrok
 *
 */
public class PixmapSprite extends Sprite implements Disposable {

	protected Pixmap pixmap;
	protected Texture texture;

	private final Color color = new Color();
	private int lastModification = 0;
	private PixmapChange[] modifications = new PixmapChange[10];	
	
	/**
	 * Définit le texture depuis le chemin d'une texture en paramètre
	 * @param texture
	 */
	public void setTexture(Pixmap pixmap) {
		this.pixmap = pixmap;

		this.texture = new Texture(new PixmapTextureData(pixmap, pixmap.getFormat(), false, false));
		this.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		for (int i = 0; i < modifications.length; i++)
			modifications[i] = new PixmapChange();

	}
	
	public void dispose() {
		
	}
	
	public Pixmap getPixmap() {
		return pixmap;
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

	public void getPixelColor(Color color, Vector2 position) {
		Color.rgba8888ToColor(color, pixmap.getPixel((int) position.x, (int) position.y));
	}
	
	/**
	 * Vérifie si le pixel dont la position est passée en paramètre est "rempli"
	 * @param position
	 * @return
	 */
	public boolean collides(Vector2 position) {
		getPixelColor(color, position);
		
		return (color.a != 0);
	}

	/**
	 * Projette le point onScreenX / onScreenY dans le système du pixmap, et stocke le résultat dans le Vector2 position
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
	 * Projette le point du système du pixmap vers l'écran, et stocke le résultat dans le Vector2 position
	 * @param position
	 * @param onPixmapX
	 * @param onPixmapY
	 */
	public void unproject(Vector2 position, float onPixmapX, float onPixmapY) {
		position.set(onPixmapX, pixmap.getHeight() - onPixmapY);

		float scaleX = getWidth() / pixmap.getWidth();
		float scaleY = getHeight() / pixmap.getHeight();

		position.x *= scaleX;
		position.y *= scaleY;
		
		position.x += getX();
		position.y += getY();
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
