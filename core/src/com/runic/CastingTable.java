package com.runic;

import android.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.runic.Effects.BendingRune;
import com.runic.Effects.PolygonEffect;
import com.runic.Projectiles.BaseProjectile;
import com.runic.Units.*;

/**
 * Created by Nothrim on 2015-08-14.
 */
public class CastingTable {
    public static final int CASTING_TABLE_SIZE=8;
    private Rune[] table;
    private Vector2 position;
    private int CurrentPosition;
    private Player owner;

    public Vector2 getPosition() {
        return position;
    }

    public int getCurrentPosition() {
        return CurrentPosition;
    }

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
        PolygonEffect.clear(owner);
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
            if(Combinations.getCombination(combination.toString()).getType()==0)
            owner.spawnUnit(new Footman(owner, BaseUnit.TYPE.LAND,5,10,5,BaseUnit.MELEE_RANGE,30,owner.castle.getSpawnpoint(),200,1,Assets.getInstance().FootmanAnimation,8));
            else if(Combinations.getCombination(combination.toString()).getType()==1) {
                owner.spawnUnit(new Archer(owner,owner.castle.getSpawnpoint(),200));
            }
            else if(Combinations.getCombination(combination.toString()).getType()==2) {
                owner.spawnUnit(new BloodKnight(owner,owner.castle.getSpawnpoint(),200));
            }
            else if(Combinations.getCombination(combination.toString()).getType()==3){
                owner.spawnUnit(new EnergyCloud(owner,owner.castle.getSpawnpoint(),620));
            }
            else if(Combinations.getCombination(combination.toString()).getType()==4)
            {
                owner.spawnUnit(new EarthWorm(owner,owner.getCastle().getSpawnpoint(),0,MathUtils.random(5,8)));
            }
            else if(Combinations.getCombination(combination.toString()).getType()==1000) {
                    Dummy.newDummy(new DummyArrow(owner.getCastle().getSpawnpoint(), owner.getCastle().getY() + 100, owner, 180));

            }
            else if(Combinations.getCombination(combination.toString()).getType()==1001)
            {
                int position=Combinations.getCombination(combination.toString()).getSpecialCharacterPosition();
                if(combination.charAt(position+1)!='.')
                    owner.fillWith( (((int)combination.charAt(position) - 48)*10+(int) combination.charAt(position + 1) - 48), 2);
                else
                    owner.fillWith((int) combination.charAt(position) - 48, 2);
            }
            else if(Combinations.getCombination(combination.toString()).getType()==1002)
            {
                Dummy.newDummy(new FootmanSummoner(owner.getCastle().getSpawnpoint(), owner.getCastle().getY() + 100, owner, 420));
            }
            else if(Combinations.getCombination(combination.toString()).getType()==1003)
            {
                int position=Combinations.getCombination(combination.toString()).getSpecialCharacterPosition();
                if(combination.charAt(position+1)!='.') {
                    owner.refillChosen(((int) combination.charAt(position) - 48) * 10 + (int) combination.charAt(position + 1) - 48);
                }
                else
                    owner.refillChosen((int) combination.charAt(position) - 48);
            }
            owner.cast();

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
        PolygonEffect.create(new BendingRune(r.id,0.6f,r.getX(),r.getY(),x,y,owner));
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
    public void dispose()
    {
    }
}
