package com.runic.Network;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by Nothrim on 2015-10-04.
 */
public class NetworkManager {
    public static final int STANDARD_PORT=54555;
    private static final Pattern PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    private static NetworkManager instance;
    public KryoServer server;
    public KryoClient client;
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

        int port;
        if(sPort.length()<1)
           port=STANDARD_PORT;
        else if(checkDigidts(sPort))
            port=Integer.parseInt(sPort);
        else
            port=STANDARD_PORT;
        if(sHost.length()<1) {
            try{
                client=new KryoClient(port);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        boolean canGo=validateIp(sHost);
        if(canGo) {
            try {
            client=new KryoClient(sHost,port);

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
        int port;
        if(sPort.length()<1)
            port=STANDARD_PORT;
        else if(checkDigidts(sPort))
            port=Integer.parseInt(sPort);
        else
            port=STANDARD_PORT;
        try{
            server=new KryoServer(port);
        }
        catch (Exception e)
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
