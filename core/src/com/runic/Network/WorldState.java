package com.runic.Network;

import android.annotation.TargetApi;
import android.os.Build;
import com.badlogic.gdx.math.Vector2;
import com.runic.Projectiles.BaseProjectile;
import com.runic.Units.BaseUnit;
import com.runic.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Nothrim on 2015-10-06.
 */
public class WorldState {

    public float timestamp;
    public void interpolate(WorldState previousState,float alpha)
    {
        for(int i=0;i<p1.size();i++)
        {
            UnitData u=p1.get(i);
            if(i<previousState.p1.size() && p1.get(i)!=null )
                World.getInstance().p1.getArmy()[u.id].interpolate(alpha, u.position,previousState.p1.get(i).position);
        }
        for(int i=0;i<p2.size();i++)
        {
            UnitData u=p2.get(i);
            if(i<previousState.p2.size() && p2.get(i)!=null )
            World.getInstance().p2.getArmy()[u.id].interpolate(alpha, u.position,previousState.p2.get(i).position);
        }
    }
    public void loadBoolData()
    {
        for(int i=0;i<p2.size();i++) {
            UnitData u = p2.get(i);
            World.getInstance().p2.getArmy()[u.id].setAttacking(u.attacking);
            World.getInstance().p2.getArmy()[u.id].setWalking(u.walking);
        }
        for(int i=0;i<p1.size();i++)
        {
            UnitData u=p1.get(i);
            World.getInstance().p1.getArmy()[u.id].setAttacking(u.attacking);
            World.getInstance().p1.getArmy()[u.id].setWalking(u.walking);
        }
    }
    ArrayList<UnitData>p1;
    ArrayList<UnitData>p2;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public WorldState(float timestamp)
    {
        this.timestamp=timestamp;
        p1=new ArrayList<>();
        p2=new ArrayList<>();
        for(int i=0;i<World.getInstance().p1.getArmy().length;i++)
        {
            BaseUnit u=World.getInstance().p1.getArmy()[i];
            if(u!=null && u.isActive())
            {
                p1.add(new UnitData(new Vector2(u.getX(),u.getY()),i,u.isWalking(),u.isAttacking()));
            }
        }
        for(int i=0;i<World.getInstance().p2.getArmy().length;i++)
        {
            BaseUnit u=World.getInstance().p2.getArmy()[i];
            if(u!=null && u.isActive())
            {
                p2.add(new UnitData(new Vector2(u.getX(),u.getY()),i,u.isWalking(),u.isAttacking()));
            }
        }

    }
    public WorldState()
    {
    }
}
