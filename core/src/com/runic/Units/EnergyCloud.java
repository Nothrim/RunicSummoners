package com.runic.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.runic.AnimationData;
import com.runic.Assets;
import com.runic.Effects.Line;
import com.runic.Effects.SpriteEffect;
import com.runic.Effects.SpriteLighting;
import com.runic.Particles.PremadeEffect;
import com.runic.Player;

/**
 * Created by Nothrim on 2015-09-19.
 */
public class EnergyCloud extends BaseUnit {
    public static final int CLOUD_MAX_GORE=3;
    public static final Vector2 MAX_RANGE=new Vector2(100,1200);
    public static final int AOE_RANGE=85;
    private float energy;
    private BaseUnit target;
    private Rectangle range;
    private Rectangle AoE;
    private float searchTimer=0;
    private float attackTimer;
    public EnergyCloud(Player owner, float positionX, float positionY) {
        super(owner, TYPE.AIR, 3, 25, 5, 0, 60, positionX, positionY, 2, Assets.getInstance().EnergyCloudAnimation, 6);
        energy=100;
        range=new Rectangle(getX(),getY(),MAX_RANGE.x*2,MAX_RANGE.y);
        AoE= new Rectangle(getX(),getY(),AOE_RANGE,AOE_RANGE);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }
    private void dealAreaDamage(int damage)
    {
        if(target!=null) {
            AoE.setCenter(target.getX(),target.getY());
            energy-=damage;
            PremadeEffect.spawn(0,target.getX(),target.getY()-10);
            SpriteEffect.create(new SpriteLighting(new Vector2(getX()+32,getY()),target,0.33f));
            //Line.create(new Vector2(getX(),getY()),new Vector2(target.getX(),target.getY()),1); lines are lame
            for (BaseUnit u : owner.enemy.getArmy())
            {
                intersection.setWidth(0);
                intersection.setHeight(0);
                if(u!=null && u.active && Intersector.intersectRectangles(AoE,u.getHitbox(),intersection))
                {
                    if(intersection.getWidth()>5 || intersection.getHeight()>5)
                    {
                        u.hurt(damage);
                    }
                }
            }
        }
    }

    @Override
    public void move(float DeltaTime, int direction) {
        super.move(DeltaTime, direction);
//        if(direction==-1)
//            range.setX(getX()-MAX_RANGE.x);
//        else
//            range.setX(getX());
        range.setX(getX()-MAX_RANGE.x);
        range.setY(getY() - MAX_RANGE.y);
        translateX(direction*speed*DeltaTime);
    }


    @Override
    public void resetLogic() {
        super.resetLogic();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if(walking)
        {

                batch.draw(walk.getKeyFrame(time).getTexture(),getX(),getY(),walk.getKeyFrame(time).getRegionWidth(),walk.getKeyFrame(time).getRegionHeight(),walk.getKeyFrame(time).getRegionX(),walk.getKeyFrame(time).getRegionY(),walk.getKeyFrame(time).getRegionWidth(),walk.getKeyFrame(time).getRegionHeight(),direction==-1,false);
        }
        else if(attacking)
        {
            batch.draw(attack.getKeyFrame(time).getTexture(),getX(),getY(),attack.getKeyFrame(time).getRegionWidth(),attack.getKeyFrame(time).getRegionHeight(),attack.getKeyFrame(time).getRegionX(),attack.getKeyFrame(time).getRegionY(),attack.getKeyFrame(time).getRegionWidth(),attack.getKeyFrame(time).getRegionHeight(),direction==-1,false);
        }
        else
            batch.draw(stance.getKeyFrame(time).getTexture(),getX(),getY(),stance.getKeyFrame(time).getRegionWidth(),stance.getKeyFrame(time).getRegionHeight(),stance.getKeyFrame(time).getRegionX(),stance.getKeyFrame(time).getRegionY(),stance.getKeyFrame(time).getRegionWidth(),stance.getKeyFrame(time).getRegionHeight(),direction==-1,false);
    }

    @Override
    public BaseUnit findTarget(TYPE type) {

        for(BaseUnit u: owner.enemy.getArmy())
        {
            if(u!=null && u.active && u.getType()!=TYPE.UNDERGROUND && Intersector.intersectRectangles(range,u.getHitbox(),intersection))
            {
                if(intersection.getWidth()>10)
                {
                    return u;
                }
            }
        }
        return null;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        resetLogic();
        energy-=deltaTime;
        searchTimer+=deltaTime;
        if(energy<0) {
            preKill();
            Kill();
        }
        if(searchTimer>SEARCH_FREQUENCY)
        {
            searchTimer=0;
            target=findTarget(TYPE.LAND);
        }
        if(target!=null && target.active)
        {
            attacking=true;
            attackTimer+=deltaTime;
            if(attackTimer>AttackSpeed)
            {
                attackTimer=0;
                dealAreaDamage(damage);
            }
        }
        else
        {
            walking=true;
            move(deltaTime,direction);
            destroyCastle();
        }


    }

    @Override
    public void attack(BaseUnit target) {
        super.attack(target);
    }

    @Override
    public boolean canAttack(BaseUnit target) {
        return super.canAttack(target);
    }

    @Override
    public void preKill() {
        super.preKill();
        for(int i=0;i<MathUtils.random(1,CLOUD_MAX_GORE);i++)
        Gore.newGore(getX(),getY(),90,Assets.getInstance().EnergyCloudGore.getRandomGore(), 10*MathUtils.random(-100,100));
    }
    private void destroyCastle()
    {
        if(Math.abs(getX()-owner.enemy.getCastle().getSpawnpoint())<10)
        {
            owner.enemy.getCastle().damage((int)energy);
            preKill();
            Kill();
        }
    }
}
