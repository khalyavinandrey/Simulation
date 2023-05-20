public class Renderer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK_SQUARE_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN_PODCH = "\u001B[5;32m";
    public static final String ANSI_WHITE = "\u001B[97m";
    public static final String ANSI_BLUE = "\u001B[34m";

   public void render(Map map) {

       for (int y = Map.y; y >= 0; y--) {
           String line = "";
           for (int x = 0; x <= Map.x; x++) {
               Coordinates coordinates = new Coordinates(x, y);
               if (!map.isThereSomething(coordinates)) {
                   line += getSpriteForEmptySquare(new Coordinates(x, y), map);
               } else {
                   line += getSpriteForEntity(new Coordinates(x, y), map);
               }

           }
           line += ANSI_RESET;
           System.out.println(line);
       }
       System.out.println();
   }

//   public boolean clear() {
//
//   }

    public String  colorizeSprite(String sprite, Map map, Coordinates coordinates) {
        String result = sprite;

        if (map.isThereSomething(coordinates)) {
            result = ANSI_BLACK_SQUARE_BACKGROUND + result;
        } else result = ANSI_BLACK_SQUARE_BACKGROUND + result;

        if (map.isThereGrass(coordinates)) {
            result = ANSI_GREEN_PODCH + result;
        } else if (map.isThereRock(coordinates)) {
            result = ANSI_WHITE + result;
        } else if (map.isThereTree(coordinates)) {
            result = ANSI_YELLOW + result;
        } else if (map.isThereHerbivore(coordinates)) {
            result = ANSI_BLUE + result;
        } else if (map.isTherePredator(coordinates)) {
            result = ANSI_RED + result;
        }
        return result;
    }

    public String getSpriteForEmptySquare(Coordinates coordinates, Map map) {
       return colorizeSprite("   ", map, coordinates);
    }

    public String selectUnicodeForSprite(Map map, Coordinates coordinates) {
        if (map.isThereGrass(coordinates)) {
            return " G ";
        } else if (map.isThereRock(coordinates)) {
            return " R ";
        } else if (map.isThereTree(coordinates)) {
            return " T ";
        } else if (map.isThereHerbivore(coordinates)) {
            return " H ";
        } else if (map.isTherePredator(coordinates)) {
            return " P ";
        }

        return "";
    }

    private String getSpriteForEntity(Coordinates coordinates, Map map) {
        return colorizeSprite(selectUnicodeForSprite(map, coordinates), map, coordinates);
    }
}
