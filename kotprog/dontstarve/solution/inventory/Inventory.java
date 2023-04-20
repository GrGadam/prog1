package prog1.kotprog.dontstarve.solution.inventory;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;

import java.util.ArrayList;
import java.util.List;

public class Inventory implements BaseInventory {

    private List<AbstractItem> inventory = new ArrayList<>(10);
    private int equippedItem;

    @Override
    public boolean addItem(AbstractItem item) {

        if (item instanceof EquippableItem) {
            for (int i = 0; i < 10; i++) {
                if (inventory.get(i) == null) {
                    inventory.add(i, item);
                    return true;
                }
            }
        }
        else {
            for ( AbstractItem i : inventory ) {
                if (i.getType().equals(item.getType())) {
                    if (i.getAmount() < i.getMaxAmount()) {
                        //Ha az item darbszámának hozzáadása meghaladná a max stackméretet
                        if (i.getAmount() + item.getAmount() > i.getMaxAmount()) {
                            int maradek = (i.getAmount() + item.getAmount()) % i.getMaxAmount();
                            i.setAmount(i.getMaxAmount());

                            for (int j = 0; j < 10; j++) {
                                if (inventory.get(j) == null) {
                                    item.setAmount(maradek);
                                    inventory.add(j, item);
                                }
                            }
                            return true;
                        }
                        else {
                            for (int j = 0; j < 10; j++) {
                                if (inventory.get(j) == null) {
                                    inventory.add(j, item);
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public AbstractItem dropItem(int index) {
        return null;
    }

    @Override
    public boolean removeItem(ItemType type, int amount) {
        return false;
    }

    @Override
    public boolean swapItems(int index1, int index2) {
        return false;
    }

    @Override
    public boolean moveItem(int index, int newIndex) {
        return false;
    }

    @Override
    public boolean combineItems(int index1, int index2) {
        return false;
    }

    @Override
    public boolean equipItem(int index) {
        return false;
    }

    @Override
    public EquippableItem unequipItem() {
        return null;
    }

    @Override
    public ItemType cookItem(int index) {
        return null;
    }

    @Override
    public ItemType eatItem(int index) {
        return null;
    }

    @Override
    public int emptySlots() {
        return 0;
    }

    @Override
    public EquippableItem equippedItem() {
        return null;
    }

    @Override
    public AbstractItem getItem(int index) {
        return null;
    }
}
