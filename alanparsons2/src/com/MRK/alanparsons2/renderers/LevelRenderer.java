package com.MRK.alanparsons2.renderers;

import com.MRK.alanparsons2.controllers.ProjectileController;
import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.models.Level;
import com.MRK.alanparsons2.models.RotatingCamera;
import com.MRK.alanparsons2.models.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Renderer du niveau
 */
public class LevelRenderer implements Disposable {

	private Level level;
	
	private SpriteBatch batch;
	private RotatingCamera camera;
	
	private ShipController shipController;
	private ProjectileController projectileController;

	/* debug */
	private ShapeRenderer shapeRenderer;
	
	/**
	 * Constructeur
	 * @param level
	 */
	public LevelRenderer(Level level) {
		this.level = level;
		
		batch = new SpriteBatch();
		camera = new RotatingCamera();
		
		shipController = new ShipController(level.getShip());
		projectileController = new ProjectileController();
		
		projectileController.addWeapon(level.getShip().getWeapon());
		
		projectileController.addTarget(level.getShip());
		projectileController.addTarget(level.getFoe());
		
		shapeRenderer = new ShapeRenderer();
	}
	
	public void update() {
		camera.rotateCameraAround(level.getFoe().getOriginX(), level.getFoe().getOriginY(), shipController.getLastRotateValue());
		shipController.update();
		projectileController.update();
		
		level.getFoe().update();
	}
	
	public void render() {
		camera.update();
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		for (int w=0; w<4; w++) {
			for (int h=0; h<4; h++) {
				batch.draw(level.getStarsTexture(), w * Level.LEVEL_WIDTH / 4, h * Level.LEVEL_HEIGHT / 4, Level.LEVEL_WIDTH / 4, Level.LEVEL_HEIGHT / 4);
			}
		}
		
		batch.draw(level.getSunTexture(), 3 * Level.LEVEL_WIDTH / 7 - (ShipController.deltaX / 5), 3 * Level.LEVEL_HEIGHT / 7 - (ShipController.deltaY / 5) + 8, 8, 8);
		batch.draw(level.getPlanetTexture(), ShipController.deltaX / 3 - level.getPlanetTexture().getWidth() / 24, ShipController.deltaY / 3 - level.getPlanetTexture().getHeight() / 24, Level.LEVEL_WIDTH / 2, Level.LEVEL_HEIGHT / 2);
		
		projectileController.drawProjectiles(batch);
		
		level.getShip().draw(batch);
		level.getFoe().draw(batch);
		
		batch.end();
		
		drawDebug();
	}	
	
	private void drawDebug() {
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(level.getShip().getX(), level.getShip().getY(), Ship.SHIP_WIDTH, Ship.SHIP_HEIGHT);
		shapeRenderer.circle(level.getShip().getWeapon().getPosition().x, level.getShip().getWeapon().getPosition().y, 0.4f);
		shapeRenderer.line(level.getShip().getWeapon().getPosition().x, level.getShip().getWeapon().getPosition().y, level.getShip().getWeapon().getAimAt().x, level.getShip().getWeapon().getAimAt().y);
		shapeRenderer.end();
	}
	
	public void resize(int width, int height) {
		camera.init(width, height);
		
		level.resize(width, height);
		
		shipController.init(new Vector2(level.getFoe().getOriginX(), level.getFoe().getOriginY()));
		shipController.update();
		
		camera.setRadius(Math.abs(level.getFoe().getY() + level.getFoe().getHeight() / 2 - camera.position.y));
	}
	
	public void dispose() {
		batch.dispose();
	}
}
