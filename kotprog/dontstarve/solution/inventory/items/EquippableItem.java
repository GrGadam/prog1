package prog1.kotprog.dontstarve.solution.inventory.items;

import prog1.kotprog.dontstarve.solution.exceptions.NotImplementedException;

/**
 * Felvehető / kézbe vehető item leírására szolgáló osztály.
 */
public abstract class EquippableItem extends AbstractItem {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param type   az item típusa
     */

    private float percentage;

    public EquippableItem(ItemType type) {
        super(type, 1);
    }

    /**
     * Megadja, hogy milyen állapotban van a tárgy.
     * @return a tárgy használatlansága, %-ban (100%: tökéletes állapot)
     */
    public float percentage() {
        return this.percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
