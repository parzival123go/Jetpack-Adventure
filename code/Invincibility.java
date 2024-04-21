import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Invincibility extends PowerUps{
    Image doubleCoinPickup;
    private GameWindow gw;  
    SoundManager sm;
    private int timer=0;
    private boolean timerStarted=false;
    private boolean collision;
    public Invincibility(int x, int y, MainCharacter mc, GameWindow gw){
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
        sm = SoundManager.getInstance();
    }

    public void apply(){
        if(isActive) return;
        else{
            isActive = true;
            mc.setInvincibility(true);
            gw.setInvincActive(true);
        }
    }

    public void removeEffect(){
        if(isActive){
            isActive=false;
        }
    }

    public void draw(Graphics2D g2){
        
            g2.drawImage(doubleCoinPickup, x, y, width, height, null);
        
    }
    

    public void update(){
        if(collidesWithMc(x, y, width, height ) && !collision){
            sm.playClip("invisible",false);
            mc.setInvincibility(true);  
            gw.setInvincActive(true);          
            collision=true;
            timerStarted=true;
        }
        else{
            x+=dx;
        }

        if(timerStarted){
            timer++;
        }
        

        if(timer==300 && timerStarted){
            mc.setInvincibility(false);
            gw.setInvincActive(false);
            timerStarted=false;
            timer=0;
        }
    }

    public void loadImages(){
        doubleCoinPickup = ImageManager.loadImage("code/images/invincible.png");

        width = doubleCoinPickup.getWidth(gw);
        height = doubleCoinPickup.getHeight(gw);
    }
    
}
