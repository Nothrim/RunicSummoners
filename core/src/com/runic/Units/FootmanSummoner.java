package com.runic.Units;

import com.runic.Player;

/**
 * Created by Nothrim on 2015-09-17.
 */
public class FootmanSummoner extends Dummy {
    public FootmanSummoner(float x, float y, Player owner, int life) {
        super(x, y, owner, life);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(life%60==0)
            owner.spawnUnit(new Footman(owner,x,y));
    }
}
