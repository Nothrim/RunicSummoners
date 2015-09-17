package com.runic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;



/**
 * Created by Nothrim on 2015-08-20.
 */
public class AnimationData {
    public static final String WALK_KEY="walk";
    public static final String ATTACK_KEY="attack";
    public static final String STANCE_KEY="stance";
    public static final String JUMP_KEY="jump";
    public Array<TextureRegion>walk;
    public Array<TextureRegion>attack;
    public Array<TextureRegion>stance;
    public Array<TextureRegion>jump;
    public AnimationData(TextureAtlas atlas){
        int cwalk=0;
        int cattack=0;
        int cstance=0;
        int cjump=0;
        for(TextureAtlas.AtlasRegion r : atlas.getRegions())
        {
            if(r.toString().contains(WALK_KEY))cwalk++;
            if(r.toString().contains(ATTACK_KEY))cattack++;
            if(r.toString().contains(STANCE_KEY))cstance++;
            if(r.toString().contains(JUMP_KEY))cjump++;

        }
        walk=new Array<>(true,cwalk);
        attack=new Array<>(true,cattack);
        stance=new Array<>(true,cstance);
        jump= new Array<>(true,cjump);
        for(TextureAtlas.AtlasRegion r : atlas.getRegions())
        {
            if(r.toString().contains(WALK_KEY))
            {
                walk.add(r);
            }
            if(r.toString().contains(ATTACK_KEY))
            {
               attack.add(r);
            }
            if(r.toString().contains(STANCE_KEY))
            {
               stance.add(r);
            }
            if(r.toString().contains(JUMP_KEY))
            {
                jump.add(r);
            }

        }
    }

}
