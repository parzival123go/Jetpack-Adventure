import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Laser extends Obstacles{
    
    private JPanel panel;
    private Image laser;
    // when called, set a random y
    public Laser(int y, MainCharacter mc, JPanel panel){
        this.mc = mc;
        this.y = y;
        x = panel.getWidth();  // start at right side at random y
        dx = -5;
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
        laser = ImageManager.loadImage("images/laser.png");
        height = laser.getHeight(panel);
        width = laser.getWidth(panel);
    }
}
