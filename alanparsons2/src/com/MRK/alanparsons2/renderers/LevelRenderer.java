package com.MRK.alanparsons2.renderers;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.Alanparsons2;
import com.MRK.alanparsons2.controllers.CollisionController;
import com.MRK.alanparsons2.controllers.EnemyController;
import com.MRK.alanparsons2.controllers.ParticleController;
import com.MRK.alanparsons2.controllers.ProjectileController;
import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.factories.ProjectileFactory;
import com.MRK.alanparsons2.factories.WeakPointFactory;
import com.MRK.alanparsons2.factories.WeaponFactory;
import com.MRK.alanparsons2.helpers.PixmapHelper;
import com.MRK.alanparsons2.helpers.WeakPointHelper;
import com.MRK.alanparsons2.helpers.WeaponHelper;
import com.MRK.alanparsons2.models.Level;
import com.MRK.alanparsons2.models.RotatingCamera;
import com.MRK.alanparsons2.models.WeakPoint;
import com.MRK.alanparsons2.models.Weapon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Disposable;

/**
 * Renderer du niveau
 */
public class LevelRenderer implements Disposable {

	private Level level;
	private SpriteBatch batch;
	private RotatingCamera camera;
	private WeaponFactory weaponFactory;
	private WeakPointFactory weakPointFactory;
	
	private ShipController shipController;
	private EnemyController enemyController;
	private ProjectileController projectileController;
	private CollisionController collisionController;
	private ParticleController particleController;
	
	private PixmapHelper pixHelper = new PixmapHelper();
	private WeaponHelper weaponHelper;
	private WeakPointHelper weakPointsHelper;
	
	private List<Weapon> removedWeapons = new ArrayList<Weapon>();
	private List<WeakPoint> removedWeakPoints = new ArrayList<WeakPoint>();
	
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
		
		weaponHelper = new WeaponHelper(level.getWeaponTemplates());
		weakPointsHelper = new WeakPointHelper(level.getWeakPointsTemplates());
		weaponFactory = new WeaponFactory();
		weakPointFactory = new WeakPointFactory();
		
		shipController = new ShipController(level.getShip(), level.getTouchTemplate());
		enemyController = new EnemyController(level.getEnemies(), level.getWeapons(), level.getWeakPoints(), pixHelper);
		projectileController = new ProjectileController(new ProjectileFactory(level.getProjectilesTemplates()), level.getProjectiles());
		collisionController = new CollisionController(level.getProjectiles(), pixHelper);
		particleController = new ParticleController();
		
		shapeRenderer = new ShapeRenderer();
	}
	
	public void update() {
		removedWeapons.clear();
		removedWeakPoints.clear();
		
		camera.rotateCameraAround(shipController.getLastRotateValue());
		
		shipController.update();
		enemyController.updateEnemies(level.getShip().getX() + level.getShip().getWidth() / 2, level.getShip().getY() + level.getShip().getHeight() / 2);
		projectileController.update(level.getWeapons());
		collisionController.computeCollisions(projectileController.getToBeRemovedList());
		projectileController.refreshProjectilesList();
		particleController.update(ParticleController.COLLISION, collisionController.getImpacts());
		enemyController.updateWeapons(removedWeapons, weaponHelper);
		enemyController.updateWeakPoint(removedWeakPoints, weaponHelper);
		collisionController.removeWeaponsTargets(removedWeapons);
		collisionController.removeWeakPointsTargets(removedWeakPoints);
		particleController.update(ParticleController.EXPLOSION, enemyController.getExplodingParts());
		
		collisionController.clearImpacts();
	}
	
	public void render() {
		camera.update();
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
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
			shapeRenderer.circle(level.getShip().getWeapon().getX(), level.getShip().getWeapon().getY(), 0.4f);
			shapeRenderer.line(level.getShip().getWeapon().getX(), level.getShip().getWeapon().getY(), level.getShip().getWeapon().getAimAt().x, level.getShip().getWeapon().getAimAt().y);
			shapeRenderer.end();
		}
	}
	
	public void resize(int width, int height) {
		camera.initViewport(width, height, level.getCameraTemplate().getCameraWidth());
		
		level.resize();
	}
	
	public void init() {
		camera.setRadius(Math.abs(level.getCameraTemplate().getCameraRadius()));
		camera.setRotateCenter(level.getLevelCenterX(), level.getLevelCenterY());
		camera.initPosition(level.getWidth());
		
		shipController.init(level.getLevelCenterX(), level.getLevelCenterY(), camera.getRadius() + camera.getViewportHeight() / 2 - level.getShip().getHeight() / 2 - level.getShip().getyFromScreen());
		shipController.setWeapon(weaponHelper, level.getWeapons(), weaponFactory);
		shipController.update();
		
		enemyController.setEnemiesWeapons(weaponHelper, weaponFactory);
		enemyController.setEnemiesWeakPoints(weakPointsHelper, weakPointFactory);

		collisionController.addTarget(level.getShip());
		collisionController.addEnemyTargets(level.getEnemies());
		collisionController.addWeaponTargets(level.getWeapons());
		collisionController.addWeakPointsTargets(level.getWeakPoints());
	}
	
	public void dispose() {
		batch.dispose();
	}
}
