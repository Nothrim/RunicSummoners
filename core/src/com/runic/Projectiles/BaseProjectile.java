package com.runic.Projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Nothrim on 2015-08-25.
 */
public class BaseProjectile {
    protected Animation movement;
    protected Animation hit;
    protected float x;
    protected float y;
    protected float velocityX;
    protected float velocityY;
    protected int damage;
    public void update()
    {
        x+=velocityX;
        y+=velocityY;
    }
    public void draw(SpriteBatch sb)
    {
        update();
    }


}
