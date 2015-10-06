package com.runic.Units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.runic.AnimationData;
import com.runic.Assets;
import com.runic.Player;

/**
 * Created by Nothrim on 2015-09-16.
 */
public class BloodKnight extends BaseUnit {
    public static final float MAX_GORE_ELEMENTS=7;
    public static final int FALL_SPEED=100;
    BaseUnit target;
    private boolean jumping=false;
    private float JumpTime;
    private float AttackTimer;
    private float searchTimer=0;
    private int direction;


    public BloodKnight(Player owner, float positionX, float positionY) {
        super(owner, TYPE.LAND, 5, 60, 3,MELEE_RANGE, 15, positionX, positionY, 1.35f, Assets.getInstance().BloodKnightAnimation, 8);
        if(owner.whoAmI()==0)
            direction=1;
        else
            direction=-1;
        walking=true;
        standing=false;
        hitbox.setWidth(walk.getKeyFrame(0).getRegionWidth()/2);
        attack.setFrameDuration(1f/7f);
        attack.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }


    @Override
    public void draw(Batch batch) {
        super.draw(batch);

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
    public boolean preHurt() {
        return super.preHurt();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        searchTimer+=deltaTime;
        if(jumping && JumpTime<0.7f)
        {
            JumpTime+= deltaTime;
        }
        else
        {
            jumping=false;
            JumpTime=0;
        }

        if(searchTimer>SEARCH_FREQUENCY)
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
                if(owner.enemy.getCastle().distance(this)<3*MELEE_RANGE)
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
            AttackTimer+=deltaTime;
        else
        {
            AttackTimer=0;
            attack(target);
        }
        move(deltaTime,direction);

    }

    @Override
    public void resetLogic() {
        super.resetLogic();
        jumping=false;
    }

    @Override
    public void move(float DeltaTime, int direction) {
        hitbox.y=getY();
        hitbox.x=getX()+hitbox.getWidth()*(direction==-1?1:0);
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
    public void preKill() {
        super.preKill();
        for(int i=0;i< MathUtils.random(1,MAX_GORE_ELEMENTS);i++)
        {
            Gore.newGore(getX(),getY(),1,Assets.getInstance().BloodKnightGore.getRandomGore(),MathUtils.random(-100, 100));
        }
    }

    @Override
    public void attack(BaseUnit target) {
        if(canAttack(target)) {
            target.hurt(damage);
        }
        else
            findTarget(TYPE.LAND);

    }
}
