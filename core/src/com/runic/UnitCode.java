package com.runic;

/**
 * Created by Nothrim on 2015-08-14.
 */
public class UnitCode {
    private int type;
    private String name;
    public UnitCode()
    {

    }
    public UnitCode(int type, String name)
    {
        this.type=type;
        this.name=name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
