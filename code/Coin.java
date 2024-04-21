import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Coin extends Obstacles{
    
    Image coin;
    private GameWindow gw;
    private boolean collision;
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
        loadImages();
        sm = SoundManager.getInstance();
    }

    public void draw(Graphics2D g2){
        if(isVisible){
            g2.drawImage(coin, x, y, width, height, null);
        }
    }

    public void update(){
        if(collidesWithMc(x, y, width, height) && !collision){
            dx = 0;
            isVisible = false;
            collision=true;
            mc.addCoins(1);  // each coin is worth 1
        }
        else{
            x+=dx;
        }
    }

    public void loadImages(){
        coin = ImageManager.loadImage("code/images/coin.png");
        width = coin.getWidth(gw);
        height = coin.getHeight(gw);
    }


}
