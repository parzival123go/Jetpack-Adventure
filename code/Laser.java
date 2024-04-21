import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Laser extends Obstacles{
    
    private GameWindow panel;
    private Image laser;
    private int angle;
    private Boolean ifRotate;
    private Random random;
    // when called, set a random y
    public Laser(int x, int y, MainCharacter mc, GameWindow panel){
        this.mc = mc;
        this.y = y;
        this.x = x;
        dx = -10;
        dy = 0;
        random = new Random();
        angle = random.nextInt(0, 290);
        ifRotate = random.nextBoolean();
        this.panel = panel;
        isVisible = true;
        loadImages();
    }

    public void update(){
        if(collidesWithMc(x, y, width, height) && !mc.getInvincibility()){
            dx = 0;
            isVisible = false;

            mc.damageMc();
        }
        else{
            x+=dx;
            if(ifRotate) angle+=5;
        }
    }

    public void draw(Graphics2D g2){
        AffineTransform originalTransform = g2.getTransform();

        // Translate to the center of the image
        g2.translate(x + width / 2, y + height / 2);

        // Rotate the image
        g2.rotate(Math.toRadians(angle));

        // Draw the image
        g2.drawImage(laser, -width / 2, -height / 2, width, height, null);

        // Restore the original transformation
        g2.setTransform(originalTransform);
    }

    public void loadImages(){
        laser = ImageManager.loadImage("code/images/laser.png");

        // height = laser.getHeight(panel);
        // width = laser.getWidth(panel);
        height = 100;   // 720/4
        width = 250;    // 1280/4

    }
}
