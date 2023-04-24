package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.character.Character;
import prog1.kotprog.dontstarve.solution.utility.Position;

import java.util.Objects;

public class ActionManager {
    private Action action;
    private Character character;

    public ActionManager(Action action, Character character) {
        this.action = action;
        this.character = character;
    }

    public void start() {
        switch (action.getType()) {
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

    private void step() {
        ActionStep actionStep = (ActionStep) this.action;
        Position position = character.getCurrentPosition();

        switch (actionStep.getDirection()) {
            case UP -> {
                if (position.getY() - character.getSpeed() > 0) {
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
                if (position.getX() - character.getSpeed() > 0) {
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
        ActionInteract actionInteract = (ActionInteract) this.action;

        setLastAction();
    }

    private void attack() {
        setLastAction();
    }

    private void stepAndAttack() {
        setLastAction();
    }

    private void craft() {
        ActionCraft actionCraft = (ActionCraft) this.action;
        setLastAction();
    }

    private void collectItem() {
        setLastAction();
    }

    private void dropItem() {
        ActionDropItem actionDropItem = (ActionDropItem) this.action;
        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().dropItem(actionDropItem.getIndex());
        setLastAction();
    }

    private void eat() {
        ActionEat actionEat = (ActionEat) this.action;
        Objects.requireNonNull(GameManager.getInstance().getCharacter(character.getName())).getInventory().eatItem(actionEat.getIndex());
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
