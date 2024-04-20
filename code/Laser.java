import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Laser extends Obstacles{
    
    private GameWindow panel;
    private Image laser;
    // when called, set a random y
    public Laser(int x, int y, MainCharacter mc, GameWindow panel){
        this.mc = mc;
        this.y = y;
        this.x = x;
        dx = -10;
        dy = 0;
        this.panel = panel;
        isVisible = true;
        loadImages();
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

    public void draw(Graphics2D g2){
        g2.drawImage(laser, x, y, width, height, null);
    }

    public void loadImages(){
        laser = ImageManager.loadImage("code/images/laser.png");
        // height = laser.getHeight(panel);
        // width = laser.getWidth(panel);
        height = 180;   // 720/4
        width = 320;    // 1280/4
    }
}
