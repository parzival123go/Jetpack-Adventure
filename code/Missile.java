import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Missile extends Obstacles{
    
    Image missile;
    JPanel panel;
    SoundManager sm;
    // when called, set a random x
    public Missile(int x, MainCharacter mc, JPanel panel){
        this.mc = mc;
        y = panel.getWidth();
        this.x = x;
        dx = -10;
        dy = 0;
        this.panel = panel;
        isVisible = true;
        loadImages();
        sm = SoundManager.getInstance();
    }

    public void draw(Graphics2D g2){
        if(isVisible){
            g2.drawImage(missile, x, y, width, height, null);
        }
    }
    
    public void update(){
        if(collidesWithMc(x, y, width, height)){
            dx = 0;
            isVisible = false;
            
            mc.damageMc();
        }
        else{
            x+=dx;
        }
    }
    
    public void loadImages(){
        missile = ImageManager.loadImage("images/missile.png");
        height = missile.getHeight(panel);
        width = missile.getWidth(panel);
    }
}
