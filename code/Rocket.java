public class Rocket extends Obstacles{
    
    public Rocket(int x, int y, MainCharacter mc){
        this.mc = mc;
        this.y = y;
        this.x = x;
        this.dx = 10;
        loadImages();
    }

    // need to do collsion with mc

    public void loadImages(){

    }
}
