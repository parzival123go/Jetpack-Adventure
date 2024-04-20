import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Image;

public class Background {
  	private Image bgImage;
  	private int bgImageWidth;      		// width of the background (>= panel Width)

	private GameWindow gw;

 	private int bgX;			// X-coordinate of "actual" position

	private int bg1X;			// X-coordinate of first background
	private int bg2X;			// X-coordinate of second background
	private int bgDX;			// size of the background move (in pixels)


  public Background(GameWindow gw, String imageFile, int bgDX) {
    
	this.gw = gw;
    	this.bgImage = ImageManager.loadImage(imageFile);
    	bgImageWidth = bgImage.getWidth(null);	// get width of the background

	System.out.println ("bgImageWidth = " + bgImageWidth);

	if (bgImageWidth < gw.getWidth())
      		System.out.println("Background width < panel width");

    	this.bgDX = bgDX;

	bgX = 0;
	bg1X = 0;
	bg2X = bgImageWidth;

  }


  public void move (int direction) {

	if (direction == 1)
		moveRight();
	else
	if (direction == 2)
		moveLeft();
  }

// player moving right
  public void moveLeft() {

	bgX = bgX - bgDX;

	bg1X = bg1X - bgDX;
	bg2X = bg2X - bgDX;

	// String mess = "Moving background left: bgX=" + bgX + " bg1X=" + bg1X + " bg2X=" + bg2X;
	// System.out.println (mess);

	if (bg1X < (bgImageWidth * -1)) {
		// System.out.println ("Background change: bgX = " + bgX); 
		bg1X = 0;
		bg2X = bgImageWidth;
	}

  }

//  player moving left
  public void moveRight() {

	bgX = bgX + bgDX;
				
	bg1X = bg1X + bgDX;
	bg2X = bg2X + bgDX;

	String mess = "Moving background right: bgX=" + bgX + " bg1X=" + bg1X + " bg2X=" + bg2X;
	System.out.println (mess);

	if (bg1X > 0) {
		System.out.println ("Background change: bgX = " + bgX); 
		bg1X = bgImageWidth * -1;
		bg2X = 0;
	}

   }
 

  public void draw (Graphics2D g2) {
	int panelWidth = gw.getWidth();  
    int panelHeight = gw.getHeight();

	g2.drawImage(bgImage, bg1X, 0,panelWidth, panelHeight, null);
	g2.drawImage(bgImage, bg2X, 0,panelWidth, panelHeight, null);
  }

}
