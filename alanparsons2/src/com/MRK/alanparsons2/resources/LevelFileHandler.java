package com.MRK.alanparsons2.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.files.FileHandle;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Lit le contenu d'un fichier de ressources et le place dans un dictionnaire<BR>
 * Le fichier devra avoir le format suivant <BR><BR>
 * [section1]<BR>
 * entry1:value<BR>
 * entry2:value<BR>
 * ...<BR>
 * [sectionN]<BR>
 * entry1:value<BR>
 * ...
 * 
 * @author malrok
 *
 */
public class LevelFileHandler {

	private static final String SECTION_MARKER = "[";
	private static final String SEMI_COLON = ":";
	
	public Multimap<String, Resource> resources;
	
	public void load(FileHandle levelFile) throws IOException {
		String line = "", entry = "", value = "";
		resources = ArrayListMultimap.create();
		Resource resource = new Resource();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(levelFile.read()));
		
		line = reader.readLine().trim();
		
		while (line != null) {
			if (!line.startsWith("#")) {
				if (line.startsWith(SECTION_MARKER)) {
					resource = new Resource();
					resources.put(line.substring(1, line.length() - 1), resource);
				} else if (resource != null && line.contains(SEMI_COLON)) {
					entry = line.substring(0, line.indexOf(SEMI_COLON));
					value = line.substring(line.indexOf(SEMI_COLON) + 1, line.length());
					
					try {
						resource.addValue(entry, Float.valueOf(value));
					} catch (NumberFormatException e) {
						resource.addValue(entry, value);
					}
				}
			}
			line = reader.readLine();
		}
		
		reader.close();
	}
	
}
