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

/**
 * Renderer du niveau
 */
public class LevelRenderer {

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
		
		shapeRenderer = new ShapeRenderer();
	}
	
	public void update() {
		camera.rotateCameraAround(level.getFoe().getOriginX(), level.getFoe().getOriginY(), shipController.getLastRotateValue());
		shipController.update();
		projectileController.update();
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
		
		batch.draw(level.getPlanetTexture(), ShipController.deltaX / 3, ShipController.deltaY / 3, Level.LEVEL_WIDTH / 2, Level.LEVEL_HEIGHT / 2);
		
		level.getShip().draw(batch);
		level.getFoe().draw(batch);
		
		projectileController.drawProjectiles(batch);
		
		batch.end();
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		  
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(level.getShip().getX(), level.getShip().getY(), Ship.SHIP_WIDTH, Ship.SHIP_HEIGHT);
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
