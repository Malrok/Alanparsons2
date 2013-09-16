package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.MRK.alanparsons2.models.BackgroundElement;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundController {
	
	private float levelWidth, levelHeight;
	private List<BackgroundElement> elements = new ArrayList<BackgroundElement>();
	
	public void init(float width, float height) {
		this.levelWidth = width;
		this.levelHeight = height;
	}
	
	public void addElement(BackgroundElement element) {
		elements.add(element);
		
		Collections.sort(elements);
	}
	
	public void draw(SpriteBatch batch, float deltax, float deltay) {
		for (BackgroundElement element : elements) {
			if (element.getTexture() != null) {
				if (element.isLight())
					batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
				else
					batch.setBlendFunction(GL11.GL_ACTIVE_TEXTURE, GL11.GL_ONE);
				
				if (element.isRepeated()) {
					for (int w = 0; w < element.getRepeatX(); w++) {
						for (int h = 0; h < element.getRepeatY(); h++) {
							batch.draw(
									element.getTexture(), 
									w * levelWidth / element.getRepeatX(), h * levelHeight / element.getRepeatY(), 
									levelWidth / element.getRepeatX(), levelHeight / element.getRepeatY());
						}
					}
				} else {
					batch.draw(
							element.getTexture(), 
							element.getX() + element.getShiftx() * deltax, element.getY() + element.getShifty() * deltay, 
							element.getWidth(), element.getHeight());
				}
			}
		}
	}
}
