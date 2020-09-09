package server.Items;

public class CashItemInfo {

    private final int itemId, count, price, period;
    private final boolean OnSale;

    public CashItemInfo(int itemId, int count, int price, int period, boolean OnSale) {
        this.itemId = itemId;
        this.count = count;
        this.price = price;
        this.period = period;
        this.OnSale = OnSale;
    }

    public int getId() {
        return itemId;
    }

    public int getCount() {
        return count;
    }

    public int getPeriod() {
        return period;
    }

    public int getPrice() {
        return price;
    }

    public boolean getOnSale() {
        return OnSale;
    }
}
