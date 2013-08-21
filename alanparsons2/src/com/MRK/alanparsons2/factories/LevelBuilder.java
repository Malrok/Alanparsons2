package com.MRK.alanparsons2.factories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.MRK.alanparsons2.controllers.BackgroundController;
import com.MRK.alanparsons2.models.BackgroundElement;
import com.MRK.alanparsons2.models.EnemyShip;
import com.MRK.alanparsons2.models.GameLevel;
import com.MRK.alanparsons2.models.Ship;
import com.MRK.alanparsons2.resources.LevelFileHandler;
import com.MRK.alanparsons2.resources.Resource;
import com.MRK.alanparsons2.resources.ResourceValue;
import com.MRK.alanparsons2.templates.CameraTemplate;
import com.MRK.alanparsons2.templates.ProjectileTemplate;
import com.MRK.alanparsons2.templates.TouchInputTemplate;
import com.MRK.alanparsons2.templates.WeakPointTemplate;
import com.MRK.alanparsons2.templates.WeaponTemplate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class LevelBuilder implements Disposable {

	/* répertoires */
	public static final String LEVEL_DIR_INTERNAL = "levels/";
	public static final String LEVEL_DIR_EXTERNAL = "Progzzz/alanp/";
	public static final String ATLAS_DIR = "atlas/";
	public static final String PARTICLES_DIR = "particles/";
	
	/* extensions */
	public static final String LEVEL_EXT = ".lvl";
	public static final String ATLAS_EXT = ".pack";
	public static final String TEXTURES_EXT = ".png";
	
	/* entités */
	public static final String LEVEL = "level";
	public static final String CAMERA = "camera";
	public static final String INPUT = "input";
	public static final String SHIP = "ship";
	public static final String ENEMY = "enemy";
	public static final String WEAPON = "weapon";
	public static final String WEAK_POINT = "weakpoint";
	public static final String PROJECTILE = "projectile";
	public static final String BACKGROUND = "background";
	
	/* propriétés */
	public static final String X = "x";
	public static final String Y = "y";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String TEXTURE = "texture";
	public static final String SHELL_TEXTURE = "shelltexture";
	public static final String STRUCTURE_TEXTURE = "structuretexture";
	public static final String POWER = "power";
	public static final String SHOTS_PER_SECOND = "sps";
	public static final String SPEED = "speed";
	public static final String ZOOM_MIN = "zoom_min";
	public static final String ZOOM_MAX = "zoom_max";
	public static final String REPEAT_X = "repeatx";
	public static final String REPEAT_Y = "repeaty";
	public static final String SHIFTX = "shiftx";
	public static final String SHIFTY = "shifty";
	public static final String NAME = "name";
	public static final String HOST = "host";
	public static final String PROJECTILE_TYPE = "projectiletype";
	public static final String Z_RANK = "zrank";
	public static final String DAMAGE_PERCENTAGE_LEVEL_UP = "damagepercentagelevelup";
	public static final String WEAPON_DESTRUCTION_LEVELS_UP = "weapondestructionlevelsup";
	public static final String AFTER_TIME_LEVEL_UP = "aftertimelevelup";
	public static final String SHIP_LEVEL = "shiplevel";
	public static final String SHOOTING_DISTANCE = "shootdistance";
	public static final String UPPER_SPEED_LIMIT = "up";
	public static final String NORMAL_SPEED_LIMIT = "middle";
	public static final String LOWER_SPEED_LIMIT = "down";
	public static final String MIN_SHIP_SPEED = "minspeed";
	public static final String NORMAL_SHIP_SPEED = "normalspeed";
	public static final String MAX_SHIP_SPEED = "maxspeed";
	public static final String SPEED_DOWN_DELAY = "speeddowndelay";
	public static final String CAMERA_RADIUS = "radius";
	public static final String HEALTH_POINTS = "hp";
	public static final String ORDER = "order";
	
	private LevelFileHandler levelHandler = new LevelFileHandler();	
	private TextureAtlas atlas;
	private PixmapTextureAtlas pixmapAtlas;
	private ShipFactory shipFactory = new ShipFactory();
	private GameLevel level;
	private Map<String, Sprite> sprites;
	private List<WeaponTemplate> weapons;
	private List<WeakPointTemplate> weakPoints;
	private List<ProjectileTemplate> projectiles;
	private CameraTemplate cameraTemplate;
	private BackgroundController background;
	private TouchInputTemplate touchTemplate;
	
	@Override
	public void dispose() {
		if (level != null)
			level.dispose();
		if (atlas != null)
			atlas.dispose();
		if (pixmapAtlas != null)
			pixmapAtlas.dispose();
	}
	
	public void load(boolean internal, String levelFile) throws IOException {
		FileHandle handle;
		
		level = new GameLevel();
		sprites = new HashMap<String, Sprite>();
		weapons = new ArrayList<WeaponTemplate>();
		weakPoints = new ArrayList<WeakPointTemplate>();
		projectiles = new ArrayList<ProjectileTemplate>();
		background = new BackgroundController();
		atlas = new TextureAtlas(Gdx.files.internal(ATLAS_DIR + levelFile + ATLAS_EXT));
		pixmapAtlas = new PixmapTextureAtlas(Gdx.files.internal(ATLAS_DIR + levelFile + TEXTURES_EXT), Gdx.files.internal(ATLAS_DIR + levelFile + ATLAS_EXT));
		
		if (internal)
			handle = Gdx.files.internal(LEVEL_DIR_INTERNAL + levelFile + LEVEL_EXT);
		else
			handle = Gdx.files.external(LEVEL_DIR_EXTERNAL + levelFile + LEVEL_EXT);
		
		levelHandler.load(handle);
	}
	
	public void parse() {
		for (Map.Entry<String, Resource> resource : levelHandler.resources.entries()) {
			constructObject(resource.getKey(), resource.getValue().getValues());
		}
	}
	
	public void buildLevel() {
		level.setBackground(background);
		
		for (WeaponTemplate weapon : weapons) {
			weapon.setEmitter(getSpriteByName(weapon.getEmitterName()));
			level.addWeaponTemplate(weapon);
		}
		
		for (WeakPointTemplate weakPoint : weakPoints) {
			weakPoint.setHost(getSpriteByName(weakPoint.getHostName()));
			level.addWeakPointTemplate(weakPoint);
		}
		
		for (Entry<String, Sprite> sprite : sprites.entrySet()) {
			if (sprite.getValue() instanceof Ship)
				level.setShip((Ship) sprite.getValue());
			else if (sprite.getValue() instanceof EnemyShip)
				level.addEnemy((EnemyShip) sprite.getValue());
		}
		
		level.setProjectilesTemplates(projectiles);
		level.setCameraTemplate(cameraTemplate);
		level.setTouchTemplate(touchTemplate);
	}
	
	private void constructObject(String entity, Map<String, ResourceValue> values) {
		if (entity.equalsIgnoreCase(LEVEL)) {
			constructLevel(values);
		}
		if (entity.equalsIgnoreCase(CAMERA)) {
			constructCamera(values);
		}
		if (entity.equalsIgnoreCase(INPUT)) {
			constructInput(values);
		}
		if (entity.equalsIgnoreCase(SHIP) || entity.equalsIgnoreCase(ENEMY)) {
			constructShip(entity, values);
		}
		if (entity.equalsIgnoreCase(WEAPON)) {
			constructWeapon(values);
		}
		if (entity.equalsIgnoreCase(WEAK_POINT)) {
			constructWeakPoint(values);
		}
		if (entity.equalsIgnoreCase(PROJECTILE)) {
			constructProjectile(values);
		}
		if (entity.equalsIgnoreCase(BACKGROUND)) {
			constructBackground(values);
		}
	}

	private void constructLevel(Map<String, ResourceValue> values) {
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
		cameraTemplate = new CameraTemplate();
		
		for (Entry<String, ResourceValue> value : values.entrySet()) {
			if (value.getKey().equalsIgnoreCase(WIDTH))
				cameraTemplate.setCameraWidth(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(ZOOM_MIN))
				cameraTemplate.setCameraZoomMin(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(ZOOM_MAX))
				cameraTemplate.setCameraZoomMax(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(CAMERA_RADIUS))
				cameraTemplate.setCameraRadius(value.getValue().getNumber());
		}
	}

	private void constructInput(Map<String, ResourceValue> values) {
		touchTemplate = new TouchInputTemplate();
		
		for (Entry<String, ResourceValue> value : values.entrySet()) {
			if (value.getKey().equalsIgnoreCase(UPPER_SPEED_LIMIT)) {
				touchTemplate.setUpperSpeedLimit(value.getValue().getNumber());
			}
			if (value.getKey().equalsIgnoreCase(NORMAL_SPEED_LIMIT)) {
				touchTemplate.setNormalSpeedLimit(value.getValue().getNumber());
			}
			if (value.getKey().equalsIgnoreCase(LOWER_SPEED_LIMIT)) {
				touchTemplate.setLowerSpeedLimit(value.getValue().getNumber());
			}
			if (value.getKey().equalsIgnoreCase(MIN_SHIP_SPEED)) {
				touchTemplate.setMinSpeed(value.getValue().getNumber());
			}
			if (value.getKey().equalsIgnoreCase(NORMAL_SHIP_SPEED)) {
				touchTemplate.setNormalSpeed(value.getValue().getNumber());
			}
			if (value.getKey().equalsIgnoreCase(MAX_SHIP_SPEED)) {
				touchTemplate.setMaxSpeed(value.getValue().getNumber());
			}
			if (value.getKey().equalsIgnoreCase(SPEED_DOWN_DELAY)) {
				touchTemplate.setSpeedDownDelay((int) value.getValue().getNumber());
			}
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
				for (int phase = 0; phase < 5; phase++) {
					((Ship)ship).addTexture(phase, atlas.findRegion(value.getValue().getString() + phase));
				}
			if (value.getKey().equalsIgnoreCase(SHELL_TEXTURE))
				((EnemyShip)ship).setHull(pixmapAtlas.createPixmap(value.getValue().getString()));
			if (value.getKey().equalsIgnoreCase(STRUCTURE_TEXTURE))
				((EnemyShip)ship).setStructure(pixmapAtlas.createPixmap(value.getValue().getString()));
		}
		
		if (ship instanceof EnemyShip && width != 0 && height != 0 && x != 0 && y != 0) {
			ship.setSize(width, height);
			ship.setPosition(x - width / 2, y - height / 2);
		} else if (width != 0 && height != 0) {
			ship.setSize(width, height);
			((Ship)ship).setyFromScreen(y);
		}
		
		sprites.put((ship instanceof Ship) ? type : ((EnemyShip)ship).getName(), ship);
	}
	
	private void constructWeapon(Map<String, ResourceValue> values) {
		WeaponTemplate weapon = new WeaponTemplate();
		
		for (Entry<String, ResourceValue> value : values.entrySet()) {
			if (value.getKey().equalsIgnoreCase(NAME))
				weapon.setName(value.getValue().getString());
			if (value.getKey().equalsIgnoreCase(HOST))
				weapon.setEmitterName(value.getValue().getString());
			if (value.getKey().equalsIgnoreCase(SHOTS_PER_SECOND))
				weapon.setShootFrequency((int) value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(PROJECTILE_TYPE))
				weapon.setProjectileType((int) value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(SHIP_LEVEL))
				weapon.setShipLevel((int) value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(TEXTURE))
				weapon.setTexture(atlas.findRegion(value.getValue().getString()));
			if (value.getKey().equalsIgnoreCase(WIDTH))
				weapon.setWidth(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(HEIGHT))
				weapon.setHeight(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(HEALTH_POINTS))
				weapon.setHps((int)value.getValue().getNumber());
		}
		
		weapons.add(weapon);
	}

	private void constructWeakPoint(Map<String, ResourceValue> values) {
		WeakPointTemplate weakPoint = new WeakPointTemplate();
		
		for (Entry<String, ResourceValue> value : values.entrySet()) {
			if (value.getKey().equalsIgnoreCase(HOST))
				weakPoint.setHostName(value.getValue().getString());
			if (value.getKey().equalsIgnoreCase(TEXTURE))
				weakPoint.setTexture(atlas.findRegion(value.getValue().getString()));
			if (value.getKey().equalsIgnoreCase(WIDTH))
				weakPoint.setWidth(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(HEIGHT))
				weakPoint.setHeight(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(HEALTH_POINTS))
				weakPoint.setEnergy((int)value.getValue().getNumber());
		}
		
		weakPoints.add(weakPoint);
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
	
	private void constructBackground(Map<String, ResourceValue> values) {
		BackgroundElement element = new BackgroundElement();
		
		float x = 0, y = 0;
		
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
				if (value.getValue().getType() == ResourceValue.NUMBER)
					x = value.getValue().getNumber();
				else
					element.setxFormula(value.getValue().getString());
			if (value.getKey().equalsIgnoreCase(Y))
				if (value.getValue().getType() == ResourceValue.NUMBER)
					y = value.getValue().getNumber();
				else
					element.setyFormula(value.getValue().getString());
			if (value.getKey().equalsIgnoreCase(SHIFTX))
				element.setShiftx(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(SHIFTY))
				element.setShifty(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(REPEAT_X))
				element.setRepeatX(value.getValue().getNumber());
			if (value.getKey().equalsIgnoreCase(REPEAT_Y))
				element.setRepeatY(value.getValue().getNumber());
		}
		
		element.setX(x - element.getWidth() / 2);
		element.setY(y - element.getHeight() / 2);
		
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
	
	public GameLevel getLevel() {
		return level;
	}
	
	public Map<String, Sprite> getSprites() {
		return sprites;
	}

	public CameraTemplate getCameraTemplate() {
		return cameraTemplate;
	}
}
