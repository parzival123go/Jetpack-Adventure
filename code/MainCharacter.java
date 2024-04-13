public class MainCharacter extends Character{
    private Boolean isDead;
    private int lives;
    private Boolean damageable;
    private Boolean doubleCoins;

    public MainCharacter(){

    }

    private void fly(){

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
