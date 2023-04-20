package prog1.kotprog.dontstarve.solution.character;

import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.Inventory;
import prog1.kotprog.dontstarve.solution.utility.Position;

public class Character implements BaseCharacter {

    private String name;
    private int health = 100;
    private int hunger = 100;
    private Inventory inventory = new Inventory();
    private Position position;
    private Action lastAction;

    public Character (String name) {
        this.name = name;
    }

    @Override
    public float getSpeed() {
        float speed = 1;

        if (this.health >= 50) { speed *= 1.0F; }
        else if (this.health >= 30) { speed *= 0.9F; }
        else if (this.health >= 10) { speed *= 0.75F; }
        else { speed *= 0.6F;}

        if (this.hunger >= 50) { speed *= 1.0F; }
        else if (this.health >= 20) { speed *= 0.9F; }
        else if (this.health > 0) { speed *= 0.8F; }
        else { speed *= 0.5F;}

        return speed;
    }

    @Override
    public float getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    @Override
    public float getHp() {
        return health;
    }

    public void setHp(int health) {
        this.health = health;
    }

    @Override
    public BaseInventory getInventory() {
        return inventory;
    }

    @Override
    public Position getCurrentPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Action getLastAction() {
        return lastAction;
    }

    public void setLastAction(Action lastAction) {
        this.lastAction = lastAction;
    }

    @Override
    public String getName() {
        return name;
    }
}
