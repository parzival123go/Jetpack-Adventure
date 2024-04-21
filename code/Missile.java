import java.awt.Graphics2D;
import java.awt.Image;

public class Missile extends Obstacles{
    
    Image missile;
    GameWindow panel;
    SoundManager sm;
    // when called, set a random x,y
    public Missile(int x, int y, MainCharacter mc, GameWindow panel){
        this.mc = mc;
        this.y = y;
        this.x = x;  
        dx = -30;
        dy = 0;
        this.panel = panel;
        isVisible = true;
        loadImages();
        sm = SoundManager.getInstance();
    }

    public void draw(Graphics2D g2){
        if(isVisible){
            g2.drawImage(missile, x, y, 200, 200, null);
        }
    }
    
    public void update(){
        if(collidesWithMc(x, y, width, height) ){
            dx = 0;
            isVisible = false;
            
            mc.damageMc();
        }
        else{
            x+=dx;
        }
    }
    
    public void loadImages(){
        missile = ImageManager.loadImage("code/images/missile.png");
        // height = missile.getHeight(panel);
        // width = missile.getWidth(panel);
        height = 200;
        width = 200;
    }


}
