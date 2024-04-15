import java.awt.geom.Rectangle2D;

public abstract class Obstacles extends GameObjects{
    protected MainCharacter mc;
    protected Boolean isVisible;
    private Rectangle2D.Double getBoundingRectangle(int x, int y, int width, int height) {
        return new Rectangle2D.Double (x, y, width, height);
    }
    
    protected boolean collidesWithMc(int x, int y, int width, int height) {
        Rectangle2D.Double myRect = getBoundingRectangle(x, y, width, height);
        Rectangle2D.Double mcRect = mc.getBoundingRectangle();
        
        return myRect.intersects(mcRect); 
    }

}
