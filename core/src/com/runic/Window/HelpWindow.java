package com.runic.Window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.runic.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Nothrim on 2015-10-03.
 */
public class HelpWindow extends Window {

    public HelpWindow(Player owner) {
        super(owner.whoAmI() == 1 ? 0 : 1530, 0, 390, 0, new Color(0,0,0,0));
    }
    private float shift;
    private float runeShift;
    private int counter;
    private int runeId;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void drawContent(SpriteBatch sb, float deltaTime) {
        super.drawContent(sb, deltaTime);
        shift=0;
        for(Map.Entry<String,UnitCode> entry: Combinations.Units.entrySet()) {
            shift += 36;
            runeShift = 0;
            counter = 0;

                for (int i = 0; i < entry.getKey().length(); i++) {

                        if (entry.getKey().charAt(Math.min(i, entry.getKey().length() - 1)) == '.' || i >= (entry.getKey().length()-1)) {
                            runeId = 0;

                            if (counter > 0) {

                                for (int j = counter; j > 0; j--) {

                                    runeId += ((int) entry.getKey().charAt(i - j) - 48) * (j > 1 ? 10 : 1);
                                }
                                if(i>=(entry.getKey().length()-1))
                                {
                                    runeId*=10;
                                    runeId+=(int)entry.getKey().charAt(entry.getKey().length()-1)-48;
                                }
                            } else {
                                runeId += ((int) entry.getKey().charAt(i) - 48);
                            }
                            sb.draw(Rune.getTextureRegion(runeId), box.x + runeShift, box.y + shift);
                            runeShift += 42;
                            counter = 0;
                        } else  {
                            counter++;
                        }





                }
                    Assets.getInstance().StandardFontSmall.draw(sb, entry.getValue().getName(), box.x + runeShift + 6, box.y + shift+22);

            }
        if(box.height==0)box.height=shift+82;

    }
}
