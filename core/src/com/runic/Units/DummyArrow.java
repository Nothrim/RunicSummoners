package com.runic.Units;

import com.badlogic.gdx.math.MathUtils;
import com.runic.Player;
import com.runic.Projectiles.BaseProjectile;

/**
 * Created by Nothrim on 2015-09-09.
 */
public class DummyArrow extends Dummy {
    public DummyArrow(float x, float y, Player owner, int life) {
        super(x, y, owner, life);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(life%5==0) {
            if (owner.whoAmI() == 0) {
                BaseProjectile.createProjectile(0, x, y, owner, MathUtils.random(4f, 9f), MathUtils.random(9f, 12f));
            } else {
                BaseProjectile.createProjectile(0, x, y, owner, MathUtils.random(-4f,-9f), MathUtils.random(9f, 12f));
            }
        }
    }
}
