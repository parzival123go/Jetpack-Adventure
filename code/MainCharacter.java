import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.JPanel;

public class MainCharacter extends Character{
    private Boolean isDead;
    private int lives;
    private Boolean damageable;
    private Boolean doubleCoins;
    private JPanel panel;
    private Boolean isOnGround;
    private int health;

    private HashMap<String, Animation> animations; // will contain all animations
    // animations keys: fly, run, jump, dyingFlying, dyingStanding
    private SoundManager sm;
    public MainCharacter(JPanel panel){
        animations = new HashMap<String, Animation>();
        loadImages();
        dy = 5;
        dx = 5;
        health = 20;
        this.panel = panel;
        this.sm = SoundManager.getInstance();
    }

    public void start(){
        x = 50;
        y = 200;
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

        if(y>=200){
            y=200;
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
        Image animation1 = ImageManager.loadImage("images/player/using/running.gif");
        Animation run = new Animation(true);
        run.addFrame(animation1, 100); // just set to 100 for now
        animations.put("run", run);
        height = animation1.getHeight(panel);
        width = animation1.getWidth(panel);
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
