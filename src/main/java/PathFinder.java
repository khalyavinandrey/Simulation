import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class PathFinder {
    Queue<CoordinateShift> queueOfCoordinates = new LinkedList<>();

    Herbivore herbivore;
    Predator predator;

    public PathFinder(Herbivore herbivore, Predator predator) {
        this.herbivore = herbivore;
        this.predator = predator;
    }

    public void createInitialQueueForHerbivore() {
        queueOfCoordinates.add(new CoordinateShift(0, -1));
        queueOfCoordinates.add(new CoordinateShift(-1, 0));
        queueOfCoordinates.add(new CoordinateShift(0,1));
        queueOfCoordinates.add(new CoordinateShift(1, 0));
    }

    public void addNewElementsToQueue(CoordinateShift coordinateShift) { //  неэффективно
        int x = coordinateShift.shiftX;
        int y = coordinateShift.shiftY;
        queueOfCoordinates.add(new CoordinateShift(x, y - 1));
        queueOfCoordinates.add(new CoordinateShift(x - 1, y));
        queueOfCoordinates.add(new CoordinateShift(x, y + 1));
        queueOfCoordinates.add(new CoordinateShift(x + 1, y));
    }

    public ArrayList<Coordinates> findPathToNearestGrass(Map map) {

        Coordinates currentCoordinatesOfHerbivore = herbivore.initialCoordinates;
        ArrayList<Coordinates> pathList = new ArrayList<>();
        Coordinates result = null;
        ArrayList<Coordinates> listOfVisitedSquares = new ArrayList<>();
        Queue<Coordinates> queueOfRemovedSquaresFromArrList = new LinkedList<>();
        createInitialQueueForHerbivore();

        while (!queueOfCoordinates.isEmpty()) {
            CoordinateShift coordinateShift = queueOfCoordinates.poll();
            if (!listOfVisitedSquares.contains(currentCoordinatesOfHerbivore.shift(coordinateShift))) {
                Coordinates newCoord = null;
                if (currentCoordinatesOfHerbivore.shift(coordinateShift).canMoveThere()) {
                    newCoord = currentCoordinatesOfHerbivore.shift(coordinateShift);
//                    System.out.println("newCord " + newCoord);
                    // Для того, чтобы можно было с помощью этого метода найти траву, травоядного или хищника
                    if (map.isThereGrass(newCoord)) {
                        result = newCoord;
                        break;
                    } else {
                        if (!map.isThereRock(newCoord) && !map.isThereTree(newCoord)) {
                            listOfVisitedSquares.add(newCoord);
                            addNewElementsToQueue(coordinateShift);
                        }
                    }
                }
            }
        }
        if (result == null) return null;
//        System.out.println(result);
        pathList.add(result);
        int i = 0;
        // когда все клетки до травы обработаны
//        System.out.println("res " + result);
//        System.out.println(listOfVisitedSquares);
        while ((Math.abs(currentCoordinatesOfHerbivore.x - result.x) != 0 || Math.abs(currentCoordinatesOfHerbivore.y - result.y) != 1)
            && (Math.abs(currentCoordinatesOfHerbivore.x - result.x) != 1 || Math.abs(currentCoordinatesOfHerbivore.y - result.y) != 0)) {
            int coordXfromList = listOfVisitedSquares.get(i).x;
            int coordYfromList = listOfVisitedSquares.get(i).y;
            if (i == listOfVisitedSquares.size() - 1 && pathList.size() == 1) {
                System.out.println("Herbivore will speed up");
                map.createNewGrass();
                break;
            }
//            System.out.println(i);
//            System.out.println("X " + coordXfromList);
//            System.out.println("Y " + coordYfromList);
//            System.out.println("list " + listOfVisitedSquares);
//            System.out.println("path " + pathList);
//            System.out.println("coords of herbivore: " + herbivore.initialCoordinates);
            // Если Хищник находится близко к травоядному
            if (isPredatorNear(map)) {
//                System.out.println("possible " + possibleLocationsOfPredator());
//                System.out.println("predatorCoord " + predator.initialCoordinates);
                int predatorX = predator.initialCoordinates.x;
                int predatorY = predator.initialCoordinates.y;

                for (Coordinates coordinates : possibleLocationsOfPredator()) {
                    if ((coordinates.equals(predator.initialCoordinates) ||
                            (Math.abs(predatorX - coordinates.x) == 0 && Math.abs(predatorY - coordinates.y) == herbivore.speed) ||
                            (Math.abs(predatorX - coordinates.x) == herbivore.speed && Math.abs(predatorY - coordinates.y) == 0))) {
//                        System.out.println("delete " + coordinates);
                        listOfVisitedSquares.remove(coordinates);
                    }
                }

                if ((Math.abs(coordXfromList - result.x) == 1 && coordYfromList - result.y == 0) ||
                        (coordXfromList - result.x == 0 && Math.abs(coordYfromList - result.y) == 1)) {
                    pathList.add(listOfVisitedSquares.get(i));
                    result = listOfVisitedSquares.get(i);
                    listOfVisitedSquares.remove(i);
                    i = -1;
                }

                if (i == listOfVisitedSquares.size() - 1) {
                    pathList.remove(pathList.size() - 1);
                    Coordinates lastRemovedSquare = queueOfRemovedSquaresFromArrList.poll();
                    pathList.add(lastRemovedSquare);
                    i = -1;
                    result = lastRemovedSquare;
                }
            } else if (((Math.abs(coordXfromList - result.x) == 1 && coordYfromList - result.y == 0) ||
                    (coordXfromList - result.x == 0 && Math.abs(coordYfromList - result.y) == 1))) {
                pathList.add(listOfVisitedSquares.get(i));
                result = listOfVisitedSquares.get(i);
                listOfVisitedSquares.remove(i);
                i = -1;
            } else if (i == listOfVisitedSquares.size() - 1) {
                pathList.remove(pathList.size() - 1);
                Coordinates lastRemovedSquare = queueOfRemovedSquaresFromArrList.poll();
                pathList.add(lastRemovedSquare);
                i = -1;
                result = lastRemovedSquare;
            }
//            System.out.println(1);
            i++;
        }
        queueOfCoordinates.clear();
        return pathList;
    }

    public ArrayList<Coordinates> findPathToNearestHerbivore(Map map) {
        Coordinates currentCoordinatesOfPredator = predator.initialCoordinates;
        ArrayList<Coordinates> pathList = new ArrayList<>();
        Coordinates result = null;
        ArrayList<Coordinates> listOfVisitedSquares = new ArrayList<>();
        Queue<Coordinates> queueOfRemovedSquaresFromArrList = new LinkedList<>();
        createInitialQueueForHerbivore();

        while (!queueOfCoordinates.isEmpty()) {
            CoordinateShift coordinateShift = queueOfCoordinates.poll();
            if (!listOfVisitedSquares.contains(currentCoordinatesOfPredator.shift(coordinateShift))) {
                Coordinates newCoord = null;
                if (currentCoordinatesOfPredator.shift(coordinateShift).canMoveThere()) {
                    newCoord = currentCoordinatesOfPredator.shift(coordinateShift);

                    if (map.isThereHerbivore(newCoord)) {
                        result = newCoord;
                        break;
                    } else {
                        if (!map.isThereRock(newCoord) && !map.isThereTree(newCoord) && !map.isTherePredator(newCoord)) {
                            listOfVisitedSquares.add(newCoord);
                            addNewElementsToQueue(coordinateShift);
                        }
                    }
                }
            }
        }
        if (result == null) return null;
        pathList.add(result);
        int i = 0;

        while ((Math.abs(currentCoordinatesOfPredator.x - result.x) != 0 || Math.abs(currentCoordinatesOfPredator.y - result.y) != 1)
                && (Math.abs(currentCoordinatesOfPredator.x - result.x) != 1 || Math.abs(currentCoordinatesOfPredator.y - result.y) != 0)) {
            int coordXfromList = listOfVisitedSquares.get(i).x;
            int coordYfromList = listOfVisitedSquares.get(i).y;

            if ((Math.abs(coordXfromList - result.x) == 1 && coordYfromList - result.y == 0) ||
                    (coordXfromList - result.x == 0 && Math.abs(coordYfromList - result.y) == 1)) {
                pathList.add(listOfVisitedSquares.get(i));
                result = listOfVisitedSquares.get(i);
                listOfVisitedSquares.remove(i);
                i = -1;
            }
            if (i == listOfVisitedSquares.size() - 1) {
                pathList.remove(pathList.size() - 1);
                Coordinates lastRemovedSquare = queueOfRemovedSquaresFromArrList.poll();
                pathList.add(lastRemovedSquare);
                i = -1;
                result = lastRemovedSquare;
            }
            i++;
        }
        queueOfCoordinates.clear();
        return pathList;
    }

    public boolean isPredatorNear(Map map) {
        int x = herbivore.initialCoordinates.x;
        int y = herbivore.initialCoordinates.y;
//        System.out.println("herbX" + x);
//        System.out.println("herbY" + y);

        Coordinates coordinates1 = new Coordinates(x + herbivore.speed, y);
        Coordinates coordinates2 = new Coordinates(x - herbivore.speed, y);
        Coordinates coordinates3 = new Coordinates(x, y - herbivore.speed);
        Coordinates coordinates4 = new Coordinates(x, y + herbivore.speed);
        Coordinates coordinates5 = new Coordinates(x + herbivore.speed, y + herbivore.speed);
        Coordinates coordinates6 = new Coordinates(x + herbivore.speed, y - herbivore.speed);
        Coordinates coordinates7 = new Coordinates(x - herbivore.speed, y - herbivore.speed);
        Coordinates coordinates8 = new Coordinates(x - herbivore.speed, y + herbivore.speed);

        return map.isTherePredator(coordinates1) || map.isTherePredator(coordinates2) || map.isTherePredator(coordinates3)
                || map.isTherePredator(coordinates4) || map.isTherePredator(coordinates5) || map.isTherePredator(coordinates6)
                || map.isTherePredator(coordinates7) || map.isTherePredator(coordinates8);
    }

    public ArrayList<Coordinates> possibleLocationsOfPredator() {
        ArrayList<Coordinates> listOfPossibleLocationsOfPredator = new ArrayList<>();
        int x = herbivore.initialCoordinates.x;
        int y = herbivore.initialCoordinates.y;

        Coordinates coordinates1 = new Coordinates(x + herbivore.speed, y);
        Coordinates coordinates2 = new Coordinates(x - herbivore.speed, y);
        Coordinates coordinates3 = new Coordinates(x, y - herbivore.speed);
        Coordinates coordinates4 = new Coordinates(x, y + herbivore.speed);
        Coordinates coordinates5 = new Coordinates(x + herbivore.speed, y + herbivore.speed);
        Coordinates coordinates6 = new Coordinates(x + herbivore.speed, y - herbivore.speed);
        Coordinates coordinates7 = new Coordinates(x - herbivore.speed, y - herbivore.speed);
        Coordinates coordinates8 = new Coordinates(x - herbivore.speed, y + herbivore.speed);
        listOfPossibleLocationsOfPredator.add(coordinates1);
        listOfPossibleLocationsOfPredator.add(coordinates2);
        listOfPossibleLocationsOfPredator.add(coordinates3);
        listOfPossibleLocationsOfPredator.add(coordinates4);
        listOfPossibleLocationsOfPredator.add(coordinates5);
        listOfPossibleLocationsOfPredator.add(coordinates6);
        listOfPossibleLocationsOfPredator.add(coordinates7);
        listOfPossibleLocationsOfPredator.add(coordinates8);
        return listOfPossibleLocationsOfPredator;
    }

}

