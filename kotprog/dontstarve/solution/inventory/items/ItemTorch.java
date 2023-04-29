package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A fáklya item leírására szolgáló osztály.
 */
public class ItemTorch extends EquippableItem {

    /**
     * A fáklya élete
     */
    private int health = 20;

    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     */
    public ItemTorch() {
        super(ItemType.TORCH);
    }

    public boolean tick() {
        if (health > 1) {
            health--;
        } else if (health == 1) {
            health = 0;
            return true;
        }
        return false;
    }



}
