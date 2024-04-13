import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel
		       implements Runnable {
   
	private static int NUM_ALIENS = 3;

	private SoundManager soundManager;
	
	private boolean isRunning;
	private boolean isPaused;

	private Thread gameThread;

	private BufferedImage image;
 	//private Image backgroundImage;

	private Background background;

	private ImageFX imageFX;
	private ImageFX imageFX2;


	public GamePanel () {
		
		isRunning = false;
		isPaused = false;
		soundManager = SoundManager.getInstance();

		//backgroundImage = ImageManager.loadImage ("images/Background.jpg");

		image = new BufferedImage (400, 400, BufferedImage.TYPE_INT_RGB);
	}


	public void createGameEntities() {

		background = new Background(this, "images/Scroll-Background.png", 96);

		
	
		imageFX = new TintFX (this);
		imageFX2 = new GrayScaleFX2 (this);

	}


	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				gameRender();
				Thread.sleep (50);	
			}
		}
		catch(InterruptedException e) {}
	}


	public void gameUpdate() {
/*
		for (int i=0; i<NUM_ALIENS; i++) {
			aliens[i].move();
		}

		imageFX.update();
		imageFX2.update();

		animation.update();
		animation2.update();
		animation3.update();
*/
	}


	public void updateBat (int direction) {

		if (isPaused)
			return;

		if (background != null) {
			background.move(direction);
		}

		// if (bat != null) {
		// 	bat.move(direction);
		// }

	}


	public void gameRender() {

		// draw the game objects on the image

		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		background.draw(imageContext);

		//imageContext.drawImage(backgroundImage, 0, 0, null);	// draw the background image

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(image, 0, 0, 400, 400, null);

		imageContext.dispose();
		g2.dispose();
	}


	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();

			
		}

	}


	public void startNewGame() {				// initialise and start a new game thread 

		isPaused = false;

		if (gameThread == null || !isRunning) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();

			
		}
	}


	public void pauseGame() {				// pause the game (don't update game entities)
		if (isRunning) {
			if (isPaused)
				isPaused = false;
			else
				isPaused = true;
		}
	}


	public void endGame() {					// end the game thread
		isRunning = false;
		//soundManager.stopClip ("background");
	}


	


	// public boolean isOnBat (int x, int y) {
	// 	return bat.isOnBat(x, y);
	// }
}