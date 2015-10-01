package com.runic.Projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.runic.Assets;
import com.runic.Player;
import com.runic.Units.BaseUnit;

/**
 * Created by Nothrim on 2015-09-09.
 */
public class Arrow extends BaseProjectile {
    protected Arrow(float x, float y, Animation move, int damage, Player owner, float velocityX, float velocityY) {
        super(x, y, move, damage, owner);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        Assets.getInstance().ArrowFlying.play(0.09f);
    }

    private float rotation = 1;
    private int life = 1000;

    @Override
    public void update(float deltaTime) {
        if (life > 0)
            life -= deltaTime;
        else
            active = false;
        rotation = (float) Math.atan2((double) velocityY, (double) velocityX);
        if (ground != null) {
            if (ground.getCell((int) ((x + velocityX) / 8), (int) ((y + velocityY) / 8)) == null) {
                velocityY -= 10*deltaTime;
                x += velocityX;
                y += velocityY;

            } else {
                if (life > 10) {
                    switch (MathUtils.random(3))
                    {
                        case 0:
                            Assets.getInstance().ArrowGround.play(0.05f);
                            break;
                        case 1:
                            Assets.getInstance().ArrowGround2.play(0.05f);
                            break;
                        default:
                            Assets.getInstance().ArrowGround3.play(0.05f);
                    }

                    life = 10;
                }
            }
        }
        unitCollision();


    }

    private void unitCollision()
    {
        for (BaseUnit u : owner.enemy.getArmy()) {
            if (u != null && u.isActive() && !u.isImmune()) {
                if (Intersector.intersectRectangles(u.getHitbox(),new Rectangle(x,y,8,8),Rectangle.tmp)) {
                    u.hurt(damage);
                    Assets.getInstance().ArrowFlesh.play(0.09f);
                    life = 0;
                    return;
                }
            }
        }
    }
    @Override
    public void draw(SpriteBatch sb, float deltaTime) {
        super.draw(sb, deltaTime);
        sb.draw(move.getKeyFrame(0), x, y, 8, 8, 16, 16, 1, 1, MathUtils.radiansToDegrees*rotation+32,true);
    }
}
