package prog1.kotprog.dontstarve.solution.inventory;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;

import java.util.Arrays;

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

        if (Arrays.asList(stackable).contains(item.getType())) {
            int i = 0;
            for (AbstractItem ai : inventory) {
                if (ai.getType().equals(item.getType())) {
                    if (ai.getAmount() < ai.getMaxAmount()) {
                        if (ai.getAmount() + item.getAmount() > ai.getMaxAmount()) {
                            item.setAmount(item.getAmount() - (ai.getMaxAmount() - ai.getAmount()));
                            inventory[i].setAmount(inventory[i].getMaxAmount());
                        } else {
                            inventory[i].setAmount(inventory[i].getAmount() + item.getAmount());
                            return true;
                        }
                    }
                }
                i++;
            }

            if (item.getAmount() > 0) {
                if (emptySlots() > 0) {
                    i = 0;
                    for (AbstractItem ai : inventory) {
                        if (ai == null) {
                            inventory[i] = item;
                            return true;
                        }
                        i++;
                    }
                }
            }
        } else {
            if (emptySlots() > 0) {
                int i = 0;
                for (AbstractItem ai : inventory) {
                    if (ai == null) {
                        inventory[i] = item;
                        return true;
                    }
                    i++;
                }
            }
        }

        return false;
    }

    @Override
    public AbstractItem dropItem(int index) {
        AbstractItem dropped = inventory[index];
        inventory[index] = null;
        return dropped;
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

        if (inventory[index1] != null && inventory[index2] != null) {
            AbstractItem tmp = inventory[index2];
            inventory[index2] = inventory[index1];
            inventory[index1] = tmp;
            return true;
        }

        return false;
    }

    @Override
    public boolean moveItem(int index, int newIndex) {

        if (inventory[index] != null && inventory[newIndex] == null) {
            inventory[newIndex] = inventory[index];
            inventory[index] = null;
            return true;
        }

        return false;
    }

    @Override
    public boolean combineItems(int index1, int index2) {

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

        return false;
    }

    @Override
    public boolean equipItem(int index) {

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

        return false;
    }

    @Override
    public EquippableItem unequipItem() {

        if (equippedItem != null) {
            if (emptySlots() == 0) {
                //TODO drop item
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

        return null;
    }

    @Override
    public ItemType eatItem(int index) {
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
        return inventory[index];
    }

    public AbstractItem[] getItems() {
        return inventory;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
