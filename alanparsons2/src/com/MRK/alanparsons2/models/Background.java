package com.MRK.alanparsons2.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	
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
			if (element.isMoveable()) {
//				System.out.println("drawing background moveable deltax/deltay = " + deltax + "/" + deltay + " x/shiftx/y/shifty = " + 
//						element.getX() + "/" + element.getShiftx() + "/" + element.getY() + "/" + element.getShifty());
				batch.draw(element.getTexture(), element.getX() + element.getShiftx() * deltax, element.getY() + element.getShifty() * deltay, element.getWidth(), element.getHeight());
			} else {
//				System.out.println("drawing background unmoveable lw/lh = " + levelWidth + "/" + levelHeight + " repeatW/repeatH = " + element.getRepeatWidth() + "/" + element.getRepeatHeight());
				for (int w = 0; w < element.getRepeatWidth(); w++) {
					for (int h = 0; h < element.getRepeatHeight(); h++) {
						batch.draw(element.getTexture(), 
								w * levelWidth / element.getRepeatWidth(), h * levelHeight / element.getRepeatHeight(), 
								levelWidth / element.getRepeatWidth(), levelHeight / element.getRepeatHeight());
					}
				}
			}
		}
	}
}
