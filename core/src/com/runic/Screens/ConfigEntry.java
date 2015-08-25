package com.runic.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * Created by Nothrim on 2015-08-03.
 */
public class ConfigEntry  extends ImageTextButton{
    int keycode;
    public static ConfigEntry selected=null;



    public ConfigEntry(int keycode,ImageTextButtonStyle style) {
        super(Input.Keys.toString(keycode),style);
        this.keycode=keycode;
    }
    public void setKeycode(int keycode){this.keycode=keycode;}
    public int getKeycode(){return keycode;}
}
