package prog1.kotprog.dontstarve.solution.inventory;

import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;
import prog1.kotprog.dontstarve.solution.level.Field;
import prog1.kotprog.dontstarve.solution.utility.Position;

import java.nio.file.attribute.AttributeView;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.isNull;

public class Inventory implements BaseInventory {

    private AbstractItem[] inventory = new AbstractItem[10];
    private EquippableItem equippedItem = null;
    private String characterName = null;

    private final ItemType[] stackable = {ItemType.LOG, ItemType.STONE, ItemType.TWIG, ItemType.RAW_CARROT, ItemType.COOKED_CARROT, ItemType.RAW_BERRY, ItemType.COOKED_BERRY};
    private final ItemType[] eatable = {ItemType.RAW_CARROT, ItemType.RAW_BERRY, ItemType.COOKED_CARROT, ItemType.COOKED_BERRY};
    private final ItemType[] equippable = {ItemType.AXE, ItemType.PICKAXE, ItemType.TORCH, ItemType.SPEAR};

    public Inventory() {

    }

    public Inventory(String characterName) {
        this.characterName = characterName;
    }

    @Override
    public boolean addItem(AbstractItem item) {

        if (item.getType() != null && item.getType() != ItemType.FIRE) {
            //Equipable
            if (item instanceof EquippableItem) {
                if (emptySlots() == 0) {
                    return false;
                } else {
                    int i = 0;
                    for (AbstractItem abstractItem : inventory) {
                        if (item.getAmount() == 0) {
                            break;
                        }
                        if (abstractItem == null) {
                            inventory[i] = item;
                            inventory[i].setAmount(1);
                            item.setAmount(item.getAmount() - 1);
                        }
                        i++;
                    }
                    return item.getAmount() == 0;
                }
            }
            //Stackable
            else {
                //Ugyan olyan tipusuakra
                int i = 0;
                for (AbstractItem abstractItem : inventory) {
                    if (item.getAmount() == 0) {
                        return true;
                    }
                    if (abstractItem != null && abstractItem.getType().equals(item.getType()) && abstractItem.getAmount() < abstractItem.getMaxAmount()) {
                        int db = abstractItem.getMaxAmount() - abstractItem.getAmount();

                        if (db > item.getAmount()) {
                            inventory[i].setAmount(inventory[i].getAmount() + item.getAmount());
                            item.setAmount(0);
                        } else {
                            inventory[i].setAmount(inventory[i].getMaxAmount());
                            item.setAmount(item.getAmount() - db);
                        }
                    }
                    i++;
                }

                //Nullokra a maradékot
                if (item.getAmount() == 0) {
                    return true;
                } else {
                    i = 0;
                    for (AbstractItem abstractItem : inventory) {
                        if (item.getAmount() == 0) {
                            return true;
                        }
                        if (abstractItem == null) {
                            if (item.getAmount() > item.getMaxAmount()) {
                                inventory[i] = item;
                                inventory[i].setAmount(item.getMaxAmount());
                                item.setAmount(item.getAmount() - item.getMaxAmount());
                            } else {
                                inventory[i] = item;
                                return true;
                            }
                        }
                        i++;
                    }
                }
                return item.getAmount() == 0;
            }
        }

        return false;
    }

    /*
    @Override
    public boolean addItem(AbstractItem item) {

        if (item.getType() != ItemType.FIRE && item.getType() != null) {
            //Stackable
            if (Arrays.asList(stackable).contains(item.getType())) {

                int i = 0;
                for (AbstractItem abstractItem : inventory) {
                    if (abstractItem != null && abstractItem.getType().equals(item.getType())) {
                        if (abstractItem.getAmount() < abstractItem.getMaxAmount()) {
                            int db = abstractItem.getMaxAmount() - abstractItem.getAmount();
                            if (db < item.getAmount()) {
                                item.setAmount(item.getAmount() - db);
                                inventory[i].setAmount(inventory[i].getAmount() + db);
                            } else {
                                inventory[i].setAmount(inventory[i].getAmount() + item.getAmount());
                                item.setAmount(0);
                            }
                        }
                    }
                    i++;
                }

                if (item.getAmount() == 0) {
                    return true;
                } else if (emptySlots() > 0) {
                    i = 0;
                    for (AbstractItem abstractItem : inventory) {
                        if (abstractItem == null) {
                            if (item.getAmount() > item.getMaxAmount()) {
                                item.setAmount(item.getAmount() - item.getMaxAmount());
                                inventory[i] = item;
                                inventory[i].setAmount(inventory[i].getMaxAmount());
                            } else {
                                inventory[i] = item;
                                return true;
                            }
                        }
                        i++;
                    }

                    return item.getAmount() == 0;
                }
            //Equipable
            } else {
                if (emptySlots() > 0) {
                    int i = 0;
                    for (AbstractItem ai : inventory) {
                        if (ai == null) {
                            inventory[i] = item;
                            inventory[i].setAmount(1);
                            item.setAmount(item.getAmount() - 1);
                        }
                        i++;
                    }

                    return item.getAmount() == 0;
                }
            }
        }

        return false;
    }
     */

