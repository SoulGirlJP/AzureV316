package server.Shops;

public class MapleShopItem {

    private short buyable;
    private int itemId;
    private int price;
    private int position;
    private int pricequantity;
    private byte tab;
    private int quantity;
    private int period;

    public MapleShopItem(short buyable, int itemId, int price, int pricequantity, byte tab, int quantity, int period, int position) {
        this.buyable = buyable;
        this.itemId = itemId;
        this.price = price;
        this.pricequantity = pricequantity;
        this.tab = tab;
        this.quantity = quantity;
        this.period = period;
        this.position = position;
    }

    public int getPriceQuantity() {
        return pricequantity;
    }

    public byte getTab() {
        return tab;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPeriod() {
        return period;
    }

    public short getBuyable() {
        return buyable;
    }

    public int getItemId() {
        return itemId;
    }

    public int getPrice() {
        return price;
    }

    public int getPosition() {
        return position;
    }
}
