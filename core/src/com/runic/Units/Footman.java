package com.runic.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.runic.AnimationData;
import com.runic.Assets;
import com.runic.Player;

/**
 * Created by Nothrim on 2015-08-20.
 */
public class Footman extends BaseUnit {
    public static final int FALL_SPEED=100;
    public static final int MAX_GORE_PARTS=4;
    BaseUnit target;
    private boolean jumping=false;
    private float JumpTime;
    private float AttackTimer;
    private float searchTimer=0;
    public Footman(Player owner,TYPE type,int gold, int MaxHealth, int damage,float AttackRange, int speed, float positionX, float positionY, float AttackSpeed, AnimationData animationData, float AnimationSpeed) {
        super(owner,type, gold, MaxHealth, damage,AttackRange, speed, positionX, positionY, AttackSpeed, animationData, AnimationSpeed);
        walking=true;
        standing=false;

    }
    public Footman(Player owner  ,float positionX, float positionY) {
        super(owner,TYPE.LAND, 5, 10, 5,MELEE_RANGE, 30, positionX, positionY, 1, Assets.getInstance().FootmanAnimation, 8);
        walking=true;
        standing=false;

    }

    @Override
    public void draw(Batch batch) {
        update();
        if(walking)
        {
            if(!jumping)
            {
                batch.draw(walk.getKeyFrame(time).getTexture(),getX(),getY(),walk.getKeyFrame(time).getRegionWidth(),walk.getKeyFrame(time).getRegionHeight(),walk.getKeyFrame(time).getRegionX(),walk.getKeyFrame(time).getRegionY(),walk.getKeyFrame(time).getRegionWidth(),walk.getKeyFrame(time).getRegionHeight(),direction==-1,false);
            }
            else
                batch.draw(jump.getKeyFrame(time).getTexture(),getX(),getY(),jump.getKeyFrame(time).getRegionWidth(),jump.getKeyFrame(time).getRegionHeight(),jump.getKeyFrame(time).getRegionX(),jump.getKeyFrame(time).getRegionY(),jump.getKeyFrame(time).getRegionWidth(),jump.getKeyFrame(time).getRegionHeight(),direction==-1,false);
        }
        else if(attacking)
        {
            batch.draw(attack.getKeyFrame(time).getTexture(),getX(),getY(),attack.getKeyFrame(time).getRegionWidth(),attack.getKeyFrame(time).getRegionHeight(),attack.getKeyFrame(time).getRegionX(),attack.getKeyFrame(time).getRegionY(),attack.getKeyFrame(time).getRegionWidth(),attack.getKeyFrame(time).getRegionHeight(),direction==-1,false);
        }
        else
            batch.draw(stance.getKeyFrame(time).getTexture(),getX(),getY(),stance.getKeyFrame(time).getRegionWidth(),stance.getKeyFrame(time).getRegionHeight(),stance.getKeyFrame(time).getRegionX(),stance.getKeyFrame(time).getRegionY(),stance.getKeyFrame(time).getRegionWidth(),stance.getKeyFrame(time).getRegionHeight(),direction==-1,false);


    }

    @Override
    public void move(float DeltaTime, int direction) {
        super.move(DeltaTime, direction);
        //to rearrange tomorrow
        if(collisionLayer.getCell(getWorldX(8),getWorldY(8))==null)
        {
            if(!jumping)
                this.translateY(FALL_SPEED * -DeltaTime);
            else
            {
                if(collisionLayer.getCell(getWorldX(8)+direction,getWorldY(8))==null)
                {
                    this.translateY(10 * (-DeltaTime) - JumpTime * DeltaTime * 10);
                }
                else {
                    jumping=false;
                    this.translateY(10 * -DeltaTime);
                }
            }
        }
        if(!attacking && !standing) {
            if (collisionLayer.getCell(getWorldX(8) , getWorldY(8)+1) == null)
            {
                this.translateX(speed * direction * DeltaTime);
            }
            else if(target==null){
                jumping = true;
                this.translateY(Math.min(200 * speed, 1000) * DeltaTime);
            }
        }
    }

    @Override
    public boolean canAttack(BaseUnit target) {
        return super.canAttack(target);
    }

    @Override
    public void attack(BaseUnit target) {
        if(canAttack(target)) {
            target.hurt(damage);
        }
        else
            findTarget(TYPE.LAND);


    }

    @Override
    public void resetLogic() {
        jumping=false;
        super.resetLogic();
    }

    private void update()
    {
        super.update(Gdx.graphics.getDeltaTime());
        searchTimer+=Gdx.graphics.getDeltaTime();
        if(jumping && JumpTime<0.7f)
        {
            JumpTime+= Gdx.graphics.getDeltaTime();
        }
        else
        {
            jumping=false;
            JumpTime=0;
        }

        if(searchTimer>1)
        {

            searchTimer=0;
            if(target==null)
                target= findTarget(TYPE.LAND);
            else if(!target.active)
                target= findTarget(TYPE.LAND);
            if(target!=null)
            {
                resetLogic();
                attacking=true;
            }
            else
            {
                if(owner.enemy.getCastle().distance(this)<60)
                {
                    attackCastle();
                    resetLogic();
                    attacking=true;
                }
                else
                {
                    resetLogic();
                    walking = true;
                }
            }
        }
        if(AttackTimer<AttackSpeed)
            AttackTimer+=Gdx.graphics.getDeltaTime();
        else
        {
            AttackTimer=0;
            attack(target);
        }
        move(Gdx.graphics.getDeltaTime(), direction);
    }

    @Override
    public void preKill() {
        for(int i=0;i< MathUtils.random(1,MAX_GORE_PARTS);i++)
        {
            Gore.newGore(getX(),getY(),1,Assets.getInstance().FootmanGore.gore.get(MathUtils.random(0,Assets.getInstance().FootmanGore.gore.size-1)),MathUtils.random(-100, 100));
        }
    }
}
