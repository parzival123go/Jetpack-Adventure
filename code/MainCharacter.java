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
    private Boolean flying;
    private Boolean falling;
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
        y = gw.getHeight()/2 + 250;
        isOnGround = true;
        flying=false;
        animations.get("run").start();
    }

    public void update(){
        if(isDead != true){
            // update flying anim and keep moving right
            
            if(isOnGround){
                animations.get("run").update();
                flying=false;
                falling=false;
            }
            else
            if(flying && !falling){
                animations.get("fly").update();
                y-=5;

                if(y<0)
                y=0;
            }
            else
            if(flying && falling){
                animations.get("fly").update();
                y+=5;

                if(y>gw.getHeight()/2 + 250){
                    y=gw.getHeight()/2 + 250;
                    flying=false;
                    falling=false;
                    isOnGround=true;
                    animations.get("fly").stop();
                animations.get("run").start();
                }
                
            }
            
        }
        else{
            if(isOnGround) animations.get("dyingStanding").update();
            else animations.get("dyingFlying").update();
        }
    }

    public void fly() {
        // When space is pressed, switch to the fly animation
        isOnGround=false;
        flying=true;
        falling=false;
        animations.get("run").stop();
        animations.get("fly").start();
    }


    public void fall(){
        isOnGround=false;
        flying=true;
        falling=true;
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

    private void loadRunningAnimation() {
        // Load animation frames
        Image[] animationFrames = new Image[15];
        for (int i = 0; i <15; i++) {
            animationFrames[i] = ImageManager.loadImage("code/images/player/using/running/" + (15-i) + ".png");
        }
    
        Animation runningAnimation = new Animation(true);  // play once only
        for (Image frame : animationFrames) {
            runningAnimation.addFrame(frame, 100); 
        }
    
        // Add the running animation to the animations map
        animations.put("run", runningAnimation);
    }
    

    private void loadJumpingAnimation(){
        Image animation1 = ImageManager.loadImage("code/images/player/using/jumping.gif");
        Animation jump = new Animation(true);
        jump.addFrame(animation1, 100); // just set to 100 for now
        animations.put("jump", jump);
    }

    private void loadFlyingAnimation(){
        Image animation1 = ImageManager.loadImage("code/images/player/using/jetpack-flying.gif");
        Animation flying = new Animation(true);
        flying.addFrame(animation1, 100); // just set to 100 for now
        animations.put("fly", flying);
    }

    private void loadDyingFlyingAnimation(){
        Image animation0 = ImageManager.loadImage("code/images/player/using/dying/dyingFlying000.png");
        Image animation1 = ImageManager.loadImage("code/images/player/using/dying/dyingFlying001.png");
        Image animation2 = ImageManager.loadImage("code/images/player/using/dying/dyingFlying002.png");
        Image animation3 = ImageManager.loadImage("ceod/images/player/using/dying/dyingFlying003.png");
        Image animation4 = ImageManager.loadImage("code/images/player/using/dying/dyingFlying004.png");

        Animation dyingFlying = new Animation(false);
        dyingFlying.addFrame(animation0, 20);
        dyingFlying.addFrame(animation1, 20);
        dyingFlying.addFrame(animation2, 20);
        dyingFlying.addFrame(animation3, 20);
        dyingFlying.addFrame(animation4, 20);

        animations.put("dyingFlying", dyingFlying);
    }

    private void loadDyingStandingAnimation(){
        Image animation0 = ImageManager.loadImage("code/images/player/using/dying/dyingStanding000.png");
        Image animation1 = ImageManager.loadImage("code/images/player/using/dying/dyingStanding001.png");
        Image animation2 = ImageManager.loadImage("code/images/player/using/dying/dyingStanding002.png");
        Image animation3 = ImageManager.loadImage("code/images/player/using/dying/dyingStanding003.png");
        Image animation4 = ImageManager.loadImage("code/images/player/using/dying/dyingStanding004.png");

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
