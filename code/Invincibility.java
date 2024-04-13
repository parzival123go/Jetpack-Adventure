public class Invincibility extends PowerUps{
    
    public Invincibility(MainCharacter mc, int duration){
        this.mc = mc;
        this.duration = duration;
    }

    public void apply(){
        if(isActive) return;
        else{
            isActive = true;
            mc.setDamageable(false);
        }
    }

    // need to figure out how to do duration
    // then remove effect
}
