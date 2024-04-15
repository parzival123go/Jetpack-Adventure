import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Invincibility extends PowerUps{
    Image invincibilityPickUp;
    JPanel panel;
    public Invincibility(int y, MainCharacter mc, int duration, JPanel panel){
        this.mc = mc;
        this.duration = duration;
        this.panel = panel;
        this.y = y;
        x = panel.getWidth();
        isVisible = true;
        isActive = false;
        loadImages();
    }

    public void apply(){
        if(isActive) return;
        else{
            isActive = true;
            mc.setDamageable(false);
        }
    }

    public void removeEffect(){
        if(isActive){
            mc.setDamageable(false);
        }
    }
    // need to figure out how to do duration
    // then remove effect
    // might have to do this in gamepanel and just set double coins effect to false in mc

    public void draw(Graphics2D g2){
        if(isVisible){
            g2.drawImage(invincibilityPickUp, x, y, width, height, null);
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
        invincibilityPickUp = ImageManager.loadImage("images/invincible.png");
        width = invincibilityPickUp.getWidth(panel);
        height = invincibilityPickUp.getHeight(panel);
    }

}
