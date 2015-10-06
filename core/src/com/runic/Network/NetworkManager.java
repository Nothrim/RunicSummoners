package com.runic.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.runic.World;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.regex.Pattern;

/**
 * Created by Nothrim on 2015-10-04.
 */
public class NetworkManager {
    public static final int STANDARD_PORT=54555;
    private static final Pattern PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
 private static NetworkManager instance;
    public Server server;
    public Client client;
    private NetworkManager()
    {
        server=null;
        client=null;
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
        if(client==null)
        {
            client=new Client();
        }

        int port;
        if(sPort.length()<1)
           port=STANDARD_PORT;
        else if(checkDigidts(sPort))
            port=Integer.parseInt(sPort);
        else
            port=STANDARD_PORT;
        if(sHost.length()<1) {
            client.start();
            try {
                InetAddress ia = client.discoverHost(54777, 1000);
                sHost = ia.getHostAddress();
                System.out.println(sHost);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        boolean canGo=validateIp(sHost);
        if(canGo) {
            try {
                client.start();

                Kryo kryo =client.getKryo();
                kryo.register(SimpleMessage.class);
                kryo.register(World.class);
                kryo.register(SimpleMessage.ID.class);
                client.connect(300, sHost, port,54777);
                client.addListener(new Listener() {
                    public void received(Connection connection, Object object) {
                    }
                });


            } catch (IOException e) {

                e.printStackTrace();
                return false;
            }

            return true;
        }
        return false;

    }
    public boolean createServer(String sPort)
    {
        if(server==null)
        {
            server=new Server();
        }
        int port;
        if(sPort.length()<1)
            port=STANDARD_PORT;
        else if(checkDigidts(sPort))
            port=Integer.parseInt(sPort);
        else
            port=STANDARD_PORT;
        try {

            server.start();
            server.bind(port, 54777);
            Kryo kryo =server.getKryo();
            kryo.register(SimpleMessage.class);
            kryo.register(World.class);
            kryo.register(SimpleMessage.ID.class);
            server.addListener(new Listener() {
                                   public void received(Connection connection, Object object) {
                                      System.out.println("Got something");
            
                                   }

                               }
            );


        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    private boolean validateIp(String ip)
    {
        return PATTERN.matcher(ip).matches();
    }
}
