package com.runic.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.runic.Assets;
import com.runic.CombatText;
import com.runic.Player;

import java.util.ArrayList;

/**
 * Created by Nothrim on 2015-10-01.
 */
public class EarthWorm extends BaseUnit {
    private class EarthWormPart
    {
        private EarthWormPart parent;
        private double rotation;
        private boolean immune=false;
        private float immuneTimer=0;
        private float positionX;
        private float positionY;
        private boolean active;
        private float dst;
        private int life=15;
        private Rectangle hitbox;
        public void hurt(int damage)
        {
            if(!immune) {
                life -= damage;
                immune=true;
            }
            if(life<=0)
                active=false;
        }
        public float getY() {
            return positionY;
        }
        public float getX() {
            return positionX;
        }
        private void translateX(float x)
        {
            positionX+=x;
        }
        private void translateY(float y)
        {
            positionY+=y;
        }


        public EarthWormPart(float positionX, float positionY,EarthWormPart parent) {
            this.positionX=positionX;
            this.positionY=positionY;
            this.parent=parent;
            hitbox=new Rectangle(positionX,positionY,12,12);
            active=true;
        }

        public void translate(Vector2 direction)
        {

            rotation=MathUtils.atan2(direction.y, direction.x);
            translateX(direction.x);
            translateY(direction.y);
            hitbox.x=getX();
            hitbox.y=getY();
        }
        public void translate(float x,float y)
        {
            rotation=MathUtils.atan2(y, x);
            translateX(x);
            translateY(y);
            hitbox.x=getX();
            hitbox.y=getY();
        }
        public void update(float deltaTime) {
            updateTimers(deltaTime);
            if(parent !=null ) {
                rotation = Math.atan2(parent.positionY - positionY, parent.positionX - positionX);

                dst=Vector2.dst(parent.getX(), parent.getY(), positionX, positionY);

                translate(new Vector2(parent.getX()-getX(),parent.getY()-getY()).nor().scl(dst>12?(dst-12):0+dst<10?0:speed*deltaTime));
                hitbox.x=getX();
                hitbox.y=getY();
            }
        }
        public void updateTimers(float deltaTime)
        {
            if(immune)
            {
                immuneTimer+=deltaTime;
                if(immuneTimer>0.5f)
                {
                    immune=false;
                    immuneTimer=0;
                }
            }
        }
        public void draw(Batch batch) {

            batch.draw(Assets.getInstance().projectiles.findRegion("EarthWormBody"), positionX, positionY, 8, 8, 16, 16, 1, 1, (float) rotation * MathUtils.radiansToDegrees);

        }
    }

    public Array<EarthWormPart> body;
    private EarthWormPart head;

    private boolean jumpAttack=false;
    private boolean attacking=false;
    private BaseUnit target;
    private float searchTimer=0;
    private float jumpPower=0;
    private boolean dir;
    private float targetDistance;
    private float hitboxShift;
    private Vector2 castleDirection;
    public EarthWorm(Player owner, float positionX,float positionY,int numberOfParts) {
        super(owner, TYPE.UNDERGROUND, 5, 30, 1, 45, positionX, positionY, 1);
        body=new Array<>(true,numberOfParts);
        body.add(new EarthWormPart(positionX+direction*16,positionY,null));
        for(int i=1;i<Math.max(1,numberOfParts);i++)
        {
            body.add(new EarthWormPart(body.get(i-1).positionX-direction*12,positionY,body.get(i-1)));
        }
        head=body.get(0);
        hitboxShift=numberOfParts*16;
        hitbox=new Rectangle(direction==-1?head.getX():head.getX()+hitboxShift,head.getY(),16*numberOfParts,16);
        castleDirection=new Vector2(owner.enemy.getCastle().getSpawnpoint(), owner.enemy.getCastle().getY()-25);
    }

    @Override
    public float getX() {
        return head.getX();
    }

    @Override
    public float getY() {
        return head.getY();
    }

