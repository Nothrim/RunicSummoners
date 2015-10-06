package com.runic.Units;

import com.runic.Player;

/**
 * Created by Nothrim on 2015-09-09.
 */
public abstract class Dummy {
    protected float x;
    protected float y;
    protected Player owner;
    protected int life;
    protected boolean active;
    public boolean isActive(){return active;}
    protected Dummy(float x,float y,Player owner,int life)
    {
        this.x=x;
        this.y=y;
        this.owner=owner;
        this.life=life;
        active=true;
    }
    public void update(float deltaTime)
    {
        if(life>0)
            life-=deltaTime;
        else
            active=false;
    }
}
