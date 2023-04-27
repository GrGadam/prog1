package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.character.Character;
import prog1.kotprog.dontstarve.solution.inventory.items.*;
import prog1.kotprog.dontstarve.solution.level.Field;
import prog1.kotprog.dontstarve.solution.utility.Position;

import java.util.ArrayList;
import java.util.Objects;

public class ActionManager {
    private Action action;
    private Character character;

    public ActionManager(Action action, String name) {
        this.action = action;
        this.character = (Character) GameManager.getInstance().getCharacter(name);
    }

    public void start() {
        switch (action.getType()) {
            case NONE -> none();
            case STEP -> step();
            case INTERACT -> interact();
            case ATTACK -> attack();
            case STEP_AND_ATTACK -> stepAndAttack();
            case CRAFT -> craft();
            case COLLECT_ITEM -> collectItem();
            case DROP_ITEM -> dropItem();
            case EAT -> eat();
            case EQUIP -> equip();
            case UNEQUIP -> unequip();
            case COOK -> cook();
            case MOVE_ITEM -> moveItem();
            case SWAP_ITEMS -> swapItems();
            case COMBINE_ITEMS -> combineItems();
        }
    }

    private void setLastAction() {
        ((Character) Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName()))).setLastAction(this.action);
    }

    private void none() {
        setLastAction();
    }

    private void step() {
        ActionStep actionStep = (ActionStep) this.action;
        Position position = character.getCurrentPosition();

        switch (actionStep.getDirection()) {
            case UP -> {
                if (position.getY() - character.getSpeed() >= 0) {
                    int x = (int) position.getX();
                    int y = (int) (position.getY() - character.getSpeed());
                    if (Objects.requireNonNull(GameManager.getInstance().getField(x, y)).isWalkable()) {
                        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getCurrentPosition().setY(position.getY() - character.getSpeed());
                    }
                }
            }

            case DOWN -> {
                if (position.getY() + character.getSpeed() < GameManager.getInstance().getLevel().getHeight()) {
                    int x = (int) position.getX();
                    int y = (int) (position.getY() + character.getSpeed());
                    if (Objects.requireNonNull(GameManager.getInstance().getField(x, y)).isWalkable()) {
                        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getCurrentPosition().setY(position.getY() + character.getSpeed());
                    }
                }
            }

            case RIGHT -> {
                if (position.getX() + character.getSpeed() < GameManager.getInstance().getLevel().getWidth()) {
                    int x = (int) (position.getX() + character.getSpeed());
                    int y = (int) position.getY();
                    if (Objects.requireNonNull(GameManager.getInstance().getField(x, y)).isWalkable()) {
                        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getCurrentPosition().setX(position.getX() + character.getSpeed());
                    }
                }
            }

            case LEFT -> {
                if (position.getX() - character.getSpeed() >= 0) {
                    int x = (int) (position.getX() - character.getSpeed());
                    int y = (int) position.getY();
                    if (Objects.requireNonNull(GameManager.getInstance().getField(x, y)).isWalkable()) {
                        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getCurrentPosition().setX(position.getX() - character.getSpeed());
                    }
                }
            }
        }

        setLastAction();
    }

    private void interact() {
        Position position = character.getCurrentPosition().getNearestWholePosition();
        Field field = (Field) GameManager.getInstance().getField((int) position.getX(), (int) position.getY());

        //Tree and has axe
        if (field.hasTree() && character.getInventory().equippedItem().equals(new ItemAxe())) {
            field.gather();
        }
        //Stone and has pickaxe
        else if (field.hasStone() && character.getInventory().equippedItem().equals(new ItemPickaxe())) {
            field.gather();
        }
        //Twig, Berry, Carrot
        else if (field.hasTwig() || field.hasBerry() || field.hasCarrot()) {
            field.gather();
        }

        setLastAction();
    }

    private BaseCharacter scanForAttack() {
        ArrayList<Character> characters = GameManager.getInstance().getCharacters();
        int sor = (int) character.getCurrentPosition().getY();
        int oszlop = (int) character.getCurrentPosition().getX();

        for (int i = sor - 1; i < sor + 1; i++) {
            for (int j = oszlop - 1; j < sor - 1; j++) {
                for (Character c : characters) {
                    if (!c.getName().equals(character.getName())) {
                        Position position = c.getCurrentPosition().getNearestWholePosition();
                        if ((int) position.getX() == i && (int) position.getY() == j) {

                        }
                    }
                }
            }
        }
        return null;
    }

    private void attack() {


        setLastAction();
    }

    private void stepAndAttack() {
        setLastAction();
    }

    private void craft() {
        ActionCraft actionCraft = (ActionCraft) this.action;
        Position p = character.getCurrentPosition();

        switch (actionCraft.getItemType()) {
            case AXE -> {
                if (character.getInventory().removeItem(ItemType.TWIG, 3)) {
                    if (!character.getInventory().addItem(new ItemAxe())) {
                        ((Field) Objects.requireNonNull(GameManager.getInstance().getField((int) p.getX(), (int) p.getY()))).addItem(new ItemAxe());
                    }
                }
            }
            case PICKAXE -> {
                if (character.getInventory().removeItem(ItemType.LOG, 2) && character.getInventory().removeItem(ItemType.TWIG, 2)) {
                    if (!character.getInventory().addItem(new ItemPickaxe())) {
                        ((Field) Objects.requireNonNull(GameManager.getInstance().getField((int) p.getX(), (int) p.getY()))).addItem(new ItemPickaxe());
                    }
                }
            }
            case SPEAR -> {
                if (character.getInventory().removeItem(ItemType.LOG, 2) && character.getInventory().removeItem(ItemType.STONE, 2)) {
                    if (!character.getInventory().addItem(new ItemSpear())) {
                        ((Field) Objects.requireNonNull(GameManager.getInstance().getField((int) p.getX(), (int) p.getY()))).addItem(new ItemSpear());
                    }
                }
            }
            case TORCH -> {
                if (character.getInventory().removeItem(ItemType.LOG, 1) && character.getInventory().removeItem(ItemType.TWIG, 3)) {
                    if (!character.getInventory().addItem(new ItemTorch())) {
                        ((Field) Objects.requireNonNull(GameManager.getInstance().getField((int) p.getX(), (int) p.getY()))).addItem(new ItemTorch());
                    }
                }
            }
            case FIRE -> {
                if (character.getInventory().removeItem(ItemType.TWIG, 2) && character.getInventory().removeItem(ItemType.LOG, 2) && character.getInventory().removeItem(ItemType.STONE, 4)) {
                    if (((Field) Objects.requireNonNull(GameManager.getInstance().getField((int) p.getX(), (int) p.getY()))).isEmpty()) {
                        ((Field) Objects.requireNonNull(GameManager.getInstance().getField((int) p.getX(), (int) p.getY()))).setFire();
                    } else {
                        character.getInventory().addItem(new ItemTwig(2));
                        character.getInventory().addItem(new ItemLog(2));
                        character.getInventory().addItem(new ItemStone(4));
                    }
                }
            }
        }

        setLastAction();
    }

    private void collectItem() {
        Position position = character.getCurrentPosition().getNearestWholePosition();
        Field field = (Field) GameManager.getInstance().getField((int) position.getX(), (int) position.getY());

        if (field.items() != null) {
            int i = 0;
            for (AbstractItem abstractItem : field.items()) {
                if (abstractItem != null) {
                    //Equipable
                    if (abstractItem instanceof EquippableItem) {
                        if (Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().addItem(abstractItem)) {
                            field.removeItem(i);
                        }
                    }
                    //Stackable
                    else {
                        if (Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().addItem(abstractItem)) {
                            field.removeItem(i);
                        } else {
                            field.removeItem(i);
                            field.addItem(abstractItem);
                        }
                    }
                }
                i++;
            }
        }


        setLastAction();
    }

    private void dropItem() {
        ActionDropItem actionDropItem = (ActionDropItem) this.action;
        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().dropItem(actionDropItem.getIndex());
        setLastAction();
    }

    private void eat() {
        ActionEat actionEat = (ActionEat) this.action;
        Character c = (Character) GameManager.getInstance().getCharacter(character.getName());

        switch (Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().eatItem(actionEat.getIndex())) {
            case RAW_BERRY -> {
                c.setHp((int) c.getHp() - 5);
                c.setHunger((int) c.getHunger() + 20);
            }
            case RAW_CARROT -> {
                c.setHp((int) c.getHp() + 1);
                c.setHunger((int) c.getHunger() + 12);
            }
            case COOKED_BERRY -> {
                c.setHp((int) c.getHp() + 1);
                c.setHunger((int) c.getHunger() + 10);
            }
            case COOKED_CARROT -> {
                c.setHp((int) c.getHp() + 3);
                c.setHunger((int) c.getHunger() + 10);
            }
        }

        setLastAction();
    }

    private void equip() {
        ActionEquip actionEquip = (ActionEquip) this.action;
        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().equipItem(actionEquip.getIndex());
        setLastAction();
    }

    private void unequip() {
        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().unequipItem();
        setLastAction();
    }

    private void cook() {
        ActionCook actionCook = (ActionCook) this.action;
        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().cookItem(actionCook.getIndex());
        setLastAction();
    }

    private void moveItem() {
        ActionMoveItem actionMoveItem = (ActionMoveItem) this.action;
        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().moveItem(actionMoveItem.getOldIndex(), actionMoveItem.getNewIndex());
        setLastAction();
    }

    private void swapItems() {
        ActionSwapItems actionSwapItems = (ActionSwapItems) this.action;
        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().swapItems(actionSwapItems.getIndex1(), actionSwapItems.getIndex2());
        setLastAction();
    }

    private void combineItems() {
        ActionCombineItems actionCombineItems = (ActionCombineItems) this.action;
        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().combineItems(actionCombineItems.getIndex1(), actionCombineItems.getIndex2());
        setLastAction();
    }

}
