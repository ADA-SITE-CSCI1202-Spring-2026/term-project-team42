package logic.managers;

public enum SupplyItem {
    FUEL("Jet Fuel", 5000, 2500, 100000),
    MEALS("Meals", 100, 500, 1000),
    CARTS("Luggage Carts", 100, 1200, 1000);

    private final String displayName;
    private final int defaultAmount;
    private final int defaultCost;
    private final int maxCapacity;

    SupplyItem(String displayName, int amount, int cost, int maxCapacity) {
        this.displayName = displayName;
        this.defaultAmount = amount;
        this.defaultCost = cost;
        this.maxCapacity = maxCapacity;
    }

    public String getDisplayName() { return displayName; }
    public int getDefaultAmount() { return defaultAmount; }
    public int getDefaultCost() { return defaultCost; }
    public int getMaxCapacity() { return maxCapacity; }


    public static SupplyItem fromString(String text) {
        for (SupplyItem item : SupplyItem.values()) {
            if (item.displayName.equalsIgnoreCase(text)) {
                return item;
            }
        }
        return null;
    }
}