package logic.managers;

public enum SupplyItem {
    FUEL("Jet Fuel", 5000, 2500),
    MEALS("Meals", 100, 500),
    CARTS("Luggage Carts", 100, 1200);

    private final String displayName;
    private final int defaultAmount;
    private final int defaultCost;

    SupplyItem(String displayName, int amount, int cost) {
        this.displayName = displayName;
        this.defaultAmount = amount;
        this.defaultCost = cost;
    }

    public String getDisplayName() { return displayName; }
    public int getDefaultAmount() { return defaultAmount; }
    public int getDefaultCost() { return defaultCost; }

    public static SupplyItem fromString(String text) {
        for (SupplyItem item : SupplyItem.values()) {
            if (item.displayName.equalsIgnoreCase(text)) {
                return item;
            }
        }
        return null;
    }
}