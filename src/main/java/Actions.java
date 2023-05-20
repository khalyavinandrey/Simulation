import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

public class Actions {
    public void setDefaultPositions(Map map) {
        map.setEntity(new Coordinates(4, 3), new Herbivore(35, 2));
        map.setEntity(new Coordinates(0, 0), new Grass());
        map.setEntity(new Coordinates(0, 2), new Rock());
        map.setEntity(new Coordinates(4, 5), new Rock());
        map.setEntity(new Coordinates(0, 1), new Rock());
        map.setEntity(new Coordinates(3, 3), new Rock());
        map.setEntity(new Coordinates(6, 6), new Tree());
        map.setEntity(new Coordinates(5, 4), new Tree());
        map.setEntity(new Coordinates(1, 2), new Tree());
        map.setEntity(new Coordinates(7, 6), new Predator(20, 1, 10, new Coordinates(7, 6)));
    }


    public void makeMove(Herbivore herbivore, Predator predator, Renderer renderer, Map map, PathFinder pathFinder, int moveCounter) {
        Coordinates newCoordinatesOfHerbivore = herbivore.makeMove(pathFinder, map);
        Coordinates newCoordinatesOfPredator = predator.makeMove(pathFinder, map);
        if (newCoordinatesOfHerbivore == null) {
            System.out.println("Herbivore has eaten a grass, now his hp is " + herbivore.healthPoints);
        } else if (newCoordinatesOfPredator == null) {
            int herbivoreHP = herbivore.healthPoints - predator.attackPower;
            if (herbivoreHP <= 0) {
                System.out.println("Herbivore is dead");
            } else {
                System.out.println("Now Herbivore hp is: " + herbivoreHP);
                newCoordinatesOfHerbivore = herbivore.makeMove(pathFinder, map);
                map.simulationMap.remove(herbivore.initialCoordinates);
                map.setEntity(newCoordinatesOfPredator, predator);
                map.setEntity(newCoordinatesOfHerbivore, herbivore);
            }
        } else {
            map.simulationMap.remove(predator.initialCoordinates); // §á§à§Þ§Ö§ß§ñ§Ý
            map.simulationMap.remove(herbivore.initialCoordinates);
            map.setEntity(newCoordinatesOfHerbivore, herbivore);
            map.setEntity(newCoordinatesOfPredator, predator);
            herbivore.initialCoordinates = newCoordinatesOfHerbivore;
            predator.initialCoordinates = newCoordinatesOfPredator;
        }
        renderer.render(map);
        moveCounter++;
    }
}

