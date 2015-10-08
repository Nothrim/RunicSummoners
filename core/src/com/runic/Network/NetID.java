package com.runic.Network;

import com.runic.Player;
import com.runic.Projectiles.BaseProjectile;
import com.runic.Units.*;

/**
 * Created by Nothrim on 2015-10-07.
 */
public class NetID {
    public static final int ID_FOOTMAN=0;
    public static final int ID_ARCHER=1;
    public static final int ID_BLOOD_KNIGHT=2;
    public static final int ID_ENERGY_CLOUD=3;
    public static final int ID_EARTH_WORM=5;
    public static final int ID_ARROW=100;
    public static BaseUnit getUnit(int id,Player owner)
    {
        BaseUnit unit;
        switch (id)
        {
            case ID_FOOTMAN:
                unit=new Footman(owner,owner.getCastle().getSpawnpoint(),200);
                break;
            case ID_ARCHER:
                unit=new Archer(owner,owner.getCastle().getSpawnpoint(),200);
                break;
            case ID_BLOOD_KNIGHT:
                unit=new BloodKnight(owner,owner.getCastle().getSpawnpoint(),200);
                break;
            case ID_ENERGY_CLOUD:
                unit=new EnergyCloud(owner,owner.getCastle().getSpawnpoint(),620);
                break;
            case ID_EARTH_WORM:
                unit=new EarthWorm(owner,owner.getCastle().getSpawnpoint(),0,7);
                break;
            default:
                return null;
        }
        return unit;
    }
    public static BaseProjectile getProjectile(int id)
    {
        return null;
    }
    public static int getId(BaseUnit u)
    {
        int id=-1;
        if(u.getClass()==Footman.class)
            id=ID_FOOTMAN;
        else if(u.getClass()==Archer.class)
            id=ID_ARCHER;
        else if(u.getClass()==BloodKnight.class)
            id=ID_BLOOD_KNIGHT;
        else if(u.getClass()==EnergyCloud.class)
            id=ID_ENERGY_CLOUD;
        else if(u.getClass()==EarthWorm.class)
            id=ID_EARTH_WORM;
        return id;
    }
}
