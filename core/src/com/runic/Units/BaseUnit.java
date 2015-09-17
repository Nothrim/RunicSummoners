package com.runic.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.runic.AnimationData;
import com.runic.Assets;
import com.runic.CombatText;
import com.runic.Player;

/**
 * Created by Nothrim on 2015-08-20.
 */
public class BaseUnit {
    //basic variables
    Player owner;
    public final static float MELEE_RANGE=10;
    public final static float IMMUNE_TIME=0.35f;
    public enum TYPE {LAND,AIR,UNDERGROUND}
    protected static TiledMapTileLayer collisionLayer;
    public static void setCollisionLayer(TiledMapTileLayer layer){collisionLayer=layer;}
    protected float time=0;
    protected Animation walk;
    protected Animation jump;
    protected Animation stance;
    protected Animation attack;
    protected int gold;
    protected int health;
    protected int MaxHealth;
    protected int damage;
    protected int speed;
    private TYPE type;
    protected float positionX;
    protected float positionY;
    protected float AttackSpeed;
    protected float AnimationSpeed;
    protected float AttackRange;
    protected void translateX(float x){positionX+=x;}
    protected void translateY(float y){positionY+=y;}
    protected Rectangle hitbox;
    protected float immuneTimer;
    protected int direction;
    protected Rectangle intersection;
    //logic variables
    protected boolean active=true;
    protected boolean attacking=false;
    protected boolean walking=false;
    protected boolean standing=true;
    protected boolean immune=false;
    public float getX(){return positionX;}
    public float getY(){return positionY;}
    public int getWorldX(float scale){return (int)(hitbox.getCenter(Vector2.Zero).x/scale);}
    public int getWorldY(float scale){return (int)(positionY/scale);}
    public TYPE getType(){return type;}
    //particles
    public static boolean initialized=false;
    public static ParticleEffectPool BloodParticles;
    public static Array<ParticleEffectPool.PooledEffect> effects = new Array();
    public static void initialize()
    {
        BloodParticles=new ParticleEffectPool(Assets.getInstance().Blood,10,500);
        initialized=true;
    }
    public static void drawParticles(SpriteBatch sb,float DeltaTime)
    {
        for (int i = effects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.draw(sb, DeltaTime);
            if (effect.isComplete()) {
                effect.free();
                effects.removeIndex(i);
            }
        }

    }
    public void resetLogic()
    {
        attacking=false;
        walking=false;
        standing=false;

    }
    public BaseUnit(Player owner,TYPE type,int gold,int MaxHealth,int damage,float AttackRange,int speed,float positionX,float positionY,float AttackSpeed,AnimationData animationData,float AnimationSpeed)
    {
        this.owner=owner;
        this.type=type;
        this.gold=gold;
        this.MaxHealth=MaxHealth;
        this.health=MaxHealth;
        this.damage=damage;
        this.AttackRange=AttackRange;
        this.speed=speed;
        this.positionX=positionX;
        this.positionY=positionY;
        this.AttackSpeed=AttackSpeed;
        this.AnimationSpeed=AnimationSpeed;
        walk=new Animation(1/AnimationSpeed,animationData.walk, Animation.PlayMode.LOOP);
        jump=new Animation(1/AnimationSpeed,animationData.jump);
        stance=new Animation(1/AnimationSpeed,animationData.stance, Animation.PlayMode.LOOP);
        attack=new Animation(1/AnimationSpeed,animationData.attack, Animation.PlayMode.LOOP);
        if(!initialized)
        {
            initialize();
        }
        hitbox=new Rectangle(positionX,positionY,walk.getKeyFrame(0).getRegionWidth(),walk.getKeyFrame(0).getRegionHeight());
        immuneTimer=0;
        if(owner.whoAmI()==0)
            direction=1;
        else
            direction=-1;
        intersection=new Rectangle();

    }


    public void move(float DeltaTime,int direction)
    {
        hitbox.x=positionX;
        hitbox.y=positionY;
    }


    public Rectangle getHitbox(){return hitbox;}
    public boolean isImmune(){return immune;}

    public void Kill(){
        health=-1;
        active=false;
    }
    public boolean canAttack(BaseUnit target){
        if(target!=null && target.active)
            return true;
        return false;
    }
    public void attack( BaseUnit target){
        if(canAttack(target))
        {

            target.hurt(damage);
        }
    }
    public boolean isActive(){return active;}


    public void hurt(int damage){
        if(preHurt())
        {
            immune=true;
            int DamageDealt=MathUtils.random(damage / 2, damage);
            health-=DamageDealt;
            if(owner.whoAmI()==1) {
                CombatText.create(Integer.toString(DamageDealt), getX() + MathUtils.random(-10, 10), getY() + MathUtils.random(30, 50),0.5f,0);
            }
            else
                CombatText.create(Integer.toString(DamageDealt), getX() + MathUtils.random(-10, 10), getY() + MathUtils.random(30, 50),0,0.5f);
            ParticleEffectPool.PooledEffect effect = BloodParticles.obtain();
            effect.setPosition(positionX,positionY);
            effects.add(effect);
            if(health<=0) {
                preKill();
                Kill();
            }
        }
    }

    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
    }
    public BaseUnit findTarget(TYPE type){
        intersection=new Rectangle();
            for (BaseUnit u : owner.enemy.getArmy()) {
                if (u!=null && u.active && u.getType() == type ) {
                    Intersector.intersectRectangles(u.getHitbox(), hitbox, intersection);
                    if(intersection.getWidth()>MELEE_RANGE)
                    return u;
                }
            }
        return null;
    }
    public void attackCastle(){
        owner.enemy.getCastle().damage(damage);
    }
    public void preKill()
    {

    }
    public boolean preHurt()
    {
        if(immune)return false;
        else
            return true;
    }
    public void update(float deltaTime)
    {
        time+=deltaTime;
        if(immune) {
            if (immuneTimer < IMMUNE_TIME)
                immuneTimer += deltaTime;
            else {
                immuneTimer = 0;
                immune = false;
            }
        }
    }
}
