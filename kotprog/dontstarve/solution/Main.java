package prog1.kotprog.dontstarve.solution;

import prog1.kotprog.dontstarve.solution.character.Character;
import prog1.kotprog.dontstarve.solution.inventory.Inventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemAxe;
import prog1.kotprog.dontstarve.solution.level.Level;
import prog1.kotprog.dontstarve.solution.utility.Position;

public class Main {

    public static void main(String[] args) {

        testLevel();
        testCharacter();
        testInventory();

        /*
        System.out.println("Inventory:");
        int i = 0;
        for (AbstractItem item : ((Inventory) character.getInventory()).getItems()) {
            if (item != null) {
                System.out.println(i+". " + item.getType().name() + " " + item.getAmount() + "db");
            }
            i++;
        }


        System.out.println(character.getInventory().equipItem(0));
        System.out.println(character.getInventory().equippedItem().getType().name());
        */

    }

    public static void testLevel() {
        System.out.println("<--------- Starting Level testing ---------->");

        //1.test
        try {
            Level level = new Level(System.getProperty("user.dir") + "\\kotprog\\dontstarve\\solution\\" + "level00.png");
            GameManager.getInstance().loadLevel(level);
            System.out.println("✓ 1. Level loaded successfully");
        } catch (Exception exception) {
            System.out.println("! --> 1. Error loading Level");
        }

        try {
            System.out.println("Level Width: " + GameManager.getInstance().getLevel().getWidth());
        } catch (Exception ex) {
            System.out.println("! --> Error getting map width");
        }

        try {
            System.out.println("Level Height: " + GameManager.getInstance().getLevel().getHeight());
        } catch (Exception ex) {
            System.out.println("! --> Error getting map height");
        }

        //2.test
        try {
            System.out.printf("✓ 2. Color:" + "#%06X%n", (0xFFFFFF & GameManager.getInstance().getLevel().getColor((GameManager.getInstance().getLevel().getHeight() / 2), (GameManager.getInstance().getLevel().getWidth()) / 2)));
        } catch (Exception ex) {
            System.out.println("! --> 1. Error getting color of Filed");
        }

        //3.test
        try {
            System.out.println("✓ 3. Walkable:" + GameManager.getInstance().getField(GameManager.getInstance().getLevel().getHeight() / 2, GameManager.getInstance().getLevel().getWidth() / 2).isWalkable());
        } catch (Exception ex) {
            System.out.println("! --> 1. Error walkability of Filed");
        }

    }

    public static void testCharacter() {

        System.out.println("<--------- Starting Character testing ---------->");

        if (!GameManager.getInstance().joinCharacter("player", true).equals(new Position(Integer.MAX_VALUE, Integer.MAX_VALUE))) {
            System.out.println("✓ 1. First Player join: Successfull");
        } else {
            System.out.println("! --> 1. Error First Player join: Fail");
        }


        if (GameManager.getInstance().joinCharacter("player", false).equals(new Position(Integer.MAX_VALUE, Integer.MAX_VALUE))) {
            System.out.println("✓ 2. Could not add duplicate name Character");
            System.out.print("Characters: ");
            for (Character c : GameManager.getInstance().getCharacters()) {
                System.out.println(" " + c.getName() + ",");
            }
            System.out.println();
        } else {
            System.out.println("! --> 2. Error: Could add bot with existing name");
            System.out.print("Characters: ");
            for (Character c : GameManager.getInstance().getCharacters()) {
                System.out.println(" " + c.getName() + ",");
            }
            System.out.println();
        }

    }

    public static void testInventory() {

    }

}