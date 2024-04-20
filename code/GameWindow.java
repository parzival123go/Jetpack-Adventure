import javax.swing.*;			// need this for GUI objects
import java.awt.*;			// need this for certain AWT classes
import java.awt.image.BufferedImage;
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

	private MainCharacter mainCharacter;
	private SoundManager soundManager;
	private Background background;
	

	public GameWindow() {
 
		super("Tiled Bat and Ball Game: Full Screen Exclusive Mode");

		initFullScreen();

		quit1Image = ImageManager.loadImage("code/images/Quit1.png");
		quit2Image = ImageManager.loadImage("code/images/Quit2.png");

		setButtonAreas();

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		soundManager = SoundManager.getInstance();
		image = new BufferedImage (pWidth, pHeight, BufferedImage.TYPE_INT_RGB);

		startGame();
	}

	public void createGameEntities() {

		background = new Background(this, "code/images/backgrounds/1_game_background - Copy.png", 20);
		mainCharacter= new MainCharacter(this);
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


	public void gameUpdate () {

		if (!isPaused) 
		background.move(2);

		//check x coordinates if player is on ground
		if(!isPaused)
		mainCharacter.update();

	}

	public void updateBat (int direction) {

		if (isPaused)
			return;

		if (background != null) {
			background.move(direction);
		}
	}

	private void screenUpdate() { 

		try {
			gScr = bufferStrategy.getDrawGraphics();
			gameRender(gScr);
			gScr.dispose();
			if (!bufferStrategy.contentsLost())
				bufferStrategy.show();
			else
				System.out.println("Contents of buffer lost.");
      
			// Sync the display on some systems.
			// (on Linux, this fixes event queue problems)

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

		//Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for window
		drawButtons(imageContext);			// draw the buttons

		Graphics2D g2 = (Graphics2D) gScr;
		g2.drawImage(image, 0, 0, pWidth, pHeight, null);

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


	// Specify screen areas for the buttons and create bounding rectangles

	private void setButtonAreas() {
		
		//  leftOffset is the distance of a button from the left side of the window.
		//  Buttons are placed at the top of the window.

		int leftOffset = 12;
		pauseButtonArea = new Rectangle(leftOffset, 50, 150, 40);

		startNewGameArea = new Rectangle(leftOffset, 70, 150, 40);

		int quitLength = quit1Image.getWidth(null);
		int quitHeight = quit1Image.getHeight(null);
		quitButtonArea = new Rectangle(8, 100, 180, 50);
	}

	public void startNewGame() {	

		if (gameThread == null || !isPaused) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();

			
		}
	}


	private void drawButtons (Graphics g) {
		Font oldFont, newFont;

		oldFont = g.getFont();		// save current font to restore when finished
	
		newFont = new Font ("TimesRoman", Font.ITALIC + Font.BOLD, 18);
		g.setFont(newFont);		// set this as font for text on buttons

    		g.setColor(Color.black);	// set outline colour of button

		// draw the pause 'button'

		g.setColor(Color.BLACK);
		g.drawOval(pauseButtonArea.x, pauseButtonArea.y, 
			   pauseButtonArea.width, pauseButtonArea.height);

		if (isOverPauseButton && !isStopped)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.RED);	

		if (isPaused && !isStopped)
			g.drawString("Paused", pauseButtonArea.x+45, pauseButtonArea.y+25);
		else
			g.drawString("Pause", pauseButtonArea.x+55, pauseButtonArea.y+25);

		// draw the stop 'button'

		// g.setColor(Color.BLACK);
		// g.drawOval(startNewGameArea.x, startNewGameArea.y, 
		// startNewGameArea.width, startNewGameArea.height);

		// if (isOverStartNewButton && !isStopped)
		// 	g.setColor(Color.WHITE);
		// else
		// 	g.setColor(Color.RED);

		// if (isStopped)
		// 	g.drawString("Restart Game", startNewGameArea.x+40, startNewGameArea.y+25);
		// else
		// 	g.drawString("Stop", startNewGameArea.x+60, startNewGameArea.y+25);

		

		// draw the quit button (an actual image that changes when the mouse moves over it)

		if (isOverQuitButton)
		   g.drawImage(quit1Image, quitButtonArea.x, quitButtonArea.y, 180, 50, null);
		    	       //quitButtonArea.width, quitButtonArea.height, null);
				
		else
		   g.drawImage(quit2Image, quitButtonArea.x, quitButtonArea.y, 180, 50, null);
		    	       //quitButtonArea.width, quitButtonArea.height, null);
/*
		g.setColor(Color.BLACK);
		g.drawOval(quitButtonArea.x, quitButtonArea.y, 
			   quitButtonArea.width, quitButtonArea.height);
		if (isOverQuitButton)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.RED);

		g.drawString("Quit", quitButtonArea.x+60, quitButtonArea.y+25);
*/
		g.setFont(oldFont);		// reset font

	}


	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			//soundManager.playClip ("background", true);
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


	// implementation of methods in KeyListener interface

	public void keyPressed (KeyEvent e) {

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

		// if (isOverStartNewButton) {			// mouse click on Stop button
		// 	isStopped = true;
		// 	isPaused = false;
		// 	startNewGame();;
		// }
		// else
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
			isOverStartNewButton = startNewGameArea.contains(x,y) ? true : false;
			isOverQuitButton = quitButtonArea.contains(x,y) ? true : false;
		}
	}

}