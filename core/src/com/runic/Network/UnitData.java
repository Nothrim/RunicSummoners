package com.runic.Network;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Nothrim on 2015-10-08.
 */
public class UnitData{
    public UnitData(Vector2 position,int id,boolean walking,boolean attacking)
    {
        this.position=position;
        this.id=id;
        this.walking=walking;
        this.attacking=attacking;
    }
    public UnitData(){

    }
    Vector2 position;
    boolean walking;
    boolean attacking;
    int id;
}