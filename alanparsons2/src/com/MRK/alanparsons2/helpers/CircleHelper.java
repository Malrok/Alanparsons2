package com.MRK.alanparsons2.helpers;

import com.badlogic.gdx.math.Vector2;

/**
 * Classe définissant des méthodes statiques de calculs sur les cercles
 * @author malrok
 *
 */
public class CircleHelper {

	/**
	 * Renvoie les coordonnées d'un point sur un cercle
	 * @param originx - float : abscisse du centre du cercle
	 * @param originy - float : ordonnée du centre du cercle
	 * @param radius  - float : rayon du cercle
	 * @param angle   - float : angle du point
	 * @return
	 */
	public static Vector2 getPointOnCircle(float originx, float originy, float radius, float angle) {
		double x = originx + (radius * Math.cos(angle));
		double y = originy + (radius * Math.sin(angle));

//		System.out.println("getPointOnCircle origin x/y = " + origin.x + "/" + origin.y +
//		" radius = " + radius + " angle = " + angle +
//		"\nnew x/y = " + x + "/" + y);
		
		return new Vector2((float) x, (float) y);
	}
	
	/**
	 * Retourne un vecteur pointant depuis un point vers un autre, d'une taille donnée par le paramètre speed
	 * @param originx
	 * @param originy
	 * @param aimx
	 * @param aimy
	 * @param speed
	 * @return {@link Vector2}
	 */
	public static Vector2 getVectorAimingAtCenter(float originx, float originy, float aimx, float aimy, float speed) {
		double angle = getRAngle(originx - aimx, originy - aimy);
		
//		System.out.println("getVectorAimingAtCenter angle=" + Math.toDegrees(angle));
		
		double x = speed * Math.cos(angle);
		double y = speed * Math.sin(angle);
		
		return new Vector2((float)x, (float)y);
	}
	
	/**
	 * Renvoie l'angle en radian formé par le vecteur
	 * @param x - float
	 * @param y - float
	 * @return  - float
	 */
	public static float getRAngle(float x, float y) {
		return (float) Math.atan(y / x);
	}
	
	/**
	 * Renvoie l'angle en degrés formé par le vecteur
	 * @param x - float
	 * @param y - float
	 * @return  - float
	 */
	public static float getDAngle(float x, float y) {
		return (float) Math.toDegrees(Math.atan(y / x));
	}
}