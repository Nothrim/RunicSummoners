package com.runic.Screens;

import com.badlogic.gdx.graphics.Color;

import java.util.regex.Pattern;

/**
 * Created by Nothrim on 2015-10-05.
 */
public class IpInput extends ConsoleInput {
    private static final Pattern PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    public IpInput(float x, float y, int width, int height, Color background, String label) {
        super(x, y, width, height, background, label);
    }
    private boolean validateIp(String ip)
    {
        return PATTERN.matcher(ip).matches();
    }
    @Override
    public boolean keyTyped(char character) {
        if(selected) {
            if (validateIp(stringBuilder.toString()))
                setLabel("VALID IP");
            else
                setLabel("INVALID IP");
        }
        return super.keyTyped(character);

    }
}
