package prog1.kotprog.dontstarve.solution.level;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;

public class Field implements BaseField {

    private int color;
    private String hex;
    @Override
    public boolean isWalkable() {
        return !hex.equals("#3264C8");
    }

    @Override
    public boolean hasTree() {
        return false;
    }

    @Override
    public boolean hasStone() {
        return false;
    }

    @Override
    public boolean hasTwig() {
        return false;
    }

    @Override
    public boolean hasBerry() {
        return false;
    }

    @Override
    public boolean hasCarrot() {
        return false;
    }

    @Override
    public boolean hasFire() {
        return false;
    }

    @Override
    public AbstractItem[] items() {
        return new AbstractItem[0];
    }

    public void setColor(int color) {
        this.color = color;
        this.hex = String.format("#%06X", (0xFFFFFF & color));
    }
}
