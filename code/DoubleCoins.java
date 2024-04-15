import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class DoubleCoins extends PowerUps{
    Image doubleCoinPickup;
    JPanel panel;
    public DoubleCoins(int y, MainCharacter mc, int duration, JPanel panel){
        this.duration = duration;
        this.mc = mc;  
        this.panel = panel;
        x = panel.getWidth();
        this.y = y;
        dx = -5;
        dy = 0;
        isVisible = true;
        isActive = false;
        loadImages();
    }

    public void apply(){
        if(isActive) return;
        else{
            isActive = true;
            mc.setDoubleCoins(true);
        }
    }

    public void removeEffect(){
        if(isActive){
            mc.setDoubleCoins(false);
        }
    }

    // need to figure out how to do duration
    // then remove effect
    // might have to do this in gamepanel and just set double coins effect to false in mc

    public void draw(Graphics2D g2){
        if(isVisible){
            g2.drawImage(doubleCoinPickup, x, y, width, height, null);
        }
    }

    public void update(){
        if(collidesWithMc(x, y, width, height)){
            isVisible = false;
            dx = 0;
            apply();
        }
        else{
            x+=dx;

        }
    }

    public void loadImages(){
        doubleCoinPickup = ImageManager.loadImage("images/doublecoins-rezised.png");
        width = doubleCoinPickup.getWidth(panel);
        height = doubleCoinPickup.getHeight(panel);
    }
}
