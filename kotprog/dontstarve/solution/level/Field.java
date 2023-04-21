package prog1.kotprog.dontstarve.solution.level;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;

public class Field implements BaseField {

    private boolean hasFire = false;
    private String hex;
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

    @Override
    public AbstractItem[] items() {
        return new AbstractItem[0];
    }

    public void setColor(int color) {
        this.hex = String.format("#%06X", (0xFFFFFF & color));
    }

}
