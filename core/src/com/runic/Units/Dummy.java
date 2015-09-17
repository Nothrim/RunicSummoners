package com.runic.Units;

import com.runic.Player;

/**
 * Created by Nothrim on 2015-09-09.
 */
public abstract class Dummy {
    public static Dummy[] Dummies;
    public static void initialize()
    {
        Dummies=new Dummy[500];
    }
    public static void upadte(float deltaTime)
    {
        for(int i=0;i<Dummies.length;i++)
        {
            if(Dummies[i]!=null && Dummies[i].active)
            {
                Dummies[i].update(deltaTime);
            }
        }
    }
    public static int newDummy(Dummy d)
    {
        for(int i=0;i<Dummies.length;i++)
        {
            if(Dummies[i]==null || !Dummies[i].active)
            {
                Dummies[i]=d;
                return i;
            }
        }
        return -1;
    }
    protected float x;
    protected float y;
    protected Player owner;
    protected int life;
    protected boolean active;
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
