package com.runic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Created by Nothrim on 2015-08-15.
 */
public class Unit extends Sprite {
    public static void loadMap(TiledMap map_file)
    {
        map=map_file;
        collisionLayer=(TiledMapTileLayer)map.getLayers().get(0);
    }
    public static TiledMap map;
    private static TiledMapTileLayer collisionLayer;
    private float time=0;
    Animation walk;
    Animation jump;
    Animation stance;
    Animation attack;
    private boolean walking=true;
    private int gold;
    private short owner;
    private int MaxLife;
    private int speed;
    private int type;
    private int CurrentLife;
    private boolean active;
    private int damage;
    private boolean vunerable;
    private boolean jumping =false;
    private boolean attacking=false;
    private float JumpTime;
    private int direction;

    public void move(float DeltaTime,int direction)
    {

        if(collisionLayer.getCell((int)((getX()+4*direction)/8),(int)((getY())/8))==null)
        {
            if(!jumping)
            this.translateY(100 * -DeltaTime);
            else
            {
                if(collisionLayer.getCell((int)((getX()+4*direction)/8),(int)((getY()-8)/8))==null)
                this.translateY(10 * (-DeltaTime) - JumpTime * DeltaTime * 10);
                else
                    this.translateY(10*-DeltaTime);
            }
        }
        if(collisionLayer.getCell((int)((getX()+direction*12)/8),(int)(((getY())+8)/8))==null)
        this.translateX(speed * direction * DeltaTime);
        else {
            jumping=true;
            this.translateY(Math.min(200*speed,1000) * DeltaTime);
        }
    }
    public static Unit NewUnit(TextureAtlas atlas,int positionX,int positionY,int gold,short owner,int life,int speed,int damage,int type){
        if(life>0 && type>=0) {
            Sprite sprite=atlas.createSprite("walk0");
            sprite.setPosition(positionX,positionY);
            return new Unit(atlas,sprite, gold, owner, life, speed, damage, type);
        }
        return null;
    }


    private Unit(TextureAtlas atlas,Sprite sprite,int gold,short owner,int MaxLife,int speed,int damage,int type)
    {
        super(sprite);
        this.gold=gold;
        this.owner=owner;
        this.MaxLife = MaxLife;
        this.damage=damage;
        this.speed=speed;
        this.type=type;
        CurrentLife=MaxLife;
        active=true;
        vunerable=true;

        walk=new Animation(1/8f,Assets.getInstance().FootmanAnimation.walk, Animation.PlayMode.LOOP);
        jump=new Animation(1,Assets.getInstance().FootmanAnimation.jump, Animation.PlayMode.LOOP);
        attack=new Animation(1/8f,Assets.getInstance().FootmanAnimation.attack, Animation.PlayMode.LOOP);
        stance=new Animation(1/8f,Assets.getInstance().FootmanAnimation.stance, Animation.PlayMode.LOOP);

        if(owner==0)direction=1;
        else direction=-1;
    }

    public void Kill(){
        CurrentLife=-1;
        active=false;
    }
    public boolean canAttack(Unit target){
        if(target!=null)
        return true;
        return false;
    }
    public void attack(Unit target){
        if(canAttack(target))
        {
            target.hurt(damage);
        }
    }
    public Unit findTarget(){return null;}
    public boolean isActive(){return active;}

    public int getCurrentLife() {
        return CurrentLife;
    }

    public void hurt(int damage){
        if(vunerable)
        {
            CurrentLife-=damage;
            if(CurrentLife<=0)
                Kill();
        }
    }

    @Override
    public void draw(Batch batch) {
        if(walking)
        {
            if(jumping && JumpTime<0.7f)
            {
                JumpTime+=Gdx.graphics.getDeltaTime();
            }
            else
            {
                jumping=false;
                JumpTime=0;
            }
            move(Gdx.graphics.getDeltaTime(), direction);
            time+=Gdx.graphics.getDeltaTime();
            if(!jumping)
            batch.draw(walk.getKeyFrame(time), getX(), getY(),walk.getKeyFrame(time).getRegionWidth()*direction,walk.getKeyFrame(time).getRegionHeight());
            else
                batch.draw(jump.getKeyFrame(time),getX(),getY(),jump.getKeyFrame(time).getRegionWidth()*direction,jump.getKeyFrame(time).getRegionHeight());
        }
        else
        super.draw(batch);
    }
}
