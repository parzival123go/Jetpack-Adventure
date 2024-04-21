import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public abstract class PowerUps extends Obstacles{
    protected int duration;
    protected MainCharacter mc;
    protected Boolean isActive;

    private Rectangle2D.Double getBoundingRectangle(int x, int y, int width, int height) {
        return new Rectangle2D.Double (x, y, width, height);
    }
    
    protected boolean collidesWithMc(int x, int y, int width, int height) {
        Rectangle2D.Double myRect = getBoundingRectangle(x, y, width, height);
        Rectangle2D.Double mcRect = mc.getBoundingRectangle();
        
        return myRect.intersects(mcRect); 
    }
    
    void apply(){

    }

    public void removeEffect(){

    }

    public Boolean getIsActive(){
        return this.isActive;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
