import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Coin extends Obstacles{
    
    Image coin;
    JPanel panel;

    SoundManager sm;

    public Coin(int y, MainCharacter mc, JPanel panel){
        this.panel = panel;
        this.mc = mc;
        x = panel.getWidth();
        dx = -10;
        dy = 0;
        isVisible = true;

        loadImages();
        sm = SoundManager.getInstance();
    }

    public void draw(Graphics2D g2){
        if(isVisible){
            g2.drawImage(coin, x, y, width, height, null);
        }
    }

    public void update(){
        if(collidesWithMc(x, y, width, height)){
            dx = 0;
            isVisible = false;
            
            mc.addCoins(1);  // each coin is worth 1
        }
        else{
            x+=dx;
        }
    }

    public void loadImages(){
        coin = ImageManager.loadImage("coin/images/coin.png");
        width = coin.getWidth(panel);
        height = coin.getHeight(panel);
    }


}
