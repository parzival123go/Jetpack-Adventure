import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class SpeedUp extends PowerUps{
    Image doubleCoinPickup;
    private GameWindow gw;
    private int timer=0;
    private boolean timerStarted=false;
    private boolean collision;
    public SpeedUp(int x, int y, MainCharacter mc, GameWindow gw){
        this.mc = mc;
        this.gw = gw;
        this.y = y;
        this.x = x;
        dx = -10;
        dy = 0;
        isVisible = true;
        isActive = false;
        collision=false;
        loadImages();
    }

    public void apply(){
        if(isActive) return;
        else{
            isActive = true;
            //mc.setSpeedUp(true);
        }
    }

    public void removeEffect(){
        if(isActive){
            isActive=false;
        }
    }

    public void draw(Graphics2D g2){
        if(isVisible){
            g2.drawImage(doubleCoinPickup, x, y, width, height, null);
        }
    }
    

    public void update(){
        if(collidesWithMc(x, y, width, height) && !collision){
            isVisible = false;
            dx = 0;
            collision=true;
            gw.setSpeedUp(true);
            mc.setInvincibility(true);
            timerStarted=true;
        }
        else{
            x+=dx;
        }

        if(timerStarted){
            timer++;
        }
        

        if(timer==300 && timerStarted){
            gw.setSpeedUp(false);
            mc.setInvincibility(false);
            timerStarted=false;
            timer=0;
        }

    }

    public void loadImages(){
        doubleCoinPickup = ImageManager.loadImage("code/images/speed.png");

        width = doubleCoinPickup.getWidth(gw);
        height = doubleCoinPickup.getHeight(gw);
    }
    
}
