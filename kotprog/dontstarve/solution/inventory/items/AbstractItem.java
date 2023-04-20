package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * Egy általános itemet leíró osztály.
 */
public abstract class AbstractItem {
    /**
     * Az item típusa.
     * @see ItemType
     */
    private final ItemType type;

    /**
     * Az item mennyisége.
     */
    private int amount;

    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     * @param type az item típusa
     * @param amount az item mennyisége
     */
    public AbstractItem(ItemType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    /**
     * A type gettere.
     * @return a tárgy típusa
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Az amount gettere.
     * @return a tárgy mennyisége
     */
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMaxAmount() {
        switch (this.type) {
            case LOG -> {
                return 15;
            }
            case STONE, RAW_CARROT, COOKED_CARROT, RAW_BERRY, COOKED_BERRY -> {
                return 10;
            }
            case TWIG -> {
                return 20;
            }
            default -> {
                return 1;
            }
        }
    }
}
