package com.runic.Utils;

/**
 * Created by Nothrim on 2015-09-17.
 */
public class SmoothChangingFloat {
    private float range0;
    private float range1;
    private boolean hitBoundry;
    float value;

    public SmoothChangingFloat(float range0,float range1)
    {
        this.range0=range0;
        this.range1=range1;
        value=range1;
        hitBoundry=false;
    }
    public SmoothChangingFloat(float range0,float range1,boolean startFromBegining)
    {
        this.range0=range0;
        this.range1=range1;
        if(!startFromBegining)
        value=range1;
        else
        value=range0;
        hitBoundry=false;
    }
    public float getValue(float deltaTime)
    {
        if(!hitBoundry &&value >range0)
            value-=deltaTime;
        else
        {
            hitBoundry=true;
        }
        if(hitBoundry) {
            value += deltaTime;
            if(value>range1)
                hitBoundry=false;
        }
        return value;
    }


}
