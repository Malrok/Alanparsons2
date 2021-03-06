package com.MRK.alanparsons2.helpers;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Accesseur {@link TweenAccessor} vers un {@link Sprite}<BR>
 */
public class SpriteAccessor implements TweenAccessor<Sprite> {

	public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int POSITION_XY = 3;
    public static final int ZOOM = 4;

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POSITION_X: returnValues[0] = target.getX(); return 1;
            case POSITION_Y: returnValues[0] = target.getY(); return 1;
            case POSITION_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            case ZOOM:
        		returnValues[0] = target.getScaleX();
        		returnValues[1] = target.getScaleY();
            	return 2;
            default: assert false; return -1;
        }
    }
    
    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POSITION_X: target.setX(newValues[0]); break;
            case POSITION_Y: target.setY(newValues[0]); break;
            case POSITION_XY:
                target.setX(newValues[0]);
                target.setY(newValues[1]);
                break;
            case ZOOM: target.setScale(newValues[0], newValues[1]); break;
            default: assert false; break;
        }
    }
}