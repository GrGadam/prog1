package prog1.kotprog.dontstarve.solution;

import prog1.kotprog.dontstarve.solution.level.Level;

public class Main {

    public static void main(String[] args) {
        Level level = new Level(System.getProperty("user.dir") + "\\kotprog\\dontstarve\\solution\\" + "level00.png");
        String color = String.format("#%06X", (0xFFFFFF & level.getColor(10,10)));
        System.out.println(color);
    }

}
