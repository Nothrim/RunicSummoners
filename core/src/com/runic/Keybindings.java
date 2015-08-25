package com.runic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.LinkedHashMap;

/**
 * Created by Nothrim on 2015-08-03.
 */
public class Keybindings {
    public static LinkedHashMap<String , Integer> Hotkeys1 =new LinkedHashMap<>();
    public static LinkedHashMap<String , Integer> Hotkeys2 =new LinkedHashMap<>();
    public static void initialize()
    {
        FileHandle config1=Gdx.files.local("bin/keybindings1.json");
        FileHandle config2=Gdx.files.local("bin/keybindings2.json");
        Json KeyLoader=new Json();
        if(config1.exists()){

            Hotkeys1 = KeyLoader.fromJson(Hotkeys1.getClass(),config1);
        }
        else
        {

            Hotkeys1.put("UP", Input.Keys.UP);
            Hotkeys1.put("DOWN", Input.Keys.DOWN);
            Hotkeys1.put("LEFT", Input.Keys.LEFT);
            Hotkeys1.put("RIGHT", Input.Keys.RIGHT);
            Hotkeys1.put("SELECT", Input.Keys.SPACE);
            Hotkeys1.put("RELASE", Input.Keys.B);
            config1.writeString(KeyLoader.prettyPrint(Hotkeys1, 0), false);
        }
        if(config2.exists()){
            Hotkeys2 = KeyLoader.fromJson(Hotkeys2.getClass(),config2);
        }
        else
        {
            Hotkeys2.put("UP", Input.Keys.W);
            Hotkeys2.put("DOWN", Input.Keys.S);
            Hotkeys2.put("LEFT", Input.Keys.A);
            Hotkeys2.put("RIGHT", Input.Keys.D);
            Hotkeys2.put("SELECT", Input.Keys.H);
            Hotkeys2.put("RELASE", Input.Keys.J);
            config2.writeString(KeyLoader.prettyPrint(Hotkeys2, 0), true);
        }
    }
    public static void save(){
        FileHandle config1=Gdx.files.local("bin/keybindings1.json");
        FileHandle config2=Gdx.files.local("bin/keybindings2.json");
        Json KeyLoader=new Json();
        config1.writeString(KeyLoader.prettyPrint(Hotkeys1, 0), false);
        config2.writeString(KeyLoader.prettyPrint(Hotkeys2, 0), false);
    }
}
