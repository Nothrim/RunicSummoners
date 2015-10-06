package com.runic.Network;

/**
 * Created by Nothrim on 2015-10-06.
 */
public class SimpleMessage {
    public enum ID{GET_WORLD }
    public SimpleMessage(ID id)
    {
        this.id=id;
    }
    public ID id;
}
