import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

public class Missile extends Obstacles{
    
    Image missile;
    GameWindow gw;
    SoundManager sm;
    private Random random;
    private boolean collision;
    private int x,y;
    // when called, set a random x,y
    public Missile(int x, int y, MainCharacter mc, GameWindow gw){
        this.mc = mc;
        this.y = y;
        this.x = x;  
        dx = -30;
        dy = 0;
        this.gw = gw;
        random = new Random();
        isVisible = true;
        collision=false;
        loadImages();
        sm = SoundManager.getInstance();
    }

    public void draw(Graphics2D g2){
       
            g2.drawImage(missile, x, y, 200, 200, null);
        
    }
    
    public void update(){
        if(collidesWithMc(x, y, width/2, height/2) && !mc.getInvincibility() && !collision){         
            collision=true;
            setLocation();
            sm.playClip("missile",false);
            mc.damageMc();
        }
        else{
            x+=dx;
        }

        if(x<0){
            setLocation();
        }
        
    }

    public void setLocation(){
        x += random.nextInt(gw.getWidth(), gw.getWidth()*2);
        y = random.nextInt(0, gw.getHeight()-200);
        collision=false;
    }
    
    public void loadImages(){
        missile = ImageManager.loadImage("code/images/missile.png");
        // height = missile.getHeight(panel);
        // width = missile.getWidth(panel);
        height = 200;
        width = 200;
    }


}
