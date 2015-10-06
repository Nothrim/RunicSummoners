package com.runic.Effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Nothrim on 2015-09-25.
 */
public class SpriteEffect {


    protected float life;
    protected float lifeTimer;
    protected boolean active;
    public boolean isActive(){return active;}
    public SpriteEffect(float life)
    {
        this.life=life;
        lifeTimer=0;
        active=true;
    }
    protected void update(float deltaTime)
    {
        lifeTimer+=deltaTime;
        if(lifeTimer>life)
        {
            active=false;
        }
    }
    public void draw(float deltaTime,SpriteBatch sb)
    {
        update(deltaTime);
    }
}