    @Override
    public AbstractItem dropItem(int index) {
        if (index >= 0 && index <= 9) {
            AbstractItem dropped = inventory[index];
            inventory[index] = null;
            return dropped;
        }
        return null;
    }

    public void addItemToIndex(int index, AbstractItem item) {
        inventory[index] = item;
    }

    @Override
    public boolean removeItem(ItemType type, int amount) {
        int elerhetoMennyiseg = 0;
        for (AbstractItem item : inventory) {
            if (item.getType().equals(type)) {
                elerhetoMennyiseg += item.getAmount();
            }
        }

        if (elerhetoMennyiseg >= amount) {
            int i = 0;
            for (AbstractItem item : inventory) {
                if (amount > 0) {
                    if (item.getType().equals(type)) {
                        if (item.getAmount() <= amount) {
                            amount -= item.getMaxAmount();
                            inventory[i] = null;
                        } else {
                            inventory[i].setAmount(inventory[i].getAmount() - amount);
                            return true;
                        }
                    }
                }
                i++;
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean swapItems(int index1, int index2) {

        if (index1 >= 0 && index1 <= 9 && index2 >= 0 && index2 <= 9) {
            if (inventory[index1] != null && inventory[index2] != null) {
                AbstractItem tmp = inventory[index2];
                inventory[index2] = inventory[index1];
                inventory[index1] = tmp;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean moveItem(int index, int newIndex) {
        if (index >= 0 && index <= 9 && newIndex >= 0 && newIndex <= 9) {
            if (inventory[index] != null && inventory[newIndex] == null) {
                inventory[newIndex] = inventory[index];
                inventory[index] = null;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean combineItems(int index1, int index2) {

        if (index1 >= 0 && index1 <= 9 && index2 >= 0 && index2 <= 9 && inventory[index1] != null && inventory[index2] != null) {
            if (inventory[index1].getType().equals(inventory[index2].getType())) {
                if (Arrays.asList(stackable).contains(inventory[index1].getType()) && Arrays.asList(stackable).contains(inventory[index2].getType())) {
                    if (inventory[index1].getAmount() + inventory[index2].getAmount() <= inventory[index1].getMaxAmount()) {
                        inventory[index1].setAmount(inventory[index1].getAmount() + inventory[index2].getAmount());
                        inventory[index2] = null;
                    } else {
                        inventory[index2].setAmount(inventory[index2].getAmount() - (inventory[index1].getMaxAmount() - inventory[index1].getAmount()));
                        inventory[index1].setAmount(inventory[index1].getMaxAmount());
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean equipItem(int index) {

        if (inventory[index] != null) {
            if (Arrays.asList(equippable).contains(inventory[index].getType())) {
                if (equippedItem != null) {
                    EquippableItem tmp = equippedItem;
                    equippedItem = (EquippableItem) inventory[index];
                    inventory[index] = tmp;
                } else {
                    equippedItem = (EquippableItem) inventory[index];
                    inventory[index] = null;
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public EquippableItem unequipItem() {

        if (equippedItem != null) {
            if (emptySlots() == 0) {
                Position p = Objects.requireNonNull(GameManager.getInstance().getCharacter(characterName)).getCurrentPosition().getNearestWholePosition();
                ((Field) GameManager.getInstance().getField(((int) p.getX()), ((int) p.getY()))).addItem(equippedItem);
                equippedItem = null;
            } else {
                for (int i = 0; i < inventory.length; i++) {
                    if (inventory[i] == null) {
                        inventory[i] = equippedItem;
                        equippedItem = null;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public ItemType cookItem(int index) {

        if (index >= 0 && index <= 9 && inventory[index] != null) {
            if (Arrays.asList(eatable).contains(inventory[index].getType()) && inventory[index].getType().name().contains("RAW")) {
                if (inventory[index].getAmount() == 1) {
                    ItemType itemType = inventory[index].getType();
                    inventory[index] = null;
                    return itemType;
                } else if (inventory[index].getAmount() >= 1) {
                    inventory[index].setAmount(inventory[index].getAmount() - 1);
                    return inventory[index].getType();
                }
            }
        }

        return null;
    }

    @Override
    public ItemType eatItem(int index) {

        if (index >= 0 && index <= 9 && inventory[index] != null) {
            if (Arrays.asList(eatable).contains(inventory[index].getType())) {
                if (inventory[index].getAmount() == 1) {
                    ItemType itemType = inventory[index].getType();
                    inventory[index] = null;
                    return itemType;
                } else if (inventory[index].getAmount() >= 1) {
                    inventory[index].setAmount(inventory[index].getAmount() - 1);
                    return inventory[index].getType();
                }
            }
        }

        return null;
    }

    @Override
    public int emptySlots() {
        int space = 0;

        for (AbstractItem abstractItem : inventory) {
            if (abstractItem == null) {
                space++;
            }
        }

        return space;
    }

    @Override
    public EquippableItem equippedItem() {
        return equippedItem;
    }

    @Override
    public AbstractItem getItem(int index) {
        if (index >= 0 && index <= 9) {
            return inventory[index];
        }
        return null;
    }

    public AbstractItem[] getItems() {
        return inventory;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
