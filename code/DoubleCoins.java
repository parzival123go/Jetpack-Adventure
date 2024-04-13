
public class DoubleCoins extends PowerUps{
    
    public DoubleCoins(MainCharacter mc, int duration){
        this.duration = duration;
        this.mc = mc;
    }

    public void apply(){
        if(isActive) return;
        else{
            isActive = true;
            mc.setDoubleCoins(true);
        }
    }

    // need to figure out how to do duration
    // then remove effect
}
