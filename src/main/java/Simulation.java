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

        // §Ú§ã§á§â§Ñ§Ó§Ú§ä§î §à§ê§Ú§Ò§Ü§å §ß§Ö§Ó§Ö§â§ß§à§Ô§à §â§Ñ§ã§á§à§Ý§à§Ø§Ö§ß§Ú§ñ §à§Ò§ì§Ö§Ü§ä§Ñ §á§à §à§ã§Ú x §Ú y

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
