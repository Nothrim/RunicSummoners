package com.runic.Units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.runic.Assets;
import com.runic.Player;

import java.util.ArrayList;

/**
 * Created by Nothrim on 2015-10-01.
 */
public class EarthWorm extends BaseUnit {
    private class EarthWormPart extends BaseUnit
    {
        private float rotation=0;
        public EarthWormPart(Player owner,float positionX, float positionY) {
            super(owner, TYPE.UNDERGROUND, 1, 15, 1, 15, positionX, positionY, 1);
        }

        @Override
        public void draw(Batch batch) {
            super.draw(batch);
            batch.draw(Assets.getInstance().projectiles.findRegion("EarthWormBody"),positionX,positionY,8,8,16,16,1,1,rotation* MathUtils.radiansToDegrees);
        }
    }
    private float rotation;
    private Array<EarthWormPart> body;
    public EarthWorm(Player owner, float positionX,float positionY,int numberOfParts) {
        super(owner, TYPE.UNDERGROUND, 5, 30, 5, 15, positionX, positionY, 1);
        body=new Array<>(true,numberOfParts);
        for(int i=0;i<numberOfParts;i++)
        {
            body.add(new EarthWormPart(owner,positionX+direction*16,positionY));
        }
    }

    @Override
    public void resetLogic() {
        super.resetLogic();
    }

    @Override
    public BaseUnit findTarget(TYPE type) {
        return super.findTarget(type);
    }

    @Override
    public void preKill() {
        super.preKill();
    }

    @Override
    public boolean preHurt() {
        return super.preHurt();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void attackCastle() {
        super.attackCastle();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        batch.draw(Assets.getInstance().projectiles.findRegion("EarthWormHead"),positionX,positionY,8,8,16,16,1,1,rotation*MathUtils.radiansToDegrees);
        for(BaseUnit b:body)
        {
            if(b.active)
            b.draw(batch);
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
    public void Kill() {
        super.Kill();
    }

    @Override
    public void move(float DeltaTime, int direction) {
        super.move(DeltaTime, direction);
    }
}
