package com.runic.Network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;

/**
 * Created by Nothrim on 2015-10-04.
 */
public class NetworkManager {
    public static final int STANDARD_PORT=7777;
 private static NetworkManager instance;
    public Server server;
    public Client client;

    private NetworkManager()
    {
        server=new Server();
        client=new Client();
    }
    public static NetworkManager getInstance(){
        if(instance!=null)return instance;
        else
        {
            instance=new NetworkManager();
            return instance;
        }
    }
    private boolean checkDigidts(String str)
    {
        for(char c:str.toCharArray())
        {
            if(!Character.isDigit(c))
                return false;
        }
        return true;
    }
    public boolean connectToServer(String sHost,String sPort)
    {

        int port;
        if(sPort.length()<1)
           port=STANDARD_PORT;
        else if(checkDigidts(sPort))
            port=Integer.parseInt(sPort);
        else
            port=STANDARD_PORT;
        try {
            client.connect(60, sHost, port);
        }
        catch(IOException e)
        {

            e.printStackTrace();
            return false;
        }
        return true;

    }
    public boolean createServer(String sPort)
    {

        int port;
        if(sPort.length()<1)
            port=STANDARD_PORT;
        else if(checkDigidts(sPort))
            port=Integer.parseInt(sPort);
        else
            port=STANDARD_PORT;
        try {
            server.bind(port);
            server.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
