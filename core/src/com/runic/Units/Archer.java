package com.runic.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.runic.AnimationData;
import com.runic.Assets;
import com.runic.Player;
import com.runic.Projectiles.BaseProjectile;

/**
 * Created by Nothrim on 2015-09-18.
 */
public class Archer extends BaseUnit {
    public static final int FALL_SPEED = 100;
    public static final float SHOOTING_RADIUS=1000;
    public static final float HALF_OF_SHOOTING_RADIUS=SHOOTING_RADIUS/2;
    public static final int MAX_GORE_PARTS = 4;
    public static final int MAX_ARROWS=8;
    BaseUnit target;
    private boolean jumping = false;
    private float JumpTime = 0;
    private float AttackTimer = 0;
    private float searchTimer = 0;
    private int arrows;
    private float shootingTimer=0;
    private boolean shooting;
    private Rectangle range;

    public Archer(Player owner, float positionX, float positionY) {
        super(owner, TYPE.LAND, 4, 5, 1, 8, 18, positionX, positionY, 1, Assets.getInstance().ArcherAnimation, 8);
        arrows=MAX_ARROWS;
        resetLogic();
        walking=true;
        range=new Rectangle(positionX,positionY-HALF_OF_SHOOTING_RADIUS,SHOOTING_RADIUS,SHOOTING_RADIUS);
    }

    @Override
    public BaseUnit findTarget(TYPE type) {
        intersection=new Rectangle();
        for (BaseUnit u : owner.enemy.getArmy()) {
            if (u!=null && u.active && u.getType() == type ) {
                Intersector.intersectRectangles(u.getHitbox(), hitbox, intersection);
                if(intersection.getWidth()>1)
                    return u;
            }
        }
        return null;
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
        else if(attacking || shooting)
        {
            batch.draw(attack.getKeyFrame(time).getTexture(),getX(),getY(),attack.getKeyFrame(time).getRegionWidth(),attack.getKeyFrame(time).getRegionHeight(),attack.getKeyFrame(time).getRegionX(),attack.getKeyFrame(time).getRegionY(),attack.getKeyFrame(time).getRegionWidth(),attack.getKeyFrame(time).getRegionHeight(),direction==-1,false);
        }
        else
            batch.draw(stance.getKeyFrame(time).getTexture(),getX(),getY(),stance.getKeyFrame(time).getRegionWidth(),stance.getKeyFrame(time).getRegionHeight(),stance.getKeyFrame(time).getRegionX(),stance.getKeyFrame(time).getRegionY(),stance.getKeyFrame(time).getRegionWidth(),stance.getKeyFrame(time).getRegionHeight(),direction==-1,false);
    }



    @Override
    public void resetLogic() {
        super.resetLogic();
        jumping=false;
        shooting=false;
    }

    @Override
    public void move(float DeltaTime, int direction) {
        super.move(DeltaTime, direction);
        if (collisionLayer.getCell(getWorldX(8), getWorldY(8)) == null) {
            if (!jumping)
                this.translateY(FALL_SPEED * -DeltaTime);
            else {
                if (collisionLayer.getCell(getWorldX(8) + direction, getWorldY(8)) == null) {
                    this.translateY(10 * (-DeltaTime) - JumpTime * DeltaTime * 10);
                } else {
                    jumping = false;
                    this.translateY(10 * -DeltaTime);
                }
            }
        }
        if (!attacking && !standing && !shooting) {
            if (collisionLayer.getCell(getWorldX(8), getWorldY(8) + 1) == null) {
                this.translateX(speed * direction * DeltaTime);
            } else if (target == null) {
                jumping = true;
                this.translateY(Math.min(200 * speed, 1000) * DeltaTime);
            }
        }
        if(direction==-1)
       range.setX(getX()-SHOOTING_RADIUS);
        else
        range.setX(getX());
        range.setY(getY()-HALF_OF_SHOOTING_RADIUS);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        searchTimer += deltaTime;
        if (jumping && JumpTime < 0.7f) {
            JumpTime += deltaTime;
        } else {
            jumping = false;
            JumpTime = 0;
        }
        if(searchTimer>SEARCH_FREQUENCY ) {
            searchTimer=0;
            target=searchRadius();
            if (target !=null && target.active && target.getType()!= TYPE.UNDERGROUND && arrows > 0 && collisionLayer.getCell(getWorldX(8)+direction, getWorldY(8)+1) == null) {
                resetLogic();
                shooting=true;
            }
            else
            {
                target=findTarget(TYPE.LAND);
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
        }
        if(shooting)
        {
            if(shootingTimer<1)
            {
                shootingTimer+=deltaTime;
            }
            else
            {
                shootingTimer=0;
                Vector2 velocity=((target.getHitbox().getCenter(Vector2.Y).sub(hitbox.getCenter(Vector2.X)))).nor();

                if(target.getType()==TYPE.LAND)
                BaseProjectile.createProjectile(0, getX(), getY(), owner, velocity.x*Math.abs(getX()-target.getX())/MathUtils.random(90,100),Math.max(8,-velocity.y*10) );
                else
                {
                    velocity.scl(25);
                    BaseProjectile.createProjectile(0, getX(), getY(), owner, velocity.x, velocity.y + 3.5f);
                }
                arrows--;
            }
        }
        else
        {
            if(AttackTimer<AttackSpeed)
                AttackTimer+=deltaTime;
            else
            {
                AttackTimer=0;
                attack(target);
            }
        }


        move(deltaTime, direction);
    }
    private BaseUnit searchRadius()
    {
        for(BaseUnit u : owner.enemy.getArmy())
        {
            if(u!=null && u.active&& u.getType()!=TYPE.UNDERGROUND  )
            {
                if(Intersector.intersectRectangles(range,u.getHitbox(),Rectangle.tmp)) {
                    return u;
                }
            }



        }
        return null;
    }

    @Override
    public void preKill() {
        super.preKill();
        for(int i=0;i< MathUtils.random(2,MAX_GORE_PARTS);i++)
            Gore.newGore(getX(),getY(),1,Assets.getInstance().ArcherGore.getRandomGore(),MathUtils.random(-100, 100));
    }
}