package prog1.kotprog.dontstarve.solution.character;

import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.Inventory;
import prog1.kotprog.dontstarve.solution.utility.Position;

public class Character implements BaseCharacter {

    private final String name;
    private float health;
    private float hunger;
    private final Inventory inventory;
    private Position position;
    private Action lastAction;
    private final boolean player;

    public Character(String name, boolean player) {
        this.name = name;
        this.inventory = new Inventory(name);
        this.health = 100;
        this.hunger = 100;
        this.player = player;
    }

    @Override
    public float getSpeed() {
        float speed = 1;

        if (this.health >= 50) {
            speed *= 1.0F;
        } else if (this.health >= 30) {
            speed *= 0.9F;
        } else if (this.health >= 10) {
            speed *= 0.75F;
        } else {
            speed *= 0.6F;
        }

        if (this.hunger >= 50) {
            speed *= 1.0F;
        } else if (this.hunger >= 20) {
            speed *= 0.9F;
        } else if (this.hunger > 0) {
            speed *= 0.8F;
        } else {
            speed *= 0.5F;
        }

        return speed;
    }

    @Override
    public float getHunger() {
        return hunger;
    }

    public void setHunger(float hunger) {
        this.hunger = hunger;
    }

    @Override
    public float getHp() {
        return health;
    }

    public void setHp(float health) {
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

    public boolean isPlayer() {
        return player;
    }

}
