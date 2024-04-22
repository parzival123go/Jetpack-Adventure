import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.JPanel;

public class DoubleCoins extends PowerUps{
    Image doubleCoinPickup;
    private GameWindow gw;
    SoundManager sm;
    private boolean collision;
    private Random random;
    private int timer=0;
    private boolean timerStarted=false;
    public DoubleCoins(int x, int y, MainCharacter mc, GameWindow gw){
        this.mc = mc;
        this.gw = gw;
        this.y = y;
        this.x = x;
        dx = -10;
        dy = 0;
        random = new Random();
        isVisible = true;
        isActive = false;
        collision = false;
        loadImages();
        sm = SoundManager.getInstance();
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
            gw.setDCActive(false);
        }
    }

    public void draw(Graphics2D g2){
        
            g2.drawImage(doubleCoinPickup, x, y, width, height, null);
        
    }
    

    public void update(){
        if(collidesWithMc(x, y, width, height) && !collision){
            sm.playClip("doubleCoin",false);
            mc.setDoubleCoins(true);
            gw.setDCActive(true);
            collision=true;
            setLocation();
            timerStarted=true;
        }
        else{
            x+=dx;

        }

        if(x<0)
        setLocation();

        if(timerStarted){
            timer++;
        }
        

        if(timer==300 && timerStarted){
            mc.setDoubleCoins(false);
            gw.setDCActive(false);
            timerStarted=false;
            timer=0;
        }
    }

    public void setLocation(){
        x += random.nextInt(gw.getWidth()*4, gw.getWidth()*5);
        y = random.nextInt(0, gw.getHeight()-200);
        collision=false;
    }

    public void loadImages(){
        doubleCoinPickup = ImageManager.loadImage("code/images/doublecoins.png");

        width = doubleCoinPickup.getWidth(gw);
        height = doubleCoinPickup.getHeight(gw);
    }
    
}
