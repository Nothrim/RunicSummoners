package com.runic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.runic.Units.BaseUnit;
import com.runic.Units.Footman;

/**
 * Created by Nothrim on 2015-08-14.
 */
public class CastingTable {
    public static final int CASTING_TABLE_SIZE=8;
    private Rune[] table;
    private Vector2 position;
    int CurrentPosition;
    Player owner;
    public CastingTable(Vector2 position,Player owner)
    {
        this.owner=owner;
        CurrentPosition=0;
        this.position=position;
        table=new Rune[CASTING_TABLE_SIZE];
        for(int i=0;i<CASTING_TABLE_SIZE;i++)
        {
            table[i]=new Rune(Assets.getInstance().RuneBlank,0,position.x+i*40,position.y,false);
        }

    }
    public void cast()
    {
        StringBuilder combination=new StringBuilder();
        for(int i=0;i<CurrentPosition;i++) {
            combination.append(table[i].id);
            combination.append('.');
            table[i]=new Rune(Assets.getInstance().RuneBlank,0,table[i].getX(),table[i].getY(),false);
        }
        if(combination.length()>0)
        combination.deleteCharAt(combination.length()-1);
        CurrentPosition=0;
        if(Combinations.getCombination(combination.toString())!=null)
        {

            owner.spawnUnit(new Footman(owner, BaseUnit.TYPE.LAND,5,10,5,BaseUnit.MELEE_RANGE,30,owner.castle.getSpawnpoint(),200,1,Assets.getInstance().FootmanAnimation,8));
        }
        owner.setCurrentCombination(null);

    }
    public void draw(SpriteBatch sb)
    {
        for(int i=0;i<table.length;i++)
            table[i].draw(sb);
    }
    public boolean canAdd()
    {
        if (CurrentPosition<CASTING_TABLE_SIZE)
            return true;
        else
            return false;
    }
    public void add(Rune r)
    {
        float x=table[CurrentPosition].getX();
        float y=table[CurrentPosition].getY();
        table[CurrentPosition]=r;
        table[CurrentPosition].setPosition(x, y);
        CurrentPosition++;
        StringBuilder combination=new StringBuilder();
        for(int i=0;i<CurrentPosition;i++) {
            combination.append(table[i].id);
            combination.append('.');
        }
        combination.deleteCharAt(combination.length()-1);

        if(Combinations.getCombination(combination.toString())!=null)
        {
            owner.setCurrentCombination(Combinations.getCombination(combination.toString()).getName());
        }
        else
            owner.setCurrentCombination(null);
    }

}
