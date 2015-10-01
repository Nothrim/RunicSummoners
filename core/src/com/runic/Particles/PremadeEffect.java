package com.runic.Particles;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.runic.Assets;

/**
 * Created by Nothrim on 2015-09-24.
 */
public class PremadeEffect {
    public static ParticleEffectPool EnergyExplosion;
    public static Array<ParticleEffectPool.PooledEffect> effects = new Array();
    public static void initialize()
    {
        EnergyExplosion=new ParticleEffectPool(Assets.getInstance().EnergyExplosion,10,200);
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
    public static void spawn(int type,float positionX,float positionY) {
        ParticleEffectPool.PooledEffect effect;
        switch (type)
        {
            case 0:
                effect=EnergyExplosion.obtain();
                break;
            default:
                effect=null;
        }
        if(effect!=null) {
            effect.setPosition(positionX, positionY);
            effects.add(effect);
        }
    }
}
