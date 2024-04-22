import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Objects;

public class Background {
  	private Image bgImage;
	private Image bgImage2;
  	private int bgImageWidth;      		// width of the background (>= panel Width)

	private GameWindow gw;

 	private int bgX;			// X-coordinate of "actual" position

	private int bg1X;			// X-coordinate of first background
	private int bg2X;			// X-coordinate of second background
	private int bgDX;			// size of the background move (in pixels)
	private int counter;
	private Boolean changed;
  public Background(GameWindow gw, String imageFile, int bgDX) {
    
	this.gw = gw;
	this.bgImage = ImageManager.loadImage(imageFile);
	bgImage2 = bgImage;
	bgImageWidth = bgImage.getWidth(null);	// get width of the background
	counter = 0;
	changed = false;

	// System.out.println ("bgImageWidth = " + bgImageWidth);

	if (bgImageWidth < gw.getWidth())
      		System.out.println("Background width < panel width");

    	this.bgDX = bgDX;

	bgX = 0;
	bg1X = 0;
	bg2X = bgImageWidth;

  }

  public void speedUp(boolean speedup){
	if(speedup){
		bgDX+=15;
	}else{
		bgDX-=15;
	}
	
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
		System.out.println ("Background change: bgX = " + bgX); 
		counter++;
		if(changed){
			bgImage = bgImage2;
			changed = false;
		}
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
	g2.drawImage(bgImage2, bg2X, 0,panelWidth, panelHeight, null);
  }

	public void setBgImage2(String filePath) {

		this.bgImage2 = ImageManager.loadImage(filePath);
		changed = true;
	}
  
	public int getCounter() {
		return this.counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}

}