    @Override
    public void resetLogic() {
        attacking=false;
        jumpAttack=false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        searchTimer+=deltaTime;
        hitbox.x=direction==-1?head.getX():head.getX()-hitboxShift;
        hitbox.y=head.getY();
        if (searchTimer>SEARCH_FREQUENCY)
        {
            searchTimer=0;
            target=findClosestTarget(TYPE.UNDERGROUND);

            if(target==null || !target.active)
            target=findTarget(TYPE.LAND);
        }
        if(target!=null && target.active) {
            if(target.getType()==TYPE.UNDERGROUND && !attacking)
            {
                if(Intersector.intersectRectangles(head.hitbox,target.getHitbox(),Rectangle.tmp))
                {
                    attacking=true;
                }
                head.translate((target.hitbox.getCenter(Vector2.X).sub(head.getX(), head.getY()).nor()).scl(speed * deltaTime));
            }
            else if(attacking)
            {
                target.hurt(2*damage);
                resetLogic();
            }
            if (target.getType() == TYPE.LAND && !jumpAttack) {
                if (collisionLayer.getCell((int) (head.getX() / 8), (int) (head.getY() / 8) + 1) != null)
                    head.translate((target.hitbox.getCenter(Vector2.X).sub(head.getX(), head.getY()).nor()).scl(speed * deltaTime));
                else {
                    jumpPower = 30;
                    jumpAttack = true;
                    dir = (target.getX() - head.getX()) > 0;
                }

            } else if (jumpAttack) {
                if (jumpPower > 0) {
                    head.translate(deltaTime * Math.max(jumpPower, speed) * (dir ? 1 : -1), speed * deltaTime);
                    jumpPower -= speed * deltaTime;
                } else {
                    head.translate((dir ? 1 : -1) * speed * deltaTime, -speed * deltaTime);
                }
                if (collisionLayer.getCell((int) (head.getX() / 8), (int) (head.getY() / 8) + 10) != null)
                    resetLogic();
                for (BaseUnit u : owner.enemy.getArmy()) {
                    if (u != null && u.active && u.getType() == TYPE.LAND) {
                        for (EarthWormPart p : body) {
                            if (p.active && Intersector.intersectRectangles(p.hitbox, u.getHitbox(), Rectangle.tmp)) {
                                u.hurt(damage);
                                p.hurt(1);
                            }
                        }
                    }
                }
            }
        }
        else if(castleDirection.dst(head.getX(),head.getY())>50) {
            head.translate((castleDirection.cpy().sub(head.getX(), head.getY()).nor()).scl(speed * deltaTime));
        }
        else
            owner.enemy.getCastle().damage(damage*2);


    }

    @Override
    public BaseUnit findTarget(TYPE type) {
        for(BaseUnit u:owner.enemy.getArmy())
        {
            if(u!=null && u.active && u.getType()==type)
            {
                return u;
            }
        }
        return null;
    }
    public BaseUnit findClosestTarget(TYPE type)
    {
        targetDistance=5000;
        float tempDst;
        for(BaseUnit u: owner.enemy.getArmy())
        {
            if(u!=null && u.active && u.getType()==type )
            {
                tempDst=Vector2.dst(u.getX(),u.getY(),head.getX(),head.getY());
                if(targetDistance>tempDst) {
                    targetDistance = tempDst;
                    target=u;
                }
            }
        }
        return target;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if(!head.active)
            this.active=false;
        batch.draw(Assets.getInstance().projectiles.findRegion("EarthWormHead"), head.positionX, head.positionY, 8, 8, 16, 16, 1, 1, (float) head.rotation * MathUtils.radiansToDegrees);
        head.updateTimers(Gdx.graphics.getDeltaTime());
        for(int i=1;i<body.size;i++)
        {
            if(body.get(i).active) {
                body.get(i).update(Gdx.graphics.getDeltaTime());
                body.get(i).draw(batch);
            }
            else
                return;

        }


    }


}
