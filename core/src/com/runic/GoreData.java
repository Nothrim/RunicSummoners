package com.runic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Nothrim on 2015-08-28.
 */
public class GoreData {
    public static final String SEARCH_KEY="gore";
    public Array<TextureRegion> gore;

    public GoreData(TextureAtlas atlas){
        int cgore=0;

        for(TextureAtlas.AtlasRegion r : atlas.getRegions())
        {
            if(r.toString().contains(SEARCH_KEY))cgore++;
        }
        if(cgore>0) {
            gore = new Array<>(true, cgore);
            for (TextureAtlas.AtlasRegion r : atlas.getRegions()) {
                if (r.toString().contains(SEARCH_KEY)) {
                    gore.add(r);
                }
            }
        }
        else
            gore=null;
    }
    public TextureRegion getRandomGore()
    {
        return gore.random();
    }
}
