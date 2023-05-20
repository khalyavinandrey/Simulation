import java.util.HashMap;
import java.util.Random;

public class Map {

    public static int x;

    public static int y;

    public Map(int x, int y) {
        Map.x = x;
        Map.y = y;
    }

    HashMap<Coordinates, Entity> simulationMap = new HashMap<>();

    public Entity getEntity(Coordinates coordinates) {
        return simulationMap.get(coordinates);
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        simulationMap.put(coordinates, entity);
    }

    public boolean isThereSomething(Coordinates coordinates) {
        return simulationMap.containsKey(coordinates);
    }

    public boolean isThereGrass(Coordinates coordinates) {
        if (isThereSomething(coordinates)) {
            return getEntity(coordinates).getClass().getName().equals(Grass.class.getName());
        } else return false;
    }

    public boolean isThereRock(Coordinates coordinates) {
        if (isThereSomething(coordinates)) {
            return getEntity(coordinates).getClass().getName().equals(Rock.class.getName());
        } else return false;
    }

    public boolean isThereTree(Coordinates coordinates) {
        if (isThereSomething(coordinates)) {
            return getEntity(coordinates).getClass().getName().equals(Tree.class.getName());
        } else return false;
    }

    public boolean isThereHerbivore(Coordinates coordinates) {
        if (isThereSomething(coordinates)) {
            return getEntity(coordinates).getClass().getName().equals(Herbivore.class.getName());
        } else return false;
    }

    public boolean isTherePredator(Coordinates coordinates) {
        if (isThereSomething(coordinates)) {
            return getEntity(coordinates).getClass().getName().equals(Predator.class.getName());
        } else return false;
    }

    public void createNewGrass() {
        Coordinates randomCoordinates = new Coordinates(generateNumber(0, x), generateNumber(0, y));
        if (!isThereSomething(randomCoordinates)) {
            simulationMap.put(randomCoordinates, new Grass());
        } else {
            createNewGrass();
        }
    }

    public int generateNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

}
