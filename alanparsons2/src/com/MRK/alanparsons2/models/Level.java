package com.MRK.alanparsons2.models;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

/**
 * Niveau lue depuis un fichier<BR>
 * Classe destinée à l'affichage et à la sélection d'un niveau à jouer
 * @author malrok
 *
 */
public class Level implements Comparable<Level> {

	private FileHandle file;
	private int order;
	private String name;
	private Texture image;
	
	public FileHandle getFile() {
		return file;
	}
	
	public void setFile(FileHandle file) {
		this.file = file;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Texture getImage() {
		return image;
	}
	
	public void setImage(Texture image) {
		this.image = image;
	}

	@Override
	public int compareTo(Level other) {
		if (order < other.getOrder())
			return -1;
		if (order > other.getOrder())
			return 1;
		return 0;
	}
	
}
