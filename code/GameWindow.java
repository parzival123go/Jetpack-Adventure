import javax.swing.*;			// need this for GUI objects
import java.awt.*;			// need this for certain AWT classes
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import java.awt.image.BufferStrategy;	// need this to implement page flipping


public class GameWindow extends JFrame implements
				Runnable,
				KeyListener,
				MouseListener,
				MouseMotionListener
{
  	private static final int NUM_BUFFERS = 2;	// used for page flipping

	private int pWidth, pHeight;     		// width and height of screen

	private Thread gameThread = null;            	// the thread that controls the game
	private volatile boolean isRunning = false;    	// used to stop the game thread

	private BufferedImage image;			// drawing area for each frame

	private Image quit1Image;			// first image for quit button
	private Image quit2Image;			// second image for quit button

	private boolean finishedOff = false;		// used when the game terminates

	private volatile boolean isOverQuitButton = false;
	private Rectangle quitButtonArea;		// used by the quit button

	private volatile boolean isOverPauseButton = false;
	private Rectangle pauseButtonArea;		// used by the pause 'button'
	private volatile boolean isPaused = false;

	private volatile boolean isOverStartNewButton = false;
	private Rectangle startNewGameArea;		// used by the stop 'button'
	private volatile boolean isStopped = false;
   
	private GraphicsDevice device;			// used for full-screen exclusive mode 
	private Graphics gScr;
	private BufferStrategy bufferStrategy;
	private Rectangle coinArea;
	private Rectangle distanceArea;
	private Rectangle powerUps;

	private MainCharacter mainCharacter;
	private ArrayList<Obstacles> obstacles;
	private ArrayList<PowerUps> powerups;
	private ArrayList<Coin> coins;
	private SoundManager soundManager;
	private Background background;
	private Random random;
	private int windowWidth;
	private int windowHeight;
	private int score;
	private SepiaFX doubleCoinActive;
	private DisintegrateFX invisibleActive;
	private GrayScaleFX2 speedActive;
	private boolean speedUpActive=false;
	private boolean invActive=false;
	private boolean dcActive=false;
	private boolean isDead=false;
	private int coinsCount=0;
	private int distanceCovered=0;
	private int level = 1;

	public GameWindow() {
 
		super("Jetpack Adventure =＾● ⋏ ●＾=");

		initFullScreen();

		quit1Image = ImageManager.loadImage("code/images/Quit1.png");
		quit2Image = ImageManager.loadImage("code/images/Quit2.png");

		setButtonAreas();

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		soundManager = SoundManager.getInstance();
		image = new BufferedImage (pWidth, pHeight, BufferedImage.TYPE_INT_RGB);
		random = new Random();
		windowHeight = getBounds().height;
		windowWidth = getBounds().width;
		startGame();
	}

	public void createGameEntities() {

		background = new Background(this, "code/images/backgrounds/background1.png", 10);
		mainCharacter= new MainCharacter(this);
		obstacles = new ArrayList<Obstacles>();
		powerups = new ArrayList<PowerUps>();
		coins = new ArrayList<Coin>();

		// for (int i = 0; i < 1; i++) {
		// 	obstacles.add(new Missile(random.nextInt(windowWidth, windowWidth*2), random.nextInt(0, windowHeight-200), mainCharacter, this));
		//  }
		// for (int i = 0; i < 1; i++) {
		// 	obstacles.add(new Laser(random.nextInt(windowWidth, windowWidth*2), random.nextInt(0, windowHeight-180), mainCharacter, this));
		// }

		// for (int i = 0; i < 1; i++){
		// 	powerups.add(new DoubleCoins(random.nextInt(windowWidth, windowWidth*2), random.nextInt(0, windowHeight-100), mainCharacter, this));
		// }

		// for (int i = 0; i < 1; i++){
		// 	powerups.add(new Invincibility(random.nextInt(windowWidth, windowWidth*2), random.nextInt(0, windowHeight-200), mainCharacter, this));
		// }

		// for (int i = 0; i < 1; i++){
		// 	powerups.add(new SpeedUp(random.nextInt(windowWidth, windowWidth*2), random.nextInt(20, windowHeight-200), mainCharacter, this));
		// }

		// int coinY,coinX;
		// coinY=random.nextInt(0, windowHeight-100);
		// coinX= random.nextInt(windowWidth, windowWidth*2);
		// for (int i = 0; i < 5; i++){
		// 	coins.add(new Coin(coinX + ((i+1)*100),coinY , mainCharacter, this));
		// }

		doubleCoinActive = new SepiaFX(-70, 470, this);

		invisibleActive = new DisintegrateFX(-70, 570, this);

		speedActive = new GrayScaleFX2(-70, 670, this);
		
	}

	// implementation of Runnable interface

	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				if (isPaused == false) {
					gameUpdate();
				}
				screenUpdate();
				Thread.sleep (50);
			}
		}
		catch(InterruptedException e) {}

		finishOff();
	}


	private void finishOff() { 
    		if (!finishedOff) {
			finishedOff = true;
			restoreScreen();
			System.exit(0);
		}
	}


	private void restoreScreen() { 
		Window w = device.getFullScreenWindow();
		
		if (w != null)
			w.dispose();
		
		device.setFullScreenWindow(null);
	}

	public void setSpeedUp(boolean speed){
		background.speedUp(speed);
	}


	public void gameUpdate () {
		
		if(!isPlayerDead()){

			if(background.getCounter() == 2){
				// System.out.println("GameWindow: counter hit 2");
				background.setBgImage2("code/images/backgrounds/background"+random.nextInt(2,24)+".png");
			
				background.setCounter(0);
				level++;
			}

			background.move(2);

			if(!isPaused)

			if(!speedUpActive){
				distanceCovered++;
			}
			else{
				distanceCovered+=2;
			}	

			for (Obstacles ob : obstacles) {
				ob.update();
			}

			for (PowerUps pu : powerups) {
				pu.update();
			}

			for (Coin coin : coins) {
				coin.update();
			}
		}

		mainCharacter.update();

		if(!dcActive)
		doubleCoinActive.remove();

		if(!invActive)
		invisibleActive.remove();

		if(!speedUpActive)
		speedActive.remove();

		if(doubleCoinActive!=null && dcActive){
			doubleCoinActive.setLocation();
			doubleCoinActive.update();
		}

		if(invisibleActive!=null && invActive){
			invisibleActive.setLocation();
			invisibleActive.update();
		}

		if(speedActive!=null && speedUpActive){
			speedActive.setLocation();
			speedActive.update();
		}


	}

	private Boolean isPlayerDead(){
		return mainCharacter.getIsDead();
	}
	// public void updateBat (int direction) {

	// 	if (isPaused)
	// 		return;

	// 	if (background != null) {
	// 		background.move(direction);
	// 	}
	// }

	private void screenUpdate() { 

		try {
			gScr = bufferStrategy.getDrawGraphics();
			gameRender(gScr);
			gScr.dispose();
			if (!bufferStrategy.contentsLost())
				bufferStrategy.show();
			else
				System.out.println("Contents of buffer lost.");

			Toolkit.getDefaultToolkit().sync();
		}
		catch (Exception e) { 
			e.printStackTrace();  
			isRunning = false; 
		} 
	}


	public void gameRender (Graphics gScr) {		// draw the game objects

		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		background.draw(imageContext);
		mainCharacter.draw(imageContext);
		for (Obstacles ob : obstacles) {
			ob.draw(imageContext);
		}

		for (PowerUps pu : powerups) {
			pu.draw(imageContext);
		}

		for (Coin coin : coins) {
			coin.draw(imageContext);
		}

		if(doubleCoinActive!=null){
			doubleCoinActive.draw(imageContext);
		}

		if(invisibleActive!=null){
			invisibleActive.draw(imageContext);
		}

		if(speedActive!=null){
			speedActive.draw(imageContext);
		}

		//Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for window
		drawButtons(imageContext);			// draw the buttons

		Graphics2D g2 = (Graphics2D) gScr;
		g2.drawImage(image, 0, 0, pWidth, pHeight, null);

		if(isPlayerDead()){
			drawGameOverScreen(g2, "Game Over");
		}

		imageContext.dispose();
		g2.dispose();
	}


	private void initFullScreen() {				// standard procedure to get into FSEM

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = ge.getDefaultScreenDevice();

		setUndecorated(true);	// no menu bar, borders, etc.
		setIgnoreRepaint(true);	// turn off all paint events since doing active rendering
		setResizable(false);	// screen cannot be resized
		
		if (!device.isFullScreenSupported()) {
			System.out.println("Full-screen exclusive mode not supported");
			System.exit(0);
		}

		device.setFullScreenWindow(this); // switch on full-screen exclusive mode

		// we can now adjust the display modes, if we wish

		showCurrentMode();

		pWidth = getBounds().width;
		pHeight = getBounds().height;
		
		System.out.println("Width of window is " + pWidth);
		System.out.println("Height of window is " + pHeight);

		try {
			createBufferStrategy(NUM_BUFFERS);
		}
		catch (Exception e) {
			System.out.println("Error while creating buffer strategy " + e); 
			System.exit(0);
		}

		bufferStrategy = getBufferStrategy();
	}


	// This method provides details about the current display mode.

	private void showCurrentMode() {

		DisplayMode dm[] = device.getDisplayModes();

		for (int i=0; i<dm.length; i++) {
			System.out.println("Current Display Mode: (" + 
                           dm[i].getWidth() + "," + dm[i].getHeight() + "," +
                           dm[i].getBitDepth() + "," + dm[i].getRefreshRate() + ")  " );			
		}

		//DisplayMode d = new DisplayMode (800, 600, 32, 60);
		//device.setDisplayMode(d);


		DisplayMode dm1 = device.getDisplayMode();

		dm1 = device.getDisplayMode();


		System.out.println("Current Display Mode: (" + 
                           dm1.getWidth() + "," + dm1.getHeight() + "," +
                           dm1.getBitDepth() + "," + dm1.getRefreshRate() + ")  " );
  	}


	  public void startNewGame() {	

		if (gameThread == null || !isPaused) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();

			
		}
	}

	public void setCoins(int coins){
		coinsCount=coins;
	}

	// Specify screen areas for the buttons and create bounding rectangles

	private void setButtonAreas() {
		
		//  leftOffset is the distance of a button from the left side of the window.
		//  Buttons are placed at the top of the window.

		int leftOffset = 12;
		pauseButtonArea = new Rectangle(leftOffset + 10, 50, 150, 40);

		 coinArea = new Rectangle(leftOffset+5, 180, 180, 70);

		 distanceArea = new Rectangle(leftOffset+5, 280, 180, 70);

		 powerUps = new Rectangle(leftOffset+5, 400, 180, 370);

		
		int quitLength = quit1Image.getWidth(null);
		int quitHeight = quit1Image.getHeight(null);
		quitButtonArea = new Rectangle(8, 100, 180, 50);
	}


	private void drawButtons (Graphics g) {
		Font oldFont, newFont;

		oldFont = g.getFont();		// save current font to restore when finished
	
		newFont = new Font ("TimesRoman", Font.ITALIC + Font.BOLD, 20);
		g.setFont(newFont);		// set this as font for text on buttons

    	g.setColor(Color.cyan);

		g.draw3DRect(coinArea.x, coinArea.y, coinArea.width, coinArea.height,true);
		g.drawString("Coins: " + coinsCount, coinArea.x+35, coinArea.y+45);

		g.setColor(Color.cyan);

		g.draw3DRect(distanceArea.x, distanceArea.y, distanceArea.width, distanceArea.height,true);
		g.drawString("Distance: " + distanceCovered, distanceArea.x+30, distanceArea.y+45);
		g.drawString("Level: "+ level, distanceArea.x+30, distanceArea.y+70);
		g.setColor(Color.cyan);

		g.draw3DRect(powerUps.x, powerUps.y, powerUps.width, powerUps.height,true);
		g.drawString("Active Powerups", powerUps.x+10, powerUps.y+45);

		g.setColor(Color.cyan);
		g.draw3DRect(pauseButtonArea.x, pauseButtonArea.y, 
			   pauseButtonArea.width, pauseButtonArea.height,true);

		if (isOverPauseButton && !isStopped)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.cyan);	

		if (isPaused && !isStopped)
			g.drawString("Paused", pauseButtonArea.x+35, pauseButtonArea.y+25);
		else
			g.drawString("Pause", pauseButtonArea.x+45, pauseButtonArea.y+25);


		// draw the quit button (an actual image that changes when the mouse moves over it)

		if (isOverQuitButton)
		   g.drawImage(quit1Image, quitButtonArea.x, quitButtonArea.y, 180, 50, null);
		    	       //quitButtonArea.width, quitButtonArea.height, null);
				
		else
		   g.drawImage(quit2Image, quitButtonArea.x, quitButtonArea.y, 180, 50, null);
		    	       //quitButtonArea.width, quitButtonArea.height, null);   	      

		g.setFont(oldFont);		// reset font

	}


	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			soundManager.playClip ("level1", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();

			
		}

	}


	// displays a message to the screen when the user stops the game

	private void gameOverMessage(Graphics g) {
		
		Font font = new Font("SansSerif", Font.BOLD, 24);
		FontMetrics metrics = this.getFontMetrics(font);

		String msg = "Game Over. Thanks for playing!";

		int x = (pWidth - metrics.stringWidth(msg)) / 2; 
		int y = (pHeight - metrics.getHeight()) / 2;

		g.setColor(Color.BLUE);
		g.setFont(font);
		g.drawString(msg, x, y);

	}

	private void drawGameOverScreen(Graphics g, String message) {
			
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 60);
        g.setFont(font);
        int messageWidth = g.getFontMetrics().stringWidth(message);
        int x = (getWidth() - messageWidth) / 2;
        int y = getHeight() / 2;

        g.drawString(message, x, y);

		Font font2 = new Font("Arial", Font.BOLD, 40);
        g.setFont(font2);
		String scoreMessage = "Coins Collected: " + coinsCount;
		g.drawString(scoreMessage, x, y+50);

		String scoreMessage2 = "Distance Covered: " + distanceCovered;
		g.drawString(scoreMessage2, x, y+90);
    }

	// implementation of methods in KeyListener interface

	public void keyPressed (KeyEvent e) {

		if (isPaused)
			return;

		int keyCode = e.getKeyCode();

		if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
             	   (keyCode == KeyEvent.VK_END)) {
           		isRunning = false;		
			return;					
         	}
		else
		if (keyCode == KeyEvent.VK_LEFT) {
			//tileMap.moveLeft();
		}
		else
		if (keyCode == KeyEvent.VK_RIGHT) {
			//tileMap.moveRight();
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			soundManager.playClip("fly", true);
			mainCharacter.fly();
		}
		else
		if (keyCode == KeyEvent.VK_UP) {
			//bat.moveUp();
		}
		else
		if (keyCode == KeyEvent.VK_DOWN) {
			//bat.moveDown();
		}

	}


	public void keyReleased (KeyEvent e) {

		if (isPaused)
			return;

		int keyCode = e.getKeyCode();

		if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
             	   (keyCode == KeyEvent.VK_END)) {
           		isRunning = false;		// user can quit anytime by pressing
			return;				//  one of these keys (ESC, Q, END)			
         	}
		else
		if (keyCode == KeyEvent.VK_LEFT) {
			//tileMap.moveLeft();
		}
		else
		if (keyCode == KeyEvent.VK_RIGHT) {
			//tileMap.moveRight();
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			soundManager.stopClip("fly");
			mainCharacter.fall();
		}
		else
		if (keyCode == KeyEvent.VK_UP) {
			//bat.moveUp();
		}
		else
		if (keyCode == KeyEvent.VK_DOWN) {
			//bat.moveDown();
		}
	}

	public void setDCActive(boolean active){
		dcActive=active;
	}

	public void setSpeedUpActive(boolean active){
		speedUpActive=active;
	}

	public void setInvincActive(boolean active){
		invActive=active;
	}


	public void keyTyped (KeyEvent e) {

	}


	// implement methods of MouseListener interface

	public void mouseClicked(MouseEvent e) {

	}


	public void mouseEntered(MouseEvent e) {

	}


	public void mouseExited(MouseEvent e) {

	}


	public void mousePressed(MouseEvent e) {
		testMousePress(e.getX(), e.getY());
	}


	public void mouseReleased(MouseEvent e) {

	}


	// implement methods of MouseMotionListener interface

	public void mouseDragged(MouseEvent e) {

	}	


	public void mouseMoved(MouseEvent e) {
		testMouseMove(e.getX(), e.getY()); 
	}


	/* This method handles mouse clicks on one of the buttons
	   (Pause, Stop, Start Anim, Pause Anim, and Quit).
	*/

	private void testMousePress(int x, int y) {

		if (isStopped && !isOverQuitButton) 	// don't do anything if game stopped
			return;


		if (isOverPauseButton) {		// mouse click on Pause button
			isPaused = !isPaused;     	// toggle pausing
		}
		
		else if (isOverQuitButton) {		// mouse click on Quit button
			isRunning = false;		// set running to false to terminate
		}
  	}


	/* This method checks to see if the mouse is currently moving over one of
	   the buttons (Pause, Stop, Show Anim, Pause Anim, and Quit). It sets a
	   boolean value which will cause the button to be displayed accordingly.
	*/

	private void testMouseMove(int x, int y) { 
		if (isRunning) {
			isOverPauseButton = pauseButtonArea.contains(x,y) ? true : false;
			isOverQuitButton = quitButtonArea.contains(x,y) ? true : false;
		}
	}

}