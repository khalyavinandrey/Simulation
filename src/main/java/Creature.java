public abstract class Creature extends Entity {
    public int healthPoints;
    public int speed;


    public Creature(int healthPoints, int speed) {
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    public Creature(int healthPoints, int speed, Coordinates coordinates) {
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    public abstract void makeMove();
}
