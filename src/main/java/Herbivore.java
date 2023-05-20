import java.util.ArrayList;

public class Herbivore extends Creature {

    Coordinates initialCoordinates;

    public Herbivore(int healthPoints, int speed) {
        super(healthPoints, speed);
    }

    public Herbivore(int healthPoints, int speed, Coordinates coordinates) {
        super(healthPoints, speed);
        this.initialCoordinates = coordinates;
    }

    @Override
    public void makeMove() {

    }


    public Coordinates makeMove(PathFinder pathFinder, Map map) {
        // �֧�ݧ� ��ܧ������ �ҧ�ݧ��� �ܧ�ݧڧ�֧��ӧ� �ܧӧѧէ�ѧ����
        // ���֧���, ���� �٧� ��էߧ� ��֧�֧էӧڧا֧ߧڧ� - 5 ���;
        ArrayList<Coordinates> listOfSquaresToGrass = pathFinder.findPathToNearestGrass(map);
        int movesLeft = speed;

        if (listOfSquaresToGrass == null) {
            map.createNewGrass();

            this.healthPoints += 10;
        } else {
            if (movesLeft > listOfSquaresToGrass.size()) {
                healthPoints = healthPoints - listOfSquaresToGrass.size() + 1;
                return listOfSquaresToGrass.get(0);
            } else {
                for (int i = listOfSquaresToGrass.size() - 1; i >= 0; i--) {
                    if (movesLeft - 1 == 0) {
                        return listOfSquaresToGrass.get(i);
                    } else if (healthPoints == 0) {
                        return null;
                    } else {
                        movesLeft--;
                        healthPoints--;
                        listOfSquaresToGrass.remove(i);
//                    System.out.println(" " + listOfSquaresToGrass);
                    }
                }
            }
            return null;
        }
        return null;
    }
}

//    public ArrayList<Coordinates> getNewListOfSquaresToGrass(ArrayList<Coordinates> listOfSquaresToGrass) {
//        for (int i = listOfSquaresToGrass.size() - 1; i >= 0; i--) {
//            if (this.speed != 0) {
//                listOfSquaresToGrass.remove(i);
//                speed--;
//            }
//        }
//        return listOfSquaresToGrass;
//    }

