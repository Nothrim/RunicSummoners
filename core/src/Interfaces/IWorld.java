package Interfaces;

import com.badlogic.gdx.math.Vector2;
import com.runic.Units.BaseUnit;

/**
 * Created by Nothrim on 2015-10-07.
 */
public interface IWorld {
    void netCreate(int playerId,int id);
    void netCreateProjectile(int id, int playerId, float x, float y, float velocityX, float velocityY);
    void netCreateSpriteLightning(Vector2 begin, int owner,int targetId, float life);
    void netCreatePremadeEffect(int type,float positionX,float positionY);
    void netDamageCastle(int playerId,int damage);

}
