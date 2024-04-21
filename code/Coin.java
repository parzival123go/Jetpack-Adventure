import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.JPanel;

public class Coin extends Obstacles{
    
    Image coin;
    private GameWindow gw;
    private boolean collision;
    private Random random;
    SoundManager sm;

    public Coin(int x, int y, MainCharacter mc, GameWindow gw){
        this.mc = mc;
        this.gw = gw;
        this.y = y;
        this.x = x;
        dx = -10;
        dy = 0;
        isVisible = true;
        collision=false;
        random = new Random();
        loadImages();
        sm = SoundManager.getInstance();
    }

    public void draw(Graphics2D g2){
        
            g2.drawImage(coin, x, y, width, height, null);
        
    }

    public void update(){
        if(collidesWithMc(x, y, width, height) && !collision){
            sm.playClip("coin", false);
            collision=true;
            setLocation();
            mc.addCoins(1);  // each coin is worth 1
        }
        else{
            x+=dx;
        }

        if(x<0)
        setLocation();
    }

    public void setLocation(){
        x += random.nextInt(gw.getWidth(), gw.getWidth()*2);
        y = random.nextInt(0, gw.getHeight()-100);
        collision=false;
    }

    public void loadImages(){
        coin = ImageManager.loadImage("code/images/coin.png");
        width = coin.getWidth(gw);
        height = coin.getHeight(gw);
    }


}
