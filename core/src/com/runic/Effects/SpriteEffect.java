package com.runic.Effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Nothrim on 2015-09-25.
 */
public class SpriteEffect {
    public static SpriteEffect[] effects;
    public static void initialze(int size)
    {
     effects=new SpriteEffect[size];
    }
    public static int create(SpriteEffect spriteEffect)
    {
        for(int i=0;i<effects.length;i++)
        {
            if(effects[i]==null || !effects[i].active)
            {
                effects[i]=spriteEffect;
                return i;
            }
        }
        return -1;
    }
    public static void Draw(float deltaTime,SpriteBatch sb)
    {
        for(int i=0;i<effects.length;i++)
        {
            if(effects[i]!=null && effects[i].active)
            {
                effects[i].draw(deltaTime,sb);
            }
        }
    }


    protected float life;
    protected float lifeTimer;
    protected boolean active;
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
    protected void draw(float deltaTime,SpriteBatch sb)
    {
        update(deltaTime);
    }
}
