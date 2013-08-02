package com.MRK.alanparsons2.renderers;

import com.MRK.alanparsons2.Alanparsons2;
import com.MRK.alanparsons2.controllers.ParticleController;
import com.MRK.alanparsons2.controllers.ProjectileController;
import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.factories.ProjectileFactory;
import com.MRK.alanparsons2.models.Level;
import com.MRK.alanparsons2.models.RotatingCamera;
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
	private ParticleController particleController;

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
		projectileController = new ProjectileController(new ProjectileFactory(level.getProjectilesTemplates()), level.getProjectiles());
		particleController = new ParticleController();
		
		shapeRenderer = new ShapeRenderer();
	}
	
	public void update() {
		camera.rotateCameraAround(level.getLevelCenterX(), level.getLevelCenterY(), shipController.getLastRotateValue());
		shipController.update();
		projectileController.update();
		particleController.update(projectileController.getImpacts());
		projectileController.clearImpacts();		
		level.update();
	}
	
	public void render() {
		camera.update();
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		level.draw(batch);		
		particleController.draw(batch);
		
		batch.end();
		
		drawDebug();
	}	
	
	private void drawDebug() {
		if (Alanparsons2.DEBUG) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(level.getShip().getX(), level.getShip().getY(), level.getShip().getWidth(), level.getShip().getWidth());
			shapeRenderer.circle(level.getShip().getWeapon().getPosition().x, level.getShip().getWeapon().getPosition().y, 0.4f);
			shapeRenderer.line(level.getShip().getWeapon().getPosition().x, level.getShip().getWeapon().getPosition().y, level.getShip().getWeapon().getAimAt().x, level.getShip().getWeapon().getAimAt().y);
			shapeRenderer.end();
		}
	}
	
	public void resize(int width, int height) {
		camera.init(level.getWidth(), level.getHeight(), width, height);
		
		level.resize();
		
		shipController.init(new Vector2(level.getLevelCenterX(), level.getLevelCenterY()));
		shipController.update();
		
		camera.setRadius(Math.abs(level.getLevelCenterY() - camera.position.y));
		
		projectileController.addWeapons(level.getWeapons());		
		projectileController.addTarget(level.getShip());
		projectileController.addTargets(level.getEnemies());
	}
	
	public void dispose() {
		batch.dispose();
	}
}
