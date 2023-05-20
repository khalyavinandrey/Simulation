import java.util.ArrayList;

public class Predator extends Creature {
    public int attackPower;
    Coordinates initialCoordinates;

    public Predator(int healthPoints, int speed, int attackPower, Coordinates initialCoordinates) {
        super(healthPoints, speed);
        this.attackPower = attackPower;
        this.initialCoordinates = initialCoordinates;
    }

    public void makeMove() {

    }

    public Coordinates makeMove(PathFinder pathFinder, Map map) {
        ArrayList<Coordinates> listOfSquaresToHerbivore = pathFinder.findPathToNearestHerbivore(map);
        if (listOfSquaresToHerbivore == null) {
            System.out.println("Predator has caught a Herbivore");
        } else {
            for (int i = listOfSquaresToHerbivore.size() - 1; i >= 0; i--) {
                if (speed - 1 == 0) {
                    return listOfSquaresToHerbivore.get(i);
                } else if (healthPoints == 0) {
                    return null;
                } else {
                    speed--;
                    healthPoints--;
                    listOfSquaresToHerbivore.remove(i);
                }
            }
        }
        return null;
    }




}
