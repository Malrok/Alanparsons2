package com.MRK.alanparsons2.helpers;

import java.util.List;

import com.MRK.alanparsons2.templates.WeakPointTemplate;

public class WeakPointHelper {

	private List<WeakPointTemplate> weakPointsTemplates;
	
	public WeakPointHelper(List<WeakPointTemplate> weakPointTemplates) {
		this.weakPointsTemplates = weakPointTemplates;
	}
	
	public WeakPointTemplate getMatchingTemplate(String hostName) {
		
		for (WeakPointTemplate template : weakPointsTemplates) {
			if (template.getHostName().equals(hostName))
				return template;
		}
		
		return null;
	}
	
}
