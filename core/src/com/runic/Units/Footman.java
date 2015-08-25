package com.runic.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.runic.AnimationData;
import com.runic.Player;

/**
 * Created by Nothrim on 2015-08-20.
 */
public class Footman extends BaseUnit {
    BaseUnit target;
    private boolean jumping=false;
    public static final int FALL_SPEED=100;
    private float JumpTime;
    private float AttackTimer;
    private float searchTimer=0;
    private int direction;
    public Footman(Player owner,TYPE type,int gold, int MaxHealth, int damage,float AttackRange, int speed, float positionX, float positionY, float AttackSpeed, AnimationData animationData, float AnimationSpeed) {
        super(owner,type, gold, MaxHealth, damage,AttackRange, speed, positionX, positionY, AttackSpeed, animationData, AnimationSpeed);
        if(owner.whoAmI()==0)
            direction=1;
        else
            direction=-1;
        walking=true;
        standing=false;
    }

    @Override
    public void draw(Batch batch) {
        time+=Gdx.graphics.getDeltaTime();
        update();
        if(walking)
        {
            if(!jumping)
                batch.draw(walk.getKeyFrame(time), getX(), getY(),walk.getKeyFrame(time).getRegionWidth()*direction,walk.getKeyFrame(time).getRegionHeight());
            else
                batch.draw(jump.getKeyFrame(time),getX(),getY(),jump.getKeyFrame(time).getRegionWidth()*direction,jump.getKeyFrame(time).getRegionHeight());
        }
        else if(attacking)
        {
            batch.draw(attack.getKeyFrame(time),getX(),getY(),attack.getKeyFrame(time).getRegionWidth()*direction,attack.getKeyFrame(time).getRegionHeight());
        }
        else
            batch.draw(stance.getKeyFrame(time),getX(),getY(),stance.getKeyFrame(time).getRegionWidth()*direction,stance.getKeyFrame(time).getRegionHeight());

    }

    @Override
    public void move(float DeltaTime, int direction) {
        //to rearrange tomorrow

        if(collisionLayer.getCell(getWorldX(8)+direction,getWorldY(8))==null)
        {
            if(!jumping)
                this.translateY(FALL_SPEED * -DeltaTime);
            else
            {
                if(collisionLayer.getCell(getWorldX(8)+direction,getWorldY(8)-1)==null)
                    this.translateY(10 * (-DeltaTime) - JumpTime * DeltaTime * 10);
                else {
                    jumping=false;
                    this.translateY(10 * -DeltaTime);
                }
            }
        }
        if(!attacking && !standing) {
            if (collisionLayer.getCell(getWorldX(8) + 2 * direction, getWorldY(8) + 1) == null)
                this.translateX(speed * direction * DeltaTime);
            else {
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
            if (Math.abs(getX() - target.getX()) < MELEE_RANGE-10) {
                translateX((-direction * Math.abs(getX() - target.getX()))/8);
            }
            target.hurt(damage);
        }

    }

    @Override
    public void resetLogic() {
        jumping=false;
        super.resetLogic();
    }

    private void update()
    {
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
            target= findTarget(TYPE.LAND);
            if(target!=null)
            {
                resetLogic();
                attacking=true;
            }
            else
            {
                if(owner.enemy.getCastle().distance(this)<MELEE_RANGE)
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
}
