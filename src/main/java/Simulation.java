public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        Simulation simulation = new Simulation();
        Map simMap = new Map(7, 8);
        int moveCounter = 0;
        Actions actions = new Actions();
        Herbivore herbivore = new Herbivore(35, 2, new Coordinates(4, 3));
        Predator predator = new Predator(35, 1, 30, new Coordinates(7, 6));
        PathFinder pathFinder = new PathFinder(herbivore, predator);
        actions.setDefaultPositions(simMap);

        // �ڧ���ѧӧڧ�� ���ڧҧܧ� �ߧ֧ӧ֧�ߧ�ԧ� ��ѧ���ݧ�ا֧ߧڧ� ��ҧ�֧ܧ�� ��� ���� x �� y

        Renderer renderer = new Renderer();
        renderer.render(simMap);

        simulation.startSimulation(actions, herbivore, predator, renderer, simMap, pathFinder, moveCounter);

        int a = 123;
    }

    public void nextTurn(Actions actions, Herbivore herbivore, Predator predator, Renderer renderer, Map simMap, PathFinder pathFinder, int moveCounter) {
        actions.makeMove(herbivore, predator, renderer, simMap, pathFinder, moveCounter);
    }

    public void startSimulation(Actions actions, Herbivore herbivore, Predator predator, Renderer renderer, Map simMap, PathFinder pathFinder, int moveCounter) throws InterruptedException {
        while (true) {
            nextTurn(actions, herbivore, predator, renderer, simMap, pathFinder, moveCounter);
            Thread.sleep(1000);
        }
    }
}
