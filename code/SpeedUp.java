import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class SpeedUp extends PowerUps{
    private int oldDx;
    private Image speedUpPickUp;
    JPanel panel;

    public SpeedUp(int y, MainCharacter mc, int duration, JPanel panel){
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
            oldDx = mc.getDx();
            mc.setDx(oldDx+5);
        }
    }

    public void removeEffect(){
        if(isActive){
            mc.setDx(oldDx);
        }
    }
    // need to figure out how to do duration
    // then remove effect
    // might have to do this in gamepanel and just set double coins effect to false in mc

    public void draw(Graphics2D g2){
        if(isVisible){
            g2.drawImage(speedUpPickUp, x, y, width, height, null);
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
        speedUpPickUp = ImageManager.loadImage("images/speed.png");
        width = speedUpPickUp.getWidth(panel);
        height = speedUpPickUp.getHeight(panel);
    }
}
