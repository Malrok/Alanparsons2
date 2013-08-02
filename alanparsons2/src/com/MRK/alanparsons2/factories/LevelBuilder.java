package com.MRK.alanparsons2.factories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.MRK.alanparsons2.models.Background;
import com.MRK.alanparsons2.models.BackgroundElement;
import com.MRK.alanparsons2.models.EnemyShip;
import com.MRK.alanparsons2.models.Level;
import com.MRK.alanparsons2.models.Ship;
import com.MRK.alanparsons2.models.Weapon;
import com.MRK.alanparsons2.resources.LevelFileHandler;
import com.MRK.alanparsons2.resources.Resource;
import com.MRK.alanparsons2.resources.ResourceValue;
import com.MRK.alanparsons2.templates.ProjectileTemplate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class LevelBuilder implements Disposable {

	/* répertoires */
	public static final String LEVEL_DIR = "levels/";
	public static final String ATLAS_DIR = "atlas/";
	public static final String PARTICLES_DIR = "particles/";
	
	/* extensions */
	public static final String LEVEL_EXT = ".lvl";
	public static final String ATLAS_EXT = ".pack";
	public static final String TEXTURES_EXT = ".png";
	
	/* entités */
	public static final String LEVEL = "level";
	public static final String CAMERA = "camera";
	public static final String SHIP = "ship";
	public static final String ENEMY = "enemy";
	public static final String WEAPON = "weapon";
	public static final String PROJECTILE = "projectile";
	public static final String STATIC_BACKGROUND = "staticbackground";
	public static final String MOVING_BACKGROUND = "movingbackground";
	
	/* propriétés */
	private static final String X = "x";
	private static final String Y = "y";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String TEXTURE = "texture";
	private static final String POWER = "power";
	private static final String SHOTS_PER_SECOND = "sps";
	private static final String SPEED = "speed";
	private static final String ZOOM_MIN = "zoom_min";
	private static final String ZOOM_MAX = "zoom_max";
	private static final String REPEAT_WIDTH = "repeatwidth";
	private static final String REPEAT_HEIGHT = "repeatheight";
	private static final String SHIFTX = "shiftx";
	private static final String SHIFTY = "shifty";
	private static final String NAME = "name";
	private static final String WEAPON_HOST = "host";
	private static final String PROJECTILE_TYPE = "projectiletype";
	private static final String Z_RANK = "zrank";
	
	private FileHandle handle;
	private LevelFileHandler levelHandler = new LevelFileHandler();
	
	private TextureAtlas atlas;
	private PixmapTextureAtlas pixmapAtlas;
	private ShipFactory shipFactory = new ShipFactory();
	
	private Level level;
	private Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	private Map<String, Weapon> weapons = new HashMap<String, Weapon>();
	private float cameraWidth, cameraHeight, cameraZoomMin, cameraZoomMax;
	private Background background = new Background();
	private List<ProjectileTemplate> projectiles = new ArrayList<ProjectileTemplate>();
	
	@Override
	public void dispose() {
		if (level != null)
			level.dispose();
	}
	
	public void setLevel(String levelFile) throws IOException {
		atlas = new TextureAtlas(Gdx.files.internal(ATLAS_DIR + levelFile + ATLAS_EXT));
		pixmapAtlas = new PixmapTextureAtlas(Gdx.files.internal(ATLAS_DIR + levelFile + TEXTURES_EXT), Gdx.files.internal(ATLAS_DIR + levelFile + ATLAS_EXT));
		handle = Gdx.files.internal(LEVEL_DIR + levelFile + LEVEL_EXT);
	}
	
	public void load() throws IOException {
		levelHandler.load(handle);
	}
	
	public void parse() {
		for (Map.Entry<String, Resource> resource : levelHandler.resources.entries()) {
			constructObject(resource.getKey(), resource.getValue().getValues());
		}
	}
	
	public void buildLevel() {
		level.setBackground(background);
		
		for (Entry<String, Weapon> weapon : weapons.entrySet()) {
			weapon.getValue().setEmitter(getSpriteByName(weapon.getValue().getEmitterName()));
			level.addWeapon(weapon.getValue());
		}
		
		for (Entry<String, Sprite> sprite : sprites.entrySet()) {
			if (sprite.getValue() instanceof Ship) {
				level.setShip((Ship) sprite.getValue());
				level.getShip().setWeapon(weapons);
			} else if (sprite.getValue() instanceof EnemyShip) {
				level.addEnemy((EnemyShip) sprite.getValue());
			}
		}
		
		for (EnemyShip enemy : level.getEnemies()) {
			enemy.setWeapons(weapons);
		}
		
		level.setProjectilesTemplates(projectiles);
	}
	
	private void constructObject(String entity, Map<String, ResourceValue> values) {
		if (entity.equalsIgnoreCase(LEVEL)) {
			constructLevel(values);
		}
		if (entity.equalsIgnoreCase(CAMERA)) {
			constructCamera(values);
		}
		if (entity.equalsIgnoreCase(SHIP) || entity.equalsIgnoreCase(ENEMY)) {
			constructShip(entity, values);
		}
		if (entity.equalsIgnoreCase(WEAPON)) {
			constructWeapon(values);
		}
		if (entity.equalsIgnoreCase(PROJECTILE)) {
			constructProjectile(values);
		}
		if (entity.equalsIgnoreCase(STATIC_BACKGROUND)) {
			constructBackground(false, values);
		}
		if (entity.equalsIgnoreCase(MOVING_BACKGROUND)) {
			constructBackground(true, values);
		}
	}

	private void constructLevel(Map<String, ResourceValue> values) {
		level = new Level();
		for (Entry<String, ResourceValue> value : values.entrySet()) {
			if (value.getKey().equalsIgnoreCase(NAME))
				level.setName(value.getValue().getString());
			if (value.getKey().equalsIgnoreCase(WIDTH))
				level.setWidth(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(HEIGHT))
				level.setHeight(value.getValue().getNumber());
		}
	}
	
	private void constructCamera(Map<String, ResourceValue> values) {
		for (Entry<String, ResourceValue> value : values.entrySet()) {
			if (value.getKey().equalsIgnoreCase(WIDTH))
				cameraWidth = value.getValue().getNumber();
			if (value.getKey().equalsIgnoreCase(HEIGHT))
				cameraHeight = value.getValue().getNumber();
			if (value.getKey().equalsIgnoreCase(ZOOM_MIN))
				cameraZoomMin = value.getValue().getNumber();
			if (value.getKey().equalsIgnoreCase(ZOOM_MAX))
				cameraZoomMax = value.getValue().getNumber();
		}
	}
	
	private void constructShip(String type, Map<String, ResourceValue> values) {
		Sprite ship = shipFactory.createShip(type);
		
		float width = 0, height = 0, x = 0, y = 0;
		
		for (Entry<String, ResourceValue> value : values.entrySet()) {
			if (value.getKey().equalsIgnoreCase(NAME) && ship instanceof EnemyShip)
				((EnemyShip)ship).setName(value.getValue().getString());
			if (value.getKey().equalsIgnoreCase(WIDTH))
				width = value.getValue().getNumber();
			if (value.getKey().equalsIgnoreCase(HEIGHT))
				height = value.getValue().getNumber();
			if (value.getKey().equalsIgnoreCase(X))
				x = value.getValue().getNumber();
			if (value.getKey().equalsIgnoreCase(Y))
				y = value.getValue().getNumber();
			if (value.getKey().equalsIgnoreCase(TEXTURE))
				if (ship instanceof EnemyShip) {
					((EnemyShip)ship).setTexture(pixmapAtlas.createPixmap(value.getValue().getString()));
				} else
					for (int phase = 0; phase < 5; phase++) {
						((Ship)ship).addTexture(phase, atlas.findRegion(value.getValue().getString() + phase));
					}
		}
		
		if (ship instanceof EnemyShip && width != 0 && height != 0 && x != 0 && y != 0) {
			ship.setSize(width, height);
			ship.setPosition(x - width / 2, y - height / 2);
		} else if (width != 0 && height != 0)
			ship.setSize(width, height);
		
		sprites.put((ship instanceof Ship) ? type : ((EnemyShip)ship).getName(), ship);
	}
	
	private void constructWeapon(Map<String, ResourceValue> values) {
		Weapon weapon = new Weapon();
		
		for (Entry<String, ResourceValue> value : values.entrySet()) {
			if (value.getKey().equalsIgnoreCase(NAME))
				weapon.setName(value.getValue().getString());
			if (value.getKey().equalsIgnoreCase(WEAPON_HOST))
				weapon.setEmitterName(value.getValue().getString());
			if (value.getKey().equalsIgnoreCase(SHOTS_PER_SECOND))
				weapon.setShootFrequency((int) value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(PROJECTILE_TYPE))
				weapon.setProjectileType((int) value.getValue().getNumber());
		}

		weapons.put(weapon.getName(), weapon);
	}
	
	private void constructProjectile(Map<String, ResourceValue> values) {
		ProjectileTemplate projectile = new ProjectileTemplate();
		
		for (Entry<String, ResourceValue> value : values.entrySet()) {
			if (value.getKey().equalsIgnoreCase(PROJECTILE_TYPE))
				projectile.setType((int) value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(SPEED))
				projectile.setSpeed((float) value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(WIDTH))
				projectile.setWidth((float) value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(HEIGHT))
				projectile.setHeight((float) value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(TEXTURE))
				projectile.setTexture(atlas.findRegion(value.getValue().getString()));
			if (value.getKey().equalsIgnoreCase(POWER))
				projectile.setPower((float) value.getValue().getNumber());
		}
		
		projectiles.add(projectile);
	}
	
	private void constructBackground(boolean moveable, Map<String, ResourceValue> values) {
		BackgroundElement element = new BackgroundElement();
		
		element.setMoveable(moveable);
		
		for (Entry<String, ResourceValue> value : values.entrySet()) {
			if (value.getKey().equalsIgnoreCase(Z_RANK))
				element.setZrank((int) value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(TEXTURE))
				element.setTexture(atlas.findRegion(value.getValue().getString()));
			if (value.getKey().equalsIgnoreCase(WIDTH))
				element.setWidth(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(HEIGHT))
				element.setHeight(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(X))
				element.setX(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(Y))
				element.setY(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(SHIFTX))
				element.setShiftx(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(SHIFTY))
				element.setShifty(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(REPEAT_WIDTH))
				element.setRepeatWidth(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(REPEAT_HEIGHT))
				element.setRepeatHeight(value.getValue().getNumber());
		}

		background.addElement(element);		
	}
	
	private Sprite getSpriteByName(String name) {
		for (Entry<String, Sprite> sprite : sprites.entrySet()) {
			if (sprite.getValue() instanceof Ship && name.equals(SHIP))
				return sprite.getValue();
			else if (sprite.getValue() instanceof EnemyShip && ((EnemyShip)sprite.getValue()).getName().equalsIgnoreCase(name))
				return sprite.getValue();
		}
		
		return null;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Map<String, Sprite> getSprites() {
		return sprites;
	}
	
	public float getCameraWidth() {
		return cameraWidth;
	}

	public float getCameraHeight() {
		return cameraHeight;
	}

	public float getCameraZoomMin() {
		return cameraZoomMin;
	}

	public float getCameraZoomMax() {
		return cameraZoomMax;
	}
}
