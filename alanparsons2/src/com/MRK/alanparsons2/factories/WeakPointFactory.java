package com.MRK.alanparsons2.factories;

import com.MRK.alanparsons2.helpers.WeakPointHelper;
import com.MRK.alanparsons2.models.WeakPoint;
import com.MRK.alanparsons2.templates.WeakPointTemplate;

public class WeakPointFactory {
		
	public WeakPoint createWeakPoint(WeakPointHelper helper, String host) {
		WeakPoint weakPoint = new WeakPoint();
		WeakPointTemplate template = helper.getMatchingTemplate(host);
		
		if (template != null) {
			weakPoint.setTexture(template.getTexture());
			weakPoint.setSize(template.getWidth(), template.getHeight());
			weakPoint.setHostName(template.getHostName());
			weakPoint.setHost(template.getHost());
			weakPoint.setEnergy(template.getEnergy());
		}
		
		return weakPoint;
	}

}
