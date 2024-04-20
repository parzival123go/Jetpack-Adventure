import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;


public class MainCharacter extends Character{
    private Boolean isDead;
    private int lives;
    private int coins;
    private int width=120;
    private int height = 120;
    private Boolean damageable;
    private Boolean doubleCoins;
    private GameWindow gw;
    private Boolean isOnGround;
    private int health;

    private HashMap<String, Animation> animations; // will contain all animations
    // animations keys: fly, run, jump, dyingFlying, dyingStanding
    private SoundManager sm;
    public MainCharacter(GameWindow gw){
        animations = new HashMap<String, Animation>();
        loadImages();
        dy = 0;
        dx = 0;
        health = 20;
        isDead=false;
        this.gw = gw;
        this.sm = SoundManager.getInstance();
        start();
    }

    public void start(){
        x = 400;
        y = 680;
        isOnGround = true;
        for (String key: animations.keySet()) {
            Animation temp = animations.get(key);
            temp.start();
        }
    }

    public void update(){
        if(isDead != true){
            // update flying anim and keep moving right
            
            if(isOnGround){
                animations.get("run").update();
            }
            else{
                animations.get("fly").update();
            }
            
            x+=dx;
        }
        else{
            if(isOnGround) animations.get("dyingStanding").update();
            else animations.get("dyingFlying").update();
        }
        fall();  // could do always falling 
    }

    public void fly(){
        // when space is pressed, increase dy until hit screen limit
        // and fall when space not pressed
        isOnGround = false;
        if(y<=0){
            y = 0;
        }

        else{
            y-=dy;
        }
    }

    private void fall(){
        // do fall calculation here *****

        if(y<=680){
            y=680;
            isOnGround = true;
        }
    }

    public void draw(Graphics2D g2){
        if(isDead){
            if(isOnGround){
                g2.drawImage(animations.get("dyingStanding").getImage(), x, y, width, height, null);
            }
            else{
                g2.drawImage(animations.get("dyingFlying").getImage(), x, y, width, height, null);    
            }
        }
        else{
            if(isOnGround){
                g2.drawImage(animations.get("run").getImage(), x, y, width, height, null);
            }
            else{
                g2.drawImage(animations.get("fly").getImage(), x, y, width, height, null);    
            }
        }
    }

    public void move(){
        // might not need this
    }

    public void damageMc(){
        if(damageable){
            health--;
        }
        return;
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, width, height);
    }

    public void loadImages(){
        loadDyingFlyingAnimation();
        loadDyingStandingAnimation();
        loadFlyingAnimation();
        loadJumpingAnimation();
        loadRunningAnimation();
    }

    private void loadRunningAnimation(){
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

		Animation runningAnimation = new Animation(true);	// play once only

		runningAnimation.addFrame(animImage15, 100);
		runningAnimation.addFrame(animImage14, 100);
		runningAnimation.addFrame(animImage13, 100);
		runningAnimation.addFrame(animImage12, 100);
		runningAnimation.addFrame(animImage11, 100);
		runningAnimation.addFrame(animImage10, 100);
		runningAnimation.addFrame(animImage9, 100);
		runningAnimation.addFrame(animImage8, 100);
		runningAnimation.addFrame(animImage7, 100);
		runningAnimation.addFrame(animImage6, 100);
		runningAnimation.addFrame(animImage5, 100);
		runningAnimation.addFrame(animImage4, 100);
		runningAnimation.addFrame(animImage3, 100);
		runningAnimation.addFrame(animImage2, 100);
		runningAnimation.addFrame(animImage1, 100);

        animations.put("run", runningAnimation);
    }

    private void loadJumpingAnimation(){
        Image animation1 = ImageManager.loadImage("images/player/using/jumping.gif");
        Animation jump = new Animation(true);
        jump.addFrame(animation1, 100); // just set to 100 for now
        animations.put("jump", jump);
    }

    private void loadFlyingAnimation(){
        Image animation1 = ImageManager.loadImage("images/player/using/jetpack-flying.gif");
        Animation flying = new Animation(true);
        flying.addFrame(animation1, 100); // just set to 100 for now
        animations.put("fly", flying);
    }

    private void loadDyingFlyingAnimation(){
        Image animation0 = ImageManager.loadImage("images/player/using/dying/dyingFlying000.png");
        Image animation1 = ImageManager.loadImage("images/player/using/dying/dyingFlying001.png");
        Image animation2 = ImageManager.loadImage("images/player/using/dying/dyingFlying002.png");
        Image animation3 = ImageManager.loadImage("images/player/using/dying/dyingFlying003.png");
        Image animation4 = ImageManager.loadImage("images/player/using/dying/dyingFlying004.png");

        Animation dyingFlying = new Animation(false);
        dyingFlying.addFrame(animation0, 20);
        dyingFlying.addFrame(animation1, 20);
        dyingFlying.addFrame(animation2, 20);
        dyingFlying.addFrame(animation3, 20);
        dyingFlying.addFrame(animation4, 20);

        animations.put("dyingFlying", dyingFlying);
    }

    private void loadDyingStandingAnimation(){
        Image animation0 = ImageManager.loadImage("images/player/using/dying/dyingStanding000.png");
        Image animation1 = ImageManager.loadImage("images/player/using/dying/dyingStanding001.png");
        Image animation2 = ImageManager.loadImage("images/player/using/dying/dyingStanding002.png");
        Image animation3 = ImageManager.loadImage("images/player/using/dying/dyingStanding003.png");
        Image animation4 = ImageManager.loadImage("images/player/using/dying/dyingStanding004.png");

        Animation dyingStanding = new Animation(false);
        dyingStanding.addFrame(animation0, 20);
        dyingStanding.addFrame(animation1, 20);
        dyingStanding.addFrame(animation2, 20);
        dyingStanding.addFrame(animation3, 20);
        dyingStanding.addFrame(animation4, 20);

        animations.put("dyingStanding", dyingStanding);
    }

    public Boolean isIsDead() {
        return this.isDead;
    }

    public Boolean getIsDead() {
        return this.isDead;
    }

    public void setIsonGround(Boolean onGround) {
        this.isOnGround = onGround;
    }

    public void setIsDead(Boolean isDead) {
        this.isDead = isDead;
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Boolean isDamageable() {
        return this.damageable;
    }

    public Boolean getDamageable() {
        return this.damageable;
    }

    public void setDamageable(Boolean damageable) {
        this.damageable = damageable;
    }

    public Boolean isDoubleCoins() {
        return this.doubleCoins;
    }

    public Boolean getDoubleCoins() {
        return this.doubleCoins;
    }

    public void setDoubleCoins(Boolean doubleCoins) {
        this.doubleCoins = doubleCoins;
    }

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int amnt){
        if(doubleCoins){
            this.coins+=2*amnt;
        }
        else{
            this.coins+=amnt;
        }
    }

    public int getDy(){
        return this.dy;
    }

    public void setDy(int newDy){
        this.dy = newDy;
    }

    public int getDx(){
        return this.dx;
    }

    public void setDx(int newDx){
        this.dx = newDx;
    }
}
