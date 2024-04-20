import java.awt.Image;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
    The RunningAnimation class creates an animation of a flying bird. 
*/
public class RunningAnimation {
	
	Animation animation;

	private int x;		// x position of animation
	private int y;		// y position of animation

	private int width;
	private int height;

	private int dx;		// increment to move along x-axis
	private int dy;		// increment to move along y-axis

    	private SoundManager soundManager;		// reference to SoundManager to play clip

	public RunningAnimation() {

        	dx = 0;	// increment to move along x-axis
        	dy = 0;	// increment to move along y-axis

		// load images for flying bird animation

		Image animImage1 = ImageManager.loadImage("code/images/player/using/running/1.png");
		Image animImage2 = ImageManager.loadImage("code/images/player/using/running/2.png");
		Image animImage3 = ImageManager.loadImage("code/images/player/using/running/3.png");
		Image animImage4 = ImageManager.loadImage("code/images/player/using/running/4.png");
		Image animImage5 = ImageManager.loadImage("code/images/player/using/running/5.png");
		Image animImage6 = ImageManager.loadImage("code/images/player/using/running/6.png");
		Image animImage7 = ImageManager.loadImage("code/images/player/using/running/7.png");
		Image animImage8 = ImageManager.loadImage("code/images/player/using/running/8.png");
		Image animImage9 = ImageManager.loadImage("code/images/player/using/running/9.png");
		Image animImage10 = ImageManager.loadImage("code/images/player/using/running/10.png");
		Image animImage11 = ImageManager.loadImage("code/images/player/using/running/11.png");
		Image animImage12 = ImageManager.loadImage("code/images/player/using/running/12.png");
		Image animImage13 = ImageManager.loadImage("code/images/player/using/running/13.png");
		Image animImage14 = ImageManager.loadImage("code/images/player/using/running/14.png");
		Image animImage15 = ImageManager.loadImage("code/images/player/using/running/15.png");
	
		// create animation object and insert frames

		animation = new Animation(true);	// play once only

		animation.addFrame(animImage15, 100);
		animation.addFrame(animImage14, 100);
		animation.addFrame(animImage13, 100);
		animation.addFrame(animImage12, 100);
		animation.addFrame(animImage11, 100);
		animation.addFrame(animImage10, 100);
		animation.addFrame(animImage9, 100);
		animation.addFrame(animImage8, 100);
		animation.addFrame(animImage7, 100);
		animation.addFrame(animImage6, 100);
		animation.addFrame(animImage5, 100);
		animation.addFrame(animImage4, 100);
		animation.addFrame(animImage3, 100);
		animation.addFrame(animImage2, 100);
		animation.addFrame(animImage1, 100);

	}


	public void start() {
		x = 400;
        y = 620;
		animation.start();
	//playSound();
	}

	
	public void update() {
		if (!animation.isStillActive()) {
			stopSound();
			animation.start(); // Start the animation again
			return;
		}
	
		animation.update();

		if (x > 800)
			x = 100;
	}


	public void draw(Graphics2D g2) {

		if (!animation.isStillActive()) {
			return;
		}

		g2.drawImage(animation.getImage(), x, y, 120, 120, null);
	}


    	public void playSound() {
		//soundManager.playSound("birdSound", true);
    	}


    	public void stopSound() {
		//soundManager.stopSound("birdSound");
    	}

}
