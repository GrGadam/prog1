package prog1.kotprog.dontstarve.solution;

import prog1.kotprog.dontstarve.solution.character.Character;
import prog1.kotprog.dontstarve.solution.inventory.Inventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemAxe;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemLog;
import prog1.kotprog.dontstarve.solution.level.Field;
import prog1.kotprog.dontstarve.solution.level.Level;
import prog1.kotprog.dontstarve.solution.utility.Position;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        testLevel();
        testCharacter();
        testInventory();
    }

    private static void printLevel() {
        int maxSor = GameManager.getInstance().getLevel().getHeight();
        int maxOszlop = GameManager.getInstance().getLevel().getWidth();

        for (int sor = 0; sor < maxSor; sor++) {
            for (int oszlop = 0; oszlop < maxOszlop; oszlop++) {
                Field f = (Field) GameManager.getInstance().getField(sor, oszlop);
                if (f.isWalkable()) {
                    System.out.print("\u001B[32m" + "G");
                } else {
                    System.out.print("\u001B[34m" + "W");
                }
            }
            System.out.println("\u001B[0m");    //default color
        }
    }

    private static void printLevelWithPlayers() {
        int maxSor = GameManager.getInstance().getLevel().getHeight();
        int maxOszlop = GameManager.getInstance().getLevel().getWidth();

        for (int sor = 0; sor < maxSor; sor++) {
            for (int oszlop = 0; oszlop < maxOszlop; oszlop++) {
                Field f = (Field) GameManager.getInstance().getField(sor, oszlop);
                if (f.isWalkable()) {
                    boolean isCharacter = false;

                    for (Position p : GameManager.getInstance().getCharacterPositions()) {
                        if ((int) p.getNearestWholePosition().getX() == sor && (int) p.getNearestWholePosition().getY() == oszlop) {
                            isCharacter = true;
                            break;
                        }
                    }

                    if (isCharacter) {
                        System.out.print("\u001B[35m" + "C");
                    } else {
                        System.out.print("\u001B[32m" + "G");
                    }
                } else {
                    System.out.print("\u001B[34m" + "W");
                }
            }
            System.out.println("\u001B[0m");    //default color
        }
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

        try {
            printLevel();
        } catch (Exception ex) {
            System.out.println("! --> 4. Failed to print Level");
        }

    }

    public static void testCharacter() {

        System.out.println("<--------- Starting Character testing ---------->");

        if (!GameManager.getInstance().joinCharacter("player", true).equals(new Position(Integer.MAX_VALUE, Integer.MAX_VALUE))) {
            System.out.println("✓ 1. First Player join: Successfull");
            printInventory("player");
        } else {
            System.out.println("! --> 1. Error First Player join: Fail");
        }

        try {
            Position p = Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getCurrentPosition().getNearestWholePosition();
            System.out.println("Player Position: " + p.getX() + " " + p.getY());
        } catch (Exception ex) {
            System.out.println("Fail Player position");
        }

        if (GameManager.getInstance().getCharacter("player") != null) {
            System.out.println("✓ 2. Duplicate name detection successfull");
        } else {
            System.out.println("! --> 2. Error: Duplicate name detection not working");
        }

        if (GameManager.getInstance().joinCharacter("player", false).getX() == new Position(Integer.MAX_VALUE, Integer.MAX_VALUE).getX()) {
            System.out.println("✓ 3. Could not add duplicate name Character");
            System.out.print("   ↳ Characters: ");
            for (Character c : GameManager.getInstance().getCharacters()) {
                System.out.print(" " + c.getName() + ",");
            }
            System.out.println();
        } else {
            System.out.println("! --> 3. Error: Could add bot with existing name");
            System.out.print("   ↳ Characters: ");
            for (Character c : GameManager.getInstance().getCharacters()) {
                System.out.print(" " + c.getName() + ",");
            }
            System.out.println();
        }

        if (GameManager.getInstance().joinCharacter("player2", true).getX() == new Position(Integer.MAX_VALUE, Integer.MAX_VALUE).getX()) {
            System.out.println("✓ 4. Could not add duplicate players to game");
            System.out.print("   ↳ Characters: ");
            for (Character c : GameManager.getInstance().getCharacters()) {
                System.out.print(" " + c.getName() + ",");
            }
            System.out.println();
        } else {
            System.out.println("! --> 4. Error: Could add 2 players to game");
            System.out.print("   ↳ Characters: ");
            for (Character c : GameManager.getInstance().getCharacters()) {
                System.out.print(" " + c.getName() + ",");
            }
            System.out.println();
        }

        printLevelWithPlayers();

        if (!GameManager.getInstance().joinCharacter("bot1", false).equals(new Position(Integer.MAX_VALUE, Integer.MAX_VALUE))) {
            System.out.println("✓ 11. First Bot1 join: Successful");
            printInventory("bot1");
        } else {
            System.out.println("! --> 11. Error Bot1 join: Fail");
        }

        try {
            Position p = Objects.requireNonNull(GameManager.getInstance().getCharacter("bot1")).getCurrentPosition().getNearestWholePosition();
            System.out.println("Bot1 Position: " + p.getX() + " " + p.getY());
        } catch (Exception ex) {
            System.out.println("Fail Bot1 position");
        }

        if (!GameManager.getInstance().joinCharacter("bot2", false).equals(new Position(Integer.MAX_VALUE, Integer.MAX_VALUE))) {
            System.out.println("✓ 11. First Bot2 join: Successful");
            printInventory("bot2");
        } else {
            System.out.println("! --> 11. Error Bot2 join: Fail");
        }

        try {
            Position p = Objects.requireNonNull(GameManager.getInstance().getCharacter("bot2")).getCurrentPosition().getNearestWholePosition();
            System.out.println("Bot2 Position: " + p.getX() + " " + p.getY());
        } catch (Exception ex) {
            System.out.println("Fail Bot2 position");
        }

        printLevelWithPlayers();

        try {
            System.out.println("Adding 100 bots:");
            for (int i = 3; i < 103; i++) {
                if (!GameManager.getInstance().joinCharacter("bot" + i, false).equals(new Position(Integer.MAX_VALUE, Integer.MAX_VALUE))) {
                    System.out.println("Bot" + i + " joined the game.");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error while adding 100 bots");
        }

        printLevelWithPlayers();

    }

    private static void printInventory(String name) {
        System.out.print("   ↳ player inventory: ");
        int i = 0;
        for (AbstractItem item : ((Inventory) Objects.requireNonNull(GameManager.getInstance().getCharacter(name)).getInventory()).getItems()) {
            if (item != null) {
                System.out.print(i + ". " + item.getType().name() + " " + item.getAmount() + "db, ");
            } else {
                System.out.print(i + ". " + "null" + " " + "0db, ");
            }
            i++;
        }
        System.out.println();
    }

    private static void printFieldItems(String name) {
        System.out.print("   ↳ items on Field: ");
        int i = 0;
        Position playerPos = Objects.requireNonNull(GameManager.getInstance().getCharacter(name)).getCurrentPosition().getNearestWholePosition();
        for (AbstractItem item : GameManager.getInstance().getField((int) playerPos.getX(), (int) playerPos.getY()).items()) {
            if (item != null) {
                System.out.print(i + ". " + item.getType().name() + " " + item.getAmount() + "db, ");
            }
            i++;
        }
        System.out.println();
    }

    public static void testInventory() {

        System.out.println("<--------- Starting Inventory testing ---------->");

        try {
            if (Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().addItem(new ItemAxe())) {
                System.out.println("✓ 1. Add AXE to Player inventory");
            }
        } catch (Exception ex) {
            System.out.println("! --> 1. Error: Could not add AXE to Player inventory");
        }

        try {
            printInventory("player");
        } catch (Exception ex) {
            System.out.println("! --> 2. Inventory Error");
        }

        try {
            Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().equipItem(0);
            System.out.println("✓ 3. Equip AXE");
        } catch (Exception ex) {
            System.out.println("! --> 3. equipItem(0) AXE error");
        }

        try {
            int i = 10;
            while (i > 0) {
                Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().addItem(new ItemAxe());
                i--;
            }
            System.out.println("✓ 4. Filling player inventory with 10 AXE");
            printInventory("player");
            System.out.print("   ↳ player equipped item: ");
            System.out.print(Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().equippedItem().getType().name() + " " + Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().equippedItem().getAmount() + "db");
            System.out.println();
        } catch (Exception ex) {
            System.out.println("! --> 4. Error while filling player inventory with 10 AXEs");
        }

        try {
            Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().unequipItem();
            System.out.println("✓ 5. unequipItem() with full player inventory");
            printFieldItems("player");
        } catch (Exception ex) {
            System.out.println("! --> 5. Error unequipItem() with full player inventory");
            System.out.println(ex);
        }

        try {
            Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().dropItem(0);
            System.out.println("✓ 6. Dropped 0. item");
            printInventory("player");
        } catch (Exception ex) {
            System.out.println("! --> 6. Error dropping 0. item");
            System.out.println(ex);
        }

        try {
            Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().addItem(new ItemLog(10));
            System.out.println("✓ 7. Added 0. stackable logs (10db)");
            printInventory("player");
        } catch (Exception ex) {
            System.out.println("! --> 7. Error adding 0. stackable logs (10db)");
            System.out.println(ex);
        }

        try {
            Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().swapItems(0, 2);
            System.out.println("✓ 8. Successfully swapped items 0. (10db log) with 2. (AXE)");
            printInventory("player");
        } catch (Exception ex) {
            System.out.println("! --> 8. Error swapping items 0. (10db log) with 2. (AXE)");
            System.out.println(ex);
        }

        try {
            System.out.println("    9. tesz:");
            for (int i = 0; i < 10; i++) {
                Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().dropItem(i);
                printInventory("player");
            }
            System.out.println("✓ 9. Successfully dropped all items from inventory");
        } catch (Exception ex) {
            System.out.println("! --> 9. Error while dropping all items from inventory");
        }

        try {
            System.out.println("    10. test:");
            int i = 9;
            while (i > 0) {
                Objects.requireNonNull(GameManager.getInstance().getCharacter("player")).getInventory().addItem(new ItemLog(5));
                printInventory("player");
                i--;
            }
            System.out.println("✓ 10. Successfully added 9x5db log to player inventory");
        } catch (Exception ex) {
            System.out.println("! --> 10. Error while adding 9x5db log to player inventory");
        }


    }

}
