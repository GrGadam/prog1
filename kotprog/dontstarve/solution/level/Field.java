package prog1.kotprog.dontstarve.solution.level;

import prog1.kotprog.dontstarve.solution.inventory.items.*;

import java.util.ArrayList;

public class Field implements BaseField {

    private ArrayList<AbstractItem> items;
    private boolean hasFire;
    private String hex;
    private int health;
    private int fireHealth;

    public Field(int color) {
        this.items = new ArrayList<>();
        this.hasFire = false;
        this.hex = String.format("#%06X", (0xFFFFFF & color));

        if (hasTree()) {
            health = 4;
        } else if (hasStone()) {
            health = 5;
        } else if (hasTwig()) {
            health = 2;
        } else if (hasBerry() || hasCarrot()) {
            health = 1;
        }
    }

    @Override
    public boolean isWalkable() {
        return !hex.equals("#3264C8");
    }

    public boolean isEmpty() {
        return hex.equals("#32C832");
    }

    @Override
    public boolean hasTree() {
        return hex.equals("#C86432");
    }

    @Override
    public boolean hasStone() {
        return hex.equals("#C8C8C8");
    }

    @Override
    public boolean hasTwig() {
        return hex.equals("#F0B478");
    }

    @Override
    public boolean hasBerry() {
        return hex.equals("#FF0000");
    }

    @Override
    public boolean hasCarrot() {
        return hex.equals("#FAC800");
    }

    @Override
    public boolean hasFire() {
        return hasFire;
    }

    public boolean setFire() {
        if (!hasFire) {
            hasFire = true;
            fireHealth = 60;
            return true;
        }
        return false;
    }

    @Override
    public AbstractItem[] items() {
        if (isWalkable()) {
            return items.toArray(new AbstractItem[0]);
        }
        return null;
    }

    public boolean addItem(AbstractItem item) {
        if (isWalkable()) {
            items.add(item);
            return true;
        }
        return false;
    }

    public void removeItem(int index) {
        items.remove(index);
    }

    public String getHex() {
        return hex;
    }

    //Csökkenti a mező értékét, ha elfogy az élete akkor áralakítja üres mezővé és az itemek közé adja az itemeket
    //false ha nem lett az életerő 0
    public boolean gather() {
        if (health != 0) {
            health--;
        }

        if (health == 0) {
            if (hasTree()) {
                items.add(new ItemLog(2));
            } else if (hasStone()) {
                items.add(new ItemStone(3));
            }

            hex = "#32C832";
            return true;
        }

        return false;
    }

    public boolean tickFire() {
        if (hasFire) {
            if (fireHealth > 1) {
                fireHealth--;
            } else if (fireHealth == 1) {
                fireHealth = 0;
                this.hasFire = false;
                return true;
            }
        }
        return false;
    }

}
