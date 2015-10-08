package com.runic.Network;

import Interfaces.INetPlayer;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import Interfaces.IWorld;
import com.runic.Player;
import com.runic.Screens.NetGameScreen;
import com.runic.Units.*;
import com.runic.World;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Nothrim on 2015-10-07.
 */
public class KryoClient {
    public Client client;
    public ObjectSpace space;
    public World world=World.getInstance();
    private IWorld iWorld;
    private Player p1=World.getInstance().p1;
    private Player p2=World.getInstance().p2;
    private INetPlayer iNetPlayer;
    public KryoClient(String sHost,int port) throws IOException
    {
        client=new Client(32768,32768);
        client.start();
        client.connect(300, sHost, port, 54777);
        createListener();
    }
    public void spawnUnit(BaseUnit unit)
    {
        iWorld=ObjectSpace.getRemoteObject(client,0,IWorld.class);
        iWorld.netCreate(1, NetID.getId(unit));
    }
    public void hurtUnit(int id,int damage,Player owner){

            iNetPlayer = ObjectSpace.getRemoteObject(client, owner.whoAmI()==0?3:4, INetPlayer.class);
            iNetPlayer.netHurt(id, damage);

    }
    public void spawnProjectile(int id,int playerId,float x,float y,float velocityX,float velocityY){
        iWorld=ObjectSpace.getRemoteObject(client,0,IWorld.class);
        iWorld.netCreateProjectile(id, playerId, x, y, velocityX, velocityY);

    }
    public void createSpriteLightning(Vector2 begin,int owner,int targetId,float life)
    {
        iWorld=ObjectSpace.getRemoteObject(client,1,IWorld.class);
        iWorld.netCreateSpriteLightning(begin, owner, targetId, life);
    }
    public void createPremadeEffect(int type, float positionX, float positionY)
    {
        iWorld=ObjectSpace.getRemoteObject(client,1,IWorld.class);
        iWorld.netCreatePremadeEffect(type, positionX, positionY);
    }
    public void hurtCastle(int playerId,int damage)
    {
        iWorld=ObjectSpace.getRemoteObject(client,1,IWorld.class);
        iWorld.netDamageCastle(playerId,damage);
    }
    public KryoClient(int port)
    {
        try{
            client=new Client();
            client.start();
            InetAddress ia = client.discoverHost(54777, 1000);
            String sHost = ia.getHostAddress();
            client.connect(300, sHost, port, 54777);
            createListener();

       } catch (Exception e) {
        e.printStackTrace();
    }
    }
    private void createListener()
    {
        Kryo kryo =client.getKryo();
        kryo.register(World.class);
        kryo.register(IWorld.class);
        kryo.register(WorldState.class);
        kryo.register(BaseUnit.class);
        kryo.register(BaseUnit[].class);
        kryo.register(Footman.class);
        kryo.register(Archer.class);
        kryo.register(BloodKnight.class);
        kryo.register(ArrayList.class);
        kryo.register(UnitData.class);
        kryo.register(Vector2.class);
        kryo.register(INetPlayer.class);
        kryo.register(Player.class);
        ObjectSpace.registerClasses(kryo);
        space=new ObjectSpace(client);
        space.register(0, IWorld.class);
        space.register(1,world);
        space.register(2,INetPlayer.class);
        space.register(3,p1);
        space.register(4,p2);

        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                super.connected(connection);

            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
            }

            @Override
            public void received(Connection connection, Object object) {
                super.received(connection, object);
                if (object instanceof WorldState) {
                    if(object!=null)
                    NetGameScreen.instance.loadWorldState(((WorldState)object));
                }


            }

            @Override
            public void idle(Connection connection) {
                super.idle(connection);
            }
        });
    }

}
