package prog1.kotprog.dontstarve.solution.level;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;

import java.util.ArrayList;

public class Field implements BaseField {

    private ArrayList<AbstractItem> items;
    private boolean hasFire;
    private String hex;

    public Field() {
        items = new ArrayList<>();
        hasFire = false;
    }

    @Override
    public boolean isWalkable() {
        return !hex.equals("#3264C8");
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
        return hex.equals("#F0000");
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
            return true;
        }
        return false;
    }

    @Override
    public AbstractItem[] items() {
        return items.toArray(new AbstractItem[0]);
    }

    public boolean addItem(AbstractItem item) {
        if (isWalkable()) {
            items.add(item);
            return true;
        }
        return false;
    }

    public void setColor(int color) {
        this.hex = String.format("#%06X", (0xFFFFFF & color));
    }

}
