package com.MRK.alanparsons2.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.MRK.alanparsons2.factories.LevelBuilder;
import com.MRK.alanparsons2.models.Level;
import com.MRK.alanparsons2.resources.LevelFileHandler;
import com.MRK.alanparsons2.resources.Resource;
import com.MRK.alanparsons2.resources.ResourceValue;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Charge les niveaux et les gère
 * @author malrok
 *
 */
public class LevelController {

	private List<Level> levels = new ArrayList<Level>();
	
	public void init() {
		initList();
		buildList();
	}
	
	public List<Level> getLevels() {
		return levels;
	}
	
	public boolean getNextLevel(String currentLevelFileName) {
		int index = getLevelIndex(currentLevelFileName);
		
		if (index < levels.size()) {
			currentLevelFileName = levels.get(index + 1).getFile().name();
			return true;
		}
		
		return false;
	}
	
	private void initList() {
		FileHandle dirHandle;
		
		if (Gdx.app.getType() == ApplicationType.Android) {
		  dirHandle = Gdx.files.internal("levels");
		} else {
		  dirHandle = Gdx.files.internal("./bin/levels");
		}
		
		for (FileHandle file : dirHandle.list()) {
			Level level = new Level();
			
			level.setFile(file);
			
			levels.add(level);
		}
	}
	
	private void buildList() {
		LevelFileHandler levelHandler = new LevelFileHandler();
		
		for (Level level : levels) {
			try {
				levelHandler.load(level.getFile());
			} catch (IOException e) {
				System.out.println("Problem loading level " + level.getFile().name());
				e.printStackTrace();
			}
			
			for (Map.Entry<String, Resource> resource : levelHandler.resources.entries()) {
				if (resource.getKey().equalsIgnoreCase(LevelBuilder.LEVEL)) {
					for (Entry<String, ResourceValue> value : resource.getValue().getValues().entrySet()) {
						if (value.getKey().equalsIgnoreCase(LevelBuilder.NAME)) {
							level.setName(value.getValue().getString());
						}
//						TODO
//						if (value.getKey().equalsIgnoreCase(LevelBuilder.TEXTURE)) {
//							level.setTexture(atlas.findRegion(value.getValue().getString()));
//						}
					}
				}
			}
		}
	}
	
	private int getLevelIndex(String levelFileName) {
		int ret = -1;
		
		for (Level level : levels) {
			if (level.getFile().name().equalsIgnoreCase(levelFileName))
				return levels.indexOf(level);
		}
		
		return ret;
	}
}
