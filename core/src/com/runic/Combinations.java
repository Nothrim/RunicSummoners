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
    private static StringBuilder stringBuilder;
    private static String[]SPECIAL_COMBINATIONS=
            {
                    "14.15.x.9.14",
                    "14.6.x.12.14"
            };
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
            Units.put("1",new UnitCode(1,"Archer"));
            Units.put("5.4",new UnitCode(2,"BloodKnight"));
            Units.put("14.1.14",new UnitCode(1000,"RainOfArrows"));
            Units.put("14.15.x.9.14",new UnitCode(1001,"FateTwist"));
            Units.put("14.0.6.0.14",new UnitCode(1002,"ToArms!"));
            Units.put("14.6.x.12.14",new UnitCode(1003,"Swap"));

            combinations.writeString(Loader.prettyPrint(Units, 0), false);
        }
        Units.get("14.15.x.9.14").setSpecialCharacterPosition(6);
        Units.get("14.6.x.12.14").setSpecialCharacterPosition(5);
    }
    public static UnitCode getCombination(String combination)
    {
        if(Units.containsKey(combination))
        {
            return Units.get(combination);
        }
        else
        {
            for(String s:SPECIAL_COMBINATIONS)
            {
                if(combination.length()>=s.length() && combination.startsWith(s.split("x")[0].trim())&& combination.endsWith(s.split("x")[1].trim()))
                {
                    //combination.toCharArray()[findX(s)]='x';
                    stringBuilder=new StringBuilder(combination);
                    int xPosition=findX(s);
                    stringBuilder.setCharAt(xPosition, 'x');
                    if(stringBuilder.charAt(xPosition+1)!='.')
                        stringBuilder.deleteCharAt(xPosition+1);
                    return Units.get(stringBuilder.toString());
                }
            }
        }

        return null;
    }
    private static int findX(String sequence)
    {
        for(int i=0;i<sequence.length();i++)
        {
            if(sequence.charAt(i)=='x')
                return i;
        }
        return -1;
    }
}
