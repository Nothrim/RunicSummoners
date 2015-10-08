package com.runic.Network;

import Interfaces.INetPlayer;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import Interfaces.IWorld;
import com.runic.Player;
import com.runic.Units.*;
import com.runic.World;

import java.util.ArrayList;

/**
 * Created by Nothrim on 2015-10-07.
 */
public class KryoServer extends Server {
    public Server server;
    public ObjectSpace space;
    World world=World.getInstance();
    IWorld clientWorld;
    private Player p1=world.p1;
    private Player p2=world.p2;
    private INetPlayer iNetPlayer;
    private Connection currentConnection;
    public KryoServer(int port) throws Exception
    {
        server=new Server(32768,32768);
        server.start();
        server.bind(port, 54777);
        Kryo kryo =server.getKryo();
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
        server.addListener(new Listener() {
                               @Override
                               public void connected(Connection connection) {
                                   super.connected(connection);
                                   space =new ObjectSpace(connection);
                                   space.register(0,world);
                                   space.register(1, IWorld.class);
                                   space.register(2,INetPlayer.class);
                                   space.register(3,p1);
                                   space.register(4,p2);
                                   currentConnection=connection;
                               }

                               @Override
                               public void disconnected(Connection connection) {
                                       super.disconnected(connection);
                                   }

                               @Override
                               public void received(Connection connection, Object object) {
                                   super.received(connection, object);
                               }

                               @Override
                               public void idle(Connection connection) {
                                       super.idle(connection);
                               }
                           }
            );



    }
    public void spawnUnit(BaseUnit unit)
    {
        clientWorld=ObjectSpace.getRemoteObject(currentConnection,1,IWorld.class);
        clientWorld.netCreate(0, NetID.getId(unit));
    }
    public void hurtUnit(int id,int damage,Player owner){

        iNetPlayer = ObjectSpace.getRemoteObject(currentConnection, owner.whoAmI()==0?3:4, INetPlayer.class);
        iNetPlayer.netHurt(id, damage);

    }
    public void spawnProjectile(int id,int playerId,float x,float y,float velocityX,float velocityY){
        clientWorld=ObjectSpace.getRemoteObject(currentConnection,1,IWorld.class);
        clientWorld.netCreateProjectile(id, playerId, x, y, velocityX, velocityY);

    }
    public void createSpriteLightning(Vector2 begin,int owner,int targetId,float life)
    {
        clientWorld=ObjectSpace.getRemoteObject(currentConnection,1,IWorld.class);
        clientWorld.netCreateSpriteLightning(begin, owner, targetId, life);
    }
    public void createPremadeEffect(int type, float positionX, float positionY)
    {
        clientWorld=ObjectSpace.getRemoteObject(currentConnection,1,IWorld.class);
        clientWorld.netCreatePremadeEffect(type, positionX, positionY);
    }
    public void hurtCastle(int playerId,int damage)
    {
        clientWorld=ObjectSpace.getRemoteObject(currentConnection,1,IWorld.class);
        clientWorld.netDamageCastle(playerId, damage);
    }
}
