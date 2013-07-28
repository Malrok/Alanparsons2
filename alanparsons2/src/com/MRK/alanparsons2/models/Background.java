package com.MRK.alanparsons2.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	
	private List<BackgroundElement> elements = new ArrayList<BackgroundElement>();
	
	public void addElement(BackgroundElement element) {
		elements.add(element);
		
		Collections.sort(elements);
	}
	
	public void draw(SpriteBatch batch, float deltax, float deltay) {
		for (BackgroundElement element : elements) {
			element.draw(batch, deltax, deltay);
		}
	}
	
}
