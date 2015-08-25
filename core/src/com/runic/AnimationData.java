package com.runic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;



/**
 * Created by Nothrim on 2015-08-20.
 */
public class AnimationData {
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
            if(r.toString().contains("walk"))cwalk++;
            if(r.toString().contains("attack"))cattack++;
            if(r.toString().contains("stance"))cstance++;
            if(r.toString().contains("jump"))cjump++;

        }
        walk=new Array<>(true,cwalk);
        attack=new Array<>(true,cattack);
        stance=new Array<>(true,cstance);
        jump= new Array<>(true,cjump);
        for(TextureAtlas.AtlasRegion r : atlas.getRegions())
        {
            if(r.toString().contains("walk"))
            {
                walk.add(r);
            }
            if(r.toString().contains("attack"))
            {
               attack.add(r);
            }
            if(r.toString().contains("stance"))
            {
               stance.add(r);
            }
            if(r.toString().contains("jump"))
            {
                jump.add(r);
            }

        }
    }

}
