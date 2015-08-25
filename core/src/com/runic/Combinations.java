package com.runic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.LinkedHashMap;

/**
 * Created by Nothrim on 2015-08-14.
 */
public class Combinations {
    private static LinkedHashMap<String,UnitCode> Units=new LinkedHashMap<String,UnitCode>();
    public static void Load()
    {
        FileHandle combinations= Gdx.files.local("bin/combinations.json");
        Json Loader=new Json();
        if(combinations.exists()){

            Units= Loader.fromJson(Units.getClass(),combinations);
        }
        else
        {

            Units.put("0",new UnitCode(0,"Footman"));
            //rethink it , cant put numbers >9 qq
            Units.put("0.1",new UnitCode(1,"Archer"));

            combinations.writeString(Loader.prettyPrint(Units, 0), false);
        }
    }
    public static UnitCode getCombination(String combination)
    {
        if(Units.containsKey(combination))
        {
            return Units.get(combination);
        }
        return null;
    }
}
